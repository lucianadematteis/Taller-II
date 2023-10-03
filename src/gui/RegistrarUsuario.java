package gui;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import comunicacion.DTOUsuario;
import comunicacion.IFachadaLogica;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistrarUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usuario;
	private JPasswordField pass;
	private JPasswordField pass2;
	@SuppressWarnings("unused")
	private IFachadaLogica fa;

	public RegistrarUsuario(IFachadaLogica fa) {
		
		this.fa = fa;
		Color recuadro = new Color (3,90,88);
		Color fondoPrincipal = new Color (66,141,138);
		Color fuentePrincipal = new Color (255,255,255);
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
		panel.setBounds(226, 146, 537, 413);
		contentPane.add(panel);
		panel.setLayout(null);
		
		usuario = new JTextField();
		usuario.setFont(new Font("Verdana", Font.PLAIN, 16));
		usuario.setBounds(58, 92, 407, 28);
		panel.add(usuario);
		usuario.setColumns(10);
		
		pass = new JPasswordField();
		pass.setFont(new Font("Verdana", Font.PLAIN, 16));
		pass.setBounds(58, 181, 407, 28);
		panel.add(pass);
		pass.setColumns(10);
		
		pass2 = new JPasswordField();
		pass2.setFont(new Font("Verdana", Font.PLAIN, 16));
		pass2.setBounds(58, 278, 407, 28);
		panel.add(pass2);
		pass2.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Ingrese nombre de usuario:");
		lblNewLabel.setForeground(fuentePrincipal);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 20));
		lblNewLabel.setBounds(58, 46, 346, 28);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ingrese una contraseña:");
		lblNewLabel_1.setForeground(fuentePrincipal);
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 20));
		lblNewLabel_1.setBounds(58, 150, 365, 20);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Repita la contraseña:");
		lblNewLabel_2.setForeground(fuentePrincipal);
		lblNewLabel_2.setFont(new Font("Verdana", Font.BOLD, 20));
		lblNewLabel_2.setBounds(58, 242, 281, 20);
		panel.add(lblNewLabel_2);
		
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.setForeground(fuentePrincipal);
		aceptar.setBackground(botones);
		aceptar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				char [] c = pass.getPassword();
				String contra = new String (c); 
				char [] b = pass2.getPassword();
				String contrasenia = new String (b); 
				
				if(!(contra.isEmpty())&&!(usuario.getText().isEmpty())&&!(contrasenia.isEmpty())) {
				
					if (!(fa.existeUsuario(usuario.getText()))) {
					
						if (contra.equals(contrasenia)) {
						
							UsuarioRegistroCorrecto ok = new UsuarioRegistroCorrecto(fa);
							DTOUsuario aux = new DTOUsuario(usuario.getText(),contra);
							fa.insertarUsuario(aux);
							fa.persistirDatos();
							fa.seleccionarUsuario(aux.getNombreUser());
							ok.setVisible(true);
							ok.setLocationRelativeTo(null);
							dispose();
						
						}else {
						
							UsuarioRegistroErrorContrasenia cn = new UsuarioRegistroErrorContrasenia();
							cn.setVisible(true);	
							cn.setLocationRelativeTo(null);
						}

					}else {
						
						UsuarioRegistroErrorNombre error = new UsuarioRegistroErrorNombre();
						error.setVisible(true);	
						error.setLocationRelativeTo(null);
					
					}
					
				}else {
					
					UsuarioRegistroErrorCampos camp = new UsuarioRegistroErrorCampos();
					camp.setVisible(true);
					camp.setLocationRelativeTo(null);
				
				}
				
			}
			
		});
		aceptar.setFont(new Font("Verdana", Font.BOLD, 16));
		aceptar.setBounds(280, 349, 185, 42);
		aceptar.setFocusPainted(false);
		panel.add(aceptar);
		
		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				Login frame = new Login(fa);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				dispose();
			
			}
		
		});
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Verdana", Font.BOLD, 16));
		btnCancelar.setFocusPainted(false);
		btnCancelar.setBackground(new Color(3, 60, 88));
		btnCancelar.setBounds(58, 349, 190, 42);
		panel.add(btnCancelar);
		
		JLabel lblNewLabel_3 = new JLabel("REGISTRO DE USUARIO");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Verdana", Font.BOLD, 42));
		lblNewLabel_3.setBounds(226, 11, 821, 99);
		contentPane.add(lblNewLabel_3);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 96, 988, 2);
		contentPane.add(separator_1);
		
	}
}