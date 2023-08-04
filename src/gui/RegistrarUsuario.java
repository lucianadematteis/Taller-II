package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistrarUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField usuario;
	private JPasswordField pass;
	private JPasswordField pass2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarUsuario frame = new RegistrarUsuario();
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
	public RegistrarUsuario() {
		
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
		panel.setBounds(226, 180, 537, 344);
		contentPane.add(panel);
		panel.setLayout(null);
		
		usuario = new JTextField();
		usuario.setBounds(307, 82, 198, 20);
		panel.add(usuario);
		usuario.setColumns(10);
		
		pass = new JPasswordField();
		pass.setBounds(307, 145, 198, 20);
		panel.add(pass);
		pass.setColumns(10);
		
		pass2 = new JPasswordField();
		pass2.setBounds(307, 205, 198, 20);
		panel.add(pass2);
		pass2.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Ingrese documento de usuario");
		lblNewLabel.setForeground(fuentePrincipal);
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel.setBounds(21, 82, 287, 20);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nueva contrase\u00F1a");
		lblNewLabel_1.setForeground(fuentePrincipal);
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(128, 143, 225, 17);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Confirmar contrase\u00F1a");
		lblNewLabel_2.setForeground(fuentePrincipal);
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(98, 208, 199, 17);
		panel.add(lblNewLabel_2);
		
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.setForeground(fuentePrincipal);
		aceptar.setBackground(botones);
		aceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		aceptar.setFont(new Font("SansSerif", Font.PLAIN, 11));
		aceptar.setBounds(401, 285, 104, 23);
		aceptar.setFocusPainted(false);
		panel.add(aceptar);
		
		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("SansSerif", Font.PLAIN, 11));
		btnCancelar.setFocusPainted(false);
		btnCancelar.setBackground(new Color(3, 60, 88));
		btnCancelar.setBounds(274, 285, 104, 23);
		panel.add(btnCancelar);
		
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
