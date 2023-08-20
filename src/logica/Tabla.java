package logica;

import java.util.ArrayList;

class Tabla {
	
private String nombreTabla;
private ArrayList<Registros> tuplas;


public Tabla(String nombreTabla, ArrayList<Registros> tuplas) {
	super();
	this.nombreTabla = nombreTabla;
	this.tuplas = tuplas;
}


public String getNombreTabla() {
	return nombreTabla;
}


public void setNombreTabla(String nombreTabla) {
	this.nombreTabla = nombreTabla;
}


public ArrayList<Registros> getTuplas() {
	return tuplas;
}


public void setTupla(ArrayList<Registros> tuplas) {
	this.tuplas = tuplas;
}


}
