package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import comunicacion.FachadaLogica;
import comunicacion.IFachadaLogica;

public class Comandos {
	
	VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
	IFachadaLogica logica= new FachadaLogica();
	
    private int aciertos;
    private Map<String, Consumer<ArrayList<String[]>>> acciones; //es una funci�n que toma un valor y realiza alg�n tipo de acci�n con �l, pero no produce ning�n resultado como salida.

    public Comandos() {
    	
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

			ventanaPrincipal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");

		} else {

			if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) {

				ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida");

			} else {

				return true;

			}

		}

		return false;

	}

	public boolean validaSentenciasDosLineas(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 2, 2))) {

			ventanaPrincipal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");

		} else {

			if (!(validaCantidadArgumentos(sentencia, 0, sentencia.size(), 2))) {

				ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida");

			} else {

				if (!(sentencia.get(1)[0].toUpperCase().equals("FROM"))) {

					ventanaPrincipal.insertarDepuracion("Error #01",
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

			ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida en linea 3");

		} else {

			if (!(sentencia.get(2)[0].toUpperCase().equals("WHERE"))) {

				ventanaPrincipal.insertarDepuracion("Error #01", "El comando: " + sentencia.get(2)[0].toUpperCase() + " no es valido");

			} else {

				if (!(sentencia.get(2)[2].toUpperCase().equals("="))) {

					ventanaPrincipal.insertarDepuracion("Error #05", "El operador: " + sentencia.get(2)[2] + " no es valido");

				} else {

					return true;

				}
			}

		}

		return false;

	}

	public boolean validaSentenciasFrom(ArrayList<String[]> sentencia) {

		if (!(validaCantidadArgumentos(sentencia, 1, 1, 2))) {

			ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida en la linea 2");

		} else {

			if (!(sentencia.get(1)[0].toUpperCase().equals("FROM"))) {

				ventanaPrincipal.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");

			} else {

				return true;

			}
		}

		return false;

	}

	public boolean validaSentenciasFromWhere(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 3, 3))) {

			ventanaPrincipal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");

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

			ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida en la linea 3");

		} else {

			if (!(sentencia.get(2)[2].toUpperCase().equals("=")) || !(sentencia.get(2)[6].toUpperCase().equals("="))) {

				ventanaPrincipal.insertarDepuracion("Error #05", "Operador/es de igualdad no valido en la linea 3");

			} else {

				return true;

			}

		}

		return false;

	}

	public void comandoTable(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadLineas(sentencia, 3, 5))) {
			
			ventanaPrincipal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta, recuerde que se permiten de uno a tres atributos por tabla");
        	
		}else {
			
		    if(!(validaCantidadArgumentos(sentencia, 1, sentencia.size(), 2))) {
			
		    	ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta entre las lineas 2 y " + sentencia.size());
	        	
			}else {
				
				if(!(validaTiposAtributos(sentencia, 2, sentencia.size()))) {
					
					ventanaPrincipal.insertarDepuracion("Error #04", "Tipos de datos incorrectos, recuerde que solo se admiten datos de tipo entero o cadena");
		        	
				}else {
					
					aciertos++;
					ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere crear una tabla llamada: " +  sentencia.get(1)[1]);
		        	
				}
			}		
		}
		
	}
	
	public void comandoDatabase(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadLineas(sentencia, 2, 2))) {
			
        	ventanaPrincipal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
        	
		}else {
		
			if(!(validaCantidadArgumentos(sentencia, 1, 1, 2))) {
				
	        	ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en la linea 2");
	        	
			}else {
				
				aciertos++;
	        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere crear una base de datos llamada: " + sentencia.get(1)[1]);
				
			}
			
		}
		
	}
	
	public void comandoSelectAnd(ArrayList<String[]> sentencia) {
		
		aciertos++;
    	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere realizar una consulta en la tabla: " + sentencia.get(1)[1] + 
				" \n donde el atributo: " + sentencia.get(2)[1] + " valga " + sentencia.get(2)[3] +
				" \n y el atributo: " + sentencia.get(2)[5] + " valga " + sentencia.get(2)[7]);
    	
	}
	
	public void comandoSelectOr(ArrayList<String[]> sentencia) {
		
		aciertos++;
		ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere realizar una consulta en la tabla: " + sentencia.get(1)[1] + 
				" \n donde el atributo: " + sentencia.get(2)[1] + " valga " + sentencia.get(2)[3] +
				" \n 0 el atributo: " + sentencia.get(2)[5] + " valga " + sentencia.get(2)[7]);
		
	}
	
	public void comandoShow(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
    		if(!(sentencia.get(0)[1].toUpperCase().equals("TABLES"))) {
            			
	        	ventanaPrincipal.insertarDepuracion("Error #01", "El comando: " + sentencia.get(0)[1].toUpperCase() + " no es valido");
	        	
            }else {
            			
            	aciertos++;
            	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere ver las tablas");
	        		
            }
        	
    	}
		
	}
	
	public void comandoCreate(ArrayList<String[]> sentencia) {
		
		if(!(sentencia.size()>1)){
			
    		ventanaPrincipal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
        	
		}else {
			
        	if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { //no tiene mas nada alado del create
        		
        		ventanaPrincipal.insertarDepuracion("Error #03", "Demasiados argumentos en linea 1");
	        
        	}else {
    			
        		String comando2=sentencia.get(1)[0].toUpperCase();
        		
        		if(comando2.equals("TABLE")) { //TABLE
        			
        			comandoTable(sentencia);
        			
        		}else if(comando2.equals("DATABASE")) { //DATABASE
        			
        			comandoDatabase(sentencia);
        			
        		}else {
        			
        			ventanaPrincipal.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");
		        	
        		}
        		
        	}
        	
		}
		
	}
	
	public void comandoSelect(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadArgumentos(sentencia, 0, 0, 2))) {
    		
        	ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
        	
    	}else {
    		
    		if(!(validaCantidadLineas(sentencia, 3, 3))) {
    			
	        	ventanaPrincipal.insertarDepuracion("Error #02", "Cantidad de lineas incorrecta");
	        	
    		}else {
    		
    			if(validaSentenciasFrom(sentencia)) {
    			
            		if(sentencia.get(2).length==4) { //SI ES WHERE COMUN
            	
		            	if(validaSentenciasWhereComun(sentencia)) {
		            		
		            		aciertos++;
				        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere realizar una consulta en la tabla: " + sentencia.get(1)[1] + " donde el atributo: " + sentencia.get(2)[1] + " vale " + sentencia.get(2)[3]);
				        	
		            	}	
		            	
		            }else {
		            		
	            		if(validaOperadoresLogicos(sentencia)) {
	            			
		            		String operador=sentencia.get(2)[4].toUpperCase();
		            		
		            		if(operador.equals("AND")) {
		            			
		            			comandoSelectAnd(sentencia);
		            			
		            		} else if(operador.equals("OR")) {
		            			
		            			comandoSelectOr(sentencia);
		            			
		            		}
		            		
	            		}
		            }
    			}
    		}
    	}
		
	}
	
	public void comandoUse(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
    		aciertos++;
        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere seleccionar la base de datos: " + sentencia.get(0)[1]);
        	
    	}
		
	}
	
	public void comandoInsert(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadLineas(sentencia, 2, 2))){
			
        	ventanaPrincipal.insertarDepuracion("Error #02", "Cantidad de lineas no valida");
    		
    	}else {
    		
    		if ((!(sentencia.get(1).length>1)) || (!(validaCantidadArgumentos(sentencia, 0, 0, 2)))) {
        		
	        	ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida");
	        	
        	}else { 
        		
        		if(!(sentencia.get(1)[0].toUpperCase().equals("VALUES"))) {
            			
		        	ventanaPrincipal.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");
		        	
            	}else {
            			
            		aciertos++;
		        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere insertar datos en la tabla: " + sentencia.get(0)[1]);
		        	
            	}
        	}
    	}
		
	}
	
	public void comandoDelete(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { 
    		
        	ventanaPrincipal.insertarDepuracion("Error #03", "Demasiados argumentos en linea 1");
        	
    	}else {
    	
        	if(validaSentenciasFromWhere(sentencia)) {
        		
        		aciertos++;
	        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere borrar el dato de la tabla: " + sentencia.get(1)[1] + " que cumple la condicion que el atributo " + sentencia.get(2)[1] + " es igual a: " + sentencia.get(2)[3]);
	        	
        	}
    	}
		
	}
	
	public void comandoUpdate(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadLineas(sentencia, 3, 3))) {
    		
        	ventanaPrincipal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
        	
    	}else {
    	
        	if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) { 
        		
	        	ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
	        	
        	}else {
        	
            	if(!(validaCantidadArgumentos(sentencia, 1, sentencia.size(), 4))) {
            		
		        	ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta entre las lineas 2 y 3");
		        	
            	}else {
            		
            		if(!(sentencia.get(1)[0].toUpperCase().equals("SET"))) {
            			
			        	ventanaPrincipal.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");
			        	
            		}else {
            			
	            		if(validaSentenciasWhereComun(sentencia)) {
	            			
	            			if(!(sentencia.get(1)[2].equals("="))){
	            				
					        	ventanaPrincipal.insertarDepuracion("Error #05", "El operador: " + sentencia.get(1)[2] + " no es valido");
					        	
	            			}else {
	            		
	            				aciertos++;
					        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere actualizar el dato identificado por: " + sentencia.get(2)[1] + " = " + sentencia.get(2)[3] + " de la tabla: " + sentencia.get(0)[1] + " \n que actualmente es: " + sentencia.get(1)[1] + " por " + sentencia.get(1)[3]);
					        	
	            			}
	            		}
            		}
            	}
        	}
    	}
		
	}
	
	public void comandoNotNull(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasDosLineas(sentencia)) {
    		
    		aciertos++;
        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere hacer no nulo el atributo: " + sentencia.get(0)[1] + " de la tabla: " + sentencia.get(1)[1]);
        	
    	}
		
	}
	
	public void comandoCount(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) { 
    		
        	ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
        	
    	}else {
    	
        	if(validaSentenciasFromWhere(sentencia)) {
        		
        		aciertos++;
	        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere contar la cantidad de datos de la tabla: " + sentencia.get(1)[1] + " que cumple la condicion que el atributo " + sentencia.get(2)[1] + " es igual a: " + sentencia.get(2)[3]);
        	
        	}
        	
    	}
		
	}
	
	public void comandoAvg(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) { 
    		
        	ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
        	
    	}else {
    	
        	if(validaSentenciasFromWhere(sentencia)) {
        		
        		aciertos++;
	        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere obtener el promedio de datos de la tabla: " + sentencia.get(1)[1] + " que cumple la condicion que el atributo " + sentencia.get(2)[1] + " es igual a: " + sentencia.get(2)[3]);
            	
        	}
    	}
		
	}
	
	public void comandoMax(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasDosLineas(sentencia)) {
    		
        	aciertos++; 
        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere obtener el valor maximo del atributo: " + sentencia.get(0)[1] + " de la tabla: " + sentencia.get(1)[1]);
        	
        }
		
	}
	
	public void comandoMin(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasDosLineas(sentencia)) {
    		
        	aciertos++;
        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere obtener el valor minimo del atributo: " + sentencia.get(0)[1] + " de la tabla: " + sentencia.get(1)[1]);
        	
        }
		
	}
	
	public void comandoPrimaryKey(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasDosLineas(sentencia)) {
    		
    		aciertos++;
        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere hacer clave primaria el atributo: " + sentencia.get(0)[1] + " de la tabla: " + sentencia.get(1)[1]);
        	
    	}
		
	}
	
	public void comandoDescribe(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
    		aciertos++;
        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere obtener una descripcion sobre la tabla: " + sentencia.get(0)[1]);
        	
    	}
		
	}
	
	public void comandoHelp(ArrayList<String[]> sentencia) {
		
		if(validaSentenciasUnaLinea(sentencia)){
        	
    		aciertos++;
        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere obtener ayuda sobre el comando: " + sentencia.get(0)[1]);
        	
    	}
		
	}
	
	public void comandoJoinNatural(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 0, 0, 1))) { 
    		
        	ventanaPrincipal.insertarDepuracion("Error #03", "Demasiados argumentos en linea 1");
        	
    	}else {
    		
    		if(!(validaCantidadLineas(sentencia, 2, 2))) {
    			
	        	ventanaPrincipal.insertarDepuracion("Error #02", "Cantidad de lineas no valida");
	        	
    		}else {
    			
    			if(!(sentencia.get(1)[0].toUpperCase().equals("FROM"))) {
    				
		        	ventanaPrincipal.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");
		        	
    			}else {
    			
        			if(!(validaCantidadArgumentos(sentencia, 1, sentencia.size(), 3))) {
        				
			        	ventanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida en linea 2, recuerde que el join natural se realiza entre dos tablas");
			        	
        			}else {
        				
        				aciertos++;
			        	ventanaPrincipal.insertarDepuracion("Acierto #" + aciertos, "El usuario quiere hacer un join natural entre las tablas: " + sentencia.get(1)[1] + " y " + sentencia.get(1)[2]);
			        	
        			}
    			}
    		}
    	}
		
	}
	
    public void ejecutarComando(String comando, ArrayList<String[]> sentencia) {
    	
        if (acciones.containsKey(comando)) {
        	
            acciones.get(comando).accept(sentencia);
            
        } else {
        	
            ventanaPrincipal.insertarDepuracion("Error #01", "El comando " + comando + " no es v�lido");
            
        }
    }
	
}