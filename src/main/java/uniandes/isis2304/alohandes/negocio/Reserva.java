package uniandes.isis2304.alohandes.negocio;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class Reserva implements VOReserva{
	
	/*
	 * Atributos
	 */
	
	 private long id;
	 private Timestamp fechaInicio;
	 private Timestamp fechaFin;
	 private String cancelado;
	 private long idCliente;
	 private long idInmueble;
	 private long reservaColectiva;
	 private List<Object[]> serviciosUsados;
	 
	 /**
	  * Método constructor por defecto
	  */
	 public Reserva() {
		 this.id=0;
		 this.fechaInicio= null;
		 this.fechaFin= null;
		 this.cancelado=null;
		 this.idCliente=0;
		 this.idInmueble=0;	
		 this.reservaColectiva=0;
		 serviciosUsados= new LinkedList<Object[]>();
	 }
	 
	 /**
	  * Método constructor con valores
	  */
	  public Reserva(long id, Timestamp fechaInicio, Timestamp fechaFin, long idCliente, long idInmueble, String cancelado, long reservaColectiva) {
		  this.id=id;
		  this.fechaInicio=fechaInicio;
		  this.fechaFin=fechaFin;
		  this.idCliente=idCliente;
		  this.idInmueble=idInmueble;
		  this.reservaColectiva=reservaColectiva;
		  this.cancelado=cancelado;
		  serviciosUsados= new LinkedList<Object[]>();
	  }
 
	 public long getId() {
		 return id;
	 }
	 
	 public void setId(long id) {
		 this.id=id;
	 }
 
	 public Timestamp getFechaInicio() {
		 return this.fechaInicio;
	 }
	 
	 public void setFechaInicio( Timestamp fechaInicio) {
		 this.fechaInicio=fechaInicio;
	 }
	 public void setFECHAINICIO( Timestamp fechaInicio) {
		this.fechaInicio=fechaInicio;
	}
 
	 public Timestamp getFechaFin() {
		 return this.fechaFin;
	 }
	 
	 public void setFechaFin(Timestamp fechaFin) {
		 this.fechaFin=fechaFin;
	 }
	 public void setFECHAFIN(Timestamp fechaFin) {
		this.fechaFin=fechaFin;
	}
 
	 public long getReservaColectiva() {
		 return reservaColectiva;
	 }
 
	 public void setReservaColectiva(long reservaColectiva) {
		 this.reservaColectiva = reservaColectiva;
	 }
	 public void setRESERVACOLECTIVA(long reservaColectiva) {
		this.reservaColectiva = reservaColectiva;
	}
 
	 public long getCliente() {
		 return this.idCliente;
	 }
	 
	 public void setCliente(long idCliente) {
		 this.idCliente=idCliente;
	 }
	 public void setCLIENTE(long idCliente) {
		this.idCliente=idCliente;
	}
	 
	 public long getIdInmueble() {
		 return this.idInmueble;
	 }
	 
	 public void setInmueble(long idInmueble) {
		 this.idInmueble=idInmueble;
	 }
	 public void setINMUEBLE(long idInmueble) {
		this.idInmueble=idInmueble;
	}
 
	 public String getCancelado(){
		 return cancelado;
	 }
 
	 public void setCancelado (String cancelado){
		 this.cancelado=cancelado;
	 }
	 public void setCANCELADO (String cancelado){
		this.cancelado=cancelado;
	}
 
	 public List<Object[]> getServiciosUsados() {
		 return serviciosUsados;
	 }
 
	 public void setServiciosUsados(List<Object[]> serviciosUsados) {
		 this.serviciosUsados = serviciosUsados;
	 }
 
	 public String toString() 
	 {
		 return "Reserva [id=" + id + ", Fecha inicio="+ fechaInicio + ", Fecha fin="+ fechaFin + ", Cancelado="
				 + cancelado + ", id cliente=" + idCliente+ ", id inmueble=" + idInmueble +", Reserva colectiva"+ reservaColectiva+"]";
	 }
}
