package logica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Map;

import comunicacion.DTOAtributo;
import comunicacion.DTOCadena;
import comunicacion.DTOEntero;
import comunicacion.DTOTabla;

/**
 * Esta clase se relaciona con Registros, mediante una agregacion.
Esta clase representa una tabla en una base de datos y contiene metodos para gestionar registros y atributos de la misma.
 *@author Gabriel Bolsi
 *@author Brandon Cairus
 *@author Daiana Daguerre
 *@author Luciana De Matteis
 *@author Mauricio Gonzalez
 */
public class Tabla {
	
	private String nombreTabla;
	private ArrayList<LinkedHashMap<String, Atributo>> registros;
	
	/**
	 * Constructor especifico que recibe como parametro un String que representa el nombre de la tabla e inicializa la instancia de la misma con el nombre proporcionado y crea una lista vacia de registros.
	 * @param tabla->DTOTabla
	 */
	public Tabla(DTOTabla tabla) {
		
		this.nombreTabla = tabla.getNombreTabla();
		registros = new ArrayList<LinkedHashMap<String, Atributo>>();
		
	}
	
	/**
	 * Constructor que recibe como parametro un objeto DTOTabla que inicializa la instancia de la tabla con el nombre proporcionado y crea una lista vacia de registros.
	 * @param nombreTabla->nombre de la tabla
	 */
	public Tabla(String nombreTabla) {
		
		this.nombreTabla = nombreTabla;
		registros = new ArrayList<LinkedHashMap<String, Atributo>>();
		
	}
	
	/**
	 * Metodo publico que retorna un String. Permite recuperar el valor del nombre de la tabla. Su proposito es proporcionar acceso controlado a los datos encapsulados dentro de un objeto.
	 * @return el nombre de la tabla
	 */
	public String getNombreTabla() {
		
		return nombreTabla;
		
	}
	
	
	/**
	 * Metodo publico que retorna una lista de registros de la tabla, donde cada registro es un LinkedHashMap que mapea nombres de atributos a objetos Atributo.
	 * @return lista de registros
	 */
	public ArrayList<LinkedHashMap<String, Atributo>> getRegistros() {
		
		return registros;
		
	}
	
	/**
	 * Metodo publico que retorna una lista de registros DTO, donde los atributos se convierten en objetos DTOAtributo (DTOEntero o DTOCadena) dependiendo de su tipo.
	 * @return lista de registrosDTO
	 */
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
	
	/**
	 * Metodo publico que recibe como parametro un DTOAtributo lo convierte en un objeto Atributo (Entero o Cadena)
	 * @param dtoAtributo-> DTOAtributo
	 * @return El DTOATRIBUTO en Atributo
	 */
	public Atributo convertirDTOATributo(DTOAtributo dtoAtributo) {
		
	    if (dtoAtributo instanceof DTOEntero) {
	    	
	        DTOEntero dtoEntero = (DTOEntero) dtoAtributo;
	        Entero entero = new Entero(dtoEntero);
	        return entero;
	        
	    } else if (dtoAtributo instanceof DTOCadena) {
	    	
	        DTOCadena dtoCadena = (DTOCadena) dtoAtributo;
	        Cadena cadena = new Cadena(dtoCadena);
	        return cadena;
	        
	    }
	    
	    return null;
	    
	}
	
	/**
	 * Metodo publico que recibe como parametro un mapa de nombres de atributos y sus tipos y retorna un mapa que asocia nombres de atributos con objetos DTOAtributo que representa los atributos generados.
	 * @param atributos ->mapa de atribtuos
	 * @return un mapa que vincula los nombres de atributos con un DTOAtributo
	 */
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
	
	
	/**
	 * Metodo publico que recibe una lista de registros y los establece en los registros de la tabla.
	 * @param registros->lista de registros
	 */
	public void setRegistros(ArrayList<LinkedHashMap<String, Atributo>> registros) {
		
		this.registros = registros;
		
	}
	
