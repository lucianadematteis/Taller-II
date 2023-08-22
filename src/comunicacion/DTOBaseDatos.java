package comunicacion;

public class DTOBaseDatos {
	
	private String nombreBD;

	public DTOBaseDatos(String nombreBD) {
		
		this.nombreBD = nombreBD;
		
	}

	public String getNombreBD() {
		
		return nombreBD;
		
	}

	public void setNombreBD(String nombreBD) {
		
		this.nombreBD = nombreBD;
		
	}

}
