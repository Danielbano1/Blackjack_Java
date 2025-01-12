package model;

public interface ObservadoIF {
	public String getCarta();
	public int getPontos();
	public int getAposta();
	public int getaDinheiro();
	public void add(ObservadorIF o);
    public void remove(ObservadorIF o);
}
