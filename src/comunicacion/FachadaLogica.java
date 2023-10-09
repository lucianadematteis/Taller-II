package comunicacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import logica.Atributo;
import logica.BaseDatos;
import logica.Tabla;
import logica.Usuario;
import persistencia.Persistencia;

/**
 * Esta es una implementacion concreta de la interfaz IFachadaLogica.
 * Implementa todas las operaciones de IFachadaLogica.
 *@author Gabriel Bolsi
 *@author Brandon Cairus
 *@author Daiana Daguerre
 *@author Luciana De Matteis
 *@author Mauricio Gonzalez
 */

public class FachadaLogica implements IFachadaLogica {

	Persistencia persistencia = new Persistencia();
	private String baseDatos;
	private String usuario;
	private LinkedHashMap<String, Usuario> usuarios;
	private LinkedHashMap<String, String> ayuda;
	private int demoHabilitada;

	public FachadaLogica() {

		baseDatos = "";
		usuario = "";
		usuarios = new LinkedHashMap<String, Usuario>();
		ayuda = new LinkedHashMap<String, String>();
		
	}

	/**
	 * Recibe el nombre de una base de datos como parámetro y establece la variable miembro de la clase (baseDatos) con ese nombre.
	 * @param baseDatos nombre de la base de datos a seleccionar.
	 */
	public void seleccionarBaseDatos(String baseDatos) {

		for (Map.Entry<String, BaseDatos> bd : this.obtenerUsuario().getBasesDatos().entrySet()) {
			
			if(bd.getKey().equalsIgnoreCase(baseDatos)) {
				
				this.baseDatos = bd.getKey();
				
			}
			
		}

	}
	
	/**
	 * Establece la variable baseDatos con cadena vacía.
	 */
	public void liberarMemoriaBaseDatos() {

		this.baseDatos = "";

	}
	
	/**
	 * Verifica si se ha seleccionado una base de datos.
	 * @return true si hay una base de datos seleccionada, false si no la hay.
	 */
	public boolean bdSeleccionada() {
		
		return !(this.baseDatos.isEmpty());
		
	}

	/**
	 * Establece la variable usuario con cadena vacía.
	 */
	public void liberarMemoriaUsuario() {

		this.usuario = "";

	}
	
	/**
	 * @return Cadena de texto que representa el nombre del usuario actual.
	 */
	public String getUsuario() {

		return usuario;

	}

	/**
	 *Establece la variable miembro de la clase (usuario) con ese nombre.
	 *@param usuario nombre del usuario a "seleccionar".
	 */
	public void seleccionarUsuario(String usuario) {

		this.usuario = usuario;

	}
	
	/**
	 *Persiste los datos almacenados en la colección de usuarios en la instancia de la clase Persistencia.
	 */
	public void persistirDatos() {

		persistencia.persistirTodo(usuarios, demoHabilitada);

	}
	
	/**
	 * @return Instancia de la clase BaseDatos correspondiente a la base de datos seleccionada.
	 */
	private BaseDatos obtenerBaseDatos() {
		
		for (Map.Entry<String, BaseDatos> entry : this.obtenerUsuario().getBasesDatos().entrySet()) {
			
			if(entry.getKey().equalsIgnoreCase(baseDatos)) {
				
				return entry.getValue();
				
			}
			
		}
		
		return null;
		
	}
	
	/**
	 *Recupera datos de persistencia y los carga en la colección de usuarios y en la variable ayuda.
	 */
	public void recuperarDatos() {

		ayuda=persistencia.recuperarAyuda();
		persistencia.recuperarTodo(usuarios);
		demoHabilitada=persistencia.recuperarDemo();

	}
	
	/**
	 *Crea una nueva instancia de Usuario a partir de los datos proporcionados y reemplaza la instancia anterior del usuario en la colección de usuarios.
	 *@param user -> Representa los datos del usuario a modificar.
	 */
	public void modificarUsuario(String contrasenia) {

		Usuario usuario = this.obtenerUsuario();
		usuario.setContrasenia(contrasenia);

	}
	
	/**
	 *Lo elimina de la coleccion de usuarios.
	 *@param user -> Representa al usuario a eliminar.
	 */
	public void eliminarusuario(DTOUsuario user) {

		usuarios.remove(user.getNombreUser()); 
		persistencia.eliminarCarpetaUsuarioBaseTabla(usuario, null, null);
		
	}
	
