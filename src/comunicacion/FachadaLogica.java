package comunicacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import logica.Atributo;
import logica.BaseDatos;
import logica.Cadena;
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

		for (Map.Entry<String, BaseDatos> bd : this.obtenerUsuario().getBasesDatos().entrySet()) {
			
			if(bd.getKey().equalsIgnoreCase(baseDatos)) {
				
				this.baseDatos = bd.getKey();
				
			}
			
		}

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
		
		return usuarios.get(this.usuario);
		
	}
	
	public BaseDatos obtenerBaseDatos() {
		
		for (Map.Entry<String, BaseDatos> entry : this.obtenerUsuario().getBasesDatos().entrySet()) {
			
			if(entry.getKey().equalsIgnoreCase(baseDatos)) {
				
				return entry.getValue();
			}
			
		}
		
		return null;
		
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

	public ArrayList<DTOAtributo> realizarConsultaClasica(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion){

		ArrayList<LinkedHashMap<String, DTOAtributo>> registros = this.obtenerTabla(nombreTabla).obtenerRegistros(nombreAtributoCondicion, valorCondicion);

		return this.obtenerTabla(nombreTabla).seleccionarAtributo(registros, nombreAtributo);

	}

	public void borrarRegistro(String nombreTabla, String nombreAtributoCondicion, String valorCondicion) {

		Tabla tabla = this.obtenerTabla(nombreTabla);

		ArrayList<LinkedHashMap<String, DTOAtributo>> registrosEliminar = this.obtenerTabla(nombreTabla).obtenerRegistros(nombreAtributoCondicion, valorCondicion);

		for (LinkedHashMap<String, DTOAtributo> registro : registrosEliminar) { 

			tabla.eliminarRegistro(registro);

		}

	}

	public void cambiarRegistro(String nombreTabla, String atributoCambiar, String valorNuevo, String nombreAtributoCondicion, String valorCondicion) {

		Tabla tabla = this.obtenerTabla(nombreTabla);

		ArrayList<LinkedHashMap<String, DTOAtributo>> registrosCambiar = tabla.obtenerRegistros(nombreAtributoCondicion, valorCondicion);

		tabla.modificarRegistro(registrosCambiar, nombreAtributoCondicion, valorNuevo);

	}


	public void ingresarRegistro(String nombreTabla, LinkedHashMap<String, DTOAtributo> registro) {

		Tabla tabla = this.obtenerTabla(nombreTabla);

		tabla.insertarRegistro(registro);

	}

	//recibe un nombre de tabla, busca en tabla y devuelve la tabla correspondiente
	public Tabla obtenerTabla(String nombreTabla) {
		
		for (Map.Entry<String, Tabla> tabla : this.obtenerBaseDatos().getTablas().entrySet()) {
			
			if(tabla.getKey().equalsIgnoreCase(nombreTabla))
				
				return tabla.getValue();
			
		}
		
	    return null;

	}

	public DTOAtributo obtenerAtributo(String nombreAtributo, String nombreTabla) {

		DTOAtributo atr =usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).getRegistrosDTO().get(0).get(nombreAtributo);

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

					if(regis.getValue().getNombreAtributo().equals(nombreAtributo)) {

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

		ArrayList<LinkedHashMap<String, Atributo>> registros = this.obtenerTabla(nombreTabla).getRegistros();
		
		for (LinkedHashMap<String, Atributo> reg : registros) {

			if (salto) {

				for (Map.Entry<String, Atributo> regis : reg.entrySet()) {

					if(regis.getValue().getNombreAtributo().equals(nombreAtributo)) {

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
	
	public void quitarClave (String nombreTabla) {
		
		String clave = this.obtenerTabla(nombreTabla).obtenerClave();
		usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).getRegistros().get(0).get(clave).setClave(false);
		
	}
	
	public int calcularPromedioRegistros(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion) { //hace promedios ENTEROS nada mas
		
		int promedio = 0;
		int suma = 0;
		ArrayList<Integer> numeros = new ArrayList<>();
		ArrayList<LinkedHashMap<String, DTOAtributo>> lista = new ArrayList<LinkedHashMap<String, DTOAtributo>>();
		lista = usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).obtenerRegistros(nombreAtributoCondicion, valorCondicion);
		
		for (LinkedHashMap<String, DTOAtributo> elemento : lista) {
			
		   	 for (Map.Entry<String, DTOAtributo> entry : elemento.entrySet()) {
		   		 
		   		 if(nombreAtributo == entry.getValue().getNombreAtributo()) {
		            	
		           	if (entry.getValue() instanceof DTOEntero) {
		           		
		                DTOEntero entero = (DTOEntero) entry.getValue();
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
	
	public ArrayList<DTOAtributo> consultaOr(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion1, String valorCondicion1, String nombreAtributoCondcion2, String valorCondicion2) {
		
		ArrayList <DTOAtributo> resultado1 = new ArrayList <DTOAtributo>();
		ArrayList <DTOAtributo> resultado2 = new ArrayList <DTOAtributo>();
		ArrayList <DTOAtributo> resultadoFinal = new ArrayList <DTOAtributo>();
	
		resultado1 = realizarConsultaClasica(nombreTabla, nombreAtributo, nombreAtributoCondicion1, valorCondicion1);
		resultado2 = realizarConsultaClasica(nombreTabla, nombreAtributo, nombreAtributoCondcion2, valorCondicion2);
		
		resultadoFinal.addAll(resultado1);
		resultadoFinal.addAll(resultado2);
		
		return resultadoFinal;
		
	} 
	
	public boolean existeUsuario() {
		
		return usuarios.containsKey(usuario);
		
	}

	public boolean existeBD(String nombreBD) {
		
		for (String nombre : this.obtenerUsuario().getBasesDatos().keySet()) {
			
	        if (nombre.equalsIgnoreCase(nombreBD)) 
	           
	        	return true;
	        
	    }

	    return false;
		
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
	
	public void crearTabla(DTOTabla tabla) {
		
		Tabla tablita = new Tabla(tabla);
		this.obtenerBaseDatos().agregarTabla(tablita);

	}
	
	
	public ArrayList<String> obtenerTablasNom() {
		
		return this.obtenerBaseDatos().obtenerNomTablas();
		
	}
	
	
	public boolean validarContrasenia (DTOUsuario usu) {
		
		String nombre = usu.getNombreUser();
		String contrasenia = usu.getContrasenia();
		
		if(usuarios.get(nombre).getContrasenia().equals(contrasenia))
			return true;
		else
			return false;
		
	}
	
	public ArrayList<DTOAtributo> consultaAnd (String nombreTabla, String nombreAtributo, String nombreAtributoCondicion1, String valorCondicion1, String nombreAtributoCondicion2,String valorCondicion2) {
    	
    	ArrayList <DTOAtributo>  res1= new ArrayList<DTOAtributo>();
    	ArrayList <DTOAtributo> res2 = new ArrayList<DTOAtributo>();
    	res1 = realizarConsultaClasica(nombreTabla, nombreAtributo, nombreAtributoCondicion1, valorCondicion1);
		res2 = realizarConsultaClasica(nombreTabla, nombreAtributo, nombreAtributoCondicion2, valorCondicion2);
    	res1.retainAll(res2);
    	return res1;
    	
    }
	
	public int contarRegistros( String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion){
		 
		ArrayList<LinkedHashMap<String, DTOAtributo>> resultado1 = new ArrayList<LinkedHashMap<String, DTOAtributo>>();
		Tabla tablita = this.obtenerTabla(nombreTabla);
		resultado1= tablita.obtenerRegistros(nombreAtributo, valorCondicion);	
		return  resultado1.size();
	}

	public String darAyuda(String ayuda1) {
	
		return ayuda.get(ayuda1);

	}

	public boolean validaAtributos(String nombreTabla, ArrayList<String> atributos) {
		
		Tabla tablita = this.obtenerTabla(nombreTabla);
		LinkedHashMap<String, Atributo> guia = tablita.getRegistros().get(0);
		
		int i=0;
		
		for (Entry<String, Atributo> atriGuia : guia.entrySet()) {
			
			if(!(atributos.get(i).equals("NULL"))) {
			
				if((!(validaCondicion(nombreTabla, atriGuia.getKey(), atributos.get(i))))){
					
					return false;
					
				}
			
			}
			
			i++;
				
		}
		
		return true;
		
	}
	
	public boolean validaCantidadAtributos(String nombreTabla, ArrayList<String> atributos) {
		
		Tabla tablita = this.obtenerTabla(nombreTabla);
		int cantidadAtributos = tablita.getRegistros().get(0).size();
		
		for (int i = 0; i < cantidadAtributos ; i++) {
			
			if(atributos.get(i) == null) {
				
				return false;
				
			}
			
		}
		
		return true;
		
	}
	
	public boolean validaNotNull(String nombreTabla, ArrayList<String> atributos) {
		
		Tabla tablita = this.obtenerTabla(nombreTabla);
		
		LinkedHashMap<String, Atributo> guia = tablita.getRegistros().get(0);
		
		int i=0;
		
		for (Entry<String, Atributo> atriGuia : guia.entrySet()) {
			
			if((atributos.get(i).equals("NULL")) && (atriGuia.getValue().getNulo())) {
			
				return false;
			
			}
			
			i++;
				
		}
		
		return true;
		
	}
	
	public boolean validaClave(String nombreTabla, ArrayList<String> atributos) {
		
		Tabla tablita = this.obtenerTabla(nombreTabla);
		LinkedHashMap<String, Atributo> guia = tablita.getRegistros().get(0);
		String clave = tablita.obtenerClave();
		
		int i=0;
		
		for (Entry<String, Atributo> atriGuia : guia.entrySet()) {
			
			if((atributos.get(i).equals("NULL")) && (atriGuia.getKey().equals(clave))) {
			
				return false;
			
			}else if(!(tablita.obtenerRegistros(clave, atributos.get(i)).isEmpty())) {
				
				return false;
				
			}
			
			i++;
				
		}
		
		return true;
		
	}
	
	public boolean comandoExiste(String comando) {
		
		return this.ayuda.containsKey(comando);
		
	}
	
	public ArrayList<String> obtenerBasesNom(){
		
		ArrayList <String> aux = new ArrayList<String>();
		LinkedHashMap <String,BaseDatos> bdsAux =usuarios.get(usuario).getBasesDatos();
		
		for (Entry<String, BaseDatos> Entry : bdsAux.entrySet()) {
			
			String nom = Entry.getValue().getNombreBD();
			aux.add(nom);
		
		}
		
		return aux;
		
	}
	
	public LinkedHashMap<String, DTOAtributo> generarArrayListRegistro(String nombreTabla, ArrayList<String> atributos){
		
		LinkedHashMap<String, DTOAtributo> registro = new LinkedHashMap<String, DTOAtributo>();
		LinkedHashMap<String, Atributo> guia = obtenerTabla(nombreTabla).getRegistros().get(0);
		int i=0;
		
		for (Entry<String, Atributo> atriGuia : guia.entrySet()) {
			
			if(atriGuia.getValue() instanceof Entero) {
				
				DTOEntero atrE = new DTOEntero();
				
				if(!(atributos.get(i).equals("NULL"))) {
					
					atrE.setValor(Integer.parseInt(atributos.get(i)));
					
				}
				
				registro.put(atriGuia.getKey(), atrE);
				
			}else if(atriGuia.getValue() instanceof Cadena){
				
				DTOCadena atrC = new DTOCadena();
				
				if(!(atributos.get(i).equals("NULL"))) {
					
					atrC.setDato(atributos.get(i));
					
				}
				
				registro.put(atriGuia.getKey(), atrC);
				
			}
				
			i++;
			
		}
		
		return registro;
		
	}
	
}
