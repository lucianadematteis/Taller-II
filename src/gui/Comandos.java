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
    
    /**
     * Metodo para inicializar las acciones
     * @param fa-> inicializa la logica del programa para inicializar acciones
     */
    public Comandos(IFachadaLogica fa) {
    	
    	logica=fa;
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
	 *Metodo privado que recibe como parametros una lista de DTOAtributo y el nombre de la columna y los carga en una tabla en la interfaz grafica con los atributos proporcionados.
	 *@param atributos-> lista de DTOAtributo
	 *@param nombreAtributo-> nombre de la columna
	 *
	 */
    private void cargarTablaAtributos(ArrayList<DTOAtributo> atributos, String nombreAtributo) {

    	DefaultTableModel model = (DefaultTableModel) VentanaPrincipal.salida.getModel();
    	model.setRowCount(0);
    	model.setColumnCount(0);
    	model.addColumn("<html><b>" + nombreAtributo.toUpperCase() + "</b></html>");
    
    	// Establece la altura de todas las filas a 20
    	VentanaPrincipal.salida.setRowHeight(22);

    	for(DTOAtributo atr : atributos) {

    		if (atr instanceof DTOCadena) {

    			DTOCadena cadena = (DTOCadena) atr;
    			String dato = cadena.getDato();
    			model.addRow(new Object [] {dato});

    		} else if (atr instanceof DTOEntero) {

    			DTOEntero entero = (DTOEntero) atr;
    			Integer valor = entero.getValor();
    			model.addRow(new Object [] {valor});

    		}

    	}

    }
    
	 /**
	  * Metodo privado que recibe como parámetros una lista de cadenas y el nombre de la columna y los carga en una tabla en la interfaz grafica.
	  * @param datos-> lista de cadenas
	  * @param nombreColumna-> nombre de la columna a cargar en una tabla
	  * 
	  */
    @SuppressWarnings("serial")
	private void cargarTablaString(ArrayList<String> datos, String nombreColumna) {

        DefaultTableModel model = (DefaultTableModel) VentanaPrincipal.salida.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
        model.addColumn("<html><b>" + nombreColumna.toUpperCase() + "</b></html>");
       Font customFont = new Font("Verdana", Font.PLAIN, 17);
        
        // Crea un TableCellRenderer personalizado para interpretar HTML y ajustar la altura
        DefaultTableCellRenderer htmlRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
               
            	JLabel label = new JLabel();
            	label.setFont(customFont);
                label.setText("<html>" + value.toString() + "</html>");
                table.setRowHeight(row, label.getPreferredSize().height);
                return label;
            
            }
            
        };

        // Asigna el TableCellRenderer personalizado a la columna
        VentanaPrincipal.salida.getColumnModel().getColumn(0).setCellRenderer(htmlRenderer);

        for (String dato : datos) {
        	
            model.addRow(new Object[]{dato});
            
        }
        
    }
    
	 /**
	  * Metodo privado que recibe como parámetros dos cadenas de texto que corresponden a mensajes. El metodo inserta una fila en una tabla de depuracion en la interfaz grafica.
	  * @param mensaje1->cadena de texto
	  * @param mensaje2->cadena de texto
	  * 
	  */
    private void insertarDepuracion(String mensaje1, String mensaje2) {

		DefaultTableModel model = (DefaultTableModel) VentanaPrincipal.depuracion.getModel();

		Object[] nuevaFila = { mensaje1, mensaje2 };
		model.insertRow(0, nuevaFila);

	}
    
    /**
    * 
    * Metodo privado que recibe como parámetros una lista de matrices de cadenas de texto (la sentencia), dos indices de posicion (inicial y final) y un numero entero que representa la cantidad de argumentos.  El metodo válida si la cantidad de argumentos en las l�neas de una sentencia esta dentro de un rango especifico y retorna true si es válida o false en caso contrario.
    * @param sentencia-> lista de sentencias
    * @param posInicial->indice
    * @param posFinal->indice
    * @param cantArgumentos->cantidad de argumentos
    * @return válida la cantidad de argumentos válidas para realizar la sentencia
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
	 * Metodo privado que recibe como parametro una cadena de texto y retorna true si el tipo de dato es válido (cadena o entero) o false en caso contrario.
	 * @param tipo->cadena de texto
	 * @return válida que el tipo de dato sea entero o cadena
	 * 
	 */
	private boolean validaTipoDato(String tipo) {

		return ((tipo.equalsIgnoreCase("ENTERO")) || (tipo.equalsIgnoreCase("CADENA")));

	}
	
	/**
	 * Metodo privado que recibe como parámetros una lista de matrices de cadenas de texto (sentencia) y dos indices de posicion (inicial y final). El metodo retorna true si los tipos de atributos (entero o cadena) en el rango especificado son válidos y false en caso contrario.
	 * @param sentencia-> lista de sentencias
	 * @param posInicial->indice
	 * @param posFinal->indice
	 * @return válida el tipo de atributo en el rango de indice.
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
	 * @return válida la cantidad de l�neas se encuentra dentro del rango especificado
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
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y retorna true si la sentencia de una sola l�nea es válida o false en caso contrario.
	 * @param sentencia->lista de sentencias
	 * @return válida si la sentencia es válida
	 * 
	 */
	private boolean validaSentenciasUnaLinea(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 1, 1))) {

			insertarDepuracion("<html><font color='red'>Error #02</font></html>", "<html><font color='red'><html><font color='red'>La cantidad de líneas ingresada es incorrecta</font></html>");

		} else {

			if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) {

				insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos no válida</font></html>");

			} else {

				return true;

			}

		}

		return false;

	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y retorna true si la sentencia de dos l�neas es válida o false en caso contrario.
	 * @param sentencia-> lista de sentencias
	 * @return válida si la sentencia es válida
	 */
	private boolean validaSentenciasDosLineas(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 2, 2))) {

			insertarDepuracion("<html><font color='red'>Error #02</font></html>", "<html><font color='red'>La cantidad de líneas ingresada es incorrecta</font></html>");

		} else {

			if (!(validaCantidadArgumentos(sentencia, 0, sentencia.size(), 2))) {

				insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos no válida</font></html>");

			} else {

				if (!(sentencia.get(1)[0].equalsIgnoreCase("FROM"))) {

					insertarDepuracion("<html><font color='red'>Error #01</font></html>", "<html><font color='red'>El comando: " + sentencia.get(1)[0].toUpperCase() + " no es válido</font></html>");

				} else {

					return true;

				}

			}

		}

		return false;

	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y retorna true si la sentencia con "WHERE" es válida o false en caso contrario.
	 * @param sentencia->lista de sentencias
	 * @return válida la sentencia de WHERE
	 * 
	 */
	private boolean  validaSentenciasWhereComun(ArrayList<String[]> sentencia) {

		ArrayList<String> operadores = new ArrayList<>(Arrays.asList("=", "<", ">", ">=", "<="));

		if (!(validaCantidadArgumentos(sentencia, 2, 2, 4))) {

			insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos no válida en línea 3</font></html>");

		} else {

			if (!(sentencia.get(2)[0].equalsIgnoreCase("WHERE"))) {

				insertarDepuracion("<html><font color='red'>Error #01</font></html>", "<html><font color='red'>El comando: " + sentencia.get(2)[0].toUpperCase() + " no es válido</font></html>");

			} else {

				if (!(operadores.contains(sentencia.get(2)[2]))) {

					insertarDepuracion("<html><font color='red'>Error #05</font></html>", "<html><font color='red'>El operador: " + sentencia.get(2)[2] + " no es válido</font></html>");

				} else {

					return true;

				}
			}

		}

		return false;

	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y retorna true si la sentencia con "FROM" es válida o false en caso contrario.
	 * @param sentencia->lista de sentencias
	 * @return válida la sentencia FROM
	 * 
	 */
	private boolean  validaSentenciasFrom(ArrayList<String[]> sentencia) {

		if (!(validaCantidadArgumentos(sentencia, 1, 1, 2))) {

			insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos no válida en la línea 2</font></html>");

		} else {

			if (!(sentencia.get(1)[0].equalsIgnoreCase("FROM"))) {

				insertarDepuracion("<html><font color='red'>Error #01</font></html>", "<html><font color='red'>El comando: " + sentencia.get(1)[0].toUpperCase() + " no es válido</font></html>");

			} else {

				return true;

			}
		}

		return false;

	}

	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y retorna true si la sentencia con "FROM" y "WHERE" es válida o false en caso contrario.
	 * @param sentencia->lista de sentencias
	 * @return válida la sentencia del FROM con WHERE
	 * 
	 */
	private boolean  validaSentenciasFromWhere(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 3, 3))) {

			insertarDepuracion("<html><font color='red'>Error #02</font></html>", "<html><font color='red'>La cantidad de líneas ingresada es incorrecta</font></html>");

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
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y retorna true si los operadores lOgicos en la sentencia son válidos.
	 * @param sentencia-> lista de sentencias
	 * @return válida los operadores logicos de la sentencia
	 * 
	 */
	private boolean validaOperadoresLogicos(ArrayList<String[]> sentencia) {

		 List<String> operadores = Arrays.asList("=", "<", ">", "<=", ">=");
		
		if (!(validaCantidadArgumentos(sentencia, 2, 2, 8))) {

			insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos no válida en la línea 3</font></html>");

		} else {

			if (!(operadores.contains(sentencia.get(2)[2])) || !(operadores.contains(sentencia.get(2)[6]))) {

				insertarDepuracion("<html><font color='red'>Error #05</font></html>", "<html><font color='red'>Operador/es no válido en la línea 3</font></html>");

			} else {

				return true;

			}

		}

		return false;

	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y válida la estructura de la misma: cantidad de l�neas, cantidad de argumentos,  tipos de datos y si existe la tabla en la base de datos para luego crearla con los atributos especificados.
	 * @param sentencia-> lista de sentencias
	 * 
	 */
	private void comandoTable(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadLineas(sentencia, 2, 5))) {
			
			insertarDepuracion("<html><font color='red'>Error #02</font></html>", "<html><font color='red'>La cantidad de líneas ingresada es incorrecta, recuerde que se permiten de uno a tres atributos por tabla</font></html>");
        	
		}else {
			
		    if(!(validaCantidadArgumentos(sentencia, 1, sentencia.size(), 2))) {
			
		    	insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos incorrecta entre las líneas 2 y " + sentencia.size() + "</font></html>");
	        	
			}else if(sentencia.get(0)[0].equalsIgnoreCase("CREATE")){ //SI QUIERE CREAR UNA TABLA
				
				if(!(validaTiposAtributos(sentencia, 2, sentencia.size()))) {
					
					insertarDepuracion("<html><font color='red'>Error #04</font></html>", "<html><font color='red'>Tipos de datos incorrectos, recuerde que solo se admiten datos de tipo entero o cadena</font></html>");
		        	
				}else {
					
					if(logica.existeTabla(sentencia.get(1)[1])) {
						
						insertarDepuracion("<html><font color='red'>Error #11</font></html>", "<html><font color='red'>La tabla ingresada ya existe para la base de datos seleccionada</font></html>");
			        	
					}else {
						
						LinkedHashMap<String, String> atributos = new LinkedHashMap<String, String>();

						for (int i = 0; i < sentencia.size(); i++) {
							
						    if (sentencia.get(i) != null && sentencia.get(i).length >= 2 && sentencia.get(i)[0] != null && sentencia.get(i)[1] != null) {
						       
						    	atributos.put(sentencia.get(i)[0], sentencia.get(i)[1]);
						    
						    }
						    
						}

						DTOTabla tabla = new DTOTabla(sentencia.get(1)[1]);
						
						logica.crearTabla(tabla, atributos);
						VentanaPrincipal.cargarTablas(logica.obtenerTablasNom());
						aciertos++;
						insertarDepuracion("Acierto #" + aciertos, "Se ha ingresado con éxito la tabla: " +  sentencia.get(1)[1]);
			        	
					}
				}
				
			}else if(sentencia.get(0)[0].equalsIgnoreCase("DROP")){ //SI QUIERE BORRAR UNA TABLA
				
				if(logica.existeTabla(sentencia.get(1)[1])) {
					
					logica.eliminarTabla(sentencia.get(1)[1]);
					VentanaPrincipal.cargarTablas(logica.obtenerTablasNom());
					aciertos++;
					insertarDepuracion("Acierto #" + aciertos, "Se ha eliminado con éxito la tabla: " +  sentencia.get(1)[1]);
		        	
				}else {
					
					insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
                	
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
		
		if(!(validaCantidadLineas(sentencia, 2, 2))) {
			
        	insertarDepuracion("<html><font color='red'>Error #02</font></html>", "<html><font color='red'>La cantidad de líneas ingresada es incorrecta</font></html>");
        	
		}else {
		
			if(!(validaCantidadArgumentos(sentencia, 1, 1, 2))) {
				
	        	insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos incorrecta en la línea 2</font></html>");
	        	
			}else if(sentencia.get(0)[0].equalsIgnoreCase("CREATE")){ //SI QUIERE CREAR UNA BD
				
				if(logica.existeBD(sentencia.get(1)[1])){
					
					insertarDepuracion("<html><font color='red'>Error #12</font></html>", "<html><font color='red'>La base de datos ingresada ya existe para el usuario seleccionado</font></html>");
		        	
				}else {
					
					DTOBaseDatos bd =new DTOBaseDatos(sentencia.get(1)[1]);
					logica.crearBD(bd);
					VentanaPrincipal.cargarBasesDatos(logica.obtenerBasesNom());
					aciertos++;
		        	insertarDepuracion("Acierto #" + aciertos, "Se ha ingresado con éxito la base de datos: " + sentencia.get(1)[1]);
					
				}
			
			}else if(sentencia.get(0)[0].equalsIgnoreCase("DROP")){ //SI QUIERE BORRAR UNA BD
				
				if(logica.existeBD(sentencia.get(1)[1])) {
					
					if(logica.getBaseDatos().equalsIgnoreCase(sentencia.get(1)[1])) {
						
						logica.liberarMemoriaBaseDatos();
						VentanaPrincipal.bdActual.setText("Base de datos: ");
						
					}
					
					logica.eliminarBD(sentencia.get(1)[1]);
					VentanaPrincipal.cargarBasesDatos(logica.obtenerBasesNom());
					VentanaPrincipal.cargarTablas(null);
					insertarDepuracion("Acierto #" + aciertos, "Se ha eliminado con éxito la base de datos: " + sentencia.get(1)[1]);
					
				}else {
					
					insertarDepuracion("<html><font color='red'>Error #17</font></html>", "<html><font color='red'>La base de datos ingresada no existe para el usuario logueado</font></html>");
		        	
				}
				
			}
			
		}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "SELECT AND", que realiza una consulta en una tabla con una condición "AND".  El metodo verifica la validez de la sentencia, el tipo de atributo y condición, y muestra los resultados de la consulta si es válida. 
	 * @param sentencia-> lista de sentencias
	 * 
	 */
	private boolean comandoSelectAnd(ArrayList<String[]> sentencia) {
		
		boolean resultado= false;
		
		if(logica.existeTabla(sentencia.get(1)[1])) {
		
			if((logica.obtenerAtributo(sentencia.get(2)[1], sentencia.get(1)[1])!=null) && (logica.obtenerAtributo(sentencia.get(2)[5], sentencia.get(1)[1])!=null)) {
				
				if((logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3]) && (logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[5], sentencia.get(2)[7])))) { //válido que el tipo de atributo y condición coincidan
					
					if(logica.consultaAnd(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[5], sentencia.get(2)[7], sentencia.get(2)[2], sentencia.get(2)[7]).isEmpty()) { //válido que hayan registros que mostrar para la condición dada
						
						aciertos++;
						insertarDepuracion("Acierto #" + aciertos, "No hay registros que coincidan con los parámetros de la búsqueda");
						resultado = true;
						
					}else {
						
						ArrayList<DTOAtributo> atributos=logica.consultaAnd(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[5], sentencia.get(2)[7], sentencia.get(2)[2], sentencia.get(2)[6]);
			        	this.cargarTablaAtributos(atributos, sentencia.get(0)[1]);
						aciertos++;
			        	insertarDepuracion("Acierto #" + aciertos, "Consulta éxitosa, mostrando resultados para la tabla: " + sentencia.get(1)[1]);
			        	resultado = true;
			        	
					}
					
				}else {
					
					insertarDepuracion("<html><font color='red'>Error #14</font></html>", "<html><font color='red'>El tipo de atributo y el tipo de condición no coinciden</font></html>");
					
				}
				
			}else {
				
				insertarDepuracion("<html><font color='red'>Error #23</font></html>", "<html><font color='red'>El/los atributo/s no existe/n para tabla ingresada</font></html>");
			
			}
		
		}else {
			
			insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
        	
		}
		
		return resultado;
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "SELECT OR", que realiza una consulta en una tabla con una condición "OR". Verifica la validez de la sentencia, el tipo de atributo y condición, y muestra los resultados de la consulta si es válida.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private boolean comandoSelectOr(ArrayList<String[]> sentencia) {
		
		boolean resultado= false;
		
		if(logica.existeTabla(sentencia.get(1)[1])) {
		
			if((logica.obtenerAtributo(sentencia.get(2)[1], sentencia.get(1)[1])!=null) && (logica.obtenerAtributo(sentencia.get(2)[5], sentencia.get(1)[1])!=null)) {
			
				if((logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3]) || (logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[5], sentencia.get(2)[7])))) { //válido que el tipo de atributo y condición coincidan
					
					if(logica.consultaOr(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[5], sentencia.get(2)[7], sentencia.get(2)[2], sentencia.get(2)[7]).isEmpty()) { //válido que hayan registros que mostrar para la condición dada
						
						aciertos++;
						insertarDepuracion("Acierto #" + aciertos, "No hay registros que coincidan con los parámetros de la búsqueda");
						resultado = true;
						
					}else {
						
						ArrayList<DTOAtributo> atributos=logica.consultaOr(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[5], sentencia.get(2)[7], sentencia.get(2)[2], sentencia.get(2)[6]);
			        	this.cargarTablaAtributos(atributos, sentencia.get(0)[1]);
						aciertos++;
			        	insertarDepuracion("Acierto #" + aciertos, "Consulta éxitosa, mostrando resultados para la tabla: " + sentencia.get(1)[1]);
			        	resultado = true;
			        	
					}
					
				}else {
					
					insertarDepuracion("<html><font color='red'>Error #14</font></html>", "<html><font color='red'>El tipo de atributo y el tipo de condición no coinciden</font></html>");
					
				}
			
			}else {
				
				insertarDepuracion("<html><font color='red'>Error #23</font></html>", "<html><font color='red'>El/los atributo/s no existe/n para tabla ingresada</font></html>");
			
			}
		
		}else {
			
			insertarDepuracion("<html><font color='red'>Error #11</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
	    	
		}
		
		return resultado;
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "SHOW", que verifica la validez de la sentencia y muestra las tablas si es válida.
	 * @param sentencia-> lista de sentencias
	 * 
	 */
	private void comandoShow(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
    		if(!(sentencia.get(0)[1].equalsIgnoreCase("TABLES"))) {
            			
	        	insertarDepuracion("<html><font color='red'>Error #01</font></html>", "<html><font color='red'>El comando: " + sentencia.get(0)[1].toUpperCase() + " no es válido</font></html>");
	        	
            }else {
            	
            	ArrayList<String> tablas = logica.obtenerTablasNom();
            	cargarTablaString(tablas, "tablas");
            	aciertos++;
            	insertarDepuracion("Acierto #" + aciertos, "Consulta éxitosa, se muestran las tablas para la base de datos seleccionada");
            	
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
		
		if(!(sentencia.size()>1)){
			
    		insertarDepuracion("<html><font color='red'>Error #02</font></html>", "<html><font color='red'>La cantidad de líneas ingresada es incorrecta</font></html>");
        	
		}else {
			
        	if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { //no tiene mas nada alado del create
        		
        		insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Demasiados argumentos en línea 1</font></html>");
	        
        	}else {
        		
				if(sentencia.get(1)[1].matches("[a-zA-Z\\d\\-_ñ]+( [a-zA-Z\\d\\-_ñ]+)*")) { //habilita letras de la A la Z, -, _, espacios y la ñ. Pero evita que la cadena termine en un espacio 
        		
	        		if(sentencia.get(1)[0].equalsIgnoreCase("TABLE")) { //TABLE
	        			
	        			if(logica.bdSeleccionada()) {
	        			
	        				comandoTable(sentencia);
	        			
	        			}else {
	        				
	        				insertarDepuracion("<html><font color='red'>Error #15</font></html>", "<html><font color='red'>Debe seleccionar la base de datos sobre la cual operar</font></html>");
	        				
	        			}
	        			
	        		}else if(sentencia.get(1)[0].equalsIgnoreCase("DATABASE")) { //DATABASE
	        			
	        			comandoDatabase(sentencia);
	        			
	        		}else {
	        			
	        			insertarDepuracion("<html><font color='red'>Error #01</font></html>", "<html><font color='red'>El comando: " + sentencia.get(1)[0].toUpperCase() + " no es válido");
			        	
	        		}
	        	
				}else {
					
					insertarDepuracion("<html><font color='red'>Error #28</font></html>", "<html><font color='red'>Se han ingresado caracteres no permitidos, o último caracter es un espacio</font></html>");
		        	
				}
        		
        	}
        	
		}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto llamada (sentencia) y procesa el comando "SELECT" para realizar una consulta en una tabla. El metodo válida la sentencia, el tipo de atributo y la condición. Luego, muestra los resultados de la consulta si es válida.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private boolean comandoSelect(ArrayList<String[]> sentencia) {
		
		boolean resultado = false;
		
		if(!(validaCantidadArgumentos(sentencia, 0, 0, 2))) {
    		
        	insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos incorrecta en línea 1</font></html>");
        	
    	}else {
    		
    		if(!(validaCantidadLineas(sentencia, 2, 3))) {
    			
	        	insertarDepuracion("<html><font color='red'>Error #02</font></html>", "<html><font color='red'>Cantidad de líneas incorrecta</font></html>");
	        	
    		}else {
    		
    			if( validaSentenciasFrom(sentencia)) {
    				
    				if(validaCantidadLineas(sentencia, 2, 2)) { //SI ES SIN WHERE
    					
    					if(logica.existeTabla(sentencia.get(1)[1])) {
	            			
	            			if(logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])!=null) {
	                		
	            				if(!(logica.realizarConsultaSinWhere(sentencia.get(1)[1], sentencia.get(0)[1]).isEmpty())){
	            				
			    					ArrayList<DTOAtributo> atributos=logica.realizarConsultaSinWhere(sentencia.get(1)[1], sentencia.get(0)[1]);
						        	this.cargarTablaAtributos(atributos, sentencia.get(0)[1]);
						        	aciertos++;
						        	insertarDepuracion("Acierto #" + aciertos, "Consulta éxitosa, mostrando resultados para la tabla: " + sentencia.get(1)[1]);
						        	resultado = true;
						        	
	            				}else {
	            					
	            					aciertos++;
	            					insertarDepuracion("Acierto #" + aciertos, "No hay registros para la consulta realizada");
	            					resultado = true;
	            					
	            				}
	            				
	            			}else {
	    	        			
	            				insertarDepuracion("<html><font color='red'>Error #23</font></html>", "<html><font color='red'>El atributo no existe para tabla ingresada</font></html>");
	    	        		
	            			}
		            		
	            		}else {
	            			
	            			insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
	                    	
	            		}	
    					
    				}else {
    			
	            		if(sentencia.get(2).length==4) { //SI ES WHERE COMUN
	            	
			            	if(validaSentenciasWhereComun(sentencia)) {
			            		
			            		if(logica.existeTabla(sentencia.get(1)[1])) {
			            			
			            			if(logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])!=null) {
			                			
					            		if(logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //válido que el tipo de atributo y condición coincidan
					            			
					            			if(logica.realizarConsultaClasica(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2]).isEmpty()) { //válido que hayan registros que mostrar para la condición dada
					            				
					            				aciertos++;
					            				insertarDepuracion("Acierto #" + aciertos, "No hay registros que mostrar para la consulta realizada");
					            				resultado = true;
					            				
					            			}else {
					            				
					            				ArrayList<DTOAtributo> atributos=logica.realizarConsultaClasica(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2]);
									        	this.cargarTablaAtributos(atributos, sentencia.get(0)[1]);
					            				aciertos++;
									        	insertarDepuracion("Acierto #" + aciertos, "Consulta éxitosa, mostrando resultados para la tabla: " + sentencia.get(1)[1]);
									        	resultado = true;
									        	
					            			}
					            			
					            		}else {
					            			
					            			insertarDepuracion("<html><font color='red'>Error #14</font></html>", "<html><font color='red'>El tipo de atributo y el tipo de condición no coinciden</font></html>");
				            				
					            		}
					            		
			            			}else {
			    	        			
			            				insertarDepuracion("<html><font color='red'>Error #23</font></html>", "<html><font color='red'>El atributo no existe para tabla ingresada</font></html>");
			    	        		
			            			}
				            		
			            		}else {
			            			
			            			insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
			                    	
			            		}
			            		
			            	}	
			            	
			            }else {
			            		
		            		if(validaOperadoresLogicos(sentencia)) {
		            			
			            		if(sentencia.get(2)[4].equalsIgnoreCase("AND")) {
			            			
			            			if(comandoSelectAnd(sentencia)) {
			            				
			            				resultado=true;
			            				
			            			}
			            			
			            		} else if(sentencia.get(2)[4].equalsIgnoreCase("OR")) {
			            			
			            			if(comandoSelectOr(sentencia)) {
			            				
			            				resultado=true;
			            				
			            			}
			            			
			            		}else {
			            			
			            			insertarDepuracion("Error #05</font></html>", "El operador " + sentencia.get(2)[4] + " no es válido</font></html>");
			                    	
			            		}
			            		
		            		}
		            		
			            }
	            		
    				}
    			}
    		}
    	}
		
		return resultado;
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "USE" para seleccionar una base de datos. El metodo verifica si la base de datos especificada existe y la selecciona si es válida.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private void comandoUse(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
			if(logica.existeBD(sentencia.get(0)[1])) {
				
				logica.seleccionarBaseDatos(sentencia.get(0)[1]);
				VentanaPrincipal.bdActual.setText("Base de datos: " + logica.getBaseDatos());
	    		aciertos++;
	        	insertarDepuracion("Acierto #" + aciertos, "Se seleccionó la base de datos: " + logica.getBaseDatos());
	        	
			}else {
				
				insertarDepuracion("<html><font color='red'>Error #17</font></html>", "<html><font color='red'>La base de datos ingresada no existe para el usuario logueado</font></html>");
	        	
			}
        	
    	}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "INSERT" para agregar datos a una tabla.  El metodo verifica la validez de la sentencia, los argumentos y los datos a insertar. Luego, agrega los datos si son válidos.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private void comandoInsert(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadLineas(sentencia, 2, 2))){
			
        	insertarDepuracion("<html><font color='red'>Error #02</font></html>", "<html><font color='red'>Cantidad de líneas no válida</font></html>");
    		
    	}else {
    		
    		if ((!(sentencia.get(1).length>1)) || (!(validaCantidadArgumentos(sentencia, 0, 0, 2)))) {
        		
	        	insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos no válida</font></html>");
	        	
        	}else { 
        		
        		if(!(sentencia.get(1)[0].equalsIgnoreCase("VALUES"))) {
            			
		        	insertarDepuracion("<html><font color='red'>Error #01</font></html>", "<html><font color='red'>El comando: " + sentencia.get(1)[0].toUpperCase() + " no es válido</font></html>");
		        	
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
	            			        	
	            						if(logica.validaRegistroVacio(atributos)) {
	            							
	            							insertarDepuracion("<html><font color='red'>Error #19</font></html>", "<html><font color='red'>No es posible ingresar un registro vacío</font></html>");
	    	                				
	            						}else {
	            						
		            						logica.ingresarRegistro(sentencia.get(0)[1], registro);
		            			        	aciertos++;
		            			        	insertarDepuracion("Acierto #" + aciertos, "Se ingresaron los datos con éxito, en la tabla: " + sentencia.get(0)[1]);
		            			        	
	            						}
	            					}else {
	            						
	            						insertarDepuracion("<html><font color='red'>Error #18</font></html>", "<html><font color='red'>La clave primaria no puede quedar vacia ni repetirse</font></html>");
	            						
	            					}
	            					
	            				}else {
	            					
	            					insertarDepuracion("<html><font color='red'>Error #19</font></html>", "<html><font color='red'>Ingreso como nulos atributos no permitidos</font></html>");
	                				
	            				}
	            				
	            			}else {
	            				
	            				insertarDepuracion("<html><font color='red'>Error #20</font></html>", "<html><font color='red'>Los datos a ingresar son incorrectos para los atributos de la tabla</font></html>");
	            				
	            			}
	            			
	            		}else {
	            			
	            			insertarDepuracion("<html><font color='red'>Error #21</font></html>", "<html><font color='red'>Cantidad de atributos incorrecta, en caso de nulos use NULL</font></html>");
	        				
	            		}
	            		
            		}else {
            			
            			insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
                    	
            		}
            	}
        	}
    	}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "DELETE" para eliminar registros de una tabla.  El metodo válida la sentencia, el tipo de atributo y la condición antes de realizar la eliminacion.
	 * @param sentencia->lista de sentencia
	 * 
	 */
	private void comandoDelete(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { 
    		
        	insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Demasiados argumentos en línea 1</font></html>");
        	
    	}else {
    	
        	if( validaSentenciasFromWhere(sentencia)) {
        		
        		if(logica.existeTabla(sentencia.get(1)[1])) {
        		
	        		if(logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //válido que el tipo de atributo y condición coincidan
	        			
	        			if(logica.hayRegistros(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2])) { //válido que hayan registros que mostrar para la condición dada
	        				
	        				logica.borrarRegistro(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2]);
	        				aciertos++;
	        	        	insertarDepuracion("Acierto #" + aciertos, "Se eliminaron los datos con éxito, en la tabla: " + sentencia.get(1)[1]);
	        	        	
	        			}else {
	        				
	        				aciertos++;
	        				insertarDepuracion("Acierto #" + aciertos, "No hay registros que coincidan con los parámetros de la búsqueda");
	        				
	        			}
	        			
	        		}else {
	        			
	        			insertarDepuracion("<html><font color='red'>Error #14</font></html>", "<html><font color='red'>El tipo de atributo y el tipo de condición no coinciden</font></html>");
	    				
	        		}
	        		
        		}else {
        			
        			insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
                	
        		}
        	}
    	}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "UPDATE" para modificar registros en una tabla.  El metodo válida la sentencia, los argumentos y los datos a actualizar antes de realizar la modificacion.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private void comandoUpdate(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadLineas(sentencia, 3, 3))) {
    		
        	insertarDepuracion("<html><font color='red'>Error #02</font></html>", "<html><font color='red'>La cantidad de líneas ingresada es incorrecta</font></html>");
        	
    	}else {
    	
        	if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) { 
        		
	        	insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos incorrecta en línea 1");
	        	
        	}else {
        	
            	if(!(validaCantidadArgumentos(sentencia, 1, sentencia.size(), 4))) {
            		
		        	insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos incorrecta entre las líneas 2 y 3</font></html>");
		        	
            	}else {
            		
            		if(!(sentencia.get(1)[0].equalsIgnoreCase("SET"))) {
            			
			        	insertarDepuracion("<html><font color='red'>Error #01</font></html>", "<html><font color='red'>El comando: " + sentencia.get(1)[0].toUpperCase() + " no es válido</font></html>");
			        	
            		}else {
            			
	            		if( validaSentenciasWhereComun(sentencia)) {
	            			
	            			if(!(sentencia.get(1)[2].equals("="))){
	            				
					        	insertarDepuracion("<html><font color='red'>Error #05</font></html>", "<html><font color='red'>El operador: " + sentencia.get(1)[2] + " no es válido</font></html>");
					        	
	            			}else {
	            		
	            				if(logica.existeTabla(sentencia.get(0)[1])) {
		            				
		            				if((logica.tieneClave(sentencia.get(0)[1])) && (logica.obtenerClave(sentencia.get(0)[1]).equalsIgnoreCase((sentencia.get(1)[1])))) {
		            					
			            				insertarDepuracion("<html><font color='red'>Error #22</font></html>", "<html><font color='red'>No es posible cambiar la clave primaria de un registro</font></html>");
			            					
			            			}else if((logica.validaCondicion(sentencia.get(0)[1], sentencia.get(1)[1], sentencia.get(1)[3]) && (logica.validaCondicion(sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3])))){
			            				
			            				if(logica.hayRegistros(sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2])){	
				            				
			            					if(sentencia.get(1)[3].equalsIgnoreCase("NULL") && logica.esNotNull(sentencia.get(0)[1],sentencia.get(1)[1])) {
			            						
			            						insertarDepuracion("<html><font color='red'>Error #19</font></html>", "<html><font color='red'>Ingresó como nulos atributos no permitidos</font></html>");
				                				
			            					}else {
			            						
			            						logica.cambiarRegistro(sentencia.get(0)[1], sentencia.get(1)[1], sentencia.get(1)[3], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2]);
					            				aciertos++;
					            				insertarDepuracion("Acierto #" + aciertos, "Se modificaron los datos con éxito, en la tabla: " + sentencia.get(0)[1]);
					            				
			            					}
				            				
			            				}else {
			            					
			            					aciertos++;
			            					insertarDepuracion("Acierto #" + aciertos, "No hay registros que coincidan con los parámetros de la búsqueda");
			            					
			            				}
			            				
			            			}else {
			            				
			            				insertarDepuracion("<html><font color='red'>Error #14</font></html>", "<html><font color='red'>El tipo de atributo y el tipo de condición no coinciden</font></html>");
			            				
			            			}
		            				
	            				}else {
	            					
	            					insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
	            		        	
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
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "NOT NULL" para especificar que un atributo en una tabla no puede ser nulo.  El metodo válida la sentencia y realiza la operación si es válida.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private void comandoNotNull(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasDosLineas(sentencia)) {
    		
			if(logica.existeTabla(sentencia.get(1)[1])) {
			
				if(logica.esVacia(sentencia.get(1)[1])) { 
					
					if((logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])==null)) {
						
						insertarDepuracion("<html><font color='red'>Error #23</font></html>", "<html><font color='red'>El atributo no existe para tabla ingresada</font></html>");
					
					}else {
						
						if(logica.esNotNull(sentencia.get(1)[1], sentencia.get(0)[1])) {
							
							insertarDepuracion("<html><font color='red'>Error #23</font></html>", "<html><font color='red'>El atributo ya esta indicado como no nulo</font></html>");
							
						}else {
						
							logica.hacerNotNull(sentencia.get(1)[1], sentencia.get(0)[1]);
							aciertos++;
				        	insertarDepuracion("Acierto #" + aciertos, "Se indico como no nulo con éxito el atributo: " + sentencia.get(0)[1] + " de la tabla: " + sentencia.get(1)[1]);
				        	
						}
			        	
					}
		        	
				}else {
				
					insertarDepuracion("<html><font color='red'>Error #24</font></html>", "<html><font color='red'>La tabla no debe de tener registros para la operación a realizar</font></html>");
					
				}
			
			}else {
				
				insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
	        	
			}
			
    	}
		
	}
	
	/**
	 * 
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "COUNT" para contar la cantidad de registros que cumplen con una consulta en una tabla.  El metodo verifica la validez de la sentencia, el tipo de atributo y la condición antes de realizar el conteo.
	 * @param sentencia-> lista de sentencias
	 */
	private void comandoCount(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { 
    		
        	insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos incorrecta en línea 1</font></html>");
        	
    	}else {
    	
        	if( validaSentenciasFromWhere(sentencia)) {
        		
        		if(logica.existeTabla(sentencia.get(1)[1])) {
        		
	        		if(logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //válido que el tipo de atributo y condición coincidan
	        			
	        			if(logica.hayRegistros(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2])) { //válido que hayan registros que mostrar para la condición dada
	        				
	        				aciertos++;
	        	        	insertarDepuracion("Acierto #" + aciertos, "La cantidad de registros que cumplen con la consulta es: " + logica.contarRegistros(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2]));
	            			
	        				
	        			}else {
	        				
	        				aciertos++;
	        				insertarDepuracion("Acierto #" + aciertos, "No hay registros que coincidan con los parámetros de la búsqueda");
	        				
	        			}
	        			
	        		}else {
	        			
	        			insertarDepuracion("<html><font color='red'>Error #14</font></html>", "<html><font color='red'>El tipo de atributo y el tipo de condición no coinciden</font></html>");
	    				
	        		}
	        		
        		}else {
        			
        			insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
                	
        		}
        		
        	}
        	
    	}
		
	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "AVG" para calcular el promedio de los valores de un atributo en una tabla.  El metodo verifica la validez de la sentencia, el tipo de atributo y la condición antes de realizar el calculo.
	 * @param sentencia->lista de sentencias
	 * 
	 */
	private void comandoAvg(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) { 
    		
        	insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos incorrecta en línea 1</font></html>");
        	
    	}else {
    	
        	if( validaSentenciasFromWhere(sentencia)) {
        		
        		if(logica.existeTabla(sentencia.get(1)[1])) {
        		
        			if(logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])!=null) {
        			
		        		if(logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //válido que el tipo de atributo y condición coincidan
		        			
		        			if(logica.hayRegistros(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2])) { //válido que hayan registros que mostrar para la condición dada
		        				
		        				if(logica.obtenerTipoAtributo(sentencia.get(1)[1], sentencia.get(0)[1]).equalsIgnoreCase("entero")) {
		                					
		        					aciertos++;
		                	       	insertarDepuracion("Acierto #" + aciertos, "El promedio de los registros consultados es: " + logica.calcularPromedioRegistros(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[2]));
		                            
	            				}else {
	            					
	            					insertarDepuracion("<html><font color='red'>Error #25</font></html>", "<html><font color='red'>El atributo debe de ser de tipo entero para esta operación</font></html>");
	            					
	            				}
		                				
	            			}else {
	            				
	            				aciertos++;
	            				insertarDepuracion("Acierto #" + aciertos, "No hay registros que coincidan con los parámetros de la búsqueda");
	            				
	            			}
	            		
	        			}else {
		        			
		        			insertarDepuracion("<html><font color='red'>Error #14</font></html>", "<html><font color='red'>El tipo de atributo y el tipo de condición no coinciden</font></html>");
		    				
		        		}
		        		
        			}else {
	        			
        				insertarDepuracion("<html><font color='red'>Error #23</font></html>", "<html><font color='red'>El atributo no existe para tabla ingresada</font></html>");
	        		}
	        			
        		}else {
        			
        			insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
                	
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
					
						Integer maximo=logica.obtenerMaximo(sentencia.get(1)[1], sentencia.get(0)[1]);
	    				
						if(logica.esVacia(sentencia.get(1)[1]) || (maximo == null)) { 
							
		    				insertarDepuracion("<html><font color='red'>Error #26</font></html>", "<html><font color='red'>No hay registros ingresados aun en la tabla o en el campo indicado</font></html>");
		    				
		    			}else {
		    				
	    					aciertos++; 
	        				insertarDepuracion("Acierto #" + aciertos, "El valor máximo de los registros consultados es: " + maximo);
	        			
		    			}
						
					}else {
						
						insertarDepuracion("<html><font color='red'>Error #25</font></html>", "<html><font color='red'>El atributo debe de ser de tipo entero para esta operación</font></html>");
						
					}
				
				}else {
        			
    				insertarDepuracion("<html><font color='red'>Error #23</font></html>", "<html><font color='red'>El atributo no existe para tabla ingresada</font></html>");
        		
				}
				
			}else {
				
				insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
	        	
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
					
					Integer minimo=logica.obtenerMinimo(sentencia.get(1)[1], sentencia.get(0)[1]);
    				
					if(logica.esVacia(sentencia.get(1)[1]) || (minimo == null)) { 
						
	    				insertarDepuracion("<html><font color='red'>Error #26</font></html>", "<html><font color='red'>No hay registros ingresados aun en la tabla o en el campo indicado</font></html>");
	    				
	    			}else {
				
	    				aciertos++;
	    				insertarDepuracion("Acierto #" + aciertos, "El valor mínimo de los registros consultados es: " + minimo);
	    		        
	        				
	        		}
		        	
				}else {
					
					insertarDepuracion("<html><font color='red'>Error #25</font></html>", "<html><font color='red'>El atributo debe de ser de tipo entero para esta operación</font></html>");
					
				}
				
			}else {
    			
				insertarDepuracion("<html><font color='red'>Error #23</font></html>", "<html><font color='red'>El atributo no existe para tabla ingresada</font></html>");
    		}
			
		}else {
			
			insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
        	
		}
		
	}
	
	/**
	 * Metodo privado que recibe una lista de matrices de cadenas de texto (sentencia) y procesa el comando "PRIMARY KEY" para especificar un atributo como clave primaria en una tabla. . El metodo verifica la validez de la sentencia y realiza la operación si es válida.
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
			        	insertarDepuracion("Acierto #" + aciertos, "Se hizo clave primaria con éxito el atributo: " + sentencia.get(0)[1] + " de la tabla: " + sentencia.get(1)[1]);
						
					}else {
	        			
        				insertarDepuracion("<html><font color='red'>Error #23</font></html>", "<html><font color='red'>El atributo no existe para tabla ingresada</font></html>");
	        		}
						
				}else {
				
					insertarDepuracion("<html><font color='red'>Error #24</font></html>", "<html><font color='red'>La tabla no debe de tener registros para la operación a realizar</font></html>");
					
				}
				
			}else {
				
				insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
	        	
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
			
			if(logica.existeTabla(sentencia.get(0)[1])) {
			
				String nombreTabla = logica.obtenerNomTabla(sentencia.get(0)[1]);
				
				cargarTablaString(logica.describeTabla(sentencia.get(0)[1]), "información de la tabla " + nombreTabla);
				aciertos++;
				insertarDepuracion("Acierto #" + aciertos, "Se muestra la información de la tabla: " + nombreTabla);
        	
			}else {
				
				insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La tabla ingresada no existe para la base de datos seleccionada</font></html>");
	        	
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
				cargarTablaString(ayuda, "AYUDA " + sentencia.get(0)[1].toUpperCase());
				aciertos++;
				insertarDepuracion("Acierto #" + aciertos, "Mostrando ayuda para el comando: " + sentencia.get(0)[1].toUpperCase());
        	
			}else {
				
				insertarDepuracion("<html><font color='red'>Error #27</font></html>", "<html><font color='red'>El comando ingresado no existe o no tiene ayuda asociada</font></html>");
				
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
    		
        	insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos no válida en línea 1</font></html>");
        	
    	}else {
    		
    		if(!(validaCantidadLineas(sentencia, 2, 2))) {
    			
	        	insertarDepuracion("<html><font color='red'>Error #02</font></html>", "<html><font color='red'>Cantidad de líneas no válida</font></html>");
	        	
    		}else {
    			
    			if(!(sentencia.get(1)[0].equalsIgnoreCase("FROM"))) {
    				
		        	insertarDepuracion("<html><font color='red'>Error #01</font></html>", "<html><font color='red'>El comando: " + sentencia.get(1)[0].toUpperCase() + " no es válido</font></html>");
		        	
    			}else {
    			
        			if(!(validaCantidadArgumentos(sentencia, 1, sentencia.size(), 3))) {
        				
			        	insertarDepuracion("<html><font color='red'>Error #03</font></html>", "<html><font color='red'>Cantidad de argumentos no válida en línea 2, recuerde que el join natural se realiza entre dos tablas</font></html>");
			        	
        			}else {
        				
        				if((logica.existeTabla(sentencia.get(1)[1])) && (logica.existeTabla(sentencia.get(1)[2]))) {
        					
        					if((logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])!=null) || (logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[2])!=null)) {
        						
        						if(logica.validaAtributosJoin(sentencia.get(1)[1], sentencia.get(1)[2])) {
        						
	        						if(logica.joinNatural(sentencia.get(1)[1], sentencia.get(1)[2], sentencia.get(0)[1]).isEmpty()){
	        							
	        							aciertos++;
	        							insertarDepuracion("Acierto #" + aciertos, "No hay registros que mostrar para la consulta realizada");
	        							
	        						}else {
        						
		        						cargarTablaAtributos(logica.joinNatural(sentencia.get(1)[1], sentencia.get(1)[2], sentencia.get(0)[1]), sentencia.get(0)[1]);
		            					aciertos++;
		            					insertarDepuracion("Acierto #" + aciertos, "Consulta éxitosa, mostrando resultados del join natural entre las tablas: " + sentencia.get(1)[1] + " y " + sentencia.get(1)[2]);
		    			        	
	        						}
        						
        						}else {
        							
        							insertarDepuracion("<html><font color='red'>Error #23</font></html>", "<html><font color='red'>Las tablas ingresadas no poseen ningún atributo en común</font></html>");
                	        		
        						}
        						
        					}else {
        	        			
                				insertarDepuracion("<html><font color='red'>Error #23</font></html>", "<html><font color='red'>El atributo no existe en ninguna de las tablas ingresadas</font></html>");
        	        		
        					}
        					
        				}else {
        					
        					insertarDepuracion("<html><font color='red'>Error #16</font></html>", "<html><font color='red'>La/s tabla/s ingresada/s no existe/n para la base de datos seleccionada</font></html>");
        		        	
        				}
        			}
    			}
    		}
    	}
		
	}
	
	/**
	 * Metodo publico que recibe una cadena de texto que representa el comando a ejecutar y una lista de matrices de cadenas de texto (sentencia).  El metodo ejecuta el comando especificado y las operaciones asociadas, verificando si se selecciono una base de datos y si el comando es válido. Tambien muestra resultados o mensajes de error en la tabla de depuracion.
	 * @param comando-> comando que se ejeuctura
	 * @param sentencia->lista de sentencia
	 */
	public void ejecutarComando(String comando, ArrayList<String[]> sentencia) {
		
		if(comando != null) {
		
		    if (acciones.containsKey(comando)) {
		    	
		    	if ((comando.equals("USE")) || (comando.equals("HELP") || (comando.equals("CREATE") || (comando.equals("DROP"))))) {
			    	
			        acciones.get(comando).accept(sentencia);
			        
			    } else {
			    	
			        if (logica.bdSeleccionada()) {
			        	
			        	if(Login.demo){
			        		
			        		if(comandoSelect(sentencia)) {
			        			
			        			usos++;
			        			
			        		}
			        		
			        	}else {
			        		
			        		acciones.get(comando).accept(sentencia);
			            
			        	}
			            
			        } else {
			        	
			            insertarDepuracion("<html><font color='red'>Error #15</font></html>", "<html><font color='red'>Debe seleccionar la base de datos sobre la cual operar</font></html>");
			            
			        }
			        
			    }
		       
		    } else {
		    	
		        insertarDepuracion("<html><font color='red'>Error #01</font></html>", "<html><font color='red'>El comando " + comando + " no es válido</font></html>");
		        
		    }
	    
		} else {
	    	
	        insertarDepuracion("<html><font color='red'>Error #01</font></html>", "<html><font color='red'>No ha ingresado sentencias que ejecutar</font></html>");
	        
	    }
	    
	}

}