package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.table.DefaultTableModel;

import comunicacion.DTOAtributo;
import comunicacion.DTOBaseDatos;
import comunicacion.DTOCadena;
import comunicacion.DTOEntero;
import comunicacion.DTOTabla;
import comunicacion.IFachadaLogica;

public class Comandos {
	
	private IFachadaLogica logica;
    private int aciertos;
    private Map<String, Consumer<ArrayList<String[]>>> acciones; //es una funci�n que toma un valor y realiza alg�n tipo de acci�n con �l, pero no produce ning�n resultado como salida.

    public Comandos(IFachadaLogica fa) {
    	
    	logica=fa;
        aciertos = 0;
        inicializarAcciones();
        
    }

    private void inicializarAcciones() {
    	
        acciones = new HashMap<>();
        acciones.put("CREATE", sentencia -> comandoCreate(sentencia));
        acciones.put("INSERT", sentencia -> comandoInsert(sentencia));
        acciones.put("SHOW", sentencia -> comandoShow(sentencia));
        acciones.put("USE", sentencia -> comandoUse(sentencia));
        acciones.put("DELETE", sentencia -> comandoDelete(sentencia));
        acciones.put("UPDATE", sentencia -> comandoUpdate(sentencia));
        acciones.put("NOTNULL", sentencia -> comandoNotNull(sentencia));
        acciones.put("SELECT", sentencia -> comandoSelect(sentencia));
        acciones.put("COUNT", sentencia -> comandoCount(sentencia));
        acciones.put("AVG", sentencia -> comandoAvg(sentencia));
        acciones.put("PRIMARYKEY", sentencia -> comandoPrimaryKey(sentencia));
        acciones.put("DESCRIBE", sentencia -> comandoDescribe(sentencia));
        acciones.put("HELP", sentencia -> comandoHelp(sentencia));
        acciones.put("JOINNATURAL", sentencia -> comandoJoinNatural(sentencia));
        acciones.put("MAX", sentencia -> comandoMax(sentencia));
        acciones.put("MIN", sentencia -> comandoMin(sentencia));
        
    }
	
    public boolean validaSeleccionBD() {
    	
    	return logica.bdSeleccionada();
    	
    }

    public void cargarTablaAtributos(ArrayList<DTOAtributo> atributos, String nombreAtributo) {
    	
    	DefaultTableModel model = (DefaultTableModel) VentanaPrincipal.salida.getModel();
    	model.setRowCount(0);
    	model.setColumnCount(0);
    	model.addColumn(nombreAtributo.toUpperCase());

    	for(DTOAtributo atr : atributos) {
    		
			if (atr instanceof DTOCadena) {
				
				DTOCadena cadena = (DTOCadena) atr;
				String dato = cadena.getDato();
				model.addRow(new Object [] {dato});
			
			} else if (atr instanceof DTOEntero) {
			
				DTOEntero entero = (DTOEntero) atr;
				int valor = entero.getValor();
				model.addRow(new Object [] {valor});
			
			}
    			
    	}
    	
    }
    
    public void cargarTablaString(ArrayList<String> datos, String nombrColumna) {
    	
    	DefaultTableModel model = (DefaultTableModel) VentanaPrincipal.salida.getModel();
    	model.setRowCount(0);
    	model.setColumnCount(0);
    	model.addColumn(nombrColumna.toUpperCase());

    	for(String dato : datos) {
    		
			model.addRow(new Object [] {dato});
				
    	}
    	
    }
    	
    public void insertarDepuracion(String mensaje1, String mensaje2) {

		DefaultTableModel model = (DefaultTableModel) VentanaPrincipal.depuracion.getModel();

		Object[] nuevaFila = { mensaje1, mensaje2 };
		model.insertRow(0, nuevaFila);

	}
    
