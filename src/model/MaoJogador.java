package model;

import java.util.*;

 class MaoJogador extends Mao {

    private int aposta;

    MaoJogador() {
        super();
        aposta = 0;

    }

    MaoJogador(List<Carta> cartas, int aposta) {
        super(cartas);
        this.aposta = aposta;
    }

    void aumentarAposta(int valor) {
        aposta += valor;
    }

    void setAposta(int aposta) {
        this.aposta = aposta;
    }

    int getAposta() {
        return aposta;
    }

}
