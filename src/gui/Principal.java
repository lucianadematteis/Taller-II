package gui;
/**
 * @author Gabriel Bolsi
 * @author Brandon Cair�s
 * @author Daiana Daguerre
 * @author Luciana De Matteis
 * @author Mauricio Gonz�lez
 */

import comunicacion.FachadaLogica;

public class Principal {

	public static void main(String[] args) {
		
		FachadaLogica fa = new FachadaLogica();
		fa.recuperarDatos();
		Login log = new Login(fa);
		log.setLocationRelativeTo(null);
		log.setVisible(true);
		
	}


}
