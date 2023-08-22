package comunicacion;

public class DTOUsuario {
	
	private String nombreUser;
	private String contrasenia;
	
	public DTOUsuario(String nombreUser, String contrasenia) {
		
		this.nombreUser = nombreUser;
		this.contrasenia = contrasenia;
		
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

}
