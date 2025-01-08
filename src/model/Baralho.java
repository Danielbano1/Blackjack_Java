package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

 class Baralho {
    private static Baralho instancia; // Instância única da classe

    private List<Carta> cartas;
    private int cartasUsadas;
    private int qtdDecks = 8;
    private int qtdCartas = qtdDecks * 52;
    private int limiteEmbaralhar = (int) (qtdCartas * 0.1);

    // Construtor padrão (privado)
    private Baralho() {
        this.cartas = new ArrayList<>();
        String[] naipes = { "c", "d", "h", "s" };
        String[] valores = { "a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k" };

        for (int i = 0; i < qtdDecks; i++) {
            for (String naipe : naipes) {
                for (String valor : valores) {
                    cartas.add(new Carta(valor, naipe));
                }
            }
        }
        cartasUsadas = 0;
    }

    // Construtor com lista de cartas (privado)
    private Baralho(List<Carta> cartas) {
        this.cartas = cartas;
        this.cartasUsadas = 0;
    }

    // Método estático para obter a instância (com inicialização opcional)
    static Baralho getInstancia() {
        if (instancia == null) {
            instancia = new Baralho(); // Usa o construtor padrão por padrão
        }
        return instancia;
    }

    // Sobrecarga para criar a instância com uma lista de cartas
    static Baralho getInstancia(List<Carta> cartas) {
        if (instancia == null) {
            instancia = new Baralho(cartas); // Usa o construtor com a lista de cartas
        }
        return instancia;
    }

    void embaralhar() {
        Collections.shuffle(cartas);
        cartasUsadas = 0;
    }

    Carta tirarCarta() {
        cartasUsadas++;
        Carta carta = cartas.remove(cartas.size() - 1);
        cartas.addFirst(carta);
        confereLimite();
        return carta;
    }

    List<Carta> getCartas() {
        return cartas;
    }

    int getCartasUsadas() {
        return cartasUsadas;
    }

    void setCartasUsadas(int cartasUsadas) {
        this.cartasUsadas = cartasUsadas;
    }

    private void confereLimite() {
        if (cartasUsadas >= limiteEmbaralhar) {
            embaralhar();
        }
    }

    int getQtdDecks() {
        return qtdDecks;
    }

    int getQtdCartas() {
        return qtdCartas;
    }

    int getLimiteEmbaralhar() {
        return limiteEmbaralhar;
    }
}