	/**
	 *Verifica si el nombre de usuario actual está presente en la colección de usuarios.
	 *@return La instancia de Usuario correspondiente. Si no se encuentra retorna null.
	 */
	private Usuario obtenerUsuario() {

		if (usuarios.containsKey(usuario)) {

			return usuarios.get(usuario);

		}else {

			return null ;

		}
		
	}


	/**
	 *Crea una nueva instancia de Usuario a partir de los datos proporcionados en él y lo agrega a la colección de usuarios.
	 *@param dto representa los datos del nuevo usuario a insertar.
	 */
	public void insertarUsuario (DTOUsuario dto) {

		Usuario usuario = new Usuario (dto);
		usuarios.put(dto.getNombreUser(), usuario);

	}
	
	/**
	 *@param nombreTabla nombre de una tabla.
	 *@return Clave primaria de la tabla especificada.
	 */
	public String obtenerClave (String nombreTabla) {

		return this.obtenerTabla(nombreTabla).obtenerClave();

	}
	
	/**
	 *@param nombreTabla nombre de una tabla.
	 *@return Retorna true si la tabla tiene una clave primaria, false si no la tiene.
	 */
	public boolean tieneClave (String nombreTabla) {
		
		return this.obtenerTabla(nombreTabla).tieneClave();

	}
	
	/**
	 * Realiza una consulta con 1 condición (WHERE)
	 *@param nombreTabla tabla a la que se realizará la consulta
	 *@param nombreAtributo nombre del atributo que se desea mostrar
	 *@param nombreAtributoCondicion atributo al que se le aplicará la condición
	 *@param valorCondicion valor de la condición
	 *@param operador operador lógico de la condición
	 *@return Lista de objetos DTOAtributo que representan los valores del atributo seleccionado en los registros que cumplen con la condición.
	 */
	public ArrayList<DTOAtributo> realizarConsultaClasica(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion, String operador){

		ArrayList<LinkedHashMap<String, DTOAtributo>> registros = this.obtenerTabla(nombreTabla).obtenerRegistros(nombreAtributoCondicion, valorCondicion, operador);

		return this.obtenerTabla(nombreTabla).seleccionarAtributo(registros, nombreAtributo);

	}
	
	/**
	 * Realiza una consulta sin condiciones.
	 *@param nombreTabla tabla a la que se realizará la consulta
	 *@param nombreAtributo nombre del atributo que se desea mostrar
	 *@return Lista de objetos DTOAtributo que representan los valores del atributo seleccionado en los registros que cumplen con la condición.
	 */
	public ArrayList<DTOAtributo> realizarConsultaSinWhere(String nombreTabla, String nombreAtributo){

		Tabla tablita = this.obtenerTabla(nombreTabla);
		ArrayList<DTOAtributo> registros =tablita.seleccionarAtributo(tablita.getRegistrosDTO(), nombreAtributo);
		registros.remove(0);
		
		return registros;

	}

	/**
	 * Borra los registros de la tabla especificada que cumplan con la condición establecida por el nombreAtributoCondición y el valor de condición.
	 *@param nombreTabla tabla de la que se borrarán los registros.
	 *@param nombreAtributoCondicion atributo al que se le aplicará la condición.
	 *@param valorCondicion valor de la condición.
	 *@param operador operador lógico de la condición.
	 */
	public void borrarRegistro(String nombreTabla, String nombreAtributoCondicion, String valorCondicion, String operador) {

		Tabla tabla = this.obtenerTabla(nombreTabla);
		
		ArrayList<LinkedHashMap<String, DTOAtributo>> registrosEliminar = this.obtenerTabla(nombreTabla).obtenerRegistros(nombreAtributoCondicion, valorCondicion, operador);
		
		 for (LinkedHashMap<String, DTOAtributo> registro : registrosEliminar) {
	            
			 tabla.eliminarRegistro(registro);
			 
	     }
		
	}
	
	/**
	 *Cambia el valor del atributo especificado por el nuevo valor en los registros de la tabla que cumplen con la condición especificada por el nombre del atributo de condición y el valor de condición.
	 *@param nombreTabla tabla de la que se modificarán los registros.
	 *@param atributoCambiar atributo que contiene los registros a modificar.
	 *@param valorNuevo nuevo valor de los registros modificados.
	 *@param nombreAtributoCondicion atributo al cual se le aplicarán las condiciones
	 *@param valorCondicion valor de la condición
	 *@param operador operador lógico de la condición.
	 */
	public void cambiarRegistro(String nombreTabla, String atributoCambiar, String valorNuevo, String nombreAtributoCondicion, String valorCondicion, String operador) {

		Tabla tabla = this.obtenerTabla(nombreTabla);
		ArrayList<LinkedHashMap<String, DTOAtributo>> registrosCambiar = tabla.obtenerRegistros(nombreAtributoCondicion, valorCondicion, operador);
		
		for (LinkedHashMap<String, DTOAtributo> registro : registrosCambiar) {
		
			tabla.modificarRegistros(tabla.convertirMapa(registro), atributoCambiar, valorNuevo);
			
		}
			
	}
	
