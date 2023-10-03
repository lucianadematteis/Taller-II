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

import comunicacion.DTOUsuario;
import comunicacion.IFachadaLogica;

public class ConfirmarEliminarUsuario extends JFrame {

	private static final long serialVersionUID = 3227640129070275096L;
	private JPanel contentPane;
	@SuppressWarnings("unused")
	private IFachadaLogica fa;

	public ConfirmarEliminarUsuario(IFachadaLogica fa, DTOUsuario us) {
		
		this.fa =fa;
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

		JButton confirmar = new JButton("CONFIRMAR");
		confirmar.setFont(new Font("Verdana", Font.BOLD, 14));
		confirmar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				fa.eliminarusuario(us);
				fa.persistirDatos();
				fa.recuperarDatos();
				fa.liberarMemoriaBaseDatos();
				fa.liberarMemoriaUsuario();
				Login log = new Login(fa);
				log.setVisible(true);
				log.setLocationRelativeTo(null);
				dispose();
				
			}
			
		});
		confirmar.setForeground(fuentePrincipal);
		confirmar.setBackground(botones);
		confirmar.setBounds(55, 198, 144, 40);
		confirmar.setFocusPainted(false);
		contentPane.add(confirmar);


		JLabel lblNewLabel = new JLabel("<HTML> <CENTER> ¿CONFIRMA QUE DESEA <BR> ELIMINAR EL USUARIO <BR> DE MANERA PERMANENTE? <CENTER> </HTML>");
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 20));
		lblNewLabel.setBounds(64, 32, 414, 155);
		contentPane.add(lblNewLabel);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setFont(new Font("Verdana", Font.BOLD, 14));
		btnCancelar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				BMUsuario frame = new BMUsuario(fa);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				dispose();
			
			}

		});
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFocusPainted(false);
		btnCancelar.setBackground(new Color(3, 60, 88));
		btnCancelar.setBounds(242, 198, 144, 40);
		contentPane.add(btnCancelar);
		
	}

}
