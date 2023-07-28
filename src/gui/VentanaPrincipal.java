package gui;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import logica.Fachada;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaBD;
	private JTable salida;
	private static JTable depuracion;
	
	Fachada logica = new Fachada();
	
	int aciertos=0;

	public List<String> comandosNivel1 = Arrays.asList("SHOW", "CREATE", "USE", "INSERT", "DELETE", "UPDATE", "NOTNULL", "SELECT",
			"COUNT", "AVG", "PRIMARYKEY", "DESCRIBE", "HELP", "JOINNATURAL", "MAX", "MIN");
	
	public List<String> comandosNivel2 = Arrays.asList("SET", "FROM", "WHERE", "TABLES", "DATABASE", "TABLE" , "VALUES");
	
	public List<String> operadoresLogicos = Arrays.asList("AND", "OR");
	
	public ArrayList<String[]> administraSentencia(String sentencia) {
		
		//Se pretende generar un arreglo de arreglos. Donde en cada posici�n del arreglo principal
		//se almacene una linea de la sentencia, de forma tal que cada palabra corresponda a una posicion de los arreglos secundarios
		
		//Separa el contenido en lineas
        String[] lineas = sentencia.split("\n");
        
        //Crea un arreglo para cada linea
        ArrayList<String[]> arregloLinea = new ArrayList<>();
        
        //Recorre cada linea
        for (String unaLinea : lineas) {
        	
            ArrayList<String> palabras = new ArrayList<>();
            
            Matcher matcher = Pattern.compile("\"([^\"]*)\"|\\S+").matcher(unaLinea);

            //Encuentra las palabras entre comillas o las palabras separadas por espacios
            while (matcher.find()) {
            	
                String palabra = matcher.group();

                //Si la palabra tiene comillas, se eliminan las comillas
                if (palabra.startsWith("\"") && palabra.endsWith("\"")) {
                	
                    palabra = palabra.substring(1, palabra.length() - 1);
               
                }

                palabras.add(palabra);
            
            }

            arregloLinea.add(palabras.toArray(new String[0]));
        }
        
		return arregloLinea;
		
	}

	public boolean validaCantidadArgumentos (ArrayList<String[]> sentencia, int posInicial, int posFinal, int cantArgumentos) {
		
		if(posInicial==posFinal) {
			
			return (sentencia.get(posInicial).length==cantArgumentos);
			
		}else {
		
			for (int i = posInicial; i < posFinal; i++) {
				
			    if(sentencia.get(i).length!=cantArgumentos) {
			    	
			    	return false;
			    	
			    }
			   
			}
		}
		
		return true;
		
	}
	
	public boolean validaTipoDato (String tipo) { 
		
		return ((tipo.toUpperCase().equals("CADENA")) || (tipo.toUpperCase().equals("ENTERO")));
		
	}
	
	public boolean validaTiposAtributos(ArrayList<String[]> sentencia, int posInicial, int posFinal) {
		
		for (int j = posInicial; j < posFinal; j++) {
			
			if(!(validaTipoDato(sentencia.get(j)[1]))) {
				
				return false;
				
			}
			
		}
		
		return true;
		
	}
	
	public boolean validaCantidadLineas(ArrayList<String[]> sentencia, int min, int max) {
		
		if(min==max) {
			
			return (sentencia.size()==min);
			
		}else {
		
			return ((sentencia.size()>=min) && (sentencia.size()<=max));
			
		}
		
	}
	
	public boolean validaSentenciasUnaLinea(ArrayList<String[]> sentencia) {
		
    	if(!(validaCantidadLineas(sentencia, 1, 1))){
        	
			VentanaPrincipal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
        	
    	}else {
    		
    		if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) {
    			
            	VentanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida");
            	
    		}else {
    		
            	return true;
    			
    		}
    		
    	}
		
		return false;
		
	}
	
	public boolean validaSentenciasDosLineas(ArrayList<String[]> sentencia) {
		
    	if(!(validaCantidadLineas(sentencia, 2, 2))){
        	
        	VentanaPrincipal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
        	
    	}else {
    		
    		if (!(validaCantidadArgumentos(sentencia, 0, sentencia.size(), 2))) {
    			
            	VentanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida");
            	
    		}else {
    			
    			if(!(sentencia.get(1)[0].toUpperCase().equals("FROM"))) {
    				
    	        	VentanaPrincipal.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");
                	
    			}else {
    				
    				return true;
    				
    			}
    		
    		}
    		
    	}
		
		return false;
		
	}
	
	public boolean validaSentenciasWhereComun(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadArgumentos(sentencia, 2, 2, 4))) {
			
        	VentanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida en linea 3");
        	
		}else {
		
			if(!(sentencia.get(2)[0].toUpperCase().equals("WHERE"))) {
				
            	VentanaPrincipal.insertarDepuracion("Error #01", "El comando: " + sentencia.get(2)[0].toUpperCase() + " no es valido");
            	
			}else {
				
				if(!(sentencia.get(2)[2].toUpperCase().equals("="))) {
					
	            	VentanaPrincipal.insertarDepuracion("Error #05", "El operador: " + sentencia.get(2)[2] + " no es valido");
	            	
				}else {
					
					return true;
				
				}
			}
			
		}
		
		return false;
		
	}
	
	public boolean validaSentenciasFrom(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 1, 1, 2))) {
			
        	VentanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida en la linea 2");
        	
		}else {
			
			if(!(sentencia.get(1)[0].toUpperCase().equals("FROM"))) {
				
	        	VentanaPrincipal.insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");
	        	
			}else {
				
				return true;
				
			}
		}
		
		return false;
		
	}
	
	public boolean validaSentenciasFromWhere(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadLineas(sentencia, 3, 3))){
        	
        	VentanaPrincipal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
        	
    	}else {
    		
    		if (validaSentenciasFrom(sentencia)) {
    			
    			if(validaSentenciasWhereComun(sentencia)) {
	    				
	    			return true;
	    			
    			}
    		
    		}
    		
    	}
		
		return false;
		
	}
	
	public boolean validaOperadoresLogicos(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadArgumentos(sentencia, 2, 2, 8))) {
			
        	VentanaPrincipal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida en la linea 3");
        	
		}else {
			
			if(!(sentencia.get(2)[2].toUpperCase().equals("=")) || !(sentencia.get(2)[6].toUpperCase().equals("="))) {
				
	        	VentanaPrincipal.insertarDepuracion("Error #05", "Operador/es de igualdad no valido en la linea 3");
	        	
			}else {
				
				return true;
			
			}
			
		}
		
		return false;
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void insertarDepuracion(String mensaje1, String mensaje2) {
		
		DefaultTableModel model = (DefaultTableModel) depuracion.getModel();
		
		Object[] nuevaFila = {mensaje1, mensaje2};
    	model.insertRow(0, nuevaFila);
		
	}
	
	public VentanaPrincipal() {
		
		Color fondoPrincipal = new Color (66,141,138);
		Color fondoVentana = new Color (187,218,219);
		Color fuentePrincipal = new Color (255,255,255);
		Color escritura = new Color (0,0,0);
		Color botones = new Color (3,60,88);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024,700);
		contentPane = new JPanel();
		contentPane.setBackground(fondoPrincipal);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel saludo = new JLabel("Bienvenido");
		saludo.setForeground(Color.WHITE);
		saludo.setBounds(29, 25, 223, 23);
		saludo.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		JLabel bdActual = new JLabel("Base de datos: ");
		bdActual.setForeground(Color.WHITE);
		bdActual.setBounds(616, 26, 223, 23);
		bdActual.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		JSeparator separator = new JSeparator();
		separator.setBounds(15, 55, 978, 10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(170, 71, 707, 103);
		
		JTextArea entrada = new JTextArea();
		entrada.setForeground(escritura);
		entrada.setBackground(fondoVentana);
		entrada.setFont(new Font("SansSerif", Font.PLAIN, 17));
		entrada.setToolTipText("Inserte sentencias aqu\u00ED");
		entrada.setCaretColor(Color.WHITE);
		scrollPane.setViewportView(entrada);
		contentPane.setLayout(null);
		contentPane.add(saludo);
		contentPane.add(bdActual);
		
		JButton limpiar = new JButton("LIMPIAR");
		limpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				entrada.setText("");

			}
		});
		limpiar.setFont(new Font("Tahoma", Font.BOLD, 13));
		limpiar.setBounds(887, 151, 99, 23);
		limpiar.setBackground(botones);
		limpiar.setForeground(Color.WHITE);
        limpiar.setFocusPainted(false); 
        
		JButton cerrarSesion = new JButton("CERRAR SESI\u00D3N");
		cerrarSesion.setFont(new Font("Tahoma", Font.BOLD, 13));
		cerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cerrarSesion.setBounds(843, 613, 143, 23);
		cerrarSesion.setBackground(botones);
		cerrarSesion.setForeground(Color.WHITE);
        cerrarSesion.setFocusPainted(false); 
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(25, 71, 135, 524);
		scrollPane_3.getViewport().setBackground(fondoVentana);
		contentPane.add(scrollPane_3);
		
		tablaBD = new JTable();
		tablaBD.setForeground(escritura);
		tablaBD.setBackground(fondoVentana);
		tablaBD.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
			},
			new String[] {
				"BASES DE DATOS"
			}
		));
		scrollPane_3.setViewportView(tablaBD);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(170, 504, 816, 91);
		scrollPane_2.getViewport().setBackground(fondoVentana);
		contentPane.add(scrollPane_2);
		
		depuracion = new JTable();
		depuracion.setForeground(escritura);
		depuracion.setBackground(fondoVentana);
		depuracion.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		depuracion.setModel(new DefaultTableModel(
			new Object[][] {
		//		{null, null},
			},
			new String[] {
				"NUMERO", "MENSAJE"
			}
		));
		depuracion.getColumnModel().getColumn(0).setPreferredWidth(89);
		depuracion.getColumnModel().getColumn(1).setPreferredWidth(725);
		scrollPane_2.setViewportView(depuracion);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(170, 185, 815, 310);
		scrollPane_1.getViewport().setBackground(fondoVentana);
		scrollPane_1.getVerticalScrollBar().setBackground(fondoVentana);
		scrollPane_1.getHorizontalScrollBar().setBackground(fondoVentana);
		contentPane.add(scrollPane_1);
		
		salida = new JTable();
		salida.setForeground(escritura);
		salida.setBackground(fondoVentana);
		salida.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		salida.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		scrollPane_1.setViewportView(salida);
		
		JButton ejecutar = new JButton("EJECUTAR");
		ejecutar.setFont(new Font("Tahoma", Font.BOLD, 13));
		ejecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<String[]> arregloLinea = administraSentencia(entrada.getText());
		        
		        String comando1=arregloLinea.get(0)[0].toUpperCase();
		        
		        if(!(comandosNivel1.contains(comando1))) { //Si el primer comando ingresado no es valido
		        	
		        	insertarDepuracion("Error #01", "El comando " + comando1 + " no es valido");
		        	
		        }else {
		        
			        switch (comando1) { 
			        
			            case "CREATE":
			            	
			            	if(!(arregloLinea.size()>1)){
			
					        	insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
					        	
			        		}else {
			        			
				            	if (!(validaCantidadArgumentos(arregloLinea, 0, 0, 1))) { //no tiene mas nada alado del create
				            		
						        	insertarDepuracion("Error #03", "Demasiados argumentos en linea 1");
						        
				            	}else {
			            			
				            		String comando2=arregloLinea.get(1)[0].toUpperCase();
				            		
				            		switch (comando2) {
				            		
				            			case "TABLE":
				            				
				            				if(!(validaCantidadLineas(arregloLinea, 3, 5))) {
				            					
									        	insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta, recuerde que se permiten de uno a tres atributos por tabla");
									        	
				            				}else {
				            					
			            					    if(!(validaCantidadArgumentos(arregloLinea, 1, arregloLinea.size(), 2))) {
			            						
			            							insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta entre las lineas 2 y " + arregloLinea.size());
										        	
			            						}else {
			            							
			            							if(!(validaTiposAtributos(arregloLinea, 2, arregloLinea.size()))) {
			            								
			            								insertarDepuracion("Error #04", "Tipos de datos incorrectos, recuerde que solo se admiten datos de tipo entero o cadena");
											        	
			            							}else {
			            								
			            								aciertos++;
			            								insertarDepuracion("Acierto #" + aciertos, "El usuario quiere crear una tabla llamada: " +  arregloLinea.get(1)[1]);
											        	
			            							}
			            						}		
				            				}
				            			
				            				break;
				            				
				            			case "DATABASE":
				            				
				            				if(!(validaCantidadLineas(arregloLinea, 2, 2))) {
				            					
									        	insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
									        	
				            				}else {
				            				
					            				if(!(validaCantidadArgumentos(arregloLinea, 1, 1, 2))) {
					            					
										        	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en la linea 2");
										        	
					            				}else {
					            					
					            					aciertos++;
										        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere crear una base de datos llamada: " + arregloLinea.get(1)[1]);
					            					
					            				}
					            				
				            				}
				            				
				            				break;
				            				
				            			default:
				            				
								        	insertarDepuracion("Error #01", "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido");
								        	
				            				break;
					            			
				            		}
				            	}
			        		}
			            	
			            	break;
			            	
			            case "INSERT":
			            	
			            	if (!(validaCantidadLineas(arregloLinea, 2, 2))){
		            			
					        	insertarDepuracion("Error #02", "Cantidad de lineas no valida");
			            		
			            	}else {
			            		
			            		if ((!(arregloLinea.get(1).length>1)) || (!(validaCantidadArgumentos(arregloLinea, 0, 0, 2)))) {
				            		
						        	insertarDepuracion("Error #03", "Cantidad de argumentos no valida");
						        	
				            	}else { 
				            		
				            		if(!(arregloLinea.get(1)[0].toUpperCase().equals("VALUES"))) {
					            			
							        	insertarDepuracion("Error #01", "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido");
							        	
					            	}else {
					            			
					            		aciertos++;
							        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere insertar datos en la tabla: " + arregloLinea.get(0)[1]);
							        	
					            	}
				            	}
			            	}
			            	
			                break;
			                
			            case "SHOW":
			            	
			            	if(validaSentenciasUnaLinea(arregloLinea)){
			            	
			            		if(!(arregloLinea.get(0)[1].toUpperCase().equals("TABLES"))) {
					            			
						        	insertarDepuracion("Error #01", "El comando: " + arregloLinea.get(0)[1].toUpperCase() + " no es valido");
						        	
					            }else {
					            			
					            	aciertos++;
					            	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere ver las tablas");
						        		
					            }
				            	
			            	}
			            	
			                break; 
			                
			            case "USE":
			            	
			            	if(validaSentenciasUnaLinea(arregloLinea)){
				            	
			            		aciertos++;
					        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere seleccionar la base de datos: " + arregloLinea.get(0)[1]);
					        	
			            	}
			            	
			                break; 

			            case "HELP":
			            	
			            	if(validaSentenciasUnaLinea(arregloLinea)){
				            	
			            		aciertos++;
					        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere obtener ayuda sobre el comando: " + arregloLinea.get(0)[1]);
					        	
			            	}
			            	
			                break;
			                
			            case "DESCRIBE":
			            	
			            	if(validaSentenciasUnaLinea(arregloLinea)){
				            	
			            		aciertos++;
					        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere obtener una descripcion sobre la tabla: " + arregloLinea.get(0)[1]);
					        	
			            	}
			            	
			                break;
			                
			            case "SELECT":
			            	
			            	if(!(validaCantidadArgumentos(arregloLinea, 0, 0, 2))) {
			            		
					        	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
					        	
			            	}else {
			            		
			            		if(!(validaCantidadLineas(arregloLinea, 3, 3))) {
			            			
						        	insertarDepuracion("Error #02", "Cantidad de lineas incorrecta");
						        	
			            		}else {
			            		
			            			if(validaSentenciasFrom(arregloLinea)) {
			            			
					            		if(arregloLinea.get(2).length==4) { //SI ES WHERE COMUN
					            	
							            	if(validaSentenciasWhereComun(arregloLinea)) {
							            		
							            		aciertos++;
									        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere realizar una consulta en la tabla: " + arregloLinea.get(1)[1] + " donde el atributo: " + arregloLinea.get(2)[1] + " vale " + arregloLinea.get(2)[3]);
									        	
							            	}	
							            	
							            }else {
							            		
						            		if(validaOperadoresLogicos(arregloLinea)) {
						            			
							            		String operador=arregloLinea.get(2)[4].toUpperCase();
							            		
						            			switch (operador) {
						            			
													case "AND": 
														
														aciertos++;
											        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere realizar una consulta en la tabla: " + arregloLinea.get(1)[1] + 
																" \n donde el atributo: " + arregloLinea.get(2)[1] + " valga " + arregloLinea.get(2)[3] +
																" \n y el atributo: " + arregloLinea.get(2)[5] + " valga " + arregloLinea.get(2)[7]);
											        	
														break;	
													
													case "OR": 
														
														aciertos++;
											        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere realizar una consulta en la tabla: " + arregloLinea.get(1)[1] + 
																" \n donde el atributo: " + arregloLinea.get(2)[1] + " valga " + arregloLinea.get(2)[3] +
																" \n 0 el atributo: " + arregloLinea.get(2)[5] + " valga " + arregloLinea.get(2)[7]);
											        	
														break;
														
												}
						            		}
							            }
			            			}
			            		}
			            	}
				            
			                break;
			                
			            case "UPDATE":
			            	
			            	if(!(validaCantidadLineas(arregloLinea, 3, 3))) {
			            		
					        	insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
					        	
			            	}else {
			            	
				            	if (!(validaCantidadArgumentos(arregloLinea, 0, 0, 2))) { 
				            		
						        	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
						        	
				            	}else {
				            	
					            	if(!(validaCantidadArgumentos(arregloLinea, 1, arregloLinea.size(), 4))) {
					            		
							        	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta entre las lineas 2 y 3");
							        	
					            	}else {
					            		
					            		if(!(arregloLinea.get(1)[0].toUpperCase().equals("SET"))) {
					            			
								        	insertarDepuracion("Error #01", "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido");
								        	
					            		}else {
					            			
						            		if(validaSentenciasWhereComun(arregloLinea)) {
						            			
						            			if(!(arregloLinea.get(1)[2].equals("="))){
						            				
										        	insertarDepuracion("Error #05", "El operador: " + arregloLinea.get(1)[2] + " no es valido");
										        	
						            			}else {
						            		
						            				aciertos++;
										        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere actualizar el dato identificado por: " + arregloLinea.get(2)[1] + " = " + arregloLinea.get(2)[3] + " de la tabla: " + arregloLinea.get(0)[1] + " \n que actualmente es: " + arregloLinea.get(1)[1] + " por " + arregloLinea.get(1)[3]);
										        	
						            			}
						            		}
					            		}
					            	}
				            	}
			            	}
			            	
			            	break;
			                
			            case "DELETE":
			            	
			            	if (!(validaCantidadArgumentos(arregloLinea, 0, 0, 1))) { 
			            		
					        	insertarDepuracion("Error #03", "Demasiados argumentos en linea 1");
					        	
			            	}else {
			            	
				            	if(validaSentenciasFromWhere(arregloLinea)) {
				            		
				            		aciertos++;
						        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere borrar el dato de la tabla: " + arregloLinea.get(1)[1] + " que cumple la condicion que el atributo " + arregloLinea.get(2)[1] + " es igual a: " + arregloLinea.get(2)[3]);
						        	
				            	}
			            	}
			            	
			            	break;
			            	
			            case "JOINNATURAL":
			            	
			            	if (!(validaCantidadArgumentos(arregloLinea, 0, 0, 1))) { 
			            		
					        	insertarDepuracion("Error #03", "Demasiados argumentos en linea 1");
					        	
			            	}else {
			            		
			            		if(!(validaCantidadLineas(arregloLinea, 2, 2))) {
			            			
						        	insertarDepuracion("Error #02", "Cantidad de lineas no valida");
						        	
			            		}else {
			            			
			            			if(!(arregloLinea.get(1)[0].toUpperCase().equals("FROM"))) {
			            				
							        	insertarDepuracion("Error #01", "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido");
							        	
			            			}else {
			            			
				            			if(!(validaCantidadArgumentos(arregloLinea, 1, arregloLinea.size(), 3))) {
				            				
								        	insertarDepuracion("Error #03", "Cantidad de argumentos no valida en linea 2, recuerde que el join natural se realiza entre dos tablas");
								        	
				            			}else {
				            				
				            				aciertos++;
								        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere hacer un join natural entre las tablas: " + arregloLinea.get(1)[1] + " y " + arregloLinea.get(1)[2]);
								        	
				            			}
			            			}
			            		}
			            	}
			            	
			            	break;
			                
			            case "NOTNULL":
			            	
			            	if(validaSentenciasDosLineas(arregloLinea)) {
			            		
			            		aciertos++;
					        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere hacer no nulo el atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1]);
					        	
			            	}
			            	
			            	break;
			              
			            case "COUNT":
			            	
			            	if (!(validaCantidadArgumentos(arregloLinea, 0, 0, 2))) { 
			            		
					        	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
					        	
			            	}else {
			            	
				            	if(validaSentenciasFromWhere(arregloLinea)) {
				            		
				            		aciertos++;
						        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere contar la cantidad de datos de la tabla: " + arregloLinea.get(1)[1] + " que cumple la condicion que el atributo " + arregloLinea.get(2)[1] + " es igual a: " + arregloLinea.get(2)[3]);
				            	
				            	}
				            	
			            	}
			            	
			            	break;
				              
			           case "PRIMARYKEY":
			        	   
			        	   if(validaSentenciasDosLineas(arregloLinea)) {
			            		
			            		aciertos++;
					        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere hacer clave primaria el atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1]);
				            	
			            	}
			            	
			            	break;
				          
			            case "MIN":
			            	
			            	if(validaSentenciasDosLineas(arregloLinea)) {
				            		
				            	aciertos++;
					        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere obtener el valor minimo del atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1]);
				            	
				            }
			            	
			            	break;
			                
			            case "MAX":
			            	
			            	if(validaSentenciasDosLineas(arregloLinea)) {
			            		
				            	aciertos++; 
					        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere obtener el valor maximo del atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1]);
				            	
				            }
			            	
			            	break;
			            	
			            case "AVG":
			            	
			            	if (!(validaCantidadArgumentos(arregloLinea, 0, 0, 2))) { 
			            		
					        	insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
				            	
			            	}else {
			            	
				            	if(validaSentenciasFromWhere(arregloLinea)) {
				            		
				            		aciertos++;
						        	insertarDepuracion("Acierto #" + aciertos, "El usuario quiere obtener el promedio de datos de la tabla: " + arregloLinea.get(1)[1] + " que cumple la condicion que el atributo " + arregloLinea.get(2)[1] + " es igual a: " + arregloLinea.get(2)[3]);
					            	
				            	}
			            	}
			            	
			            	break;
			           
			            default:
            				
				        	insertarDepuracion("Error #01", "El comando: " + arregloLinea.get(0)[0].toUpperCase() + " no es valido");
			            	
            				break;
			                    
			        }
		        }
			}
		});
		ejecutar.setBounds(887, 117, 99, 23);
		ejecutar.setBackground(botones);
		ejecutar.setForeground(Color.WHITE);
        ejecutar.setFocusPainted(false); 
		
		contentPane.add(scrollPane);
		contentPane.add(ejecutar);
		contentPane.add(limpiar);
		contentPane.add(cerrarSesion);
		contentPane.add(separator);
		
	}

}
