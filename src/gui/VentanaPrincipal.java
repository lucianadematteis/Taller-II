package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import logica.Fachada;
import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTable tablaBD;
	private JTable salida;
	private JTable depuracion;
	
	Fachada logica = new Fachada();

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
		entrada.setFont(new Font("SansSerif", Font.PLAIN, 13));
		entrada.setToolTipText("Inserte sentencias aqu\u00ED");
		scrollPane.setViewportView(entrada);
		contentPane.setLayout(null);
		contentPane.add(saludo);
		contentPane.add(bdActual);
		
		JButton ejecutar = new JButton("EJECUTAR");
		ejecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//LO QUE QUIERO HACER ES UN ARREGLO DE ARREGLOS
				//PARA GUARDAR EN CADA POSICION (ARREGLO PRINCIPAL) UNA LINEA Y DENTRO DE CADA LINEA (ARREGLOS SECUNDARIOS) EN CADA POSICION UNA PALABRA
				
				//Separa el contenido en lineas
				String sentencia = entrada.getText();
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
		        
		        String comando1=arregloLinea.get(0)[0].toUpperCase();
		        
		        if(!(logica.comandosNivel1.contains(comando1))) { //Si el primer comando ingresado no es valido
		        	
		        	JOptionPane.showMessageDialog(null, "El comando " + comando1 + " no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
        			
		        }else {
		        
			        switch (comando1) { 
			        
			            case "CREATE":
			            	
			            	if(!(arregloLinea.size()>1)){
			
			            		JOptionPane.showMessageDialog(null, "La cantidad de lineas ingresada es incorrecta", "ERROR", JOptionPane.ERROR_MESSAGE);
                    			
			        		}else {
			        			
				            	if (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 1))) { //no tiene mas nada alado del create
				            		
				            		JOptionPane.showMessageDialog(null, "Demasiados argumentos en linea 1", "ERROR", JOptionPane.ERROR_MESSAGE);
				        			
				            	}else {
			            			
				            		String comando2=arregloLinea.get(1)[0].toUpperCase();
				            		
				            		switch (comando2) {
				            		
				            			case "TABLE":
				            				
				            				if(!(logica.validaCantidadLineas(arregloLinea, 3, 5))) {
				            					
				            					JOptionPane.showMessageDialog(null, "La cantidad de lineas ingresada es incorrecta, recuerde que se permiten de uno a tres atributos por tabla", "ERROR", JOptionPane.ERROR_MESSAGE);
				                    			
				            				}else {
				            					
			            					    if(!(logica.validaCantidadArgumentos(arregloLinea, 1, arregloLinea.size(), 2))) {
			            							
			            							JOptionPane.showMessageDialog(null, "Cantidad de argumentos incorrecta entre las lineas 2 y " + arregloLinea.size(), "ERROR", JOptionPane.ERROR_MESSAGE);
			            		        			
			            						}else {
			            							
			            							if(!(logica.validaTiposAtributos(arregloLinea, 2, arregloLinea.size()))) {
			            								
			            								JOptionPane.showMessageDialog(null, "Tipos de datos incorrectos, recuerde que solo se admiten datos de tipo entero o cadena", "ERROR", JOptionPane.ERROR_MESSAGE);
			            			        			
			            							}else {
			            								
			            								JOptionPane.showMessageDialog(null, "El usuario quiere crear una tabla llamada: " +  arregloLinea.get(1)[1], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
			            			        			
			            							}
			            						}		
				            				}
				            			
				            				break;
				            				
				            			case "DATABASE":
				            				
				            				if(!(logica.validaCantidadLineas(arregloLinea, 2, 2))) {
				            					
				            					JOptionPane.showMessageDialog(null, "La cantidad de lineas ingresada es incorrecta", "ERROR", JOptionPane.ERROR_MESSAGE);
				                    		
				            				}else {
				            				
					            				if(!(logica.validaCantidadArgumentos(arregloLinea, 1, 1, 2))) {
					            					
					            					JOptionPane.showMessageDialog(null, "Cantidad de argumentos incorrecta en la linea 2", "ERROR", JOptionPane.ERROR_MESSAGE);
					                    			
					            				}else {
					            					
					            					JOptionPane.showMessageDialog(null, "El usuario quiere crear una base de datos llamada: " + arregloLinea.get(1)[1], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
					                    			
					            				}
					            				
				            				}
				            				
				            				break;
				            				
				            			default:
				            				
				            				JOptionPane.showMessageDialog(null, "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
			                    			
				            				break;
					            			
				            		}
				            	}
			        		}
			            	
			            	break;
			            	
			            case "INSERT":
			            	
			            	if (!(logica.validaCantidadLineas(arregloLinea, 2, 2))){
		            			
			            		JOptionPane.showMessageDialog(null, "Cantidad de lineas no valida", "ERROR", JOptionPane.ERROR_MESSAGE);
			            		
			            	}else {
			            		
			            		if ((!(arregloLinea.get(1).length>1)) || (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 2)))) {
				            		
				            		JOptionPane.showMessageDialog(null, "Cantidad de argumentos no valida", "ERROR", JOptionPane.ERROR_MESSAGE);
				            		
				            	}else { 
				            		
				            		if(!(arregloLinea.get(1)[0].toUpperCase().equals("VALUES"))) {
					            			
					            		JOptionPane.showMessageDialog(null, "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
						            		
					            	}else {
					            			
					            		JOptionPane.showMessageDialog(null, "El usuario quiere insertar datos en la tabla: " + arregloLinea.get(0)[1], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
						            		
					            	}
				            	}
			            	}
			            	
			                break;
			                
			            case "SHOW":
			            	
			            	if(logica.validaSentenciasUnaLinea(arregloLinea)){
			            	
			            		if(!(arregloLinea.get(0)[1].toUpperCase().equals("TABLES"))) {
					            			
					            	JOptionPane.showMessageDialog(null, "El comando: " + arregloLinea.get(0)[1].toUpperCase() + " no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
						            		
					            }else {
					            			
					            	JOptionPane.showMessageDialog(null, "El usuario quiere ver las tablas", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
						            		
					            }
				            	
			            	}
			            	
			                break; 
			                
			            case "USE":
			            	
			            	if(logica.validaSentenciasUnaLinea(arregloLinea)){
				            	
			            		JOptionPane.showMessageDialog(null, "El usuario quiere seleccionar la base de datos: " + arregloLinea.get(0)[1], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
							    
			            	}
			            	
			                break; 

			            case "HELP":
			            	
			            	if(logica.validaSentenciasUnaLinea(arregloLinea)){
				            	
			            		JOptionPane.showMessageDialog(null, "El usuario quiere obtener ayuda sobre el comando: " + arregloLinea.get(0)[1], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
						        
			            	}
			            	
			                break;
			                
			            case "DESCRIBE":
			            	
			            	if(logica.validaSentenciasUnaLinea(arregloLinea)){
				            	
			            		JOptionPane.showMessageDialog(null, "El usuario quiere obtener una descripcion sobre la tabla: " + arregloLinea.get(0)[1], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
						        
			            	}
			            	
			                break;
			                
			            case "SELECT":
			            	
			            	if(!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 2))) {
			            		
			            		JOptionPane.showMessageDialog(null, "Cantidad de argumentos incorrecta en linea 1", "ERROR", JOptionPane.ERROR_MESSAGE);
					            
			            	}else {
			            		
			            		if(!(logica.validaCantidadLineas(arregloLinea, 3, 3))) {
			            			
			            			JOptionPane.showMessageDialog(null, "Cantidad de lineas incorrecta", "ERROR", JOptionPane.ERROR_MESSAGE);
						            
			            		}else {
			            		
			            			if(logica.validaSentenciasFrom(arregloLinea)) {
			            			
					            		if(arregloLinea.get(2).length==4) { //SI ES WHERE COMUN
					            	
							            	if(logica.validaSentenciasWhereComun(arregloLinea)) {
							            		
							            		JOptionPane.showMessageDialog(null, "El usuario quiere realizar una consulta en la tabla: " + arregloLinea.get(1)[1] + " donde el atributo: " + arregloLinea.get(2)[1] + " vale: " + arregloLinea.get(2)[3] , "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
							            	
							            	}	
							            	
							            }else {
							            		
						            		if(logica.validaOperadoresLogicos(arregloLinea)) {
						            			
							            		String operador=arregloLinea.get(2)[4].toUpperCase();
							            		
						            			switch (operador) {
						            			
													case "AND": 
														
														JOptionPane.showMessageDialog(null, "El usuario quiere realizar una consulta en la tabla: " + arregloLinea.get(1)[1] + 
																" \n donde el atributo: " + arregloLinea.get(2)[1] + " valga " + arregloLinea.get(2)[3] +
																" \n y el atributo: " + arregloLinea.get(2)[5] + " valga " + arregloLinea.get(2)[7], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
										            	
														break;	
													
													case "OR": 
														
														JOptionPane.showMessageDialog(null, "El usuario quiere realizar una consulta en la tabla: " + arregloLinea.get(1)[1] + 
																" \n donde el atributo: " + arregloLinea.get(2)[1] + " valga " + arregloLinea.get(2)[3] +
																" \n 0 el atributo: " + arregloLinea.get(2)[5] + " valga " + arregloLinea.get(2)[7], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
										            	
														break;
														
												}
						            		}
							            }
			            			}
			            		}
			            	}
				            
			                break;
			                
			            case "UPDATE":
			            	
			            	if(!(logica.validaCantidadLineas(arregloLinea, 3, 3))) {
			            		
			            		JOptionPane.showMessageDialog(null, "La cantidad de lineas ingresada es incorrecta", "ERROR", JOptionPane.ERROR_MESSAGE);
	                    		
			            	}else {
			            	
				            	if (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 2))) { 
				            		
				            		JOptionPane.showMessageDialog(null, "Cantidad de argumentos incorrecta en linea 1", "ERROR", JOptionPane.ERROR_MESSAGE);
				        			
				            	}else {
				            	
					            	if(!(logica.validaCantidadArgumentos(arregloLinea, 1, arregloLinea.size(), 4))) {
					            		
					            		JOptionPane.showMessageDialog(null, "Cantidad de argumentos incorrecta entre las lineas 2 y 3", "ERROR", JOptionPane.ERROR_MESSAGE);
					        			
					            	}else {
					            		
					            		if(!(arregloLinea.get(1)[0].toUpperCase().equals("SET"))) {
					            			
					            			JOptionPane.showMessageDialog(null, "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
								            
					            		}else {
					            			
						            		if(logica.validaSentenciasWhereComun(arregloLinea)) {
						            			
						            			if(!(arregloLinea.get(1)[2].equals("="))){
						            				
						            				JOptionPane.showMessageDialog(null, "El operador: " + arregloLinea.get(1)[2] + " no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
						        	        		
						            			}else {
						            		
						            				JOptionPane.showMessageDialog(null, "El usuario quiere actualizar el dato identificado por: " + arregloLinea.get(2)[1] + " = " + arregloLinea.get(2)[3] + " de la tabla: " + arregloLinea.get(0)[1] + " \n que actualmente es: " + arregloLinea.get(1)[1] + " por " + arregloLinea.get(1)[3], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
						            		
						            			}
						            		}
					            		}
					            	}
				            	}
			            	}
			            	
			            	break;
			                
			            case "DELETE":
			            	
			            	if (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 1))) { 
			            		
			            		JOptionPane.showMessageDialog(null, "Demasiados argumentos en linea 1", "ERROR", JOptionPane.ERROR_MESSAGE);
			        			
			            	}else {
			            	
				            	if(logica.validaSentenciasFromWhere(arregloLinea)) {
				            		
				            		JOptionPane.showMessageDialog(null, "El usuario quiere borrar el dato de la tabla: " + arregloLinea.get(1)[1] + " que cumple la condicion que el atributo " + arregloLinea.get(2)[1] + " es igual a: " + arregloLinea.get(2)[3], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
				            		
				            	}
			            	}
			            	
			            	break;
			            	
			            case "JOINNATURAL":
			            	
			            	if (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 1))) { 
			            		
			            		JOptionPane.showMessageDialog(null, "Demasiados argumentos en linea 1", "ERROR", JOptionPane.ERROR_MESSAGE);
			        			
			            	}else {
			            		
			            		if(!(logica.validaCantidadLineas(arregloLinea, 2, 2))) {
			            			
			            			JOptionPane.showMessageDialog(null, "Cantidad de lineas no valida", "ERROR", JOptionPane.ERROR_MESSAGE);
				            		
			            		}else {
			            			
			            			if(!(arregloLinea.get(1)[0].toUpperCase().equals("FROM"))) {
			            				
			            				JOptionPane.showMessageDialog(null, "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
							            
			            			}else {
			            			
				            			if(!(logica.validaCantidadArgumentos(arregloLinea, 1, arregloLinea.size(), 3))) {
				            				
				            				JOptionPane.showMessageDialog(null, "Cantidad de argumentos no valida en linea 2, recuerde que el join natural se realiza entre dos tablas", "ERROR", JOptionPane.ERROR_MESSAGE);
							            	
				            			}else {
				            				
				            				JOptionPane.showMessageDialog(null, "El usuario quiere hacer un join natural entre las tablas: " + arregloLinea.get(1)[1] + " y " + arregloLinea.get(1)[2], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
								            
				            			}
			            			}
			            		}
			            	}
			            	
			            	break;
			                
			            case "NOTNULL":
			            	
			            	if(logica.validaSentenciasDosLineas(arregloLinea)) {
			            		
			            		JOptionPane.showMessageDialog(null, "El usuario quiere hacer no nulo el atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
					            
			            	}
			            	
			            	break;
			              
			            case "COUNT":
			            	
			            	if (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 2))) { 
			            		
			            		JOptionPane.showMessageDialog(null, "Cantidad de argumentos incorrecta en linea 1", "ERROR", JOptionPane.ERROR_MESSAGE);
			        			
			            	}else {
			            	
				            	if(logica.validaSentenciasFromWhere(arregloLinea)) {
				            		
				            		JOptionPane.showMessageDialog(null, "El usuario quiere contar la cantidad de datos de la tabla: " + arregloLinea.get(1)[1] + " que cumple la condicion que el atributo " + arregloLinea.get(2)[1] + " es igual a: " + arregloLinea.get(2)[3], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
				            		
				            	}
				            	
			            	}
			            	
			            	break;
				              
			           case "PRIMARYKEY":
			        	   
			        	   if(logica.validaSentenciasDosLineas(arregloLinea)) {
			            		
			            		JOptionPane.showMessageDialog(null, "El usuario quiere hacer clave primaria el atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
					            
			            	}
			            	
			            	break;
				          
			            case "MIN":
			            	
			            	if(logica.validaSentenciasDosLineas(arregloLinea)) {
				            		
				            	JOptionPane.showMessageDialog(null, "El usuario quiere obtener el valor minimo del atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
						            
				            }
			            	
			            	break;
			                
			            case "MAX":
			            	
			            	if(logica.validaSentenciasDosLineas(arregloLinea)) {
			            		
				            	JOptionPane.showMessageDialog(null, "El usuario quiere obtener el valor maximo del atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
						            
				            }
			            	
			            	break;
			            	
			            case "AVG":
			            	
			            	if (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 2))) { 
			            		
			            		JOptionPane.showMessageDialog(null, "Cantidad de argumentos incorrecta en linea 1", "ERROR", JOptionPane.ERROR_MESSAGE);
			        			
			            	}else {
			            	
				            	if(logica.validaSentenciasFromWhere(arregloLinea)) {
				            		
				            		JOptionPane.showMessageDialog(null, "El usuario quiere obtener el promedio de datos de la tabla: " + arregloLinea.get(1)[1] + " que cumple la condicion que el atributo " + arregloLinea.get(2)[1] + " es igual a: " + arregloLinea.get(2)[3], "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
				            		
				            	}
			            	}
			            	
			            	break;
			           
			            default:
            				
            				JOptionPane.showMessageDialog(null, "El comando: " + arregloLinea.get(0)[0].toUpperCase() + " no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
                			
            				break;
			                    
			        }
		        }
			}
		});
		ejecutar.setBounds(887, 144, 99, 23);
		ejecutar.setBackground(botones);
		ejecutar.setForeground(Color.WHITE);
        ejecutar.setFocusPainted(false); 
		
		JButton limpiar = new JButton("LIMPIAR");
		limpiar.setBounds(887, 115, 99, 23);
		limpiar.setBackground(botones);
		limpiar.setForeground(Color.WHITE);
        limpiar.setFocusPainted(false); 
        
		JButton cerrarSesion = new JButton("CERRAR SESI\u00D3N");
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
				{null, null},
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
		contentPane.add(scrollPane);
		contentPane.add(ejecutar);
		contentPane.add(limpiar);
		contentPane.add(cerrarSesion);
		contentPane.add(separator);
	}
	
	
}
