package gui;
/**
 * @autor Gabriel Bolsi
 * @autor Brandon Cair�s
 * @autor Daiana Daguerre
 * @autor Luciana De Matteis
 * @autor Mauricio Gonz�lez
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
