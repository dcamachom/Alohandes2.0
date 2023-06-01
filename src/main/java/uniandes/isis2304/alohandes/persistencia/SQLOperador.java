package uniandes.isis2304.alohandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.alohandes.negocio.Operador;

public class SQLOperador {
	
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
	
	public SQLOperador (PersistenciaAlohAndes pa) {
		this.pa=pa;
	}
	
	public long adicionarOperador(PersistenceManager pm, long id, String nombre, String tipoOperador) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pa.darTablaOperador () + "(id, nombre, tipoOperador) values (?, ?,?)");
        q.setParameters(id,nombre, tipoOperador);
        return (long) q.executeUnique();
	}
	
	public long eliminarOperadorPorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pa.darTablaOperador () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();            
	}
	
	public Operador darOperadorPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pa.darTablaOperador () + " WHERE id = ?");
		q.setResultClass(Operador.class);
		q.setParameters(id);
		return (Operador) q.executeUnique();
	}
	
	public List<Operador> darOperadores (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pa.darTablaOperador ());
		q.setResultClass(Operador.class);
		return (List<Operador>) q.executeList();
	}
	public List<Operador> consultarFuncionamientoOperadores1 (PersistenceManager pm, int semana)
	{
		String sql= "SELECT OPERADOR.* FROM "
		+pa.darTablaOperador()+" INNER JOIN (SELECT * FROM (SELECT OPERADOR, COUNT(*) AS CANT FROM "
		+pa.darTablaReserva()+" INNER JOIN "
		+pa.darTablaInmueble()+" ON RESERVA.INMUEBLE=INMUEBLE.ID WHERE TO_CHAR(FECHAINICIO, 'WW')=? OR TO_CHAR(FECHAFIN, 'WW')=? GROUP BY OPERADOR) WHERE CANT=(SELECT MIN(COUNT(*)) AS CANT FROM "
		+pa.darTablaReserva()+" INNER JOIN "
		+pa.darTablaInmueble()+" ON RESERVA.INMUEBLE=INMUEBLE.ID WHERE TO_CHAR(FECHAINICIO, 'WW')=? OR TO_CHAR(FECHAFIN, 'WW')=? GROUP BY OPERADOR))B ON OPERADOR.ID=B.OPERADOR";
		Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Operador.class);
		q.setParameters(semana, semana, semana, semana);
		return q.executeList();
	}
	public List<Operador> consultarFuncionamientoOperadores2 (PersistenceManager pm, int semana)
	{
		String sql= "SELECT OPERADOR.* FROM "
		+pa.darTablaOperador()+" INNER JOIN (SELECT * FROM (SELECT OPERADOR, COUNT(*) AS CANT FROM "
		+pa.darTablaReserva()+" INNER JOIN "
		+pa.darTablaInmueble()+" ON RESERVA.INMUEBLE=INMUEBLE.ID WHERE TO_CHAR(FECHAINICIO, 'WW')=? OR TO_CHAR(FECHAFIN, 'WW')=? GROUP BY OPERADOR) WHERE CANT=(SELECT MAX(COUNT(*)) AS CANT FROM "
		+pa.darTablaReserva()+" INNER JOIN "
		+pa.darTablaInmueble()+" ON RESERVA.INMUEBLE=INMUEBLE.ID WHERE TO_CHAR(FECHAINICIO, 'WW')=? OR TO_CHAR(FECHAFIN, 'WW')=? GROUP BY OPERADOR))B ON OPERADOR.ID=B.OPERADOR";
		Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Operador.class);
		q.setParameters(semana, semana, semana, semana);
		return q.executeList();
	}

}
