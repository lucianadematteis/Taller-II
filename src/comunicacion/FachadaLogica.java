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
	private String tabla;
	private LinkedHashMap<String, Usuario> usuarios;
	private LinkedHashMap<String, String> ayuda;
	
	public FachadaLogica() {
		
		baseDatos = "";
		usuario = "";
		tabla = "";
		usuarios = new LinkedHashMap<String, Usuario>();
		ayuda = new LinkedHashMap<String, String>();
		
	}

	public String getBaseDatos() {
		
		return baseDatos;
		
	}

	public void setBaseDatos(String baseDatos) {
		
		this.baseDatos = baseDatos;
		
	}

	public String getUsuario() {
		
		return usuario;
		
	}

	public void setUsuario(String usuario) {
		
		this.usuario = usuario;
		
	}

	public String getTabla() {
		
		return tabla;
		
	}

	public void setTabla(String tabla) {
		
		this.tabla = tabla;
		
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
	
	public void persistirDatos(LinkedHashMap<String, Usuario> usuarios) {
		
		persistencia.persistirTodo(usuarios);
		
	}
	
	public void recuperarDatos(LinkedHashMap<String, Usuario> usuarios) {
		
		persistencia.recuperarTodo(usuarios);
		
	}
	//Dai mod usuario a partir de un dto usuario
	public void modificarUsuario(DTOUsuario user){
		Usuario usAux = new Usuario (user);
		usuarios.replace(usAux.getNombreUser(), usAux);
	}
	
	//recibe un arraylist con los valores y devuelve el promedio
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
