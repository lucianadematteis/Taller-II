package logica;

import comunicacion.DTOEntero;

/**
 * Es una clase derivada de la clase Atributo
 *@author Gabriel Bolsi
 *@author Brandon Cairus
 *@author Daiana Daguerre
 *@author Luciana De Matteis
 *@author Mauricio Gonzalez
 */

public class Entero extends Atributo {
	
	private int valor;
	
	/**
	 * Constructor que recibe un objeto DTOEntero que  inicializa la instancia de Entero con los valores proporcionados por el objeto, incluyendo el nombre del atributo, si puede ser nulo, si es una clave y el valor de tipo entero.
	 * @param entero->DTOEntero
	 */
	public Entero(DTOEntero entero) {
		
		super(entero);
		this.valor = entero.getValor();
		
	}
	
	/**
	 * Constructor especifico que recibe los siguientes parametros: un String que es el nombre del atributo, un booleano que indica si es nulo,  un booleano que indica si es clave y un entero que indica el tipo de dato que manipulara ese campo. Este metodo permite crear un objeto e inicializarlo con esos parametros.
	 * @param nombreAtributo-> nombre del atributo
	 * @param nulo-> si es nulo o no
	 * @param clave-> si es clave o no
	 * @param valor->  valor del atributo
	 */
	public Entero(String nombreAtributo, boolean nulo, boolean clave, int valor) {
		
		super(nombreAtributo, nulo, clave);
		this.valor = valor;
		
	}
	
	/**
	 * Constructor que recibe solo el valor de tipo entero e inicializa la instancia de Entero con el valor proporcionado y establece los valores predeterminados para el nombre del atributo, nulidad y clave.
	 * @param valor-> valor del atributo
	 */
	public Entero(int valor) {
		
		super();
		this.valor = valor;
		
	}
	
	/**
	 * Metodo publico que retorna un entero.  Permite recuperar el valor del atributo valor. Su proposito es proporcionar acceso controlado a los datos encapsulados dentro de un objeto.
	 * @return-> el valor del atributo
	 */
	public int getValor() {
		
		return valor;
		
	}

	/**
	 * Metodo publico que recibe como parametro un entero y permite establecer o modificar el valor del atributo privado valor. Su objetivo principal es permitir la asignacion controlada de valores a los atributos encapsulados dentro de un objeto.
	 * @param valor-> valor del atributo a establecer o modificar
	 */
	public void setValor(int valor) {
		
		this.valor = valor;
		
	}

	/**
	 * Metodo publico que recibe como parametro un objeto que es el que se desea comparar. La funcion verifica si el objeto pasado como argumento es la misma instancia que el objeto actual. Si lo es, se consideran iguales y retorna true. En caso contrario retorna false. Finalmente, realiza la comparacion de contenido entre los atributos relevantes de los objetos, segun la logica de igualdad definida para esta clase especifica. Si los objetos son iguales en contenido, el metodo retorna true; en caso contrario, retorna false. 
	 */
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
	    
	    Entero entero = (Entero) anObject; 
	    
	    return valor == entero.valor;
	    
	}
	
	/**
     * Metodo publico que no recibe parametros y retorna un valor numerico que representa la instancia de un objeto (valor hash basado en los atributos nombreAtributo, nulo y clave).
     */
	public int hashCode() {
		
	    int result = super.hashCode();
	    result = 31 * result + Integer.hashCode(valor);
	    return result;
	    
	}

}
