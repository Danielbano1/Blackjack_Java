package controller;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



import model.APImodel;
import view.JanelaBanca;

public class TratadorDeClicks {
    private Controller controller;
    private JanelaBanca janelaBanca;
    private APImodel apimodel;
    private Rectangle[] botoesBounds;
    private final String[] botoesLabels = { "EXIT", "DOUBLE", "SPLIT", "CLEAR", "DEAL", "HIT", "STAND", "SURRENDER" };
    private Rectangle[] fichaBounds;

    public TratadorDeClicks(Controller controller, JanelaBanca janelaBanca) {
        this.controller = controller;
        this.janelaBanca = janelaBanca;
       apimodel = controller.apiModel;

        configurarBotoes();
        fichaBounds = janelaBanca.getFichaBounds();
        tratadorDeClicks();

    }

    private void tratadorDeClicks() {
        janelaBanca.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if (apimodel.partidaEstadoAposta()) {
                    int aposta = 0;

                    if (botoesBounds[3].contains(p)) {
                        apimodel.devolveAposta();
                        System.out.println("Aposta devolvida"); 
                    }

                    else if (botoesBounds[4].contains(p)) {
                        if (apimodel.validaAposta()) {
                            System.out.println("Aposta validada");
                            apimodel.passaEstado();
                            controller.distribuirCartas();
                        } else
                            System.out.println("Aposta abaixo de 50");
                    }

                    for (int i = 0; i < fichaBounds.length; i++) {
                        if (fichaBounds[i] != null && fichaBounds[i].contains(p)) {
                            System.out.println("Ficha " + (i + 1) + " clicada!");
                            if (i == 0) {
                                if (e.getButton() == MouseEvent.BUTTON3 && apimodel.checkReducao(1))
                                    aposta = -1;
                                else if (e.getButton() == MouseEvent.BUTTON1)
                                    aposta = 1;
                            }

                            else if (i == 1) {
                                if (e.getButton() == MouseEvent.BUTTON3 && apimodel.checkReducao(5))
                                    aposta = -5;
                                else if (e.getButton() == MouseEvent.BUTTON1)
                                    aposta = 5;
                            } else if (i == 2) {
                                if (e.getButton() == MouseEvent.BUTTON3 && apimodel.checkReducao(10))
                                    aposta = -10;
                                else if (e.getButton() == MouseEvent.BUTTON1)
                                    aposta = 10;
                            } else if (i == 3) {
                                if (e.getButton() == MouseEvent.BUTTON3 && apimodel.checkReducao(20))
                                    aposta = -20;
                                else if (e.getButton() == MouseEvent.BUTTON1)
                                    aposta = 20;
                            } else if (i == 4) {
                                if (e.getButton() == MouseEvent.BUTTON3 && apimodel.checkReducao(50))
                                    aposta = -50;
                                else if (e.getButton() == MouseEvent.BUTTON1)
                                    aposta = 50;
                            } else if (i == 5) {
                                if (e.getButton() == MouseEvent.BUTTON3 && apimodel.checkReducao(100))
                                    aposta = -100;
                                else if (e.getButton() == MouseEvent.BUTTON1)
                                    aposta = 100;
                            }

                            if (apimodel.checkAposta(aposta) == false)
                                System.out.println("Dinheiro insuficiente");
                        }
                    }
                    controller.fazerApostas();
                    
                }

                if (apimodel.partidaEstadoJogo()) {
                    for (int i = 0; i < botoesBounds.length; i++) {
                        if (botoesBounds[i].contains(p)) {
                            System.out.println("Botão '" + botoesLabels[i] + "' clicado!");
                            if (i == 0) {
                                apimodel.salvarPartida();;
                                System.out.println("valido 0");

                            } else if (i == 1 ) {
                                if(apimodel.doDouble()) {
                                    controller.fazDouble();
                                }

                                System.out.println("valido 1");

                            } else if (i == 2) {
                                if (controller.split == false) {
                                    apimodel.split();
                                    if (apimodel.isSplit()) {
                                        controller.fazSplit();
                                    }
                                    System.out.println("Valido 2");
                                }
                            } else if (i == 5 ) {
                                if (!apimodel.checkEstouro()) {
                                    apimodel.hit();
                                    if(apimodel.getTurnos()==0)
                                        controller.distribuir1Carta();
                                    else if(apimodel.getTurnos()==1)
                                        controller.distribuir1CartaSplit();
                                    if (apimodel.checkPassaOuTermina(0)) {
                                    	apimodel.terminaTurno();
                                    	controller.mostrarMensagem("Mão Split", "Turno");
                                    }
                                        
                                    else if (apimodel.checkPassaOuTermina(1))
                                        apimodel.passaEstado();

                                }
                                else {
                                    apimodel.passaEstado();

                                }
                                System.out.println("valido 5");

                            } else if (i == 6) {

                                if (!apimodel.isSplit())
                                    apimodel.passaEstado();
                                
                                else if (apimodel.getTurnos() == 1) {
                                	apimodel.passaEstado();
                                } 
                                else
                                	controller.mostrarMensagem("Mão Split", "Turno");
                                                    
                                apimodel.terminaTurno();
                                

                            } else if (i == 7) {
                                if (apimodel.rendicao()) {
                                    controller.fazSurrender();
                                    apimodel.defineEstadoFim();
                                    System.out.println("valido 7");
                                }

                            }
                        }

                    }
                }
                if (apimodel.partidaEstadoDealer()) {
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
