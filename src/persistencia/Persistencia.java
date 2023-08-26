package persistencia;

import java.io.File;
import java.io.FileWriter;//escritura
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import logica.Atributo;
import logica.Cadena;
import logica.Entero;

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
	
	public void persistirRegistros(ArrayList<Map<String, Atributo>> registros, String nombreUsuario, String nombreTabla, String nombreBD){
		
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
	
}
