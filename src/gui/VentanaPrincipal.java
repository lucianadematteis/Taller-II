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
		
		Color fondoPrincipal = new Color (30,30,30);
		Color fondoVentana = new Color (45,45,45);
		Color fuentePrincipal = new Color (255,255,255);
		Color escritura = new Color (200,200,200 );
		Color botones = new Color (74,74,74);
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
		scrollPane.setViewportView(entrada);
		contentPane.setLayout(null);
		contentPane.add(saludo);
		contentPane.add(bdActual);
		
		JButton limpiar = new JButton("LIMPIAR");
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
				
				int errores=0;
				int aciertos=0;
				
				DefaultTableModel model = (DefaultTableModel) depuracion.getModel();
				
				ArrayList<String[]> arregloLinea = logica.administraSentencia(entrada.getText());
		        
		        String comando1=arregloLinea.get(0)[0].toUpperCase();
		        
		        if(!(logica.comandosNivel1.contains(comando1))) { //Si el primer comando ingresado no es valido
		        	
		        	Object[] nuevaFila = {"Error #" + errores, "El comando " + comando1 + " no es valido"};
		        	model.addRow(nuevaFila);
		        	
		        }else {
		        
			        switch (comando1) { 
			        
			            case "CREATE":
			            	
			            	if(!(arregloLinea.size()>1)){
			
					        	Object[] nuevaFila = {"Error #" + errores, "La cantidad de lineas ingresada es incorrecta"};
					        	model.addRow(nuevaFila);
					        	
			        		}else {
			        			
				            	if (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 1))) { //no tiene mas nada alado del create
				            		
						        	Object[] nuevaFila = {"Error #" + errores, "Demasiados argumentos en linea 1"};
						        	model.addRow(nuevaFila);
						        	
				            	}else {
			            			
				            		String comando2=arregloLinea.get(1)[0].toUpperCase();
				            		
				            		switch (comando2) {
				            		
				            			case "TABLE":
				            				
				            				if(!(logica.validaCantidadLineas(arregloLinea, 3, 5))) {
				            					
									        	Object[] nuevaFila = {"Error #" + errores, "La cantidad de lineas ingresada es incorrecta, recuerde que se permiten de uno a tres atributos por tabla"};
									        	model.addRow(nuevaFila);
				            					
				            				}else {
				            					
			            					    if(!(logica.validaCantidadArgumentos(arregloLinea, 1, arregloLinea.size(), 2))) {
			            							
			            							Object[] nuevaFila = {"Error #" + errores, "Cantidad de argumentos incorrecta entre las lineas 2 y " + arregloLinea.size()};
										        	model.addRow(nuevaFila);
										        	
			            						}else {
			            							
			            							if(!(logica.validaTiposAtributos(arregloLinea, 2, arregloLinea.size()))) {
			            								
			            								Object[] nuevaFila = {"Error #" + errores, "Tipos de datos incorrectos, recuerde que solo se admiten datos de tipo entero o cadena"};
											        	model.addRow(nuevaFila);
			            								
			            							}else {
			            								
			            								aciertos++;
			            								Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere crear una tabla llamada: " +  arregloLinea.get(1)[1]};
											        	model.addRow(nuevaFila);
											        	
			            							}
			            						}		
				            				}
				            			
				            				break;
				            				
				            			case "DATABASE":
				            				
				            				if(!(logica.validaCantidadLineas(arregloLinea, 2, 2))) {
				            					
				            					Object[] nuevaFila = {"Error #" + errores, "La cantidad de lineas ingresada es incorrecta"};
									        	model.addRow(nuevaFila);
									        	
				            				}else {
				            				
					            				if(!(logica.validaCantidadArgumentos(arregloLinea, 1, 1, 2))) {
					            					
					            					Object[] nuevaFila = {"Error #" + errores, "Cantidad de argumentos incorrecta en la linea 2"};
										        	model.addRow(nuevaFila);
										        	
					            				}else {
					            					
					            					aciertos++;
					            					Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere crear una base de datos llamada: " + arregloLinea.get(1)[1]};
										        	model.addRow(nuevaFila);
					            					
					            				}
					            				
				            				}
				            				
				            				break;
				            				
				            			default:
				            				
				            				Object[] nuevaFila = {"Error #" + errores, "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido"};
								        	model.addRow(nuevaFila);
								        	
				            				break;
					            			
				            		}
				            	}
			        		}
			            	
			            	break;
			            	
			            case "INSERT":
			            	
			            	if (!(logica.validaCantidadLineas(arregloLinea, 2, 2))){
		            			
			            		Object[] nuevaFila = {"Error #" + errores, "Cantidad de lineas no valida"};
					        	model.addRow(nuevaFila);
			            		
			            	}else {
			            		
			            		if ((!(arregloLinea.get(1).length>1)) || (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 2)))) {
				            		
				            		Object[] nuevaFila = {"Error #" + errores, "Cantidad de argumentos no valida"};
						        	model.addRow(nuevaFila);
						        	
				            	}else { 
				            		
				            		if(!(arregloLinea.get(1)[0].toUpperCase().equals("VALUES"))) {
					            			
					            		Object[] nuevaFila = {"Error #" + errores, "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido"};
							        	model.addRow(nuevaFila);
							        	
					            	}else {
					            			
					            		aciertos++;
					            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere insertar datos en la tabla: " + arregloLinea.get(0)[1]};
							        	model.addRow(nuevaFila);
							        	
					            	}
				            	}
			            	}
			            	
			                break;
			                
			            case "SHOW":
			            	
			            	if(logica.validaSentenciasUnaLinea(arregloLinea)){
			            	
			            		if(!(arregloLinea.get(0)[1].toUpperCase().equals("TABLES"))) {
					            			
					            	Object[] nuevaFila = {"Error #" + errores, "El comando: " + arregloLinea.get(0)[1].toUpperCase() + " no es valido"};
						        	model.addRow(nuevaFila);
						        	
					            }else {
					            			
					            	aciertos++;
				            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere ver las tablas"};
						        	model.addRow(nuevaFila);		
					            }
				            	
			            	}
			            	
			                break; 
			                
			            case "USE":
			            	
			            	if(logica.validaSentenciasUnaLinea(arregloLinea)){
				            	
			            		aciertos++;
			            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere seleccionar la base de datos: " + arregloLinea.get(0)[1]};
					        	model.addRow(nuevaFila);
			            	}
			            	
			                break; 

			            case "HELP":
			            	
			            	if(logica.validaSentenciasUnaLinea(arregloLinea)){
				            	
			            		aciertos++;
			            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere obtener ayuda sobre el comando: " + arregloLinea.get(0)[1]};
					        	model.addRow(nuevaFila);
			            	}
			            	
			                break;
			                
			            case "DESCRIBE":
			            	
			            	if(logica.validaSentenciasUnaLinea(arregloLinea)){
				            	
			            		aciertos++;
			            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere obtener una descripcion sobre la tabla: " + arregloLinea.get(0)[1]};
					        	model.addRow(nuevaFila);
			            	}
			            	
			                break;
			                
			            case "SELECT":
			            	
			            	if(!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 2))) {
			            		
			            		Object[] nuevaFila = {"Error #" + errores, "Cantidad de argumentos incorrecta en linea 1"};
					        	model.addRow(nuevaFila);
					        	
			            	}else {
			            		
			            		if(!(logica.validaCantidadLineas(arregloLinea, 3, 3))) {
			            			
			            			Object[] nuevaFila = {"Error #" + errores, "Cantidad de lineas incorrecta"};
						        	model.addRow(nuevaFila);
						        	
			            		}else {
			            		
			            			if(logica.validaSentenciasFrom(arregloLinea)) {
			            			
					            		if(arregloLinea.get(2).length==4) { //SI ES WHERE COMUN
					            	
							            	if(logica.validaSentenciasWhereComun(arregloLinea)) {
							            		
							            		aciertos++;
							            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere realizar una consulta en la tabla: " + arregloLinea.get(1)[1] + " donde el atributo: " + arregloLinea.get(2)[1] + " vale " + arregloLinea.get(2)[3]};
									        	model.addRow(nuevaFila);
							            	}	
							            	
							            }else {
							            		
						            		if(logica.validaOperadoresLogicos(arregloLinea)) {
						            			
							            		String operador=arregloLinea.get(2)[4].toUpperCase();
							            		
						            			switch (operador) {
						            			
													case "AND": 
														
														aciertos++;
									            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere realizar una consulta en la tabla: " + arregloLinea.get(1)[1] + 
																" \n donde el atributo: " + arregloLinea.get(2)[1] + " valga " + arregloLinea.get(2)[3] +
																" \n y el atributo: " + arregloLinea.get(2)[5] + " valga " + arregloLinea.get(2)[7]};
											        	model.addRow(nuevaFila);
											        	
														break;	
													
													case "OR": 
														
														aciertos++;
									            		Object[] nuevaFila1 = {"Acierto #" + aciertos, "El usuario quiere realizar una consulta en la tabla: " + arregloLinea.get(1)[1] + 
																" \n donde el atributo: " + arregloLinea.get(2)[1] + " valga " + arregloLinea.get(2)[3] +
																" \n 0 el atributo: " + arregloLinea.get(2)[5] + " valga " + arregloLinea.get(2)[7]};
											        	model.addRow(nuevaFila1);
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
			            		
			            		Object[] nuevaFila = {"Error #" + errores, "La cantidad de lineas ingresada es incorrecta"};
					        	model.addRow(nuevaFila);
					        	
			            	}else {
			            	
				            	if (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 2))) { 
				            		
				            		Object[] nuevaFila = {"Error #" + errores, "Cantidad de argumentos incorrecta en linea 1"};
						        	model.addRow(nuevaFila);
						        	
				            	}else {
				            	
					            	if(!(logica.validaCantidadArgumentos(arregloLinea, 1, arregloLinea.size(), 4))) {
					            		
					            		Object[] nuevaFila = {"Error #" + errores, "Cantidad de argumentos incorrecta entre las lineas 2 y 3"};
							        	model.addRow(nuevaFila);
							        	
					            	}else {
					            		
					            		if(!(arregloLinea.get(1)[0].toUpperCase().equals("SET"))) {
					            			
					            			Object[] nuevaFila = {"Error #" + errores, "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido"};
								        	model.addRow(nuevaFila);
								        	
					            		}else {
					            			
						            		if(logica.validaSentenciasWhereComun(arregloLinea)) {
						            			
						            			if(!(arregloLinea.get(1)[2].equals("="))){
						            				
						            				Object[] nuevaFila = {"Error #" + errores, "El operador: " + arregloLinea.get(1)[2] + " no es valido"};
										        	model.addRow(nuevaFila);
										        	
						            			}else {
						            		
						            				aciertos++;
								            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere actualizar el dato identificado por: " + arregloLinea.get(2)[1] + " = " + arregloLinea.get(2)[3] + " de la tabla: " + arregloLinea.get(0)[1] + " \n que actualmente es: " + arregloLinea.get(1)[1] + " por " + arregloLinea.get(1)[3]};
										        	model.addRow(nuevaFila);
						            			}
						            		}
					            		}
					            	}
				            	}
			            	}
			            	
			            	break;
			                
			            case "DELETE":
			            	
			            	if (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 1))) { 
			            		
			            		Object[] nuevaFila = {"Error #" + errores, "Demasiados argumentos en linea 1"};
					        	model.addRow(nuevaFila);
					        	
			            	}else {
			            	
				            	if(logica.validaSentenciasFromWhere(arregloLinea)) {
				            		
				            		aciertos++;
				            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere borrar el dato de la tabla: " + arregloLinea.get(1)[1] + " que cumple la condicion que el atributo " + arregloLinea.get(2)[1] + " es igual a: " + arregloLinea.get(2)[3]};
						        	model.addRow(nuevaFila);
				            	}
			            	}
			            	
			            	break;
			            	
			            case "JOINNATURAL":
			            	
			            	if (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 1))) { 
			            		
			            		Object[] nuevaFila = {"Error #" + errores, "Demasiados argumentos en linea 1"};
					        	model.addRow(nuevaFila);
					        	
			            	}else {
			            		
			            		if(!(logica.validaCantidadLineas(arregloLinea, 2, 2))) {
			            			
			            			Object[] nuevaFila = {"Error #" + errores, "Cantidad de lineas no valida"};
						        	model.addRow(nuevaFila);
						        	
			            		}else {
			            			
			            			if(!(arregloLinea.get(1)[0].toUpperCase().equals("FROM"))) {
			            				
			            				Object[] nuevaFila = {"Error #" + errores, "El comando: " + arregloLinea.get(1)[0].toUpperCase() + " no es valido"};
							        	model.addRow(nuevaFila);
							        	
			            			}else {
			            			
				            			if(!(logica.validaCantidadArgumentos(arregloLinea, 1, arregloLinea.size(), 3))) {
				            				
				            				Object[] nuevaFila = {"Error #" + errores, "Cantidad de argumentos no valida en linea 2, recuerde que el join natural se realiza entre dos tablas"};
								        	model.addRow(nuevaFila);
								        	
				            			}else {
				            				
				            				aciertos++;
						            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere hacer un join natural entre las tablas: " + arregloLinea.get(1)[1] + " y " + arregloLinea.get(1)[2]};
								        	model.addRow(nuevaFila);
				            			}
			            			}
			            		}
			            	}
			            	
			            	break;
			                
			            case "NOTNULL":
			            	
			            	if(logica.validaSentenciasDosLineas(arregloLinea)) {
			            		
			            		aciertos++;
			            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere hacer no nulo el atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1]};
					        	model.addRow(nuevaFila);
			            	}
			            	
			            	break;
			              
			            case "COUNT":
			            	
			            	if (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 2))) { 
			            		
			            		Object[] nuevaFila = {"Error #" + errores, "Cantidad de argumentos incorrecta en linea 1"};
					        	model.addRow(nuevaFila);
					        	
			            	}else {
			            	
				            	if(logica.validaSentenciasFromWhere(arregloLinea)) {
				            		
				            		aciertos++;
				            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere contar la cantidad de datos de la tabla: " + arregloLinea.get(1)[1] + " que cumple la condicion que el atributo " + arregloLinea.get(2)[1] + " es igual a: " + arregloLinea.get(2)[3]};
						        	model.addRow(nuevaFila);
				            	}
				            	
			            	}
			            	
			            	break;
				              
			           case "PRIMARYKEY":
			        	   
			        	   if(logica.validaSentenciasDosLineas(arregloLinea)) {
			            		
			            		aciertos++;
			            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere hacer clave primaria el atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1]};
					        	model.addRow(nuevaFila);
			            	}
			            	
			            	break;
				          
			            case "MIN":
			            	
			            	if(logica.validaSentenciasDosLineas(arregloLinea)) {
				            		
				            	aciertos++;
			            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere obtener el valor minimo del atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1]};
					        	model.addRow(nuevaFila);
				            }
			            	
			            	break;
			                
			            case "MAX":
			            	
			            	if(logica.validaSentenciasDosLineas(arregloLinea)) {
			            		
				            	aciertos++;
			            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere obtener el valor maximo del atributo: " + arregloLinea.get(0)[1] + " de la tabla: " + arregloLinea.get(1)[1]};
					        	model.addRow(nuevaFila);   
				            }
			            	
			            	break;
			            	
			            case "AVG":
			            	
			            	if (!(logica.validaCantidadArgumentos(arregloLinea, 0, 0, 2))) { 
			            		
			            		Object[] nuevaFila = {"Error #" + errores, "Cantidad de argumentos incorrecta en linea 1"};
					        	model.addRow(nuevaFila);
					        	
			            	}else {
			            	
				            	if(logica.validaSentenciasFromWhere(arregloLinea)) {
				            		
				            		aciertos++;
				            		Object[] nuevaFila = {"Acierto #" + aciertos, "El usuario quiere obtener el promedio de datos de la tabla: " + arregloLinea.get(1)[1] + " que cumple la condicion que el atributo " + arregloLinea.get(2)[1] + " es igual a: " + arregloLinea.get(2)[3]};
						        	model.addRow(nuevaFila); 
				            	}
			            	}
			            	
			            	break;
			           
			            default:
            				
            				Object[] nuevaFila = {"Error #" + errores, "El comando: " + arregloLinea.get(0)[0].toUpperCase() + " no es valido"};
				        	model.addRow(nuevaFila);
				        	
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
