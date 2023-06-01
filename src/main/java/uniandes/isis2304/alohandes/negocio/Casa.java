package uniandes.isis2304.alohandes.negocio;

import java.util.List;

public class Casa extends Inmueble implements VOCasa{

	/*
	 * Atributos
	 */
	
	 private int cantHabitaciones;
	 private String seguro;
	 
	 /*
	  * Métodos
	  */
	 
	 /**
	  * Metodo por defecto
	  */
	 
	 public Casa() {
		 super();
		 this.cantHabitaciones=0;
		 this.seguro="";
	 }
	 
	 /**
	  * Método con valores
	  * @param id
	  * @param cantHabitaciones
	  * @param seguro
	  */
	 public Casa(long id, long idOperador, int costoBase, int cantHabitaciones, String seguro, String estado, String tipo) {
		 super(id, idOperador, costoBase, estado, tipo);
		 this.cantHabitaciones=cantHabitaciones;
		 this.seguro=seguro;
	 }
	 
	 
 
	 @Override
	 public long getId() {
		 // TODO Auto-generated method stub
		 return super.getId();
	 }
 
	 @Override
	 public void setId(long id) {
		 // TODO Auto-generated method stub
		 super.setId(id);
	 }
 
	 public int getCantHabitaciones() {
		 // TODO Auto-generated method stub
		 return cantHabitaciones;
	 }
	 
	 public void setCantHabitaciones(int cantHabitaciones) {
		 this.cantHabitaciones=cantHabitaciones;
	 }
 
	 public String getSeguro() {
		 // TODO Auto-generated method stub
		 return seguro;
	 }
	 
	 public void setSeguro(String seguro) {
		 this.seguro=seguro;
	 }
	 
	 @Override
	 public int getCostoBase() {
		 // TODO Auto-generated method stub
		 return super.getCostoBase();
	 }
 
	 @Override
	 public void setCostoBase(int costoBase) {
		 // TODO Auto-generated method stub
		 super.setCostoBase(costoBase);
	 }
 
	 @Override
	 public long getOperador() {
		 // TODO Auto-generated method stub
		 return super.getOperador();
	 }
 
	 @Override
	 public void setOperador(long idOperador) {
		 // TODO Auto-generated method stub
		 super.setOperador(idOperador);
	 }
 
	 @Override
	 public List<Object[]> getServiciosIncluidos() {
		 // TODO Auto-generated method stub
		 return super.getServiciosIncluidos();
	 }
 
	 @Override
	 public void setServiciosIncluidos(List<Object[]> serviciosIncluidos) {
		 // TODO Auto-generated method stub
		 super.setServiciosIncluidos(serviciosIncluidos);
	 }
 
	 @Override
	 public List<Object[]> getServiciosNoIncluidos() {
		 // TODO Auto-generated method stub
		 return super.getServiciosNoIncluidos();
	 }
 
	 @Override
	 public void setServiciosNoIncluidos(List<Object[]> serviciosNoIncluidos) {
		 // TODO Auto-generated method stub
		 super.setServiciosNoIncluidos(serviciosNoIncluidos);
	 }
 
	 @Override
	 public List<Object[]> getReservas() {
		 // TODO Auto-generated method stub
		 return super.getReservas();
	 }
 
	 @Override
	 public void setReservas(List<Object[]> reservas) {
		 // TODO Auto-generated method stub
		 super.setReservas(reservas);
	 }
 
	 @Override
	 public String toString() {
		 // TODO Auto-generated method stub
		 return super.toString();
	 }

	

}
