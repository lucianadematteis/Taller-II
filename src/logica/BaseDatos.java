package logica;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import comunicacion.DTOAtributo;
import comunicacion.DTOBaseDatos;
import comunicacion.DTOCadena;
import comunicacion.DTOEntero;

/**
 * Esta clase representa una base de datos y contiene metodos para administrar las tablas en la misma. 
 * Se relaciona con TABLAS mediante una relacion de agregacion
 *@author Gabriel Bolsi
 *@author Brandon Cairus
 *@author Daiana Daguerre
 *@author Luciana De Matteis
 *@author Mauricio Gonzalez
 */

public class BaseDatos {

	private String nombreBD;
	private LinkedHashMap<String, Tabla> tablas;
	
	/**
	 * Constructor que recibe como parametro un objeto DTOBaseDatos que contiene informacion sobre la base de datos, inicializa el nombre de la base de datos con el nombre proporcionado en el objeto baseDatos y crea una nueva instancia de LinkedHashMap para almacenar las tablas.
	 * @param BaseDatos->DTOBaseDatos
	 */
	public BaseDatos(DTOBaseDatos BaseDatos) {
		
		this.nombreBD = BaseDatos.getNombreBD();
		tablas = new LinkedHashMap<String, Tabla>();
		
	}
	
	/**
	 * Constructor que recibe como parametro una cadena de texto que representa el nombre de la base de datos y lo utiliza para inicializar el nombre de la misma. Asimismo crea una nueva instancia de LinkedHashMap para almacenar las tablas.
	 * @param nombreBD->nombre de la base de datos
	 */
	public BaseDatos(String nombreBD) {
		
		this.nombreBD = nombreBD;
		tablas = new LinkedHashMap<String, Tabla>();
		
	}

	/**
	 * Metodo publico que retorna un String. Permite recuperar el valor del atributo nombreBD. Su proposito es proporcionar acceso controlado a los datos encapsulados dentro de un objeto.
	 * @return el nombre de la base de datos
	 */
	public String getNombreBD() {
		
		return nombreBD;
		
	}

	/**
	 * Metodo publico que retorna un LinkedHashMap que contiene las tablas de la base de datos. Su proposito es proporcionar acceso controlado a los datos encapsulados dentro de un objeto.
	 * @return las tablas de la base de datos
	 */
	public Map<String, Tabla> getTablas() {
		
		return tablas;
		
	}

	/**
	 * Metodo publico que recibe como parametro un mapa de tablas y establece el mapa de tablas de la base de datos con el mapa proporcionado por parametro.
	 * @param tablas establece las tablas a las base de datos
	 */
	public void setTablas(LinkedHashMap<String, Tabla> tablas) {
		
		this.tablas = tablas;
		
	}
	
	/**
	 * "Metodo publico que recibe como parametro un objeto del tipo Tabla y lo inserta en el mapa de tablas de la base de datos
	 * @param tabla-> Tabla a insertar en el mapa de tablas
	 */
	public void insertarTabla(Tabla tabla) {
        
       tablas.put(tabla.getNombreTabla(), tabla);
       
    }
	
	/**
	 * Metodo publico que recibe por parametro una cadena de texto que corresponde al nombre de la tabla, la busca en la coleccion y retorna la tabla correspondiente al nombre proporcionado
	 * @param nombreTabla -> nombre de la tabla
	 * @return la tabla
	 */
	public Tabla obtenerTabla(String nombreTabla) {
			
		for (Entry<String, Tabla> tabla : this.tablas.entrySet()) {

			if(tabla.getKey().equalsIgnoreCase(nombreTabla)) {
			
				return tabla.getValue();
			
			}
			
		}
			
		return null;
			
	}
	
	/**
	 * Metodo publico que retorna una lista de cadenas de texto que representan los nombres de las tablas en la base de datos
	 * @return los nombres de las tablas
	 */
	public ArrayList<String> obtenerNomTablas(){
		
		ArrayList<String> tablasNom = new ArrayList<String>();
		
		for (Entry<String, Tabla> tabla : tablas.entrySet()) {
			
			tablasNom.add(tabla.getKey());
			
		}
		
		return tablasNom;
		
	}
	
	/**
	 * Metodo publico que recibe por parametro un objeto del tipo Tabla que representa la tabla que sera agregada al mapa de tablas de la base de datos.
	 * @param tabla -> tabla que sera aregada a mapa de tabla de la base de datos
	 */
	public void agregarTabla(Tabla tabla) {
		
		this.tablas.put(tabla.getNombreTabla(), tabla);
		
	}
	
	/**
	 * Metodo publico que dado un nombre de una tabla, la elimina del mapa de tablas de la presente clase
	 * @param nombreTabla -> nombre de la tabla a eliminar
	 */
	public void eliminarTabla(String nombreTabla) {
		
		Tabla tabla = this.obtenerTabla(nombreTabla);
		this.tablas.remove(tabla.getNombreTabla());
		
	}
	
