package logica;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import comunicacion.DTOTabla;

public class Tabla {
	
	private String nombreTabla;
	private ArrayList<LinkedHashMap<String, Atributo>> registros;
	
	public Tabla(DTOTabla tabla) {
		
		this.nombreTabla = tabla.getNombreTabla();
		registros = new ArrayList<LinkedHashMap<String, Atributo>>();
		
	}
	
	public Tabla(String nombreTabla) {
		
		this.nombreTabla = nombreTabla;
		registros = new ArrayList<LinkedHashMap<String, Atributo>>();
		
	}
	
	public String getNombreTabla() {
		
		return nombreTabla;
		
	}
	
	public void setNombreTabla(String nombreTabla) {
		
		this.nombreTabla = nombreTabla;
		
	}

	public ArrayList<LinkedHashMap<String, Atributo>> getRegistros() {
		
		return registros;
		
	}

	public void setRegistros(ArrayList<LinkedHashMap<String, Atributo>> registros) {
		
		this.registros = registros;
		
	}
	
	public void insertarRegistro(LinkedHashMap<String, Atributo> registro) {
		
		registros.add(registro);
		
	}
	
	public void eliminarRegistro(LinkedHashMap<String, Atributo> registro) {
		
		registros.remove(registro);
		
	}
	
	/*public registros obtenerRegistros(String nombreAtributoCondicion, Atributo valorCondicion) {
        List<LinkedHashMap<String, Atributo>> registrosFiltrados = new ArrayList<>();

        for (LinkedHashMap<String, Atributo> registro : registros) {
            if (registro.containsKey(nombreAtributoCondicion)) {
                Atributo valorAtributo = registro.get(nombreAtributoCondicion);
                if (valorAtributo.equals(valorCondicion)) {
                    registrosFiltrados.add(registro);
                }
            }
        }

        return registrosFiltrados;
    }

	*/
	
	public boolean tieneClave() {
		
		LinkedHashMap<String, Atributo> guia=registros.get(0);
		
		for (Entry<String, Atributo> atributo : guia.entrySet()) {
			
			if(atributo.getValue().getClave()) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public String obtenerClave() {
		
		String clave="";
		
		LinkedHashMap<String, Atributo> guia=registros.get(0);
		
		for (Entry<String, Atributo> atributo : guia.entrySet()) {
			
			if(atributo.getValue().getClave()) {
				
				clave=atributo.getValue().getNombreAtributo();
				
			}
			
		}
		
		return clave;
		
	}
	
	public ArrayList<String> obtenerNotNull() {
		
		ArrayList<String> atributosNotNull = new ArrayList<String>();
		LinkedHashMap<String, Atributo> guia=registros.get(0);
		
		for (Entry<String, Atributo> atributo : guia.entrySet()) {
            	
			if(atributo.getValue().getNulo()) {
    				
				atributosNotNull.add(atributo.getValue().getNombreAtributo());
    				
			}
                
		}
         
		return atributosNotNull;
		
	}
	
	
	
}
