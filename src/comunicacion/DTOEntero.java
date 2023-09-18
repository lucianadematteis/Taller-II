package comunicacion;

import logica.Entero;

public class DTOEntero extends DTOAtributo{

	private int valor;

	public DTOEntero(String nombreAtributo, boolean nulo, boolean clave, int valor) {
		
		super(nombreAtributo, nulo, clave);
		this.valor = valor;
		
	}
	
	@SuppressWarnings("null")
	public DTOEntero() {
		
		super();
		this.valor=(Integer) null;
		
	}
	
	public DTOEntero(Entero entero) {
		
		
	    super(entero.getNombreAtributo(), entero.getClave(), entero.getNulo());
	    this.valor = entero.getValor();
	    
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
	    DTOEntero entero = (DTOEntero) anObject; 
	    
	    return valor == entero.valor;
	    
	}
	
	public int hashCode() {
		
	    int result = super.hashCode();
	    result = 31 * result + Integer.hashCode(valor);
	    return result;
	    
	}
	
}
