package uniandes.isis2304.alohandes.persistencia;

import java.sql.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uniandes.isis2304.alohandes.negocio.Apartamento;
import uniandes.isis2304.alohandes.negocio.Casa;
import uniandes.isis2304.alohandes.negocio.Cliente;
import uniandes.isis2304.alohandes.negocio.Habitacion;
import uniandes.isis2304.alohandes.negocio.Hostal;
import uniandes.isis2304.alohandes.negocio.Hotel;
import uniandes.isis2304.alohandes.negocio.Inmueble;
import uniandes.isis2304.alohandes.negocio.Operador;
import uniandes.isis2304.alohandes.negocio.PersonaNatural;
import uniandes.isis2304.alohandes.negocio.Reserva;
import uniandes.isis2304.alohandes.negocio.ReservaColectiva;
import uniandes.isis2304.alohandes.negocio.Servicio;
import uniandes.isis2304.alohandes.negocio.ServicioInmueble;
import uniandes.isis2304.alohandes.negocio.ServicioUsado;
import uniandes.isis2304.alohandes.negocio.VOInmueble;
import uniandes.isis2304.alohandes.negocio.VOReserva;
import uniandes.isis2304.alohandes.negocio.ViviendaUniversitaria;

public class PersistenciaAlohAndes {
	
	private static Logger log =Logger.getLogger(PersistenciaAlohAndes.class.getName());
	
	public final static String SQL = "javax.jdo.query.SQL";
	
	private static PersistenciaAlohAndes instance;
	
	private List <String> tablas;
	
	private SQLUtil sqlUtil;
	
	private PersistenceManagerFactory pmf;

	private SQLApartamento sqlApartamento;
	
	private SQLCasa sqlCasa;
	
	private SQLCliente sqlCliente;
	
	private SQLHabitacion sqlHabitacion;
	
	private SQLHostal sqlHostal;
	
	private SQLHotel sqlHotel;
	
	private SQLInmueble sqlInmueble;
	
	private SQLOperador sqlOperador;
	
	private SQLPersonaNatural sqlPersonaNatural;

    private SQLReservaColectiva sqlReservaColectiva;
	
	private SQLReserva sqlReserva;
	
	private SQLServicio sqlServicio;
	
	private SQLServicioInmueble sqlServicioInmueble;
	
	private SQLServicioUsado sqlServicioUsado;
	
	private SQLViviendaUniversitaria sqlViviendaUniversitaria;
	
	/*
	 * Métodos del manejador de persistencia
	 */
	
	private PersistenciaAlohAndes()
	{
		pmf= JDOHelper.getPersistenceManagerFactory("AlohAndes");
		crearClasesSQL();
		
		tablas= new LinkedList<String>();
		tablas.add("AlohAndes_sequence");
		tablas.add("APARTAMENTO");
		tablas.add("CASA");
		tablas.add("CLIENTE");
		tablas.add("HABITACION");
		tablas.add("HOSTAL");
		tablas.add("HOTEL");
		tablas.add("INMUEBLE");
		tablas.add("OPERADOR");
		tablas.add("PERSONANATURAL");
        tablas.add("RESERVACOLECTIVA");
		tablas.add("RESERVA");
		tablas.add("SERVICIO");
		tablas.add("SERVICIOINMUEBLE");
		tablas.add("SERVICIOUSADO");
		tablas.add("VIVIENDAUNIVERSITARIA");
		
	}
	
	private PersistenciaAlohAndes (JsonObject tableConfig)
	{
		crearClasesSQL();
		tablas= leerNombresTablas(tableConfig);
		
		String unidadPersistencia= tableConfig.get("unidadPersistencia").getAsString();
		log.trace("Accediendo unidad de persistencia: "+unidadPersistencia);
		pmf= JDOHelper.getPersistenceManagerFactory(unidadPersistencia);
	}
	
	public static PersistenciaAlohAndes getInstance()
	{
		if(instance==null)
		{
			instance=new PersistenciaAlohAndes();
		}
		return instance;
	}
	
	public static PersistenciaAlohAndes getInstance(JsonObject tableConfig)
	{
		if(instance==null)
		{
			instance=new PersistenciaAlohAndes(tableConfig);
		}
		return instance;
	}
	
