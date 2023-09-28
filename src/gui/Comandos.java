package gui;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import comunicacion.DTOAtributo;
import comunicacion.DTOBaseDatos;
import comunicacion.DTOCadena;
import comunicacion.DTOEntero;
import comunicacion.DTOTabla;
import comunicacion.IFachadaLogica;
	
/**
 * 
 * Esta clase maneja las palabras claves de los comandos que se utilizan para realizar las operaciones en las bases de datos.
 * @author Brandon
 * @author Gabriel
 * @author Daiana
 * @author Mauricio
 * @author Luciana
 */
public class Comandos {
	
	private IFachadaLogica logica;
    static int aciertos;
    private Map<String, Consumer<ArrayList<String[]>>> acciones; //es una funcion que toma un valor y realiza algun tipo de accion con el, pero no produce ningun resultado como salida.
    static int usos;
    private VentanaPrincipal ven = new VentanaPrincipal(logica);
    
    /**
     * Metodo para inicializar las acciones
     * @param fa-> inicializa la logica del programa para inicializar acciones
     */
    public Comandos(IFachadaLogica fa) {
    	
    	logica=fa;
    	VentanaPrincipal ven = new VentanaPrincipal(fa);
        inicializarAcciones();
        
    }
    
	/**
	 * Metodo privado que inicializa el mapa de acciones, que asocia comandos con funciones que deben ejecutarse cuando se recibe ese comando. Por ejemplo, cuando se recibe el comando "CREATE", se asocia con la funcion comandoCreate que procesa la creacion de tablas.
	 * 
	 */
    private void inicializarAcciones() {
    	
        acciones = new HashMap<>();
        acciones.put("SELECT", sentencia -> comandoSelect(sentencia));
       
        if (!(Login.demo)) {
	    
        	acciones.put("CREATE", sentencia -> comandoCreateDrop(sentencia));
        	acciones.put("DROP", sentencia -> comandoCreateDrop(sentencia));
	        acciones.put("INSERT", sentencia -> comandoInsert(sentencia));
	        acciones.put("SHOW", sentencia -> comandoShow(sentencia));
	        acciones.put("USE", sentencia -> comandoUse(sentencia));
	        acciones.put("DELETE", sentencia -> comandoDelete(sentencia));
	        acciones.put("UPDATE", sentencia -> comandoUpdate(sentencia));
	        acciones.put("NOTNULL", sentencia -> comandoNotNull(sentencia));
	        acciones.put("COUNT", sentencia -> comandoCount(sentencia));
	        acciones.put("AVG", sentencia -> comandoAvg(sentencia));
	        acciones.put("PRIMARYKEY", sentencia -> comandoPrimaryKey(sentencia));
	        acciones.put("DESCRIBE", sentencia -> comandoDescribe(sentencia));
	        acciones.put("HELP", sentencia -> comandoHelp(sentencia));
	        acciones.put("JOINNATURAL", sentencia -> comandoJoinNatural(sentencia));
	        acciones.put("MAX", sentencia -> comandoMax(sentencia));
	        acciones.put("MIN", sentencia -> comandoMin(sentencia));
        
        }
        
    }
   
    
    /**
    * 
    * Metodo privado que recibe como par�metros una lista de matrices de cadenas de texto (la sentencia), dos indices de posicion (inicial y final) y un numero entero que representa la cantidad de argumentos.  El metodo v�lida si la cantidad de argumentos en las l�neas de una sentencia esta dentro de un rango especifico y retorna true si es v�lida o false en caso contrario.
    * @param sentencia-> lista de sentencias
    * @param posInicial->indice
    * @param posFinal->indice
    * @param cantArgumentos->cantidad de argumentos
    * @return v�lida la cantidad de argumentos v�lidas para realizar la sentencia
    * 
    */
	private boolean validaCantidadArgumentos(ArrayList<String[]> sentencia, int posInicial, int posFinal, int cantArgumentos) {

		if (posInicial == posFinal) {

			return (sentencia.get(posInicial).length == cantArgumentos);

		} else {

			for (int i = posInicial; i < posFinal; i++) {

				if (sentencia.get(i).length != cantArgumentos) {

					return false;

				}

			}
		}

		return true;

	}
	
	/**
	 * Metodo privado que recibe como parametro una cadena de texto y retorna true si el tipo de dato es v�lido (cadena o entero) o false en caso contrario.
	 * @param tipo->cadena de texto
	 * @return v�lida que el tipo de dato sea entero o cadena
	 * 
	 */
	private boolean validaTipoDato(String tipo) {

		return ((tipo.equalsIgnoreCase("ENTERO")) || (tipo.equalsIgnoreCase("CADENA")));

	}
	
