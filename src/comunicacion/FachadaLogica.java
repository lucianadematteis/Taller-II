package comunicacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import logica.Atributo;
import logica.BaseDatos;
import logica.Entero;
import logica.Tabla;
import logica.Usuario;
import persistencia.Persistencia;

public class FachadaLogica implements IFachadaLogica {

	Persistencia persistencia = new Persistencia();
	private String baseDatos;
	private String usuario;
	private LinkedHashMap<String, Usuario> usuarios;
	private LinkedHashMap<String, String> ayuda;

	public FachadaLogica() {

		baseDatos = "";
		usuario = "";
		usuarios = new LinkedHashMap<String, Usuario>();
		ayuda = new LinkedHashMap<String, String>();

	}

	public String getBaseDatos() {

		return baseDatos;

	}

	public void seleccionarBaseDatos(String baseDatos) {

		this.baseDatos = baseDatos;

	}

	public void liberarMemoriaBaseDatos() {

		this.baseDatos = "";

	}
	
	public boolean bdSeleccionada() {
		
		return !(this.baseDatos.isEmpty());
		
	}

	public void liberarMemoriaUsuario() {

		this.usuario = "";

	}

	public String getUsuario() {

		return usuario;

	}

	public void seleccionarUsuario(String usuario) {

		this.usuario = usuario;

	}

	public LinkedHashMap<String, Usuario> getUsuarios() {

		return usuarios;

	}

	public void setUsuarios(LinkedHashMap<String, Usuario> usuarios) {

		this.usuarios = usuarios;

	}

	public void persistirDatos() {

		persistencia.persistirTodo(usuarios);

	}
	
public Usuario obtenerUsuario(String nombreUsu) {
		
		Usuario auxiliar= new Usuario("");
		auxiliar=usuario.get(nombreUsu).;
		return auxiliar;
		
	}
	
