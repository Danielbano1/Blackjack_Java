package controller;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

import model.APImodel;
import model.SalvarPartida;
import view.*;

import javax.swing.JOptionPane;

public class Controller {
	
    public APImodel apiModel;
    private JanelaInicial janelaInicial;
    private JanelaCarregamento janelaCarregamento;
    private JanelaBanca janelaBanca;
    private JanelaMao maoDealer; 
    private JanelaMaoJogador maoJogador;
    private JanelaMaoJogador maoJogadorSplit;
    private TratadorDeClicks tratador;   
    public boolean split;   

    public Controller() {
        janelaInicial = new JanelaInicial();
        janelaInicial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if (janelaInicial.getBotaoNovoJogo().contains(p)) {
                    System.out.println("Botão 'Novo Jogo' clicado");
                    iniciarJogo();
                } else if (janelaInicial.getBotaoCarregarJogo().contains(p)) {
                    iniciarJogo(apiModel.carregarPartida(1));
                    System.out.println("Botão 'Continuar Jogo' clicado");
                }
            }
        });
    }

    private void iniciarJogo() {
        janelaInicial.dispose();
        apiModel = new APImodel();
        janelaBanca = new JanelaBanca();
        configurarJanelas();
        tratador = new TratadorDeClicks(this, janelaBanca);
        split = false;

    }
    private void iniciarJogo( List<Object> lista) {
        janelaInicial.dispose();
        apiModel = new APImodel(lista);
        janelaBanca = new JanelaBanca();
        configurarJanelas();
        tratador = new TratadorDeClicks(this, janelaBanca);
        split = (boolean) lista.get(2);
        maoJogadorSplit.setVisible(split);
        distribuirCartasSalvas();

    }
    
    public void mostrarMensagem(String mensagem, String titulo) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private void configurarJanelas() {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int startX = (tela.width - (1006 + 800)) / 2;
        int startY = (tela.height - 761) / 2;

        janelaBanca.setLocation(startX, startY);

        maoDealer = new JanelaMao(JanelaMao.TipoMao.DEALER);
        maoDealer.setLocation(startX + 1006, startY);

        maoJogador = new JanelaMaoJogador();
        maoJogador.setLocation(maoDealer.getX(), maoDealer.getY() + 250 + 10);

        maoJogadorSplit = new JanelaMaoJogador();
        maoJogadorSplit.setLocation(maoJogador.getX(), maoJogador.getY() + 250 + 10);

        janelaBanca.setVisible(true);
        maoDealer.setVisible(true);
        maoJogador.setVisible(true);
        maoJogadorSplit.setVisible(false); //true quando fizer split

    }

    void fazerApostas() {
        // Carregar dinheiro
        maoJogador.atualizarDinheiro(apiModel.getDinheiro());
        maoJogador.atualizarAposta(apiModel.getApostaMaoAtual());
        maoJogador.repaint();

    }

    void distribuirCartasSalvas(){
        // Configurações para o Dealer
        ArrayList<String> naipes = apiModel.getNaipesDealer();
        maoDealer.atualizarPontos(apiModel.calculaValorMaoDealer());

        for (String naipe : naipes){
            maoDealer.receberCarta(naipe);
        }
        // Configurações para o Jogador Principal
        naipes = apiModel.getNaipesPrincipal();
        maoJogador.atualizarDinheiro(apiModel.getDinheiro());
        maoJogador.atualizarPontos(apiModel.calculaValorMaoPrincipal());
        maoJogador.atualizarAposta(apiModel.getApostaMaoPrincipal());

        for (String naipe : naipes){
            maoJogador.receberCarta(naipe);
        }

        if(apiModel.isSplit()) {
            // Configurações para o Jogador Split
            naipes = apiModel.getNaipesSplit();
            maoJogadorSplit.atualizarDinheiro(apiModel.getDinheiro());
            maoJogadorSplit.atualizarPontos(apiModel.calculaValorMaoSplit());
            maoJogadorSplit.atualizarAposta(apiModel.getApostaMaoSplit());


            for (String naipe : naipes){
                maoJogadorSplit.receberCarta(naipe);
            }
        }
    }

    void distribuirCartas() {
        // Distribui as cartas
        apiModel.distribuiCarta();
        
        // Configurações para o Dealer
        maoDealer.atualizarPontos(apiModel.calculaValorMaoDealer());
        maoDealer.receberCarta(apiModel.getPrimeiroNaipeDealer());
        maoDealer.receberCarta(apiModel.getUltimoNaipeDealer());

        // Configurações para o Jogador Principal
        maoJogador.atualizarPontos(apiModel.calculaValorMaoPrincipal());
        maoJogador.receberCarta(apiModel.getPrimeiroNaipePrincipal());
        maoJogador.receberCarta(apiModel.getUltimoNaipePrincipal());

    }

    void distribuir1Carta() {
        // Configurações para o Jogador Principal
        maoJogador.atualizarPontos(apiModel.calculaValorMaoPrincipal());
        maoJogador.receberCarta(apiModel.getUltimoNaipePrincipal());

    }
    void distribuirCartaSplit() {
        // Configurações para o Split Principal
        maoJogadorSplit.atualizarPontos(apiModel.calculaValorMaoSplit());
        maoJogadorSplit.receberCarta(apiModel.getPrimeiroNaipeSplit());
        maoJogadorSplit.receberCarta(apiModel.getUltimoNaipeSplit());

    }

    void distribuir1CartaSplit() {
        // Configurações para o split Principal
        maoJogadorSplit.atualizarPontos(apiModel.calculaValorMaoSplit());
        maoJogadorSplit.receberCarta(apiModel.getUltimoNaipeSplit());

    }


    void jogaDealer() {
        apiModel.jogaDealer();
        // Configurações para o Dealer
        ArrayList<String> naipes = apiModel.getNaipesDealer();
        maoDealer.atualizarPontos(apiModel.calculaValorMaoDealer());

        for (int i = naipes.size(); i > 2; i--)
            maoDealer.receberCarta(naipes.get(i - 1));

        
        exibeResultados();
        maoJogador.atualizarDinheiro(apiModel.getDinheiro());
        maoJogadorSplit.atualizarDinheiro(apiModel.getDinheiro());
    }

    void fazSplit() {
        //atualização de dinheiro e aposta
        maoJogadorSplit.atualizarAposta(apiModel.getApostaMaoAtual());
        maoJogador.atualizarDinheiro(apiModel.getDinheiro());
        maoJogadorSplit.atualizarDinheiro(apiModel.getDinheiro());
        //distribuição de cartas
        distribuirCartaSplit();
        distribuir1Carta();
        //remoção da segunda carta da maojogador
        maoJogador.removeCarta();
        maoJogadorSplit.setVisible(true);
        maoJogador.repaint();
        maoJogadorSplit.repaint();

        split = true;
    }

    void fazSurrender() {
        maoJogador.atualizarDinheiro(apiModel.getDinheiro());
        maoJogador.repaint();

    }

    void exibeResultados(){
        List<Integer> resultados = apiModel.checkStatusPartida();
        String placar = "";
        for (int i = 0; i < resultados.size(); i++){
            if (resultados.get(i) == 1) {
                placar += "Mao " + (i+1) + " venceu    ";
            } else if (resultados.get(i) == -1) {
                placar +=  "Mao " + (i+1) + " perdeu    ";
            } else {
                placar += "Mao " + (i+1) + "Empate    ";
            }
        }
        mostrarMensagem(placar, "Resultado");
    }

    void fazDouble() {
    	
        if (apiModel.getTurnos() == 1) { // turno mao principal
            maoJogador.atualizarPontos(apiModel.calculaValorMaoAnterior());
            maoJogador.receberCarta(apiModel.getNaipeMaoAnterior());
            System.out.println("teste aposta jogador");
            maoJogador.atualizarDinheiro(apiModel.getDinheiro());
            maoJogador.atualizarAposta(apiModel.getApostaMaoAnterior());
            if (!apiModel.isSplit())
                apiModel.passaEstado();;

        }
        if (apiModel.isSplit() && apiModel.getTurnos() == 2) { // turno split
            maoJogadorSplit.atualizarPontos(apiModel.calculaValorMaoAnterior());
            maoJogadorSplit.receberCarta(apiModel.getNaipeMaoAnterior());
            System.out.println("teste aposta split");
            maoJogadorSplit.atualizarDinheiro(apiModel.getDinheiro());
            maoJogadorSplit.atualizarAposta(apiModel.getApostaMaoAnterior());
            maoJogador.atualizarDinheiro(apiModel.getDinheiro());
            apiModel.passaEstado();;

        }

        maoJogador.repaint();
        maoJogadorSplit.repaint();

    }

    public static void main(String[] args) {
        new Controller();
    }
}