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
	private IFachadaLogica fa;
	
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
		saludo.setBounds(29, 25, 223, 23);
		saludo.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		bdActual = new JLabel("Base de datos: ");
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
				entrada.requestFocus();

			}
		});
		limpiar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		limpiar.setBounds(887, 146, 99, 28);
		limpiar.setBackground(botones);
		limpiar.setForeground(fuentePrincipal);
        limpiar.setFocusPainted(false); 
        
		JButton cerrarSesion = new JButton("CERRAR SESI\u00D3N");
		cerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 14));
		cerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login frame = new Login(fa);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				fa.persistirDatos();
				fa.liberarMemoriaBaseDatos();
				fa.liberarMemoriaUsuario();
				dispose();
			}
		});
		cerrarSesion.setBounds(843, 619, 143, 31);
		cerrarSesion.setBackground(botones);
		cerrarSesion.setForeground(fuentePrincipal);
        cerrarSesion.setFocusPainted(false); 
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(25, 71, 135, 524);
		scrollPane_3.getViewport().setBackground(fondoVentana);
		contentPane.add(scrollPane_3);
		
		guia = new JTable();
		guia.setFont(new Font("SansSerif", Font.PLAIN, 14));
		guia.setForeground(escritura);
		guia.setBackground(fondoVentana);
		guia.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		scrollPane_3.setViewportView(guia);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(170, 484, 816, 111);
		scrollPane_2.getViewport().setBackground(fondoVentana);
		contentPane.add(scrollPane_2);
		
		depuracion = new JTable();
		depuracion.setFont(new Font("SansSerif", Font.PLAIN, 14));
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
		scrollPane_1.setBounds(170, 185, 815, 288);
		scrollPane_1.getViewport().setBackground(fondoVentana);
		scrollPane_1.getVerticalScrollBar().setBackground(fondoVentana);
		scrollPane_1.getHorizontalScrollBar().setBackground(fondoVentana);
		contentPane.add(scrollPane_1);
		
		salida = new JTable();
		salida.setFont(new Font("SansSerif", Font.PLAIN, 14));
		salida.setForeground(escritura);
		salida.setBackground(fondoVentana);
		salida.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		salida.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				
			}
		));
		scrollPane_1.setViewportView(salida);
		
		JButton ejecutar = new JButton("EJECUTAR");
		ejecutar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		ejecutar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Comandos comandos = new Comandos(fa);
				
				ArrayList<String[]> arregloLinea = administraSentencia(entrada.getText());
		        
		        String comando=arregloLinea.get(0)[0].toUpperCase();
		        	
		        comandos.ejecutarComando(comando, arregloLinea);
		         
			} 
			
		});
		ejecutar.setBounds(887, 104, 99, 28);
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
		btnConfigUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnConfigUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BMUsuario frame = new BMUsuario(fa);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				dispose();	
			}
		});
		btnConfigUsuario.setBounds(24, 620, 248, 30);
		btnConfigUsuario.setFocusPainted(false);
		contentPane.add(btnConfigUsuario);
		
		cargarBasesDatos(fa.obtenerBasesNom());
		
	}
	
	public static void cargarBasesDatos(ArrayList<String> nBD) {
		
		DefaultTableModel model = (DefaultTableModel) guia.getModel();
		model.setRowCount(0);
    	model.setColumnCount(0);
		model.addColumn("BASES DE DATOS");
		
		for (int i = 0; i < nBD.size(); i++) {
		
			model.addRow(new Object [] {nBD.get(i)});
			
		}
		
	}
}
