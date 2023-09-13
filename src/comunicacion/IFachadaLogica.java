package comunicacion;

import java.util.ArrayList;

import logica.Atributo;
import logica.Tabla;

public interface IFachadaLogica {
	
	public void seleccionarUsuario(String usuario);
	
	public void seleccionarBaseDatos(String baseDatos);
	
	public ArrayList<Atributo> realizarConsultaClasica(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion);
	
	public boolean validaCondicion(String nombreTabla, String nombreAtributo, String valorCondicion);
	
	public Tabla obtenerTabla(String nombreTabla);
		
	public String obtenerTipoAtributo(String nombreTabla, String nombreAtributo);

	public boolean esVacia(String nombreTabla);
	
	public boolean hayRegistros(String nombreTabla, String nombreAtributo, String valorCondicion);
	
	//public ArrayList<String> obtenerTablasNom();
	
}
