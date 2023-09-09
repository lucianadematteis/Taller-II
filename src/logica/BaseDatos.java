package logica;

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
	
	//Dai bool buscar en tablas objeto tabla
	public boolean buscarTabla(String nombreTabla) {	
		return tablas.get(nombreTabla) != null; 
	}
	
	public void insertarTabla(LinkedHashMap<String, Tabla> tablita, String tablon) {
        
        Tabla nuevaTabla = new Tabla(); 
        
        tablita.put(tablon, nuevaTabla);
    }
	
	 public Tabla obtenerTabla(LinkedHashMap<String, Tabla> tablita,String nombreTabla) {
	       
	        if (tablita.containsKey(nombreTabla)) {
	        	
	            return tablita.get(nombreTabla); 
	        else {
	        	
	        	System.out.println("La tabla no existe");
	    }

}
