package gui;

import comunicacion.FachadaLogica;

/**
 * Clase principal
 * Proporciona clases para crear la interfaz de usuario y gestionar interacciones con el usuario.
 */

public class Principal {

	public static void main(String[] args) {
		
		FachadaLogica fa = new FachadaLogica();
		fa.recuperarDatos();
		Login log = new Login(fa);
		log.setLocationRelativeTo(null);
		log.setVisible(true);
		
	}

}
