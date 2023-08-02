package logica;

import comunicacion.IFachadaLogica;
import comunicacion.IFachadaPersistencia;
import persistencia.FachadaPersistencia;

public class FachadaLogica implements IFachadaLogica {

	IFachadaPersistencia persitencia = new FachadaPersistencia();
	
}
