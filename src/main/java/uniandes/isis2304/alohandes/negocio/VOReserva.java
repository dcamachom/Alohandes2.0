package uniandes.isis2304.alohandes.negocio;

import java.sql.Timestamp;
import java.util.List;

public interface VOReserva {

	/*
	 * Métodos
	 */
	public long getId();	

	public long getCliente();

	public long getIdInmueble();
	
	public Timestamp getFechaInicio();
	
	public Timestamp getFechaFin();
	
	public String getCancelado();

	public long getReservaColectiva();
	
	public List<Object[]> getServiciosUsados();
	
	public String toString();
}
