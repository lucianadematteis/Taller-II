package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;//escritura
import java.io.IOException;
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

public class Persistencia {

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
	
	private int identificarSistema() {
		
		String so = System.getProperty("os.name").toLowerCase();
        
        if (so.contains("win")) {
        	
            return 1;
            
        } else if (so.contains("nix") || so.contains("nux") || so.contains("mac")) {
        	
            return 0;
            
        } else {
        	
        	return -1;
        }
		
	}
	
	private String obtenerRutaAyuda() {
		
		String nombreArchivo="";
	    
		if (identificarSistema() == 1) { // Si es Windows
        	
            nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\Ayuda.txt";
        
        } else if (identificarSistema() == 0) { // Si es Linux
            
        	nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//Ayuda.txt";
       
        }
	     
	    return nombreArchivo;
		
	}
	
	private String obtenerRutaRegistro(String nombreUsuario, String nombreBD, String nombreTabla) {
		
		String nombreArchivo="";
	    
		if (identificarSistema() == 1) { // Si es Windows
        	
            nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\" + nombreUsuario + "\\" + nombreBD + "\\" + nombreTabla + "\\" + "Registros.txt";
        
        } else if (identificarSistema() == 0) { // Si es Linux
            
        	nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//" + nombreUsuario + "//" + nombreBD + "//" + nombreTabla + "//" + "Registros.txt";
       
        }
	     
	    return nombreArchivo;
	    
	}
	
	private String obtenerRutaUsuarios() {
		
		String nombreArchivo = "";

		if (identificarSistema() == 1) { // Si es Windows
			
			nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\Usuarios.txt";
			
		} else if (identificarSistema() == 0) { // Si es Linux
			
			nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//Usuarios.txt";
			
		}

		return nombreArchivo;
		
	}
	
	private String obtenerRutaBD(String nombreUsuario) {

		String nombreArchivo = "";

		if (identificarSistema() == 1) { // Si es windows

			nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\" + nombreUsuario + "\\"
					+ "BasesDeDatos.txt"; //Se acomodaron el nombre del doc bd

		} else if (identificarSistema() == 0) { // Si es linux

			nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//" + nombreUsuario + "//"
					+ "BasesDeDatos.txt";

		}

		return nombreArchivo;

	}
	
	private String obtenerRutaTabla(String nombreUsuario, String nombreBD, String nombreTabla) {
		
		String nombreArchivo="";
	    
		if (identificarSistema() == 1) { // Si es Windows
        	
            nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\" + nombreUsuario + "\\" + nombreBD + "\\" + "Tablas.txt";
        
        } else if (identificarSistema() == 0) { // Si es Linux
            
        	nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//" + nombreUsuario + "//" + nombreBD + "//" + "Tablas.txt";
       
        }
	     
	    return nombreArchivo;
	}

	private void persistirUsuario(Usuario usuario, FileWriter archivo) {
		
	    String infoUsuario = usuario.getNombreUser() + ":" + usuario.getContrasenia();
	    
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
	
	private void persistirBasesDatosTotales(LinkedHashMap<String, Usuario> usuarios) {
		
		for (Map.Entry<String, Usuario> usuario : usuarios.entrySet()) {

			Usuario user = usuario.getValue();
			
			//se comenta porque al recuperar si no existe el archivo da error entonces si es vacio lo crea vacio y no da error
		//	if (!(user.getBasesDatos().isEmpty())) {
				
				persistirBasesDeDatos(user.getBasesDatos(), user.getNombreUser());
				
			//}
			
		}

	}
	
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
	
	public void persistirTodo(LinkedHashMap<String, Usuario> usuarios) {
		
		crearCarpetaInicial();
		persistirUsuariosTotales(usuarios);
		persistirBasesDatosTotales(usuarios);
		persistirTablasTotales(usuarios);
		persistirRegistrosTotales(usuarios);
							
	}

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

                    Usuario usuario = new Usuario(nombreUsuario, contrasenia);
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
   
	private Atributo recuperarAtributo(String nombre, String nulo, String pk, String tipoDato) {
		
		Atributo resultado = null;
		
		boolean clave=pk.equals("1");
		boolean esNulo=nulo.equals("1");
		
		if(tipoDato.equals("Cadena")) {
			
			resultado = new Cadena(nombre, clave, esNulo, "");
			
		}else {
			
			resultado = new Entero(nombre, clave, esNulo, 0);
			
		}
		
		return resultado;
		
	}
	
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
	                        Cadena atri = new Cadena(palabra);
	                        registro.put(guiaa.getKey(), atri);
	                        
	                    } else if (guiaa.getValue() instanceof Entero) {
	                    	
	                        // Crear y agregar un atributo de tipo Entero al registro
	                        Entero atri = new Entero(Integer.parseInt(palabra));
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
	
	public LinkedHashMap<String, String> recuperarAyuda() {
		
		String ruta = obtenerRutaAyuda();
		
		StringBuilder contenidoArchivo = new StringBuilder();
		
		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
		
			String linea;
		
			while ((linea = br.readLine()) != null) {
		
				contenidoArchivo.append(linea);
				contenidoArchivo.append("\n");
		
			}
		
		} catch (FileNotFoundException e) {
        	
        	System.err.println("El archivo no se encontro: " + e.getMessage());
        	
        } catch (IOException g) {
        	
        	System.err.println("Error de lectura del archivo: " + g.getMessage());
        	
        }
		
		String contenidoString = contenidoArchivo.toString();
		String[] lineas = contenidoString.split("\\|");// separador
		int cantLineas = lineas.length;
		
		LinkedHashMap<String, String> cargado=new LinkedHashMap<String, String>();

		for (int i = 0; i < cantLineas-1; i++) {
		
			String[] comandos = lineas[i].split(":");
			cargado.put(comandos[0],comandos[1]);
			
		}

		return cargado;
		
	}

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