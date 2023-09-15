package gui;

import java.awt.Color;
import java.awt.EventQueue;

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
import comunicacion.FachadaLogica;

import javax.swing.JSeparator;


public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField user;
	private JPasswordField pass;

	public static void main(String[] args) { //BORRAR LUEGO
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		
		Color recuadro = new Color (3,90,88);
		Color fondoPrincipal = new Color (66,141,138);
		Color fondoVentana = new Color (187,218,219);
		Color fuentePrincipal = new Color (255,255,255);
		Color escritura = new Color (0,0,0);
		Color botones = new Color (3,60,88);
		FachadaLogica fa = new FachadaLogica();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 700);
		contentPane = new JPanel();
		contentPane.setBackground(fondoPrincipal);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);;
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(417, 276, 160, 26);
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(414, 376, 160, 26);
		lblContrasea.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasea.setForeground(Color.WHITE);
		contentPane.setLayout(null);
		contentPane.add(lblNewLabel);
		contentPane.add(lblContrasea);
		
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
		pass_1.setBounds(50, 182, 365, 20);
		panel.add(pass_1);
		pass_1.setForeground(escritura);
		pass_1.setBackground(fondoVentana);
		pass_1.setColumns(10);
		
		
		JButton inicioSesion = new JButton("INICIAR SESION");
		inicioSesion.setForeground(fuentePrincipal);
		inicioSesion.setBackground(botones);
		inicioSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] arrayC = pass_1.getPassword();
				String contra = new String (arrayC);
				DTOUsuario usuario = new DTOUsuario (user.getText(),contra);
			
				if (fa.existeUsuario(user.getText())){

					if (fa.validarContrasenia(usuario)){
						VentanaPrincipal frame = new VentanaPrincipal();
						frame.setVisible(true);
						dispose();	
					} else {
							LoginError frame = new LoginError();
							frame.setVisible(true);	
							System.out.println("eror contra");
						}
					}
				else {
					LoginError frame = new LoginError();
					frame.setVisible(true);

					System.out.println("no existe user");
				}
			}
		});
		inicioSesion.setBounds(250, 275, 146, 23);
		inicioSesion.setFocusPainted(false);
		panel.add(inicioSesion);
		
		JButton btnRegistrarse = new JButton("REGISTRARSE");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarUsuario frame= new RegistrarUsuario();
				frame.setVisible(true);
				dispose();
			}
		});
		btnRegistrarse.setForeground(Color.WHITE);
		btnRegistrarse.setFocusPainted(false);
		btnRegistrarse.setBackground(new Color(3, 60, 88));
		btnRegistrarse.setBounds(64, 275, 146, 23);
		panel.add(btnRegistrarse);
		
		user = new JTextField();
		user.setBounds(50, 82, 365, 20);
		panel.add(user);
		user.setForeground(escritura);
		user.setBackground(fondoVentana);
		user.setColumns(10);
		
		JButton btnIniciarDemo = new JButton("INICIAR DEMO");
		btnIniciarDemo.setForeground(Color.WHITE);
		btnIniciarDemo.setFocusPainted(false);
		btnIniciarDemo.setBackground(new Color(3, 60, 88));
		btnIniciarDemo.setBounds(796, 568, 146, 47);
		contentPane.add(btnIniciarDemo);

		
	}
}
