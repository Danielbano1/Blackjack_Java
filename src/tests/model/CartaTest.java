package model;

import static org.junit.Assert.*;
import model.*;
import org.junit.Test;

public class CartaTest {

    @Test
    public void testCriacaoCartaComValorNumerico() {
    	//cria carta e da um naipe e valor a ela
        Carta carta = new Carta("Espadas", 7);
        //testa naipe
        assertEquals("Espadas", carta.getNaipe());
        //testa valor
        assertEquals(7, carta.getValor());
    }

    @Test
    public void testCriacaoCartaComAs() {
    	//cria carta e da um naipe e valor a ela
        Carta carta = new Carta("a", "Copas");
        //testa naipe
        assertEquals("aCopas", carta.getNaipe());
        //testa valor
        assertEquals(1, carta.getValor());
    }

    @Test
    public void testCriacaoCartaComFigura() {
    	//cria carta e da um naipe e valor a ela
        Carta carta = new Carta("k", "Ouros");
        //testa naipe
        assertEquals("kOuros", carta.getNaipe());
        //testa valor
        assertEquals(10, carta.getValor());
    }

    @Test
    public void testCriacaoCartaComValor10() {
    	//cria carta e da um naipe e valor a ela
        Carta carta = new Carta("10", "Paus");
        //testa naipe
        assertEquals("10Paus", carta.getNaipe());
        //testa valor
        assertEquals(10, carta.getValor());
    }
}
