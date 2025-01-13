package model;

import java.util.ArrayList;
import java.util.List;

class Partida {
	Dinheiro dinheiro;
	private boolean split;
	private List<Mao> maos = new ArrayList<Mao>();
	private int turnos;
	private Baralho baralho;
	public GerenciadorDeEstados gerenciadorDeEstados;

	public Partida() { // construtor
		split = false;
		dinheiro = new Dinheiro();
		maos.addFirst(new Mao()); /* adiciona mao do dealer */
		maos.addFirst(new MaoJogador()); /* adiciona mao do Jogador */
		turnos = 0;
		baralho = Baralho.getInstancia();
		baralho.embaralhar();
		gerenciadorDeEstados = new GerenciadorDeEstados();

	}

	public Partida(List<Object> infos, ObservadorIF d, ObservadorIF p, ObservadorIF s) { // construtor
		baralho = Baralho.getInstancia((List<Carta>) infos.get(0));
		baralho.setCartasUsadas((int) infos.get(1));
		split = (boolean) infos.get(2);
		dinheiro = new Dinheiro((int) infos.get(3));
		turnos = (int) infos.get(4);
		maos.addFirst(new Mao((List<Carta>) infos.get(5), d)); /* adiciona mao do dealer */
		if (split) {
			maos.addFirst(
					new MaoJogador((List<Carta>) infos.get(9), (int) infos.get(8), s)); /* adiciona mao do Jogador2 */
					dinheiro.add(s);
		}
		maos.addFirst(new MaoJogador((List<Carta>) infos.get(7), (int) infos.get(6), p)); /* adiciona mao do Jogador1 */
		dinheiro.add(p);

		gerenciadorDeEstados = new GerenciadorDeEstados();
		gerenciadorDeEstados.setEstadoAtual(Estado.JOGO);

	}

	public void hit() {
		Mao maoAtual = maos.get(turnos);
		if (turnos < maos.size() && !checkEstouro()) {
			Carta carta = baralho.tirarCarta();
			maoAtual.addCarta(carta);
		} else if (maoAtual.calculaValorMao() == 21)
			terminaTurno();
	}

	public void hit(Mao mao) {
		if (turnos < maos.size()) {
			Carta carta = baralho.tirarCarta();
			mao.addCarta(carta);
		} else if (mao.calculaValorMao() == 21)
			terminaTurno();
	}

	public void split(ObservadorIF o) {
		Mao maoAtual = maos.get(turnos);

		if (maoAtual.getLista_cartas().size() == 2 && !split) {

			MaoJogador maoSplit = new MaoJogador();
			maoSplit.add(o);
			dinheiro.add(o);

			// add a segunda carta da mao do jogador a primeira carta na mao do split
			maoSplit.addCarta(maoAtual.getLista_cartas().get(1));  
			hit(maoSplit);

			// remove a segunda carta da mao do jogador
			maoAtual.removerCarta();  
			hit(maoAtual);

			// Add a nova mao a lista de maos
			maos.add(1, maoSplit);

			// Multiplica a aposta da mão original
			MaoJogador maoJogador = (MaoJogador) maoAtual;
			maoSplit.setAposta(maoJogador.getAposta());
			dinheiro.diminuiDinheiro(maoJogador.getAposta());  

			// split agora é true para garantir q há somente um
			split = true;

		}
	}
	

	public void terminaTurno() {
		this.turnos++;
	}

	public boolean rendicao() {

		Mao maoAtual = maos.get(turnos);
		Mao maoDealer = maos.get(maos.size() - 1);

		if (maoAtual.getLista_cartas().size() == 2 && !split && maoDealer.calculaValorMao() != 21) {

			// Faz o casting para MaoJogador
			MaoJogador maoJogador = (MaoJogador) maoAtual;
			int aposta = maoJogador.getAposta();
			dinheiro.aumentaDinheiro(aposta / 2);
			return true;

		} else
			return false;
	}

	public int getDinheiro() {
		return dinheiro.getDinheiro();
	}

	public boolean doDouble() {

		MaoJogador maoAtual = (MaoJogador) maos.get(turnos);

		if (maoAtual.getLista_cartas().size() == 2) {

			int aposta = maoAtual.getAposta();

			if (checkAposta(aposta)) {

				hit(maoAtual);
				terminaTurno();
				return true;

			} else
				return false;

		} else
			return false;
	}