	/**
	 * Metodo publico que inserta un nuevo registro en la tabla especificada en nombre de Tabla con los atributos y valores proporcionados en el mapa registro
	 * @param nombreTabla-> nombre de la tabla
	 * @param registro -> mapa correspondiente al nuevo registro a ingresar
	 */
	public void ingresarRegistro(String nombreTabla, LinkedHashMap<String, DTOAtributo> registro) {

		Tabla tabla = this.obtenerTabla(nombreTabla);

		tabla.insertarRegistro(registro);

	}

	/**
	 * Metodo privado que, dado el nombre de una tabla, retorna tabla (en caso de encontrarla) o null si no se encuentra ninguna tabla con el nombre especificado
	 * @param nombreTabla
	 * @return objeto tabla encontrado
	 */
	private Tabla obtenerTabla(String nombreTabla) {
		
		for (Map.Entry<String, Tabla> tabla : this.obtenerBaseDatos().getTablas().entrySet()) {
			
			if(tabla.getKey().equalsIgnoreCase(nombreTabla)) {
				
				return tabla.getValue();
			}
			
		}
		
	    return null;

	}

	/**
	 * Metodo publico que recibe como parametro el nombre de un atributo y el nombre de una tabla. Obtiene el valor del atributo especificado en la tabla nombreTabla y lo retorna como un objeto DTOAtributo.
	 * @param nombreTabla -> nombre de la tabla que contiene el atributo a buscar
	 * @param nombreAtributo -> nombre del atributo a buscar
	 * @return obtejo de tipo DTOAtributo correspondiente al nombreAtributo proporcionado
	 */
	public DTOAtributo obtenerAtributo(String nombreAtributo, String nombreTabla) {

		LinkedHashMap<String, DTOAtributo> guia = this.obtenerTabla(nombreTabla).getRegistrosDTO().get(0);
		
		for (Entry<String, DTOAtributo> entry : guia.entrySet()) {
			
			if(entry.getKey().equalsIgnoreCase(nombreAtributo)) {
				
				return entry.getValue();
				
			}
				
		}
		
		return null;
		
	}
	
	/**
	 * Metodo publico que recibe como parametro el nombre de una tabla, el nombre de un atributo y un valor de condicion. Valida si el valor de condicion proporcionado es valido para el tipo de atributo especificado en la tabla. Si el atributo es de tipo entero, verifica si puede ser convertido a un numero entero. Retorna: true si la condicion es valida, false si no lo es
	 * @param nombreTabla-> nombre de la tabla
	 * @param nombreAtributo-> nombre del atributo
	 * @param valorCondicion-> valor de la condicion
	 * @return verifica si la condicion es valida o no
	 */
	public boolean validaCondicion(String nombreTabla, String nombreAtributo, String valorCondicion) {
		
	    return this.obtenerTabla(nombreTabla).validaCondicion(nombreAtributo, valorCondicion);
	    
	}
	
	/**
	 * Metodo publico que recibe como parametro el nombre de una tabla y el nombre de un atributo. Obtiene el tipo del atributo especificado en el nombre del atributo en la tabla y retorna una cadena de texto que representa el tipo de atributo
	 * @param nombreTabla-> nombre de la tabla
	 * @param nombreAtributo-> nombre del atributo
	 * @return el tipo de atribtuo que tiene el atirbuto en la tabla que se recibe
	 */
	public String obtenerTipoAtributo(String nombreTabla, String nombreAtributo) {
		
		String nombre = this.obtenerAtributo(nombreAtributo, nombreTabla).getNombreAtributo();
		return this.obtenerTabla(nombreTabla).obtenerTipo(nombre);
		
	}
	
	/**
	 * Metodo publico que recibe el nombre de una tabla y verifica si la misma esta vacia, es decir, si no contiene registros. Retorna true si la tabla esta vacia, false si contiene registros.
	 * @param nombreTabla-> nombre de la tabla
	 * @return si la tabla es vacia o no
	 */
	public boolean esVacia(String nombreTabla) { //retorna true si no tiene
		
		return this.obtenerTabla(nombreTabla).esVacia();
		
	}
	
