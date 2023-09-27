package gui;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.LineBorder;
import comunicacion.DTOUsuario;
import comunicacion.IFachadaLogica;
import javax.swing.JSeparator;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField user;
	@SuppressWarnings("unused")
	private IFachadaLogica fa;
	static boolean demo;

	public Login(IFachadaLogica fa) {
		
		this.fa = fa;
		Color recuadro = new Color (3,90,88);
		Color fondoPrincipal = new Color (66,141,138);
		Color fondoVentana = new Color (187,218,219);
		Color fuentePrincipal = new Color (255,255,255);
		Color escritura = new Color (0,0,0);
		Color botones = new Color (3,60,88);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 700);
		contentPane = new JPanel();
		contentPane.setBackground(fondoPrincipal);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);;
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("MANEJADOR DE BASES DE DATOS");
		lblNewLabel_1.setForeground(fuentePrincipal);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 48));
		lblNewLabel_1.setBounds(10, 49, 988, 94);
		contentPane.add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 143, 966, 2);
		contentPane.add(separator);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBackground(recuadro);
		panel.setBounds(250, 228, 471, 323);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPasswordField pass_1 = new JPasswordField();
		pass_1.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 17));
		pass_1.setBounds(64, 196, 340, 26);
		panel.add(pass_1);
		pass_1.setForeground(escritura);
		pass_1.setBackground(fondoVentana);
		pass_1.setColumns(10);
		pass_1.setHorizontalAlignment(JTextField.CENTER);
		
		JButton inicioSesion = new JButton("INICIAR SESION");
		inicioSesion.setFont(new Font("Segoe UI", Font.BOLD, 14));
		inicioSesion.setForeground(fuentePrincipal);
		inicioSesion.setBackground(botones);
		inicioSesion.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
			
				char[] arrayC = pass_1.getPassword();
				String contra = new String (arrayC);
				DTOUsuario usuario = new DTOUsuario (user.getText(),contra);
			
				if (fa.existeUsuario(user.getText())){

					if (fa.validarContrasenia(usuario)){
						
						fa.seleccionarUsuario(user.getText());
						VentanaPrincipal frame = new VentanaPrincipal(fa);
						frame.setVisible(true);
				        frame.setLocationRelativeTo(null);
						dispose();	
						
					} else {
				
						LoginError frame = new LoginError();
						frame.setVisible(true);	
						frame.setLocationRelativeTo(null);
					
					}
				
				}else {
				
					LoginError frame = new LoginError();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				
				}
			
			}
		
		});
		inicioSesion.setBounds(252, 269, 152, 30);
		inicioSesion.setFocusPainted(false);
		panel.add(inicioSesion);
		
		JButton btnRegistrarse = new JButton("REGISTRARSE");
		btnRegistrarse.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnRegistrarse.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				RegistrarUsuario frame= new RegistrarUsuario(fa);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				dispose();
			
			}
	
		});
		btnRegistrarse.setForeground(Color.WHITE);
		btnRegistrarse.setFocusPainted(false);
		btnRegistrarse.setBackground(new Color(3, 60, 88));
		btnRegistrarse.setBounds(64, 269, 152, 30);
		panel.add(btnRegistrarse);
		
		user = new JTextField();
		user.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 17));
		user.setBounds(64, 92, 340, 26);
		panel.add(user);
		user.setForeground(escritura);
		user.setBackground(fondoVentana);
		user.setColumns(10);
		user.setHorizontalAlignment(JTextField.CENTER);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(153, 45, 160, 26);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(153, 148, 160, 26);
		panel.add(lblContrasea);
		lblContrasea.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasea.setForeground(Color.WHITE);
		
		JButton btnIniciarDemo = new JButton("INICIAR DEMO");
		btnIniciarDemo.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				DTOUsuario prueba = new DTOUsuario ("demo","demo");
				fa.seleccionarUsuario(prueba.getNombreUser());
				fa.seleccionarBaseDatos("base de prueba");
				demo = true;
				VentanaPrincipal frame = new VentanaPrincipal(fa);
				frame.setVisible(true);
		        frame.setLocationRelativeTo(null);
				dispose();	
				
			}
			
		});
		btnIniciarDemo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnIniciarDemo.setForeground(Color.WHITE);
		btnIniciarDemo.setFocusPainted(false);
		btnIniciarDemo.setBackground(new Color(3, 60, 88));
		btnIniciarDemo.setBounds(796, 568, 146, 47);
		contentPane.add(btnIniciarDemo);

		  if (fa.getDemoHabilitada() == 0 ) {
			   
		    	btnIniciarDemo.setEnabled(false);
		    	demo = false;
		    	
		    }
	   
	}
	
}
