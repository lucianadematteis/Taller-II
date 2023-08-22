package comunicacion;

import java.util.HashMap;
import java.util.Map;
import logica.Usuario;

public class FachadaLogica implements IFachadaLogica {

	IFachadaPersistencia persitencia = new FachadaPersistencia();
	private String baseDatos;
	private String usuario;
	private String tabla;
	private Map<String, Usuario> usuarios;
	private Map<String, String> ayuda;
	
	public FachadaLogica() {
		
		baseDatos = "";
		usuario = "";
		tabla = "";
		usuarios = new HashMap<String, Usuario>();
		ayuda = new HashMap<String, String>();
		
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

	public Map<String, Usuario> getUsuarios() {
		
		return usuarios;
		
	}

	public void setUsuarios(Map<String, Usuario> usuarios) {
		
		this.usuarios = usuarios;
		
	}

	public Map<String, String> getAyuda() {
		
		return ayuda;
		
	}

	public void setAyuda(Map<String, String> ayuda) {
		
		this.ayuda = ayuda;
		
	}
	
}
