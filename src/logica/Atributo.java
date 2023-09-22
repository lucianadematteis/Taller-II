package logica;

import java.util.Objects;



import comunicacion.DTOAtributo;
/**
 * Clase abstracta que mantiene una relacion de herencia con las clases Cadena y Entero.
 * Asimismo se relaciona con la clase Atributos con quien mantiene una relacion de agregacion.
 *@author Brandon
 *@author Gabriel
 */
public abstract class Atributo {
	
	private String nombreAtributo;
	private boolean nulo;
	private boolean clave;

	public Atributo(DTOAtributo atributo) {
		
		this.nombreAtributo = atributo.getNombreAtributo();
		this.nulo = atributo.getNulo();
		this.clave = atributo.getClave();
		
	}
	
	public Atributo(String nombreAtributo, boolean nulo, boolean clave) {
		
		this.nombreAtributo = nombreAtributo;
		this.nulo = nulo;
		this.clave = clave;
		
	}
	
	public Atributo() {
		
		this.nombreAtributo = "";
		this.nulo = false;
		this.clave = false;
		
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

	public void setNulo(boolean nulo) {
		
		this.nulo = nulo;
	}
	

	public void setClave(boolean clave) {
		
		this.clave = clave;
		
	}
	
    public boolean equals(Object o) {
    	
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atributo atributo = (Atributo) o;
        
        return nulo == atributo.nulo &&
               clave == atributo.clave &&
               Objects.equals(nombreAtributo, atributo.nombreAtributo);
    
    }
    
    public int hashCode() {
    	
        return Objects.hash(nombreAtributo, nulo, clave);
   
    }

}
