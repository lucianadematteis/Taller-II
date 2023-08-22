package logica;

import comunicacion.DTOCadena;

public class Cadena extends Atributo {
	
	private String dato;

	public Cadena(DTOCadena cadena) {
		
		super(cadena);
		this.dato = cadena.getDato();
		
	}

	public String getDato() {
		
		return dato;
		
	}

	public void setDato(String dato) {
		
		this.dato = dato;
		
	}

}
