package logica;

import java.util.LinkedHashMap;

import comunicacion.DTOUsuario;

public class Usuario {

	private String nombreUser;
	private String contrasenia;
	private LinkedHashMap<String, BaseDatos> basesDatos;

	public Usuario(DTOUsuario usuario) {

		this.nombreUser = usuario.getNombreUser();
		this.contrasenia = usuario.getContrasenia();
		basesDatos = new LinkedHashMap<String, BaseDatos>();

	}

	public Usuario(String nombreUser, String contrasenia) {
		
		this.nombreUser = nombreUser;
		this.contrasenia = contrasenia;
		this.basesDatos = null; // Establece el mapa de bases de datos como null
	}

	public String getNombreUser() {

		return nombreUser;

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

	public LinkedHashMap<String, BaseDatos> getBasesDatos() {

		return basesDatos;

	}

	public void setBasesDatos(LinkedHashMap<String, BaseDatos> basesDatos) {

		this.basesDatos = basesDatos;

	}

}