	public void cerrarUnidadPersistencia()
	{
		pmf.close();
		instance=null;
	}
	
	private List<String> leerNombresTablas(JsonObject tableConfig)
	{
		JsonArray nombres= tableConfig.getAsJsonArray("tablas");
		List<String> resp= new LinkedList<String> ();
		for(JsonElement nom:nombres)
		{
			resp.add(nom.getAsString());
		}
		return resp;
	}
	
	private void crearClasesSQL() {
		sqlApartamento= new SQLApartamento(this);
		sqlCasa= new SQLCasa (this);
		sqlCliente= new SQLCliente (this);
		sqlHabitacion= new SQLHabitacion (this);
		sqlHostal= new SQLHostal(this);
		sqlHotel= new SQLHotel (this);
		sqlInmueble= new SQLInmueble (this);
		sqlOperador= new SQLOperador (this);
		sqlPersonaNatural= new SQLPersonaNatural (this);
        sqlReservaColectiva= new SQLReservaColectiva(this);
		sqlReserva= new SQLReserva (this);
		sqlServicio= new SQLServicio(this);
		sqlServicioUsado= new SQLServicioUsado (this);
		sqlViviendaUniversitaria= new SQLViviendaUniversitaria (this);
        sqlUtil= new SQLUtil(this);
	}
	
	public String darSeqAlohAndes() {
		return tablas.get(0);
	}
	
	public String darTablaApartamento() {
		return tablas.get(1);
	}
	
	public String darTablaCasa() {
		return tablas.get(2);
	}
	
	public String darTablaCliente() {
		return tablas.get(3);
	}
	
	public String darTablaHabitacion() {
		return tablas.get(4);
	}
	
	public String darTablaHostal() {
		return tablas.get(5);
	}
	
	public String darTablaHotel() {
		return tablas.get(6);
	}
	
	public String darTablaInmueble() {
		return tablas.get(7);
	}
	
	public String darTablaOperador() {
		return tablas.get(8);
	}
	
	public String darTablaPersonaNatural() {
		return tablas.get(9);
	}

    public String darTablaReservaColectiva() {
		return tablas.get(10);
	}
	
	public String darTablaReserva() {
		return tablas.get(11);
	}
	
	public String darTablaServicio() {
		return tablas.get(12);
	}
	public String darTablaServicioInmueble() {
		return tablas.get(13);
	}
	public String darTablaServicioUsado() {
		return tablas.get(14);
	}
	
	public String darTablaViviendaUniversitaria() {
		return tablas.get(15);
	}
	
