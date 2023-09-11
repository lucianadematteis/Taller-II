package logica;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Map;

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
	
	public ArrayList<LinkedHashMap<String, Atributo>> obtenerRegistros(String nombreAtributo, String valorCondicion) {
		
		ArrayList<LinkedHashMap<String, Atributo>> registrosObtenidos = new ArrayList<LinkedHashMap<String, Atributo>>();
		
		for (LinkedHashMap<String, Atributo> misRegistros : this.getRegistros()) { 
			
		    for (Map.Entry<String, Atributo> entrada : misRegistros.entrySet()) {
		    	
		    	if(entrada.getValue().getNombreAtributo().equals(nombreAtributo)) { //si es el atributo que debo evaluar
		    		
		    		if(entrada.getValue() instanceof Entero) { //como la condicion es un string debo de evaluar esto para convertirla
			    		
			    		int valorCondicionEntera = Integer.parseInt(valorCondicion); 
			    		
			    		Entero entradaEntera = (Entero) entrada.getValue();
			    		
			    		if(entradaEntera.getValor() == valorCondicionEntera) { //si cumple con la condicion
			    			
			    			registrosObtenidos.add(misRegistros);
			    			
			    		}
			    		
			    	}else if(entrada.getValue() instanceof Cadena){
			    		
			    		Cadena entradaCadena = (Cadena) entrada.getValue();
			    		
			    		if(entradaCadena.getDato().equals(valorCondicion)) { //si cumple con la condicion
			    			
			    			registrosObtenidos.add(misRegistros);
			    			
			    		}
			    		
			    	}
		    		
		    	}
		    	
		    }
		    
		}
         
		return registrosObtenidos;
		
	}
	public boolean buscarAtributo(String nombreAtributo) {
	 
	    for (LinkedHashMap<String, Atributo> registro : registros) {
	        
	        for (Atributo atributo : registro.values()) {
	            
	            if (atributo.getNombreAtributo().equals(nombreAtributo)) {
	                return true; 
	            }
	        }
	    }
	    return false; 
	}

	public void insertarAtributo (Atributo atributo) {
		registros.get(0).put(atributo.getNombreAtributo(), atributo); //agrega solo a la guia
		
	}
	
	public Atributo obtenerAtributo(String nombreAtributo) {
	    
	    for (LinkedHashMap<String, Atributo> registro : registros) {
	       
	        Atributo atributo = registro.get(nombreAtributo);
	        if (atributo != null) {
	            return atributo; 
	        }
	    }
	    return null; 
	}
	
	
	
}
