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
                if (janelaInicial.getBotaoNovoJogoBounds().contains(p)) {
                    System.out.println("Botão 'Novo Jogo' clicado");
                    iniciarJogo();
                } else if (janelaInicial.getBotaoContinuarBounds().contains(p)) {
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
        maoJogador.atualizarAposta(partida.getMaoJogador().getAposta());
        maoJogador.repaint();

    }

    void distribuirCartasSalvas(){
        // Configurações para o Dealer
        ArrayList<String> naipes = partida.retornaListaCartas(partida.getMaoDealer());
        maoDealer.atualizarPontos(partida.getMaos().getLast().calculaValorMao());

        for (String naipe : naipes){
            maoDealer.receberCarta(naipe);
        }
        // Configurações para o Jogador Principal
        naipes = partida.retornaListaCartas(partida.getMaoPrincipal());
        maoJogador.atualizarDinheiro(partida.getDinheiro());
        maoJogador.atualizarPontos(partida.getMaoPrincipal().calculaValorMao());
        maoJogador.atualizarAposta(partida.getMaoPrincipal().getAposta());

        for (String naipe : naipes){
            maoJogador.receberCarta(naipe);
        }

        if(partida.isSplit()) {
            // Configurações para o Jogador Split
            naipes = partida.retornaListaCartas(partida.getMaoSplit());
            maoJogadorSplit.atualizarDinheiro(partida.getDinheiro());
            maoJogadorSplit.atualizarPontos(partida.getMaoSplit().calculaValorMao());
            maoJogadorSplit.atualizarAposta(partida.getMaoSplit().getAposta());


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
        maoDealer.atualizarPontos(partida.getMaos().getLast().calculaValorMao());
        maoDealer.receberCarta(partida.getMaos().getLast().getLista_cartas().getFirst().getNaipe());
        maoDealer.receberCarta(partida.getMaos().getLast().getLista_cartas().getLast().getNaipe());

        // Configurações para o Jogador Principal
        maoJogador.atualizarPontos(partida.getMaoJogador().calculaValorMao());
        maoJogador.receberCarta(partida.getMaoJogador().getLista_cartas().getFirst().getNaipe());
        maoJogador.receberCarta(partida.getMaoJogador().getLista_cartas().getLast().getNaipe());

    }

    void distribuir1Carta() {
        // Configurações para o Jogador Principal
        maoJogador.atualizarPontos(partida.getMaoJogador().calculaValorMao());
        maoJogador.receberCarta(partida.getMaoJogador().getLista_cartas().getLast().getNaipe());

    }
    void distribuirCartaSplit() {
        // Configurações para o Split Principal
        maoJogadorSplit.atualizarPontos(partida.getMaos().get(1).calculaValorMao());
        maoJogadorSplit.receberCarta(partida.getMaos().get(1).getLista_cartas().getFirst().getNaipe());
        maoJogadorSplit.receberCarta(partida.getMaos().get(1).getLista_cartas().getLast().getNaipe());

    }

    void distribuir1CartaSplit() {
        // Configurações para o split Principal
        maoJogadorSplit.atualizarPontos(partida.getMaos().get(1).calculaValorMao());
        maoJogadorSplit.receberCarta(partida.getMaos().get(1).getLista_cartas().getLast().getNaipe());

    }


    void jogaDealer() {
        partida.jogaDealer();
        // Configurações para o Dealer
        ArrayList<String> naipes = partida.retornaListaCartas();
        maoDealer.atualizarPontos(partida.getMaos().getLast().calculaValorMao());

        for (int i = naipes.size(); i > 2; i--)
            maoDealer.receberCarta(naipes.get(i - 1));

        partida.gerenciadorDeEstados.proxEstado();
        exibeResultados();
        maoJogador.atualizarDinheiro(partida.getDinheiro());
        maoJogadorSplit.atualizarDinheiro(partida.getDinheiro());
    }

    void fazSplit() {
        //atualização de dinheiro e aposta
        maoJogadorSplit.atualizarAposta(partida.getMaoJogador().getAposta());
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
        janelaBanca.exibePlacar(placar);
    }

    void fazDouble() {

        if (partida.getTurnos() == 1) { // turno mao principal
            maoJogador.atualizarPontos(partida.getMaoAnterior().calculaValorMao());
            maoJogador.receberCarta(partida.getMaoAnterior().getLista_cartas().getLast().getNaipe());
            System.out.println("teste aposta jogador");
            maoJogador.atualizarDinheiro(partida.getDinheiro());
            maoJogador.atualizarAposta(partida.getMaoAnterior().getAposta());
            if (!partida.isSplit())
                partida.gerenciadorDeEstados.proxEstado();

        }
        if (partida.isSplit() && partida.getTurnos() == 2) { // turno split
            maoJogadorSplit.atualizarPontos(partida.getMaoAnterior().calculaValorMao());
            maoJogadorSplit.receberCarta(partida.getMaoAnterior().getLista_cartas().getLast().getNaipe());
            System.out.println("teste aposta split");
            maoJogadorSplit.atualizarDinheiro(partida.getDinheiro());
            maoJogadorSplit.atualizarAposta(partida.getMaoAnterior().getAposta());
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