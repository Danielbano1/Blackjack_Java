package controller;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import model.GerenciadorDeEstados;
import model.Partida;
import view.*;

public class Controller {
	
    public Partida partida;
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
                    iniciarJogo(SalvarPartida.carregarPartida(1));
                    System.out.println("Botão 'Continuar Jogo' clicado");
                }
            }
        });
    }

    private void iniciarJogo() {
        janelaInicial.dispose();
        partida = new Partida();
        janelaBanca = new JanelaBanca();
        configurarJanelas();
        tratador = new TratadorDeClicks(this, janelaBanca);
        split = false;

    }
    private void iniciarJogo( List<Object> lista) {
        janelaInicial.dispose();
        partida = new Partida(lista);
        janelaBanca = new JanelaBanca();
        configurarJanelas();
        tratador = new TratadorDeClicks(this, janelaBanca);
        split = (boolean) lista.get(2);
        maoJogadorSplit.setVisible(split);
        distribuirCartasSalvas();

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
        maoJogador.atualizarDinheiro(partida.getDinheiro());
        maoJogador.atualizarAposta(partida.getApostaMaoAtual());
        maoJogador.repaint();

    }

    void distribuirCartasSalvas(){
        // Configurações para o Dealer
        ArrayList<String> naipes = partida.getNaipesDealer();
        maoDealer.atualizarPontos(partida.calculaValorMaoDealer());

        for (String naipe : naipes){
            maoDealer.receberCarta(naipe);
        }
        // Configurações para o Jogador Principal
        naipes = partida.getNaipesPrincipal();
        maoJogador.atualizarDinheiro(partida.getDinheiro());
        maoJogador.atualizarPontos(partida.calculaValorMaoPrincipal());
        maoJogador.atualizarAposta(partida.getApostaMaoPrincipal());

        for (String naipe : naipes){
            maoJogador.receberCarta(naipe);
        }

        if(partida.isSplit()) {
            // Configurações para o Jogador Split
            naipes = partida.getNaipesSplit();
            maoJogadorSplit.atualizarDinheiro(partida.getDinheiro());
            maoJogadorSplit.atualizarPontos(partida.calculaValorMaoSplit());
            maoJogadorSplit.atualizarAposta(partida.getApostaMaoSplit());


            for (String naipe : naipes){
                maoJogadorSplit.receberCarta(naipe);
            }
        }
    }

    void distribuirCartas() {
        // Distribui as cartas
        partida.distribuiCarta();
        partida.getMaoJogador().getLista_cartas();
        // Configurações para o Dealer
        maoDealer.atualizarPontos(partida.calculaValorMaoDealer());
        maoDealer.receberCarta(partida.getPrimeiroNaipeDealer());
        maoDealer.receberCarta(partida.getUltimoNaipeDealer());

        // Configurações para o Jogador Principal
        maoJogador.atualizarPontos(partida.calculaValorMaoPrincipal());
        maoJogador.receberCarta(partida.getPrimeiroNaipePrincipal());
        maoJogador.receberCarta(partida.getUltimoNaipePrincipal());

    }

    void distribuir1Carta() {
        // Configurações para o Jogador Principal
        maoJogador.atualizarPontos(partida.calculaValorMaoPrincipal());
        maoJogador.receberCarta(partida.getUltimoNaipePrincipal());

    }
    void distribuirCartaSplit() {
        // Configurações para o Split Principal
        maoJogadorSplit.atualizarPontos(partida.calculaValorMaoSplit());
        maoJogadorSplit.receberCarta(partida.getPrimeiroNaipeSplit());
        maoJogadorSplit.receberCarta(partida.getUltimoNaipeSplit());

    }

    void distribuir1CartaSplit() {
        // Configurações para o split Principal
        maoJogadorSplit.atualizarPontos(partida.calculaValorMaoSplit());
        maoJogadorSplit.receberCarta(partida.getUltimoNaipeSplit());

    }


    void jogaDealer() {
        partida.jogaDealer();
        // Configurações para o Dealer
        ArrayList<String> naipes = partida.retornaListaCartas();
        maoDealer.atualizarPontos(partida.calculaValorMaoDealer());

        for (int i = naipes.size(); i > 2; i--)
            maoDealer.receberCarta(naipes.get(i - 1));

        partida.gerenciadorDeEstados.proxEstado();
        exibeResultados();
        maoJogador.atualizarDinheiro(partida.getDinheiro());
        maoJogadorSplit.atualizarDinheiro(partida.getDinheiro());
    }

    void fazSplit() {
        //atualização de dinheiro e aposta
        maoJogadorSplit.atualizarAposta(partida.getApostaMaoAtual());
        maoJogador.atualizarDinheiro(partida.getDinheiro());
        maoJogadorSplit.atualizarDinheiro(partida.getDinheiro());
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
        maoJogador.atualizarDinheiro(partida.getDinheiro());
        maoJogador.repaint();

    }

    void exibeResultados(){
        List<Integer> resultados = partida.checkStatusPartida();
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
        //janelaBanca.exibePlacar(placar);
    }

    void fazDouble() {
    	
        if (partida.getTurnos() == 1) { // turno mao principal
            maoJogador.atualizarPontos(partida.calculaValorMaoAnterior());
            maoJogador.receberCarta(partida.getNaipeMaoAnterior());
            System.out.println("teste aposta jogador");
            maoJogador.atualizarDinheiro(partida.getDinheiro());
            maoJogador.atualizarAposta(partida.getApostaMaoAnterior());
            if (!partida.isSplit())
                partida.gerenciadorDeEstados.proxEstado();

        }
        if (partida.isSplit() && partida.getTurnos() == 2) { // turno split
            maoJogadorSplit.atualizarPontos(partida.calculaValorMaoAnterior());
            maoJogadorSplit.receberCarta(partida.getNaipeMaoAnterior());
            System.out.println("teste aposta split");
            maoJogadorSplit.atualizarDinheiro(partida.getDinheiro());
            maoJogadorSplit.atualizarAposta(partida.getApostaMaoAnterior());
            maoJogador.atualizarDinheiro(partida.getDinheiro());
            partida.gerenciadorDeEstados.proxEstado();

        }

        maoJogador.repaint();
        maoJogadorSplit.repaint();

    }

    public static void main(String[] args) {
        new Controller();
    }
}