	/**
	 * Metodo publico que recibe como parametros el nombre de una tabla, el nombre de un atributo y un valor de condicion. Verifica si existen registros en la tabla que cumplan con la condicion especificada por el nombre del atributo y el valor de condicion. Retorna true si hay registros que cumplen la condicion, false si no los hay
	 * @param nombreTabla-> nombre de la tabla
	 * @param nombreAtributo-> nombre del atributo
	 * @param valorCondicion-> valor de la condicion
	 * @param operador-> operador de la condicion
	 * @return verifica si hay registros que correspondan con la condicion
	 */
	public boolean hayRegistros(String nombreTabla, String nombreAtributo, String valorCondicion, String operador) {
		
		return !(this.obtenerTabla(nombreTabla).obtenerRegistros(nombreAtributo, valorCondicion, operador).isEmpty());
		
	}
	
	/** 
	 * Metodo publico que recibe como parametros el nombre de la tabla y el nombre del atributo en los que se buscara el valor minimo. El metodo retorna un entero que es el valor minimo encontrado como entero.
	 * @param nombreTabla-> nombre de la tabla
	 * @param nombreAtributo -> nombre del atributo
	 * @return el valor minimo del atributo
	 */
	public Integer obtenerMinimo(String nombreTabla, String nombreAtributo) {
		
		return this.obtenerTabla(nombreTabla).obtenerMinimo(nombreAtributo);
	
	}

	/**
	 * Metodo publico que recibe como parametros el nombre de la tabla y el nombre del atributo en los que se buscara el valor maximo. El metodo retorna el valor maximo encontrado como un entero
	  * @param nombreTabla-> nombre de la tabla
	 * @param nombreAtributo -> nombre del atributo
	 * @return el valor maximo del atributo
	 */
	public Integer obtenerMaximo(String nombreTabla, String nombreAtributo) {

		return this.obtenerTabla(nombreTabla).obtenerMaximo(nombreAtributo);
	    
	}
	
    /**
     * Metodo publico que recibe como parametros el nombre de la tabla y el nombre del atributo al que se le establecera la restriccion NOT NULL
     * @param nombreTabla-> nombre de la tabla
     * @param nombreAtributo-> nombre del atributo
     */
	public void hacerNotNull (String nombreTabla, String nombreAtributo) {
		
		String nombre = this.obtenerAtributo(nombreAtributo, nombreTabla).getNombreAtributo();
		this.obtenerTabla(nombreTabla).getRegistros().get(0).get(nombre).setNulo(true);
					
	}
	
	/**
	 * Comprueba si un atributo de una tabla especifica no es nulo en su primera fila de registros.
	 * @param nombreTabla -> El nombre de la tabla a verificar.
	 * @param nombreAtributo -> El nombre del atributo a comprobar.
	 * @return true si el atributo no es nulo en la primera fila de registros, false de lo contrario.
	 */
	public boolean esNotNull (String nombreTabla, String nombreAtributo) {
		
		String nombre = this.obtenerAtributo(nombreAtributo, nombreTabla).getNombreAtributo();
		return this.obtenerTabla(nombreTabla).getRegistros().get(0).get(nombre).getNulo();
					
	}
	
	/**
	 * Metodo publico que recibe como parametros el nombre de la tabla y el nombre del atributo que se establecera como clave primaria y se aplicara asimismo la restriccion NOT NULL
	 * @param nombreTabla-> nombre de la Tabla
	 * @param nombreAtributo-> nombre del atributo
	 */
	public void hacerClave (String nombreTabla, String nombreAtributo) {
		
		String nombre = this.obtenerAtributo(nombreAtributo, nombreTabla).getNombreAtributo();
		this.obtenerTabla(nombreTabla).getRegistros().get(0).get(nombre).setClave(true);
		hacerNotNull(nombreTabla, nombre);
				
	}
	
	/**
	 * Metodo publico que recibe como parametro el nombre de la tabla en la que se eliminara la restriccion de clave primaria
	 * @param nombreTabla-> nombre de la tabla
	 */
	public void quitarClave (String nombreTabla) {
		
		String clave = this.obtenerTabla(nombreTabla).obtenerClave();
		this.obtenerTabla(nombreTabla).getRegistros().get(0).get(clave).setClave(false);
		this.obtenerTabla(nombreTabla).getRegistros().get(0).get(clave).setNulo(false);;
		
	}
	
