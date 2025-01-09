package model;

import static org.junit.Assert.*;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MaoJogadorTest {

    private MaoJogador maoJogador;

    @Before
    public void setUp() {
        maoJogador = new MaoJogador();
    }

    @Test
    public void testApostaInicialZero() {
    	//testa a aposta inicial de zero
        assertEquals(0, maoJogador.getAposta());
    }

    @Test
    public void testDefinirAposta() {
    	//testa o definir a aposta
        maoJogador.setAposta(50);
        assertEquals(50, maoJogador.getAposta());
    }

    @Test
    public void testAumentarAposta() {
    	//testa o incremento da aposta
        maoJogador.aumentarAposta(30);
        assertEquals(30, maoJogador.getAposta());
        maoJogador.aumentarAposta(20);
        assertEquals(50, maoJogador.getAposta());
    }

}
