package logica;

import java.util.Objects;



import comunicacion.DTOAtributo;

/**
 * Clase abstracta que mantiene una relacion de herencia con las clases Cadena y Entero.
 * Asimismo se relaciona con la clase Atributos con quien mantiene una relacion de agregacion.
 *@author Gabriel Bolsi
 *@author Brandon Cairus
 *@author Daiana Daguerre
 *@author Luciana De Matteis
 *@author Mauricio Gonzalez
 */

public abstract class Atributo {
	
	String nombreAtributo;
	private boolean nulo;
	private boolean clave;

	/**
	 * Constructor que recibe un objeto DTOAtributo como argumento e inicializa los atributos de la instancia a partir de los valores del objeto.
	 * @param atributo->DTOAtributo
	 */
	public Atributo(DTOAtributo atributo) {
		
		this.nombreAtributo = atributo.getNombreAtributo();
		this.nulo = atributo.getNulo();
		this.clave = atributo.getClave();
		
	}
	
	/**
	 * Constructor que recibe un String que es el nombre del atributo, un booleano que indica si puede ser nulo y un booleano que indica si es una clave y los utiliza para inicializar los atributos de la instancia.
	 * @param nombreAtributo->nombre del atributo
	 * @param nulo-> Si es nulo o no
	 * @param clave-> Si es clave o no
	 */
	public Atributo(String nombreAtributo, boolean nulo, boolean clave) {
		
		this.nombreAtributo = nombreAtributo;
		this.nulo = nulo;
		this.clave = clave;
		
	}
	
	/**
	 * Constructor sin argumentos que inicializa los atributos de la instancia con valores predeterminados.
	 */
	public Atributo() {
		
		this.nombreAtributo = "";
		this.nulo = false;
		this.clave = false;
		
	}
	
	/**
	 * Metodo publico que retorna un String. Permite recuperar el nombre del atributo. Su proposito es proporcionar acceso controlado a los datos encapsulados dentro de un objeto.
	 * @return el nombre del atributo
	 */
	public String getNombreAtributo() {
		
		return nombreAtributo;
	
	}
	
	/**
	 * Metodo publico que retorna true si el atributo nulo es true y false en caso contrario. 
	 * @return Si es clave o no
	 */
	public boolean getClave() {
		
		return this.clave;
	
	}
	
	/**
	 * Metodo publico que retorna true si el atributo Clave es true y false en caso contrario.
	 * @return si es nulo o no
	 */
	public boolean getNulo() {
		
		return this.nulo;
	
	}
	
	/**
	 * Metodo publico que permite establecer o modificar el valor del atributo nulo.
	 * @param nulo-> atributo nulo a modificar o establecer
	 */
	public void setNulo(boolean nulo) {
		
		this.nulo = nulo;
		
	}
	
	/**
	 * Metodo publico que permite establecer o modificar el valor del atributo clave
	 * @param clave-> atributo clave a nodificar o establecer
	 */
	public void setClave(boolean clave) {
		
		this.clave = clave;
		
	}
	
	/**
	 * Metodo publico que recibe como parametro un objeto que es el que se desea comparar. La funcion verifica si el objeto pasado como argumento es la misma instancia que el objeto actual. Si lo es, se consideran iguales y retorna true. En caso contrario retorna false. Finalmente, realiza la comparacion de contenido entre los atributos relevantes de los objetos, segun la logica de igualdad definida para esta clase especifica. Si los objetos son iguales en contenido, el metodo retorna true; en caso contrario, retorna false. 
	 */
    public boolean equals(Object o) {
    	
        if (this == o) {
        	
        	return true;
        
        }
        
        if (o == null || getClass() != o.getClass()) {
        	
        	return false;
        
        }
        
        Atributo atributo = (Atributo) o;
        
        return nulo == atributo.nulo &&
               clave == atributo.clave &&
               Objects.equals(nombreAtributo, atributo.nombreAtributo);
    
    }
    
    /**
     * Metodo publico que no recibe parametros y retorna un valor numerico que representa la instancia de un objeto (valor hash basado en los atributos nombreAtributo, nulo y clave).
     */
    public int hashCode() {
    	
        return Objects.hash(nombreAtributo, nulo, clave);
   
    }

}
