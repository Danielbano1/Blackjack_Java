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
                    iniciarJogo(apiModel.carregarPartida(2));
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
        janelaBanca = new JanelaBanca();
        configurarJanelas();
        split = (boolean) lista.get(2);
        maoJogadorSplit.setVisible(split);
        apiModel = new APImodel(lista, maoDealer, maoJogador, maoJogadorSplit);
        tratador = new TratadorDeClicks(this, janelaBanca);
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
    
    void distribuirCartas() {
        // Distribui as cartas
        apiModel.distribuiCarta();
        
    }

    void jogaDealer() {
        apiModel.jogaDealer();
     
        exibeResultados();
    }
    

    void fazSplit() { 
    	maoJogadorSplit.setVisible(true);
        split = true;
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
    
    JanelaMaoJogador getMaoJogadorSplit() {
    	return maoJogadorSplit;
    }
   

    public static void main(String[] args) {
        new Controller();
    }
}