package comunicacion;

import java.util.Objects;

import logica.Atributo;

public abstract class DTOAtributo {

	private String nombreAtributo;
	private boolean nulo;
	private boolean clave;

	public DTOAtributo(String nombreAtributo, boolean nulo, boolean clave) {
		
		this.nombreAtributo = nombreAtributo;
		this.nulo = nulo;
		this.clave = clave;
		
	}
	
	public DTOAtributo(Atributo atributo) {
		
		this.nombreAtributo = atributo.getNombreAtributo();
		this.nulo = atributo.getNulo();
		this.clave = atributo.getClave();
		
	}
	
	public DTOAtributo() {
		
		this.nombreAtributo="";
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

	public void setNombreAtributo(String nombreAtributo) {
		
		this.nombreAtributo = nombreAtributo;
		
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
        DTOAtributo atributo = (DTOAtributo) o;
        
        return nulo == atributo.nulo &&
               clave == atributo.clave &&
               Objects.equals(nombreAtributo, atributo.nombreAtributo);
    
    }
    
    public int hashCode() {
    	
        return Objects.hash(nombreAtributo, nulo, clave);
   
    }
	
}
