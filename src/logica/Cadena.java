package logica;

import java.util.Objects;

import comunicacion.DTOCadena;
/**
 * Es una clase derivada de la clase Atributo
 *@author Gabriel Bolsi
 *@author Brandon Cairus
 *@author Daiana Daguerre
 *@author Luciana De Matteis
 *@author Mauricio Gonzalez
 */

public class Cadena extends Atributo {
	
	private String dato;
/**
 * Metodo publico que recibe un objeto DTOCadena que  inicializa la instancia de Cadena con los valores proporcionados por el objeto, incluyendo el nombre del atributo, si puede ser nulo, si es una clave y el dato de tipo cadena.
 * @param cadena->DTOCadena
 */
	public Cadena(DTOCadena cadena) {
		
		super(cadena);
		this.dato = cadena.getDato();
		
	}
	/**
	 * Metodo pblico que recibe un objeto DTOCadena que  inicializa la instancia de Cadena con los valores proporcionados por el objeto, incluyendo el nombre del atributo, si puede ser nulo, si es una clave y el dato de tipo cadena.
	 * @param nombreAtributo->nombre del atributo
	 * @param nulo->si es nulo o no
	 * @param clave-> si es clave o no
	 * @param dato-> el valor del atributo
	 */
	public Cadena(String nombreAtributo, boolean nulo, boolean clave, String dato) {
		
		super(nombreAtributo, nulo, clave);
		this.dato = dato;
		
	}
	
	/**
	 * Metodo publico que recibe un solo parametro que es el dato de tipo cadena e inicializa la instancia de Cadena con el dato proporcionado y establece los valores predeterminados para el nombre del atributo, nulidad y clave.
	 * @param dato->valor del atributo
	 */
	public Cadena(String dato) {
		
		super();
		this.dato = dato;
		
	}
	/**
	 * Metodo publico que retorna un String. Permite recuperar el valor del atributo dato. Su proposito es proporcionar acceso controlado a los datos encapsulados dentro de un objeto.
	 * @return el valor del dato
	 */
	public String getDato() {
		
		return dato;
		
	}

	/**
	 * Metodo publico que recibe como parametro un String y permite establecer o modificar el valor del atributo privado dato. Su objetivo principal es permitir la asignacion controlada de valores a los atributos encapsulados dentro de un objeto.
	 * @param dato->valor del dato a modificar o establecer
	 */
	public void setDato(String dato) {
		
		this.dato = dato;
		
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
	    Cadena cadena = (Cadena) anObject;
	    
	    return Objects.equals(dato, cadena.dato);
	
	}
	
	/**
     * Metodo publico que no recibe parametros y retorna un valor numerico que representa la instancia de un objeto (valor hash basado en los atributos nombreAtributo, nulo y clave).
     */
	public int hashCode() {
		
	    int result = super.hashCode();
	    result = 31 * result + Objects.hashCode(dato);
	    return result;
	
	}

}
