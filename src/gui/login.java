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
import javax.swing.JSeparator;


public class login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField user;
	private JPasswordField pass;

	public static void main(String[] args) { //BORRAR LUEGO
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public login() {
		
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
		setContentPane(contentPane);
		
		user = new JTextField();
		user.setForeground(escritura);
		user.setBackground(fondoVentana);
		user.setBounds(311, 308, 365, 20);
		user.setColumns(10);
		
		JPasswordField pass = new JPasswordField();
		pass.setForeground(escritura);
		pass.setBounds(311, 408, 365, 20);
		pass.setBackground(fondoVentana);;
		pass.setColumns(10);
		
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
		contentPane.add(pass);
		contentPane.add(user);
		
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
		
		JButton inicioSesion = new JButton("INICIAR SESION");
		inicioSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		inicioSesion.setBounds(281, 275, 146, 23);
		panel.add(inicioSesion);
		
		
        
  
		
	}
}
