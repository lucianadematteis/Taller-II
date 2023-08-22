package logica;

import comunicacion.DTOEntero;

class Entero extends Atributo {
	
	private int valor;

	public Entero(DTOEntero entero) {
		
		super(entero);
		this.valor = entero.getValor();
		
	}
	
	public Entero(String nombreAtributo, boolean nulo, boolean clave, int valor) {
		
		super(nombreAtributo, nulo, clave);
		this.valor = valor;
		
	}

	public int getValor() {
		
		return valor;
		
	}

	public void setValor(int valor) {
		
		this.valor = valor;
		
	}

}
