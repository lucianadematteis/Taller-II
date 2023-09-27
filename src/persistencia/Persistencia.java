package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;//escritura
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import logica.Atributo;
import logica.BaseDatos;
import logica.Cadena;
import logica.Entero;
import logica.Tabla;
import logica.Usuario;

/**
 * En esta clase se definen en los metodos persistirUsuario, persistirBasesDeDatos y persistirTablas los separadores
 *@author Gabriel Bolsi
 *@author Brandon Cairus
 *@author Daiana Daguerre
 *@author Luciana De Matteis
 *@author Mauricio Gonzalez
 *
 */
public class Persistencia {

	/**
	 * Metodo privado que recibe como parametro el nombre de una carpeta y su ruta. El metodo crea una carpeta en la ubicacion especificada si aun no existe y retorna true si la carpeta se crea con exito, o false si ya existe o no se puede crear.
	 * @param nombreCarpeta-> Nombre de la carpeta
	 * @param ruta-> Ruta de la carpeta
	 * @return verifica si se puede crear o si de lo contarrio ya existe o tuvo algun problema al crearse
	 */
	private boolean crearCarpeta(String nombreCarpeta, String ruta) { //Retorna true si tiene exito
		
		File carpeta = new File(ruta + File.separator + nombreCarpeta);
		
		if (carpeta.exists()) {
			
			return false;
			
		}else if (carpeta.mkdir()){
		     
			return true;
			
		} else {
		
			return false;
		
		}
		
	}
	
	/*
	 * Metodo privado que crea una carpeta inicial llamada 'Sistema' en el escritorio del usuario.
	 */
	private void crearCarpetaInicial() {
		
		String nombreArchivo="";
		int sistema = identificarSistema();
	    
		if (sistema == 1) { // Si es Windows
        	
            nombreArchivo = System.getProperty("user.home") + "\\Desktop\\";
            
        } else if (sistema == 0) { // Si es Linux
            
        	nombreArchivo = System.getProperty("user.home") + "//Desktop//";
       
        }
	    
		crearCarpeta("Sistema", nombreArchivo);
		
	}
	
	/**
	 * Metodo privado que identifica el sistema operativo en el que se esta ejecutando la aplicacion. 
	 * @return segun el sistema operativo, 0-> Linux, 1-> Windows
	 */
	private int identificarSistema() {
		
		String so = System.getProperty("os.name").toLowerCase();
        
        if (so.contains("win")) {
        	
            return 1;
            
        } else if (so.contains("nix") || so.contains("nux")) {
        	
            return 0;
            
        } else {
        	
        	return -1;
        }
		
	}
	
	/**
	 * Metodo privado que retorna en una cadena de caracteres la ruta completa del archivo de ayuda en funcion del sistema operativo detectado.
	 * @return la ruta donde se guardara la ayuda
	 */
	private String obtenerRutaAyuda() {
		
		String nombreArchivo="";
	    
		if (identificarSistema() == 1) { // Si es Windows
        	
            nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\Ayuda.txt";
        
        } else if (identificarSistema() == 0) { // Si es Linux
            
        	nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//Ayuda.txt";
       
        }
	     
	    return nombreArchivo;
		
	}
	
	/**
	 * Metodo privado que recibe tres parametros: el nombre del usuario, el nombre de la base de datos y el nombre de la tabla. El metodo obtiene la ruta donde se guardaran los registros de una tabla especifica y retorna una cadena de texto que representa la misma.
	 * @param nombreUsuario-> nombre de usuario
	 * @param nombreBD-> nombre de base de datos
	 * @param nombreTabla-> nombre de la tabla
	 * @return la ruta donde se guardara el archivo Registros.txt segun el sistema operativo
	 */
	private String obtenerRutaRegistro(String nombreUsuario, String nombreBD, String nombreTabla) {
		
		String nombreArchivo="";
	    
		if (identificarSistema() == 1) { // Si es Windows
        	
            nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\" + nombreUsuario + "\\" + nombreBD + "\\" + nombreTabla + "\\" + "Registros.txt";
        
        } else if (identificarSistema() == 0) { // Si es Linux
            
        	nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//" + nombreUsuario + "//" + nombreBD + "//" + nombreTabla + "//" + "Registros.txt";
       
        }
	     
	    return nombreArchivo;
	    
	}
	
