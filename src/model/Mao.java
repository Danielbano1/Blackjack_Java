package model;

import java.util.*;

public class Mao {

    private ArrayList<Carta> lista_cartas;

    public Mao() {	//construtor
        this.lista_cartas = new ArrayList<>();  // cria uma lista para as cartas na mao						//mao nao passou e não é 21 em valor ainda
    }

    public Mao(List<Carta> cartas){
        this.lista_cartas = (ArrayList<Carta>) cartas;
    }
    public void addCarta(Carta carta) {
        lista_cartas.add(carta);
    }

    public int calculaValorMao() {
        int total = 0;
        int numAs =0 ;
        for(Carta carta : lista_cartas) {
            total += carta.getValor();
            if(carta.getValor()==1)
                numAs++;
        }
        //logica para o valor de As
        while (numAs > 0 && total+10 <=21) {
            total+=10;
            numAs--;
        }
        return total;
    }

    public void limpaMao() {
        lista_cartas.clear();
    }

    public ArrayList<Carta> getLista_cartas() {
        return lista_cartas;
    }

    public boolean estourou() {
        return this.calculaValorMao() >= 21;
    }


}