	/**
	 * Metodo publico que recibe como parametros el nombre de la tabla, el nombre del atributo, el nombre del atributo de condicion y el valor de condicion. El metodo calcula el promedio de los valores de un atributo especifico de registros que cumplan con una condicion dada y retorna el promedio de esos valores como un numero decimal.
	 * @param nombreTabla-> nombre de la tabla
	 * @param nombreAtributo-> nombre del atributo
	 * @param nombreAtributoCondicion-> el nombre de la condicion
	 * @param valorCondicion-> valor de la condicion
	 * @param operador-> operador
	 * @return el promedio de los valores que corresponden a la condicion
	 */
	public double calcularPromedioRegistros(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion, String operador) {
	    
		return this.obtenerTabla(nombreTabla).calcularPromedioRegistros(nombreAtributo, nombreAtributoCondicion, valorCondicion, operador);
	    
	}
	
	/**
	 * Metodo publico que recibe como parametros el nombre de la tabla, el nombre del atributo de consulta, dos conjuntos de nombre de atributo y valor de condicion. El metodo realiza una consulta OR en la tabla especificada, buscando registros que cumplan cualquiera de las dos condiciones y retorna una lista de objetos DTOAtributo que representan los registros encontrados despues de aplicar la consulta OR, la que no incluye elementos duplicados.
	 * @param nombreTabla -> nombre de la tabla
	 * @param nombreAtributo -> nombre del atributo
	 * @param nombreAtributoCondicion1 -> nombre de la condion del atributo 1
	 * @param valorCondicion1 ->  valor de la condicion del atributo 1
	 * @param nombreAtributoCondicion2 -> nombre de la condion del atributo 2
	 * @param valorCondicion2 ->  valor de la condicion del atributo 2
	 * @return lista de registros que cumplan con al menos una  condicion
	 */
	public ArrayList<DTOAtributo> consultaOr(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion1, String valorCondicion1, String nombreAtributoCondicion2, String valorCondicion2, String operador1, String operador2) {
		
		String nomTabla = this.obtenerTabla(nombreTabla).getNombreTabla();
		String nomAtr1 = obtenerAtributo(nombreAtributoCondicion1, nomTabla).getNombreAtributo();
		String nomAtr2 = obtenerAtributo(nombreAtributoCondicion2, nomTabla).getNombreAtributo();
		
		return obtenerTabla(nombreTabla).consultaOr(nombreAtributo, nomAtr1, valorCondicion1, nomAtr2, valorCondicion2, operador1, operador2);
		
	}
	
	/**
	  * Metodo publico que recibe como parametro el nombre de una base de datos y retorna true si la misma existe para el usuario actual y false en caso contrario
	  * @return verifica si existe la base de datos
	  */
	public boolean existeBD(String nombreBD) {
		
		for (String nombre : this.obtenerUsuario().getBasesDatos().keySet()) {
			
	        if (nombre.equalsIgnoreCase(nombreBD)) {
	           
	        	return true;
	        
	        }
	        
	    }

	    return false;
		
	}

	/**
	 * Metodo publico que recibe como parametro el nombre de una tabla y retorna true si la misma existe para la base de datos seleccionada y false en caso contrario
	 * @param nombreTabla-> nombre de la tabla
	 * @return verifica si existe la tabla
	 */
	public boolean existeTabla(String nombreTabla) {

		Tabla tablita = obtenerTabla(nombreTabla);
	
		if (tablita != null){ 
			
			return true;
			
		}else {
			
			return false;
		
		}
		
	}
	
	/**
	 * Metodo publico que recibe como parametro un objeto DTOBaseDatos que representa la informacion de la nueva base de datos y la agrega al usuario actual.
	 * @param base-> DTOBaseDatos 
	 */
	public void crearBD(DTOBaseDatos base) {
		
		Usuario usuarioActual = obtenerUsuario();
		BaseDatos baseDatos = new BaseDatos(base);
		usuarioActual.agregarBD(baseDatos);
		
	}
	
	/**
	 * Metodo publico que recibe como parametro el nombre de un usuario y retorna true si el usuario existe en la coleccion de usuario y false en caso contrario.
	 * @param nombreUsuario-> nombre del usuario
	 * @return verifica si el usuario existe
	 */
	public boolean existeUsuario(String nombreUsuario) {
		
		if (usuarios.containsKey(nombreUsuario)) {
			
			return true;
		
		}else {
			
			return false;
		
		}
		
	}
	
