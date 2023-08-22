package logica;

import java.util.ArrayList;
import java.util.Map;

import comunicacion.DTOTabla;

public class Tabla {
	
	private String nombreTabla;
	private ArrayList<Map<String, Atributo>> registros;
	
	public Tabla(DTOTabla tabla) {
		
		this.nombreTabla = tabla.getNombreTabla();
		registros = new ArrayList<Map<String, Atributo>>();
		
	}
	
	public Tabla(String nombreTabla) {
		
		this.nombreTabla = nombreTabla;
		registros = new ArrayList<Map<String, Atributo>>();
		
	}
	
	public String getNombreTabla() {
		
		return nombreTabla;
		
	}
	
	public void setNombreTabla(String nombreTabla) {
		
		this.nombreTabla = nombreTabla;
		
	}

	public ArrayList<Map<String, Atributo>> getRegistros() {
		
		return registros;
		
	}

	public void setRegistros(ArrayList<Map<String, Atributo>> registros) {
		
		this.registros = registros;
		
	}

}
