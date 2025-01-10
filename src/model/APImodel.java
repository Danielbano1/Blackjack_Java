package model;

import java.util.ArrayList;
import java.util.List;

import model.Partida;
public class APImodel {
	private Partida partida;
	private GerenciadorDeEstados gerenciadorDeEstados;
	
	
	public APImodel() {
		this.partida = new Partida();
		gerenciadorDeEstados = this.partida.gerenciadorDeEstados;
	}
	
	public APImodel(List<Object> infos) {
		this.partida = new Partida(infos);
		gerenciadorDeEstados = this.partida.gerenciadorDeEstados;
	}

	
	//estruturtas para a fachada 
	
	public static List<Object> carregarPartida(int nome){
		return SalvarPartida.carregarPartida(nome);
		
	}
	
	public void salvarPartida() {
		SalvarPartida.salvarPartida(partida);
	} 
	
	public int getDinheiro() {
		return partida.getDinheiro();
	}
	
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
    
    public int getApostaMaoAtual() {
    	return partida.getMaoJogador().getAposta();
    }
    
    public int getApostaMaoPrincipal() {
    	return partida.getMaoPrincipal().calculaValorMao();
    }
    
    public int getApostaMaoSplit() {
    	return partida.getMaoSplit().calculaValorMao();
    }
    
    // retorna ArrayList<String> de naipes da mao do dealer
    public ArrayList<String> getNaipesDealer(){
    	return partida.retornaListaCartas(partida.getMaoDealer());
    }
    // retorna ArrayList<String> de naipes da mao principal
    public ArrayList<String> getNaipesPrincipal(){
    	return partida.retornaListaCartas(partida.getMaoPrincipal());
    }
    
 // retorna ArrayList<String> de naipes da mao split
    public ArrayList<String> getNaipesSplit(){
    	return partida.retornaListaCartas(partida.getMaoSplit());
    }
    
    public int calculaValorMaoDealer() {
    	return partida.getMaoDealer().calculaValorMao();
    }
    
    public int calculaValorMaoPrincipal() {
    	return partida.getMaoPrincipal().calculaValorMao();
    }

    public int calculaValorMaoSplit() {
    	return partida.getMaoSplit().calculaValorMao();
    }
    
    // retorna naipe da primeira carta da mao
    public String getPrimeiroNaipeDealer() {
    	return getNaipesDealer().getFirst();
    }
    
    public String getPrimeiroNaipePrincipal() {
    	return getNaipesPrincipal().getFirst();
    }
    
    public String getPrimeiroNaipeSplit() {
    	return getNaipesSplit().getFirst();
    }
    
    public String getUltimoNaipeDealer() {
    	return getNaipesDealer().getLast();
    }
    
    public String getUltimoNaipePrincipal() {
    	return getNaipesPrincipal().getLast();
    }
    
    public String getUltimoNaipeSplit() {
    	return getNaipesSplit().getLast();
    }
    
    // double remover
    public int calculaValorMaoAnterior() {
    	return partida.getMaoAnterior().calculaValorMao();
    }
    
    public String getNaipeMaoAnterior() {
    	return partida.getMaoAnterior().getLista_cartas().getLast().getNaipe();
    }
    
    public int getApostaMaoAnterior() {
    	return partida.getMaoAnterior().getAposta();
    }
    
    public void distribuiCarta() {
    	partida.distribuiCarta();
    }
    
    public void jogaDealer() {
    	partida.jogaDealer();
    }
    
    public List<Integer> checkStatusPartida(){
    	return partida.checkStatusPartida();
    }
  


}
