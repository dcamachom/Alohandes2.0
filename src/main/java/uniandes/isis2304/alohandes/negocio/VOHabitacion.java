package uniandes.isis2304.alohandes.negocio;

public interface VOHabitacion {
	
	/*
	 * Métodos
	 */
	
	public boolean getCompartida();
	
	public String getTipo ();
	
	public int getCapacidad();
	
	public long getId();
	
	@Override
	public String toString();
	
}
