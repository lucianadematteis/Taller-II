package logica;

import java.util.HashMap;
import java.util.Map;

import comunicacion.DTOUsuario;

public class Usuario {
	
	private String nombreUser;
	private String contrasenia;
	private Map<String, BaseDatos> basesDatos;
	
	public Usuario(DTOUsuario usuario) {
		
		this.nombreUser = usuario.getNombreUser();
		this.contrasenia = usuario.getContrasenia();
		basesDatos = new HashMap<String, BaseDatos>();
		
	}
	
	public Usuario(String nombreUser, String contrasenia) {
		
		this.nombreUser = nombreUser;
		this.contrasenia = contrasenia;
		basesDatos = new HashMap<String, BaseDatos>();
		
	}

	public String getNombreUser() {
		
		return nombreUser;
		//Usaadasdasd
		
	}

	public String getContrasenia() {
		
		return contrasenia;
		
	}

	public void setNombreUser(String nombreUser) {
		
		this.nombreUser = nombreUser;
		
	}

	public void setContrasenia(String contrasenia) {
		
		this.contrasenia = contrasenia;
		
	}

	public Map<String, BaseDatos> getBasesDatos() {
		
		return basesDatos;
		
	}

	public void setBasesDatos(Map<String, BaseDatos> basesDatos) {
		
		this.basesDatos = basesDatos;
		
	}

}
