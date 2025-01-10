package controller;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Estado;
import model.Partida;
import view.JanelaBanca;

public class TratadorDeClicks {
    Controller controller;
    JanelaBanca janelaBanca;
    Partida partida;
    private Rectangle[] botoesBounds;
    private final String[] botoesLabels = { "EXIT", "DOUBLE", "SPLIT", "CLEAR", "DEAL", "HIT", "STAND", "SURRENDER" };
    private Rectangle[] fichaBounds;

    public TratadorDeClicks(Controller controller, JanelaBanca janelaBanca) {
        this.controller = controller;
        this.janelaBanca = janelaBanca;
        partida = controller.partida;

        configurarBotoes();
        fichaBounds = janelaBanca.getFichaBounds();
        tratadorDeClicks();

    }

    private void tratadorDeClicks() {
        janelaBanca.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if (partida.gerenciadorDeEstados.getEstadoAtual() == Estado.APOSTA) {
                    int aposta = 0;

                    if (botoesBounds[3].contains(p)) {
                        partida.devolveAposta();
                        System.out.println("Aposta devolvida"); 
                    }

                    else if (botoesBounds[4].contains(p)) {
                        if (partida.validaAposta()) {
                            System.out.println("Aposta validada");
                            partida.gerenciadorDeEstados.proxEstado();
                            controller.distribuirCartas();
                        } else
                            System.out.println("Aposta abaixo de 50");
                    }

                    for (int i = 0; i < fichaBounds.length; i++) {
                        if (fichaBounds[i] != null && fichaBounds[i].contains(p)) {
                            System.out.println("Ficha " + (i + 1) + " clicada!");
                            if (i == 0) {
                                if (e.getButton() == MouseEvent.BUTTON3  && (partida.getMaoJogador().getAposta()-1) >= 0)
                                    aposta = -1;
                                else if (e.getButton() == MouseEvent.BUTTON1)
                                    aposta = 1;
                            }

                            else if (i == 1) {
                                if (e.getButton() == MouseEvent.BUTTON3 && (partida.getMaoJogador().getAposta()-5) >= 0)
                                    aposta = -5;
                                else if (e.getButton() == MouseEvent.BUTTON1)
                                    aposta = 5;
                            } else if (i == 2) {
                                if (e.getButton() == MouseEvent.BUTTON3 && (partida.getMaoJogador().getAposta()-10) >= 0)
                                    aposta = -10;
                                else if (e.getButton() == MouseEvent.BUTTON1)
                                    aposta = 10;
                            } else if (i == 3) {
                                if (e.getButton() == MouseEvent.BUTTON3 && (partida.getMaoJogador().getAposta()-20) >= 0)
                                    aposta = -20;
                                else if (e.getButton() == MouseEvent.BUTTON1)
                                    aposta = 20;
                            } else if (i == 4) {
                                if (e.getButton() == MouseEvent.BUTTON3 && (partida.getMaoJogador().getAposta()-50) >= 0)
                                    aposta = -50;
                                else if (e.getButton() == MouseEvent.BUTTON1)
                                    aposta = 50;
                            } else if (i == 5) {
                                if (e.getButton() == MouseEvent.BUTTON3 && (partida.getMaoJogador().getAposta()-100) >= 0)
                                    aposta = -100;
                                else if (e.getButton() == MouseEvent.BUTTON1)
                                    aposta = 100;
                            }

                            if (partida.checkAposta(aposta) == false)
                                System.out.println("Dinheiro insuficiente");
                        }
                    }
                    controller.fazerApostas();
                }

                if (partida.gerenciadorDeEstados.getEstadoAtual() == Estado.JOGO) {
                    for (int i = 0; i < botoesBounds.length; i++) {
                        if (botoesBounds[i].contains(p)) {
                            System.out.println("Botão '" + botoesLabels[i] + "' clicado!");
                            if (i == 0) {
                                SalvarPartida.salvarPartida(partida);
                                System.out.println("valido 0");

                            } else if (i == 1 ) {
                                if(partida.doDouble()) {
                                    controller.fazDouble();
                                }

                                System.out.println("valido 1");

                            } else if (i == 2) {
                                if (controller.split == false) {
                                    partida.split();
                                    if (partida.isSplit()) {
                                        controller.fazSplit();
                                    }
                                    System.out.println("Valido 2");
                                }
                            } else if (i == 5 ) {
                                if (!partida.checkEstouro()) {
                                    partida.hit();
                                    if(partida.getTurnos()==0)
                                        controller.distribuir1Carta();
                                    else if(partida.getTurnos()==1)
                                        controller.distribuir1CartaSplit();
                                    if (partida.checkEstouro() && partida.getTurnos()==0)
                                        partida.terminaTurno();
                                    else if (partida.checkEstouro() && partida.getTurnos()==1)
                                        partida.gerenciadorDeEstados.proxEstado();

                                }
                                else {
                                    partida.gerenciadorDeEstados.proxEstado();

                                }
                                System.out.println("valido 5");

                            } else if (i == 6) {

                                if (!partida.isSplit())
                                    partida.gerenciadorDeEstados.proxEstado();
                                else if (partida.getTurnos() == 1)
                                    partida.gerenciadorDeEstados.proxEstado();

                                partida.terminaTurno();

                            } else if (i == 7) {
                                if (partida.rendicao()) {
                                    controller.fazSurrender();
                                    partida.gerenciadorDeEstados.setEstadoAtual(Estado.FIM);
                                    System.out.println("valido 7");
                                }

                            }
                        }

                    }
                }
                if (partida.gerenciadorDeEstados.getEstadoAtual() == Estado.DEALER) {
                    controller.jogaDealer();
                }

            }
        });

    }


    private void configurarBotoes() {
        botoesBounds = new Rectangle[] { new Rectangle(25, 600, 140, 50), // EXIT
                new Rectangle(220, 700, 140, 50), // DOUBLE
                new Rectangle(360, 700, 140, 50), // SPLIT
                new Rectangle(510, 700, 140, 50), // CLEAR
                new Rectangle(660, 700, 140, 50), // DEAL
                new Rectangle(840, 600, 140, 50), // HIT
                new Rectangle(840, 650, 140, 50), // STAND
                new Rectangle(840, 700, 140, 50) // SURRENDER
        };
    }
}
