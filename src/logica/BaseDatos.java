package logica;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import comunicacion.DTOBaseDatos;

/**
 * Esta clase representa una base de datos y contiene metodos para administrar las tablas en la misma. 
 * Se relaciona con TABLAS mediante una relacion de agregacion
 *@author Gabriel Bolsi
 *@author Brandon Cairus
 *@author Daiana Daguerre
 *@author Luciana De Matteis
 *@author Mauricio Gonzalez
 */

public class BaseDatos {

	private String nombreBD;
	private LinkedHashMap<String, Tabla> tablas;
	
	/**
	 * Constructor que recibe como parametro un objeto DTOBaseDatos que contiene informacion sobre la base de datos, inicializa el nombre de la base de datos con el nombre proporcionado en el objeto baseDatos y crea una nueva instancia de LinkedHashMap para almacenar las tablas.
	 * @param BaseDatos->DTOBaseDatos
	 */
	public BaseDatos(DTOBaseDatos BaseDatos) {
		
		this.nombreBD = BaseDatos.getNombreBD();
		tablas = new LinkedHashMap<String, Tabla>();
		
	}
	
	/**
	 * Constructor que recibe como parametro una cadena de texto que representa el nombre de la base de datos y lo utiliza para inicializar el nombre de la misma. Asimismo crea una nueva instancia de LinkedHashMap para almacenar las tablas.
	 * @param nombreBD->nombre de la base de datos
	 */
	public BaseDatos(String nombreBD) {
		
		this.nombreBD = nombreBD;
		tablas = new LinkedHashMap<String, Tabla>();
		
	}

	/**
	 * Metodo publico que retorna un String. Permite recuperar el valor del atributo nombreBD. Su proposito es proporcionar acceso controlado a los datos encapsulados dentro de un objeto.
	 * @return el nombre de la base de datos
	 */
	public String getNombreBD() {
		
		return nombreBD;
		
	}

	/**
	 * Metodo publico que retorna un LinkedHashMap que contiene las tablas de la base de datos. Su proposito es proporcionar acceso controlado a los datos encapsulados dentro de un objeto.
	 * @return las tablas de la base de datos
	 */
	public Map<String, Tabla> getTablas() {
		
		return tablas;
		
	}

	/**
	 * Metodo publico que recibe como parametro un mapa de tablas y establece el mapa de tablas de la base de datos con el mapa proporcionado por parametro.
	 * @param tablas establece las tablas a las base de datos
	 */
	public void setTablas(LinkedHashMap<String, Tabla> tablas) {
		
		this.tablas = tablas;
		
	}
	
	/**
	 * "Metodo publico que recibe como parametro un objeto del tipo Tabla y lo inserta en el mapa de tablas de la base de datos
	 * @param tabla-> Tabla a insertar en el mapa de tablas
	 */
	public void insertarTabla(Tabla tabla) {
        
       tablas.put(tabla.getNombreTabla(), tabla);
       
    }
	
	/**
	 * Metodo publico que recibe por parametro una cadena de texto que corresponde al nombre de la tabla, la busca en la coleccion y retorna la tabla correspondiente al nombre proporcionado
	 * @param nombreTabla -> nombre de la tabla
	 * @return la tabla
	 */
	public Tabla obtenerTabla(String nombreTabla) {
			
		for (Entry<String, Tabla> tabla : this.tablas.entrySet()) {

			if(tabla.getKey().equalsIgnoreCase(nombreTabla)) {
			
				return tabla.getValue();
			
			}
			
		}
			
		return null;
			
	}
	
	/**
	 * Metodo publico que retorna una lista de cadenas de texto que representan los nombres de las tablas en la base de datos
	 * @return los nombres de las tablas
	 */
	public ArrayList<String> obtenerNomTablas(){
		
		ArrayList<String> tablasNom = new ArrayList<String>();
		
		for (Entry<String, Tabla> tabla : tablas.entrySet()) {
			
			tablasNom.add(tabla.getKey());
			
		}
		
		return tablasNom;
		
	}
	
	/**
	 * Metodo publico que recibe por parametro un objeto del tipo Tabla que representa la tabla que sera agregada al mapa de tablas de la base de datos.
	 * @param tabla -> tabla que sera aregada a mapa de tabla de la base de datos
	 */
	public void agregarTabla(Tabla tabla) {
		
		this.tablas.put(tabla.getNombreTabla(), tabla);
		
	}
	
	/**
	 * Metodo publico que dado un nombre de una tabla, la elimina del mapa de tablas de la presente clase
	 * @param nombreTabla -> nombre de la tabla a eliminar
	 */
	public void eliminarTabla(String nombreTabla) {
		
		Tabla tabla = this.obtenerTabla(nombreTabla);
		this.tablas.remove(tabla.getNombreTabla());
		
	}
	
}