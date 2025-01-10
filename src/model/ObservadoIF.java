package model;

public interface ObservadoIF {
	public Object get();			
	public void add(ObservadorIF o);
    public void remove(ObservadorIF o);
}