	/**
	 * Metodo publico que recibe como parametro un objeto DTOTabla que representa la informacion de la nueva tabla y un mapa de atributos. El metodo crea una nueva tabla en la base de datos seleccionada y agrega registros con los atributos especificados.
	 */
	public void crearTabla(DTOTabla tabla, LinkedHashMap<String, String> atributos) {
		
		Tabla tablita = new Tabla(tabla);
		this.obtenerBaseDatos().insertarTabla(tablita);
		tablita.insertarRegistro(tablita.generarAtributos(atributos));

	}
	
	/**
	 * Metodo publico que retorna una lista de cadenas de caracteres que representa los nombres de todas las tablas en la base de datos seleccionada
	 * @return lista con los nombres de la tabla
	 */
	public ArrayList<String> obtenerTablasNom() {
		
		return this.obtenerBaseDatos().obtenerNomTablas();
		
	}
	
	/**
	 * Metodo publico que recibe un objeto DTOUsuario que contiene el nombre de usuario y la contrasena a validar. El metodo retorna true si la contrasena ingresada es valida para el usuario especificado y false en caso contrario.
	 * @param usu->DTOUsuario
	 * @return valida si la contrase�a que se ingreso es compatible con el usuario
	 */
	public boolean validarContrasenia (DTOUsuario usu) {
		
		String nombre = usu.getNombreUser();
		String contrasenia = usu.getContrasenia();
		
		if(usuarios.get(nombre).getContrasenia().equals(contrasenia)) {
			
			return true;
		
		}else {
			
			return false;
		
		}
		
	}
	
	/**
	 * Metodo publico que recibe como parametros el nombre de la tabla, el nombre del atributo a consultar, dos nombres de atributos de condicion y dos valores de condicion. El metodo retorna una lista de objetos DTOAtributo que contiene solo aquellos que cumplen simultaneamente ambas condiciones.
	 *  @param nombreTabla -> nombre de la tabla
	 * @param nombreAtributo -> nombre del atributo
	 * @param nombreAtributoCondicion1 -> nombre de la condion del atributo 1
	 * @param valorCondicion1 ->  valor de la condicion del atributo 1
	 * @param nombreAtributoCondicion2 -> nombre de la condion del atributo 2
	 * @param valorCondicion2 ->  valor de la condicion del atributo 2
	 * @return lista de registros que cumplan ambas condiciones
	 */
	public ArrayList<DTOAtributo> consultaAnd (String nombreTabla, String nombreAtributo, String nombreAtributoCondicion1, String valorCondicion1, String nombreAtributoCondicion2,String valorCondicion2, String operador1, String operador2) {
    	
		String nomTabla = this.obtenerTabla(nombreTabla).getNombreTabla();
		String nomAtr1 = obtenerAtributo(nombreAtributoCondicion1, nomTabla).getNombreAtributo();
		String nomAtr2 = obtenerAtributo(nombreAtributoCondicion2, nomTabla).getNombreAtributo();
		
		return obtenerTabla(nombreTabla).consultaAnd(nombreAtributo, nomAtr1, valorCondicion1, nomAtr2, valorCondicion2, operador1, operador2);
		
    }
	
	/**
	 * Metodo publico que recibe como parametros el nombre de la tabla, un atributo de condicion y su valor. El metodo retorna la cantidad de registros que cumplen con la condicion
	 *  @param nombreTabla -> nombre de la tabla
	 * @param nombreAtributoCondicion -> nombre de la condion del atributo 1
	 * @param valorCondicion ->  valor de la condicion del atributo 1
	 * @param nombreAtributoCondicion2 -> nombre de la condion del atributo 2
	 * @return  la cantidad de registros que cumplan con la condicion
	 */
	public int contarRegistros( String nombreTabla, String nombreAtributoCondicion, String valorCondicion, String operador){
		 
		ArrayList<LinkedHashMap<String, DTOAtributo>> resultado1 = new ArrayList<LinkedHashMap<String, DTOAtributo>>();
		Tabla tablita = this.obtenerTabla(nombreTabla);
		resultado1= tablita.obtenerRegistros(nombreAtributoCondicion, valorCondicion, operador);	
		
		return resultado1.size();
	
	}

	/**
	 * Metodo publico que recibe como parametro el nombre de un comando y retorna descripcion del mismo como una cadena de caracteres
	 * @param comando-> comando del cual se requiere la ayuda
	 * @param la ayuda relacionada con el comando
	 */
	public String darAyuda(String comando) {
	
		return ayuda.get(comando.toUpperCase());

	}


