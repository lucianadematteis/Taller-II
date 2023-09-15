package comunicacion;

import logica.Cadena;

public class DTOCadena extends DTOAtributo{

	private String dato;

	public DTOCadena(String nombreAtributo, boolean nulo, boolean clave, String dato) {
		
		super(nombreAtributo, nulo, clave);
		this.dato = dato;
		
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
	
}
