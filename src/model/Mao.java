package model;

import java.util.*;

 class Mao implements ObservadoIF {

    private ArrayList<Carta> lista_cartas;
    protected List<ObservadorIF> observadores = new ArrayList<ObservadorIF>();

    Mao() {	//construtor
        this.lista_cartas = new ArrayList<>();  // cria uma lista para as cartas na mao						
    }

    Mao(List<Carta> cartas){
        this.lista_cartas = (ArrayList<Carta>) cartas;
    }
    void addCarta(Carta carta) {
        lista_cartas.add(carta);
        for(ObservadorIF o : observadores) {
        	o.notificaAddCarta(carta.getNaipe());
        	o.notificaPontos(calculaValorMao());
        }
    }

    int calculaValorMao() {
        int total = 0;
        int numAs = 0;
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
    

    void limpaMao() {
        lista_cartas.clear();
    }

    ArrayList<Carta> getLista_cartas() {
        return lista_cartas;
    }

    boolean estourou() {
        return this.calculaValorMao() >= 21;
    }
    
    // Observer
    @Override
    public void add(ObservadorIF o){
        observadores.add(o);
    }
    
    @Override
    public void remove(ObservadorIF o){
        observadores.remove(o);
    }


}
