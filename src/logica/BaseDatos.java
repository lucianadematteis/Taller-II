package logica;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public void insertarTabla(Tabla tabla) {
        
       tablas.put(tabla.getNombreTabla(), tabla);
       
    }
	
	public Tabla obtenerTabla(String nombreTabla) {
		
	    if (tablas.containsKey(nombreTabla)) {
	    	
	        return tablas.get(nombreTabla);
	        
	    }else{
	      
	        return null;
	        
	    }
	    
	}
	
	public ArrayList<String> obtenerNomTablas(){
		
		ArrayList<String> tablasNom = new ArrayList<String>();
		
		for (Entry<String, Tabla> tabla : tablas.entrySet()) {
			
			tablasNom.add(tabla.getKey());
			
		}
		
		return tablasNom;
		
	}
	
	public void agregarTabla(Tabla tabla) {
		
		this.tablas.put(tabla.getNombreTabla(), tabla);
		
	}

}