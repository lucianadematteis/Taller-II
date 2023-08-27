package persistencia;

import java.io.File;
import java.io.FileWriter;//escritura
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import logica.Atributo;
import logica.BaseDatos;
import logica.Cadena;
import logica.Entero;
import logica.Tabla;
import logica.Usuario;

public class Persistencia {

	public boolean crearCarpeta(String nombreCarpeta, String ruta) { //Retorna true si tiene exito
		
		File carpeta = new File(ruta + File.separator + nombreCarpeta);
		
		if (carpeta.exists()) {
			
			return false;
			
		}else if (carpeta.mkdir()){
		     
			return true;
			
		} else {
		
			return false;
		
		}
		
	}
	
	public int identificarSistema() {
		
		String so = System.getProperty("os.name").toLowerCase();
        
        if (so.contains("win")) {
        	
            return 1;
            
        } else if (so.contains("nix") || so.contains("nux") || so.contains("mac")) {
        	
            return 0;
            
        } else {
        	
        	return -1;
        }
		
	}
	
	public void persistirRegistros(ArrayList<LinkedHashMap<String, Atributo>> registros, String nombreUsuario, String nombreBD, String nombreTabla){
		
		String nombreArchivo="";
		
		if (identificarSistema()==1) { //Si es windows
			
			nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\" + nombreUsuario + "\\" + nombreBD + "\\" + nombreTabla + "\\" + "Registros.txt";
			
		}else if(identificarSistema()==0){ //Si es linux
			
			nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//" + nombreUsuario + "//" + nombreBD + "//" + nombreTabla + "//" + "Registros.txt";
			
		}

		try {
			
		    FileWriter archivo = new FileWriter(nombreArchivo, true);

		    for (Map<String, Atributo> registro : registros) {
		    	
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

		        registroFinal += "|"; // Agregar el car�cter "|" al final de cada registro

		        archivo.write(registroFinal + "\n");
		    }

		    archivo.close();
		    
		} catch (IOException e) {
		    
			System.out.println("Ocurri� un error al persistir los datos en el archivo: " + e.getMessage());
		
		}

	}
	
	public void persistirRegistrosTotales(LinkedHashMap<String, Usuario> usuarios) {
		
		for (Map.Entry<String, Usuario> usuario : usuarios.entrySet()) {
			
			Usuario user = usuario.getValue();
			
			for (Map.Entry<String, BaseDatos> baseDatos : user.getBasesDatos().entrySet()) {
				
				BaseDatos bd = baseDatos.getValue();
				
				for (Map.Entry<String, Tabla> tabla : bd.getTablas().entrySet()) {
					
					Tabla tablita = tabla.getValue();
					
					this.persistirRegistros(tablita.getRegistros(), user.getNombreUser(), bd.getNombreBD(), tablita.getNombreTabla());
					
				}
				
			}
			
		}
		
	}
	public void persistirTablas(Map<String, Tabla> tablas, String nombreBase, String nombreUsuario){
		
		String nombreArchivo="";
		StringBuilder insertar = new StringBuilder();
		if (identificarSistema()==1) { //Si es windows
			nombreArchivo = System.getProperty("user.home") + "\\Desktop\\Sistema\\" + nombreUsuario + "\\" + nombreBase + "\\" + "Tablas.txt";
			
		}else if(identificarSistema()==0){ //Si es linux
			
			nombreArchivo = System.getProperty("user.home") + "//Desktop//Sistema//" + nombreUsuario + "//" + nombreBase + "//" + "Tablas.txt";
			
		}
		
        try (FileWriter archivo = new FileWriter(nombreArchivo)) {
            for (Map.Entry<String, Tabla> entry : tablas.entrySet()) {
                String nombreTabla = entry.getKey();
                Tabla tabla = entry.getValue();
                insertar.append(nombreTabla + ":");
                for (LinkedHashMap<String, Atributo> registro : tabla.getRegistros()) {//esta linea esta mal
                    for (Map.Entry<String, Atributo> atributoEntry : registro.entrySet()) {//esta linea esta mal
                        String nombreAtributo = atributoEntry.getKey();
                        Atributo atributo = atributoEntry.getValue();
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
                        
                        insertar.append(nombreAtributo + ":"+ nulo + ":" + clave + ":" + tipo + ":");
                        
                    }
                }
                insertar.deleteCharAt(insertar.length() - 1);
                insertar.append("|"); // Separador de salto de línea
                String ingreso = insertar.toString();
                archivo.write(ingreso + "\n");
                insertar.setLength(0);
                
            }
            archivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

}
	
}


