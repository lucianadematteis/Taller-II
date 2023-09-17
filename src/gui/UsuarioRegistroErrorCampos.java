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

public class UsuarioRegistroErrorCampos extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioRegistroErrorCampos frame = new UsuarioRegistroErrorCampos();
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
	public UsuarioRegistroErrorCampos() {
		
		Color fondoPrincipal = new Color (66,141,138);
		Color fondoVentana = new Color (187,218,219);
		Color fuentePrincipal = new Color (255,255,255);
		Color escritura = new Color (0,0,0);
		Color botones = new Color (3,60,88);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(fondoPrincipal);
		
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.setLayout(null);
		
		aceptar.setForeground(fuentePrincipal);
		aceptar.setBackground(botones);
		aceptar.setBounds(173, 215, 89, 23);
		aceptar.setFocusPainted(false);
		contentPane.add(aceptar);
		
		JLabel lblNewLabel = new JLabel("Error. Debe completar todos los campos");
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel.setBounds(34, 34, 485, 155);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("C\u00F3digo de error 10");
		lblNewLabel_1.setBounds(321, 233, 108, 23);
		contentPane.add(lblNewLabel_1);
	}

}
