package gui;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaBD;
	private JTable salida;
	static JTable depuracion;
	
	Principal principal = new Principal();
	
	public static void main(String[] args) { //BORRAR LUEGO
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

	public VentanaPrincipal() {
		//holiwis
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
		saludo.setForeground(fuentePrincipal);
		saludo.setBounds(29, 25, 223, 23);
		saludo.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		JLabel bdActual = new JLabel("Base de datos: ");
		bdActual.setForeground(fuentePrincipal);
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
		limpiar.setForeground(fuentePrincipal);
        limpiar.setFocusPainted(false); 
        
		JButton cerrarSesion = new JButton("CERRAR SESI\u00D3N");
		cerrarSesion.setFont(new Font("Tahoma", Font.BOLD, 13));
		cerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cerrarSesion.setBounds(843, 613, 143, 23);
		cerrarSesion.setBackground(botones);
		cerrarSesion.setForeground(fuentePrincipal);
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
				
				ArrayList<String[]> arregloLinea = principal.administraSentencia(entrada.getText());
		        
		        String comando1=arregloLinea.get(0)[0].toUpperCase();
		        
		        if(!(principal.comandosNivel1.contains(comando1))) { //Si el primer comando ingresado no es valido
		        	
		        	principal.insertarDepuracion("Error #01", "El comando " + comando1 + " no es valido");
		        	
		        }else {
		        
			        switch (comando1) { 
			        
			            case "CREATE":
			            	
			            	if(!(arregloLinea.size()>1)){
			
			            		principal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
					        	
			        		}else {
			        			
				            	if (!(principal.validaCantidadArgumentos(arregloLinea, 0, 0, 1))) { //no tiene mas nada alado del create
				            		
				            		principal.insertarDepuracion("Error #03", "Demasiados argumentos en linea 1");
						        
				            	}else {
			            			
				            		String comando2=arregloLinea.get(1)[0].toUpperCase();
				            		
				            		switch (comando2) {
				            		
				            			case "TABLE":
				            				
				            				if(!(principal.validaCantidadLineas(arregloLinea, 3, 5))) {
				            					
				            					principal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta, recuerde que se permiten de uno a tres atributos por tabla");
									        	
				            				}else {
				            					
			            					    if(!(principal.validaCantidadArgumentos(arregloLinea, 1, arregloLinea.size(), 2))) {
			            						
			            					    	principal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta entre las lineas 2 y " + arregloLinea.size());
										        	
			            						}else {
			            							
			            							if(!(principal.validaTiposAtributos(arregloLinea, 2, arregloLinea.size()))) {
			            								
			            								principal.insertarDepuracion("Error #04", "Tipos de datos incorrectos, recuerde que solo se admiten datos de tipo entero o cadena");
											        	
			            							}else {
			            								
			            								principal.aciertos++;
			            								principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere crear una tabla llamada: " +  arregloLinea.get(1)[1]);
											        	
			            							}
			            						}		
				            				}
				            			
				            				break;
				            				
				            			case "DATABASE":
				            				
				            				if(!(principal.validaCantidadLineas(arregloLinea, 2, 2))) {
				            					
									        	principal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
									        	
				            				}else {
				            				
					            				if(!(principal.validaCantidadArgumentos(arregloLinea, 1, 1, 2))) {
					            					
										        	principal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en la linea 2");
										        	
					            				}else {
					            					
					            					principal.aciertos++;
										        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere crear una base de datos llamada: " + arregloLinea.get(1)[1]);
					            					
					            				}
					            				
				            				}
				            				
				            				break;
				            				
				            			default:
				            				
								        	principal.insertarDepuracion("Error #01", "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido");
								        	
				            				break;
					            			
				            		}
				            	}
			        		}
			            	
			            	break;
			            	
			            case "INSERT":
			            	
			            	if (!(principal.validaCantidadLineas(arregloLinea, 2, 2))){
		            			
					        	principal.insertarDepuracion("Error #02", "Cantidad de lineas no valida");
			            		
			            	}else {
			            		
			            		if ((!(arregloLinea.get(1).length>1)) || (!(principal.validaCantidadArgumentos(arregloLinea, 0, 0, 2)))) {
				            		
						        	principal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida");
						        	
				            	}else { 
				            		
				            		if(!(arregloLinea.get(1)[0].toUpperCase().equals("VALUES"))) {
					            			
							        	principal.insertarDepuracion("Error #01", "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido");
							        	
					            	}else {
					            			
					            		principal.aciertos++;
							        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere insertar datos en la tabla: " + arregloLinea.get(0)[1]);
							        	
					            	}
				            	}
			            	}
			            	
			                break;
			                
			            case "SHOW":
			            	
			            	if(principal.validaSentenciasUnaLinea(arregloLinea)){
			            	
			            		if(!(arregloLinea.get(0)[1].toUpperCase().equals("TABLES"))) {
					            			
						        	principal.insertarDepuracion("Error #01", "El comando: " + arregloLinea.get(0)[1].toUpperCase() + " no es valido");
						        	
					            }else {
					            			
					            	principal.aciertos++;
					            	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere ver las tablas");
						        		
					            }
				            	
			            	}
			            	
			                break; 
			                
			            case "USE":
			            	
			            	if(principal.validaSentenciasUnaLinea(arregloLinea)){
				            	
			            		principal.aciertos++;
					        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere seleccionar la base de datos: " + arregloLinea.get(0)[1]);
					        	
			            	}
			            	
			                break; 

			            case "HELP":
			            	
			            	if(principal.validaSentenciasUnaLinea(arregloLinea)){
				            	
			            		principal.aciertos++;
					        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere obtener ayuda sobre el comando: " + arregloLinea.get(0)[1]);
					        	
			            	}
			            	
			                break;
			                
			            case "DESCRIBE":
			            	
			            	if(principal.validaSentenciasUnaLinea(arregloLinea)){
				            	
			            		principal.aciertos++;
					        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere obtener una descripcion sobre la tabla: " + arregloLinea.get(0)[1]);
					        	
			            	}
			            	
			                break;
			                
			            case "SELECT":
			            	
			            	if(!(principal.validaCantidadArgumentos(arregloLinea, 0, 0, 2))) {
			            		
					        	principal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
					        	
			            	}else {
			            		
			            		if(!(principal.validaCantidadLineas(arregloLinea, 3, 3))) {
			            			
						        	principal.insertarDepuracion("Error #02", "Cantidad de lineas incorrecta");
						        	
			            		}else {
			            		
			            			if(principal.validaSentenciasFrom(arregloLinea)) {
			            			
					            		if(arregloLinea.get(2).length==4) { //SI ES WHERE COMUN
					            	
							            	if(principal.validaSentenciasWhereComun(arregloLinea)) {
							            		
							            		principal.aciertos++;
									        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere realizar una consulta en la tabla: " + arregloLinea.get(1)[1] + " donde el atributo: " + arregloLinea.get(2)[1] + " vale " + arregloLinea.get(2)[3]);
									        	
							            	}	
							            	
							            }else {
							            		
						            		if(principal.validaOperadoresLogicos(arregloLinea)) {
						            			
							            		String operador=arregloLinea.get(2)[4].toUpperCase();
							            		
						            			switch (operador) {
						            			
													case "AND": 
														
														principal.aciertos++;
											        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere realizar una consulta en la tabla: " + arregloLinea.get(1)[1] + 
																" \n donde el atributo: " + arregloLinea.get(2)[1] + " valga " + arregloLinea.get(2)[3] +
																" \n y el atributo: " + arregloLinea.get(2)[5] + " valga " + arregloLinea.get(2)[7]);
											        	
														break;	
													
													case "OR": 
														
														principal.aciertos++;
											        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere realizar una consulta en la tabla: " + arregloLinea.get(1)[1] + 
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
			            	
			            	if(!(principal.validaCantidadLineas(arregloLinea, 3, 3))) {
			            		
					        	principal.insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");
					        	
			            	}else {
			            	
				            	if (!(principal.validaCantidadArgumentos(arregloLinea, 0, 0, 2))) { 
				            		
						        	principal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
						        	
				            	}else {
				            	
					            	if(!(principal.validaCantidadArgumentos(arregloLinea, 1, arregloLinea.size(), 4))) {
					            		
							        	principal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta entre las lineas 2 y 3");
							        	
					            	}else {
					            		
					            		if(!(arregloLinea.get(1)[0].toUpperCase().equals("SET"))) {
					            			
								        	principal.insertarDepuracion("Error #01", "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido");
								        	
					            		}else {
					            			
						            		if(principal.validaSentenciasWhereComun(arregloLinea)) {
						            			
						            			if(!(arregloLinea.get(1)[2].equals("="))){
						            				
										        	principal.insertarDepuracion("Error #05", "El operador: " + arregloLinea.get(1)[2] + " no es valido");
										        	
						            			}else {
						            		
						            				principal.aciertos++;
										        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere actualizar el dato identificado por: " + arregloLinea.get(2)[1] + " = " + arregloLinea.get(2)[3] + " de la tabla: " + arregloLinea.get(0)[1] + " \n que actualmente es: " + arregloLinea.get(1)[1] + " por " + arregloLinea.get(1)[3]);
										        	
						            			}
						            		}
					            		}
					            	}
				            	}
			            	}
			            	
			            	break;
			                
			            case "DELETE":
			            	
			            	if (!(principal.validaCantidadArgumentos(arregloLinea, 0, 0, 1))) { 
			            		
					        	principal.insertarDepuracion("Error #03", "Demasiados argumentos en linea 1");
					        	
			            	}else {
			            	
				            	if(principal.validaSentenciasFromWhere(arregloLinea)) {
				            		
				            		principal.aciertos++;
						        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere borrar el dato de la tabla: " + arregloLinea.get(1)[1] + " que cumple la condicion que el atributo " + arregloLinea.get(2)[1] + " es igual a: " + arregloLinea.get(2)[3]);
						        	
				            	}
			            	}
			            	
			            	break;
			            	
			            case "JOINNATURAL":
			            	
			            	if (!(principal.validaCantidadArgumentos(arregloLinea, 0, 0, 1))) { 
			            		
					        	principal.insertarDepuracion("Error #03", "Demasiados argumentos en linea 1");
					        	
			            	}else {
			            		
			            		if(!(principal.validaCantidadLineas(arregloLinea, 2, 2))) {
			            			
						        	principal.insertarDepuracion("Error #02", "Cantidad de lineas no valida");
						        	
			            		}else {
			            			
			            			if(!(arregloLinea.get(1)[0].toUpperCase().equals("FROM"))) {
			            				
							        	principal.insertarDepuracion("Error #01", "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido");
							        	
			            			}else {
			            			
				            			if(!(principal.validaCantidadArgumentos(arregloLinea, 1, arregloLinea.size(), 3))) {
				            				
								        	principal.insertarDepuracion("Error #03", "Cantidad de argumentos no valida en linea 2, recuerde que el join natural se realiza entre dos tablas");
								        	
				            			}else {
				            				
				            				principal.aciertos++;
								        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere hacer un join natural entre las tablas: " + arregloLinea.get(1)[1] + " y " + arregloLinea.get(1)[2]);
								        	
				            			}
			            			}
			            		}
			            	}
			            	
			            	break;
			                
			            case "NOTNULL":
			            	
			            	if(principal.validaSentenciasDosLineas(arregloLinea)) {
			            		
			            		principal.aciertos++;
					        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere hacer no nulo el atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1]);
					        	
			            	}
			            	
			            	break;
			              
			            case "COUNT":
			            	
			            	if (!(principal.validaCantidadArgumentos(arregloLinea, 0, 0, 2))) { 
			            		
					        	principal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
					        	
			            	}else {
			            	
				            	if(principal.validaSentenciasFromWhere(arregloLinea)) {
				            		
				            		principal.aciertos++;
						        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere contar la cantidad de datos de la tabla: " + arregloLinea.get(1)[1] + " que cumple la condicion que el atributo " + arregloLinea.get(2)[1] + " es igual a: " + arregloLinea.get(2)[3]);
				            	
				            	}
				            	
			            	}
			            	
			            	break;
				              
			           case "PRIMARYKEY":
			        	   
			        	   if(principal.validaSentenciasDosLineas(arregloLinea)) {
			            		
			            		principal.aciertos++;
					        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere hacer clave primaria el atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1]);
				            	
			            	}
			            	
			            	break;
				          
			            case "MIN":
			            	
			            	if(principal.validaSentenciasDosLineas(arregloLinea)) {
				            		
				            	principal.aciertos++;
					        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere obtener el valor minimo del atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1]);
				            	
				            }
			            	
			            	break;
			                
			            case "MAX":
			            	
			            	if(principal.validaSentenciasDosLineas(arregloLinea)) {
			            		
				            	principal.aciertos++; 
					        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere obtener el valor maximo del atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1]);
				            	
				            }
			            	
			            	break;
			            	
			            case "AVG":
			            	
			            	if (!(principal.validaCantidadArgumentos(arregloLinea, 0, 0, 2))) { 
			            		
					        	principal.insertarDepuracion("Error #03", "Cantidad de argumentos incorrecta en linea 1");
				            	
			            	}else {
			            	
				            	if(principal.validaSentenciasFromWhere(arregloLinea)) {
				            		
				            		principal.aciertos++;
						        	principal.insertarDepuracion("Acierto #" + principal.aciertos, "El usuario quiere obtener el promedio de datos de la tabla: " + arregloLinea.get(1)[1] + " que cumple la condicion que el atributo " + arregloLinea.get(2)[1] + " es igual a: " + arregloLinea.get(2)[3]);
					            	
				            	}
			            	}
			            	
			            	break;
			           
			            default:
            				
				        	principal.insertarDepuracion("Error #01", "El comando: " + arregloLinea.get(0)[0].toUpperCase() + " no es valido");
			            	
            				break;
			                    
			        }
		        }
			}
		});
		ejecutar.setBounds(887, 117, 99, 23);
		ejecutar.setBackground(botones);
		ejecutar.setForeground(fuentePrincipal);
        ejecutar.setFocusPainted(false); 
		
		contentPane.add(scrollPane);
		contentPane.add(ejecutar);
		contentPane.add(limpiar);
		contentPane.add(cerrarSesion);
		contentPane.add(separator);
		
	}

}
