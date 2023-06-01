package uniandes.isis2304.alohandes.negocio;

import java.util.LinkedList;
import java.util.List;

public class Inmueble implements VOInmueble{

	/*
	 * Atributos
	 */
	private long id;
	private long operador;
	private int costoBase;
	private String estado;
	private String tipo;
	private List<Object []> serviciosIncluidos;
	private List<Object[]> serviciosNoIncluidos;
	private List<Object[]> reservas;
	
	/*
	 * Métodos
	 */
	
	/**
	 * Constructor por defecto
	 */
	public Inmueble() {
		this.id=0;
		this.operador=0;
		this.costoBase=0;
		this.estado=null;
		this.tipo=null;
		serviciosIncluidos= new LinkedList<Object[]>();
		serviciosNoIncluidos= new LinkedList<Object[]>();
		reservas= new LinkedList<Object[]>();
	}
	
	/**
	 * Constructor con parametros
	 * @param id
	 * @param costoBase
	 * @param disponible
	 */
	public Inmueble(long id, long operador, int costoBase, String estado, String tipo) {
		this.id=id;
		this.operador=operador;
		this.costoBase=costoBase;
		this.estado=estado;
		this.tipo=tipo;
		serviciosIncluidos= new LinkedList<Object[]>();
		serviciosNoIncluidos= new LinkedList<Object[]>();
		reservas= new LinkedList<Object[]>();
	}
	
	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getEstado(){
		return estado;
	}
	public void setEstado( String estado){
		this.estado=estado;
	}

	public String getTipo(){
		return tipo;
	}
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	
	public void setId(long id) {
		this.id=id;
	}

	public int getCostoBase() {
		return costoBase;
	}
	
	public void setCostoBase(int costoBase) {
		this.costoBase=costoBase;
	}
	
	public long getOperador() {
		return operador;
	}

	public void setOperador(long operador) {
		this.operador = operador;
	}

	public List<Object[]> getServiciosIncluidos() {
		return serviciosIncluidos;
	}

	public void setServiciosIncluidos(List<Object[]> serviciosIncluidos) {
		this.serviciosIncluidos = serviciosIncluidos;
	}

	public List<Object[]> getServiciosNoIncluidos() {
		return serviciosNoIncluidos;
	}

	public void setServiciosNoIncluidos(List<Object[]> serviciosNoIncluidos) {
		this.serviciosNoIncluidos = serviciosNoIncluidos;
	}

	public List<Object[]> getReservas() {
		return reservas;
	}

	public void setReservas(List<Object[]> reservas) {
		this.reservas = reservas;
	}

	public String toString() 
	{
		return "Inmueble [id=" + id + ", Costo base="+ costoBase + "]";
	}
}
