package comunicacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
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

}
