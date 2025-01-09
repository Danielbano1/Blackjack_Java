package model;

import java.util.*;

public class MaoJogador extends Mao {

    private int aposta;

    public MaoJogador() {
        super();
        aposta = 0;

    }

    public MaoJogador(List<Carta> cartas, int aposta) {
        super(cartas);
        this.aposta = aposta;
    }

    public void aumentarAposta(int valor) {
        aposta += valor;
    }

    public void setAposta(int aposta) {
        this.aposta = aposta;
    }

    public int getAposta() {
        return aposta;
    }

}
