package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class BMUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField pass;
	private JTextField pass2;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BMUsuario frame = new BMUsuario();
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
	public BMUsuario() {
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
		panel.setBounds(35, 156, 456, 353);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Ingrese nueva contrase\u00F1a");
		lblNewLabel_1.setForeground(fuentePrincipal);
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(92, 102, 250, 39);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Repita la contrase\u00F1a");
		lblNewLabel_2.setForeground(fuentePrincipal);
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(92, 202, 205, 26);
		panel.add(lblNewLabel_2);
		
		JButton aceptar = new JButton("CONFIRMAR");
		aceptar.setForeground(fuentePrincipal);
		aceptar.setBackground(botones);
		aceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			/*
				if (*inserte aqu� si se pudo registrar*) {
					UsuarioRegistroCorrecto frame = new UsuarioRegistroCorrecto();
					frame.setVisible(true);
					dispose();
				}else if {
					UsuarioRegistroErrorNombre frame1 = new UsuarioRegistroErrorNombre();
					frame1.setVisible(true);
				}else{
				UsuarioRegisroErrorContrasenia frame2 = new UsuarioRegistroErrorContrasenia();
				frame2.setVisible (true);
				}
				*/
				
			}
		});
		aceptar.setFont(new Font("SansSerif", Font.PLAIN, 11));
		aceptar.setBounds(316, 302, 104, 23);
		aceptar.setFocusPainted(false);
		panel.add(aceptar);
		
		JLabel lblNewLabel = new JLabel("CAMBIAR CONTRASE\u00D1A");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(70, 11, 328, 55);
		panel.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(22, 77, 392, 2);
		panel.add(separator);
		
		pass = new JPasswordField();
		pass.setBounds(92, 152, 235, 20);
		panel.add(pass);
		pass.setColumns(10);
		
		pass2 = new JPasswordField();
		pass2.setBounds(92, 234, 229, 20);
		panel.add(pass2);
		pass2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("MODIFICACI\u00D3N DE USUARIO");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 36));
		lblNewLabel_3.setBounds(276, 11, 545, 99);
		contentPane.add(lblNewLabel_3);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 96, 988, 2);
		contentPane.add(separator_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setBackground(new Color(3, 90, 88));
		panel_1.setBounds(513, 156, 456, 353);
		contentPane.add(panel_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("Ingrese su contrase\u00F1a actual");
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_2_1.setBounds(102, 201, 266, 26);
		panel_1.add(lblNewLabel_2_1);
		
		JButton aceptar_1 = new JButton("ELIMINAR CUENTA");
		aceptar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//si usuario y contrase�a correctos entonces:
				ConfirmarEliminarUsuario frame = new ConfirmarEliminarUsuario();
				frame.setVisible(true);
				
			}
		});
		aceptar_1.setForeground(Color.WHITE);
		aceptar_1.setFont(new Font("SansSerif", Font.PLAIN, 11));
		aceptar_1.setFocusPainted(false);
		aceptar_1.setBackground(new Color(3, 60, 88));
		aceptar_1.setBounds(275, 302, 145, 23);
		panel_1.add(aceptar_1);
		
		JLabel lblEliminarUsuario = new JLabel("ELIMINAR USUARIO");
		lblEliminarUsuario.setForeground(Color.WHITE);
		lblEliminarUsuario.setFont(new Font("SansSerif", Font.BOLD, 24));
		lblEliminarUsuario.setBounds(102, 11, 328, 55);
		panel_1.add(lblEliminarUsuario);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(22, 77, 392, 2);
		panel_1.add(separator_2);
		
		textField_1 = new JPasswordField();
		textField_1.setColumns(10);
		textField_1.setBounds(111, 238, 229, 20);
		panel_1.add(textField_1);
		
		JTextArea txtrAtencinElSiguiente = new JTextArea();
		txtrAtencinElSiguiente.setForeground(Color.YELLOW);
		txtrAtencinElSiguiente.setFont(new Font("SansSerif", Font.BOLD, 13));
		txtrAtencinElSiguiente.setBackground(recuadro);
		txtrAtencinElSiguiente.setText("ATENCI\u00D3N: El siguiente cambio dar\u00E1 de baja el usuario pero\r\nno realizar\u00E1 la eliminaci\u00F3n total del mismo. La cuenta podr\u00E1 \r\nser reactivada registr\u00E1ndose nuevamente.");
		txtrAtencinElSiguiente.setBounds(22, 99, 398, 92);
		panel_1.add(txtrAtencinElSiguiente);
		
		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("SansSerif", Font.PLAIN, 11));
		btnCancelar.setFocusPainted(false);
		btnCancelar.setBackground(new Color(3, 60, 88));
		btnCancelar.setBounds(865, 579, 104, 23);
		contentPane.add(btnCancelar);
		
		
	}
}
