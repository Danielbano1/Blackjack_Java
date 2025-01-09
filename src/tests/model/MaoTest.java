package model;

import static org.junit.Assert.*;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class MaoTest {

    private Mao mao;

    @Before
    public void setUp() {
        mao = new Mao();
    }

    @Test
    public void testAdicionarCarta() {
        Carta carta = new Carta("Espadas", 10);
        mao.addCarta(carta);
        assertEquals(1, mao.getLista_cartas().size());
        assertEquals(10, mao.calculaValorMao());
    }

    @Test
    public void testCalculoValorMaoSemAs() {
        mao.addCarta(new Carta("Copas", 5));
        mao.addCarta(new Carta("Ouros", 8));
        assertEquals(13, mao.calculaValorMao());
    }

    @Test
    public void testCalculoValorMaoComAs() {
        mao.addCarta(new Carta("Espadas", 1));
        mao.addCarta(new Carta("Paus", 7));
        assertEquals(18, mao.calculaValorMao());
    }

    @Test
    public void testCalculoValorMaoComDoisAs() {
        mao.addCarta(new Carta("Espadas", 1));
        mao.addCarta(new Carta("Copas", 1));
        mao.addCarta(new Carta("Ouros", 9));
        assertEquals(21, mao.calculaValorMao());
    }

    @Test
    public void testEstourouComMaisDe21() {
        mao.addCarta(new Carta("Espadas", 10));
        mao.addCarta(new Carta("Copas", 5));
        mao.addCarta(new Carta("Ouros", 7));
        assertTrue(mao.estourou());
    }

    @Test
    public void testNaoEstourouCom21OuMenos() {
        mao.addCarta(new Carta("Espadas", 10));
        mao.addCarta(new Carta("Copas", 5));
        assertFalse(mao.estourou());
    }

    @Test
    public void testLimpaMao() {
        mao.addCarta(new Carta("Espadas", 10));
        mao.addCarta(new Carta("Copas", 5));
        mao.limpaMao();
        assertEquals(0, mao.getLista_cartas().size());
    }
}
