package model;

import java.util.ArrayList;
import java.util.List;

class Dinheiro implements ObservadoIF {
	private int dinheiro;
	List<ObservadorIF> observadores = new ArrayList<ObservadorIF>();
	
	Dinheiro(){
		dinheiro = 2400;
	}
	
	Dinheiro(int valor){
		dinheiro = valor;
	}
	
	int getDinheiro() {
		return dinheiro;
	}
	
	void aumentaDinheiro(int valor) {
		dinheiro += valor;
		for(ObservadorIF o : observadores) {
        	o.notificaDinheiro(dinheiro);
        }
	}
	
	void diminuiDinheiro(int valor) {
		dinheiro -= valor;
		for(ObservadorIF o : observadores) {
        	o.notificaDinheiro(dinheiro);
        }
	}

	@Override
	public void add(ObservadorIF o) {
		observadores.add(o);
		o.notificaDinheiro(dinheiro);
	}

	@Override
	public void remove(ObservadorIF o) {
		observadores.remove(o);
	}
	
}
