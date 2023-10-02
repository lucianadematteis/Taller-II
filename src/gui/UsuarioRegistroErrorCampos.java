package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class UsuarioRegistroErrorCampos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public UsuarioRegistroErrorCampos() {
		
		Color fondoPrincipal = new Color (66,141,138);
		Color fuentePrincipal = new Color (255,255,255);
		Color botones = new Color (3,60,88);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(fondoPrincipal);
		
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		aceptar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				dispose();
			
			}
			
		});
		contentPane.setLayout(null);
		
		aceptar.setForeground(fuentePrincipal);
		aceptar.setBackground(botones);
		aceptar.setBounds(167, 200, 100, 37);
		aceptar.setFocusPainted(false);
		contentPane.add(aceptar);
		
		JLabel lblNewLabel = new JLabel("Error. Debe completar todos los campos");
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel.setBounds(34, 34, 485, 155);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Codigo de error 10");
		lblNewLabel_1.setBounds(321, 233, 108, 23);
		contentPane.add(lblNewLabel_1);
		
	}

}