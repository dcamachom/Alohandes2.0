package uniandes.isis2304.alohandes.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.alohandes.negocio.Inmueble;

public class SQLInmueble {

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
	
	public SQLInmueble (PersistenciaAlohAndes pa) {
		this.pa=pa;
	}
	
	public long adicionarInmueble(PersistenceManager pm, long id,int costoBase, long idOperador, String estado, String tipo) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pa.darTablaInmueble () + "(id, costoBase, idOperador, estado, tipo) values (?, ?, ?, ?, ?)");
        q.setParameters(id, costoBase, idOperador, estado, tipo);
        return (long) q.executeUnique();
	}
	
	public long eliminarInmueblePorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pa.darTablaInmueble () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();            
	}
	
	public Inmueble darInmueblePorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pa.darTablaInmueble () + " WHERE id = ?");
		q.setResultClass(Inmueble.class);
		q.setParameters(id);
		return (Inmueble) q.executeUnique();
	}
	
	public List<Inmueble> darInmuebles (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pa.darTablaInmueble ());
		q.setResultClass(Inmueble.class);
		return (List<Inmueble>) q.executeList();
	}

	public List<Inmueble> darInmueblesDisponibles (PersistenceManager pm, String tipo, Timestamp fechaInicio, Timestamp fechaFin, String servicioDeseado)
	{
		String sql= "SELECT I.* FROM (SELECT * FROM "
		+pa.darTablaServicioInmueble()+ " LEFT OUTER JOIN "+pa.darTablaServicio() +" ON SERVICIO=SERVICIO.ID WHERE LOWER(NOMBRE)=LOWER(?)) S INNER JOIN (SELECT INMUEBLE.* FROM "+pa.darTablaInmueble() +" LEFT OUTER JOIN "+ pa.darTablaReserva() +" ON RESERVA.INMUEBLE=INMUEBLE.ID WHERE (FECHAINICIO>? OR FECHAFIN<? OR FECHAINICIO IS NULL) AND TIPO =? AND ESTADO='Habilitada')I ON S.INMUEBLE=I.ID ORDER BY S.INMUEBLE";
		Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Inmueble.class);
		q.setParameters(servicioDeseado, fechaInicio, fechaFin, tipo);
		return q.executeList();
	}
	public List<Inmueble> darInmueblesDisponiblesSinServicio (PersistenceManager pm, String tipo, Timestamp fechaInicio, Timestamp fechaFin)
	{
		String sql= "SELECT I.* FROM (SELECT * FROM "
		+pa.darTablaServicioInmueble()+ " LEFT OUTER JOIN "+pa.darTablaServicio() +" ON SERVICIO=SERVICIO.ID) S INNER JOIN (SELECT INMUEBLE.* FROM "+pa.darTablaInmueble() +" LEFT OUTER JOIN "+ pa.darTablaReserva() +" ON RESERVA.INMUEBLE=INMUEBLE.ID WHERE (FECHAINICIO>? OR FECHAFIN<? OR FECHAINICIO IS NULL) AND TIPO =? AND ESTADO='Habilitada')I ON S.INMUEBLE=I.ID ORDER BY S.INMUEBLE";
		Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Inmueble.class);
		q.setParameters(fechaInicio, fechaFin, tipo);
		return q.executeList();
	}
	public long deshabilitarInmueble(PersistenceManager pm, long id)
	{
		Query q= pm.newQuery(SQL, "UPDATE "+pa.darTablaInmueble()+" SET ESTADO='Deshabilitada' WHERE ID=?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}
	public long rehabilitarInmueble (PersistenceManager pm, long id)
	{
		Query q= pm.newQuery(SQL, "UPDATE "+pa.darTablaInmueble()+" SET ESTADO='Habilitada' WHERE ID=?");
		q.setParameters(id);
		return (long) q.executeUnique();  
	}
	public String darTipoInmueble (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT TIPO FROM " + pa.darTablaInmueble () + " WHERE id = ?");
		q.setResultClass(String.class);
		q.setParameters(id);
		return (String) q.executeUnique();
	}
	public List<Inmueble> consultarFuncionamientoInmuebles1 (PersistenceManager pm, int semana)
	{
		String sql= "SELECT INMUEBLE.* FROM "
		+pa.darTablaInmueble()+" INNER JOIN (SELECT * FROM (SELECT "
		+pa.darTablaInmueble()+", COUNT(*) AS CANT FROM "
		+pa.darTablaReserva()+" WHERE TO_CHAR(FECHAINICIO, 'WW')=? OR TO_CHAR(FECHAFIN, 'WW')=? GROUP BY INMUEBLE) WHERE CANT=(SELECT MIN(COUNT(*)) AS CANT FROM "
		+pa.darTablaReserva()+" WHERE TO_CHAR(FECHAINICIO, 'WW')=? OR TO_CHAR(FECHAFIN, 'WW')=? GROUP BY INMUEBLE))B ON INMUEBLE.ID=B.INMUEBLE";
		Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Inmueble.class);
		q.setParameters(semana, semana, semana, semana);
		return q.executeList();
	}
	public List<Inmueble> consultarFuncionamientoInmuebles2 (PersistenceManager pm, int semana)
	{
		String sql= "SELECT INMUEBLE.* FROM "
		+pa.darTablaInmueble()+" INNER JOIN (SELECT * FROM (SELECT "
		+pa.darTablaInmueble()+", COUNT(*) AS CANT FROM "
		+pa.darTablaReserva()+" WHERE TO_CHAR(FECHAINICIO, 'WW')=? OR TO_CHAR(FECHAFIN, 'WW')=? GROUP BY INMUEBLE) WHERE CANT=(SELECT MAX(COUNT(*)) AS CANT FROM "
		+pa.darTablaReserva()+" WHERE TO_CHAR(FECHAINICIO, 'WW')=? OR TO_CHAR(FECHAFIN, 'WW')=? GROUP BY INMUEBLE))B ON INMUEBLE.ID=B.INMUEBLE";
		Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Inmueble.class);
		q.setParameters(semana, semana, semana, semana);
		return q.executeList();
	}
}
