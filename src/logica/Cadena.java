package logica;

public class Cadena extends Atributo {
	private String dato;

	
	
	public Cadena(String nombreAtributo, boolean nulo, boolean clave, String dato) {
		super(nombreAtributo, nulo, clave);
		this.dato = dato;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

}
