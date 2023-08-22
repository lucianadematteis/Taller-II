package logica;

import java.util.HashMap;
import java.util.Map;

import comunicacion.DTOBaseDatos;

public class BaseDatos {

	private String nombreBD;
	private Map<String, Tabla> tablas;
	
	public BaseDatos(DTOBaseDatos BaseDatos) {
		
		this.nombreBD = BaseDatos.getNombreBD();
		tablas = new HashMap<String, Tabla>();
		
	}

	public String getNombreBD() {
		
		return nombreBD;
		
	}

	public void setNombreBD(String nombreBD) {
		
		this.nombreBD = nombreBD;
		
	}

	public Map<String, Tabla> getTablas() {
		
		return tablas;
		
	}

	public void setTablas(Map<String, Tabla> tablas) {
		
		this.tablas = tablas;
		
	}

}