	/**
	 * Metodo publico que recibe como parametro el nombre de la tabla y una lista de atributos. El metodo valida si los atributos proporcionados son validos en la tabla especificada segun su tipo y condiciones y retorna true en caso afirmativo, o false si al menos uno de ellos no lo es
	 * @param nombreTabla-> nombre de la tabla
	 * @param atributos-> lista de atributos
	 * @return valida los atributos son los correctos en la tabla
	 */
	public boolean validaAtributos(String nombreTabla, ArrayList<String> atributos) {
		
		return this.obtenerTabla(nombreTabla).validaAtributos(atributos);
	}
	
	/**
	 * Metodo publico que recibe como parametro el nombre de un comando. El metodo verifica si el comando especificado existe en el mapa de ayuda y retorna true en caso afirmativo y false en caso contrario.
	 * @param comando para verificar si existe
	 * @return valida la existencia del comando
	 */
	public boolean comandoExiste(String comando) {
		
		return this.ayuda.containsKey(comando.toUpperCase());
		
	}
	
	/**
	 * Metodo publico que retorna una lista de cadenas de caracteres que representa los nombres de todas las bases de datos del usuario actual.
	 * @return el nombre de las bases de datos de un usuario
	 */
	public ArrayList<String> obtenerBasesNom(){
		
		ArrayList <String> aux = new ArrayList<String>();
		LinkedHashMap <String,BaseDatos> bdsAux =usuarios.get(usuario).getBasesDatos();
		
		for (Entry<String, BaseDatos> Entry : bdsAux.entrySet()) {
			
			String nom = Entry.getValue().getNombreBD();
			aux.add(nom);
		
		}
		
		return aux;
		
	}
	
	/**
	 * Metodo publico que recibe el nombre de una tabla y una lista de atributos. El metodo llama al metodo en tabla que genera un registro a partir de los atributos proporcionados y lo retorna como un mapa de atributos.
	 * @param nombreTabla -> nombre de la tabla
	 * @param atributos -> lista de atributos
	 * @return  mapa de registos a partir del nombre de la tabla y de los atributos
	 */
	public LinkedHashMap<String, DTOAtributo> generarArrayListRegistro(String nombreTabla, ArrayList<String> atributos){
		
		return this.obtenerTabla(nombreTabla).generarArrayListRegistro(atributos);
		
	}
    	
	/**
	 * Metodo publico que recibe tres parametros: nombre de la primera tabla, nombre de la segunda tabla y el nombre del atributo de busqueda. El metodo realiza una operacion de join natural entre las dos tablas en funcion del atributo de busqueda y retorna una lista de objetos DTOAtributo
	 * @param tabla1-> Tabla 1 a buscar
	 * @param tabla2-> Tabla 2 a buscar
	 * @param busqueda-> atributo a buscar 
	 * @return Lista que cumplan el JOIN NATURAL de ambas tablas
	 */
	public ArrayList<DTOAtributo> joinNatural(String tabla1, String tabla2, String busqueda){
		
		return this.obtenerBaseDatos().joinNatural(tabla1, tabla2, busqueda);
		
	}
	
	/**
	 * Metodo publico que recibe como parametro el nombre de una tabla y retorna una lista de cadenas que describen las caracteristicas de los atributos de la tabla
	 * @param nombreTabla-> nombre de la tabla
	 * @return Lista con la descripcion de los atributos de la tabla
	 */
	public ArrayList<String> describeTabla (String nombreTabla){

		return this.obtenerTabla(nombreTabla).describeTabla();
		
	}
	
	/**
	 * Metodo publico que recibe como parametros los nombres de dos tablas. El metodo busca los atributos de las dos tablas y compara si hay alguno en comun entre ellas. Si encuentra al menos un atributo comun, retorna true de lo contrario retorna false
	 * @param tabla1-> Tabla 1 a buscar
	 * @param tabla2-> Tabla 2 a buscar
	 * @return verifica si las tablas tienen algun atributo en comun
	 */
	public boolean validaAtributosJoin (String tabla1, String tabla2) {
		
		Tabla tablita1 = this.obtenerTabla(tabla1);
		Tabla tablita2 = this.obtenerTabla(tabla2);
		
		if (tablita1.getRegistros().size()>0 && tablita2.getRegistros().size()>0) {
			
			LinkedHashMap <String, Atributo> guia1 = tablita1.getRegistros().get(0);
			LinkedHashMap <String, Atributo> guia2 = tablita2.getRegistros().get(0);
			
	        for (Map.Entry<String, Atributo> entry : guia1.entrySet()) {
	        	
	            Atributo atributoGuia1 = entry.getValue();
	            	
	            for (Map.Entry<String, Atributo> entry2 : guia2.entrySet()) {
	            	
	            	Atributo atributoGuia2 = entry2.getValue();
	            	
            		if(atributoGuia2.equals(atributoGuia1)) {
            			
            			return true;
            		
            		}
            		
	            }
	            
	        }
	        
	    }
		
        return false;
		
	}
	