	/**
	 * Metodo privado que obtiene la ruta donde se guardaran los datos de los usuarios y retorna una cadena de texto que representa la misma
	 * @return la ruta donde se guardara el archivo Usuarios.txt segun el sistema operativo
	 */
	private String obtenerRutaUsuarios() {
		
		String nombreArchivo = "";

		if (identificarSistema() == 1) { // Si es Windows
			
			nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\Usuarios.txt";
			
		} else if (identificarSistema() == 0) { // Si es Linux
			
			nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//Usuarios.txt";
			
		}

		return nombreArchivo;
		
	}
	
	/**
	 * Metodo privado que recibe por parametro el nombre del usuario. El metodo obtiene la ruta donde se guardaran los datos de las bases de de datos de ese usuario y retorna una cadena de texto que representa la misma.
	 * @param nombreUsuario-> nombre de usuario
	 * @return la ruta donde se guardara el archivo BaseDeDatos.txt segun el sistema operativo
	 */
	private String obtenerRutaBD(String nombreUsuario) {

		String nombreArchivo = "";

		if (identificarSistema() == 1) { // Si es windows

			nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\" + nombreUsuario + "\\"
					+ "BasesDeDatos.txt"; 

		} else if (identificarSistema() == 0) { // Si es linux

			nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//" + nombreUsuario + "//"
					+ "BasesDeDatos.txt";

		}

		return nombreArchivo;

	}
	
	/**
	 * Metodo privado que recibe los siguientes parametros: el nombre del usuario, el nombre de la base de datos y el nombre de la tabla. El metodo obtiene la ruta donde se guardara la informacion de la estructura de la tabla y retorna una cadena de texto que representa la misma.
	 * @param nombreUsuario-> nombre de usuario
	 * @param nombreBD-> nombre de base de datos
	 * @param nombreTabla-> nombre tabla
	 * @return  ruta donde se guardara el archivo Tablas.txt segun el sistema operativo
	 */
	private String obtenerRutaTabla(String nombreUsuario, String nombreBD, String nombreTabla) {
		
		String nombreArchivo="";
	    
		if (identificarSistema() == 1) { // Si es Windows
        	
            nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\" + nombreUsuario + "\\" + nombreBD + "\\" + "Tablas.txt";
        
        } else if (identificarSistema() == 0) { // Si es Linux
            
        	nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//" + nombreUsuario + "//" + nombreBD + "//" + "Tablas.txt";
       
        }
	     
	    return nombreArchivo;
	}
	
	/**
	 * Metodo privado que obtiene la ruta del archivo "demo.txt" en el escritorio del usuario, dependiendo del sistema operativo (Windows o Linux).
	 * @return La ruta completa del archivo "demo.txt".
	 */
	private String obtenerRutaDemo() {
		
		String nombreArchivo="";
	    
		if (identificarSistema() == 1) { // Si es Windows
        	
            nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\demo.txt";
        
        } else if (identificarSistema() == 0) { // Si es Linux
            
        	nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//demo.txt";
       
        }
	     
	    return nombreArchivo;
		
	}

	/**
	 * Este metodo privado cifra o descifra una cadena de texto utilizando un cifrado Cesar con un desplazamiento de 5 caracteres.
	 * @param cadena -> La cadena de texto a cifrar o descifrar.
	 * @param accion -> Indica si se debe cifrar (true) o descifrar (false) la cadena.
	 * @return La cadena cifrada o descifrada.
	 */
	private String metodoCifrarDescifrar(String cadena, boolean accion) {

	    List<Character> abecedario = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
	            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0',
	            '1', '2', '3', '4', '5', '6', '7', '8', '9');

	    String cadenaFinal = "";

	    for (int i = 0; i < cadena.length(); i++) {

	        char caracter = cadena.charAt(i);

	        int indiceViejo = abecedario.indexOf(Character.toLowerCase(caracter));
	        int indiceNuevo = 0;

	        if (indiceViejo != -1) { // Verifica si el carácter esta en el abecedario

	            if (accion) { // Si quiere cifrar

	                indiceNuevo = (indiceViejo + 5) % abecedario.size();

	            } else { // Si quiere descifrar

	                indiceNuevo = (indiceViejo - 5) % abecedario.size();

	            }

	            if (indiceNuevo < 0) {
	            	
	                indiceNuevo += abecedario.size();
	                
	            }

	            if (Character.isUpperCase(caracter)) {
	            	
	                cadenaFinal += Character.toUpperCase(abecedario.get(indiceNuevo));
	           
	            } else {
	               
	            	cadenaFinal += abecedario.get(indiceNuevo);
	            
	            }

	        } else {
	        	
	            cadenaFinal += caracter; // Si el caracter no esta en el abecedario, lo aniade tal como esta
	        
	        }
	        
	    }

