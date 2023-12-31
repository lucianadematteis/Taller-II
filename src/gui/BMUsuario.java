package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import comunicacion.DTOUsuario;
import comunicacion.IFachadaLogica;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class BMUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField pass;
	private JPasswordField pass2;
	private JPasswordField passActual;
	@SuppressWarnings("unused")
	private IFachadaLogica fa;

	public BMUsuario(IFachadaLogica fa) {
		
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
		panel.setBounds(35, 156, 456, 388);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("INGRESE NUEVA CONTRASEÑA");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setForeground(fuentePrincipal);
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 18));
		lblNewLabel_1.setBounds(59, 124, 387, 26);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("REPITA LA CONTRASEÑA");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setForeground(fuentePrincipal);
		lblNewLabel_2.setFont(new Font("Verdana", Font.BOLD, 18));
		lblNewLabel_2.setBounds(59, 225, 328, 26);
		panel.add(lblNewLabel_2);
		
		JButton aceptar = new JButton("CONFIRMAR");
		aceptar.setForeground(fuentePrincipal);
		aceptar.setBackground(botones);
		aceptar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				char []c1 =pass.getPassword();
				String contra1 = new String (c1);
				char[] c2 = pass2.getPassword();
				String contra2 = new String (c2);

				if(!(contra1.isEmpty()) && !(contra2.isEmpty())){
					
					if (contra1.equals(contra2)) {
						
						fa.modificarUsuario(contra1);
						fa.persistirDatos();
						fa.recuperarDatos();
						fa.seleccionarUsuario(fa.getUsuario());
						VentanaPrincipal ven = new VentanaPrincipal(fa);
						ven.setVisible(true);
						ven.setLocationRelativeTo(null);
						UsuarioCambioContraseniaCorrecto rc = new UsuarioCambioContraseniaCorrecto();
						rc.setVisible(true);
						rc.setLocationRelativeTo(null);
						dispose();
						
					} else {
						
						UsuarioRegistroErrorContrasenia cn = new UsuarioRegistroErrorContrasenia();
						cn.setVisible(true);
						cn.setLocationRelativeTo(null);
						
					}

				}else {
					UsuarioRegistroErrorCampos camp = new UsuarioRegistroErrorCampos();
					camp.setVisible(true);
					camp.setLocationRelativeTo(null);
				}

			}
			
		});
		aceptar.setFont(new Font("Verdana", Font.BOLD, 16));
		aceptar.setBounds(133, 324, 176, 38);
		aceptar.setFocusPainted(false);
		panel.add(aceptar);
		
		JLabel lblNewLabel = new JLabel("CAMBIAR CONTRASE\u00D1A");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 24));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(59, 24, 328, 55);
		panel.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(22, 77, 392, 2);
		panel.add(separator);
		
		pass = new JPasswordField();
		pass.setFont(new Font("Verdana", Font.PLAIN, 17));
		pass.setBounds(59, 161, 328, 26);
		panel.add(pass);
		pass.setColumns(10);
		
		pass2 = new JPasswordField();
		pass2.setFont(new Font("Verdana", Font.PLAIN, 17));
		pass2.setBounds(59, 260, 328, 26);
		panel.add(pass2);
		pass2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("MODIFICACI\u00D3N DE USUARIO");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Verdana", Font.BOLD, 40));
		lblNewLabel_3.setBounds(10, 11, 988, 99);
		contentPane.add(lblNewLabel_3);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 96, 988, 2);
		contentPane.add(separator_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setBackground(new Color(3, 90, 88));
		panel_1.setBounds(513, 156, 456, 388);
		contentPane.add(panel_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("INGRESE SU CONTRASEÑA ACTUAL");
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("Verdana", Font.BOLD, 18));
		lblNewLabel_2_1.setBounds(42, 234, 392, 26);
		panel_1.add(lblNewLabel_2_1);
		
		JButton aceptar_1 = new JButton("ELIMINAR CUENTA");
		aceptar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				char [] c3 = passActual.getPassword();
				String passA = new String (c3);
				DTOUsuario auxUs = new DTOUsuario(fa.getUsuario(),passA);
				
				if (fa.validarContrasenia(auxUs)){
				
					ConfirmarEliminarUsuario frame = new ConfirmarEliminarUsuario(fa,auxUs);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					dispose();
				
				} else {
					UsuarioErrorContrasenia frame = new UsuarioErrorContrasenia();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				}
				
			}
		});
		aceptar_1.setForeground(Color.WHITE);
		aceptar_1.setFont(new Font("Verdana", Font.BOLD, 16));
		aceptar_1.setFocusPainted(false);
		aceptar_1.setBackground(new Color(3, 60, 88));
		aceptar_1.setBounds(88, 321, 274, 38);
		panel_1.add(aceptar_1);
		
		JLabel lblEliminarUsuario = new JLabel("ELIMINAR USUARIO");
		lblEliminarUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminarUsuario.setForeground(Color.WHITE);
		lblEliminarUsuario.setFont(new Font("Verdana", Font.BOLD, 24));
		lblEliminarUsuario.setBounds(10, 24, 436, 55);
		panel_1.add(lblEliminarUsuario);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(22, 77, 392, 2);
		panel_1.add(separator_2);
		
		passActual = new JPasswordField();
		passActual.setFont(new Font("Verdana", Font.PLAIN, 17));
		passActual.setColumns(10);
		passActual.setBounds(41, 271, 373, 26);
		panel_1.add(passActual);
		
		JTextArea txtrAtencinElSiguiente = new JTextArea();
		txtrAtencinElSiguiente.setForeground(Color.YELLOW);
		txtrAtencinElSiguiente.setFont(new Font("Verdana", Font.BOLD, 15));
		txtrAtencinElSiguiente.setBackground(recuadro);
		txtrAtencinElSiguiente.setText("EL SIGUIENTE CAMBIO DARÁ DE BAJA\r\nEL USUARIO DE MANERA PERMANENTE\r\nELIMINANDO LOS CAMBIOS Y REGISTROS\r\nREALIZADOS EN EL SISTEMA.");
		txtrAtencinElSiguiente.setBounds(54, 122, 392, 92);
		panel_1.add(txtrAtencinElSiguiente);
		
		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				VentanaPrincipal ven = new VentanaPrincipal(fa);
				ven.setVisible(true);
				ven.setLocationRelativeTo(null);
				dispose();
			
			}
		
		});
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Verdana", Font.BOLD, 18));
		btnCancelar.setFocusPainted(false);
		btnCancelar.setBackground(new Color(3, 60, 88));
		btnCancelar.setBounds(773, 576, 196, 39);
		contentPane.add(btnCancelar);
		
	}
	
}