	/**
	 * Metodo publico que  retorna el valor almacenado en la variable baseDatos de la presente clase
	 * @return Valor almacenado en la variable baseDatos de la clase FachadaLogica
	 */
	public String getBaseDatos() {
		
		return this.baseDatos;
		
	}
	
	/**
	 * Metodo publico que  retorna el valor almacenado en la variable demoHabilitada de la presente clase
	 * @return Valor almacenado en la variable demoHabilitada de la clase FachadaLogica
	 */
	public int getDemoHabilitada() {

		return this.demoHabilitada;

	}

	/**
	 * Metodo publico que establece el valor de la variable demoHabilitada en la clase FachadaLogica.
	 * @param demoHabilitada El nuevo valor que se desea asignar a la variable demoHabilitada.
	 */
	public void setDemoHabilitada(int demoHabilitada) {

		this.demoHabilitada = demoHabilitada;

	}

	/**
	 * Metodo publico que, dado un nombre de una base de datos, la elimina del mapa correspondiente
	 * @param nombreBD -> nombre de la base de datos a eliminar
	 */
	public void eliminarBD(String nombreBD) {
		
		this.obtenerUsuario().eliminarBD(nombreBD);
		persistencia.eliminarCarpetaUsuarioBaseTabla(usuario, nombreBD, null);
		
	}
	
	/**
	 * Metodo publico que, dado un nombre de una tabla, la elimina del mapa correspondiente
	 * @param nombreTabla -> nombre de la tabla a eliminar
	 */
	public void eliminarTabla(String nombreTabla) {
		
		this.obtenerBaseDatos().eliminarTabla(nombreTabla);
		persistencia.eliminarCarpetaUsuarioBaseTabla(usuario, baseDatos, nombreTabla);
		
	}
	
	/**
	 * Metodo publico que, dado un nombre de una tabla, obtiene el nombre de la tabla correspondiente tal cual esta en el programa almacenado.
	 * @param nombreTabla -> El nombre de la tabla cuyo nombre se desea obtener.
	 * @return El nombre de la tabla correspondiente como esta almacenado.
	 */
	public String obtenerNomTabla (String nombreTabla) {
		
		Tabla tablita = this.obtenerTabla(nombreTabla);
		return tablita.getNombreTabla();
		
	}
	
	/**
	 * Metodo publico que recibe el nombre de una tabla y una lista de atributos. El metodo hace uso de valida cantidad de atributos de Tabla
	 * @param nombreTabla -> nombre de la tabla
	 * @param atributos -> lista de atributos
	 * @return  validacion de la cantidad de atributos
	 */
	public boolean validaCantidadAtributos(String nombreTabla, ArrayList<String> atributos) {
		
		return this.obtenerTabla(nombreTabla).validaCantidadAtributos(atributos);
	
	}

	/**
	 * Metodo publico que recibe el nombre de una tabla y una lista de atributos. El metodo hace uso de valida not null  de Tabla
	 * @param nombreTabla -> nombre de la tabla
	 * @param atributos -> lista de atributos
	 * @return  validacion not null
	 */
	public boolean validaNotNull(String nombreTabla, ArrayList<String> atributos) {
		
		return this.obtenerTabla(nombreTabla).validaNotNull(atributos);
	
	}
	
	/**
	 * Metodo publico que recibe el nombre de una tabla y una lista de atributos. El metodo hace uso de valida clave de Tabla
	 * @param nombreTabla -> nombre de la tabla
	 * @param atributos -> lista de atributos
	 * @return  validacion de la clave
	 */
	public boolean validaClave(String nombreTabla, ArrayList<String> atributos) {
		
		return this.obtenerTabla(nombreTabla).validaClave(atributos);
	
	}
	
	/**
	 * Verifica si una lista de atributos contiene unicamente valores nulos.
	 *
	 * @param atributos -> La lista de atributos a verificar.
	 * @return true si todos los atributos son nulos, false si al menos uno de ellos no es nulo.
	 */
	public boolean validaRegistroVacio(ArrayList<String> atributos) {
		
		int i=0;
		
		for (String dato : atributos) {
			
			if(dato.equalsIgnoreCase("NULL")) {
				
				i++;
				
			}
			
		}
		
		return (i==atributos.size());
		
	}
	
}