	// funcao chamada depois de uma acao
	public void checkStatusMao() {
		Mao maoAtual = maos.get(turnos);
		int valor = maoAtual.calculaValorMao();
		if (valor >= 21)
			terminaTurno();
	}

	// funcao chamada depois de adicionar valor na aposta
	public boolean checkAposta(int aposta) {
		Mao maoAtual = maos.get(turnos);

		if (aposta <= dinheiro.getDinheiro()) {
			dinheiro.diminuiDinheiro(aposta); ;
			MaoJogador maoJogador = (MaoJogador) maoAtual;
			maoJogador.aumentarAposta(aposta);
			return true;
		} else
			return false;

	}

	// funcao verifica se a aposta final é maior ou igual que 50
	public boolean validaAposta() {
		MaoJogador maoAtual = (MaoJogador) maos.get(turnos);
		if (maoAtual.getAposta() >= 50)
			return true;
		else
			return false;
	}

	// clear
	public void devolveAposta() {
		MaoJogador maoAtual = (MaoJogador) maos.get(turnos);
		dinheiro.aumentaDinheiro(maoAtual.getAposta());;
		maoAtual.setAposta(0);
	}

	public void distribuiCarta() {
		MaoJogador maoJogador = (MaoJogador) maos.get(turnos);
		Mao maoDealer = maos.get(turnos + 1);

		hit(maoJogador);
		hit(maoJogador);
		hit(maoDealer);
		hit(maoDealer);
	}

	public void jogaDealer() {
		Mao maoDealer = maos.get(turnos);
		while (maoDealer.calculaValorMao() < 17) {
			hit();
		}
		gerenciadorDeEstados.proxEstado();
	}

	public boolean checkEstouro() {
		Mao maoJogador = maos.get(turnos);
		return maoJogador.estourou();
	}

	// 1 vitoria, 0 empate, -1 derrota
	public int comparaComDealer(MaoJogador maoJogador) {
		int dealer = maos.getLast().calculaValorMao();
		int jogador = maoJogador.calculaValorMao();

		if (jogador <= 21 && dealer <= 21) {
			if (jogador > dealer) {
				ganhaDinheiro(maoJogador.getAposta() * 2);
				return 1;
			} else if (jogador == dealer) {
				ganhaDinheiro(maoJogador.getAposta());
				return 0;
			} else
				return -1;
		} else if (jogador > 21 && dealer > 21) {
			ganhaDinheiro(maoJogador.getAposta());
			return 0;
		} else {
			if (jogador <= 21) {
				ganhaDinheiro(maoJogador.getAposta() * 2);
				return 1;
			} else
				return -1;
		}
	}

	public List<Integer> checkStatusPartida() {
		List<Integer> lista = new ArrayList<>();
		for (int i = 0; i < maos.size() - 1; i++) {
			lista.add(comparaComDealer((MaoJogador) maos.get(i)));
		}
		return lista;
	}

	public void ganhaDinheiro(int valor) {
		dinheiro.aumentaDinheiro(valor);
	}

	// Getters and Setters
	public int getTurnos() {
		return turnos;
	}

	public Mao getMao() {
		return maos.get(turnos);

	}

	public MaoJogador getMaoPrincipal() {
		return (MaoJogador) maos.get(0);
	}

	public MaoJogador getMaoSplit() {
		return (MaoJogador) maos.get(1);
	}

	public MaoJogador getMaoAnterior() {
		return (MaoJogador) maos.get(turnos - 1);
	}

	public MaoJogador getMaoJogador() {
		return (MaoJogador) maos.get(turnos);

	}

	public List<Mao> getMaos() {
		return maos;
	}

	public ArrayList<String> retornaListaCartas() {
		Mao maoAtual = maos.get(turnos);
		ArrayList<Carta> cartas = maoAtual.getLista_cartas();
		ArrayList<String> naipes = new ArrayList<String>();
		for (Carta carta : cartas) {
			naipes.add(carta.getNaipe());
		}
		return naipes;
	}

	public ArrayList<String> retornaListaCartas(Mao maoAtual) {
		ArrayList<Carta> cartas = maoAtual.getLista_cartas();
		ArrayList<String> naipes = new ArrayList<String>();
		for (Carta carta : cartas) {
			naipes.add(carta.getNaipe());
		}
		return naipes;
	}

	public boolean isSplit() {
		return split;
	}

	public Baralho getBaralho() {
		return baralho;
	}

	public Mao getMaoDealer() {
		return maos.getLast();
	}

}