	/**
	 * Metodo publico que recibe un recibe un mapa que asocia nombres de atributos con objetos DTOAtributo, lo convierte en un registro de atributos (LinkedHashMap) y lo inserta en la lista de registros de la tabla
	 * @param registro->Mapa que vincula los nombres de atributos con los DTOAtributo
	 */
	public void insertarRegistro(LinkedHashMap<String, DTOAtributo> registro) {
		
		LinkedHashMap<String, Atributo> registroConvertido = this.convertirMapa(registro);
		registros.add(registroConvertido);
		
	}
	
	/**
	 * Metodo publico que recibe un mapa que asocia nombres de atributos con objetos DTOAtributo, lo convierte en un registro de atributos (LinkedHashMap) y lo retorna
	 * @param registro-> mapa que asocia nombres de atributo con DTOAtributo
	 * @return mapa que vincula nombres de atributo con Atributo
	 */
	public LinkedHashMap<String, Atributo> convertirMapa(LinkedHashMap<String, DTOAtributo> registro){
		
		LinkedHashMap<String, Atributo> registroConvertido = new LinkedHashMap<String, Atributo>();
		
		for (Map.Entry<String, DTOAtributo> entry : registro.entrySet()) {
			
			Atributo resultado=this.convertirDTOATributo(entry.getValue());
			registroConvertido.put(entry.getKey(), resultado);
			
		}
		
		return registroConvertido;
		
	}
	 
	/**
	 * "Metodo publico que recibe un mapa que representa un registro de atributos para eliminar de la lista de registros de la tabla
	 * @param registro-> mapa de registro de atributos
	 */
	public void eliminarRegistro(LinkedHashMap<String, DTOAtributo> registro) {
		
		LinkedHashMap<String, Atributo> registroConvertido = this.convertirMapa(registro);
		registros.remove(registroConvertido);
		 
	}
	
	/**
	 * Metodo publico que recibe los siguientes parametros: un registro de atributos a modificar, el nombre del atributo a cambiar y el nuevo valor como cadena. Este metodo modifica un registro de atributos en la lista de registros de la tabla, actualizando el valor del atributo especificado.
	 * @param registroCambiar-> registro de atributos a cambiar 
	 * @param nombreAtributo-> nomrbre del atributo a cambiar
	 * @param valorNuevo->nombre del atributo nuevo
	 */
	public void modificarRegistros(LinkedHashMap<String, Atributo> registroCambiar, String nombreAtributo, String valorNuevo) {
		 	
		this.registros.remove(registroCambiar);
		
		for(Entry<String, Atributo> registro : registroCambiar.entrySet()) {
		
			Atributo atributo = registro.getValue();
			
			if(registro.getKey().equalsIgnoreCase(nombreAtributo)) {
			
				if (atributo instanceof Entero) {
					
				    Integer valorEntero = null;
				    if (!valorNuevo.equalsIgnoreCase("NULL")) {
				    	
				        valorEntero = Integer.parseInt(valorNuevo);
				        
				    }
				    
				    ((Entero) atributo).setValor(valorEntero);
				    
				} else if (atributo instanceof Cadena) {
					
				    String valorCadena = null;
				    if (!valorNuevo.equalsIgnoreCase("NULL")) {
				    	
				        valorCadena = valorNuevo;
				        
				    }
				    
				    ((Cadena) atributo).setDato(valorCadena);
				    
				}
				
				this.registros.add(registroCambiar);
				
			}
		}
    }
	
