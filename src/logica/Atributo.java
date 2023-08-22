package logica;

import comunicacion.DTOAtributo;

public abstract class Atributo {
	
	private String nombreAtributo;
	private boolean nulo;
	private boolean clave;

	public Atributo(DTOAtributo atributo) {
		
		this.nombreAtributo = atributo.getNombreAtributo();
		this.nulo = atributo.getNulo();
		this.clave = atributo.getClave();
		
	}

	public String getNombreAtributo() {
		
		return nombreAtributo;
	
	}
	
	public boolean getClave() {
		
		return this.clave;
	
	}
	
	public boolean getNulo() {
		
		return this.nulo;
	
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
