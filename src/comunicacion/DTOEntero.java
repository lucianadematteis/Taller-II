package comunicacion;

import logica.Entero;

public class DTOEntero extends DTOAtributo{

	private int valor;

	public DTOEntero(String nombreAtributo, boolean nulo, boolean clave, int valor) {
		
		super(nombreAtributo, nulo, clave);
		this.valor = valor;
		
	}
	
	public DTOEntero(Entero entero) {
		
		super(entero.getNombreAtributo(), entero.getClave(), entero.getNulo());
		this.valor=entero.getValor();
		
	}

	public int getValor() {
		
		return valor;
		
	}

	public void setValor(int valor) {
		
		this.valor = valor;
		
	}
	
}