	/**
	 * Metodo publico que verifica si la tabla tiene un atributo marcado como clave en su primer registro y retorna true o false segun se verifique
	 * @return valida si la tabla tiene clave o no
	 */
	public boolean tieneClave() {
		
		LinkedHashMap<String, Atributo> guia=registros.get(0);
		
		for (Entry<String, Atributo> atributo : guia.entrySet()) {
			
			if(atributo.getValue().getClave()==true) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	/**
	 * Metodo publico que retorna el nombre del atributo clave que se encuentra en el primer registro de la tabla
	 * @return el nombre del atributo clave
	 */
	public String obtenerClave() {
		
		for (Entry<String, Atributo> atributo : registros.get(0).entrySet()) {
			
			if(atributo.getValue().getClave() == true) {
				
				return atributo.getKey();
				
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * Metodo publico que retorna una lista con los nombres de los atributos que se encuentran en el primer registro de la tabla y que no pueden ser nulos.
	 * @return  lista con los atributos que son no nulos
	 */
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
	
	/**
	 * Metodo publico que recibe como parametros dos String que son el nombre del atributo y el valor de la condicion que debe cumplir. El metodo filtra de la tabla aquellos registros donde el valor del atributo especificado coincide con el valor de la condicion y retorna una lista de registros DTO que cumplen con la condicion
	 * @param nombreAtributo-> nombre del atributo
	 * @param valorCondicion-> valor de la condicion que deberia de cumplir
	 * @param operador-> operador del la condicion
	 * @return una lista con los DTOAtrbuto que cumplan la condicion
	 */
	public ArrayList<LinkedHashMap<String, DTOAtributo>> obtenerRegistros(String nombreAtributo, String valorCondicion, String operador) {
			
		ArrayList<LinkedHashMap<String, DTOAtributo>> registrosObtenidos = new ArrayList<LinkedHashMap<String, DTOAtributo>>();
		
		boolean primero=true;
		
		for (LinkedHashMap<String, DTOAtributo> misRegistros : this.getRegistrosDTO()) { 
			
			if (!primero) {
			
			    for (Map.Entry<String, DTOAtributo> entrada : misRegistros.entrySet()) {
			    	
		    		DTOAtributo atributo = entrada.getValue();
		    		
		    		if(entrada.getKey().equalsIgnoreCase(nombreAtributo)){
		    		
			    		if(atributo instanceof DTOEntero) { //como la condicion es un string debo de evaluar esto para convertirla
				    		
				    		DTOEntero entradaEntera = (DTOEntero) entrada.getValue();
				    		
				    		if((entradaEntera.getValor() != null) && (!valorCondicion.equalsIgnoreCase("NULL"))) {
				    		
				    			int valorCondicionEntera = Integer.parseInt(valorCondicion);
				    			
					    		if (operador.equals("=") && entradaEntera.getValor() == valorCondicionEntera) {
				                   
					    			registrosObtenidos.add(misRegistros);
				                
					    		}else if (operador.equals("<") && entradaEntera.getValor() < valorCondicionEntera) {
				                
					    			registrosObtenidos.add(misRegistros);
				                
					    		}else if (operador.equals(">") && entradaEntera.getValor() > valorCondicionEntera) {
				                
					    			registrosObtenidos.add(misRegistros);
				                
					    		}else if (operador.equals(">=") && entradaEntera.getValor() >= valorCondicionEntera) {
				                    
					    			registrosObtenidos.add(misRegistros);
				                
					    		}else if (operador.equals("<=") && entradaEntera.getValor() <= valorCondicionEntera) {
				                
					    			registrosObtenidos.add(misRegistros);
				                
					    		}
				    		
				    		}else if((valorCondicion.equalsIgnoreCase("NULL")) &&  (entradaEntera.getValor() == null)){
				    			
				    			registrosObtenidos.add(misRegistros);
				    			
				    		}
				    		
				    	}else if(atributo instanceof DTOCadena){
				    		
				    		DTOCadena entradaCadena = (DTOCadena) entrada.getValue();
				    		
				    		if(entradaCadena.getDato() != null) {
				    		
					    		if(entradaCadena.getDato().equalsIgnoreCase(valorCondicion)) { //si cumple con la condicion
					    			
					    			registrosObtenidos.add(misRegistros);
					    			
					    		}
					    	
				    		}else if((valorCondicion.equalsIgnoreCase("NULL")) &&  (entradaCadena.getDato() == null)){
				    			
				    			registrosObtenidos.add(misRegistros);
				    			
				    		}
				    		
				    	}
		    		
		    		}
		    		
			    }
		    
			}else {
				
				primero=false;
				
			}
	    	
		}
	     
		return registrosObtenidos;
		
	}

	/**
	 * Metodo publico que recibe como parametro el identificador del atributo que se desea agregar al mapa y lo inserta en el mismo.
	 * @param atributo->Identificador del atributo
	 */
	public void insertarAtributo (Atributo atributo) {
		
		registros.get(0).put(atributo.getNombreAtributo(), atributo); //agrega solo a la guia
		
	}
	
	/**
	 * Metodo publico que recibe como parametro una lista de registros y el nombre del atributo a seleccionar. El metodo itera a traves de los registros y busca el atributo con el nombre especificado, luego agrega los valores de ese atributo a una lista de objetos DTOAtributo y la retorna.
	 * @param registros->lista de registros
	 * @param nombreAtributo-> nombre del atributo que se quiere seleccionar
	 * @return lista DTOAtributo
	 */
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
	
	/**
	 * Metodo publico que recibe como parametro el nombre de un atributo y retorna una cadena de texto que indica el tipo (entero o cadena)
	 * @param nombreAtributo-> nombre del atributo
	 * @return el tipo de atributo
	 */
	public String obtenerTipo(String nombreAtributo) {
		
		if(this.getRegistros().get(0).get(nombreAtributo) instanceof Entero) {
			
			return "entero";
			
		}else if(this.getRegistros().get(0).get(nombreAtributo) instanceof Cadena){
			
			return "cadena";
			
		}
		
		return null;
		
	}
	
	/**
	 * Metodo publico que retorna true si la tabla esta vacia o false en caso contrario.
	 * @return verifica si la tabla se encuentra vacia o no
	 */
	public boolean esVacia() { //retorna true si no tiene
		
		return this.getRegistros().size()==1;
		
	}
	
	/**
	 * Metodo publico que recibe el nombre de la tabla y una lista de atributos. El metodo valida si la cantidad de atributos proporcionados coincide con la cantidad de atributos en la tabla y retorna true si la cantidad coincide, o false en caso contrario
	 * @param atributos-> lista de atributos
	 * @return valida si la cantidad de atributos es correcta
	 */
	public boolean validaCantidadAtributos( ArrayList<String> atributos) {
		

	//	Tabla tablita = obtenerTabla(getNombreTabla);

		int cantidadAtributos = this.getRegistros().get(0).size();

		if(atributos.size() != cantidadAtributos) {
			
			return false;
			
		}else {
			
			return true;
		
		}
		
	}
	/**
	 * Metodo publico que recibe como parametro el nombre de la tabla y una lista de atributos. El metodo valida si los atributos que no sean nulos encajan con la definicion de la tabla y retorna true si todos los atributos cumplen con la restriccion, false si al menos uno no lo hace
	 * @param atributos-> lista de atributos
	 * @return valida si los atributos de la tabla pueden ser notnull
	 */
	public boolean validaNotNull(ArrayList<String> atributos) {
		
		LinkedHashMap<String, Atributo> guia = this.getRegistros().get(0);
		
		int i=0;
		
		for (Entry<String, Atributo> atriGuia : guia.entrySet()) {
			
			if((atributos.get(i).equalsIgnoreCase("NULL")) && (atriGuia.getValue().getNulo()==true)) {
			
				return false;
			
			}
			
			i++;
				
		}
		
		return true;
		
	}
	
	/**
	 * Metodo publico que recibe como parametros el nombre de una tabla y una lista de atributos. El metodo verifica si la tabla tiene una clave primaria definida; si no la tiene, se considera valida y retorna true. Si tiene una clave primaria, el metodo comprueba que ninguno de los atributos de la clave sea 'NULL' y que no se repitan en otros registros. Si alguna de estas condiciones no se cumple, retorna false
	 * @param atributos -> lista de atributos
	 * @return valida si los atributos de la tabla pueden ser clave
	 */
	public boolean validaClave (ArrayList<String> atributos) {
		
		//Tabla tablita = this.obtenerTabla(nombreTabla);
		LinkedHashMap<String, Atributo> guia = this.getRegistros().get(0);
		
		if(this.tieneClave()) {
		
			String clave = this.obtenerClave();
			int posClave = 0;
			
			for (Entry<String, Atributo> atriGuia : guia.entrySet()) {
			
				if(atriGuia.getKey().equals(clave)) { //Si la clave es nula o se repite
					
					if((atributos.get(posClave).equals("NULL")) || (!(this.obtenerRegistros(clave, atributos.get(posClave), "=").isEmpty()))) {
						
						return false;
					
					}
					
				}
				
				posClave++;
				
			}
			
		}
	
		return true;
		
	}
	/**
	 * Metodo publico que recibe como parametro el nombre de una tabla y retorna una lista de cadenas que describen las caracteristicas de los atributos de la tabla
	 * @return Lista con la descripcion de los atributos de la tabla
	 */
	public ArrayList<String> describeTabla (){

		ArrayList<String> resultado = new ArrayList<String>();
		LinkedHashMap <String, Atributo> atributos = this.getRegistros().get(0);

		for (Map.Entry<String, Atributo> entry : atributos.entrySet()) {

			String insertar="";
			String nombre = entry.getKey();
			String clave="";
			String tipo="";
			String notNull="";

			if(entry.getValue().getClave()==true) {
				
				clave = "Clave primaria";
			
			}else {
			
				clave = "No es clave primaria";
			
			}
			
			if (entry.getValue() instanceof Cadena) {
				
				tipo="Cadena";
			
			}else {
			
				tipo="Entero";
			
			}
			
			if(entry.getValue().getNulo()==true) {
			
				notNull="No nulo";
			
			}else {
			
				notNull="Nulo";
			}
			
			insertar = nombre + " - " + tipo + " - " + clave + " - " + notNull;
			resultado.add(insertar);

		}
		
		return resultado;
		
	}
	
	/**
	 * Metodo publico que recibe el nombre de una tabla y una lista de atributos. El metodo genera un registro a partir de los atributos proporcionados y lo retorna como un mapa de atributos.
	 * @param atributos -> lista de atributos
	 * @return  mapa de registos a partir del nombre de la tabla y de los atributos
	 */
	public LinkedHashMap<String, DTOAtributo> generarArrayListRegistro(ArrayList<String> atributos){
		
		LinkedHashMap<String, DTOAtributo> registro = new LinkedHashMap<String, DTOAtributo>();
		LinkedHashMap<String, Atributo> guia = this.getRegistros().get(0);
		int i=0;
		
		for (Entry<String, Atributo> atriGuia : guia.entrySet()) {
			
			if(atriGuia.getValue() instanceof Entero) {
				
				Entero atributoE = (Entero) atriGuia.getValue();
				DTOEntero atrE =  new DTOEntero(atributoE);
				
				if(!(atributos.get(i).equalsIgnoreCase("NULL"))) {
					
					atrE.setValor(Integer.parseInt(atributos.get(i)));
					
				}else {
					
					atrE.setValor(null);
					
				}
				
				registro.put(atriGuia.getKey(), atrE);
				
			}else if(atriGuia.getValue() instanceof Cadena){

				Cadena atributoC = (Cadena) atriGuia.getValue();
				DTOCadena atrC = new DTOCadena(atributoC);
				
				if(!(atributos.get(i).equalsIgnoreCase("NULL"))) {
					
					atrC.setDato(atributos.get(i));
					
				}
				
				registro.put(atriGuia.getKey(), atrC);
				
			}
				
			i++;
			
		}
		
		return registro;
		
	}
	

	/**
	 * Metodo publico que recibe como parametros el nombre del atributo a consultar, dos nombres de atributos de condicion y dos valores de condicion. El metodo retorna una lista de objetos DTOAtributo que contiene solo aquellos que cumplen simultaneamente ambas condiciones.
	 * @param nombreAtributo -> nombre del atributo
	 * @param nombreAtributoCondicion1 -> nombre de la condion del atributo 1
	 * @param valorCondicion1 ->  valor de la condicion del atributo 1
	 * @param nombreAtributoCondicion2 -> nombre de la condion del atributo 2
	 * @param valorCondicion2 ->  valor de la condicion del atributo 2
	 * @return lista de registros que cumplan ambas condiciones
	 */
	public ArrayList<DTOAtributo> consultaAnd (String nombreAtributo, String nombreAtributoCondicion1, String valorCondicion1, String nombreAtributoCondicion2,String valorCondicion2, String operador) {
    	
    	ArrayList <DTOAtributo>  res1= new ArrayList<DTOAtributo>();
    	ArrayList <DTOAtributo> res2 = new ArrayList<DTOAtributo>();
    	
    	
		ArrayList<LinkedHashMap<String, DTOAtributo>> registros1 = new ArrayList<LinkedHashMap<String, DTOAtributo>> (this.obtenerRegistros(nombreAtributoCondicion1, valorCondicion1, operador));
		res1= this.seleccionarAtributo(registros1, nombreAtributo);
		ArrayList<LinkedHashMap<String, DTOAtributo>> registros2 = new ArrayList<LinkedHashMap<String, DTOAtributo>> (this.obtenerRegistros(nombreAtributoCondicion2, valorCondicion2, operador));
		res2 = this.seleccionarAtributo(registros2, nombreAtributo);
		
    	res1.retainAll(res2);
		
		return res1;
		
    }
	
	/**
	 * Metodo publico que recibe como parametros el nombre del atributo de consulta, dos conjuntos de nombre de atributo y valor de condicion. El metodo realiza una consulta OR en la tabla especificada, buscando registros que cumplan cualquiera de las dos condiciones y retorna una lista de objetos DTOAtributo que representan los registros encontrados despues de aplicar la consulta OR, la que no incluye elementos duplicados.
	 * @param nombreAtributo -> nombre del atributo
	 * @param nombreAtributoCondicion1 -> nombre de la condion del atributo 1
	 * @param valorCondicion1 ->  valor de la condicion del atributo 1
	 * @param nombreAtributoCondicion2 -> nombre de la condion del atributo 2
	 * @param valorCondicion2 ->  valor de la condicion del atributo 2
	 * @return lista de registros que cumplan con al menos una  condicion
	 */
	public ArrayList<DTOAtributo> consultaOr(String nombreAtributo, String nombreAtributoCondicion1, String valorCondicion1, String nombreAtributoCondicion2, String valorCondicion2, String operador) {
		
		ArrayList <DTOAtributo> resultado1 = new ArrayList <DTOAtributo>();
		ArrayList <DTOAtributo> resultado2 = new ArrayList <DTOAtributo>();
		
		ArrayList<LinkedHashMap<String, DTOAtributo>> registros1 = new ArrayList<LinkedHashMap<String, DTOAtributo>> (this.obtenerRegistros(nombreAtributoCondicion1, valorCondicion1, operador));
		resultado1= this.seleccionarAtributo(registros1, nombreAtributo);
		ArrayList<LinkedHashMap<String, DTOAtributo>> registros2 = new ArrayList<LinkedHashMap<String, DTOAtributo>> (this.obtenerRegistros(nombreAtributoCondicion2, valorCondicion2, operador));
		resultado2 = this.seleccionarAtributo(registros2, nombreAtributo);

        HashSet<DTOAtributo> elementosUnicos = new HashSet<>(resultado1);

        for (DTOAtributo elemento : resultado2) {
        	
            if (!elementosUnicos.contains(elemento)) {
            	
                resultado1.add(elemento);
                elementosUnicos.add(elemento);
                
            }
            
        }
        
		return resultado1;
		
	}
	
	/**
	 * Metodo publico que recibe como parametro el nombre de un atributo y un valor de condicion. Valida si el valor de condicion proporcionado es valido para el tipo de atributo especificado en la tabla. Si el atributo es de tipo entero, verifica si puede ser convertido a un numero entero. Retorna: true si la condicion es valida, false si no lo es
	 * @param nombreAtributo-> nombre del atributo
	 * @param valorCondicion-> valor de la condicion
	 * @return verifica si la condicion es valida o no
	 */
	public boolean validaCondicion(String nombreAtributo, String valorCondicion) {
		
	    String tipoAtributo = this.obtenerTipo(nombreAtributo);
	    
	    if(!valorCondicion.equalsIgnoreCase("NULL")) {
	    
		    if ("entero".equals(tipoAtributo)) {
		    	
		        try {
		        	
		            Integer.parseInt(valorCondicion); // Si esto tiene éxito es porque es numérico
		            return true;
		            
		        } catch (NumberFormatException e) {
		        	
		            return false;
		            
		        }
		        
		    }
	    
	    }

	    return true; 
	    
	}
	
	/**
	 * Metodo publico que recibe como parametro la tabla y una lista de atributos. El metodo valida si los atributos proporcionados son validos en la tabla especificada segun su tipo y condiciones y retorna true en caso afirmativo, o false si al menos uno de ellos no lo es
	 * @param atributos-> lista de atributos
	 * @return valida los atributos son los correctos en la tabla
	 */
	public boolean validaAtributos(ArrayList<String> atributos) {
		
		LinkedHashMap<String, Atributo> guia = this.getRegistros().get(0);
		
		int i=0;
		
		for (Entry<String, Atributo> atriGuia : guia.entrySet()) {
			
			if(!(atributos.get(i).equalsIgnoreCase("NULL"))) {
			
				if((!(validaCondicion(atriGuia.getKey(), atributos.get(i))))) {
					
					return false;
				}
				
			}
			
			i++;
				
		}
		
		return true;
		
	}
	
	/**
	 * Metodo publico que recibe como parametros la tabla, el nombre del atributo en los que se buscara el valor maximo. El metodo retorna el valor maximo encontrado como un entero
	 * @param nombreAtributo -> nombre del atributo
	 * @return el valor maximo del atributo
	 */
	public Integer obtenerMaximo(String nombreAtributo) {
		
	    Integer maximo = null;
	    boolean primero = true; 
	    ArrayList<DTOAtributo> registros = this.seleccionarAtributo(this.getRegistrosDTO(), nombreAtributo);

	    for (DTOAtributo reg : registros) {
	    	
	        if (primero) {
	            
	        	primero = false;  
	            
	        } else {
	        
	        	DTOEntero atributo = (DTOEntero) reg;
	            Integer valor = atributo.getValor();

	            if(valor != null) {
	            
	            	if (maximo == null || valor > maximo) {
	              
	            		maximo = valor;
	            
	            	}
	            
	            }
	            
	        }
	        
	    }

	    return maximo;
	    
	}
	
	/** 
	 * Metodo publico que recibe como parametros la tabla y el nombre del atributo en los que se buscara el valor minimo. El metodo retorna un entero que es el valor minimo encontrado como entero.
	 * @param nombreAtributo -> nombre del atributo
	 * @return el valor minimo del atributo
	 */
	public Integer obtenerMinimo(String nombreAtributo) {
		
	    Integer minimo = null;
	    boolean primero=true;
	;
	    ArrayList<DTOAtributo> registros = this.seleccionarAtributo(this.getRegistrosDTO(), nombreAtributo);

	    for (DTOAtributo reg : registros) {
	    
	    	if (primero) {
	            
	        	primero = false;  
	            
	        }else {
	    	
		    	DTOEntero atributo = (DTOEntero) reg;
		        Integer valor = atributo.getValor();
	
		        if(valor != null) {
		        
			        if (minimo == null || valor < minimo) {
			        
			        	minimo = valor;
			        }
		        
		        }
		   }
	    
	    }

	    return minimo;
	    
	}
	
}