	public BaseDatos obtenerBaseDatos(String nomBD) {
		BaseDatos auxiliar = new BaseDatos("");
		auxiliar=usuario.get(usuario).getBasesDatos().get(nomBD);
		
		
	}
	
}

	public void recuperarDatos() {

		ayuda=persistencia.recuperarAyuda();
		persistencia.recuperarTodo(usuarios);

	}

	// Dai mod usuario a partir de un dto usuario
	public void modificarUsuario(DTOUsuario user) {

		Usuario usAux = new Usuario(user);
		usuarios.replace(usAux.getNombreUser(), usAux);

	}

	public void eliminarusuario(DTOUsuario user) {

		usuarios.remove(user.getNombreUser()); 
		
	}

	public Usuario obtenerUsuario() {

		if (usuarios.containsKey(usuario)) {

			return usuarios.get(usuario);

		}else{

			return null ;
		}

	}

	public int obtenerCantidad(ArrayList<Integer> valores) {

		return valores.size();

	}

	// recibe un arraylist con los valores y devuelve el promedio
	public int obtenerPromedio(ArrayList<Integer> valores) {

		int suma = 0;

		for (int i = 0; i < valores.size(); i++) {

			suma += valores.get(i);

		}

		int promedio = suma / valores.size();

		return promedio;

	}

	public void insertarUsuario (DTOUsuario dto) {

		Usuario usuario = new Usuario (dto);
		usuarios.put(dto.getNombreUser(), usuario);

	}

	public String obtenerClave (String nombreTabla) {

		return this.obtenerTabla(nombreTabla).obtenerClave();

	}
	
	public boolean tieneClave (String nombreTabla) {

		return this.obtenerTabla(nombreTabla).tieneClave();

	}

	public ArrayList<String> obtenerNotNull (String nombreTabla){

		return this.obtenerTabla(nombreTabla).obtenerNotNull();

	}

	public ArrayList<Atributo> realizarConsultaClasica(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion){

		ArrayList<LinkedHashMap<String, Atributo>> registros = this.obtenerTabla(nombreTabla).obtenerRegistros(nombreAtributoCondicion, valorCondicion);

		return this.obtenerTabla(nombreTabla).seleccionarAtributo(registros, nombreAtributoCondicion);

	}

	public void borrarRegistro(String nombreTabla, String nombreAtributoCondicion, String valorCondicion) {

		Tabla tabla = this.obtenerTabla(nombreTabla);

		ArrayList<LinkedHashMap<String, Atributo>> registrosEliminar = this.obtenerTabla(nombreTabla).obtenerRegistros(nombreAtributoCondicion, valorCondicion);

		for (LinkedHashMap<String, Atributo> registro : registrosEliminar) { 

			tabla.eliminarRegistro(registro);

		}

	}

	public void cambiarRegistro(String nombreTabla, String atributoCambiar, String valorNuevo, String nombreAtributoCondicion, String valorCondicion) {

		Tabla tabla = this.obtenerTabla(nombreTabla);

		ArrayList<LinkedHashMap<String, Atributo>> registrosCambiar = tabla.obtenerRegistros(nombreAtributoCondicion, valorCondicion);

		tabla.modificarRegistro(registrosCambiar, nombreAtributoCondicion, valorNuevo);

	}

	public void ingresarRegistro(String nombreTabla, LinkedHashMap<String, Atributo> registro) {

		Tabla tabla = this.obtenerTabla(nombreTabla);

		tabla.insertarRegistro(registro);

	}

	//recibe un nombre de tabla, busca en tabla y devuelve la tabla correspondiente
	public Tabla obtenerTabla(String nombreTabla) {

		Tabla aux = new Tabla("");	

		aux= usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla);

		return aux;
		
	}

	public Atributo obtenerAtributo(String nombreAtributo, String nombreTabla) {

		//debo crear un control por si no existe el atributo? o se encarga otro m�todo de eso?
		Atributo atr =usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).getRegistros().get(0).get(nombreAtributo);

		return atr;
		
	}
	
	public boolean validaCondicion(String nombreTabla, String nombreAtributo, String valorCondicion) {
		
	    String tipoAtributo = this.obtenerTabla(nombreTabla).obtenerTipo(nombreAtributo);

	    if ("entero".equals(tipoAtributo)) {
	    	
	        try {
	        	
	            Integer.parseInt(valorCondicion); // Si esto tiene éxito es porque es numérico
	            return true;
	            
	        } catch (NumberFormatException e) {
	        	
	            return false;
	            
	        }
	        
	    }

	    return true; // Si el tipo no es "entero", asumimos que cualquier valor de cadena es válido
	    
	}
	
	public String obtenerTipoAtributo(String nombreTabla, String nombreAtributo) {
		
		return this.obtenerTabla(nombreTabla).obtenerTipo(nombreAtributo);
		
	}
	
	public boolean esVacia(String nombreTabla) { //retorna true si no tiene
		
		return this.obtenerTabla(nombreTabla).esVacia();
		
	}
	
	public boolean hayRegistros(String nombreTabla, String nombreAtributo, String valorCondicion) {
		
		return !(this.obtenerTabla(nombreTabla).obtenerRegistros(nombreAtributo, valorCondicion).isEmpty());
		
	}
	

	public int obtenerMinimo (String nombreTabla, String nombreAtributo) {
		
		boolean salto=false;
		Integer min= null;
		Entero aux;

		ArrayList<LinkedHashMap<String, Atributo>> registros = usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).getRegistros();		

		for (LinkedHashMap<String, Atributo> reg : registros) {

			if (salto) {

				for (Map.Entry<String, Atributo> regis : reg.entrySet()) {

					if(regis.getValue().getNombreAtributo()==nombreAtributo) {

						if (regis.getValue() instanceof Entero) {

							aux = (Entero) regis.getValue();
							int valor = aux.getValor();
							
							if(min==null) {
								
								min=valor;  
								
							}
							
							if (valor< min) {
								
								min=valor;
								
							}

						}  
					}
				}
			}
			
			salto = true;
			
		}
		
		return min;
		
	}
	
	public int obtenerMaximo (String nombreTabla, String nombreAtributo) {
		
		boolean salto=false;
		Integer max= null;
		Entero aux;

		ArrayList<LinkedHashMap<String, Atributo>> registros = usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).getRegistros();		

		for (LinkedHashMap<String, Atributo> reg : registros) {

			if (salto) {

				for (Map.Entry<String, Atributo> regis : reg.entrySet()) {

					if(regis.getValue().getNombreAtributo()==nombreAtributo) {

						if (regis.getValue() instanceof Entero) {

							aux = (Entero) regis.getValue();
							int valor = aux.getValor();
							
							if(max==null) {
								
								max=valor;  
								
							}
							
							if (valor> max) {
								
								max=valor;
								
							}

						}  
					}
				}
			}
			
			salto = true;
		}
		
		return max;
		
	}
	
	public void hacerNotNull (String nombreTabla, String nombreAtributo) {
		
		usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).getRegistros().get(0).get(nombreAtributo).setNulo(false);
					
	}

	public void hacerClave (String nombreTabla, String nombreAtributo) {
		
		usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).getRegistros().get(0).get(nombreAtributo).setClave(true);
				
	}
	
	public int calcularPromedioRegistros(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion) { //hace promedios ENTEROS nada mas
		
		int promedio = 0;
		int suma = 0;
		ArrayList<Integer> numeros = new ArrayList<>();
		ArrayList<LinkedHashMap<String, Atributo>> lista = new ArrayList<LinkedHashMap<String, Atributo>>();
		lista = usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).obtenerRegistros(nombreAtributoCondicion, valorCondicion);
		
		for (LinkedHashMap<String, Atributo> elemento : lista) {
			
		   	 for (Map.Entry<String, Atributo> entry : elemento.entrySet()) {
		   		 
		   		 if(nombreAtributo == entry.getValue().getNombreAtributo()) {
		            	
		           	if (entry.getValue() instanceof Entero) {
		           		
		                Entero entero = (Entero) entry.getValue();
		                int valor = entero.getValor();
				        numeros.add(valor); 	 
		             
		             }
		           	
		         }
		       
		    }
		    
		}
		
        for (int numero : numeros) {
        	
            suma += numero;
            
        }
		
		promedio = suma/numeros.size();
		return promedio;
	
	}
	
	public ArrayList<Atributo> consultaOr(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion1, String valorCondicion1, String nombreAtributoCondcion2, String valorCondicion2) {
		
		ArrayList <Atributo> resultado1 = new ArrayList <Atributo>();
		ArrayList <Atributo> resultado2 = new ArrayList <Atributo>();
		ArrayList <Atributo> resultadoFinal = new ArrayList <Atributo>();
	
		resultado1 = realizarConsultaClasica(nombreTabla, nombreAtributo, nombreAtributoCondicion1, valorCondicion1);
		resultado2 = realizarConsultaClasica(nombreTabla, nombreAtributo, nombreAtributoCondcion2, valorCondicion2);
		
		resultadoFinal.addAll(resultado1);
		resultadoFinal.addAll(resultado2);
		
		return resultadoFinal;
		
	} 
	
	
	public boolean existeUsuario() {
		
		return usuarios.containsKey(usuario);
		
	}

	public boolean existeBD() {
		
		Usuario usuarioActual = obtenerUsuario();
		return usuarioActual.getBasesDatos().containsKey(baseDatos);
		
	}

	public boolean existeTabla(String nombreTabla) {

		Tabla tablita = obtenerTabla(nombreTabla);
	
		if (tablita != null) {
			
			return true;
			
		} else {
			
			return false;
		
		}
		
	}

	public void crearBD(DTOBaseDatos base) {
		
		Usuario usuarioActual = obtenerUsuario();
		BaseDatos baseDatos = new BaseDatos(base);
		usuarioActual.agregarBD(baseDatos);
		
	}
	
	
	public void crearTabla(DTOTabla tabla) {
			
		Tabla tablita = new Tabla(tabla);
		
		//obteneerBD.agregarTabla(tablita);

	}
	
	public boolean existeUsuario(String nombreUsuario) {
		
		if (usuarios.containsKey(nombreUsuario))
			return true;
		else
			return false;
	}

	public boolean usuarioTieneBase(String nombreBaseDatos) {
		
		if(usuarios.get(usuario).getBasesDatos().containsKey(nombreBaseDatos))
			return true;
		else
			return false;
	}

	public boolean baseTieneTabla(String tabla) {
		
		if(usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().containsKey(tabla))
			return true;
		else
			return false;
	}
	
	/*
	public ArrayList<String> obtenerTablasNom(){
		
		return this.obtenerBaseDatos.obtenerNomTablas();
		
	}
	*/
}
