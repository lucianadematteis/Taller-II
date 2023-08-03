package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JButton;

public class registrarUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField usuario;
	private JTextField pass;
	private JTextField pass2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					registrarUsuario frame = new registrarUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public registrarUsuario() {
		
		Color recuadro = new Color (3,90,88);
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
		contentPane.setLayout(null);
		

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBackground(recuadro);
		panel.setBounds(276, 180, 471, 344);
		contentPane.add(panel);
		panel.setLayout(null);
		
		usuario = new JTextField();
		usuario.setBounds(236, 95, 198, 20);
		panel.add(usuario);
		usuario.setColumns(10);
		
		pass = new JTextField();
		pass.setBounds(236, 158, 198, 20);
		panel.add(pass);
		pass.setColumns(10);
		
		pass2 = new JTextField();
		pass2.setBounds(236, 218, 198, 20);
		panel.add(pass2);
		pass2.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Ingrese un usuario");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel.setBounds(59, 91, 210, 20);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nueva contrase\u00F1a");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(57, 156, 225, 17);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Confirmar contrase\u00F1a");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(27, 221, 199, 17);
		panel.add(lblNewLabel_2);
		
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.setFont(new Font("SansSerif", Font.PLAIN, 11));
		aceptar.setBounds(345, 298, 89, 23);
		panel.add(aceptar);
		
		JLabel lblNewLabel_3 = new JLabel("REGISTRO DE USUARIO");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 36));
		lblNewLabel_3.setBounds(276, 11, 545, 99);
		contentPane.add(lblNewLabel_3);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 96, 988, 2);
		contentPane.add(separator_1);
	}
}
