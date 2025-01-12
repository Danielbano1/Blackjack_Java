package model;

public interface ObservadorIF {
	public void notificaAddCarta(String carta);
	public void notificaRemoveCarta(String carta);
	public void notificaPontos(int pontos);
	public void notificaAposta(int aposta);
	public void notificaDinheiro(int dinheiro);
}
