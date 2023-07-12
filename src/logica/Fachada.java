package logica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

public class Fachada {
	
	public List<String> comandosNivel1 = Arrays.asList("SHOW", "CREATE", "USE", "INSERT", "DELETE", "UPDATE", "NOTNULL", "SELECT",
			"COUNT", "AVG", "PRIMARYKEY", "DESCRIBE", "HELP", "JOINNATURAL", "MAX", "MIN");
	
	public List<String> comandosNivel2 = Arrays.asList("SET", "FROM", "WHERE", "TABLES", "DATABASE", "TABLE" , "VALUES");
	
	public List<String> operadoresLogicos = Arrays.asList("AND", "OR");
	
	public boolean validaCantidadArgumentos (ArrayList<String[]> sentencia, int posInicial, int posFinal, int cantArgumentos) {
		
		if(posInicial==posFinal) {
			
			return (sentencia.get(posInicial).length==cantArgumentos);
			
		}else {
		
			for (int i = posInicial; i < posFinal; i++) {
				
			    if(sentencia.get(i).length!=cantArgumentos) {
			    	
			    	return false;
			    	
			    }
			   
			}
		}
		
		return true;
		
	}
	
	public boolean validaTipoDato (String tipo) { 
		
		return ((tipo.toUpperCase().equals("CADENA")) || (tipo.toUpperCase().equals("ENTERO")));
		
	}
	
	public boolean validaTiposAtributos(ArrayList<String[]> sentencia, int posInicial, int posFinal) {
		
		for (int j = posInicial; j < posFinal; j++) {
			
			if(!(validaTipoDato(sentencia.get(j)[1]))) {
				
				return false;
				
			}
			
		}
		
		return true;
		
	}
	
	public boolean validaCantidadLineas(ArrayList<String[]> sentencia, int min, int max) {
		
		if(min==max) {
			
			return (sentencia.size()==min);
			
		}else {
		
			return ((sentencia.size()>=min) && (sentencia.size()<=max));
			
		}
		
		
	}
	
	public boolean validaSentenciasUnaLinea(ArrayList<String[]> sentencia) {
		
    	if(!(validaCantidadLineas(sentencia, 1, 1))){
        	
    		JOptionPane.showMessageDialog(null, "La cantidad de lineas ingresada es incorrecta", "ERROR", JOptionPane.ERROR_MESSAGE);
    		
    	}else {
    		
    		if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) {
    			
    			JOptionPane.showMessageDialog(null, "Cantidad de argumentos no valida", "ERROR", JOptionPane.ERROR_MESSAGE);
        		
    		}else {
    		
            	return true;
    			
    		}
    		
    	}
		
		return false;
		
	}
	
	public boolean validaSentenciasDosLineas(ArrayList<String[]> sentencia) {
		
    	if(!(validaCantidadLineas(sentencia, 2, 2))){
        	
    		JOptionPane.showMessageDialog(null, "La cantidad de lineas ingresada es incorrecta", "ERROR", JOptionPane.ERROR_MESSAGE);
    		
    	}else {
    		
    		if (!(validaCantidadArgumentos(sentencia, 0, sentencia.size(), 2))) {
    			
    			JOptionPane.showMessageDialog(null, "Cantidad de argumentos no valida", "ERROR", JOptionPane.ERROR_MESSAGE);
        		
    		}else {
    			
    			if(!(sentencia.get(1)[0].toUpperCase().equals("FROM"))) {
    				
    				JOptionPane.showMessageDialog(null, "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
            		
    			}else {
    				
    				return true;
    				
    			}
    		
    		}
    		
    	}
		
		return false;
		
	}
	
	public boolean validaSentenciasWhereComun(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadArgumentos(sentencia, 2, 2, 4))) {
			
			JOptionPane.showMessageDialog(null, "Cantidad de argumentos no valida en la linea 3", "ERROR", JOptionPane.ERROR_MESSAGE);
    		
		}else {
		
			if(!(sentencia.get(2)[0].toUpperCase().equals("WHERE"))) {
				
				JOptionPane.showMessageDialog(null, "El comando: " + sentencia.get(2)[0].toUpperCase() + " no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
	    		
			}else {
				
				if(!(sentencia.get(2)[2].toUpperCase().equals("="))) {
					
					JOptionPane.showMessageDialog(null, "El operador: " + sentencia.get(2)[2] + " no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
	        		
				}else {
					
					return true;
				
				}
			}
			
		}
		
		return false;
		
	}
	
	public boolean validaSentenciasFrom(ArrayList<String[]> sentencia) {
		
		if (!(validaCantidadArgumentos(sentencia, 1, 1, 2))) {
			
			JOptionPane.showMessageDialog(null, "Cantidad de argumentos no valida en las linea 2", "ERROR", JOptionPane.ERROR_MESSAGE);
    		
		}else {
			
			if(!(sentencia.get(1)[0].toUpperCase().equals("FROM"))) {
				
				JOptionPane.showMessageDialog(null, "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
        		
			}else {
				
				return true;
				
			}
		}
		
		return false;
		
	}
	
	public boolean validaSentenciasFromWhere(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadLineas(sentencia, 3, 3))){
        	
    		JOptionPane.showMessageDialog(null, "La cantidad de lineas ingresada es incorrecta", "ERROR", JOptionPane.ERROR_MESSAGE);
    		
    	}else {
    		
    		if (validaSentenciasFrom(sentencia)) {
    			
    			if(validaSentenciasWhereComun(sentencia)) {
	    				
	    			return true;
	    			
    			}
    		
    		}
    		
    	}
		
		return false;
		
	}
	
	public boolean validaOperadoresLogicos(ArrayList<String[]> sentencia) {
		
		if(!(validaCantidadArgumentos(sentencia, 2, 2, 8))) {
			
			JOptionPane.showMessageDialog(null, "Cantidad de argumentos no valida en la linea 3", "ERROR", JOptionPane.ERROR_MESSAGE);
    		
		}else {
			
			if(!(sentencia.get(2)[2].toUpperCase().equals("=")) || !(sentencia.get(2)[6].toUpperCase().equals("="))) {
				
				JOptionPane.showMessageDialog(null, "El operador de igualdad no valido en linea 3", "ERROR", JOptionPane.ERROR_MESSAGE);
        		
			}else {
				
				return true;
			
			}
			
		}
		
		return false;
		
	}
	
}
