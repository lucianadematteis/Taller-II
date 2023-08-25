package persistencia;

import java.util.StringTokenizer;//tokenizador
import java.io.FileWriter;//escritura
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Persistencia {

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
	
}
