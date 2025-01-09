package model;

import static org.junit.Assert.*;
import model.*;
import org.junit.Test;

public class CartaTest {

    @Test
    public void testCriacaoCartaComValorNumerico() {
        Carta carta = new Carta("Espadas", 7);
        assertEquals("Espadas", carta.getNaipe());
        assertEquals(7, carta.getValor());
    }

    @Test
    public void testCriacaoCartaComAs() {
        Carta carta = new Carta("a", "Copas");
        assertEquals("aCopas", carta.getNaipe());
        assertEquals(1, carta.getValor());
    }

    @Test
    public void testCriacaoCartaComFigura() {
        Carta carta = new Carta("k", "Ouros");
        assertEquals("kOuros", carta.getNaipe());
        assertEquals(10, carta.getValor());
    }

    @Test
    public void testCriacaoCartaComValor10() {
        Carta carta = new Carta("10", "Paus");
        assertEquals("10Paus", carta.getNaipe());
        assertEquals(10, carta.getValor());
    }
}
