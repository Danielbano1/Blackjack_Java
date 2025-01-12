package model;

public interface ObservadorIF {
	public String notificaAddCarta(ObservadoIF o);
	public String notificaRemoveCarta(ObservadoIF o);
	public int notificaPontos(ObservadoIF o);
	public int notificaAposta(ObservadoIF o);
	public int notificaDinheiro(ObservadoIF o);
}