	    return cadenaFinal;
	    
	}
	
	/**
	 * Metodo privado que recibe los siguientes parametros: un objeto de la clase Usuario que contiene la informacion del usuario y un objeto FileWriter asociado al archivo donde se guardaran los usuarios. El metodo escribe la informacion del usuario en el archivo de usuarios
	 * @param usuario-> informacion del usuario
	 * @param archivo-> archivo donde se guardaran los usuarios
	 */
	private void persistirUsuario(Usuario usuario, FileWriter archivo) {
		
		String contraCifrada = this.metodoCifrarDescifrar(usuario.getContrasenia(), true);
	    String infoUsuario = usuario.getNombreUser() + ":" + contraCifrada;
	    
	    if(identificarSistema()==1) {
			 
            crearCarpeta(usuario.getNombreUser(), System.getProperty("user.home") + "\\Desktop\\Sistema\\" );
          
		 } else {
			 
            crearCarpeta(usuario.getNombreUser(), System.getProperty("user.home") + "//Desktop//Sistema//" );
            
		 }
	    
	    for (Map.Entry<String, BaseDatos> bdEntry : usuario.getBasesDatos().entrySet()) {
	    	
	        infoUsuario += ":" + bdEntry.getValue().getNombreBD(); // Agregar nombres de las bases de datos
	    
	    }
	    
	    infoUsuario += "|";
	    
	    try {
	    	
	        archivo.write(infoUsuario + "\n");
	        
	    } catch (IOException e) {
	    	
	        e.printStackTrace();
	        
	    }
	    
	}
	
	/**
	 * Metodo privado que recibe como parametro un mapa de usuarios y los persiste a todos los usuarios en el archivo de usuarios.
	 * @param usuarios-> mapa de usuarios
	 */
	private void persistirUsuariosTotales(LinkedHashMap<String, Usuario> usuarios) {
		
	    String ruta = obtenerRutaUsuarios();
	    
	    try (FileWriter archivo = new FileWriter(ruta)) {
	    	
	        for (Map.Entry<String, Usuario> usuarioEntry : usuarios.entrySet()) {
	        	
	            Usuario usuario = usuarioEntry.getValue();
	            persistirUsuario(usuario, archivo);
	            
	        }
	        
	    } catch (IOException e) {
	    	
	        e.printStackTrace();
	        
	    }
	    
	}

	/**
	 * Metodo privado que recibe como parametros un mapa de bases de datos y el nombre del usuario al que pertenecen las bases de datos. El metodo persiste las bases de datos en el archivo de texto asociado al usuario.
	 * @param BasesDatos -> mapa de bases de datps
	 * @param nombreUsuario-> nombre de usuarios para saber a quien pertenecen las bases de datos
	 */
	private void persistirBasesDeDatos(Map<String, BaseDatos> BasesDatos, String nombreUsuario) {

		String nombreArchivo = obtenerRutaBD(nombreUsuario);
		StringBuilder insertar = new StringBuilder();
		
		try (FileWriter archivo = new FileWriter(nombreArchivo)) {
			
			for (Map.Entry<String, BaseDatos> entry : BasesDatos.entrySet()) {
				
				BaseDatos baseDatos = entry.getValue();
				String nombreBase = baseDatos.getNombreBD();
				Map<String, Tabla> tablas = baseDatos.getTablas();
				insertar.append(nombreBase);
				
				if (identificarSistema() == 1) {
					
					crearCarpeta(nombreBase, System.getProperty("user.home") + "\\Desktop\\Sistema\\" + nombreUsuario);
				
				} else {
					
					crearCarpeta(nombreBase, System.getProperty("user.home") + "//Desktop//Sistema//" + nombreUsuario);
				
				}

				for (Map.Entry<String, Tabla> entry2 : tablas.entrySet()) {

					String nombreTabla = entry2.getKey();
					insertar.append(":" + nombreTabla);
					
				}

				insertar.append("|"); // Separador de salto de linea
				String ingreso = insertar.toString();
				archivo.write(ingreso + "\n");
				insertar.setLength(0);

			}
			
			archivo.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * Metodo privado que recibe como parametros un mapa de usuarios y persiste todas las bases de datos de todos los usuarios.
	 * @param -> mapa de usuarios
	 */
	private void persistirBasesDatosTotales(LinkedHashMap<String, Usuario> usuarios) {
		
		for (Map.Entry<String, Usuario> usuario : usuarios.entrySet()) {

			Usuario user = usuario.getValue();
				
			persistirBasesDeDatos(user.getBasesDatos(), user.getNombreUser());
				
		}

	}
	
	/**
	 * Metodo privado que recibe los siguientes parametros: un mapa de tablas, el nombre de la base de datos a la que pertenecen las tablas y el nombre del usuario al que pertenecen las tablas. El metodo persiste la estructura de las tablas en archivos asociados a las bases de datos y usuarios correspondientes
	 * @param tablas-> mapa de tablas
	 * @param nombreBase-> nombre de la base de datos
	 * @param nombreUsuario-> nombre del usuario
	 */
	private void persistirTablas(Map<String, Tabla> tablas, String nombreBase, String nombreUsuario){
		
		String nombreArchivo="";
		String ruta="";
		StringBuilder insertar = new StringBuilder();
		
		if (identificarSistema()==1) { //Si es windows
			
			nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\" + nombreUsuario + "\\" + nombreBase + "\\" + "Tablas.txt";
			ruta = System.getProperty("user.home") + "\\Desktop\\Sistema\\" + nombreUsuario + "\\" + nombreBase;
		
		}else if(identificarSistema()==0){ //Si es linux
			
			nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//" + nombreUsuario + "//" + nombreBase + "//" + "Tablas.txt";
			ruta = System.getProperty("user.home") + "//Desktop//Sistema//" + nombreUsuario + "//" + nombreBase;
		
		}
		
        try (FileWriter archivo = new FileWriter(nombreArchivo)) {
        	
            for (Map.Entry<String, Tabla> entry : tablas.entrySet()) {
            	
                String nombreTabla = entry.getKey();
                Tabla tabla = entry.getValue();
                insertar.append(nombreTabla + ":");
                
                LinkedHashMap<String, Atributo> atributos = new LinkedHashMap<String, Atributo>();
                atributos = tabla.getRegistros().get(0);
               
                for (Map.Entry<String, Atributo> atrib : atributos.entrySet()) {
    
            		String nombreAtributo = atrib.getKey();
                    Atributo atributo = atrib.getValue();
                    String tipo="";
                    String nulo="";
                    String clave="";
                  
                    if (atributo instanceof Cadena) {
		               
                    	tipo="Cadena";
                    	
		            } else if (atributo instanceof Entero) {
		            	
		            	tipo="Entero";
		          
		            }
                   
                    if (atributo.getNulo()) {
		             
                    	nulo="1";
                    	
		            } else if (atributo.getNulo()==false) {
		            
		            	nulo="0";
		          
		            }
                  
                    if (atributo.getClave()) {
		                clave="1";
                    	
		            } else if (atributo.getClave()==false) {
		          
		            	clave="0";
		            }
                    
                    insertar.append(nombreAtributo + ":"+ clave + ":" + nulo + ":" + tipo + ":");

                }
                
                insertar.deleteCharAt(insertar.length() - 1);
                insertar.append("|"); // Separador de salto de lÃ­nea
                String ingreso = insertar.toString();
                archivo.write(ingreso + "\n");
                insertar.setLength(0);
                crearCarpeta(nombreTabla, ruta);
                
            }
            
            archivo.close();
            
        } catch (IOException e) {
        	
            e.printStackTrace();
            
        }

	}
	
	/**
	 * Metodo privado que recibe como parametro un mapa de usuarios y persiste la estructura de todas las tablas de todos los usuarios y bases de datos
	 * @param usuarios-> mapa de usuarios
	 */
	private void persistirTablasTotales(LinkedHashMap<String, Usuario> usuarios) {

		for (Map.Entry<String, Usuario> usuario : usuarios.entrySet()) {

			Usuario user = usuario.getValue();

			for (Map.Entry<String, BaseDatos> baseDatos : user.getBasesDatos().entrySet()) {

				BaseDatos bd = baseDatos.getValue();

				if (!(user.getNombreUser().isEmpty() || bd.getNombreBD().isEmpty())) {
					
					Map<String, Tabla> tablas = bd.getTablas();
					persistirTablas(tablas,bd.getNombreBD(),user.getNombreUser());

				}

			}

		}

	}
	
	/**
	 * Metodo privado que recibe como parametros una lista de registros y un objeto para escribir en un archivo. El metodo persiste todos los registros de la lista en un archivo de texto, separando los valores de los atributos de cada registro con dos puntos (":") y separando los registros con un caracter "|" al final de cada registro.
	 * @param registros-> lista de registros
	 * @param archivo-> archivo de registros
	 */
	private void persistirRegistros(ArrayList<LinkedHashMap<String, Atributo>> registros, FileWriter archivo){
		
		boolean primerRegistro = true;
		
	    for (Map<String, Atributo> registro : registros) {
	    	
	    	if (primerRegistro) {
	    		
	    		primerRegistro = false;
	    		
            } else {
	    	
		        String registroFinal = ""; // Reiniciar para cada nuevo registro
		        int atributoActual = 0;
	
		        for (Map.Entry<String, Atributo> posicion : registro.entrySet()) {
		        	
		            Atributo atributo = posicion.getValue();
		            atributoActual++;
	
		            if (atributo instanceof Cadena) {
		            	
		                Cadena cadena = (Cadena) atributo;
		                String dato = cadena.getDato();
		                registroFinal += dato;
		                
		            } else if (atributo instanceof Entero) {
		            	
		                Entero entero = (Entero) atributo;
		                int valor = entero.getValor();
		                registroFinal += Integer.toString(valor);
		                
		            }
	
		            if (atributoActual < registro.size()) {
		            	
		                registroFinal += ":";
		                
		            }
		            
		        }
		        
		        registroFinal += "|"; // Agregar el caracter "|" al final de cada registro
	
		        try {
		        	
					archivo.write(registroFinal + "\n");
					
				} catch (IOException e) {
					
					
					e.printStackTrace();
					
				}
			       
		    }
	    	
	    }

	}
	
	/**
	 * "Recibe un parametro LinkedHashMap<String, Usuario> usuarios, que es un mapa de usuarios donde cada usuario tiene bases de datos y tablas asociadas. Lo que hace este metodo es iterar a traves de estos usuarios y sus bases de datos, y para cada tabla en cada base de datos, verifica si los nombres de usuario, base de datos, tabla y registros no estan vacios. Si no estan vacios, obtiene una ruta de archivo especifica para ese conjunto de datos y utiliza un objeto FileWriter para escribir los registros en un archivo en esa ubicacion. El metodo no retorna ningun valor ya que es de tipo void, simplemente realiza la tarea de persistir los registros de las tablas de los usuarios en archivos correspondientes en funcion de ciertas condiciones.
	 * @param usuarios-> mapa de usuarios
	 */
	private void persistirRegistrosTotales(LinkedHashMap<String, Usuario> usuarios) {

		for (Map.Entry<String, Usuario> usuario : usuarios.entrySet()) {

			Usuario user = usuario.getValue();

			for (Map.Entry<String, BaseDatos> baseDatos : user.getBasesDatos().entrySet()) {

				BaseDatos bd = baseDatos.getValue();

				for (Map.Entry<String, Tabla> tabla : bd.getTablas().entrySet()) {

					Tabla tablita = tabla.getValue();

					if (!(user.getNombreUser().isEmpty() || bd.getNombreBD().isEmpty()
							|| tablita.getNombreTabla().isEmpty() || tablita.getRegistros().isEmpty())) {

						String ruta = obtenerRutaRegistro(user.getNombreUser(), bd.getNombreBD(),
								tablita.getNombreTabla());
						
						try (FileWriter archivo = new FileWriter(ruta)) {
						
							ArrayList<LinkedHashMap<String, Atributo>> registros = tablita.getRegistros();
							persistirRegistros(registros, archivo);

						}catch (IOException e) {

							e.printStackTrace();

						}
						
					}

				}

			}

		}

	}
	
	/**
	 * Metodo publico que guarda un valor numerico en el archivo "demo.txt" ubicado en el escritorio del usuario, sobrescribiendo cualquier contenido existente en el archivo.
	 * @param value -> El valor numerico que se desea guardar en el archivo.
	 */
	public void persistirDemo(int value) {
	   
		String ruta = obtenerRutaDemo();  // Ruta del archivo
	
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
	   
	    	writer.write(String.valueOf(value));
	   
	    } catch (IOException e) {
	   
	    	System.err.println("Error al escribir en el archivo: " + e.getMessage());
	   
	    }
	
	}
	
	/**
	 * Metodo publico que recibe como parametro un mapa de usuarios. El metodo realiza una serie de llamadas a otros metodos privados para persistir informacion relacionada con usuarios, bases de datos, tablas y registros.
	 * @param usuarios-> mapa de usuarios
	 */
	public void persistirTodo(LinkedHashMap<String, Usuario> usuarios, int demo) {
		
		crearCarpetaInicial();
		persistirUsuariosTotales(usuarios);
		persistirBasesDatosTotales(usuarios);
		persistirTablasTotales(usuarios);
		persistirRegistrosTotales(usuarios);
		persistirDemo(demo);
							
	}

	/**
	 * Metodo privado que recibe como parametros un mapa de usuarios que se utiliza para almacenar los usuarios recuperados de un archivo. El metodo lee un archivo de texto que contiene informacion sobre usuarios, lo procesa y crea objetos Usuario a partir de los datos leidos. Luego, agrega estos usuarios a la coleccion que recibio como parametro y por ultimo la retorna.
	 * @param usuarios-> mapa de usarios
	 * @return los usuarios en un mapa recuperados del archivo Usuarios.txt
	 */
	private LinkedHashMap<String, Usuario> recuperarUsuarios(LinkedHashMap<String, Usuario> usuarios) {
	       
		try (BufferedReader br = new BufferedReader(new FileReader(obtenerRutaUsuarios()))) {
            
			String linea;
            
            while ((linea = br.readLine()) != null) {
            	
                String[] partes = linea.split(":");
                
                if (partes.length > 1) {
                	
                    String nombreUsuario = partes[0];
                    String contrasenia = partes[1];
                    
                    if (contrasenia.endsWith("|")) {
                        
                    	contrasenia = contrasenia.substring(0, contrasenia.length() - 1);
                        
                    }

                    String contraDecifrada = this.metodoCifrarDescifrar(contrasenia, false);
                    Usuario usuario = new Usuario(nombreUsuario, contraDecifrada);
                    usuarios.put(nombreUsuario, usuario);
                    
                }
                
            }
            
        } catch (FileNotFoundException e) {
        	
        	System.err.println("El archivo no se encontro: " + e.getMessage());
        	
        } catch (IOException g) {
        	
        	System.err.println("Error de lectura del archivo: " + g.getMessage());
        	
        }

        return usuarios;
    }
	
	/**
	 * Metodo privado que recibe como parametro una cadena de texto que indica la ubicacion del archivo que contiene informacion sobre bases de datos. El metodo lee el archivo de bases de datos, procesa la informacion y crea objetos BaseDatos con sus respectivas tablas y retorna una coleccion que las contiene.
	 * @param ruta-> ruta de BaseDeDatos.txt
	 * @return un mapa con las bases de datos
	 */
	private LinkedHashMap <String,BaseDatos> recuperarBasesDeDatos (String ruta){
		
    	LinkedHashMap <String, BaseDatos> bds = new LinkedHashMap <String, BaseDatos>();
		
    	try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
         
    		String linea;

            while ((linea = br.readLine()) != null) {
           
            	boolean primerElemento=false;
            	linea =linea.replace("|","");
                String[] partes = linea.split(":");
                String nombreBD = partes[0];
                BaseDatos bd = new BaseDatos(nombreBD);
         
                LinkedHashMap<String, Tabla> tabs = new LinkedHashMap<String, Tabla>(); 
              
                if (partes.length > 1) {
                
                	for (String tabla : partes) {
                   
                		if (primerElemento) {
                    	
                		Tabla tab = new Tabla (tabla);
                    	
                		tabs.put(tabla, tab);
                	
                		}
                	
                		primerElemento =true;
                	
                	}
                	
	                bd.setTablas(tabs);
	                bds.put(nombreBD, bd);
                
		        }else {
		             
		        	bds.put(nombreBD, bd);
		       
		        }
                
            }
            
        } catch (FileNotFoundException e) {
        	
        	System.err.println("El archivo no se encontro: " + e.getMessage());
        	
        } catch (IOException g) {
        	
        	System.err.println("Error de lectura del archivo: " + g.getMessage());
        	
        }
		
		return bds;
		
	}
   
	/**
	 * Metodo privado que recibe los siguientes parametros: cuatro cadenas de texto que representan el nombre del atributo, si puede ser nulo, si es clave primaria y el tipo de dato del atributo. El metodo crea y retorna un objeto Atributo (que puede ser de tipo Cadena o Entero) basado en los parametros recibidos.
	 * @param nombre-> nombre del atributo
	 * @param nulo-> si es nulo
	 * @param pk-> si es priamry key
	 * @param tipoDato-> valor del atributo
	 * @return el atributo del archivo atributos.txt
	 */
	private Atributo recuperarAtributo(String nombre, String nulo, String pk, String tipoDato) {
		
		Atributo resultado = null;
		
		boolean clave=pk.equals("1");
		boolean esNulo=nulo.equals("1");
		
		if(tipoDato.equals("Cadena")) {
			
			resultado = new Cadena(nombre, clave, esNulo, "");
			
		}else if(tipoDato.equals("Entero")){
			
			resultado = new Entero(nombre, clave, esNulo, 0);
			
		}
		
		return resultado;
		
	}
	
	/**
	 * Metodo privado que recibe los siguientes argumentos: nombre de una tabla y una cadena que indica la ubicacion del archivo que contiene la informacion de la tabla. El metodo lee el archivo de la tabla, procesa la informacion y crea objetos Atributo para representar los atributos de la tabla. Luego, agrega estos atributos a una coleccion que representa la estructura de la tabla y la retorna.
	 * @param nombreTabla-> nombre de la tabla
	 * @param ruta-> ruta del archivo Tabla.txt
	 * @return el mapa de tablas del archivo Tablas.txt
	 */
	private LinkedHashMap<String, Atributo> recuperarTabla(String nombreTabla, String ruta){
		
		LinkedHashMap<String, Atributo> resultado = new LinkedHashMap<String, Atributo>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
		    
			String linea;
			
            while ((linea = br.readLine()) != null) {
            	
                String[] partes = linea.split(":");
                
                if (partes[0].equals(nombreTabla)) {
                	
                	for (int i = 1; i < partes.length; i += 4) {
                		
                        String nombreAtributo = partes[i];
                        String nulo = partes[i + 1];
                        String pk = partes[i + 2];
                        String tipo = partes[i + 3];
                        tipo =tipo.replace("|","");
                        
                        Atributo atributoResultante=recuperarAtributo(nombreAtributo, nulo, pk, tipo);
                        
                        resultado.put(nombreAtributo, atributoResultante);
                        
                    }
               
                }
                
            }
		    
		} catch (FileNotFoundException e) {
        	
        	System.err.println("El archivo no se encontro: " + e.getMessage());
        	
        } catch (IOException g) {
        	
        	System.err.println("Error de lectura del archivo: " + g.getMessage());
        	
        }

		return resultado;
		
	}
		 	
	/**
	 * Metodo privado que recibe una cadena que indica la ubicacion del archivo que contiene los registros de la tabla y una coleccion que representa la estructura de la tabla. El metodo lee el archivo de registros, procesa la informacion y crea listas de registros representados por LinkedHashMaps que asocian nombres de atributos con sus valores. Por ultimo retorna una lista de LinkedHashMaps, donde cada LinkedHashMap representa un registro de la tabla, y los nombres de atributos se asocian con sus valores
	 * @param ruta-> ruta de registros.txt
	 * @param guia-> estructura de la tabla
	 * @return-> mapa de atributos recuperado de registros.txt
	 */
	private ArrayList<LinkedHashMap<String, Atributo>> recuperarRegistros(String ruta, LinkedHashMap<String, Atributo> guia){
		
		ArrayList<LinkedHashMap<String, Atributo>> resultado = new ArrayList<LinkedHashMap<String, Atributo>>();
		
		resultado.add(guia);
		
		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
		    
			String linea;

		    while ((linea = br.readLine()) != null) {
		    	
		    	LinkedHashMap<String, Atributo> registro = new LinkedHashMap<String, Atributo>(); // Nuevo registro en cada iteraciÃ¯Â¿Â½n
		        
		        String[] palabras = linea.split(":");
		        int index = 0;
		        
		        for (Entry<String, Atributo> guiaa : guia.entrySet()) {
		        	
		        	if (index < palabras.length) {
		        		
	                    String palabra = palabras[index++];
	                    
	                    if (palabra.endsWith("|")) {
	                    	
	                        palabra = palabra.substring(0, palabra.length() - 1);
	                        
	                    }
	                    
	                    if (guiaa.getValue() instanceof Cadena) {
	                    	
	                        // Crear y agregar un atributo de tipo Cadena al registro
	                        Cadena atri = new Cadena(guiaa.getKey(), guiaa.getValue().getNulo(), guiaa.getValue().getClave(), palabra);
	                        registro.put(guiaa.getKey(), atri);
	                        
	                    } else if (guiaa.getValue() instanceof Entero) {
	                    	
	                        // Crear y agregar un atributo de tipo Entero al registro
	                        Entero atri = new Entero(guiaa.getKey(), guiaa.getValue().getNulo(), guiaa.getValue().getClave(), Integer.parseInt(palabra));
	                        registro.put(guiaa.getKey(), atri);
	                        
	                    }
	                    
	                } 
		        	
	            }
		        
		        resultado.add(registro); // Agregar el registro al resultado
		        
		    }
		    
		} catch (FileNotFoundException e) {
        	
        	System.err.println("El archivo no se encontro: " + e.getMessage());
        	
        } catch (IOException g) {
        	
        	System.err.println("Error de lectura del archivo: " + g.getMessage());
        	
        }

		return resultado;
		
	}
	
	/**
	 * Metodo publico que lee un archivo de ayuda que contiene informacion sobre comandos y sus descripciones. Procesa esta informacion y la almacena en una coleccion donde los comandos son las claves y las descripciones son los valores, que por ultimo se retorna.
	 * @return realiza un mapa con el archivo ayuda.txt
	 */
	public LinkedHashMap<String, String> recuperarAyuda() {
		
		String ruta = obtenerRutaAyuda();
		
		StringBuilder contenidoArchivo = new StringBuilder();
	
		try (BufferedReader br =new BufferedReader(new InputStreamReader(new FileInputStream(ruta),"UTF-8"))) {
			
			String linea;
		
			while ((linea = br.readLine()) != null) {
			
				contenidoArchivo.append(linea);
		
			}
		
		} catch (FileNotFoundException e) {
        	
        	System.err.println("El archivo no se encontro: " + e.getMessage());
        	
        } catch (IOException g) {
        	
        	System.err.println("Error de lectura del archivo: " + g.getMessage());
        	
        }
		
		String contenidoString = contenidoArchivo.toString();
		String[] lineas = contenidoString.split("\\|");// separador
		int cantLineas = lineas.length;// obtener tamanioo del arreglo

		LinkedHashMap<String, String> cargado=new LinkedHashMap<String, String>();

		for (int i = 0; i < cantLineas; i++) {
			
			String[] comandos = lineas[i].split(":");
			cargado.put(comandos[0],comandos[1]);
			
		}
     
		return cargado;
		
	}
	
	/**
	 * Metodo publico que recupera un valor numerico almacenado en el archivo "demo.txt" ubicado en el escritorio del usuario.
	 * @return El valor numerico recuperado del archivo.
	 * @throws NumberFormatException Si el archivo no contiene un valor numerico valido.
	 */
	public int recuperarDemo() {
		
		String ruta = obtenerRutaDemo();
		
		StringBuilder contenidoArchivo = new StringBuilder();
	
		try (BufferedReader br =new BufferedReader(new InputStreamReader(new FileInputStream(ruta),"UTF-8"))) {
			
			String linea;
		
			while ((linea = br.readLine()) != null) {
			
				contenidoArchivo.append(linea);
		
			}
		
		} catch (FileNotFoundException e) {
        	
        	System.err.println("El archivo no se encontro: " + e.getMessage());
        	
        } catch (IOException g) {
        	
        	System.err.println("Error de lectura del archivo: " + g.getMessage());
        	
        }
		
		String contenidoDemo = contenidoArchivo.toString();
		int demo = Integer.parseInt(contenidoDemo);
  
		return demo;
		
	}

	/**
	 * Metodo publico que recibe como parametro una coleccion de usuarios. El metodo coordina la recuperacion de informacion relacionada con usuarios, bases de datos, tablas y registros llamando a otros metodos privados. Recorre los usuarios y sus respectivas bases de datos y tablas para cargar la informacion de manera completa
	 * @param usuarios-> mapa de usuarios
	 */
	public void recuperarTodo(LinkedHashMap<String, Usuario> usuarios) {

		recuperarUsuarios(usuarios);
		String ruta = "";
		
		for (Map.Entry<String, Usuario> usuario : usuarios.entrySet()) {

			Usuario user = usuario.getValue();
			ruta = obtenerRutaBD(user.getNombreUser());
			user.setBasesDatos(recuperarBasesDeDatos(ruta));
			
			for (Map.Entry<String, BaseDatos> bd : user.getBasesDatos().entrySet()) {

				BaseDatos base = bd.getValue();
				
				for (Map.Entry<String, Tabla> tabla : base.getTablas().entrySet()) {

					Tabla tablita = tabla.getValue();
					
					String rutaTabla= obtenerRutaTabla(user.getNombreUser(), base.getNombreBD(), tablita.getNombreTabla());

					if (!(user.getNombreUser().isEmpty() || base.getNombreBD().isEmpty() || tablita.getNombreTabla().isEmpty())) {

						ruta = obtenerRutaRegistro(user.getNombreUser(), base.getNombreBD(), tablita.getNombreTabla());
						LinkedHashMap<String, Atributo> guia = recuperarTabla(tablita.getNombreTabla(), rutaTabla);

						tablita.setRegistros(recuperarRegistros(ruta, guia));

					}

				}

			}

		}

	}
	
}