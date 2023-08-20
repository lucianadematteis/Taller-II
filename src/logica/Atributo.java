package logica;

public abstract class Atributo {
	
	private String nombreAtributo;
	private boolean nulo;
	private boolean clave;

	
	
	public Atributo(String nombreAtributo, boolean nulo, boolean clave) {
		super();
		this.nombreAtributo = nombreAtributo;
		this.nulo = nulo;
		this.clave = clave;
	}

	public String getNombreAtributo() {
		return nombreAtributo;
	}

	public void setNombreAtributo(String nombreAtributo) {
		this.nombreAtributo = nombreAtributo;
	}

	public void setNulo(boolean nulo) {
		this.nulo = nulo;
	}

	public void setClave(boolean clave) {
		this.clave = clave;
	}

}
