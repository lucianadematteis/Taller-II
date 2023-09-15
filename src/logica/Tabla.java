package logica;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Map;

import comunicacion.DTOAtributo;
import comunicacion.DTOCadena;
import comunicacion.DTOEntero;
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
	
	public ArrayList<LinkedHashMap<String, DTOAtributo>> getRegistrosDTO() {
		
	    ArrayList<LinkedHashMap<String, Atributo>> registros = this.getRegistros();
	    ArrayList<LinkedHashMap<String, DTOAtributo>> registrosDTO = new ArrayList<>();

	    for (LinkedHashMap<String, Atributo> registro : registros) {
	    	
	        LinkedHashMap<String, DTOAtributo> registroDTO = new LinkedHashMap<>();

	        for (Map.Entry<String, Atributo> entry : registro.entrySet()) {
	        	
	            Atributo atributo = entry.getValue();
	            DTOAtributo dtoAtributo = convertirAtributoATDTO(atributo); // Función de conversión unificada
	            registroDTO.put(entry.getKey(), dtoAtributo);
	       
	        }

	        registrosDTO.add(registroDTO);
	 
	    }

	    return registrosDTO;
	}

	private DTOAtributo convertirAtributoATDTO(Atributo atributo) {
		
	    if (atributo instanceof Entero) {
	    	
	        Entero entero = (Entero) atributo;
	        return new DTOEntero(entero);
	        
	    } else if (atributo instanceof Cadena) {
	    	
	        Cadena cadena = (Cadena) atributo;
	        return new DTOCadena(cadena);
	    }
	    
		return null;
	}


	public void setRegistros(ArrayList<LinkedHashMap<String, Atributo>> registros) {
		
		this.registros = registros;
		
	}
	
	public void insertarRegistro(LinkedHashMap<String, Atributo> registro) {
		
		registros.add(registro);
		
	}
	
	public void eliminarRegistro(LinkedHashMap<String, DTOAtributo> registro) {
		
		registros.remove(registro);
		
	}
	
	public void modificarRegistro(ArrayList<LinkedHashMap<String, DTOAtributo>> registros, String nombreAtributo, String valorNuevo) {
		
		for (LinkedHashMap<String, DTOAtributo> registro : registros) {
	    	
	        DTOAtributo atributo = registro.get(nombreAtributo);

            if (atributo instanceof DTOEntero) {
            	
                int valorEntero = Integer.parseInt(valorNuevo);
                ((DTOEntero) atributo).setValor(valorEntero);
                 
            } else if (atributo instanceof DTOCadena) {
            	
                ((DTOCadena) atributo).setDato(valorNuevo);
                
            }
            
	    }
		
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
	
	public ArrayList<LinkedHashMap<String, DTOAtributo>> obtenerRegistros(String nombreAtributo, String valorCondicion) {
		
		ArrayList<LinkedHashMap<String, DTOAtributo>> registrosObtenidos = new ArrayList<LinkedHashMap<String, DTOAtributo>>();
		
		for (LinkedHashMap<String, DTOAtributo> misRegistros : this.getRegistrosDTO()) { 
			
		    for (Map.Entry<String, DTOAtributo> entrada : misRegistros.entrySet()) {
		    	
		    	if(entrada.getValue().getNombreAtributo().equals(nombreAtributo)) { //si es el atributo que debo evaluar
		    		
		    		if(entrada.getValue() instanceof DTOEntero) { //como la condicion es un string debo de evaluar esto para convertirla
			    		
			    		int valorCondicionEntera = Integer.parseInt(valorCondicion); 
			    		
			    		DTOEntero entradaEntera = (DTOEntero) entrada.getValue();
			    		
			    		if(entradaEntera.getValor() == valorCondicionEntera) { //si cumple con la condicion
			    			
			    			registrosObtenidos.add(misRegistros);
			    			
			    		}
			    		
			    	}else if(entrada.getValue() instanceof DTOCadena){
			    		
			    		DTOCadena entradaCadena = (DTOCadena) entrada.getValue();
			    		
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
		 
		if  (registros.get(0).get(nombreAtributo)==null)
			return false;
		else
			return true;
	}

	public void insertarAtributo (Atributo atributo) {
		
		registros.get(0).put(atributo.getNombreAtributo(), atributo); //agrega solo a la guia
		
	}
	
	public Atributo obtenerAtributo(String nombreAtributo) {
	    
		return registros.get(0).get(nombreAtributo); 
	    
	}
	
	public ArrayList<DTOAtributo> seleccionarAtributo(ArrayList<LinkedHashMap<String, DTOAtributo>> registros, String nombreAtributo){
		
		ArrayList<DTOAtributo> registrosFinales = new ArrayList<DTOAtributo>();
		
		for (LinkedHashMap<String, DTOAtributo> misRegistros : registros) { 
			
		    for (Map.Entry<String, DTOAtributo> entrada : misRegistros.entrySet()) {
		    	
		    	if(entrada.getKey().equals(nombreAtributo)) { 
		    		
		    		DTOAtributo atributo = (DTOAtributo) entrada.getValue();
		    	
		    		registrosFinales.add(atributo);
		    		
		    	}
		    	
		    }
		    
		}
		
		return registrosFinales;
		
	}
	
	public String obtenerTipo(String nombreAtributo) {
		
		if(this.getRegistros().get(0).get(nombreAtributo) instanceof Entero) {
			
			return "entero";
			
		}else if(this.getRegistros().get(0).get(nombreAtributo) instanceof Cadena){
			
			return "cadena";
			
		}
		
		return null;
		
	}
	
	public boolean esVacia() { //retorna true si no tiene
		
		return this.getRegistros().size()==1;
		
	}
	
	
	
}
