package comunicacion;

import java.util.Objects;

import logica.Cadena;

public class DTOCadena extends DTOAtributo{

	private String dato;

	public DTOCadena(String nombreAtributo, boolean nulo, boolean clave, String dato) {
		
		super(nombreAtributo, nulo, clave);
		this.dato = dato;
		
	}
	
	public DTOCadena() {
		
		super();
		this.dato="";
		
	}

	public DTOCadena(Cadena cadena) {
		
		super(cadena.getNombreAtributo(), cadena.getClave(), cadena.getNulo());
		this.dato=cadena.getDato();
		
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
	    DTOCadena cadena = (DTOCadena) anObject;
	    
	    return Objects.equals(dato, cadena.dato);
	
	}
	
	public int hashCode() {
		
	    int result = super.hashCode();
	    result = 31 * result + Objects.hashCode(dato);
	    return result;
	
	}
	
}
