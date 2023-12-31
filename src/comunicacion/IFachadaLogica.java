package comunicacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Esta es una interfaz donde se encuentran todas las operaciones que "comunican" entre las capas del programa.
 * La implementacion de estas operaciones esta en FachadaLogica.
 */

public interface IFachadaLogica {
	
	public boolean bdSeleccionada();
	
	public void seleccionarUsuario(String usuario);
	
	public void seleccionarBaseDatos(String baseDatos);
	
	public ArrayList<DTOAtributo> realizarConsultaClasica(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion, String operador);
	
	public boolean validaCondicion(String nombreTabla, String nombreAtributo, String valorCondicion);
			
	public String obtenerTipoAtributo(String nombreTabla, String nombreAtributo);

	public boolean esVacia(String nombreTabla);
	
	public boolean hayRegistros(String nombreTabla, String nombreAtributo, String valorCondicion, String operador);
	
	public String obtenerClave (String nombreTabla);
	
	public boolean tieneClave (String nombreTabla);
	
	public void cambiarRegistro(String nombreTabla, String atributoCambiar, String valorNuevo, String nombreAtributoCondicion, String valorCondicion, String operador);
	
	public ArrayList<String> obtenerTablasNom();
	
	public boolean validaAtributos(String nombreTabla, ArrayList<String> atributos);
	
	public boolean validaCantidadAtributos(String nombreTabla, ArrayList<String> atributos);
	
	public boolean validaNotNull(String nombreTabla, ArrayList<String> atributos);
	
	public boolean validaClave(String nombreTabla, ArrayList<String> atributos);
	
	public void hacerNotNull (String nombreTabla, String nombreAtributo);
	
	public void hacerClave (String nombreTabla, String nombreAtributo);
	
	public void quitarClave (String nombreTabla);
	
	public double calcularPromedioRegistros(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion, String operador);
	
	public Integer obtenerMinimo (String nombreTabla, String nombreAtributo);
	
	public Integer obtenerMaximo (String nombreTabla, String nombreAtributo);
		
	public ArrayList<DTOAtributo> consultaAnd (String nombreTabla, String nombreAtributo, String nombreAtributoCondicion1, String valorCondicion1, String nombreAtributoCondicion2,String valorCondicion2, String operador1, String operador2);
	    
	public ArrayList<DTOAtributo> consultaOr(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion1, String valorCondicion1, String nombreAtributoCondcion2, String valorCondicion2, String operador1, String operador2);
		
	public boolean existeTabla(String nombreTabla);
	
	public void crearTabla(DTOTabla tabla, LinkedHashMap<String, String> atributos);
	
	public boolean existeBD(String nombreBD);
	
	public void crearBD(DTOBaseDatos base);
	
	public void ingresarRegistro(String nombreTabla, LinkedHashMap<String, DTOAtributo> registro);
	
	public void borrarRegistro(String nombreTabla, String nombreAtributoCondicion, String valorCondicion, String operador);
	
	public String darAyuda(String ayuda1);
	
	public DTOAtributo obtenerAtributo(String nombreAtributo, String nombreTabla);
	
	public boolean comandoExiste(String comando);
	
	public int contarRegistros( String nombreTabla, String nombreAtributoCondicion, String valorCondicion, String operador);
		
	public boolean existeUsuario(String nombreUsuario);
	
	public boolean validarContrasenia (DTOUsuario usu);
	
	public void liberarMemoriaUsuario();
	
	public void liberarMemoriaBaseDatos();
	
	public void persistirDatos();
	
	public void insertarUsuario (DTOUsuario dto);
	
	public LinkedHashMap<String, DTOAtributo> generarArrayListRegistro(String nombreTabla, ArrayList<String> atributos);
	
	public ArrayList<String> obtenerBasesNom();

	public void eliminarusuario(DTOUsuario user);
	
	public String getUsuario();
	
	public void recuperarDatos();
	
	public void modificarUsuario(String contrasenia);
	
	public ArrayList<String> describeTabla(String nombreTabla);
	
	public ArrayList<DTOAtributo> joinNatural(String tabla1, String tabla2, String busqueda);
	
	public ArrayList<DTOAtributo> realizarConsultaSinWhere(String nombreTabla, String nombreAtributo);
	
	public boolean validaAtributosJoin (String tabla1, String tabla2);
	
	public String getBaseDatos();
	
	public void eliminarBD(String nombreBD);
	
	public void eliminarTabla(String nombreTabla);
	
	public String obtenerNomTabla (String nombreTabla);
	
	public int getDemoHabilitada();
	
	public void setDemoHabilitada(int demoHabilitada);
	
	public boolean esNotNull (String nombreTabla, String nombreAtributo);
	
	public boolean validaRegistroVacio(ArrayList<String> atributos);

}