	private ArrayList<LinkedHashMap<String, Atributo>> obtenerAuxiliar(Tabla tabla1, Tabla tabla2) {
		
		ArrayList<LinkedHashMap<String, Atributo>> resultado = new ArrayList<LinkedHashMap<String, Atributo>>();
		ArrayList<LinkedHashMap<String, Atributo>> reg1;
		ArrayList<LinkedHashMap<String, Atributo>> reg2;
		
		if(tabla1.getRegistros().size() > tabla2.getRegistros().size()) {
			
			reg1 = new ArrayList<LinkedHashMap<String, Atributo>>(tabla1.getRegistros());
			reg2 = new ArrayList<LinkedHashMap<String, Atributo>>(tabla2.getRegistros());
	   
		}else {
			
			reg1 = new ArrayList<LinkedHashMap<String, Atributo>>(tabla2.getRegistros());
			reg2 = new ArrayList<LinkedHashMap<String, Atributo>>(tabla1.getRegistros());	
		
		}
		
		if(reg1.size()>1 && reg2.size()>1) {
			
			reg2.remove(0);
			reg1.remove(0);
		   
		    for (int i=0; i<reg1.size(); i++) {
		    	
		    	for (int j=0; j<reg2.size(); j++) {
		    	
		    		for(Map.Entry<String, Atributo> entry1 : reg1.get(i).entrySet()) {
		    		
		    			for(Map.Entry<String, Atributo> entry2 : reg2.get(j).entrySet()) {
		    				
		    				if (repiteAtributo(entry1.getValue(), entry2.getValue())) {
		    					
		    					LinkedHashMap<String, Atributo> regCombinado = new LinkedHashMap<String, Atributo>();
		    					
	    						regCombinado.putAll(reg1.get(i));
	    						regCombinado.putAll(reg2.get(j));
	    						resultado.add(regCombinado);
		    					
		    				}
		             
		                }
		            }
		        }
		    }
		    
		}
    	
		return resultado;
		
    }
    	
	/**
	 * Metodo publico que recibe tres parametros: nombre de la primera tabla, nombre de la segunda tabla y el nombre del atributo de busqueda. El metodo realiza una operacion de join natural entre las dos tablas en funcion del atributo de busqueda y retorna una lista de objetos DTOAtributo
	 * @param tabla1-> Tabla 1 a buscar
	 * @param tabla2-> Tabla 2 a buscar
	 * @param busqueda-> atributo a buscar 
	 * @return Lista que cumplan el JOIN NATURAL de ambas tablas
	 */
	public ArrayList<DTOAtributo> joinNatural(String tabla1, String tabla2, String busqueda){
		
		Tabla tab1 = obtenerTabla(tabla1);
		Tabla tab2 = obtenerTabla(tabla2);
		ArrayList<LinkedHashMap<String, Atributo>> buscar = new ArrayList<LinkedHashMap<String, Atributo>>();
		ArrayList<DTOAtributo> resultado = new ArrayList<DTOAtributo>();
		buscar = obtenerAuxiliar(tab1, tab2);
		
		for (LinkedHashMap<String, Atributo> bus : buscar) {//Recorre el buscar
					
			if (bus.get(busqueda) instanceof Cadena) {
				
				Cadena cadena1 = (Cadena) bus.get(busqueda);
				DTOCadena dto = new DTOCadena (cadena1);
				resultado.add(dto);
						
			}
					
			if (bus.get(busqueda) instanceof Entero) {
						
				Entero entero1 = (Entero) bus.get(busqueda);
				DTOEntero dto = new DTOEntero (entero1);
				resultado.add(dto);
						
			}
						
		}

		return resultado;
		
	}
	
	private boolean repiteAtributo(Atributo atr1, Atributo atr2) {
		
		if(atr1.getNombreAtributo().equals(atr2.getNombreAtributo())) {
		
			if (atr1 instanceof Cadena && atr2 instanceof Cadena) {
				
				Cadena cadena1 = (Cadena) atr1;
				Cadena cadena2 = (Cadena) atr2;
				
				 if (cadena1.getDato().equals(cadena2.getDato()) && cadena1.getDato().length()!=0 && cadena2.getDato().length()!=0)
					 return true;
				 else 
					 return false;
				
			}else if (atr1 instanceof Entero && atr2 instanceof Entero) {
			
				Entero entero1 = (Entero) atr1;
				Entero entero2 = (Entero) atr2;
				
				if (entero1.getValor()==entero2.getValor() && entero1.getValor()!=null && entero2.getValor()!=null)
					return true;
				 else 
					return false;
			}
			
		}
		
		return false;
		
	}
	
	/**
	 * Metodo publico que recibe como parametros los nombres de dos tablas. El metodo busca los atributos de las dos tablas y compara si hay alguno en comun entre ellas. Si encuentra al menos un atributo comun, retorna true de lo contrario retorna false
	 * @param tabla1-> Tabla 1 a buscar
	 * @param tabla2-> Tabla 2 a buscar
	 * @return verifica si las tablas tienen algun atributo en comun
	 */
	public boolean validaAtributosJoin (String tabla1, String tabla2) {
		
		Tabla tablita1 = this.obtenerTabla(tabla1);
		Tabla tablita2 = this.obtenerTabla(tabla2);
		
		if (tablita1.getRegistros().size()>0 && tablita2.getRegistros().size()>0) {
			
			LinkedHashMap <String, Atributo> guia1 = tablita1.getRegistros().get(0);
			LinkedHashMap <String, Atributo> guia2 = tablita2.getRegistros().get(0);
			
	        for (Map.Entry<String, Atributo> entry : guia1.entrySet()) {
	        	
	            Atributo atributoGuia1 = entry.getValue();
	            	
	            for (Map.Entry<String, Atributo> entry2 : guia2.entrySet()) {
	            	
	            	Atributo atributoGuia2 = entry2.getValue();
	            	
            		if(atributoGuia2.equals(atributoGuia1)) {
            			
            			return true;
            		
            		}
            		
	            }
	            
	        }
	        
	    }
		
        return false;
		
	}
	
}