package model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

import java.util.List;

import org.junit.Before;

public class BaralhoTest {
	// Cria um baralho 
	private Baralho baralho = Baralho.getInstancia();
	
	@Test
	public void testConstrutorInicializaCorretamente() {
		 // Verifica se o número de cartas no baralho é o esperado.
		assertEquals(baralho.getQtdCartas(), baralho.getCartas().size());
		
	}
	
	@Test
	public void testEmbaralhar() {
		// Salva o estado do baralho antes de embaralhar
		List<Carta> cartasAntesEmbaralhar = new ArrayList<>(baralho.getCartas());
		

		// Embaralha as cartas
		baralho.embaralhar();
		

		// Verifica que as cartas foram embaralhadas (ou seja, a ordem mudou)
		assertNotEquals(cartasAntesEmbaralhar, baralho.getCartas());

		// Verifica se o contador de cartas usadas foi reiniciado
		assertEquals(0, baralho.getCartasUsadas());
	}
	
	@Test
	public void testDarCarta() {
		// Tira a primeira carta do baralho
		Carta primeiraCarta = baralho.tirarCarta();
		

		// Verifica se a carta foi distribuída e não é nula
		assertNotNull(primeiraCarta);
		

		// Verifica se o número de cartas usadas aumentou
		assertEquals(1, baralho.getCartasUsadas());
	}

	@Test
	public void testEmbaralharAposLimiteUsado() {
		// Distribui o número de cartas até atingir o limite de embaralhamento (10% do
		// total)
		Carta carta;
		for(int i = baralho.getCartasUsadas(); i < baralho.getLimiteEmbaralhar(); i++) {
			carta = baralho.tirarCarta();
		}

		// O embaralhamento deve ter sido acionado e o contador deve ter sido reiniciado
		assertEquals(0, baralho.getCartasUsadas());
	}

}
