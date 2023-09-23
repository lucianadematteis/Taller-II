package logica;

import java.util.LinkedHashMap;

import comunicacion.DTOUsuario;

/**
 * Esta clase representa a un usuario con nombre y contrasenia y puede tener acceso a varias bases de datos. La clase se relaciona con BASESDATOS mediante una relacion de agregacion.
  *@author Gabriel Bolsi
 *@author Brandon Cairus
 *@author Daiana Daguerre
 *@author Luciana De Matteis
 *@author Mauricio Gonzalez
 */
public class Usuario {

	private String nombreUser;
	private String contrasenia;
	private LinkedHashMap<String, BaseDatos> basesDatos;

	/**
	 * Constructor que recibe un objeto del tipo DTOUsuario que contiene informacion sobre el usuario e inicializa el nombre del mismo y la contrasenia con los valores proporcionados en el objeto que recibe
	 * @param usuario-> DTOUsuario
	 */
	public Usuario(DTOUsuario usuario) {

		this.nombreUser = usuario.getNombreUser();
		this.contrasenia = usuario.getContrasenia();
		basesDatos = new LinkedHashMap<String, BaseDatos>();

	}
	
	/**
	 * Constructor especifico que recibe dos cadenas de texto que corresponden al nombre del usuario y la contrasenia que tendra asociada el mismo. Este metodo permite crear un objeto e inicializarlo con esos parametros
	 * @param nombreUser-> nombre usuario
	 * @param contrasenia->contrasenia del usuario
	 */
	public Usuario(String nombreUser, String contrasenia) {
		
		this.nombreUser = nombreUser;
		this.contrasenia = contrasenia;
		basesDatos = new LinkedHashMap<String, BaseDatos>(); 
		
	}

	/**
	 * Metodo publico que retorna un String. Permite recuperar el valor del atributo nombreUser. Su proposito es proporcionar acceso controlado a los datos encapsulados dentro de un objeto.
	 * @return el nombre de usuario
	 */
	public String getNombreUser() {

		return nombreUser;

	}

	/**
	 * Metodo publico que retorna un String. Permite recuperar el valor del atributo contrasenia. Su proposito es proporcionar acceso controlado a los datos encapsulados dentro de un objeto.
	 * @return la contrasenia del usuario
	 */
	public String getContrasenia() {

		return contrasenia;

	}

	/**
	 * Metodo publico que retorna la coleccion que contiene las bases de datos relacionadas con el usuario. Su proposito es proporcionar acceso controlado a los datos encapsulados dentro de un objeto.
	 * @return las bases de datos del usuario
	 */
	public LinkedHashMap<String, BaseDatos> getBasesDatos() {

		return basesDatos;

	}
	
	/**
	 * Metodo publico que recibe un mapa de bases de datos y establece el mapa de base de datos relacionadas con este usuario con el mapa proporcionado por parametro. Su objetivo principal es permitir la asignacion controlada de valores a los atributos encapsulados dentro de un objeto.
	 * @param basesDatos-> establece el mapa de bases de datos relacionas con el usuario
	 */
	public void setBasesDatos(LinkedHashMap<String, BaseDatos> basesDatos) {

		this.basesDatos = basesDatos;

	}
	
	/**
	 * Metodo publico que recibe un objeto BaseDatos y la agrega al mapa de bases de datos del usuario.
	 * @param base-> agrega el objeto a las base de datos del usuario
	 */
	public void agregarBD(BaseDatos base) {
		
		this.basesDatos.put(base.getNombreBD(), base);
		
	}

}