	public boolean validaCantidadArgumentos(ArrayList<String[]> sentencia, int posInicial, int posFinal, int cantArgumentos) {

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

	public boolean validaTipoDato(String tipo) {

		return ((tipo.toUpperCase().equals("ENTERO")) || (tipo.toUpperCase().equals("CADENA")));

	}

	public boolean validaTiposAtributos(ArrayList<String[]> sentencia, int posInicial, int posFinal) {

		for (int j = posInicial; j < posFinal; j++) {

			if (!(validaTipoDato(sentencia.get(j)[1]))) {

				return false;

			}

		}

		return true;

	}

	public boolean validaCantidadLineas(ArrayList<String[]> sentencia, int min, int max) {

		if (min == max) {

			return (sentencia.size() == min);

		} else {

			return ((sentencia.size() >= min) && (sentencia.size() <= max));

		}

	}

	public boolean validaSentenciasUnaLinea(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 1, 1))) {

			insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");

		} else {

			if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) {

				insertarDepuracion("Error #03", "Cantidad de argumentos no valida");

			} else {

				return true;

			}

		}

		return false;

	}

	public boolean validaSentenciasDosLineas(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 2, 2))) {

			insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");

		} else {

			if (!(validaCantidadArgumentos(sentencia, 0, sentencia.size(), 2))) {

				insertarDepuracion("Error #03", "Cantidad de argumentos no valida");

			} else {

				if (!(sentencia.get(1)[0].toUpperCase().equals("FROM"))) {

					insertarDepuracion("Error #01",
							"El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");

				} else {

					return true;

				}

			}

		}

		return false;

	}

	public boolean validaSentenciasWhereComun(ArrayList<String[]> sentencia) {

		if (!(validaCantidadArgumentos(sentencia, 2, 2, 4))) {

			insertarDepuracion("Error #03", "Cantidad de argumentos no valida en linea 3");

		} else {

			if (!(sentencia.get(2)[0].toUpperCase().equals("WHERE"))) {

				insertarDepuracion("Error #01", "El comando: " + sentencia.get(2)[0].toUpperCase() + " no es valido");

			} else {

				if (!(sentencia.get(2)[2].toUpperCase().equals("="))) {

					insertarDepuracion("Error #05", "El operador: " + sentencia.get(2)[2] + " no es valido");

				} else {

					return true;

				}
			}

		}

		return false;

	}

	public boolean validaSentenciasFrom(ArrayList<String[]> sentencia) {

		if (!(validaCantidadArgumentos(sentencia, 1, 1, 2))) {

			insertarDepuracion("Error #03", "Cantidad de argumentos no valida en la linea 2");

		} else {

			if (!(sentencia.get(1)[0].toUpperCase().equals("FROM"))) {

				insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");

			} else {

				return true;

			}
		}

		return false;

	}

	public boolean validaSentenciasFromWhere(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 3, 3))) {

			insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");

		} else {

			if (validaSentenciasFrom(sentencia)) {

				if (validaSentenciasWhereComun(sentencia)) {

					return true;

				}

			}

		}

		return false;

	}

	public boolean validaOperadoresLogicos(ArrayList<String[]> sentencia) {

		if (!(validaCantidadArgumentos(sentencia, 2, 2, 8))) {

			insertarDepuracion("Error #03", "Cantidad de argumentos no valida en la linea 3");

		} else {

			if (!(sentencia.get(2)[2].toUpperCase().equals("=")) || !(sentencia.get(2)[6].toUpperCase().equals("="))) {

				insertarDepuracion("Error #05", "Operador/es de igualdad no valido en la linea 3");

			} else {

				return true;

			}

		}

		return false;

	}

	public void comandoTable(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadLineas(sentencia, 3, 5))) {
			
			insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta, recuerde que se permiten de uno a tres atributos por tabla");
        	
		}else {
			
		    if(!(validaCantidadArgumentos(sentencia, 1, sentencia.size(), 2))) {
			
		    	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta entre las lineas 2 y " + sentencia.size());
	        	
			}else {
				
				if(!(validaTiposAtributos(sentencia, 2, sentencia.size()))) {
					
					insertarDepuracion("Error #04", "Tipos de datos incorrectos, recuerde que solo se admiten datos de tipo entero o cadena");
		        	
				}else {
					
					if(logica.existeTabla(sentencia.get(1)[1])) {
						
						insertarDepuracion("Error #N", "La tabla ingresada ya existe para la base de datose seleccionada");
			        	
					}else {
						
						LinkedHashMap<String, String> atributos = new LinkedHashMap<String, String>();

						for (int i = 0; i < sentencia.size(); i++) {
							
						    if (sentencia.get(i) != null && sentencia.get(i).length >= 2 && sentencia.get(i)[0] != null && sentencia.get(i)[1] != null) {
						       
						    	atributos.put(sentencia.get(i)[0], sentencia.get(i)[1]);
						    
						    }
						}

						DTOTabla tabla = new DTOTabla(sentencia.get(1)[1]);
						
						logica.crearTabla(tabla, atributos);
						aciertos++;
						insertarDepuracion("Acierto #" + aciertos, "Se ha ingresado con exito la tabla: " +  sentencia.get(1)[1]);
			        	
					}
				}
			}		
		}
		
	}
	
	public void comandoDatabase(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadLineas(sentencia, 2, 2))) {
			
        	insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
        	
		}else {
		
			if(!(validaCantidadArgumentos(sentencia, 1, 1, 2))) {
				
	        	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en la linea 2");
	        	
			}else if(logica.existeBD(sentencia.get(1)[1])){
				
				insertarDepuracion("Error #N", "La base de datos ingresada ya existe para el usuario seleccionado");
	        	
			}else {
				
				DTOBaseDatos bd =new DTOBaseDatos(sentencia.get(1)[1]);
				logica.crearBD(bd);
				VentanaPrincipal.cargarBasesDatos(logica.obtenerBasesNom());
				aciertos++;
	        	insertarDepuracion("Acierto #" + aciertos, "Se ha ingresado con exito la base de datos: " + sentencia.get(1)[1]);
				
			}
			
		}
		
	}
	
	public void comandoSelectAnd(ArrayList<String[]> sentencia) {
		
		if(logica.existeTabla(sentencia.get(1)[1])) {
		
			if((logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3]) && (logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[5], sentencia.get(2)[7])))) { //Valido que el tipo de atributo y condicion coincidan
				
				if(logica.consultaAnd(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[5], sentencia.get(2)[7]).isEmpty()) { //Valido que hayan registros que mostrar para la condicion dada
					
					insertarDepuracion("Error #N", "No hay registros que coincidan con los parametros de la busqueda");
					
				}else {
					
					ArrayList<DTOAtributo> atributos=logica.consultaAnd(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[5], sentencia.get(2)[7]);
		        	this.cargarTablaAtributos(atributos, sentencia.get(0)[1]);
					aciertos++;
		        	insertarDepuracion("Acierto #" + aciertos, "Consulta exitosa");
		        	
				}
				
			}else {
				
				insertarDepuracion("Error #N", "El tipo de atributo y el tipo de condicion no coinciden");
				
			}
		
		}else {
			
			insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datose seleccionada");
        	
		}
		
	}
	
	public void comandoSelectOr(ArrayList<String[]> sentencia) {
		
		if(logica.existeTabla(sentencia.get(1)[1])) {
		
			if((logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3]) || (logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[5], sentencia.get(2)[7])))) { //Valido que el tipo de atributo y condicion coincidan
				
				if(logica.consultaOr(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[5], sentencia.get(2)[7]).isEmpty()) { //Valido que hayan registros que mostrar para la condicion dada
						
					insertarDepuracion("Error #N", "No hay registros que coincidan con los parametros de la busqueda");
					
				}else {
					
					ArrayList<DTOAtributo> atributos=logica.consultaOr(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3], sentencia.get(2)[5], sentencia.get(2)[7]);
		        	this.cargarTablaAtributos(atributos, sentencia.get(0)[1]);
					aciertos++;
		        	insertarDepuracion("Acierto #" + aciertos, "Consulta exitosa");
		        	
				}
				
			}else {
				
				insertarDepuracion("Error #N", "El tipo de atributo y el tipo de condicion no coinciden");
				
			}
		
		}else {
			
			insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datose seleccionada");
	    	
		}
		
		
	}
	
	public void comandoShow(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
    		if(!(sentencia.get(0)[1].toUpperCase().equals("TABLES"))) {
            			
	        	insertarDepuracion("Error #01", "El comando: " + sentencia.get(0)[1].toUpperCase() + " no es valido");
	        	
            }else {
            	
            	ArrayList<String> tablas = logica.obtenerTablasNom();
            	cargarTablaString(tablas, "tablas");
            	aciertos++;
            	insertarDepuracion("Acierto #" + aciertos, "Consulta exitosa, se muestran las tablas para la base de datos seleccionada");
            	
            }
        	
    	}
		
	}
	
	public void comandoCreate(ArrayList<String[]> sentencia) {
		
		if(!(sentencia.size()>1)){
			
    		insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
        	
		}else {
			
        	if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { //no tiene mas nada alado del create
        		
        		insertarDepuracion("Error #03", "Demasiados argumentos en linea 1");
	        
        	}else {
    			
        		String comando2=sentencia.get(1)[0].toUpperCase();
        		
        		if(comando2.equals("TABLE")) { //TABLE
        			
        			if(logica.bdSeleccionada()) {
        			
        				comandoTable(sentencia);
        			
        			}else {
        				
        				insertarDepuracion("Error #N", "Debe seleccionar la base de datos sobre la cual operar");
        				
        			}
        			
        		}else if(comando2.equals("DATABASE")) { //DATABASE
        			
        			comandoDatabase(sentencia);
        			
        		}else {
        			
        			insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");
		        	
        		}
        		
        	}
        	
		}
		
	}
	
	public void comandoSelect(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadArgumentos(sentencia, 0, 0, 2))) {
    		
        	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
        	
    	}else {
    		
    		if(!(validaCantidadLineas(sentencia, 3, 3))) {
    			
	        	insertarDepuracion("Error #02", "Cantidad de lineas incorrecta");
	        	
    		}else {
    		
    			if(validaSentenciasFrom(sentencia)) {
    			
            		if(sentencia.get(2).length==4) { //SI ES WHERE COMUN
            	
		            	if(validaSentenciasWhereComun(sentencia)) {
		            		
		            		if(logica.existeTabla(sentencia.get(1)[1])) {
		            		
			            		if(logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //Valido que el tipo de atributo y condicion coincidan
			            			
			            			if(logica.realizarConsultaClasica(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3]).isEmpty()) { //Valido que hayan registros que mostrar para la condicion dada
			            				
			            				insertarDepuracion("Error #N", "No hay registros que mostrar para la consulta realizada");
			            				
			            			}else {
			            				
			            				ArrayList<DTOAtributo> atributos=logica.realizarConsultaClasica(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3]);
							        	this.cargarTablaAtributos(atributos, sentencia.get(0)[1]);
			            				aciertos++;
							        	insertarDepuracion("Acierto #" + aciertos, "Consulta exitosa");
							        	
			            			}
			            			
			            		}else {
			            			
			            			insertarDepuracion("Error #N", "El tipo de atributo y el tipo de condicion no coinciden");
		            				
			            		}
			            		
		            		}else {
		            			
		            			insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datose seleccionada");
		                    	
		            		}
		            		
		            	}	
		            	
		            }else {
		            		
	            		if(validaOperadoresLogicos(sentencia)) {
	            			
		            		String operador=sentencia.get(2)[4].toUpperCase();
		            		
		            		if(operador.equals("AND")) {
		            			
		            			comandoSelectAnd(sentencia);
		            			
		            		} else if(operador.equals("OR")) {
		            			
		            			comandoSelectOr(sentencia);
		            			
		            		}else {
		            			
		            			insertarDepuracion("Error #N", "El operador " + sentencia.get(2)[4] + " no es valido");
		                    	
		            		}
		            		
	            		}
	            		
		            }
    			}
    		}
    	}
		
	}
	
	public void comandoUse(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
			if(logica.existeBD(sentencia.get(0)[1])) {
				
				logica.seleccionarBaseDatos(sentencia.get(0)[1]);
				VentanaPrincipal.bdActual.setText("Base de datos: " + sentencia.get(0)[1]);
	    		aciertos++;
	        	insertarDepuracion("Acierto #" + aciertos, "Se selecciono la base de datos: " + sentencia.get(0)[1]);
	        	
			}else {
				
				insertarDepuracion("Error #N", "La base de datos ingresada no existe para el usuario logueado");
	        	
			}
        	
    	}
		
	}
	
	public void comandoInsert(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadLineas(sentencia, 2, 2))){
			
        	insertarDepuracion("Error #02", "Cantidad de lineas no valida");
    		
    	}else {
    		
    		if ((!(sentencia.get(1).length>1)) || (!(validaCantidadArgumentos(sentencia, 0, 0, 2)))) {
        		
	        	insertarDepuracion("Error #03", "Cantidad de argumentos no valida");
	        	
        	}else { 
        		
        		if(!(sentencia.get(1)[0].toUpperCase().equals("VALUES"))) {
            			
		        	insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");
		        	
            	}else {
            		
            		if(logica.existeTabla(sentencia.get(0)[1])) {
            		
            			ArrayList<String> atributos = new ArrayList<>();

            			for (int i = 1; i <= sentencia.size(); i++) {
            				
            			    atributos.add(sentencia.get(1)[i]);
            			    
            			}
	            		
	            		if(logica.validaCantidadAtributos(sentencia.get(0)[1], atributos)) {
	            		
	            			if(logica.validaAtributos(sentencia.get(0)[1], atributos)) {
	            				
	            				if(logica.validaNotNull(sentencia.get(0)[1], atributos)) {
	            					
	            					if(logica.validaClave(sentencia.get(0)[1], atributos)) {
	            						
	            						LinkedHashMap<String, DTOAtributo> registro=logica.generarArrayListRegistro(sentencia.get(0)[1], atributos);
	            			        	logica.ingresarRegistro(sentencia.get(0)[1], registro);
	            			        	aciertos++;
	            			        	insertarDepuracion("Acierto #" + aciertos, "Se ingresaron los datos con exito");
	            			        	
	            					}else {
	            						
	            						insertarDepuracion("Error #N", "La calve primaria no puede quedar vacia ni repetirse");
	            						
	            					}
	            					
	            				}else {
	            					
	            					insertarDepuracion("Error #N", "Ingreso como nulos atributos no permitidos");
	                				
	            				}
	            				
	            			}else {
	            				
	            				insertarDepuracion("Error #N", "Los datos a ingresar son incorrectos para los atributos de la tabla");
	            				
	            			}
	            			
	            		}else {
	            			
	            			insertarDepuracion("Error #N", "Los datos no pueden quedar vacios, utilice la palabra NULL");
	        				
	            		}
	            		
            		}else {
            			
            			insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datose seleccionada");
                    	
            		}
            	}
        	}
    	}
		
	}
	
	public void comandoDelete(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { 
    		
        	insertarDepuracion("Error #03", "Demasiados argumentos en linea 1");
        	
    	}else {
    	
        	if(validaSentenciasFromWhere(sentencia)) {
        		
        		if(logica.existeTabla(sentencia.get(1)[1])) {
        		
	        		if(logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //Valido que el tipo de atributo y condicion coincidan
	        			
	        			if(logica.hayRegistros(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //Valido que hayan registros que mostrar para la condicion dada
	        				
	        				logica.borrarRegistro(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3]);
	        				aciertos++;
	        	        	insertarDepuracion("Acierto #" + aciertos, "Se eliminaron los datos con exito");
	        	        	
	        			}else {
	        				
	        				insertarDepuracion("Error #N", "No hay registros que coincidan con los parametros de la busqueda");
	        				
	        			}
	        			
	        		}else {
	        			
	        			insertarDepuracion("Error #N", "El tipo de atributo y el tipo de condicion no coinciden");
	    				
	        		}
	        		
        		}else {
        			
        			insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datose seleccionada");
                	
        		}
        	}
    	}
		
	}
	
	public void comandoUpdate(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadLineas(sentencia, 3, 3))) {
    		
        	insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
        	
    	}else {
    	
        	if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) { 
        		
	        	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
	        	
        	}else {
        	
            	if(!(validaCantidadArgumentos(sentencia, 1, sentencia.size(), 4))) {
            		
		        	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta entre las lineas 2 y 3");
		        	
            	}else {
            		
            		if(!(sentencia.get(1)[0].toUpperCase().equals("SET"))) {
            			
			        	insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");
			        	
            		}else {
            			
	            		if(validaSentenciasWhereComun(sentencia)) {
	            			
	            			if(!(sentencia.get(1)[2].equals("="))){
	            				
					        	insertarDepuracion("Error #05", "El operador: " + sentencia.get(1)[2] + " no es valido");
					        	
	            			}else {
	            		
	            				if(logica.existeTabla(sentencia.get(0)[1])) {
	            				
		            				if((logica.tieneClave(sentencia.get(0)[1])) && (logica.obtenerClave(sentencia.get(0)[1]).equalsIgnoreCase((sentencia.get(1)[1])))) {
		            					
			            				insertarDepuracion("Error #N", "No es posible cambiar la clave primaria de un registro");
			            					
			            			}else if(logica.validaCondicion(sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3])){
			            				
			            				if(logica.hayRegistros(sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3])){	
				            				
			            					logica.cambiarRegistro(sentencia.get(0)[1], sentencia.get(1)[1], sentencia.get(1)[3], sentencia.get(2)[1], sentencia.get(2)[3]);
				            				aciertos++;
				            				insertarDepuracion("Acierto #" + aciertos, "Se modificaron los datos con exito");
				            				
			            				}else {
			            					
			            					insertarDepuracion("Error #N", "No hay registros que coincidan con los parametros de la busqueda");
			            					
			            				}
			            				
			            			}else {
			            				
			            				insertarDepuracion("Error #N", "El tipo de atributo y el tipo de condicion no coinciden");
			            				
			            			}
		            				
	            				}else {
	            					
	            					insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datose seleccionada");
	            		        	
	            				}
	            			}
	            		}
            		}
            	}
        	}
    	}
		
	}
	
	public void comandoNotNull(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasDosLineas(sentencia)) {
    		
			if(logica.existeTabla(sentencia.get(1)[1])) {
			
				if(logica.esVacia(sentencia.get(1)[1])) { 
					
					if((logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])==null)) {
						
						insertarDepuracion("Error #N", "El atributo no existe para tabla ingresada");
					
					}else {
						
						logica.hacerNotNull(sentencia.get(1)[1], sentencia.get(0)[1]);
						aciertos++;
			        	insertarDepuracion("Acierto #" + aciertos, "Se indico como no nulo con exito el atributo: " + sentencia.get(0)[1] + " de la tabla: " + sentencia.get(1)[1]);
			        	
					}
		        	
				}else {
				
					insertarDepuracion("Error #N", "La tabla no debe de tener registros para la operacion a realizar");
					
				}
			
			}else {
				
				insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datose seleccionada");
	        	
			}
			
    	}
		
	}
	
	public void comandoCount(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { 
    		
        	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
        	
    	}else {
    	
        	if(validaSentenciasFromWhere(sentencia)) {
        		
        		if(logica.existeTabla(sentencia.get(1)[1])) {
        		
	        		if(logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //Valido que el tipo de atributo y condicion coincidan
	        			
	        			if(logica.hayRegistros(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //Valido que hayan registros que mostrar para la condicion dada
	        				
	        				insertarDepuracion("Error #N", "No hay registros No hay registros que coincidan con los parametros de la busqueda");
	        				
	        			}else {
	        				
	        				aciertos++;
	        	        	insertarDepuracion("Acierto #" + aciertos, "La cantidad de registros que cumplen con la consulta es: " + logica.contarRegistros(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3]));
	            			
	        			}
	        			
	        		}else {
	        			
	        			insertarDepuracion("Error #N", "El tipo de atributo y el tipo de condicion no coinciden");
	    				
	        		}
	        		
        		}else {
        			
        			insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datose seleccionada");
                	
        		}
        		
        	}
        	
    	}
		
	}
	
	public void comandoAvg(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) { 
    		
        	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
        	
    	}else {
    	
        	if(validaSentenciasFromWhere(sentencia)) {
        		
        		if(logica.existeTabla(sentencia.get(1)[1])) {
        		
	        		if(logica.validaCondicion(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //Valido que el tipo de atributo y condicion coincidan
	        			
	        			if(logica.hayRegistros(sentencia.get(1)[1], sentencia.get(2)[1], sentencia.get(2)[3])) { //Valido que hayan registros que mostrar para la condicion dada
	        				
	        				if(logica.obtenerTipoAtributo(sentencia.get(1)[1], sentencia.get(0)[1]).equalsIgnoreCase("entero")) {
	                					
	        					aciertos++;
	                	       	insertarDepuracion("Acierto #" + aciertos, "El promedio de los registros consultados es: " + logica.calcularPromedioRegistros(sentencia.get(1)[1], sentencia.get(0)[1], sentencia.get(2)[1], sentencia.get(2)[3]));
	                            
            				}else {
            					
            					insertarDepuracion("Error #N", "El atributo debe de ser de tipo entero para esta operacion");
            					
            				}
	                				
            			}else {
            				
            				insertarDepuracion("Error #N", "No hay registros que coincidan con los parametros de la busqueda");
            				
            			}
            		
        			}else {
	        			
	        			insertarDepuracion("Error #N", "El tipo de atributo y el tipo de condicion no coinciden");
	    				
	        		}
	        			
        		}else {
        			
        			insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datose seleccionada");
                	
        		}
        	}
    	}
		
	}
	
	public void comandoMax(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasDosLineas(sentencia)) {
    		
			if(logica.existeTabla(sentencia.get(1)[1])) {
			
				if(logica.obtenerTipoAtributo(sentencia.get(1)[1], sentencia.get(0)[1]).equalsIgnoreCase("entero")) {
				
					if(logica.esVacia(sentencia.get(1)[1])) { 
						
	    				insertarDepuracion("Error #N", "No hay registros ingresados aun en la tabla");
	    				
	    			}else {
					
    					aciertos++; 
        				insertarDepuracion("Acierto #" + aciertos, "El valor maximo de los registros consultados es: " + logica.obtenerMaximo(sentencia.get(1)[1], sentencia.get(0)[1]));
        				
	    			}
					
				}else {
					
					insertarDepuracion("Error #N", "El atributo debe de ser de tipo entero para esta operacion");
					
				}
				
			}else {
				
				insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datose seleccionada");
	        	
			}
        	
        }
		
	}
	
	public void comandoMin(ArrayList<String[]> sentencia) {
		
		if(logica.existeTabla(sentencia.get(1)[1])) {
		
			if(logica.obtenerTipoAtributo(sentencia.get(1)[1], sentencia.get(0)[1]).equals("entero")) {
				
				if(logica.esVacia(sentencia.get(1)[1])) { 
					
					insertarDepuracion("Error #N", "No hay registros ingresados aun en la tabla");
					
				}else {
			
    				aciertos++;
    				insertarDepuracion("Acierto #" + aciertos, "El valor minimo de los registros consultados es: " + logica.obtenerMinimo(sentencia.get(1)[1], sentencia.get(0)[1]));
    		        
        				
        		}
	        	
			}else {
				
				insertarDepuracion("Error #N", "El atributo debe de ser de tipo entero para esta operacion");
				
			}
			
		}else {
			
			insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datose seleccionada");
        	
		}
		
	}
	
	public void comandoPrimaryKey(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasDosLineas(sentencia)) {
    		
			if(logica.existeTabla(sentencia.get(1)[1])) {
			
				if(logica.esVacia(sentencia.get(1)[1])) { 
					
					if((logica.obtenerAtributo(sentencia.get(0)[1], sentencia.get(1)[1])==null)) {
						
						insertarDepuracion("Error #N", "El atributo no existe para tabla ingresada");
					
					}else if (logica.tieneClave(sentencia.get(1)[1])){
						
						logica.quitarClave(sentencia.get(1)[1]);
						
					}
						
					logica.hacerClave(sentencia.get(1)[1], sentencia.get(0)[1]);
					aciertos++;
		        	insertarDepuracion("Acierto #" + aciertos, "Se hizo clave primaria con exito el atributo: " + sentencia.get(0)[1] + " de la tabla: " + sentencia.get(1)[1]);
		        
				}else {
				
					insertarDepuracion("Error #N", "La tabla no debe de tener registros para la operacion a realizar");
					
				}
				
			}else {
				
				insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datose seleccionada");
	        	
			}
			
    	}
		
	}
	
	public void comandoDescribe(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
			if(logica.existeTabla(sentencia.get(0)[1])) {
			
				aciertos++;
				insertarDepuracion("Acierto #" + aciertos, "El usuario quiere obtener una descripcion sobre la tabla: " + sentencia.get(0)[1]);
        	
			}else {
				
				insertarDepuracion("Error #N", "La tabla ingresada no existe para la base de datos seleccionada");
	        	
			}
    	}
		
	}
	
	public void comandoHelp(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
			if(logica.comandoExiste(sentencia.get(0)[1])) {
					
				aciertos++;
				insertarDepuracion("Acierto #" + aciertos, "El usuario quiere obtener ayuda sobre el comando: " + sentencia.get(0)[1]);
        	
			}else {
				
				insertarDepuracion("Error #N", "El comando ingresado no existe o no tiene ayuda asociada");
				
			}
    	}
		
	}
	
	public void comandoJoinNatural(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { 
    		
        	insertarDepuracion("Error #03", "Demasiados argumentos en linea 1");
        	
    	}else {
    		
    		if(!(validaCantidadLineas(sentencia, 2, 2))) {
    			
	        	insertarDepuracion("Error #02", "Cantidad de lineas no valida");
	        	
    		}else {
    			
    			if(!(sentencia.get(1)[0].toUpperCase().equals("FROM"))) {
    				
		        	insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");
		        	
    			}else {
    			
        			if(!(validaCantidadArgumentos(sentencia, 1, sentencia.size(), 3))) {
        				
			        	insertarDepuracion("Error #03", "Cantidad de argumentos no valida en linea 2, recuerde que el join natural se realiza entre dos tablas");
			        	
        			}else {
        				
        				if((logica.existeTabla(sentencia.get(1)[1])) && (logica.existeTabla(sentencia.get(1)[2]))) {
        					
        					aciertos++;
        					insertarDepuracion("Acierto #" + aciertos, "El usuario quiere hacer un join natural entre las tablas: " + sentencia.get(1)[1] + " y " + sentencia.get(1)[2]);
			        	
        				}else {
        					
        					insertarDepuracion("Error #N", "La/s tabla/s ingresada/s no existe/n para la base de datos seleccionada");
        		        	
        				}
        			}
    			}
    		}
    	}
		
	}
	
	public void ejecutarComando(String comando, ArrayList<String[]> sentencia) {
		
	    if (acciones.containsKey(comando)) {
	    	
	    	if ((comando.equals("USE")) || (comando.equals("HELP") || (comando.equals("CREATE")))) {
		    	
		        acciones.get(comando).accept(sentencia);
		        
		    } else {
		    	
		        if (logica.bdSeleccionada()) {
		        	
		            acciones.get(comando).accept(sentencia);
		            
		        } else {
		        	
		            insertarDepuracion("Error #N", "Debe seleccionar la base de datos sobre la cual operar");
		            
		        }
		        
		    }
	        
	    } else {
	    	
	        insertarDepuracion("Error #01", "El comando " + comando + " no es v�lido");
	        
	    }
	    
	}


	
}