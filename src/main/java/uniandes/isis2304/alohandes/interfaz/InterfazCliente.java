package uniandes.isis2304.alohandes.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.alohandes.negocio.*;

@SuppressWarnings("serial")
public class InterfazCliente extends JFrame implements ActionListener{


	private static Logger log = Logger.getLogger(InterfazCliente.class.getName());
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigCliente.json"; 
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD.json";
	
	private JsonObject tableConfig;
	private AlohAndes alohAndes;
	 
	private JsonObject guiConfig;
	private PanelDatos panelDatos;
	private JMenuBar menuBar;
	
	public InterfazCliente( )
	  {
	    // Carga la configuración de la interfaz desde un archivo JSON
	    guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
	        
	    // Configura la apariencia del frame que contiene la interfaz gráfica
	    configurarFrame ( );
	    if (guiConfig != null) 	   
	    {
	   	crearMenu( guiConfig.getAsJsonArray("menuBar") );
	    }
	        
	    tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
	    alohAndes = new AlohAndes (tableConfig);
	        
	    String path = guiConfig.get("bannerPath").getAsString();
	    panelDatos = new PanelDatos ( );

	    setLayout (new BorderLayout());
	    add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
	   }
	 
	 private JsonObject openConfig (String tipo, String archConfig)
	    {
	    	JsonObject config = null;
			try 
			{
				Gson gson = new Gson( );
				FileReader file = new FileReader (archConfig);
				JsonReader reader = new JsonReader ( file );
				config = gson.fromJson(reader, JsonObject.class);
				log.info ("Se encontró un archivo de configuración válido: " + tipo);
			} 
			catch (Exception e)
			{
//				e.printStackTrace ();
				log.info ("NO se encontró un archivo de configuración válido");			
				JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
			}	
	        return config;
	    }
	 
	 private void configurarFrame(  )
	    {
	    	int alto = 0;
	    	int ancho = 0;
	    	String titulo = "";	
	    	
	    	if ( guiConfig == null )
	    	{
	    		log.info ( "Se aplica configuración por defecto" );			
				titulo = "Parranderos APP Default";
				alto = 300;
				ancho = 500;
	    	}
	    	else
	    	{
				log.info ( "Se aplica configuración indicada en el archivo de configuración" );
	    		titulo = guiConfig.get("title").getAsString();
				alto= guiConfig.get("frameH").getAsInt();
				ancho = guiConfig.get("frameW").getAsInt();
	    	}
	    	
	        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	        setLocation (50,50);
	        setResizable( true );
	        setBackground( Color.WHITE );

	        setTitle( titulo );
			setSize ( ancho, alto);        
	    }
	 
	 private void crearMenu(  JsonArray jsonMenu )
	    {    	
	    	// Creación de la barra de menús
	        menuBar = new JMenuBar();       
	        for (JsonElement men : jsonMenu)
	        {
	        	// Creación de cada uno de los menús
	        	JsonObject jom = men.getAsJsonObject(); 

	        	String menuTitle = jom.get("menuTitle").getAsString();        	
	        	JsonArray opciones = jom.getAsJsonArray("options");
	        	
	        	JMenu menu = new JMenu( menuTitle);
	        	
	        	for (JsonElement op : opciones)
	        	{       	
	        		// Creación de cada una de las opciones del menú
	        		JsonObject jo = op.getAsJsonObject(); 
	        		String lb =   jo.get("label").getAsString();
	        		String event = jo.get("event").getAsString();
	        		
	        		JMenuItem mItem = new JMenuItem( lb );
	        		mItem.addActionListener( this );
	        		mItem.setActionCommand(event);
	        		
	        		menu.add(mItem);
	        	}       
	        	menuBar.add( menu );
	        }        
	        setJMenuBar ( menuBar );	
	    }
	 
	 /*
	  * Métodos para los requerimientos funcionales
	  */
	 
