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
    	//cria carta
        Carta carta = new Carta("Espadas", 10);
        //adiciona carta a mao
        mao.addCarta(carta);
        //testa tamanho da mao
        assertEquals(1, mao.getLista_cartas().size());
        //testa valor da mao
        assertEquals(10, mao.calculaValorMao());
    }

    @Test
    public void testCalculoValorMaoSemAs() {
    	//teste do calculo do valor sem um "As" na mao
        mao.addCarta(new Carta("Copas", 5));
        mao.addCarta(new Carta("Ouros", 8));
        //testa valor da mao
        assertEquals(13, mao.calculaValorMao());
    }

    @Test
    public void testCalculoValorMaoComAs() {
    	//teste o valor de calculo com um "A"
        mao.addCarta(new Carta("Espadas", 1));
        mao.addCarta(new Carta("Paus", 7));
        assertEquals(18, mao.calculaValorMao());
    }

    @Test
    public void testCalculoValorMaoComDoisAs() {
    	//teste o valor de calculo com dois "A"
        mao.addCarta(new Carta("Espadas", 1));
        mao.addCarta(new Carta("Copas", 1));
        mao.addCarta(new Carta("Ouros", 9));
        assertEquals(21, mao.calculaValorMao());
    }

    @Test
    public void testEstourouComMaisDe21() {
    	//testa o calculo para estouro
        mao.addCarta(new Carta("Espadas", 10));
        mao.addCarta(new Carta("Copas", 5));
        mao.addCarta(new Carta("Ouros", 7));
        assertTrue(mao.estourou());
    }

    @Test
    public void testNaoEstourouCom21OuMenos() {
    	//testa para noa estouro
        mao.addCarta(new Carta("Espadas", 10));
        mao.addCarta(new Carta("Copas", 5));
        assertFalse(mao.estourou());
    }

    @Test
    public void testLimpaMao() {
    	//testa a quantidade de cartas na mao após limpar a mesma
        mao.addCarta(new Carta("Espadas", 10));
        mao.addCarta(new Carta("Copas", 5));
        mao.limpaMao();
        assertEquals(0, mao.getLista_cartas().size());
    }
}
