package logica;

import java.util.Map;


public class Usuario {
	
	private String nombreUser;
	private String contrasenia;
	private Map<String, BasesDatos> basesDatos;
	
	
	public Usuario(String nombreUser, String contrasenia, Map<String, BasesDatos> basesDatos) {
		this.nombreUser = nombreUser;
		this.contrasenia = contrasenia;
		this.basesDatos = basesDatos;
	}


	
	public String getNombreUser() {
		return nombreUser;
	}



	public String getContrasenia() {
		return contrasenia;
	}



	public Map<String, BasesDatos> getBasesDatos() {
		return basesDatos;
	}



	public void setNombreUser(String nombreUser) {
		this.nombreUser = nombreUser;
	}


	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}


	public void setBasesDatos(Map<String, BasesDatos> basesDatos) {
		this.basesDatos = basesDatos;
	}
	
	
	

}