	 public void registroCliente() {
		 
		 try {
			 
			 String idCliente= JOptionPane.showInputDialog(this, "Id del cliente?", "Registro ciente", JOptionPane.QUESTION_MESSAGE);
			 Long id=Long.parseLong(idCliente);
			 String nombre= JOptionPane.showInputDialog(this, "Nombre del cliente?", "Registro ciente", JOptionPane.QUESTION_MESSAGE);
			 String correo=JOptionPane.showInputDialog(this, "Correo", "Registro ciente", JOptionPane.QUESTION_MESSAGE);
			 String telefono=JOptionPane.showInputDialog(this, "Telefono?", "Registro ciente", JOptionPane.QUESTION_MESSAGE);
			 String tipoMiembro=JOptionPane.showInputDialog(this, "TipoMiembro?", "Registro ciente", JOptionPane.QUESTION_MESSAGE);
			 if (nombre!=null && correo!=null && telefono!=null && tipoMiembro!=null) {
				 VOCliente tb= alohAndes.adicionarCliente(id, nombre, correo, telefono, tipoMiembro);
				 if (tb == null)
	        		{
	        			throw new Exception ("No se registro al cliente con el nombre: " + nombre +", Id: "+id);
	        	}else {
	        		String resultado = "En registroCliente\n\n";
	        		resultado += "Cliente adicionado exitosamente: " + tb;
	    			resultado += "\n Operación terminada";
	    			panelDatos.actualizarInterfaz(resultado);
	        	}
			 }
		 }
		 catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		 }
	 }
	 public void listarClientes( )
     {
    	try 
    	{
			List <VOCliente> lista = alohAndes.darVOClientes();

			String resp = "Los clientes actuales son:\n";
    		int i = 0;
        	for (VOCliente tb : lista)
        	{
        		resp += i++ + ". " + tb.toString() + "\n";
        	}
			panelDatos.actualizarInterfaz(resp);
			resp += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
	 
	 public void cancelarReserva() {
		 
		 try {
			 String idInput= JOptionPane.showInputDialog(this, "id de reserva?", "Cancelar reserva", JOptionPane.QUESTION_MESSAGE);
			 Long id=Long.parseLong(idInput);
			 if (id!=null) {
				 alohAndes.eliminarReservaPorId(id);
				 String resultado = "En cancelarReserva \n\n";
	     		 resultado += "Reserva "+idInput+" eliminada exitosamente";
	 			 resultado += "\n Operación terminada";
	 			 panelDatos.actualizarInterfaz(resultado);
			 }
		 }
		 catch (Exception e) {
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			 }
	 }
	 
	 public void registroReserva() {
		try {
			 
			String fechaInicio= JOptionPane.showInputDialog(this, "Fecha inicial de la reserva (yyyy-mm-dd)?", "Registro reserva", JOptionPane.QUESTION_MESSAGE);
			String fechaFin=JOptionPane.showInputDialog(this, "Fecha final de la reserva (yyyy-mm-dd)", "Registro reserva", JOptionPane.QUESTION_MESSAGE);
			String idCliente=JOptionPane.showInputDialog(this, "Cliente que desea la reserva?", "Registro reserva", JOptionPane.QUESTION_MESSAGE);
			String idInmueble=JOptionPane.showInputDialog(this, "En qué inmueble desea la reserva?", "Registro reserva", JOptionPane.QUESTION_MESSAGE);
			if (fechaInicio!=null && fechaFin!=null && idCliente!=null && idInmueble!=null) {
				VOReserva tb= alohAndes.adicionarReserva(Timestamp.valueOf(fechaInicio+=" 00:00:00.00"), Timestamp.valueOf(fechaFin+=" 00:00:00.00"), Long.parseLong(idCliente), Long.parseLong(idInmueble), "False", 0);
				if (tb == null)
				   {
					   throw new Exception ("No se agregó la reserva para el cliente:" +idCliente+" en el inmueble: "+idInmueble );
			   }else {
				   String resultado = "En registroReserva\n\n";
				   resultado += "Reserva adicionada exitosamente: " + tb;
				   resultado += "\n Operación terminada";
				   panelDatos.actualizarInterfaz(resultado);
			   }
			}
		}
		catch (Exception e) {
		   String resultado = generarMensajeError(e);
		   panelDatos.actualizarInterfaz(resultado);
		}
	 }

	 public void registroReservaColectiva()
	 {
		try {
			String idCliente=JOptionPane.showInputDialog(this, "Cliente que desea la reserva colectiva?", "Registro reserva colectiva", JOptionPane.QUESTION_MESSAGE);
			String tipoEvento=JOptionPane.showInputDialog(this, "Tipo de evento?", "Registro reserva colectiva", JOptionPane.QUESTION_MESSAGE);
			String fechaInicio= JOptionPane.showInputDialog(this, "Fecha inicial de la reserva (yyyy-mm-dd)?", "Registro reserva colectiva", JOptionPane.QUESTION_MESSAGE);
			String fechaFin=JOptionPane.showInputDialog(this, "Fecha final de la reserva (yyyy-mm-dd)", "Registro reserva colectiva", JOptionPane.QUESTION_MESSAGE);
			String tipoInmueble= JOptionPane.showInputDialog(this, "Tipo de inmueble (Suite, Semisuite, Estandar, Casa o Apartamento)?", "Registro reserva colectiva", JOptionPane.QUESTION_MESSAGE);
			String servicioDeseado= JOptionPane.showInputDialog(this, "Servicio deseado?", "Registro reserva colectiva", JOptionPane.QUESTION_MESSAGE);
			String cantidad= JOptionPane.showInputDialog(this, "Cantidad de personas?", "Registro reserva colectiva colectiva", JOptionPane.QUESTION_MESSAGE);
			Integer cant=Integer.parseInt(cantidad);
			
			if (idCliente!=null && fechaInicio!=null && fechaFin!=null && cant!=null && tipoInmueble!=null) {
				List<VOInmueble> inmuebles= alohAndes.inmueblesDisponibles(tipoInmueble, Timestamp.valueOf(fechaInicio+=" 00:00:00.00"), Timestamp.valueOf(fechaFin+=" 00:00:00.00"), servicioDeseado);
				if(inmuebles.size()<cant)
				{
					throw new Exception("No se puede realizar la reserva colectiva, inmuebles insuficientes.");
				}
				VOReservaColectiva tb= alohAndes.adicionarReservaColectiva(Long.parseLong(idCliente), tipoEvento, Timestamp.valueOf(fechaInicio), Timestamp.valueOf(fechaFin), tipoInmueble, Integer.parseInt(cantidad), inmuebles);
				if (tb == null)
				   {
					   throw new Exception ("No se agregó la reserva colectiva para el cliente:" +idCliente);
			   }else {
				   String resultado = "En registroReservaColectiva\n\n";
				   resultado += "Reserva colectiva adicionada exitosamente: " + tb;
				   resultado += "\n Operación terminada";
				   panelDatos.actualizarInterfaz(resultado);
			   }
			}

		}
		catch(Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		
	 }

	 public void cancelarReservaColectiva()
	 {
		try{

			String idReservaColectiva= JOptionPane.showInputDialog(this,"Reserva colectiva a cancelar");
			if (idReservaColectiva!=null){
				long resp1= alohAndes.eliminarReservaPorReservaColectiva(Long.parseLong(idReservaColectiva));
				long resp2= alohAndes.eliminarReservaPorReservaColectiva(Long.parseLong(idReservaColectiva));
				String resultado = "En cancelarReserva \n\n";
	     		resultado += "Reserva "+idReservaColectiva+" eliminada exitosamente";
	 			resultado += "\n Operación terminada";
	 			panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch(Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }

	 public void consultarConsumov1()
	 {
		try{
			String idInmueble= JOptionPane.showInputDialog(this,"Inmueble a consultar");
			String fechaInic= JOptionPane.showInputDialog(this, "Fecha de inicio, yyyy-mm-dd");
			String fechaFin= JOptionPane.showInputDialog(this, "Fecha de fin, yyyy-mm-dd");
			String opcion= JOptionPane.showInputDialog(this, "0:Si no desea clasificacion ,1: Si desea ordenar por nombre, 2: Si desea ordenar por id, 3: Si desea agrupar");
			if (idInmueble!=null && fechaInic!=null && fechaFin!=null && opcion!=null){
				List<Cliente> resp= alohAndes.consultaConsumoV1(Long.parseLong(idInmueble), Timestamp.valueOf(fechaInic+=" 00:00:00.00"), Timestamp.valueOf(fechaFin+=" 00:00:00.00"), Integer.parseInt(opcion));
				String resultado = "En cancelarConsumoAlohandes \n\n";
				for (int i=0; i<resp.size();i++){
					resultado+="Id: "+ String.valueOf(resp.get(i).getId());
					resultado+="Nombre: " +resp.get(i).getNombre()+" ";
					resultado+="Correo: " +resp.get(i).getCorreo()+" ";
					resultado+="Telefono: " +resp.get(i).getTelefono()+" ";
					resultado+="Tipo Miembro: " +resp.get(i).getTipoMiembro();
					resultado+="\n\n";
				}
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch(Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }

	 public void consultarConsumov2()
	 {
		try{
			String idInmueble= JOptionPane.showInputDialog(this,"Inmueble a consultar");
			String fechaInic= JOptionPane.showInputDialog(this, "Fecha de inicio, yyyy-mm-dd");
			String fechaFin= JOptionPane.showInputDialog(this, "Fecha de fin, yyyy-mm-dd");
			String opcion= JOptionPane.showInputDialog(this, "0:Si no desea clasificacion ,1: Si desea ordenar por nombre, 2: Si desea ordenar por id, 3: Si desea agrupar");
			if (idInmueble!=null && fechaInic!=null && fechaFin!=null && opcion!=null){
				List<Cliente> resp= alohAndes.consultaConsumoV2(Long.parseLong(idInmueble), Timestamp.valueOf(fechaInic+=" 00:00:00.00"), Timestamp.valueOf(fechaFin+=" 00:00:00.00"), Integer.parseInt(opcion));
				String resultado = "En cancelarConsumoAlohandes \n\n";
				for (int i=0; i<resp.size();i++){
					resultado+="Id: "+ String.valueOf(resp.get(i).getId());
					resultado+="Nombre: " +resp.get(i).getNombre()+" ";
					resultado+="Correo: " +resp.get(i).getCorreo()+" ";
					resultado+="Telefono: " +resp.get(i).getTelefono()+" ";
					resultado+="TipoMiembro: " +resp.get(i).getTipoMiembro();
					resultado+="\n\n";
				}
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch(Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }
	
	 /*
	  * Main
	  */
	 
	 public static void main( String[] args )
	    {
	        try
	        {
	        	
	            // Unifica la interfaz para Mac y para Windows.
	            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
	            InterfazCliente interfaz = new InterfazCliente( );
	            interfaz.setVisible( true );
	        }
	        catch( Exception e )
	        {
	            e.printStackTrace( );
	        }
	    }
	 
	 private String generarMensajeError(Exception e) 
		{
			String resultado = "************ Error en la ejecución\n";
			resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
			return resultado;
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String evento = e.getActionCommand( );		
        try 
        {
			Method req = InterfazCliente.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception a) 
        {
			a.printStackTrace();
		} 
	}

}
