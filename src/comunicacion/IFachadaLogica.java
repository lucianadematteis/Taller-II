package comunicacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import logica.Atributo;
import logica.Tabla;

public interface IFachadaLogica {
	
	public boolean bdSeleccionada();
	
	public void seleccionarUsuario(String usuario);
	
	public void seleccionarBaseDatos(String baseDatos);
	
	public ArrayList<DTOAtributo> realizarConsultaClasica(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion);
	
	public boolean validaCondicion(String nombreTabla, String nombreAtributo, String valorCondicion);
	
	public Tabla obtenerTabla(String nombreTabla);
		
	public String obtenerTipoAtributo(String nombreTabla, String nombreAtributo);

	public boolean esVacia(String nombreTabla);
	
	public boolean hayRegistros(String nombreTabla, String nombreAtributo, String valorCondicion);
	
	public String obtenerClave (String nombreTabla);
	
	public boolean tieneClave (String nombreTabla);
	
	public void cambiarRegistro(String nombreTabla, String atributoCambiar, String valorNuevo, String nombreAtributoCondicion, String valorCondicion);
	
	public ArrayList<String> obtenerTablasNom();
	
	public boolean validaAtributos(String nombreTabla, ArrayList<String> atributos);
	
	public boolean validaCantidadAtributos(String nombreTabla, ArrayList<String> atributos);
	
	public boolean validaNotNull(String nombreTabla, ArrayList<String> atributos);
	
	public boolean validaClave(String nombreTabla, ArrayList<String> atributos);
	
	public void hacerNotNull (String nombreTabla, String nombreAtributo);
	
	public void hacerClave (String nombreTabla, String nombreAtributo);
	
	public int calcularPromedioRegistros(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion);
	
	public int obtenerMinimo (String nombreTabla, String nombreAtributo);
	
	public int obtenerMaximo (String nombreTabla, String nombreAtributo);
		
	public ArrayList<DTOAtributo> consultaAnd (String nombreTabla, String nombreAtributo, String nombreAtributoCondicion1, String valorCondicion1, String nombreAtributoCondicion2,String valorCondicion2);
	    
	public ArrayList<DTOAtributo> consultaOr(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion1, String valorCondicion1, String nombreAtributoCondcion2, String valorCondicion2);
		
	public boolean existeTabla(String nombreTabla);
	
	public void crearTabla(DTOTabla tabla);
	
	public boolean existeBD(String nombreBD);
	
	public void crearBD(DTOBaseDatos base);
	
	public void ingresarRegistro(String nombreTabla, LinkedHashMap<String, DTOAtributo> registro);
	
	public void borrarRegistro(String nombreTabla, String nombreAtributoCondicion, String valorCondicion);
	
	public String darAyuda(String ayuda1);
	
	public Atributo obtenerAtributo(String nombreAtributo, String nombreTabla);
	
	public boolean comandoExiste(String comando);
	
	public int contarRegistros( String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion);
		
	public boolean existeUsuario(String nombreUsuario);
	
	public boolean validarContrasenia (DTOUsuario usu);
	
	public void liberarMemoriaUsuario();
	
	public void liberarMemoriaBaseDatos();
	
	public void persistirDatos();
	
	public void insertarUsuario (DTOUsuario dto);
	
	public LinkedHashMap<String, DTOAtributo> generarArrayListRegistro(String nombreTabla, ArrayList<String> atributos);
	
	public ArrayList<String> obtenerBasesNom();
	
	public void modificarUsuario(DTOUsuario user);
	
	public void recuperarDatos();

}
