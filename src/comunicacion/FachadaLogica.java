package comunicacion;

import java.util.ArrayList;
import java.util.HashSet;
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

	public void persistirDatos() {

		persistencia.persistirTodo(usuarios);

	}
	
	private BaseDatos obtenerBaseDatos() {
		
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

	private Usuario obtenerUsuario() {

		if (usuarios.containsKey(usuario)) {

			return usuarios.get(usuario);

		}else{

			return null ;
		}

	}

	public int obtenerCantidad(ArrayList<Integer> valores) {

		return valores.size();

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
		
		for (LinkedHashMap<String, DTOAtributo> registro : registrosCambiar) {
		
			tabla.modificarRegistros(tabla.convertirMapa(registro), atributoCambiar, valorNuevo);
			
		}
			
	}

	public void ingresarRegistro(String nombreTabla, LinkedHashMap<String, DTOAtributo> registro) {

		Tabla tabla = this.obtenerTabla(nombreTabla);

		tabla.insertarRegistro(registro);

	}

	//recibe un nombre de tabla, busca en tabla y devuelve la tabla correspondiente
	private Tabla obtenerTabla(String nombreTabla) {
		
		for (Map.Entry<String, Tabla> tabla : this.obtenerBaseDatos().getTablas().entrySet()) {
			
			if(tabla.getKey().equalsIgnoreCase(nombreTabla))
				
				return tabla.getValue();
			
		}
		
	    return null;

	}

	public DTOAtributo obtenerAtributo(String nombreAtributo, String nombreTabla) {

		LinkedHashMap<String, DTOAtributo> guia = this.obtenerTabla(nombreTabla).getRegistrosDTO().get(0);
		
		for (Entry<String, DTOAtributo> entry : guia.entrySet()) {
			
			if(entry.getKey().equalsIgnoreCase(nombreAtributo)) {
				
				return entry.getValue();
				
			}
			
		}
		
		return null;
		
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

	    return true; 
	    
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
	

	public int obtenerMinimo(String nombreTabla, String nombreAtributo) {
		
	    Integer minimo = null;
	    boolean primero=true;
	    Tabla tabla = this.obtenerTabla(nombreTabla);
	    ArrayList<DTOAtributo> registros = tabla.seleccionarAtributo(tabla.getRegistrosDTO(), nombreAtributo);

	    for (DTOAtributo reg : registros) {
	    
	    	if (primero) {
	            
	        	primero = false;  
	            
	        } else {
	    	
		    	DTOEntero atributo = (DTOEntero) reg;
		        int valor = atributo.getValor();
	
		        if (minimo == null || valor < minimo) {
		        
		        	minimo = valor;
		        
		        }
	        
		   }
	    
	    }

	    return minimo;
	}

	public int obtenerMaximo(String nombreTabla, String nombreAtributo) {
		
	    Integer maximo = null;
	    boolean primero = true; 
	    Tabla tablita = this.obtenerTabla(nombreTabla);
	    ArrayList<DTOAtributo> registros = tablita.seleccionarAtributo(tablita.getRegistrosDTO(), nombreAtributo);

	    for (DTOAtributo reg : registros) {
	    	
	        if (primero) {
	            
	        	primero = false;  
	            
	        } else {
	        
	        	DTOEntero atributo = (DTOEntero) reg;
	            int valor = atributo.getValor();

	            if (maximo == null || valor > maximo) {
	              
	            	maximo = valor;
	            
	            }
	        }
	    }

	    return maximo;
	    
	}

	public void hacerNotNull (String nombreTabla, String nombreAtributo) {
		
		usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).getRegistros().get(0).get(nombreAtributo).setNulo(true);
					
	}

	public void hacerClave (String nombreTabla, String nombreAtributo) {
		
		usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).getRegistros().get(0).get(nombreAtributo).setClave(true);
		hacerNotNull (nombreTabla, nombreAtributo);
				
	}
	
	public void quitarClave (String nombreTabla) {
		
		String clave = this.obtenerTabla(nombreTabla).obtenerClave();
		usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).getRegistros().get(0).get(clave).setClave(false);
		
	}
	
	public double calcularPromedioRegistros(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion, String valorCondicion) {
	    
		double promedio = 0.0;
	    int suma = 0;
	    ArrayList<LinkedHashMap<String, DTOAtributo>> registros = this.obtenerTabla(nombreTabla).obtenerRegistros(nombreAtributoCondicion, valorCondicion);
	    ArrayList<DTOAtributo> seleccion = this.obtenerTabla(nombreTabla).seleccionarAtributo(registros, nombreAtributo);
	    int cantidadValores = seleccion.size(); 

	    for (DTOAtributo dato : seleccion) {
			
            if (dato instanceof DTOEntero) {
            
            	DTOEntero atributo = (DTOEntero) dato;
                int valor = atributo.getValor();
                suma += valor;
            
            }
            
	    }

	    if (cantidadValores > 0) {
	      
	    	promedio = (double) suma / cantidadValores;
	    
	    }
	    
	    return promedio;
	    
	}

	public ArrayList<DTOAtributo> consultaOr(String nombreTabla, String nombreAtributo, String nombreAtributoCondicion1, String valorCondicion1, String nombreAtributoCondcion2, String valorCondicion2) {
		
		ArrayList <DTOAtributo> resultado1 = new ArrayList <DTOAtributo>();
		ArrayList <DTOAtributo> resultado2 = new ArrayList <DTOAtributo>();
	
		resultado1 = realizarConsultaClasica(nombreTabla, nombreAtributo, nombreAtributoCondicion1, valorCondicion1);
		resultado2 = realizarConsultaClasica(nombreTabla, nombreAtributo, nombreAtributoCondcion2, valorCondicion2);
	
		
        HashSet<DTOAtributo> elementosUnicos = new HashSet<>(resultado1);

        for (DTOAtributo elemento : resultado2) {
        	
            if (!elementosUnicos.contains(elemento)) {
            	
                resultado1.add(elemento);
                elementosUnicos.add(elemento);
                
            }
        }
        
        
		return resultado1;
		
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
	
	public void crearTabla(DTOTabla tabla, LinkedHashMap<String, String> atributos) {
		
		Tabla tablita = new Tabla(tabla);
		this.obtenerBaseDatos().agregarTabla(tablita);
		tablita.insertarRegistro(tablita.generarAtributos(atributos));

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
    	
    	HashSet<DTOAtributo> noHayRepetidos = new HashSet<>(res1);

        ArrayList<DTOAtributo> sinRepetidos = new ArrayList<>(noHayRepetidos);
		
		return sinRepetidos;
		
    }
	
	public int contarRegistros( String nombreTabla, String nombreAtributoCondicion, String valorCondicion){
		 
		ArrayList<LinkedHashMap<String, DTOAtributo>> resultado1 = new ArrayList<LinkedHashMap<String, DTOAtributo>>();
		Tabla tablita = this.obtenerTabla(nombreTabla);
		resultado1= tablita.obtenerRegistros(nombreAtributoCondicion, valorCondicion);	
		
		return resultado1.size();
	
	}

	public String darAyuda(String comando) {
	
		return ayuda.get(comando.toUpperCase());

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

		if(atributos.size() != cantidadAtributos) {
			
			return false;
			
		}
			
		
		return true;
		
	}
	
	public boolean validaNotNull(String nombreTabla, ArrayList<String> atributos) {
		
		Tabla tablita = this.obtenerTabla(nombreTabla);
		
		LinkedHashMap<String, Atributo> guia = tablita.getRegistros().get(0);
		
		int i=0;
		
		for (Entry<String, Atributo> atriGuia : guia.entrySet()) {
			
			if((atributos.get(i).equalsIgnoreCase("NULL")) && (atriGuia.getValue().getNulo()==true)) {
			
				return false;
			
			}
			
			i++;
				
		}
		
		return true;
		
	}
	
	public boolean validaClave(String nombreTabla, ArrayList<String> atributos) {
		
		Tabla tablita = this.obtenerTabla(nombreTabla);
		LinkedHashMap<String, Atributo> guia = tablita.getRegistros().get(0);
		
		if(tablita.tieneClave()) {
		
			String clave = tablita.obtenerClave();
			
			for (Entry<String, Atributo> atriGuia : guia.entrySet()) {
				
				for (int i = 0; i < atributos.size(); i++) {
					
					if((atriGuia.getKey().equals(clave))) { //SI la clave es nula
						
						if((atributos.get(i).equals("NULL"))) {
							
							return false;
						
						}
						
						if(!(tablita.obtenerRegistros(atriGuia.getKey(), atributos.get(i)).isEmpty())) { //Si la clave se repite
							
							return false;
							
						}else {
							
							return true;
							
						}
						
					}
					
				}
				
			}
			
		}
	
		return true;
		
	}
	
	public boolean comandoExiste(String comando) {
		
		return this.ayuda.containsKey(comando.toUpperCase());
		
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
				
				Entero atributoE = (Entero) atriGuia.getValue();
				DTOEntero atrE =  new DTOEntero(atributoE);
				
				if(!(atributos.get(i).equals("NULL"))) {
					
					atrE.setValor(Integer.parseInt(atributos.get(i)));
					
				}
				
				registro.put(atriGuia.getKey(), atrE);
				
			}else if(atriGuia.getValue() instanceof Cadena){

				Cadena atributoC = (Cadena) atriGuia.getValue();
				DTOCadena atrC = new DTOCadena(atributoC);
				
				if(!(atributos.get(i).equals("NULL"))) {
					
					atrC.setDato(atributos.get(i));
					
				}
				
				registro.put(atriGuia.getKey(), atrC);
				
			}
				
			i++;
			
		}
		
		return registro;
		
	}
	
	private ArrayList<Atributo> obtenerAuxiliar(Tabla tabla1, Tabla tabla2) {
		
		ArrayList<Atributo> resultado = new ArrayList< Atributo> ();
		
		ArrayList<LinkedHashMap<String, Atributo>> reg1 = new ArrayList<LinkedHashMap<String, Atributo>>();
		ArrayList<LinkedHashMap<String, Atributo>> reg2 = new ArrayList<LinkedHashMap<String, Atributo>>();
		
		if(tabla1.getRegistros().size() > tabla2.getRegistros().size()) {
			reg1 = tabla1.getRegistros();
			reg2 = tabla2.getRegistros();
	   
		}else {
			 reg2 = tabla1.getRegistros();
			 reg1 = tabla2.getRegistros();
			
		}
		
		reg2.remove(0);
		reg1.remove(0);
	   
	    for (int i=0; i<reg1.size(); i++) {
	    	
	    	for (int j=0; j<reg2.size(); j++) {
	    	
	    		for(Map.Entry<String, Atributo> entry1 : reg1.get(i).entrySet()) {
	    		
	    			for(Map.Entry<String, Atributo> entry2 : reg2.get(j).entrySet()) {
	    				
	    				if (repiteAtributo(entry1.getValue(), entry2.getValue())) {
	    					
	    					resultado.add(entry2.getValue());
	                
	    				}
	             
	                }
	            }
	        }
	    }
	    
	    return resultado;
    		
    }
    	
	public ArrayList<DTOAtributo> joinNatural(String tabla1, String tabla2, String busqueda){
		
		Tabla tab1 = obtenerTabla(tabla1);
		Tabla tab2 = obtenerTabla(tabla2);
		ArrayList<Atributo> buscar = new ArrayList<Atributo> ();
		ArrayList<DTOAtributo> resultado = new ArrayList<DTOAtributo>();
		buscar = obtenerAuxiliar(tab1, tab2);
		
		ArrayList<LinkedHashMap<String, Atributo>> reg = new ArrayList<LinkedHashMap<String, Atributo>>();
		
		if (existeAtributo(busqueda, tab1.getNombreTabla())) {
			
			reg = tab1.getRegistros();
			
		}
		
		if (existeAtributo(busqueda, tab2.getNombreTabla())) {
			
			reg = tab2.getRegistros();
		}
		for (int i=0; i<buscar.size(); i++) {
			boolean yaAgregado = false;
			Atributo atr = buscar.get(i);
			
			for(LinkedHashMap<String, Atributo> registro : reg) {
					
				if (registro.get(atr.getNombreAtributo()) instanceof Cadena) {
					
					Cadena cadena1 = (Cadena) registro.get(atr.getNombreAtributo());
					Cadena cad = (Cadena) atr;
						
					if(cad.getDato().equals(cadena1.getDato()) && yaAgregado==false) {
											
						if (registro.get(busqueda) instanceof Cadena) {
							
							Cadena ing = (Cadena)registro.get(busqueda);
							DTOCadena dto = new DTOCadena (ing);
							resultado.add(dto);
							yaAgregado=true;
						}
						
						if (registro.get(busqueda) instanceof Entero) {
							
							Entero ing = (Entero)registro.get(busqueda);
							DTOEntero dto = new DTOEntero (ing);
							resultado.add(dto);
							yaAgregado=true;
							
						}
					}
					
				}else{
					
					if (registro.get(atr.getNombreAtributo()) instanceof Entero) {
						Entero entero1 = (Entero) registro.get(atr.getNombreAtributo());
						Entero ent = (Entero) atr;
						
						if (ent.getValor() == entero1.getValor() && yaAgregado==false) {
							
							if (registro.get(busqueda) instanceof Cadena) {
								
								Cadena ing = (Cadena)registro.get(busqueda);
								DTOCadena dto = new DTOCadena (ing);
								resultado.add(dto);
								yaAgregado=true;
								
							}
							
							if (registro.get(busqueda) instanceof Entero) {
							
								Entero ing = (Entero)registro.get(busqueda);
								DTOEntero dto = new DTOEntero (ing);
								resultado.add(dto);
								yaAgregado=true;
								
							}
						}		
						
					}
				}
			}
					
		}

		return resultado;
		
	}
	
	private boolean existeAtributo(String atributo, String nombreTabla) {
		
		if (usuarios.get(usuario).getBasesDatos().get(baseDatos).getTablas().get(nombreTabla).getRegistros().get(0).get(atributo) == null) {
		
			return false;
			
		}else {
			
			return true;
			
		}
	}

	// Método para verificar si dos registros tienen atributos con el mismo nombre
	private boolean repiteAtributo(Atributo atr1, Atributo atr2) {
		
		if(atr1.getNombreAtributo().equals(atr2.getNombreAtributo())) {
		
			if (atr1 instanceof Cadena && atr2 instanceof Cadena) {
				
				Cadena cadena1 = (Cadena) atr1;
				Cadena cadena2 = (Cadena) atr2;
				
				return (cadena1.getDato().equals(cadena2.getDato()));
				
			}else if (atr1 instanceof Entero && atr1 instanceof Entero) {
			
				Entero entero1 = (Entero) atr1;
				Entero entero2 = (Entero) atr2;
				
				return (entero1.getValor()==entero2.getValor());
				
			}
		}else {
			
			return false;
			
		}
		
		return false;
		
	}
	
	public ArrayList<String> describeTabla (String nombreTabla){

		ArrayList<String> resultado = new ArrayList<String>();
		Tabla tablita = this.obtenerTabla(nombreTabla);

		LinkedHashMap <String, Atributo> atributos = tablita.getRegistros().get(0);

		for (Map.Entry<String, Atributo> entry : atributos.entrySet()) {

			String insertar="";
			String nombre = entry.getKey();
			String clave="";
			String tipo="";
			String notNull="";

			if(entry.getValue().getClave()==true)
				clave = "Clave primaria";
			else
				clave = "No es clave primaria";

			if (entry.getValue() instanceof Cadena)
				tipo="Cadena";
			else
				tipo="Entero";

			if(entry.getValue().getNulo()==true)
				notNull="No es nulo";
			else
				notNull="Nulo";

			insertar = nombre + " - " + tipo + " - " + clave + " - " + notNull;
			resultado.add(insertar);

		}
		
		return resultado;
	}
}
