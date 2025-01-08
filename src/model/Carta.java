package model;

public class Carta {
    private String naipe;
    private int valor;

    Carta (String valor, String naipe){
        if("tjqk".contains(valor))
            this.valor = 10;

        else if(valor.equals("a"))
            this.valor = 1;

        else
            this.valor = Integer.parseInt(valor);



        this.naipe = valor + naipe;
    }

    public Carta (String naipe, int valor){
        this.naipe = naipe;
        this.valor = valor;
    }

    public String getNaipe() {
        return naipe;
    }

    public int getValor() {
        return valor;
    }


}
