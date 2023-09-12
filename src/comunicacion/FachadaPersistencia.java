package comunicacion;

import persistencia.Persistencia;

public class FachadaPersistencia implements IFachadaPersistencia {
	
	 Persistencia persistencia;
	 
	 public FachadaPersistencia() {
			
		persistencia = new Persistencia();
			
	}
	
}
