package model;

 class Carta {
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

    Carta (String naipe, int valor){
        this.naipe = naipe;
        this.valor = valor;
    }

    String getNaipe() {
        return naipe;
    }

    int getValor() {
        return valor;
    }

}
