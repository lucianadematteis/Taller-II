package logica;

import java.util.Objects;

import comunicacion.DTOCadena;

public class Cadena extends Atributo {
	
	private String dato;

	public Cadena(DTOCadena cadena) {
		
		super(cadena);
		this.dato = cadena.getDato();
		
	}
	
	public Cadena(String nombreAtributo, boolean nulo, boolean clave, String dato) {
		
		super(nombreAtributo, nulo, clave);
		this.dato = dato;
		
	}
	
	public Cadena(String dato) {
		
		super();
		this.dato = dato;
		
	}

	public String getDato() {
		
		return dato;
		
	}

	public void setDato(String dato) {
		
		this.dato = dato;
		
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
	    Cadena cadena = (Cadena) anObject;
	    
	    return Objects.equals(dato, cadena.dato);
	
	}
	
	public int hashCode() {
		
	    int result = super.hashCode();
	    result = 31 * result + Objects.hashCode(dato);
	    return result;
	
	}

}