	private long nextval ()
	{
		long resp = sqlUtil.nextval(pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	
	
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}
	
	/* ********************************************************
	 * Métodos para manejar los APARTAMENTOS
	 * ********************************************************
	 */
	
     public Apartamento adicionarApartamento (int costoBase, long idOperador, boolean amoblado, int cantHabitaciones, String estado, String tipo) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlApartamento.adicionarApartamento(pm, id, amoblado, cantHabitaciones);
            tx.commit();
            
            log.trace ("Inserción del apartamento: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Apartamento (id, idOperador, costoBase, amoblado, cantHabitaciones, estado, tipo);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarApartamentoPorId(long id) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlApartamento.eliminarApartamento(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<Apartamento> darApartamentos ()
	{
		return sqlApartamento.darApartamentos (pmf.getPersistenceManager());
	}
	
	public Apartamento darApartamentoPorId (long id)
	{
		return sqlApartamento.darApartamentoPorId (pmf.getPersistenceManager(), id);
	}
	
	/* *******************************
	 * Métodos para manejar las CASAS
	 *********************************/
	
     public Casa adicionarCasa(int costoBase, long idOperador, int cantHabitaciones, String seguro) 
     {
         PersistenceManager pm = pmf.getPersistenceManager();
         Transaction tx=pm.currentTransaction();
         try
         {
             tx.begin();            
             long id = nextval ();
             long tuplasInsertadas = sqlCasa.adicionarCasa(pm, id, cantHabitaciones, seguro);
             tx.commit();
             
             log.trace ("Inserción casa: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
             return new Casa (id, idOperador, costoBase, cantHabitaciones, seguro, "Habilitada", "Casa");
         }
         catch (Exception e)
         {
 //        	e.printStackTrace();
             log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
             return null;
         }
         finally
         {
             if (tx.isActive())
             {
                 tx.rollback();
             }
             pm.close();
         }
     }
	
	public long eliminarCasaPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlCasa.eliminarCasaPorId (pm, id);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<Casa> darCasas ()
	{
		return sqlCasa.darCasas (pmf.getPersistenceManager());
	}
	public Casa darCasaPorId (long id)
	{
		return sqlCasa.darCasaPorId (pmf.getPersistenceManager(), id);
	}
	/* **********************************
	 * Métodos para manejar los CLIENTES
	 * *********************************/

	public Cliente adicionarCliente(Long id, String nombre, String correo, String telefono, String tipoMiembro)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlCliente.adicionarCliente(pm, id, nombre, correo, telefono, tipoMiembro);
            System.out.println(nombre);
            tx.commit();
            
            log.trace ("Inserción del cliente: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Cliente (id, nombre, correo, telefono, tipoMiembro);
        }
        catch (Exception e)
        {
        	//e.printStackTrace();
            System.out.println("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarClientePorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlCliente.eliminarClientePorNombre(pm, nombre);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarClientePorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlCliente.eliminarClientePorId(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

    public List<Cliente> consultaConsumoV1(long inmueble, Timestamp fechaI, Timestamp fechaF, int opcion)
     {
        PersistenceManager pm = pmf.getPersistenceManager();
         if (opcion==0)
         {
            return sqlCliente.consultaConsumov1(pm, inmueble, fechaI, fechaF);
         }else if (opcion==1)
         {
            return sqlCliente.consultaConsumov1_0(pm, inmueble, fechaI, fechaF);
         }else if (opcion==2){
            return sqlCliente.consultaConsumov1_1(pm, inmueble, fechaI, fechaF);
         }else{
            return sqlCliente.consultaConsumov1_2(pm, inmueble, fechaI, fechaF);
         }
     }

     public List<Cliente> consultaConsumoV2(long inmueble, Timestamp fechaI, Timestamp fechaF, int opcion)
     {
        PersistenceManager pm = pmf.getPersistenceManager();
         if (opcion==0)
         {
            return sqlCliente.consultaConsumov2(pm, inmueble, fechaI, fechaF);
         }else if (opcion==1)
         {
            return sqlCliente.consultaConsumov2_0(pm, inmueble, fechaI, fechaF);
         }else if (opcion==2){
            return sqlCliente.consultaConsumov2_1(pm, inmueble, fechaI, fechaF);
         }else{
            return sqlCliente.consultaConsumov2_2(pm, inmueble, fechaI, fechaF);
         }
     }
	
	public List<Cliente> darClientes ()
	{
		return sqlCliente.darClientes (pmf.getPersistenceManager());
	}
	
	public Cliente darClientePorId (long idTipoBebida)
	{
		return sqlCliente.darClientePorId (pmf.getPersistenceManager(), idTipoBebida);
	}
	
	public Cliente darClientePorNombre (String nombre)
	{
		return sqlCliente.darClientePorNombre (pmf.getPersistenceManager(), nombre);
	}
	
	/* *****************************
	 * Método para las HABITACIONES
	 ******************************/

     public Habitacion adicionarHabitacion(int costoBase, long idOperador, int capacidad, boolean compartida, String tipo)
     {
         PersistenceManager pm = pmf.getPersistenceManager();
         Transaction tx=pm.currentTransaction();
         try
         {
             tx.begin();
             long id = nextval ();
             long tuplasInsertadas = sqlHabitacion.adicionarHabitacion(pm, id, capacidad, compartida,tipo);
             tx.commit();
             
             log.trace ("Inserción de la habitacion: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
             
             return new Habitacion (id, idOperador, costoBase, capacidad, compartida,tipo,"Habilitada");
         }
         catch (Exception e)
         {
 //        	e.printStackTrace();
             log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
             return null;
         }
         finally
         {
             if (tx.isActive())
             {
                 tx.rollback();
             }
             pm.close();
         }
     }
	
	public long eliminarHabitacionPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlHabitacion.eliminarHabitacionPorId(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<Habitacion> darHabitaciones ()
	{
		return sqlHabitacion.darHabitaciones (pmf.getPersistenceManager());
	}
	
	public Habitacion darHabitacionPorId (long id)
	{
		return sqlHabitacion.darHabitacionPorId (pmf.getPersistenceManager(), id);
	}
	
	/* *************************
	 * Método para los HOSTALES
	 ***************************/
	
	public Hostal adicionarHostal(String nombre, String tipoOperador, int cantHabitaciones, String registroCamaraComercio, String registroSuperIntendencia, Time horaApertura, Time horaCierre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlHostal.adicionarHostal(pm, id, registroCamaraComercio, registroSuperIntendencia, horaApertura, horaCierre);
            tx.commit();
            
            log.trace ("Inserción del hostal: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Hostal (id, nombre, tipoOperador, cantHabitaciones, registroCamaraComercio, registroSuperIntendencia, horaApertura, horaCierre);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarHostalPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlHostal.eliminarHostalPorId(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<Hostal> darHostales ()
	{
		return sqlHostal.darHostales (pmf.getPersistenceManager());
	}
	
	public Hostal darHostalPorId (long id)
	{
		return sqlHostal.darHostalPorId (pmf.getPersistenceManager(), id);
	}
	
	/* **************************
	 * Métodos para los HOTELES
	 *****************************/
	
	public Hotel adicionarHotel(String nombre, String tipoOperador, int cantHabitaciones, String registroCamaraComercio, String registroSuperIntendencia)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlHotel.adicionarHotel(pm, id, registroCamaraComercio, registroSuperIntendencia);
            tx.commit();
            
            log.trace ("Inserción del hotel: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Hotel (id, nombre, tipoOperador, cantHabitaciones, registroCamaraComercio, registroSuperIntendencia);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarHotelPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlHotel.eliminarHotelPorId(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<Hotel> darHoteles ()
	{
		return sqlHotel.darHoteles (pmf.getPersistenceManager());
	}
	
	public Hotel darHotelPorId (long id)
	{
		return sqlHotel.darHotelPorId (pmf.getPersistenceManager(), id);
	}
	
	/* ***************************
	 * Métodos para los inmuebles
	 *****************************/
	
     public Inmueble adicionarInmueble(int costoBase, long idOperador, String estado, String tipo)
     {
         PersistenceManager pm = pmf.getPersistenceManager();
         Transaction tx=pm.currentTransaction();
         try
         {
             tx.begin();
             long id = nextval ();
             long tuplasInsertadas = sqlInmueble.adicionarInmueble(pm, id, costoBase, idOperador, estado, tipo);
             tx.commit();
             
             log.trace ("Inserción del Inmueble: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
             
             return new Inmueble (id, idOperador, costoBase, estado, tipo);
         }
         catch (Exception e)
         {
 //        	e.printStackTrace();
             log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
             return null;
         }
         finally
         {
             if (tx.isActive())
             {
                 tx.rollback();
             }
             pm.close();
         }
     }
	
	public long eliminarInmueblePorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlInmueble.eliminarInmueblePorId(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
        	//e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<Inmueble> darInmuebles ()
	{
		return sqlInmueble.darInmuebles (pmf.getPersistenceManager());
	}
	
	public Inmueble darInmueblePorId (long id)
	{
		return sqlInmueble.darInmueblePorId (pmf.getPersistenceManager(), id);
	}

	/* **************************
	 * Métodos de los OPERADORES
	 ****************************/
	
	public Operador adicionarOperador(String nombre, String tipoOperador)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlOperador.adicionarOperador(pm, id, nombre, tipoOperador);
            tx.commit();
            
            log.trace ("Inserción del operador: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Operador (id,nombre, tipoOperador);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarOperadorPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOperador.eliminarOperadorPorId(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<Operador> darOperadores ()
	{
		return sqlOperador.darOperadores (pmf.getPersistenceManager());
	}
	
	public Operador darOperadorPorId (long id)
	{
		return sqlOperador.darOperadorPorId (pmf.getPersistenceManager(), id);
	}
	
	/*
	 * Métodos de las PERSONAS NATURALES
	 */
	
	public PersonaNatural adicionarPersonaNatural(String nombre, String tipoOperador, String correo, String telefono)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlPersonaNatural.adicionarPersonaNatural(pm, id, correo, telefono);
            tx.commit();
            
            log.trace ("Inserción de persona natural: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new PersonaNatural (id,nombre, tipoOperador, correo, telefono);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarPersonaNaturalPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlPersonaNatural.eliminarPersonaNaturalPorId(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<PersonaNatural> darPersonasNaturales ()
	{
		return sqlPersonaNatural.darPersonasNaturales (pmf.getPersistenceManager());
	}
	
	public PersonaNatural darPersonaNaturalPorId (long id)
	{
		return sqlPersonaNatural.darPersonaNaturalPorId (pmf.getPersistenceManager(), id);
	}
	
	/* ***************************
	 * Método para las RESERVAS
	 ****************************/
	
     public Reserva adicionarReserva(Timestamp fechaInicio, Timestamp fechaFin, long idCliente, long idInmueble, String cancelado, long reservaColectiva)
     {
         PersistenceManager pm = pmf.getPersistenceManager();
         Transaction tx=pm.currentTransaction();
         try
         {
             tx.begin();
             long id = nextval ();
             long tuplasInsertadas = sqlReserva.adicionarReserva(pm, id, idCliente, idInmueble, fechaInicio, fechaFin, cancelado, reservaColectiva);
             System.out.println("Reserva: "+id);
             tx.commit();
             
             log.trace ("Inserción del reserva: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
             
             return new Reserva (id,fechaInicio, fechaFin, idCliente, idInmueble, cancelado, reservaColectiva);
         }
         catch (Exception e)
         {
             //e.printStackTrace();
             log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
             return null;
         }
         finally
         {
             if (tx.isActive())
             {
                 tx.rollback();
             }
             pm.close();
         }
     }
 
     public List<Inmueble> inmueblesDisponibles(String tipo, Timestamp fechaInicio, Timestamp fechaFin, String servicioDeseado)
     {
         List<Inmueble> lista=sqlInmueble.darInmueblesDisponibles(pmf.getPersistenceManager(), tipo, fechaInicio, fechaFin, servicioDeseado);
         List<Inmueble> listaVo= new ArrayList<>();
         for (int i=0;i<lista.size();i++)
         {
             Inmueble vo=lista.get(i);
             listaVo.add(vo);
         }
         return listaVo ;
     }	
     public List<Inmueble> inmueblesDisponiblesSinServicio(String tipo, Timestamp fechaInicio, Timestamp fechaFin)
     {
         List<Inmueble> lista=sqlInmueble.darInmueblesDisponiblesSinServicio(pmf.getPersistenceManager(), tipo, fechaInicio, fechaFin);
         List<Inmueble> listaVo= new ArrayList<>();
         for (int i=0;i<lista.size();i++)
         {
             Inmueble vo=lista.get(i);
             listaVo.add(vo);
         }
         return listaVo ;
     }	
     public List<List<Long>> deshabilitarInmueble(long id, List<Reserva> reservas, String tipo)
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            List<List<Long>> resp= new LinkedList<List<Long>>();
            List<Long> noHechos= new LinkedList<Long>();
            List<Long> hechos= new LinkedList<Long>(); 
            for(Reserva r: reservas)
            {
                Timestamp fInicio=r.getFechaInicio();
                log.info(fInicio.toString());
                Timestamp fFin=r.getFechaFin();
                List<Inmueble> inmueblesDisp=inmueblesDisponiblesSinServicio(tipo, fInicio, fFin);
                if(inmueblesDisp.size()>=1)
                {
                    sqlReserva.actualizarReserva(pm, r.getId(), inmueblesDisp.get(0).getId());
                    hechos.add(r.getId());
                }
                else
                {
                    noHechos.add(r.getId());
                }
            }
            sqlInmueble.deshabilitarInmueble(pm, id);
            resp.add(hechos);
            resp.add(noHechos);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
        	//e.printStackTrace();
            System.out.println("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }
    public long rehabilitarInmueble(long id)
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp= sqlInmueble.rehabilitarInmueble(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
        	//e.printStackTrace();
            System.out.println("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }
    public List<Reserva> reservasPorInmueble(long inmueble)
    {
        List<Reserva> lista=sqlReserva.reservasPorInmueble(pmf.getPersistenceManager(), inmueble);
        List<Reserva> listaVo= new ArrayList<>();
         for (int i=0;i<lista.size();i++)
         {
             Reserva vo=lista.get(i);
             listaVo.add(vo);
         }
         return listaVo ;
    }
    public String darTipoInmueble (Long inmueble)
     {
         return sqlInmueble.darTipoInmueble(pmf.getPersistenceManager(), inmueble);
     }
     public long eliminarReservaPorId (long id) 
     {
         PersistenceManager pm = pmf.getPersistenceManager();
         Transaction tx=pm.currentTransaction();
         try
         {
             tx.begin();
             long resp = sqlReserva.eliminarReservaPorId(pm, id);
             tx.commit();
             return resp;
         }
         catch (Exception e)
         {
 //        	e.printStackTrace();
             log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
             return -1;
         }
         finally
         {
             if (tx.isActive())
             {
                 tx.rollback();
             }
             pm.close();
         }
     }
 
     public long eliminarReservaPorReservaColectiva (long id)
     {
         PersistenceManager pm = pmf.getPersistenceManager();
         Transaction tx=pm.currentTransaction();
         try
         {
             tx.begin();
             long resp = sqlReserva.eliminarReservaPorReservaColectiva(pm, id);
             tx.commit();
             return resp;
         }
         catch (Exception e)
         {
 //        	e.printStackTrace();
             log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
             return -1;
         }
         finally
         {
             if (tx.isActive())
             {
                 tx.rollback();
             }
             pm.close();
         }
     }
     
     public List<Reserva> darReservas ()
     {
         return sqlReserva.darReservas (pmf.getPersistenceManager());
     }
     
     public Reserva darReservaPorId (long id)
     {
         return sqlReserva.darReservaPorId (pmf.getPersistenceManager(), id);
     }

    /**************************************
     * Método para las RESERVAS COLECTIVAS
     *************************************/

     public ReservaColectiva adicionarReservaColectiva(long idCliente, String tipoEvento, Timestamp fechaIni, Timestamp fechaF, String tipoInmueble, Integer cantidad, List<VOInmueble> inmuebles)
     {
         PersistenceManager pm = pmf.getPersistenceManager();
         Transaction tx=pm.currentTransaction();
         try
         {
             tx.begin();
             long id = nextval ();
             long tuplasInsertadas = sqlReservaColectiva.adicionarReservaColectiva(pm, id, tipoEvento, tipoInmueble,cantidad);
             for (int i=0; i<cantidad;i++)
             {
                long id2 = nextval ();
                sqlReserva.adicionarReserva(pm, id2, idCliente, inmuebles.get(i).getId(), fechaIni, fechaF, "False", id);
             }
             tx.commit();
             
             log.trace ("Inserción de reserva colectiva: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
             
             return new ReservaColectiva (id,tipoInmueble, tipoEvento,cantidad);
         }
         catch (Exception e)
         {
             //e.printStackTrace();
             log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
             return null;
         }
         finally
         {
             if (tx.isActive())
             {
                 tx.rollback();
             }
             pm.close();
         }
     }

     public long eliminarReservaColectivaPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlReservaColectiva.eliminarReservaColectivaPorId(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

    public List<ReservaColectiva> darReservasColectivas ()
	{
		return sqlReservaColectiva.darReservasColectivas (pmf.getPersistenceManager());
	}
	
	public ReservaColectiva darReservaColectivaPorId (long id)
	{
		return sqlReservaColectiva.darReservaColectivaPorId (pmf.getPersistenceManager(), id);
	}
	
	/* *************************
	 * Método para los SERVICIOS
	 **************************/
	
	public Servicio adicionarServicio(String nombre, String descripcion, int valorAdicional)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlServicio.adicionarServicio(pm, id, nombre, descripcion, valorAdicional);
            tx.commit();
            
            log.trace ("Inserción del servicio: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Servicio (id,nombre, descripcion, valorAdicional);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarServicioPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlServicio.eliminarServicioPorId(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<Servicio> darServicios()
	{
		return sqlServicio.darServicios (pmf.getPersistenceManager());
	}
	
	public Servicio darServicioPorId (long id)
	{
		return sqlServicio.darServicioPorId (pmf.getPersistenceManager(), id);
	}
	/* *************************
	 * Método para los SERVICIOS INMUEBLE
	 **************************/
	
	public ServicioInmueble adicionarServicioInmueble(long idServicio, long idInmueble, boolean incluido, int valorAdicional)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            //long id = nextval ();
            long tuplasInsertadas = sqlServicioInmueble.adicionarServicioInmueble(pm, idServicio, idInmueble, incluido, valorAdicional);
            tx.commit();
            
            log.trace ("Inserción del servicio: " + idServicio + "en el inmueble: "+ idInmueble+ ":" + tuplasInsertadas + " tuplas insertadas");
            
            return new ServicioInmueble (incluido, valorAdicional, idServicio, idInmueble);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarServicioInmueblePorId (long idServicio, long idInmueble) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlServicioInmueble.eliminarServicioInmueblePorId(pm, idServicio, idInmueble);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<ServicioInmueble> darServiciosInmueble()
	{
		return sqlServicioInmueble.darServiciosInmueble (pmf.getPersistenceManager());
	}
	
	public ServicioInmueble darServicioInmueblePorId (long idServicio, long idInmueble)
	{
		return sqlServicioInmueble.darServicioInmueblePorId (pmf.getPersistenceManager(), idServicio, idInmueble);
	}
	/* *****************************************
	 * Métodos para SERVICIOS ADICIONALES USADOS
	 *******************************************/
	
	public ServicioUsado adicionarServicioUsado (long idServicio, long idReserva, long idInmueble)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            //long id = nextval ();
            long tuplasInsertadas = sqlServicioUsado.adicionarServicioAdicionalUsado(pm, idServicio, idReserva, idInmueble);
            tx.commit();
            
            log.trace ("Inserción del servicio adicional usado: " + idServicio + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new ServicioUsado (idServicio, idReserva, idInmueble);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarServicioUsadoPorIdServicio (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlServicioUsado.eliminarServicioAdicionalUsadoPorIdServicio(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarServicioUsadoPorIdReserva (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlServicioUsado.eliminarServicioAdicionalUsadoPorIdReserva(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<ServicioUsado> darServiciosUsados()
	{
		return sqlServicioUsado.darServiciosAdicionalesUsados (pmf.getPersistenceManager());
	}
	
	public ServicioUsado darServiciosUsadosPorIdReserva (long id)
	{
		return sqlServicioUsado.darServicioAdicionalUsadoPorIdReserva (pmf.getPersistenceManager(), id);
	}
	
	public ServicioUsado darServiciosUsadosPorIdServicio (long id)
	{
		return sqlServicioUsado.darServicioAdicionalUsadoPorIdServicio (pmf.getPersistenceManager(), id);
	}
	
	/* *********************************
	 * Métodos para SERVICIOS INCLUIDOS
	 ***********************************/
	
	
	
	/* ********************************************
	 * Métodos para las VIVIENDAS UNIVERSITARIAS
	 *********************************************/

	public ViviendaUniversitaria adicionarViviendaUniversitaria (String nombre, String tipoOperador, int cantHabitaciones)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlViviendaUniversitaria.adicionarViviendaUniversitaria(pm,id);
            tx.commit();
            
            log.trace ("Inserción de la ViviendaUniversitaria: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new ViviendaUniversitaria (id, nombre, tipoOperador, cantHabitaciones);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarViviendaUniversitariaPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlViviendaUniversitaria.eliminarViviendaUniversitariaPorId(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<ViviendaUniversitaria> darViviendasUniversitarias()
	{
		return sqlViviendaUniversitaria.darViviendasUniversitarias (pmf.getPersistenceManager());
	}
	
	public ViviendaUniversitaria darViviendaUniversitariaPorId (long id)
	{
		return sqlViviendaUniversitaria.darViviendaUniversitariaPorId (pmf.getPersistenceManager(), id);
	}
	
	public long [] limpiarAlohAndes ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiarAlohAndes (pm);
            tx.commit ();
            log.info ("Borrada la base de datos");
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1, -1, -1, -1, -1, -1};
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}
	
}
