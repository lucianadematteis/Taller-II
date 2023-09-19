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
	            
	            if (atributo instanceof Entero) {
	    	    	
	    	        Entero entero = (Entero) atributo;
	    	        DTOEntero enteroConvertido = new DTOEntero(entero);
	    	        registroDTO.put(entry.getKey(), enteroConvertido);
	    	        
	    	    } else if (atributo instanceof Cadena) {
	    	    	
	    	        Cadena cadena = (Cadena) atributo;
	    	        DTOCadena cadenaConvertida = new DTOCadena(cadena);
	    	        registroDTO.put(entry.getKey(), cadenaConvertida);
	    	        
	    	    }
	            
	        }

	        registrosDTO.add(registroDTO);
	 
	    }

	    return registrosDTO;
	}
	
	public Atributo convertirDTOATributo(DTOAtributo dtoAtributo) {
		
	    if (dtoAtributo instanceof DTOEntero) {
	    	
	        DTOEntero dtoEntero = (DTOEntero) dtoAtributo;
	        return new Entero(dtoEntero.getValor());
	        
	    } else if (dtoAtributo instanceof DTOCadena) {
	    	
	        DTOCadena dtoCadena = (DTOCadena) dtoAtributo;
	        return new Cadena(dtoCadena.getDato());
	        
	    }
	    
	    return null;
	    
	}
	
	public LinkedHashMap<String, DTOAtributo> generarAtributos(LinkedHashMap<String, String> atributos) {
		
		LinkedHashMap<String, DTOAtributo> resultado = new LinkedHashMap<String, DTOAtributo>();
		
		for (Map.Entry<String, String> atr : atributos.entrySet()) {
			
			if(!(atr.getValue().equals(null)) && (!(atr.getKey().equals(null)))) {
				
				if(atr.getValue().equalsIgnoreCase("entero")) {
					
					DTOEntero atrEntero = new DTOEntero(atr.getKey(), false, false, 0);
					resultado.put(atrEntero.getNombreAtributo(), atrEntero);
					
				}else if(atr.getValue().equalsIgnoreCase("cadena")) {
					
					DTOCadena atrCadena = new DTOCadena(atr.getKey(), false, false, "");
					resultado.put(atr.getKey(), atrCadena);
					
				}
				
			}
			
		}
		
		return resultado;
		
	}

	public void setRegistros(ArrayList<LinkedHashMap<String, Atributo>> registros) {
		
		this.registros = registros;
		
	}
	
	public void insertarRegistro(LinkedHashMap<String, DTOAtributo> registro) {
		
		LinkedHashMap<String, Atributo> registroConvertido = new LinkedHashMap<String, Atributo>();
		
		for (Map.Entry<String, DTOAtributo> entry : registro.entrySet()) {
			
			Atributo resultado=this.convertirDTOATributo(entry.getValue());
			registroConvertido.put(entry.getKey(), resultado);
			
		}
		registros.add(registroConvertido);
		
	}
	
	public LinkedHashMap<String, Atributo> convertirMapa(LinkedHashMap<String, DTOAtributo> registro){
		
		LinkedHashMap<String, Atributo> registroConvertido = new LinkedHashMap<String, Atributo>();
		
		for (Map.Entry<String, DTOAtributo> entry : registro.entrySet()) {
			
			Atributo resultado=this.convertirDTOATributo(entry.getValue());
			registroConvertido.put(entry.getKey(), resultado);
			
		}
		
		return registroConvertido;
		
	}
	
	public void eliminarRegistro(LinkedHashMap<String, DTOAtributo> registro) {
		
		LinkedHashMap<String, Atributo> registroConvertido = this.convertirMapa(registro);
		
		registros.remove(registroConvertido);
		   
	}
	
	public void modificarRegistros(ArrayList<LinkedHashMap<String, Atributo>> registros, String nombreAtributo, String valorNuevo) {
		   
		for (LinkedHashMap<String, Atributo> registro : registros) {
			
			this.registros.remove(registro);
			
			for(Entry<String, Atributo> registroE : registro.entrySet()) {
			
				Atributo atributo = registroE.getValue();
				
				if(registroE.getKey().equalsIgnoreCase(nombreAtributo)) {
				
					if (atributo instanceof Entero) {
			        
						int valorEntero = Integer.parseInt(valorNuevo);
			            ((Entero) atributo).setValor(valorEntero);
			        
					} else if (atributo instanceof Cadena) {
			        
						((Cadena) atributo).setDato(valorNuevo);
			        
					}
					
					this.registros.add(registro);
				}
			
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
		
		boolean primero=true;
		
		for (LinkedHashMap<String, DTOAtributo> misRegistros : this.getRegistrosDTO()) { 
			
		    for (Map.Entry<String, DTOAtributo> entrada : misRegistros.entrySet()) {
		    	
		    	if (!primero) {
		    		
		    		DTOAtributo atributo = entrada.getValue();
		    		
		    		if(entrada.getKey().equalsIgnoreCase(nombreAtributo)){
		    		
			    		if(atributo instanceof DTOEntero) { //como la condicion es un string debo de evaluar esto para convertirla
				    		
				    		int valorCondicionEntera = Integer.parseInt(valorCondicion); 
				    		DTOEntero entradaEntera = (DTOEntero) entrada.getValue();
				    		
				    		if(entradaEntera.getValor() == valorCondicionEntera) { //si cumple con la condicion
				    			
				    			registrosObtenidos.add(misRegistros);
				    			
				    		}
				    		
				    	}else if(atributo instanceof DTOCadena){
				    		
				    		DTOCadena entradaCadena = (DTOCadena) entrada.getValue();
				    		if(entradaCadena.getDato().equalsIgnoreCase(valorCondicion)) { //si cumple con la condicion
				    			
				    			registrosObtenidos.add(misRegistros);
				    			
				    		}
				    		
				    	}
		    		
		    		}
	    		
	    		}else {
	    			
		    		primero=false;
	    			
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
		    	
		    	if(entrada.getKey().equalsIgnoreCase(nombreAtributo)) { 
		    		
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
