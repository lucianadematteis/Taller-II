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

import comunicacion.IFachadaLogica;

public class LimiteAlcanzado extends JFrame {

	private static final long serialVersionUID = -7301989674983369037L;
	private JPanel contentPane;
	@SuppressWarnings("unused")
	private IFachadaLogica fa;

	public LimiteAlcanzado(IFachadaLogica fa) {
		
		this.fa =fa;
		setType(Type.POPUP);
		
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
			
				Login frame = new Login(fa);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				
				dispose();
			
			}
		
		});
		aceptar.setForeground(fuentePrincipal);
		aceptar.setBackground(botones);
		aceptar.setBounds(166, 202, 110, 37);
		aceptar.setFocusPainted(false);
		contentPane.add(aceptar);
		
		JLabel lblNewLabel = new JLabel("<html> <CENTER> SE HA ALCANZADO EL LÍMITE DE USOS <br> <BR> Adquiera un usuario para continuar </CENTER> </html>");
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 20));
		lblNewLabel.setBounds(49, 33, 344, 155);
		contentPane.add(lblNewLabel);
	
	}

}
