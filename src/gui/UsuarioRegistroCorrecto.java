package gui;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import comunicacion.IFachadaLogica;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UsuarioRegistroCorrecto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("unused")
	private IFachadaLogica fa;

	public UsuarioRegistroCorrecto(IFachadaLogica fa) {
		
		this.fa = fa;

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
		aceptar.setFont(new Font("Verdana", Font.BOLD, 18));
		aceptar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				dispose();
			
			}
			
		});
		aceptar.setForeground(fuentePrincipal);
		aceptar.setBackground(botones);
		aceptar.setBounds(135, 183, 168, 54);
		aceptar.setFocusPainted(false);
		contentPane.add(aceptar);
		
		JLabel lblNewLabel = new JLabel("<HTML> <CENTER> EL USUARIO SE HA REGISTRADO <BR> CORRECTAMENTE </CENTER> </HTML>");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 20));
		lblNewLabel.setBounds(32, 31, 392, 155);
		contentPane.add(lblNewLabel);
		
	}
	
}