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

import comunicacion.DTOUsuario;
import comunicacion.FachadaLogica;
import comunicacion.IFachadaLogica;

public class ConfirmarEliminarUsuario extends JFrame {

	private JPanel contentPane;
	private FachadaLogica fa;

	public ConfirmarEliminarUsuario(FachadaLogica fa, DTOUsuario us) {
		this.fa =fa;
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

		JButton confirmar = new JButton("CONFIRMAR");
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fa.eliminarusuario(us);
				fa.persistirDatos();
				fa.recuperarDatos();
				fa.liberarMemoriaBaseDatos();
				fa.liberarMemoriaUsuario();
				Login log = new Login(fa);
				log.setVisible(true);
				dispose();
			}
		});
		confirmar.setForeground(fuentePrincipal);
		confirmar.setBackground(botones);
		confirmar.setBounds(43, 203, 156, 35);
		confirmar.setFocusPainted(false);
		contentPane.add(confirmar);


		JLabel lblNewLabel = new JLabel("\u00BFConfirma que desea eliminar el usuario?");
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel.setBounds(38, 37, 485, 155);
		contentPane.add(lblNewLabel);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BMUsuario frame = new BMUsuario(fa);
				frame.setVisible(true);
				dispose();
			}
		});
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFocusPainted(false);
		btnCancelar.setBackground(new Color(3, 60, 88));
		btnCancelar.setBounds(242, 203, 144, 35);
		contentPane.add(btnCancelar);
	}

}
