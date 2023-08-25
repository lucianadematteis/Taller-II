package logica;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import comunicacion.DTOTabla;

public class Tabla {
	
	private String nombreTabla;
	private ArrayList<LinkedHashMap<String, Atributo>> registros;
	
	public Tabla(DTOTabla tabla) {
		
		this.nombreTabla = tabla.getNombreTabla();
		registros = new ArrayList<LinkedHashMap<String, Atributo>>();
		
	}
	
	public Tabla(String nombreTabla) {
		
		this.nombreTabla = nombreTabla;
		registros = new ArrayList<LinkedHashMap<String, Atributo>>();
		
	}
	
	public String getNombreTabla() {
		
		return nombreTabla;
		
	}
	
	public void setNombreTabla(String nombreTabla) {
		
		this.nombreTabla = nombreTabla;
		
	}

	public ArrayList<LinkedHashMap<String, Atributo>> getRegistros() {
		
		return registros;
		
	}

	public void setRegistros(ArrayList<LinkedHashMap<String, Atributo>> registros) {
		
		this.registros = registros;
		
	}

}
