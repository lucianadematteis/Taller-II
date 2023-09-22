package gui;
/**
 * @autor Gabriel Bolsi
 * @autor Brandon Cairús
 * @autor Daiana Daguerre
 * @autor Luciana De Matteis
 * @autor Mauricio González
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
