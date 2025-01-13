package model;

import java.util.*;

 class MaoJogador extends Mao {

    private int aposta;

    MaoJogador() {
    	
        super();
        aposta = 0;

    }

    MaoJogador(List<Carta> cartas, int aposta, ObservadorIF o) {
        super(cartas, o);
        setAposta(aposta);
    }

    void aumentarAposta(int valor) {
        aposta += valor;
        for(ObservadorIF o : observadores) {
        	o.notificaAposta(aposta);
        }
    }

    void setAposta(int aposta) {
        this.aposta = aposta;
        for(ObservadorIF o : observadores) {
        	o.notificaAposta(aposta);
        }
    }

    int getAposta() {
        return aposta;
    }

}
