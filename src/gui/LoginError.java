package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class LoginError extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public LoginError() {
		
		Color fondoPrincipal = new Color (66,141,138);
		Color fuentePrincipal = new Color (255,255,255);
		Color botones = new Color (3,60,88);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(fondoPrincipal);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		aceptar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				dispose();
			
			}
		});
		aceptar.setForeground(fuentePrincipal);
		aceptar.setBackground(botones);
		aceptar.setBounds(164, 202, 114, 36);
		aceptar.setFocusPainted(false);
		contentPane.add(aceptar);
		
		JLabel lblNewLabel = new JLabel("Usuario o contrase\u00F1a invalidos");
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel.setBounds(75, 36, 349, 155);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Codigo de error 8");
		lblNewLabel_1.setBounds(322, 236, 102, 14);
		contentPane.add(lblNewLabel_1);
		
	}

}