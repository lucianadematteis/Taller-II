package gui;
/**
 * 
 * Proporciona clases para crear la interfaz de usuario y gestionar interacciones con el usuario.
 * 
 */


import comunicacion.FachadaLogica;
/**
 * 
 * Clase principall
 *
 */
public class Principal {
	/**
	 * @author Gabriel Bolsi
	 * @author Brandon Cairus
	 * @author Daiana Daguerre
	 * @author Luciana De Matteis
	 * @author Mauricio Gonzalez
	 * 
	 */

	public static void main(String[] args) {
		
		FachadaLogica fa = new FachadaLogica();
		fa.recuperarDatos();
		Login log = new Login(fa);
		log.setLocationRelativeTo(null);
		log.setVisible(true);
		
	}


}
