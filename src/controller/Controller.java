package controller;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

import model.APImodel;
import model.ObservadorIF;
import model.SalvarPartida;
import view.*;

public class Controller {
	
    public APImodel apiModel;
    private JanelaInicial janelaInicial;
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
        apiModel.addObsever(maoDealer, maoJogador);
        tratador = new TratadorDeClicks(this, janelaBanca);
        split = false;

    }
    private void iniciarJogo( List<Object> lista) {
        janelaInicial.dispose();
        apiModel = new APImodel(lista);
        janelaBanca = new JanelaBanca();
        configurarJanelas();
        apiModel.addObsever(maoDealer, maoJogador);
        tratador = new TratadorDeClicks(this, janelaBanca);
        split = (boolean) lista.get(2);
        maoJogadorSplit.setVisible(split);
        if(split == true)
        	apiModel.addObserverSplit(maoJogadorSplit);
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
        maoJogador.atualizarDinheiro(apiModel.getDinheiro());
        maoJogador.atualizarAposta(apiModel.getApostaMaoAtual());
        

    }
    
    void distribuirCartasSalvas() {
        // Configurações para o Jogador Principal
        maoJogador.atualizarDinheiro(apiModel.getDinheiro());
        maoJogador.atualizarAposta(apiModel.getApostaMaoPrincipal());

        if(apiModel.isSplit()) {
            // Configurações para o Jogador Split
            maoJogadorSplit.atualizarDinheiro(apiModel.getDinheiro());
            maoJogadorSplit.atualizarAposta(apiModel.getApostaMaoSplit());
        }
    }

    void distribuirCartas() {
        // Distribui as cartas
        apiModel.distribuiCarta();
        
    }

    void jogaDealer() {
        apiModel.jogaDealer();
     
        exibeResultados();
        maoJogador.atualizarDinheiro(apiModel.getDinheiro());
        maoJogadorSplit.atualizarDinheiro(apiModel.getDinheiro());
    }

    void fazSplit() {
        //atualização de dinheiro e aposta
        maoJogadorSplit.atualizarAposta(apiModel.getApostaMaoAtual());
        maoJogador.atualizarDinheiro(apiModel.getDinheiro());
        maoJogadorSplit.atualizarDinheiro(apiModel.getDinheiro());
        //remoção da segunda carta da maojogador
        maoJogador.removeCarta();
        maoJogadorSplit.setVisible(true);
        

        split = true;
        apiModel.addObserverSplit(maoJogadorSplit);
    }

    void fazSurrender() {
        maoJogador.atualizarDinheiro(apiModel.getDinheiro());
        

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
        //janelaBanca.exibePlacar(placar);
    }

    void fazDouble() {
    	
        if (apiModel.getTurnos() == 1) { // turno mao principal
            maoJogador.atualizarDinheiro(apiModel.getDinheiro());
            maoJogador.atualizarAposta(apiModel.getApostaMaoAnterior());
            if (!apiModel.isSplit())
                apiModel.passaEstado();;

        }
        if (apiModel.isSplit() && apiModel.getTurnos() == 2) { // turno split
            maoJogadorSplit.atualizarDinheiro(apiModel.getDinheiro());
            maoJogadorSplit.atualizarAposta(apiModel.getApostaMaoAnterior());
            maoJogador.atualizarDinheiro(apiModel.getDinheiro());
            apiModel.passaEstado();;

        }

        

    }

    public static void main(String[] args) {
        new Controller();
    }
}