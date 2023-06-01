package uniandes.isis2304.alohandes.negocio;

public class ReservaColectiva implements VOReservaColectiva {
    
    private long id;
    private String  tipoEvento;
    private String tipoAlojamiento;
    private int cantidad;

    /*
     * Constructor por defecto
     */
    public ReservaColectiva(){
        this.id=0;
        this.tipoAlojamiento="";
        this.tipoEvento="";
        this.cantidad=0;
    }

    /*
     * Constructor con valores
     */
    public ReservaColectiva (long id, String tipoEvento, String tipoAlojamiento, int cantidad){
        this.id=id;
        this.tipoAlojamiento=tipoAlojamiento;
        this.tipoEvento=tipoEvento;
        this.cantidad=cantidad;
    }

    public long getId() {
        return this.id;
    }
    public String getTipoEvento() {
        return this.tipoEvento;
    }

    public String getTipoAlojamiento() {
        return this.tipoAlojamiento;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setId(long id){
        this.id=id;
    }

    public void setTipoEvento (String tipoEvento){
        this.tipoEvento=tipoEvento;
    }

    public void setTipoAlojamiento (String tipoAlojamiento){
        this.tipoAlojamiento=tipoAlojamiento;
    }

    public void setCantidad (int cantidad){
        this.cantidad=cantidad;
    }

    public String toString() 
	{
		return "Reserva [id=" + id + ", Tipo Evento="+ tipoEvento +", Tipo Alojamiento"+tipoAlojamiento+
        ", Cantidad"+ cantidad +"]";
	}

}
