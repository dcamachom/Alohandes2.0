package uniandes.isis2304.alohandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.alohandes.negocio.ReservaColectiva;

public class SQLReservaColectiva {
    /*
	 * Constantes
	 */
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	/*
	 * Atributos
	 */
	private PersistenciaAlohAndes pa;
	
	/*
	 * Métodos
	 */
	
	public SQLReservaColectiva(PersistenciaAlohAndes pa) {
		this.pa=pa;
	}
    
    public long adicionarReservaColectiva (PersistenceManager pm, long id, String tipoEvento, String tipoAlojamiento, int cantidad){
        Query q = pm.newQuery(SQL, "INSERT INTO " + pa.darTablaReservaColectiva () + "(Id, TipoEvento, TipoAlojamiento, Cantidad, Cancelado) values (?, ?, ?, ?, 'False')");
        q.setParameters(id, tipoEvento,tipoAlojamiento,cantidad);
        return (long) q.executeUnique();
    }

	public long eliminarReservaColectivaPorId(PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "UPDATE" + pa.darTablaReservaColectiva () + "SET CANCELADO='True' WHERE ID=?");
        q.setParameters(id);
        return (long) q.executeUnique();            
	}

	public ReservaColectiva darReservaColectivaPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pa.darTablaReservaColectiva () + " WHERE id = ?");
		q.setResultClass(ReservaColectiva.class);
		q.setParameters(id);
		return (ReservaColectiva) q.executeUnique();
	}

	public List<ReservaColectiva> darReservasColectivas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pa.darTablaReservaColectiva ());
		q.setResultClass(ReservaColectiva.class);
		return (List<ReservaColectiva>) q.executeList();
	}
}
