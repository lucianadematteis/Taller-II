package gui;

import java.awt.Color;
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
import comunicacion.IFachadaLogica;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JTable guia;
	static JTable salida;
	static JTable depuracion;
	static JLabel bdActual;
	@SuppressWarnings("unused")
	private IFachadaLogica fa;
	static JTable guiaTabla;
	
	/**
	 * Metodo publico que se utiliza para procesar una sentencia y generar un arreglo de arreglos.
	 * Cada arreglo secundario contiene las palabras de una linea de la sentencia, donde las palabras
	 * pueden estar entre comillas o separadas por espacios. Elimina las comillas de las palabras entre comillas.
	 *
	 * @param sentencia -> La sentencia a procesar.
	 * @return Un ArrayList de arreglos de cadenas, donde cada arreglo contiene las palabras de una linea de la sentencia.
	 */
	public ArrayList<String[]> administraSentencia(String sentencia) {

		// Se pretende generar un arreglo de arreglos. Donde en cada posici�n del
		// arreglo principal
		// se almacene una linea de la sentencia, de forma tal que cada palabra
		// corresponda a una posicion de los arreglos secundarios

		// Separa el contenido en lineas
		String[] lineas = sentencia.split("\n");

		// Crea un arreglo para cada linea
		ArrayList<String[]> arregloLinea = new ArrayList<>();

		// Recorre cada linea
		for (String unaLinea : lineas) {

			ArrayList<String> palabras = new ArrayList<>();

			Matcher matcher = Pattern.compile("\"([^\"]*)\"|\\S+").matcher(unaLinea);

			// Encuentra las palabras entre comillas o las palabras separadas por espacios
			while (matcher.find()) {

				String palabra = matcher.group();

				// Si la palabra tiene comillas, se eliminan las comillas
				if (palabra.startsWith("\"") && palabra.endsWith("\"")) {

					palabra = palabra.substring(1, palabra.length() - 1);

				}

				palabras.add(palabra);

			}

			arregloLinea.add(palabras.toArray(new String[0]));
		}

		return arregloLinea;

	}

	public VentanaPrincipal(IFachadaLogica fa) {
		
		this.fa =fa;
		
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
		
		JLabel saludo = new JLabel("Bienvenido " + fa.getUsuario());
		saludo.setForeground(fuentePrincipal);
		saludo.setBounds(29, 25, 447, 23);
		saludo.setFont(new Font("Verdana", Font.BOLD, 20));
		
		bdActual = new JLabel("Base de datos: ");
		bdActual.setForeground(fuentePrincipal);
		bdActual.setBounds(564, 26, 429, 23);
		bdActual.setFont(new Font("Verdana", Font.PLAIN, 20));
		
		JSeparator separator = new JSeparator();
		separator.setBounds(15, 55, 978, 10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(170, 71, 691, 103);
		
		JTextArea entrada = new JTextArea();
		entrada.setForeground(escritura);
		entrada.setBackground(fondoVentana);
		entrada.setFont(new Font("Verdana", Font.PLAIN, 17));
		entrada.setToolTipText("Inserte sentencias aqu\u00ED");
		scrollPane.setViewportView(entrada);
		contentPane.setLayout(null);
		contentPane.add(saludo);
		contentPane.add(bdActual);
		
		JButton limpiar = new JButton("LIMPIAR");
		limpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				entrada.setText("");
				entrada.requestFocus();

			}
		});
		limpiar.setFont(new Font("Verdana", Font.BOLD, 13));
		limpiar.setBounds(871, 146, 115, 28);
		limpiar.setBackground(botones);
		limpiar.setForeground(fuentePrincipal);
        limpiar.setFocusPainted(false); 
        
		JButton cerrarSesion = new JButton("CERRAR SESI\u00D3N");
		cerrarSesion.setFont(new Font("Verdana", Font.BOLD, 14));
		cerrarSesion.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				Login frame = new Login(fa);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				fa.persistirDatos();
				fa.liberarMemoriaBaseDatos();
				fa.liberarMemoriaUsuario();
				Login.demo = false;
				dispose();
			
			}
			
		});
		cerrarSesion.setBounds(801, 619, 185, 31);
		cerrarSesion.setBackground(botones);
		cerrarSesion.setForeground(fuentePrincipal);
        cerrarSesion.setFocusPainted(false); 
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(25, 71, 135, 246);
		scrollPane_3.getViewport().setBackground(fondoVentana);
		contentPane.add(scrollPane_3);
		
		guia = new JTable();
		guia.setCellSelectionEnabled(false);
		guia.setFont(new Font("Verdana", Font.PLAIN, 15));
		guia.setForeground(escritura);
		guia.setBackground(fondoVentana);
		guia.setRowHeight(22);
		guia.setModel(new DefaultTableModel(
		  
		  new Object[][] {

		  },

		  new String[] {

		  }
		    
		) {
		  /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int rowIndex, int columnIndex) {
		    return false; // Ninguna celda es editable
		  }
		});
		scrollPane_3.setViewportView(guia);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(170, 484, 816, 111);
		scrollPane_2.getViewport().setBackground(fondoVentana);
		contentPane.add(scrollPane_2);
		
		depuracion = new JTable();
		depuracion.setFont(new Font("Verdana", Font.PLAIN, 14));
		depuracion.setForeground(escritura);
		depuracion.setBackground(fondoVentana);
		depuracion.setRowHeight(22);
		depuracion.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		depuracion.setModel(new DefaultTableModel(
		  
		  new Object[][] {
		    
		  },
		    
		  new String[] {
		    
		    "<html><b>NUMERO</b></html>", "<html><b>MENSAJE</b></html>"
		  
		  }
		  
		) {
		  /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int rowIndex, int columnIndex) {
		    return false; // Ninguna celda es editable
		  }
		});
		depuracion.setCellSelectionEnabled(false); // Desactiva la selección de celdas
		depuracion.getColumnModel().getColumn(0).setPreferredWidth(89);
		depuracion.getColumnModel().getColumn(1).setPreferredWidth(723);
		scrollPane_2.setViewportView(depuracion);

		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(170, 185, 815, 288);
		scrollPane_1.getViewport().setBackground(fondoVentana);
		scrollPane_1.getVerticalScrollBar().setBackground(fondoVentana);
		scrollPane_1.getHorizontalScrollBar().setBackground(fondoVentana);
		contentPane.add(scrollPane_1);
		
		salida = new JTable();
		salida.setFont(new Font("Verdana", Font.PLAIN, 17));
		salida.setForeground(escritura);
		salida.setBackground(fondoVentana);
		salida.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		salida.setModel(new DefaultTableModel(
		  
		  new Object[][] {
		    
		  },
		  
		  new String[] {
		    
		  
		  }
		    
		) {
		  /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int rowIndex, int columnIndex) {
		    return false; // Ninguna celda es editable
		  }
		});
		salida.setCellSelectionEnabled(false); // Desactiva la selección de celdas
		scrollPane_1.setViewportView(salida);
		salida.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JButton ejecutar = new JButton("EJECUTAR");
		ejecutar.setFont(new Font("Verdana", Font.BOLD, 12));
		ejecutar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Comandos comandos = new Comandos(fa);
				ArrayList<String[]> arregloLinea = administraSentencia(entrada.getText());
				
				if(arregloLinea.get(0).length != 0) {
				
			        String comando=arregloLinea.get(0)[0].toUpperCase();
			        comandos.ejecutarComando(comando, arregloLinea);
			        fa.setDemoHabilitada(Comandos.usos);
			        
			        if (Login.demo && Comandos.usos == 4 ){
			        	
			        	fa.liberarMemoriaBaseDatos();
			        	fa.liberarMemoriaUsuario();
			        	LimiteAlcanzado li = new LimiteAlcanzado(fa);
			        	li.setVisible(true);
			        	dispose();
			        	
			        }
			        
			        if(fa.bdSeleccionada()) {
					
			        	cargarTablas(fa.obtenerTablasNom());
					
			        }
			        
			        fa.persistirDatos();
		        
				}else {
					
					comandos.ejecutarComando(null, arregloLinea);
					
				}
		        
			} 
			
		});
		ejecutar.setBounds(871, 104, 115, 28);
		ejecutar.setBackground(botones);
		ejecutar.setForeground(fuentePrincipal);
        ejecutar.setFocusPainted(false); 
		
		contentPane.add(scrollPane);
		contentPane.add(ejecutar);
		contentPane.add(limpiar);
		contentPane.add(cerrarSesion);
		contentPane.add(separator);
		
		JButton btnConfigUsuario = new JButton("CONFIGURACI\u00D3N DE USUARIO");
		btnConfigUsuario.setBackground(botones);
		btnConfigUsuario.setForeground(fuentePrincipal);
		btnConfigUsuario.setFont(new Font("Verdana", Font.BOLD, 14));
		btnConfigUsuario.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
			
				BMUsuario frame = new BMUsuario(fa);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				dispose();	
			
			}
			
		});
		btnConfigUsuario.setBounds(24, 620, 284, 30);
		btnConfigUsuario.setFocusPainted(false);
		contentPane.add(btnConfigUsuario);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.getViewport().setBackground(fondoVentana);
		scrollPane_4.setBackground(fondoVentana);
		scrollPane_4.setBounds(27, 329, 133, 266);
		contentPane.add(scrollPane_4);
		
		guiaTabla = new JTable();
		scrollPane_4.setViewportView(guiaTabla);
		guiaTabla.setForeground(escritura);
		guiaTabla.setFont(new Font("Verdana", Font.PLAIN, 15));
		guiaTabla.setBackground(fondoVentana);
		guiaTabla.setRowHeight(22);
		guiaTabla.setModel(new DefaultTableModel(
		  
		  new Object[][] {
		    
		  },
		    
		  new String[] {
		    
		  }
		  
		) {
		  /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int rowIndex, int columnIndex) {
		    return false; // Ninguna celda es editable
		  }
		});
		guiaTabla.setCellSelectionEnabled(false); // Desactiva la selección de celdas

		cargarBasesDatos(fa.obtenerBasesNom());

		if(Login.demo) {

		  VentanaPrincipal.bdActual.setText("Base de datos: base de prueba");   
		  btnConfigUsuario.setEnabled(false);
		  cargarTablas(fa.obtenerTablasNom());

		}
	}

	/**
	 * Carga las bases de datos en una tabla GUI.
	 *
	 * @param nBD -> ArrayList que contiene los nombres de las bases de datos.
	 */
	public static void cargarBasesDatos(ArrayList<String> nBD) {
		
		DefaultTableModel model = (DefaultTableModel) guia.getModel();
		model.setRowCount(0);
    	model.setColumnCount(0);
    	model.addColumn("<html><b>BASES DE DATOS</b></html>");
		
		for (int i = 0; i < nBD.size(); i++) {
		
			model.addRow(new Object [] {nBD.get(i)});
			
		}
		
	}

	/**
	 * Carga las tablas en una tabla GUI.
	 *
	 * @param nTablas -> ArrayList que contiene los nombres de las tablas.
	 */
	public static void cargarTablas(ArrayList<String> nTablas) {

		DefaultTableModel model = (DefaultTableModel) guiaTabla.getModel();
		model.setRowCount(0);
		model.setColumnCount(0);
		model.addColumn("<html><b>TABLAS</b></html>");
		
		if(nTablas != null) {

			for (int i = 0; i < nTablas.size(); i++) {
				
				model.addRow(new Object [] {nTablas.get(i)});
	
			}

		}
		
	}
	
}