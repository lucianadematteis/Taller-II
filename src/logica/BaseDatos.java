package logica;

import java.util.Map;

public class BaseDatos {

	private String nombreBD;
	private Map <String, Tablas> tablas;
	
	
	public BaseDatos(String nombreBD, Map<String, Tablas> tablas) {
		this.nombreBD = nombreBD;
		this.tablas = tablas;
	}


	public String getNombreBD() {
		return nombreBD;
	}


	public void setNombreBD(String nombreBD) {
		this.nombreBD = nombreBD;
	}



	
	
}
