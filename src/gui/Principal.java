package gui;

import comunicacion.FachadaLogica;

public class Principal {

	public static void main(String[] args) {
		
		FachadaLogica fa = new FachadaLogica();
		fa.recuperarDatos();
		Login log = new Login(fa);
		log.setVisible(true);
		
	}


}
