package uniandes.isis2304.alohandes.negocio;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.sql.Time;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import uniandes.isis2304.alohandes.persistencia.*;


public class AlohAndes {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(AlohAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaAlohAndes pa;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public AlohAndes ()
	{
		pa = PersistenciaAlohAndes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public AlohAndes (JsonObject tableConfig)
	{
		pa = PersistenciaAlohAndes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pa.cerrarUnidadPersistencia ();
	}
	/***************************************
	 * Métodos para manejar los Apartamentos
	 ***************************************/

	public Apartamento adicionarApartamento (int costoBase, long idOperador, boolean amoblado, int cantHabitaciones, String estado, String tipo)
	{
        log.info ("Adicionando Apartamento" );
        Apartamento apartamento = pa.adicionarApartamento(costoBase, idOperador, amoblado, cantHabitaciones, estado, tipo);		
        log.info ("Adicionando Apartamento: " + apartamento);
        return apartamento;
	}
	public long eliminarApartamentoPorId (long idApartamento)
	{
		log.info ("Eliminando Apartamento por id: " + idApartamento);
        long resp = pa.eliminarApartamentoPorId(idApartamento);		
        log.info ("Eliminando Apartamento por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<Apartamento> darApartamentos ()
	{
		log.info ("Consultando Apartamentos");
        List<Apartamento> apartamentos = pa.darApartamentos ();	
        log.info ("Consultando Apartamentos: " + apartamentos.size() + " existentes");
        return apartamentos;
	}
	public Apartamento darApartamentoPorId (long id)
	{
		log.info ("Buscando Apartamento por id: " + id);
		Apartamento apto = pa.darApartamentoPorId(id);
		return apto;
	}
	/********************************
	 * Métodos para manejar las Casas
	 ********************************/

	public Casa adicionarCasa (int costoBase, long idOperador, int cantHabitaciones, String seguro)
	{
        log.info ("Adicionando Casa" );
        Casa casa = pa.adicionarCasa(costoBase, idOperador, cantHabitaciones, seguro);		
        log.info ("Adicionando Casa: " + casa);
        return casa;
	}
	public long eliminarCasaPorId (long idCasa)
	{
		log.info ("Eliminando Casa por id: " + idCasa);
        long resp = pa.eliminarCasaPorId(idCasa);		
        log.info ("Eliminando Casa por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<Casa> darCasas ()
	{
		log.info ("Consultando Casas");
        List<Casa> casas = pa.darCasas ();	
        log.info ("Consultando Casas: " + casas.size() + " existentes");
        return casas;
	}
	public Casa darCasaPorId (long id)
	{
		log.info ("Buscando Casa por id: " + id);
		Casa casa = pa.darCasaPorId(id);
		return casa;
	}
	/************************************
	 * Métodos para manejar los Clientes
	 ************************************/
	public Cliente adicionarCliente (Long id, String nombre, String correo, String telefono, String tipoMiembro)
	{
        log.info ("Adicionando Cliente" );
        Cliente cliente = pa.adicionarCliente(id, nombre, correo, telefono, tipoMiembro);		
        log.info ("Adicionando Cliente: " + cliente);
        return cliente;
	}
	public long eliminarClientePorId (long idCliente)
	{
		log.info ("Eliminando Cliente por id: " + idCliente);
        long resp = pa.eliminarClientePorId(idCliente);		
        log.info ("Eliminando Cliente por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<VOCliente> darVOClientes ()
	{
		log.info ("Generando los VO de Tipos de bebida");        
        List<VOCliente> voClientes = new LinkedList<VOCliente> ();
        for (Cliente tb : pa.darClientes())
        {
        	voClientes.add (tb);
        }
        log.info ("Generando los VO de Tipos de bebida: " + voClientes.size() + " existentes");
        return voClientes;
	}
	public Cliente darClientePorId (long id)
	{
		log.info ("Buscando Cliente por id: " + id);
		Cliente cliente = pa.darClientePorId(id);
		return cliente;
	}
	/***************************************
	 * Métodos para manejar las Habitaciones
	 ***************************************/

	public Habitacion adicionarHabitacion (int costoBase, long idOperador, int capacidad, boolean compartida, String tipo)
	{
        log.info ("Adicionando Habitacion" );
        Habitacion habitacion = pa.adicionarHabitacion(costoBase, idOperador, capacidad, compartida, tipo);		
        log.info ("Adicionando Habitacion: " + habitacion);
        return habitacion;
	}
	public long eliminarHabitacionPorId (long idHabitacion)
	{
		log.info ("Eliminando Habitacion por id: " + idHabitacion);
        long resp = pa.eliminarHabitacionPorId(idHabitacion);		
        log.info ("Eliminando Habitacion por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<Habitacion> darHabitaciones ()
	{
		log.info ("Consultando Habitaciones");
        List<Habitacion> habitaciones = pa.darHabitaciones ();	
        log.info ("Consultando Habitaciones: " + habitaciones.size() + " existentes");
        return habitaciones;
	}
	public Habitacion darHabitacionPorId (long id)
	{
		log.info ("Buscando Habitacion por id: " + id);
		Habitacion habitacion = pa.darHabitacionPorId(id);
		return habitacion;
	}
	/************************************
	 * Métodos para manejar los Hostales
	 ***********************************/
	public Hostal adicionarHostal (String nombre, String tipoOperador, int cantHabitaciones, String registroCamaraComercio, String registroSuperIntendencia, Time horaApertura, Time horaCierre)
	{
        log.info ("Adicionando Hostal" );
        Hostal hostal = pa.adicionarHostal(nombre, tipoOperador, cantHabitaciones, registroCamaraComercio, registroSuperIntendencia, horaApertura, horaCierre);		
        log.info ("Adicionando Hostal: " + hostal);
        return hostal;
	}
	public long eliminarHostalPorId (long idHostal)
	{
		log.info ("Eliminando Hostal por id: " + idHostal);
        long resp = pa.eliminarHostalPorId(idHostal);		
        log.info ("Eliminando Hostal por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<Hostal> darHostales ()
	{
		log.info ("Consultando Hostales");
        List<Hostal> hostales = pa.darHostales ();	
        log.info ("Consultando Hostales: " + hostales.size() + " existentes");
        return hostales;
	}
	public Hostal darHostalPorId (long id)
	{
		log.info ("Buscando Hostal por id: " + id);
		Hostal hostal = pa.darHostalPorId(id);
		return hostal;
	}
	/***********************************
	 * Métodos para manejar los Hoteles
	 **********************************/
	public Hotel adicionarHotel (String nombre, String tipoOperador, int cantHabitaciones, String registroCamaraComercio, String registroSuperIntendencia)
	{
        log.info ("Adicionando Hotel" );
        Hotel hotel = pa.adicionarHotel(nombre, tipoOperador, cantHabitaciones, registroCamaraComercio, registroSuperIntendencia);		
        log.info ("Adicionando Hotel: " + hotel);
        return hotel;
	}
	public long eliminarHotelPorId (long idHotel)
	{
		log.info ("Eliminando Hotel por id: " + idHotel);
        long resp = pa.eliminarHotelPorId(idHotel);		
        log.info ("Eliminando Hotel por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<Hotel> darHoteles ()
	{
		log.info ("Consultando Hoteles");
        List<Hotel> hoteles = pa.darHoteles ();	
        log.info ("Consultando Hoteles: " + hoteles.size() + " existentes");
        return hoteles;
	}
	public Hotel darHotelPorId (long id)
	{
		log.info ("Buscando Hotel por id: " + id);
		Hotel hotel = pa.darHotelPorId(id);
		return hotel;
	}
	/**************************************
	 * Métodos para manejar los Inmuebles
	 *************************************/

	public Inmueble adicionarInmueble (int costoBase, long idOperador, String estado, String tipo)
	{
        log.info ("Adicionando Inmueble" );
        Inmueble inmueble = pa.adicionarInmueble(costoBase, idOperador, estado, tipo);		
        log.info ("Adicionando Inmueble: " + inmueble);
        return inmueble;
	}
	public long eliminarInmueblePorId (long idInmueble)
	{
		log.info ("Eliminando Inmueble por id: " + idInmueble);
        long resp = pa.eliminarInmueblePorId(idInmueble);		
        log.info ("Eliminando Inmueble por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<Inmueble> darInmuebles ()
	{
		log.info ("Consultando Inmuebles");
        List<Inmueble> inmuebles = pa.darInmuebles ();	
        log.info ("Consultando Inmuebles: " + inmuebles.size() + " existentes");
        return inmuebles;
	}
	public Inmueble darInmueblePorId (long id)
	{
		log.info ("Buscando Inmueble por id: " + id);
		Inmueble inmueble = pa.darInmueblePorId(id);
		return inmueble;
	}

	/**************************************
	 * Métodos para manejar los Operadores
	 *************************************/

	public Operador adicionarOperador (String nombre, String tipoOperador)
	{
        log.info ("Adicionando Operador" );
        Operador operador = pa.adicionarOperador(nombre, tipoOperador);		
        log.info ("Adicionando Operador: " + operador);
        return operador;
	}
	public long eliminarOperadorPorId (long idOperador)
	{
		log.info ("Eliminando Operador por id: " + idOperador);
        long resp = pa.eliminarOperadorPorId(idOperador);		
        log.info ("Eliminando Operador por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<Operador> darOperadores ()
	{
		log.info ("Consultando Operadores");
        List<Operador> operadores = pa.darOperadores ();	
        log.info ("Consultando Operadors: " + operadores.size() + " existentes");
        return operadores;
	}
	public Operador darOperadorPorId (long id)
	{
		log.info ("Buscando Operador por id: " + id);
		Operador operador = pa.darOperadorPorId(id);
		return operador;
	}
	/**********************************************
	 * Métodos para manejar las Personas Naturales
	 *********************************************/

	public PersonaNatural adicionarPersonaNatural (String nombre, String tipoOperador, String correo, String telefono)
	{
        log.info ("Adicionando PersonaNatural" );
        PersonaNatural personaNatural = pa.adicionarPersonaNatural(nombre, tipoOperador, correo, telefono);		
        log.info ("Adicionando PersonaNatural: " + personaNatural);
        return personaNatural;
	}
	public long eliminarPersonaNaturalPorId (long idPersonaNatural)
	{
		log.info ("Eliminando PersonaNatural por id: " + idPersonaNatural);
        long resp = pa.eliminarPersonaNaturalPorId(idPersonaNatural);		
        log.info ("Eliminando PersonaNatural por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<PersonaNatural> darPersonasNaturales ()
	{
		log.info ("Consultando PersonasNaturales");
        List<PersonaNatural> personasNaturales = pa.darPersonasNaturales ();	
        log.info ("Consultando PersonasNaturals: " + personasNaturales.size() + " existentes");
        return personasNaturales;
	}
	public PersonaNatural darPersonaNaturalPorId (long id)
	{
		log.info ("Buscando PersonaNatural por id: " + id);
		PersonaNatural personaNatural = pa.darPersonaNaturalPorId(id);
		return personaNatural;
	}
	/**********************************
	 * Métodos para manejar las Reservas
	 **********************************/
	public Reserva adicionarReserva (Timestamp fechaInicio, Timestamp fechaFin, long idCliente, long idInmueble, String cancelado, long reservaColectiva)
	{
        log.info ("Adicionando Reserva" );
        Reserva reserva = pa.adicionarReserva(fechaInicio, fechaFin, idCliente, idInmueble, cancelado, reservaColectiva);		
        log.info ("Adicionando Reserva: " + reserva);
        return reserva;
	}
	public List<VOInmueble> inmueblesDisponibles(String tipo, Timestamp fechaInicio, Timestamp fechaFin, String servicioDeseado)
	{

		log.info ("Generando los VO de inmuebles disponibles");        
        List<VOInmueble> voInmuebles = new LinkedList<VOInmueble> ();
        for (VOInmueble im : pa.inmueblesDisponibles(tipo, fechaInicio, fechaFin, servicioDeseado))
        {
        	voInmuebles.add (im);
        }
        log.info ("Generando los VO de inmuebles disponibles: " + voInmuebles.size() + " existentes");
        return voInmuebles;
	}
	public ReservaColectiva adicionarReservaColectiva (long idCliente, String tipoEvento, Timestamp fechaIni, Timestamp fechaF, String tipoInmueble, Integer cantidad, List<VOInmueble> inmuebles)
	{
        log.info ("Adicionando Reserva Colectiva" );
        ReservaColectiva reserva = pa.adicionarReservaColectiva(idCliente, tipoEvento, fechaIni, fechaF, tipoInmueble, cantidad, inmuebles);		
        log.info ("Adicionando Reserva: " + reserva);
        return reserva;
	}
	public List<List<Long>> deshabilitarInmueble(long id, List reservas, String tipo)
	{
		log.info ("Buscando Inmueble por id: " + id);
		List<List<Long>> resp = pa.deshabilitarInmueble(id, reservas, tipo);
		return resp;
	}
	public long rehabilitarInmueble(long id)
	{
		log.info("rehabilitando inmueble");
		long resp= pa.rehabilitarInmueble(id);
		log.info("Inmueble rehabilitado");
		return resp;
	}
	public List<VOReserva> reservasPorInmueble(Long inmueble)
	{
		log.info ("Generando los VO de reservas por inmueble");        
        List<VOReserva> voReservas = new LinkedList<VOReserva> ();
        for (VOReserva re : pa.reservasPorInmueble(inmueble))
        {
        	voReservas.add (re);
        }
        log.info ("Generando los VO de reservas por inmueble: " + voReservas.size() + " existentes");
        return voReservas;
	}
	public String darTipoInmueble (Long inmueble)
	{
		log.info ("Consultando tipo de inmueble");
        String tipo = pa.darTipoInmueble(inmueble);	
        log.info ("Consultando tipo de inmueble: " + inmueble);
        return tipo;
	}
	public long eliminarReservaPorId (long idReserva)
	{
		log.info ("Eliminando Reserva por id: " + idReserva);
        long resp = pa.eliminarReservaPorId(idReserva);		
        log.info ("Eliminando Reserva por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<Reserva> darReservas ()
	{
		log.info ("Consultando Reservas");
        List<Reserva> reservas = pa.darReservas();	
        log.info ("Consultando Reservas: " + reservas.size() + " existentes");
        return reservas;
	}
	public Reserva darReservaPorId (long id)
	{
		log.info ("Buscando Reserva por id: " + id);
		Reserva reserva = pa.darReservaPorId(id);
		return reserva;
	}

	public long eliminarReservaColectiva (long id)
	{
		log.info("Eliminando reserva por id "+id);
		long resp=pa.eliminarReservaColectivaPorId(id);
		log.info("Eliminando reserva por id: "+resp+ " tuplas eliminadas");
		return resp;
	}

	public long eliminarReservaPorReservaColectiva(long reservaColectiva)
	{
		log.info("Eliminando reservas de reserva colectiva: "+reservaColectiva);
		long resp= pa.eliminarReservaPorReservaColectiva(reservaColectiva);
		log.info("Eliminando reservas: "+resp+" tuplas eliminadas");
		return resp;
	}

	/*************************************
	 * Métodos para manejar los Servicios
	 ************************************/
	public Servicio adicionarServicio (String nombre, String descripcion, int valorAdicional)
	{
        log.info ("Adicionando Servicio" );
        Servicio servicio = pa.adicionarServicio(nombre, descripcion, valorAdicional);		
        log.info ("Adicionando Servicio: " + servicio);
        return servicio;
	}
	public long eliminarServicioPorId (long idServicio)
	{
		log.info ("Eliminando Servicio por id: " + idServicio);
        long resp = pa.eliminarServicioPorId(idServicio);		
        log.info ("Eliminando Servicio por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<Servicio> darServicios ()
	{
		log.info ("Consultando Servicios");
        List<Servicio> servicios = pa.darServicios();	
        log.info ("Consultando Servicios: " + servicios.size() + " existentes");
        return servicios;
	}
	public Servicio darServicioPorId (long id)
	{
		log.info ("Buscando Servicio por id: " + id);
		Servicio servicio = pa.darServicioPorId(id);
		return servicio;
	}
	/*********************************************
	 * Métodos para manejar los Servicios Inmueble
	 ********************************************/
	public ServicioInmueble adicionarServicioInmueble (long idServicio, long idInmueble, boolean incluido, int valorAdicional)
	{
        log.info ("Adicionando ServicioInmueble" );
        ServicioInmueble servicioInmueble = pa.adicionarServicioInmueble(idServicio, idInmueble, incluido, valorAdicional);		
        log.info ("Adicionando ServicioInmueble: " + servicioInmueble);
        return servicioInmueble;
	}
	public long eliminarServicioInmueblePorId (long idServicio, long idInmueble)
	{
		log.info ("Eliminando ServicioInmueble por id: " + idServicio + ", "+ idInmueble);
        long resp = pa.eliminarServicioInmueblePorId(idServicio, idInmueble);		
        log.info ("Eliminando ServicioInmueble por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<ServicioInmueble> darServicioInmuebles ()
	{
		log.info ("Consultando ServicioInmuebles");
        List<ServicioInmueble> servicioInmuebles = pa.darServiciosInmueble();	
        log.info ("Consultando ServicioInmuebles: " + servicioInmuebles.size() + " existentes");
        return servicioInmuebles;
	}
	public ServicioInmueble darServicioInmueblePorId (long idServicio, long idInmueble)
	{
		log.info ("Buscando ServicioInmueble por id: " + idServicio + ", "+idInmueble);
		ServicioInmueble servicioInmueble = pa.darServicioInmueblePorId(idServicio, idInmueble);
		return servicioInmueble;
	}
	/********************************************
	 * Métodos para manejar los Servicios Usados
	 ********************************************/

	public ServicioUsado adicionarServicioUsado (long idServicio, long idReserva, long idInmueble)
	{
        log.info ("Adicionando ServicioUsado" );
        ServicioUsado servicioUsado = pa.adicionarServicioUsado(idServicio, idReserva, idInmueble);		
        log.info ("Adicionando ServicioUsado: " + servicioUsado);
        return servicioUsado;
	}
	public long eliminarServicioUsadoPorId (long idServicioUsado)
	{
		log.info ("Eliminando ServicioUsado por id: " + idServicioUsado);
        long resp = pa.eliminarServicioUsadoPorIdReserva(idServicioUsado);		
        log.info ("Eliminando ServicioUsado por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<ServicioUsado> darServiciosUsados ()
	{
		log.info ("Consultando Servicios Usados");
        List<ServicioUsado> serviciosUsados = pa.darServiciosUsados();	
        log.info ("Consultando Servicios Usados: " + serviciosUsados.size() + " existentes");
        return serviciosUsados;
	}
	public ServicioUsado darServiciosUsadosPorIdReserva (long id)
	{
		log.info ("Buscando ServicioUsado por id: " + id);
		ServicioUsado servicioUsado = pa.darServiciosUsadosPorIdReserva(id);
		return servicioUsado;
	}
	/***************************************************
	 * Métodos para manejar las Viviendas Universitarias
	 **************************************************/
	
	public ViviendaUniversitaria adicionarViviendaUniversitaria (String nombre, String tipoOperador, int cantHabitaciones)
	{
        log.info ("Adicionando ViviendaUniversitaria" );
        ViviendaUniversitaria viviendaUniversitaria = pa.adicionarViviendaUniversitaria(nombre, tipoOperador, cantHabitaciones);		
        log.info ("Adicionando ViviendaUniversitaria: " + viviendaUniversitaria);
        return viviendaUniversitaria;
	}
	public long eliminarViviendaUniversitariaPorId (long idViviendaUniversitaria)
	{
		log.info ("Eliminando ViviendaUniversitaria por id: " + idViviendaUniversitaria);
        long resp = pa.eliminarViviendaUniversitariaPorId(idViviendaUniversitaria);		
        log.info ("Eliminando ViviendaUniversitaria por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	public List<ViviendaUniversitaria> darViviendasUniversitarias ()
	{
		log.info ("Consultando ViviendasUniversitarias");
        List<ViviendaUniversitaria> viviendasUniversitarias = pa.darViviendasUniversitarias();	
        log.info ("Consultando ViviendasUniversitarias: " + viviendasUniversitarias.size() + " existentes");
        return viviendasUniversitarias;
	}
	public ViviendaUniversitaria darViviendaUniversitariaPorId (long id)
	{
		log.info ("Buscando ViviendaUniversitaria por id: " + id);
		ViviendaUniversitaria viviendaUniversitaria = pa.darViviendaUniversitariaPorId(id);
		return viviendaUniversitaria;
	}

	/*****************************
	 * Métodos para las consultas
	*****************************/
	public List<Cliente> consultaConsumoV1(long inmueble, Timestamp fechaInic, Timestamp fechaFin, int opcion)
	{
		log.info("Realizando consulta de consumo version 1 en alohandes");
		List<Cliente> resp= pa.consultaConsumoV1(inmueble, fechaInic, fechaFin, opcion);
		return resp;
	}

	public List<Cliente> consultaConsumoV2(long inmueble, Timestamp fechaInic, Timestamp fechaFin, int opcion)
	{
		log.info("Realizando consulta de consumo version 2 en alohandes");
		List<Cliente> resp= pa.consultaConsumoV2(inmueble, fechaInic, fechaFin, opcion);
		return resp;
	}
	public List<VOInmueble> consultarFuncionamientoInmuebles1 (int semana)
	{
		log.info ("Consultar funcionamiento inmuebles");
		List<VOInmueble> voInmuebles = new LinkedList<VOInmueble> ();
        for (Inmueble tb : pa.consultarFuncionamientoInmuebles1(semana))
        {
        	voInmuebles.add (tb);
        }
        return voInmuebles;
	}
	public List<VOInmueble> consultarFuncionamientoInmuebles2 (int semana)
	{
		log.info ("Consultar funcionamiento inmuebles");
		List<VOInmueble> voInmuebles = new LinkedList<VOInmueble> ();
        for (Inmueble tb : pa.consultarFuncionamientoInmuebles2(semana))
        {
        	voInmuebles.add (tb);
        }
        return voInmuebles;
	}
	public List<VOOperador> consultarFuncionamientoOperadores1 (int semana)
	{
		log.info ("Consultar funcionamiento operadores");
		List<VOOperador> voOperadores = new LinkedList<VOOperador> ();
        for (Operador tb : pa.consultarFuncionamientoOperadores1(semana))
        {
        	voOperadores.add (tb);
        }
        return voOperadores;
	}
	public List<VOOperador> consultarFuncionamientoOperadores2 (int semana)
	{
		log.info ("Consultar funcionamiento operadores");
		List<VOOperador> voOperadores = new LinkedList<VOOperador> ();
        for (Operador tb : pa.consultarFuncionamientoOperadores2(semana))
        {
        	voOperadores.add (tb);
        }
        return voOperadores;
	}
	public List<VOCliente> clientesReservasCostosas ()
	{
		log.info ("Consultar buenos clientes");
		List<VOCliente> voClientes = new LinkedList<VOCliente> ();
        for (Cliente tb : pa.clientesReservasCostosas())
        {
        	voClientes.add (tb);
        }
        return voClientes;
	}
	public List<VOCliente> clientesReservasUnaPorMes ()
	{
		log.info ("Consultar buenos clientes");
		List<VOCliente> voClientes = new LinkedList<VOCliente> ();
        for (Cliente tb : pa.clientesReservasUnaPorMes())
        {
        	voClientes.add (tb);
        }
        return voClientes;
	}
}
