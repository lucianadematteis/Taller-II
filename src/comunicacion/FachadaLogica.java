package comunicacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import logica.Atributo;
import logica.Tabla;
import logica.Usuario;
import persistencia.Persistencia;

public class FachadaLogica implements IFachadaLogica {

	Persistencia persistencia = new Persistencia();
	private String baseDatos;
	private String usuario;
	private LinkedHashMap<String, Usuario> usuarios;
	private LinkedHashMap<String, String> ayuda;

	public FachadaLogica() {

		baseDatos = "";
		usuario = "";
		usuarios = new LinkedHashMap<String, Usuario>();
		ayuda = new LinkedHashMap<String, String>();

	}

	public String getBaseDatos() {

		return baseDatos;

	}

	public void seleccionarBaseDatos(String baseDatos) {

		this.baseDatos = baseDatos;

	}

	public void liberarMemoriaBaseDatos() {

		this.baseDatos = "";

	}
	
	public boolean bdSeleccionada() {
		
		return !(this.baseDatos.isEmpty());
		
	}

	public void liberarMemoriaUsuario() {

		this.usuario = "";

	}

	public String getUsuario() {

		return usuario;

	}

	public void seleccionarUsuario(String usuario) {

		this.usuario = usuario;

	}

	public LinkedHashMap<String, Usuario> getUsuarios() {

		return usuarios;

	}

	public void setUsuarios(LinkedHashMap<String, Usuario> usuarios) {

		this.usuarios = usuarios;

	}

	public Map<String, String> getAyuda() {

		return ayuda;

	}

	public void setAyuda(LinkedHashMap<String, String> ayuda) {

		this.ayuda = ayuda;

	}

	public void persistirDatos() {

		persistencia.persistirTodo(usuarios);

	}

	public void recuperarDatos() {

		ayuda=persistencia.recuperarAyuda();
		persistencia.recuperarTodo(usuarios);

	}

	// Dai mod usuario a partir de un dto usuario
	public void modificarUsuario(DTOUsuario user) {

		Usuario usAux = new Usuario(user);
		usuarios.replace(usAux.getNombreUser(), usAux);

	}

	public void eliminarusuario(DTOUsuario user) {

		usuarios.remove(user.getNombreUser()); //Asi tambien funciona porque solo necesitas la cedula que es la key

	}

	public Usuario obtenerUsuario() {

		if (usuarios.containsKey(usuario)) {

			return usuarios.get(usuario);

		}else{

			return null ;
		}

	}

	public int obtenerCantidad(ArrayList<Integer> valores) {

		return valores.size();

	}

	public int obtenerMaximo(ArrayList<Integer> valores) {

		int maximo = valores.get(0);

		for (int i = 1; i < valores.size(); i++) {

			if (valores.get(i) > maximo) {

				maximo = valores.get(i);

			}

		}

		return maximo;

	}

	public int obtenerMinimo(ArrayList<Integer> valores) {

		int minimo = valores.get(0);

		for (int i = 1; i < valores.size(); i++) {

			if (valores.get(i) < minimo) {

				minimo = valores.get(i);

			}
		}

		return minimo;

	}

	// recibe un arraylist con los valores y devuelve el promedio
	public int obtenerPromedio(ArrayList<Integer> valores) {

		int suma = 0;

		for (int i = 0; i < valores.size(); i++) {

			suma += valores.get(i);

		}

		int promedio = suma / valores.size();

		return promedio;

	}

	public void insertarUsuario (DTOUsuario dto) {

		Usuario usuario = new Usuario (dto);
		usuarios.put(dto.getNombreUser(), usuario);

	}

	public String obtenerClave (String nombreTabla) {

		return this.obtenerTabla(nombreTabla).obtenerClave();

	}
	
	public boolean tieneClave (String nombreTabla) {

		return this.obtenerTabla(nombreTabla).tieneClave();

	}

	public ArrayList<String> obtenerNotNull (String nombreTabla){

		return this.obtenerTabla(nombreTabla).obtenerNotNull();

	}

	public ArrayList<Atributo> realizarConsultaClasica(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion){

		ArrayList<LinkedHashMap<String, Atributo>> registros = this.obtenerTabla(nombreTabla).obtenerRegistros(nombreAtributoCondicion, valorCondicion);

		return this.obtenerTabla(nombreTabla).seleccionarAtributo(registros, nombreAtributoCondicion);

	}

	public void borrarRegistro(String nombreTabla, String nombreAtributoCondicion, String valorCondicion) {

		Tabla tabla = this.obtenerTabla(nombreTabla);

		ArrayList<LinkedHashMap<String, Atributo>> registrosEliminar = this.obtenerTabla(nombreTabla).obtenerRegistros(nombreAtributoCondicion, valorCondicion);

		for (LinkedHashMap<String, Atributo> registro : registrosEliminar) { 

			tabla.eliminarRegistro(registro);

		}

	}

	public void cambiarRegistro(String nombreTabla, String atributoCambiar, String valorNuevo, String nombreAtributoCondicion, String valorCondicion) {

		Tabla tabla = this.obtenerTabla(nombreTabla);

		ArrayList<LinkedHashMap<String, Atributo>> registrosCambiar = tabla.obtenerRegistros(nombreAtributoCondicion, valorCondicion);

		tabla.modificarRegistro(registrosCambiar, nombreAtributoCondicion, valorNuevo);

	}

	public void ingresarRegistro(String nombreTabla, LinkedHashMap<String, Atributo> registro) {

		Tabla tabla = this.obtenerTabla(nombreTabla);

		tabla.insertarRegistro(registro);

	}

	//recibe un nombre de tabla, busca en tabla y devuelve la tabla correspondiente
	public Tabla obtenerTabla(String nombreTabla) {

		Tabla aux = new Tabla("");	

		aux= usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla);

		return aux;
	}

	public Atributo obtenerAtributo(String nombreAtributo, String nombreTabla) {

		//debo crear un control por si no existe el atributo? o se encarga otro m�todo de eso?
		Atributo atr =usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).getRegistros().get(0).get(nombreAtributo);

		return atr;
	}
	
	public boolean validaCondicion(String nombreTabla, String nombreAtributo, String valorCondicion) {
		
	    String tipoAtributo = this.obtenerTabla(nombreTabla).obtenerTipo(nombreAtributo);

	    if ("entero".equals(tipoAtributo)) {
	    	
	        try {
	        	
	            Integer.parseInt(valorCondicion); // Si esto tiene éxito es porque es numérico
	            return true;
	            
	        } catch (NumberFormatException e) {
	        	
	            return false;
	            
	        }
	        
	    }

	    return true; // Si el tipo no es "entero", asumimos que cualquier valor de cadena es válido
	    
	}
	
	public String obtenerTipoAtributo(String nombreTabla, String nombreAtributo) {
		
		return this.obtenerTabla(nombreTabla).obtenerTipo(nombreAtributo);
		
	}
	
	public boolean esVacia(String nombreTabla) { //retorna true si no tiene
		
		return this.obtenerTabla(nombreTabla).esVacia();
		
	}
	
	public boolean hayRegistros(String nombreTabla, String nombreAtributo, String valorCondicion) {
		
		return !(this.obtenerTabla(nombreTabla).obtenerRegistros(nombreAtributo, valorCondicion).isEmpty());
		
	}
	/*
	public ArrayList<String> obtenerTablasNom(){
		
		return this.obtenerBaseDatos.obtenerNomTablas();
		
	}
	*/
}
