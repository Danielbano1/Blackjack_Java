package model;

import model.Partida;
public class APImodel {
	private Partida partida;
	private GerenciadorDeEstados gerenciadorDeEstados;
	
	
	private APImodel(Partida partida) {
		this.partida = partida;
		gerenciadorDeEstados = this.partida.gerenciadorDeEstados;
	}
	
	//estruturtas para a fachada 
    //verifica se uma ficha pode ser retirada
    public boolean checkReducao(int valorFicha) {
    	return (partida.getMaoJogador().getAposta() - valorFicha >= 0);
    }
    //passa a partida para o proximo estado
    public void passaEstado() {
    	gerenciadorDeEstados.proxEstado();
    }
    //verifica se o hit pode ser feito
    public boolean checkPassaOuTermina(int turno) {
    	return (partida.checkEstouro() && partida.getTurnos()==turno);
    }
    //define o estado do jogo
    public void defineEstadoFim() {
    	gerenciadorDeEstados.setEstadoAtual(Estado.FIM);
    }
    //verifica o estado dealer
    public boolean partidaEstadoDealer() {
    	return gerenciadorDeEstados.getEstadoAtual() == Estado.DEALER;
    }
    //verifica o estado dealer
    public boolean partidaEstadoFim() {
    	return gerenciadorDeEstados.getEstadoAtual() == Estado.FIM;
    }
    //verifica o estado dealer
    public boolean partidaEstadoJogo() {
    	return gerenciadorDeEstados.getEstadoAtual() == Estado.JOGO;
    }
    //verifica o estado dealer
    public boolean partidaEstadoAposta() {
    	return gerenciadorDeEstados.getEstadoAtual() == Estado.APOSTA;
    }
    
    //Encapsulamento de metodos da partida
    public void devolveAposta() {
        partida.devolveAposta();
    }
    //funcao verifica se a aposta final é maior ou igual que 50
    public boolean validaAposta() {
        return partida.validaAposta();
    }
    //funcao chamada depois de adicionar valor na aposta
    public boolean checkAposta(int aposta) {
        return partida.checkAposta(aposta);
    }
    public boolean doDouble() {
    	return partida.doDouble();
    }
    public void split() {
    	partida.split();
    }
    public boolean isSplit() {
        return partida.isSplit();
    }
    public boolean checkEstouro() {
        return partida.checkEstouro();
    }
    public void hit() {
        partida.hit();
    }
    public int getTurnos() {
        return partida.getTurnos();
    }
    public void terminaTurno() {
        partida.terminaTurno();
    }
    public boolean rendicao() {
    	return partida.rendicao();
    }


}
