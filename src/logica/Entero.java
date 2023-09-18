package logica;

import comunicacion.DTOEntero;

public class Entero extends Atributo {
	
	private int valor;

	public Entero(DTOEntero entero) {
		
		super(entero);
		this.valor = entero.getValor();
		
	}
	
	public Entero(String nombreAtributo, boolean nulo, boolean clave, int valor) {
		
		super(nombreAtributo, nulo, clave);
		this.valor = valor;
		
	}

	public Entero(int valor) {
		
		super();
		this.valor = valor;
		
	}
	
	public int getValor() {
		
		return valor;
		
	}

	public void setValor(int valor) {
		
		this.valor = valor;
		
	}

	public boolean equals(Object anObject) {
		
	    if (this == anObject) {
	        return true;
	    }
	    if (anObject == null || getClass() != anObject.getClass()) {
	        return false;
	    }
	    if (!super.equals(anObject)) {
	        return false;
	    }
	    Entero entero = (Entero) anObject; 
	    
	    return valor == entero.valor;
	    
	}
	
	public int hashCode() {
		
	    int result = super.hashCode();
	    result = 31 * result + Integer.hashCode(valor);
	    return result;
	    
	}

}