	/**
	 * Metodo privado que recibe como par�metros una lista de matrices de cadenas de texto (sentencia) y dos indices de posicion (inicial y final). El metodo retorna true si los tipos de atributos (entero o cadena) en el rango especificado son v�lidos y false en caso contrario.
	 * @param sentencia-> lista de sentencias
	 * @param posInicial->indice
	 * @param posFinal->indice
	 * @return v�lida el tipo de atributo en el rango de indice.
	 * 
	 */
	private boolean validaTiposAtributos(ArrayList<String[]> sentencia, int posInicial, int posFinal) {

		for (int j = posInicial; j < posFinal; j++) {

			if (!(validaTipoDato(sentencia.get(j)[1]))) {

				return false;

			}

		}

		return true;

	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia), dos valores enteros (min y max) que representan los limites de la cantidad de l�neas permitidas. El metodo retorna true si la cantidad de l�neas en la sentencia esta dentro del rango especificado o false en caso contrario.
	 * @param sentencia->lista de sentencia
	 * @param min-> limite m�nimo de cantidad de l�neas
	 * @param max-> limite m�ximo de cantidad de l�neas
	 * @return v�lida la cantidad de l�neas se encuentra dentro del rango especificado
	 * 
	 */
	private boolean validaCantidadLineas(ArrayList<String[]> sentencia, int min, int max) {

		if (min == max) {

			return (sentencia.size() == min);

		} else {

			return ((sentencia.size() >= min) && (sentencia.size() <= max));

		}

	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y retorna true si la sentencia de una sola l�nea es v�lida o false en caso contrario.
	 * @param sentencia->lista de sentencias
	 * @return v�lida si la sentencia es v�lida
	 * 
	 */
	private boolean validaSentenciasUnaLinea(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 1, 1))) {

			ven.insertarDepuracion("Error #02", "La cantidad de l�neas ingresada es incorrecta");

		} else {

			if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) {

				ven.insertarDepuracion("Error #03", "Cantidad de argumentos no v�lida");

			} else {

				return true;

			}

		}

		return false;

	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y retorna true si la sentencia de dos l�neas es v�lida o false en caso contrario.
	 * @param sentencia-> lista de sentencias
	 * @return v�lida si la sentencia es v�lida
	 */
	private boolean validaSentenciasDosLineas(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 2, 2))) {

			ven.insertarDepuracion("Error #02", "La cantidad de l�neas ingresada es incorrecta");

		} else {

			if (!(validaCantidadArgumentos(sentencia, 0, sentencia.size(), 2))) {

				ven.insertarDepuracion("Error #03", "Cantidad de argumentos no v�lida");

			} else {

				if (!(sentencia.get(1)[0].equalsIgnoreCase("FROM"))) {

					ven.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es v�lido");

				} else {

					return true;

				}

			}

		}

		return false;

	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y retorna true si la sentencia con "WHERE" es v�lida o false en caso contrario.
	 * @param sentencia->lista de sentencias
	 * @return v�lida la sentencia de WHERE
	 * 
	 */
	private boolean  validaSentenciasWhereComun(ArrayList<String[]> sentencia) {

		ArrayList<String> operadores = new ArrayList<>(Arrays.asList("=", "<", ">", ">=", "<="));

		if (!(validaCantidadArgumentos(sentencia, 2, 2, 4))) {

			ven.insertarDepuracion("Error #03", "Cantidad de argumentos no v�lida en l�nea 3");

		} else {

			if (!(sentencia.get(2)[0].equalsIgnoreCase("WHERE"))) {

				ven.insertarDepuracion("Error #01", "El comando: " + sentencia.get(2)[0].toUpperCase() + " no es v�lido");

			} else {

				if (!(operadores.contains(sentencia.get(2)[2]))) {

					ven.insertarDepuracion("Error #05", "El operador: " + sentencia.get(2)[2] + " no es v�lido");

				} else {

					return true;

				}
			}

		}

		return false;

	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y retorna true si la sentencia con "FROM" es v�lida o false en caso contrario.
	 * @param sentencia->lista de sentencias
	 * @return v�lida la sentencia FROM
	 * 
	 */
	private boolean  validaSentenciasFrom(ArrayList<String[]> sentencia) {

		if (!(validaCantidadArgumentos(sentencia, 1, 1, 2))) {

			ven.insertarDepuracion("Error #03", "Cantidad de argumentos no v�lida en la l�nea 2");

		} else {

			if (!(sentencia.get(1)[0].equalsIgnoreCase("FROM"))) {

				ven.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es v�lido");

			} else {

				return true;

			}
		}

		return false;

	}

	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y retorna true si la sentencia con "FROM" y "WHERE" es v�lida o false en caso contrario.
	 * @param sentencia->lista de sentencias
	 * @return v�lida la sentencia del FROM con WHERE
	 * 
	 */
	private boolean  validaSentenciasFromWhere(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 3, 3))) {

			ven.insertarDepuracion("Error #02", "La cantidad de l�neas ingresada es incorrecta");

		} else {

			if ( validaSentenciasFrom(sentencia)) {

				if ( validaSentenciasWhereComun(sentencia)) {

					return true;

				}

			}

		}

		return false;

	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y retorna true si los operadores lOgicos en la sentencia son v�lidos.
	 * @param sentencia-> lista de sentencias
	 * @return v�lida los operadores logicos de la sentencia
	 * 
	 */
	private boolean validaOperadoresLogicos(ArrayList<String[]> sentencia) {

		 List<String> operadores = Arrays.asList("=", "<", ">", "<=", ">=");
		
		if (!(validaCantidadArgumentos(sentencia, 2, 2, 8))) {

			ven.insertarDepuracion("Error #03", "Cantidad de argumentos no v�lida en la l�nea 3");

		} else {

			if (!(operadores.contains(sentencia.get(2)[2])) || !(operadores.contains(sentencia.get(2)[6]))) {

				ven.insertarDepuracion("Error #05", "Operador/es de igualdad no v�lido en la l�nea 3");

			} else {

				return true;

			}

		}

		return false;

	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y v�lida la estructura de la misma: cantidad de l�neas, cantidad de argumentos,  tipos de datos y si existe la tabla en la base de datos para luego crearla con los atributos especificados.
	 * @param sentencia-> lista de sentencias
	 * 
	 */
	private void comandoTable(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadLineas(sentencia, 2, 5))) {
			
			ven.insertarDepuracion("Error #02", "La cantidad de l�neas ingresada es incorrecta, recuerde que se permiten de uno a tres atributos por tabla");
        	
		}else {
			
		    if(!(validaCantidadArgumentos(sentencia, 1, sentencia.size(), 2))) {
			
		    	ven.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta entre las l�neas 2 y " + sentencia.size());
	        	
			}else if(sentencia.get(0)[0].equalsIgnoreCase("CREATE")){ //SI QUIERE CREAR UNA TABLA
				
				if(!(validaTiposAtributos(sentencia, 2, sentencia.size()))) {
					
					ven.insertarDepuracion("Error #04", "Tipos de datos incorrectos, recuerde que solo se admiten datos de tipo entero o cadena");
		        	
				}else {
					
					if(logica.existeTabla(sentencia.get(1)[1])) {
						
						ven.insertarDepuracion("Error #11", "La tabla ingresada ya existe para la base de datos seleccionada");
			        	
					}else {
						
						LinkedHashMap<String, String> atributos = new LinkedHashMap<String, String>();

						for (int i = 0; i < sentencia.size(); i++) {
							
						    if (sentencia.get(i) != null && sentencia.get(i).length >= 2 && sentencia.get(i)[0] != null && sentencia.get(i)[1] != null) {
						       
						    	atributos.put(sentencia.get(i)[0], sentencia.get(i)[1]);
						    
						    }
						}

						DTOTabla tabla = new DTOTabla(sentencia.get(1)[1]);
						
						logica.crearTabla(tabla, atributos);
						ven.cargarTablas(logica.obtenerTablasNom());
						aciertos++;
						ven.insertarDepuracion("Acierto #" + aciertos, "Se ha ingresado con �xito la tabla: " +  sentencia.get(1)[1]);
			        	
					}
				}
				
			}else if(sentencia.get(0)[0].equalsIgnoreCase("DROP")){ //SI QUIERE BORRAR UNA TABLA
				
				if(logica.existeTabla(sentencia.get(1)[1])) {
					
					logica.eliminarTabla(sentencia.get(1)[1]);
					ven.cargarTablas(logica.obtenerTablasNom());
					aciertos++;
					ven.insertarDepuracion("Acierto #" + aciertos, "Se ha eliminado con �xito la tabla: " +  sentencia.get(1)[1]);
		        	
				}else {
					
					ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
                	
				}
				
			}
		}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "DATABASE" para crear una nueva base de datos.  El metodo verifica la validez de la sentencia, asegura que se haya seleccionado una base de datos y comprueba si la base de datos ya existe antes de crearla.
	 * @param sentencia->lista de sentencia
	 * 
	 */
	private void comandoDatabase(ArrayList<String[]> sentencia) {
		VentanaPrincipal ven = new VentanaPrincipal(logica);
		if(!(validaCantidadLineas(sentencia, 2, 2))) {
			
        	ven.insertarDepuracion("Error #02", "La cantidad de l�neas ingresada es incorrecta");
        	
		}else {
		
			if(!(validaCantidadArgumentos(sentencia, 1, 1, 2))) {
				
	        	ven.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en la l�nea 2");
	        	
			}else if(sentencia.get(0)[0].equalsIgnoreCase("CREATE")){ //SI QUIERE CREAR UNA BD
				
				if(logica.existeBD(sentencia.get(1)[1])){
					
					ven.insertarDepuracion("Error #12", "La base de datos ingresada ya existe para el usuario seleccionado");
		        	
				}else {
					
					DTOBaseDatos bd =new DTOBaseDatos(sentencia.get(1)[1]);
					logica.crearBD(bd);
					ven.cargarBasesDatos(logica.obtenerBasesNom());
				//	VentanaPrincipal.cargarBasesDatos(logica.obtenerBasesNom());
					aciertos++;
		        	ven.insertarDepuracion("Acierto #" + aciertos, "Se ha ingresado con �xito la base de datos: " + sentencia.get(1)[1]);
					
				}
			
			}else if(sentencia.get(0)[0].equalsIgnoreCase("DROP")){ //SI QUIERE BORRAR UNA BD
				
				if(logica.existeBD(sentencia.get(1)[1])) {
					
					if(logica.getBaseDatos().equalsIgnoreCase(sentencia.get(1)[1])) {
						logica.liberarMemoriaBaseDatos();
						ven.liberarBDActual();
						
					}
					
					logica.eliminarBD(sentencia.get(1)[1]);
					ven.cargarBasesDatos(logica.obtenerBasesNom());
					//VentanaPrincipal.cargarBasesDatos(logica.obtenerBasesNom());
					ven.cargarTablas(null);
					//VentanaPrincipal.cargarTablas(null);
					ven.insertarDepuracion("Acierto #" + aciertos, "Se ha eliminado con �xito la base de datos: " + sentencia.get(1)[1]);
					
				}else {
					
					ven.insertarDepuracion("Error #17", "La base de datos ingresada no existe para el usuario logueado");
		        	
				}
				
			}
			
		}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "SELECT AND", que realiza una consulta en una tabla con una condici�n "AND".  El metodo verifica la validez de la sentencia, el tipo de atributo y condici�n, y muestra los resultados de la consulta si es v�lida. 
	 * @param sentencia-> lista de sentencias
	 * 
	 */
	private void comandoSelectAnd(ArrayList<String[]> sentencia) {
		VentanaPrincipal ven = new VentanaPrincipal(logica);
		
		if(logica.existeTabla(sentencia.get(1)[1])) {
		
			if((logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3]) && (logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[5], sentencia.get(2)[7])))) { //v�lido que el tipo de atributo y condici�n coincidan
				
				if(logica.consultaAnd(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[5], sentencia.get(2)[7], sentencia.get(2)[2]).isEmpty()) { //v�lido que hayan registros que mostrar para la condici�n dada
					
					ven.insertarDepuracion("Error #13", "No hay registros que coincidan con los par�metros de la b�squeda");
					
				}else {
					
					ArrayList<DTOAtributo> atributos=logica.consultaAnd(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[5], sentencia.get(2)[7], sentencia.get(2)[2]);
					ven.cargarTablaAtributos(atributos, sentencia.get(0)[1]);
					aciertos++;
		        	ven.insertarDepuracion("Acierto #" + aciertos, "Consulta �xitosa, mostrando resultados para la tabla: " + sentencia.get(1)[1]);
		        	
				}
				
			}else {
				
				ven.insertarDepuracion("Error #14", "El tipo de atributo y el tipo de condici�n no coinciden");
				
			}
		
		}else {
			
			ven.insertarDepuracion("Error #11", "La tabla ingresada no existe para la base de datos seleccionada");
        	
		}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "SELECT OR", que realiza una consulta en una tabla con una condici�n "OR". Verifica la validez de la sentencia, el tipo de atributo y condici�n, y muestra los resultados de la consulta si es v�lida.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private void comandoSelectOr(ArrayList<String[]> sentencia) {
		VentanaPrincipal ven= new VentanaPrincipal(logica);
		
		if(logica.existeTabla(sentencia.get(1)[1])) {
		
			if((logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3]) || (logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[5], sentencia.get(2)[7])))) { //v�lido que el tipo de atributo y condici�n coincidan
				
				if(logica.consultaOr(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[5], sentencia.get(2)[7], sentencia.get(2)[2]).isEmpty()) { //v�lido que hayan registros que mostrar para la condici�n dada
						
					ven.insertarDepuracion("Error #13", "No hay registros que coincidan con los par�metros de la b�squeda");
					
				}else {
					
					ArrayList<DTOAtributo> atributos=logica.consultaOr(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[5], sentencia.get(2)[7], sentencia.get(2)[2]);
					ven.cargarTablaAtributos(atributos, sentencia.get(0)[1]);
					aciertos++;
		        	ven.insertarDepuracion("Acierto #" + aciertos, "Consulta �xitosa, mostrando resultados para la tabla: " + sentencia.get(1)[1]);
		        	
				}
				
			}else {
				
				ven.insertarDepuracion("Error #14", "El tipo de atributo y el tipo de condici�n no coinciden");
				
			}
		
		}else {
			
			ven.insertarDepuracion("Error #11", "La tabla ingresada no existe para la base de datos seleccionada");
	    	
		}
		
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "SHOW", que verifica la validez de la sentencia y muestra las tablas si es v�lida.
	 * @param sentencia-> lista de sentencias
	 * 
	 */
	private void comandoShow(ArrayList<String[]> sentencia) {
		VentanaPrincipal ven = new VentanaPrincipal(logica);
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
    		if(!(sentencia.get(0)[1].equalsIgnoreCase("TABLES"))) {
            			
	        	ven.insertarDepuracion("Error #01", "El comando: " + sentencia.get(0)[1].toUpperCase() + " no es v�lido");
	        	
            }else {
            	
            	ArrayList<String> tablas = logica.obtenerTablasNom();
            	ven.cargarTablaString(tablas, "tablas");
            	aciertos++;
            	ven.insertarDepuracion("Acierto #" + aciertos, "Consulta �xitosa, se muestran las tablas para la base de datos seleccionada");
            	
            }
        	
    	}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "CREATE" para crear una tabla o una base de datos. El metodo verifica la validez de la sentencia y el tipo de comando ("TABLE" o "DATABASE") antes de llamar a metodos especificos (comandoTable o comandoDatabase) para ejecutar la accion correspondiente.
	 * @param sentencia->lista de sentecias
	 * 
	 */
	private void comandoCreateDrop(ArrayList<String[]> sentencia) {
		VentanaPrincipal ven = new VentanaPrincipal(logica);
		
		if(!(sentencia.size()>1)){
			
    		ven.insertarDepuracion("Error #02", "La cantidad de l�neas ingresada es incorrecta");
        	
		}else {
			
        	if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { //no tiene mas nada alado del create
        		
        		ven.insertarDepuracion("Error #03", "Demasiados argumentos en l�nea 1");
	        
        	}else {
    			
        		if(sentencia.get(1)[0].equalsIgnoreCase("TABLE")) { //TABLE
        			
        			if(logica.bdSeleccionada()) {
        			
        				comandoTable(sentencia);
        			
        			}else {
        				
        				ven.insertarDepuracion("Error #15", "Debe seleccionar la base de datos sobre la cual operar");
        				
        			}
        			
        		}else if(sentencia.get(1)[0].equalsIgnoreCase("DATABASE")) { //DATABASE
        			
        			comandoDatabase(sentencia);
        			
        		}else {
        			
        			ven.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es v�lido");
		        	
        		}
        		
        	}
        	
		}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto llamada (sentencia) y procesa el comando "SELECT" para realizar una consulta en una tabla. El metodo v�lida la sentencia, el tipo de atributo y la condici�n. Luego, muestra los resultados de la consulta si es v�lida.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private void comandoSelect(ArrayList<String[]> sentencia) {
		VentanaPrincipal ven = new VentanaPrincipal(logica);
		
		if(!(validaCantidadArgumentos(sentencia, 0, 0, 2))) {
    		
        	ven.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en l�nea 1");
        	
    	}else {
    		
    		if(!(validaCantidadLineas(sentencia, 2, 3))) {
    			
	        	ven.insertarDepuracion("Error #02", "Cantidad de l�neas incorrecta");
	        	
    		}else {
    		
    			if( validaSentenciasFrom(sentencia)) {
    				
    				if(validaCantidadLineas(sentencia, 2, 2)) { //SI ES SIN WHERE
    					
    					if(logica.existeTabla(sentencia.get(1)[1])) {
	            			
	            			if(logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])!=null) {
	                		
	            				if(!(logica.realizarConsultaSinWhere(sentencia.get(1)[1], sentencia.get(0)[1]).isEmpty())){
	            				
			    					ArrayList<DTOAtributo> atributos=logica.realizarConsultaSinWhere(sentencia.get(1)[1], sentencia.get(0)[1]);
			    					ven.cargarTablaAtributos(atributos, sentencia.get(0)[1]);
						        	aciertos++;
						        	ven.insertarDepuracion("Acierto #" + aciertos, "Consulta �xitosa, mostrando resultados para la tabla: " + sentencia.get(1)[1]);
		            			
	            				}else {
		    	        			
	            					ven.insertarDepuracion("Error #13", "No hay registros que mostrar para la consulta realizada");
		            				
		            			}
	            				
	            			}else {
	    	        			
	            				ven.insertarDepuracion("Error #23", "El atributo no existe para tabla ingresada");
	    	        		
	            			}
		            		
	            		}else {
	            			
	            			ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
	                    	
	            		}	
    					
    				}else {
    			
	            		if(sentencia.get(2).length==4) { //SI ES WHERE COMUN
	            	
			            	if( validaSentenciasWhereComun(sentencia)) {
			            		
			            		if(logica.existeTabla(sentencia.get(1)[1])) {
			            			
			            			if(logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])!=null) {
			                			
					            		if(logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //v�lido que el tipo de atributo y condici�n coincidan
					            			
					            			if(logica.realizarConsultaClasica(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2]).isEmpty()) { //v�lido que hayan registros que mostrar para la condici�n dada
					            				
					            				ven.insertarDepuracion("Error #13", "No hay registros que mostrar para la consulta realizada");
					            				
					            			}else {
					            				
					            				ArrayList<DTOAtributo> atributos=logica.realizarConsultaClasica(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2]);
					            				ven.cargarTablaAtributos(atributos, sentencia.get(0)[1]);
					            				aciertos++;
									        	ven.insertarDepuracion("Acierto #" + aciertos, "Consulta �xitosa, mostrando resultados para la tabla: " + sentencia.get(1)[1]);
									        	
					            			}
					            			
					            		}else {
					            			
					            			ven.insertarDepuracion("Error #14", "El tipo de atributo y el tipo de condici�n no coinciden");
				            				
					            		}
					            		
			            			}else {
			    	        			
			            				ven.insertarDepuracion("Error #23", "El atributo no existe para tabla ingresada");
			    	        		
			            			}
				            		
			            		}else {
			            			
			            			ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
			                    	
			            		}
			            		
			            	}	
			            	
			            }else {
			            		
		            		if(validaOperadoresLogicos(sentencia)) {
		            			
			            		if(sentencia.get(2)[4].equalsIgnoreCase("AND")) {
			            			
			            			comandoSelectAnd(sentencia);
			            			
			            		} else if(sentencia.get(2)[4].equalsIgnoreCase("OR")) {
			            			
			            			comandoSelectOr(sentencia);
			            			
			            		}else {
			            			
			            			ven.insertarDepuracion("Error #5", "El operador " + sentencia.get(2)[4] + " no es v�lido");
			                    	
			            		}
			            		
		            		}
		            		
			            }
	            		
    				}
    			}
    		}
    	}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "USE" para seleccionar una base de datos. El metodo verifica si la base de datos especificada existe y la selecciona si es v�lida.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private void comandoUse(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
			if(logica.existeBD(sentencia.get(0)[1])) {
				
				logica.seleccionarBaseDatos(sentencia.get(0)[1]);
				VentanaPrincipal ven = new VentanaPrincipal(logica);
				ven.cargarBDActual();
	    		aciertos++;
	    		ven.insertarDepuracion("Acierto #" + aciertos, "Se selecciono la base de datos: " + logica.getBaseDatos());
	        	
			}else {
				
				ven.insertarDepuracion("Error #17", "La base de datos ingresada no existe para el usuario logueado");
	        	
			}
        	
    	}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "INSERT" para agregar datos a una tabla.  El metodo verifica la validez de la sentencia, los argumentos y los datos a insertar. Luego, agrega los datos si son v�lidos.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private void comandoInsert(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadLineas(sentencia, 2, 2))){
			
			ven.insertarDepuracion("Error #02", "Cantidad de l�neas no v�lida");
    		
    	}else {
    		
    		if ((!(sentencia.get(1).length>1)) || (!(validaCantidadArgumentos(sentencia, 0, 0, 2)))) {
        		
    			ven.insertarDepuracion("Error #03", "Cantidad de argumentos no v�lida");
	        	
        	}else { 
        		
        		if(!(sentencia.get(1)[0].equalsIgnoreCase("VALUES"))) {
            			
        			ven.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es v�lido");
		        	
            	}else {
            		
            		if(logica.existeTabla(sentencia.get(0)[1])) {
            		
            			ArrayList<String> atributos = new ArrayList<>();
            			
            			for (int i = 1; i < sentencia.get(1).length; i++) {
            				
            			    atributos.add(sentencia.get(1)[i]);
            			    
            			}
            			
	            		if(logica.validaCantidadAtributos(sentencia.get(0)[1], atributos)) {
	            		
	            			if(logica.validaAtributos(sentencia.get(0)[1], atributos)) {
	            				
	            				if(logica.validaNotNull(sentencia.get(0)[1], atributos)) {
	            					
	            					if(logica.validaClave(sentencia.get(0)[1], atributos)) {
	            						
	            						LinkedHashMap<String, DTOAtributo> registro=logica.generarArrayListRegistro(sentencia.get(0)[1], atributos);
	            			        	logica.ingresarRegistro(sentencia.get(0)[1], registro);
	            			        	aciertos++;
	            			        	ven.insertarDepuracion("Acierto #" + aciertos, "Se ingresaron los datos con �xito, en la tabla: " + sentencia.get(0)[1]);
	            			        	
	            					}else {
	            						
	            						ven.insertarDepuracion("Error #18", "La clave primaria no puede quedar vacia ni repetirse");
	            						
	            					}
	            					
	            				}else {
	            					
	            					ven.insertarDepuracion("Error #19", "Ingreso como nulos atributos no permitidos");
	                				
	            				}
	            				
	            			}else {
	            				
	            				ven.insertarDepuracion("Error #20", "Los datos a ingresar son incorrectos para los atributos de la tabla");
	            				
	            			}
	            			
	            		}else {
	            			
	            			ven.insertarDepuracion("Error #21", "Cantidad de atributos incorrecta, en caso de nulos use NULL");
	        				
	            		}
	            		
            		}else {
            			
            			ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
                    	
            		}
            	}
        	}
    	}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "DELETE" para eliminar registros de una tabla.  El metodo v�lida la sentencia, el tipo de atributo y la condici�n antes de realizar la eliminacion.
	 * @param sentencia->lista de sentencia
	 * 
	 */
	private void comandoDelete(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { 
    		
			ven.insertarDepuracion("Error #03", "Demasiados argumentos en l�nea 1");
        	
    	}else {
    	
        	if( validaSentenciasFromWhere(sentencia)) {
        		
        		if(logica.existeTabla(sentencia.get(1)[1])) {
        		
	        		if(logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //v�lido que el tipo de atributo y condici�n coincidan
	        			
	        			if(logica.hayRegistros(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2])) { //v�lido que hayan registros que mostrar para la condici�n dada
	        				
	        				logica.borrarRegistro(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2]);
	        				aciertos++;
	        				ven.insertarDepuracion("Acierto #" + aciertos, "Se eliminaron los datos con �xito, en la tabla: " + sentencia.get(1)[1]);
	        	        	
	        			}else {
	        				
	        				ven.insertarDepuracion("Error #13", "No hay registros que coincidan con los par�metros de la b�squeda");
	        				
	        			}
	        			
	        		}else {
	        			
	        			ven.insertarDepuracion("Error #14", "El tipo de atributo y el tipo de condici�n no coinciden");
	    				
	        		}
	        		
        		}else {
        			
        			ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
                	
        		}
        	}
    	}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "UPDATE" para modificar registros en una tabla.  El metodo v�lida la sentencia, los argumentos y los datos a actualizar antes de realizar la modificacion.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private void comandoUpdate(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadLineas(sentencia, 3, 3))) {
    		
			ven.insertarDepuracion("Error #02", "La cantidad de l�neas ingresada es incorrecta");
        	
    	}else {
    	
        	if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) { 
        		
        		ven.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en l�nea 1");
	        	
        	}else {
        	
            	if(!(validaCantidadArgumentos(sentencia, 1, sentencia.size(), 4))) {
            		
            		ven.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta entre las l�neas 2 y 3");
		        	
            	}else {
            		
            		if(!(sentencia.get(1)[0].equalsIgnoreCase("SET"))) {
            			
            			ven.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es v�lido");
			        	
            		}else {
            			
	            		if( validaSentenciasWhereComun(sentencia)) {
	            			
	            			if(!(sentencia.get(1)[2].equals("="))){
	            				
	            				ven.insertarDepuracion("Error #05", "El operador: " + sentencia.get(1)[2] + " no es v�lido");
					        	
	            			}else {
	            		
	            				if(logica.existeTabla(sentencia.get(0)[1])) {
	            				
		            				if((logica.tieneClave(sentencia.get(0)[1])) && (logica.obtenerClave(sentencia.get(0)[1]).equalsIgnoreCase((sentencia.get(1)[1])))) {
		            					
		            					ven.insertarDepuracion("Error #22", "No es posible cambiar la clave primaria de un registro");
			            					
			            			}else if(logica.validaCondicion(sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3])){
			            				
			            				if(logica.hayRegistros(sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2])){	
				            				
			            					logica.cambiarRegistro(sentencia.get(0)[1], sentencia.get(1)[1], sentencia.get(1)[3], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2]);
				            				aciertos++;
				            				ven.insertarDepuracion("Acierto #" + aciertos, "Se modificaron los datos con �xito, en la tabla: " + sentencia.get(0)[1]);
				            				
			            				}else {
			            					
			            					ven.insertarDepuracion("Error #13", "No hay registros que coincidan con los par�metros de la b�squeda");
			            					
			            				}
			            				
			            			}else {
			            				
			            				ven.insertarDepuracion("Error #14", "El tipo de atributo y el tipo de condici�n no coinciden");
			            				
			            			}
		            				
	            				}else {
	            					
	            					ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
	            		        	
	            				}
	            			}
	            		}
            		}
            	}
        	}
    	}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "NOT NULL" para especificar que un atributo en una tabla no puede ser nulo.  El metodo v�lida la sentencia y realiza la operaci�n si es v�lida.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private void comandoNotNull(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasDosLineas(sentencia)) {
    		
			if(logica.existeTabla(sentencia.get(1)[1])) {
			
				if(logica.esVacia(sentencia.get(1)[1])) { 
					
					if((logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])==null)) {
						
						ven.insertarDepuracion("Error #23", "El atributo no existe para tabla ingresada");
					
					}else {
						
						logica.hacerNotNull(sentencia.get(1)[1], sentencia.get(0)[1]);
						aciertos++;
						ven.insertarDepuracion("Acierto #" + aciertos, "Se indico como no nulo con �xito el atributo: " + sentencia.get(0)[1] + " de la tabla: " + sentencia.get(1)[1]);
			        	
					}
		        	
				}else {
				
					ven.insertarDepuracion("Error #24", "La tabla no debe de tener registros para la operaci�n a realizar");
					
				}
			
			}else {
				
				ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
	        	
			}
			
    	}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "COUNT" para contar la cantidad de registros que cumplen con una consulta en una tabla.  El metodo verifica la validez de la sentencia, el tipo de atributo y la condici�n antes de realizar el conteo.
	 * @param sentencia-> lista de sentencias
	 */
	private void comandoCount(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { 
    		
			ven.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en l�nea 1");
        	
    	}else {
    	
        	if( validaSentenciasFromWhere(sentencia)) {
        		
        		if(logica.existeTabla(sentencia.get(1)[1])) {
        		
	        		if(logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //v�lido que el tipo de atributo y condici�n coincidan
	        			
	        			if(logica.hayRegistros(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2])) { //v�lido que hayan registros que mostrar para la condici�n dada
	        				
	        				aciertos++;
	        				ven.insertarDepuracion("Acierto #" + aciertos, "La cantidad de registros que cumplen con la consulta es: " + logica.contarRegistros(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2]));
	            			
	        				
	        			}else {
	        				
	        				ven.insertarDepuracion("Error #13", "No hay registros que coincidan con los par�metros de la b�squeda");
	        				
	        			}
	        			
	        		}else {
	        			
	        			ven.insertarDepuracion("Error #14", "El tipo de atributo y el tipo de condici�n no coinciden");
	    				
	        		}
	        		
        		}else {
        			
        			ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
                	
        		}
        		
        	}
        	
    	}
		
	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "AVG" para calcular el promedio de los valores de un atributo en una tabla.  El metodo verifica la validez de la sentencia, el tipo de atributo y la condici�n antes de realizar el calculo.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private void comandoAvg(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) { 
    		
        	ven.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en l�nea 1");
        	
    	}else {
    	
        	if( validaSentenciasFromWhere(sentencia)) {
        		
        		if(logica.existeTabla(sentencia.get(1)[1])) {
        		
        			if(logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])!=null) {
        			
		        		if(logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //v�lido que el tipo de atributo y condici�n coincidan
		        			
		        			if(logica.hayRegistros(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2])) { //v�lido que hayan registros que mostrar para la condici�n dada
		        				
		        				if(logica.obtenerTipoAtributo(sentencia.get(1)[1], sentencia.get(0)[1]).equalsIgnoreCase("entero")) {
		                					
		        					aciertos++;
		                	       	ven.insertarDepuracion("Acierto #" + aciertos, "El promedio de los registros consultados es: " + logica.calcularPromedioRegistros(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2]));
		                            
	            				}else {
	            					
	            					ven.insertarDepuracion("Error #25", "El atributo debe de ser de tipo entero para esta operaci�n");
	            					
	            				}
		                				
	            			}else {
	            				
	            				ven.insertarDepuracion("Error #13", "No hay registros que coincidan con los par�metros de la b�squeda");
	            				
	            			}
	            		
	        			}else {
		        			
		        			ven.insertarDepuracion("Error #14", "El tipo de atributo y el tipo de condici�n no coinciden");
		    				
		        		}
		        		
        			}else {
	        			
        				ven.insertarDepuracion("Error #23", "El atributo no existe para tabla ingresada");
	        		}
	        			
        		}else {
        			
        			ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
                	
        		}
        	}
    	}
		
	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "MAX" para encontrar el valor m�ximo de un atributo en una tabla.  El metodo verifica la validez de la sentencia, el tipo de atributo y la presencia de registros antes de buscar el valor m�ximo.
	 * @param sentencia-> lista de sentencias
	 */
	private void comandoMax(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasDosLineas(sentencia)) {
    		
			if(logica.existeTabla(sentencia.get(1)[1])) {
				
				if(logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])!=null) {
        			
					if(logica.obtenerTipoAtributo(sentencia.get(1)[1], sentencia.get(0)[1]).equalsIgnoreCase("entero")) {
					
						if(logica.esVacia(sentencia.get(1)[1])) { 
							
		    				ven.insertarDepuracion("Error #26", "No hay registros ingresados aun en la tabla");
		    				
		    			}else {
						
	    					aciertos++; 
	        				ven.insertarDepuracion("Acierto #" + aciertos, "El valor m�ximo de los registros consultados es: " + logica.obtenerMaximo(sentencia.get(1)[1], sentencia.get(0)[1]));
	        				
		    			}
						
					}else {
						
						ven.insertarDepuracion("Error #25", "El atributo debe de ser de tipo entero para esta operaci�n");
						
					}
				
				}else {
        			
    				ven.insertarDepuracion("Error #23", "El atributo no existe para tabla ingresada");
        		
				}
				
			}else {
				
				ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
	        	
			}
        	
        }
		
	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "MIN" para encontrar el valor m�nimo de un atributo en una tabla.  El metodo verifica la validez de la sentencia, el tipo de atributo y la presencia de registros antes de buscar el valor m�nimo.
	 * @param sentencia->lista de sentencia
	 */
	private void comandoMin(ArrayList<String[]> sentencia) {
		
		if(logica.existeTabla(sentencia.get(1)[1])) {
		
			if(logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])!=null) {
    		
				if(logica.obtenerTipoAtributo(sentencia.get(1)[1], sentencia.get(0)[1]).equals("entero")) {
					
					if(logica.esVacia(sentencia.get(1)[1])) { 
						
						ven.insertarDepuracion("Error #26", "No hay registros ingresados aun en la tabla");
						
					}else {
				
	    				aciertos++;
	    				ven.insertarDepuracion("Acierto #" + aciertos, "El valor m�nimo de los registros consultados es: " + logica.obtenerMinimo(sentencia.get(1)[1], sentencia.get(0)[1]));
	    		        
	        				
	        		}
		        	
				}else {
					
					ven.insertarDepuracion("Error #25", "El atributo debe de ser de tipo entero para esta operaci�n");
					
				}
				
			}else {
    			
				ven.insertarDepuracion("Error #23", "El atributo no existe para tabla ingresada");
    		}
			
		}else {
			
			ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
        	
		}
		
	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "PRIMARY KEY" para especificar un atributo como clave primaria en una tabla. . El metodo verifica la validez de la sentencia y realiza la operaci�n si es v�lida.
	 * @param sentencia->lista de sentencias
	 */
	private void comandoPrimaryKey(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasDosLineas(sentencia)) {
    		
			if(logica.existeTabla(sentencia.get(1)[1])) {
			
				if(logica.esVacia(sentencia.get(1)[1])) { 
					
					if(logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])!=null) {
						
						if (logica.tieneClave(sentencia.get(1)[1])){
							
							logica.quitarClave(sentencia.get(1)[1]);
							
						}
						
						logica.hacerClave(sentencia.get(1)[1], sentencia.get(0)[1]);
						aciertos++;
			        	ven.insertarDepuracion("Acierto #" + aciertos, "Se hizo clave primaria con �xito el atributo: " + sentencia.get(0)[1] + " de la tabla: " + sentencia.get(1)[1]);
						
					}else {
	        			
        				ven.insertarDepuracion("Error #23", "El atributo no existe para tabla ingresada");
	        		}
						
				}else {
				
					ven.insertarDepuracion("Error #24", "La tabla no debe de tener registros para la operaci�n a realizar");
					
				}
				
			}else {
				
				ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
	        	
			}
			
    	}
		
	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y 
	 * procesa el comando "DESCRIBE" para mostrar informaci�n sobre la estructura de una tabla.  
	 * El metodo verifica si la tabla especificada existe y muestra su descripcion.
	 * @param sentencia->lista de sentencias
	 */
	private void comandoDescribe(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
			
			String nombreTabla = logica.obtenerNomTabla(sentencia.get(0)[1]);
			
			if(logica.existeTabla(nombreTabla)) {
			
				ven.cargarTablaString(logica.describeTabla(sentencia.get(0)[1]), "informaci�n de la tabla " + nombreTabla);
				aciertos++;
				ven.insertarDepuracion("Acierto #" + aciertos, "Se muestra la informaci�n de la tabla: " + nombreTabla);
        	
			}else {
				
				ven.insertarDepuracion("Error #16", "La tabla ingresada no existe para la base de datos seleccionada");
	        	
			}
    	}
		
	}
	


	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "HELP" para mostrar informaci�n de ayuda sobre un comando especifico.  El metodo verifica si el comando especificado existe y muestra su ayuda asociada.
	 * @param sentencia->lista de sentencia
	 */
	private void comandoHelp(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
			if(logica.comandoExiste(sentencia.get(0)[1])) {
					
				ArrayList<String> ayuda = new ArrayList<String>();
				ayuda.add(logica.darAyuda(sentencia.get(0)[1]));
				ven.cargarTablaString(ayuda, "AYUDA " + sentencia.get(0)[1].toUpperCase());
				aciertos++;
				ven.insertarDepuracion("Acierto #" + aciertos, "Mostrando ayuda para el comando: " + sentencia.get(0)[1].toUpperCase());
        	
			}else {
				
				ven.insertarDepuracion("Error #27", "El comando ingresado no existe o no tiene ayuda asociada");
				
			}
    	}
		
	}
	
	/**
	 * Realiza un join natural entre dos tablas basado en una sentencia.
	 * 
	 * Este es un metodo privado que recibe una lista de matrices de cadenas de texto (sentencia)
	 * y procesa el comando "JOIN NATURAL" para realizar un join natural entre dos tablas.
	 * El metodo verifica la validez de la sentencia y si las tablas especificadas existen.
	 * @param sentencia Lista de sentencias a procesar.
	 */
	private void comandoJoinNatural(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) { 
    		
        	ven.insertarDepuracion("Error #03", "Demasiados argumentos en l�nea 1");
        	
    	}else {
    		
    		if(!(validaCantidadLineas(sentencia, 2, 2))) {
    			
    			ven.insertarDepuracion("Error #02", "Cantidad de l�neas no v�lida");
	        	
    		}else {
    			
    			if(!(sentencia.get(1)[0].equalsIgnoreCase("FROM"))) {
    				
    				ven.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es v�lido");
		        	
    			}else {
    			
        			if(!(validaCantidadArgumentos(sentencia, 1, sentencia.size(), 3))) {
        				
        				ven.insertarDepuracion("Error #03", "Cantidad de argumentos no v�lida en l�nea 2, recuerde que el join natural se realiza entre dos tablas");
			        	
        			}else {
        				
        				if((logica.existeTabla(sentencia.get(1)[1])) && (logica.existeTabla(sentencia.get(1)[2]))) {
        					
        					if((logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])!=null) || (logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[2])!=null)) {
        						
        						if(logica.joinNatural(sentencia.get(1)[1], sentencia.get(1)[2], sentencia.get(0)[1]).isEmpty()){
        							
        							ven.insertarDepuracion("Error #13", "No hay registros que mostrar para la consulta realizada");
		            				
        						}else {
        						
        							ven.cargarTablaAtributos(logica.joinNatural(sentencia.get(1)[1], sentencia.get(1)[2], sentencia.get(0)[1]), sentencia.get(0)[1]);
	            					aciertos++;
	            					ven.insertarDepuracion("Acierto #" + aciertos, "Consulta �xitosa, mostrando resultados del join natural entre las tablas: " + sentencia.get(1)[1] + " y " + sentencia.get(1)[2]);
	    			        	
        						}
        						
        					}else {
        	        			
        						ven.insertarDepuracion("Error #23", "El atributo no existe en ning�na de las tablas ingresadas");
        	        		}
        					
        				}else {
        					
        					ven.insertarDepuracion("Error #16", "La/s tabla/s ingresada/s no existe/n para la base de datos seleccionada");
        		        	
        				}
        			}
    			}
    		}
    	}
		
	}
	
	/**
	 * Metodo publico que recibe una cadena de texto que representa el comando a ejecutar y una lista de matrices de cadenas de texto (sentencia).  El metodo ejecuta el comando especificado y las operaciones asociadas, verificando si se selecciono una base de datos y si el comando es v�lido. Tambien muestra resultados o mensajes de error en la tabla de depuracion.
	 * @param comando-> comando que se ejeuctura
	 * @param sentencia->lista de sentencia
	 */
	public void ejecutarComando(String comando, ArrayList<String[]> sentencia) {
		
	    if (acciones.containsKey(comando)) {
	    	
	    	if ((comando.equals("USE")) || (comando.equals("HELP") || (comando.equals("CREATE") || (comando.equals("DROP"))))) {
		    	
		        acciones.get(comando).accept(sentencia);
		        
		    } else {
		    	
		        if (logica.bdSeleccionada()) {
		        	
		            acciones.get(comando).accept(sentencia);
		            
		        } else {
		        	
		        	ven.insertarDepuracion("Error #15", "Debe seleccionar la base de datos sobre la cual operar");
		            
		        }
		        
		    }
	       
	    	if(Login.demo) {
	    		
		        usos++;
		    
	    	}
	    	
	    } else {
	    	
	    	ven.insertarDepuracion("Error #01", "El comando " + comando + " no es v�lido");
	        
	    }
	    
	}

}