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

public class UsuarioRegistroErrorNombre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public UsuarioRegistroErrorNombre() {
		
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
		aceptar.setFont(new Font("Verdana", Font.BOLD, 14));
		aceptar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				dispose();
			
			}
		
		});
		aceptar.setForeground(fuentePrincipal);
		aceptar.setBackground(botones);
		aceptar.setBounds(144, 202, 146, 36);
		aceptar.setFocusPainted(false);
		contentPane.add(aceptar);
		
		
		JLabel lblNewLabel = new JLabel("<HTML> <CENTER> ERROR </CENTER> <BR> <CENTER> EL USUARIO YA SE ENCUENTRA <BR>\r\n REGISTRADO </CENTER> </HTML>");
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblNewLabel.setBounds(49, 28, 385, 155);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("C\u00F3digo de error 6");
		lblNewLabel_1.setBounds(322, 236, 102, 14);
		contentPane.add(lblNewLabel_1);
	
	}
}