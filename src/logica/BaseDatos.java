package logica;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import comunicacion.DTOBaseDatos;

public class BaseDatos {

	private String nombreBD;
	private LinkedHashMap<String, Tabla> tablas;
	
	public BaseDatos(DTOBaseDatos BaseDatos) {
		
		this.nombreBD = BaseDatos.getNombreBD();
		tablas = new LinkedHashMap<String, Tabla>();
		
	}
	
	public BaseDatos(String nombreBD) {
		
		this.nombreBD = nombreBD;
		tablas = new LinkedHashMap<String, Tabla>();
		
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

	public void setTablas(LinkedHashMap<String, Tabla> tablas) {
		
		this.tablas = tablas;
		
	}

}
