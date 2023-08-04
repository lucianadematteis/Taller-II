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
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;

public class UsuarioRegistroErrorContrasenia extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioRegistroErrorContrasenia frame = new UsuarioRegistroErrorContrasenia();
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
	public UsuarioRegistroErrorContrasenia() {
		setType(Type.POPUP);
		
		Color fondoPrincipal = new Color (66,141,138);
		Color fondoVentana = new Color (187,218,219);
		Color fuentePrincipal = new Color (255,255,255);
		Color escritura = new Color (0,0,0);
		Color botones = new Color (3,60,88);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(fondoPrincipal);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		aceptar.setForeground(fuentePrincipal);
		aceptar.setBackground(botones);
		aceptar.setBounds(173, 215, 89, 23);
		aceptar.setFocusPainted(false);
		contentPane.add(aceptar);
		
		JLabel lblNewLabel = new JLabel("Error. Las contraseñas no coinciden");
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel.setBounds(59, 36, 485, 155);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("C\u00F3digo de error 7");
		lblNewLabel_1.setBounds(322, 236, 102, 14);
		contentPane.add(lblNewLabel_1);
	}

}
