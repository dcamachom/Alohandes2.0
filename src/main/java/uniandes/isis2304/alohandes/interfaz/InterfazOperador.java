package uniandes.isis2304.alohandes.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.alohandes.negocio.*;

@SuppressWarnings("serial")
public class InterfazOperador extends JFrame implements ActionListener{
	
	private static Logger log = Logger.getLogger(InterfazCliente.class.getName());
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigOperador.json"; 
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD.json";
	
	private JsonObject tableConfig;
	private AlohAndes alohAndes;
	 
	private JsonObject guiConfig;
	private PanelDatos panelDatos;
	private JMenuBar menuBar;
	
	public InterfazOperador( )
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
	        
	    //String path = guiConfig.get("bannerPath").getAsString();
	    panelDatos = new PanelDatos ( );

	    setLayout (new BorderLayout());
	    //add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
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
	 
	 public void registroOperador() {
		 try {
			 
			 String nombre= JOptionPane.showInputDialog(this, "Nombre del cliente?", "Registro ciente", JOptionPane.QUESTION_MESSAGE);
			 String tipoMiembro=JOptionPane.showInputDialog(this, "TipoMiembro?", "Registro ciente", JOptionPane.QUESTION_MESSAGE);
			 if (nombre!=null && tipoMiembro!=null) {
				 VOOperador tb= alohAndes.adicionarOperador(nombre, tipoMiembro);
				 if (tb == null)
	        		{
	        			throw new Exception ("No se registro al operador con el nombre: " + nombre);
	        	}else {
	        		String resultado = "En registroOperador\n\n";
	        		resultado += "Operador adicionado exitosamente: " + tb;
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
	 
	 public void registroAlojamiento() {
		 
		 PanelInmuebles panel= new PanelInmuebles(this);
		 panel.setVisible(true);
		 this.setVisible(false);
		 
	 }
	 
	 public void registroHabitacion() {
		 
		 try {
			 String capacidad= JOptionPane.showInputDialog(this, "Capacidad?", "Registro Habitacion", JOptionPane.QUESTION_MESSAGE);
			 String tipo=JOptionPane.showInputDialog(this, "Tipo?", "Registro Habitacion", JOptionPane.QUESTION_MESSAGE);
			 String costo= JOptionPane.showInputDialog(this, "Costo base?", "Registro Habitacion", JOptionPane.QUESTION_MESSAGE);
			 String idOp= JOptionPane.showInputDialog(this, "IdOperador?", "Registro Habitacion", JOptionPane.QUESTION_MESSAGE);
			 int capacidad2=Integer.parseInt(capacidad);
			 int costoB2= Integer.parseInt(costo);
			 long idOp2= Long.parseLong(idOp);
			 if (tipo!=null) {
				 VOHabitacion tb=alohAndes.adicionarHabitacion(costoB2, idOp2, capacidad2, true, tipo);
				 if (tb == null)
	        		{
	        			throw new Exception ("No se registro a la habitacion");
	        	}else {
	        		String resultado = "En registroHabitacion\n\n";
	        		resultado += "Habitacion adicionado exitosamente: " + tb;
	    			resultado += "\n Operación terminada";
	    			panelDatos.actualizarInterfaz(resultado);
	        	}
			 }
		 }catch(Exception e) {
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			 }
		 
	 }
	 
	 public void registrarApto() {
		try {
			String numHabi= JOptionPane.showInputDialog(this, "Numero de habitaciones?", "Registro Habitacion", JOptionPane.QUESTION_MESSAGE);
			String costo= JOptionPane.showInputDialog(this, "Costo base?", "Registro Habitacion", JOptionPane.QUESTION_MESSAGE);
			String idOp= JOptionPane.showInputDialog(this, "IdOperador?", "Registro Habitacion", JOptionPane.QUESTION_MESSAGE);
			String amoblado= JOptionPane.showInputDialog(this, "Amoblado?", "Registro Habitacion", JOptionPane.QUESTION_MESSAGE);
			int numHabi2=Integer.parseInt(numHabi);
			int costoB2= Integer.parseInt(costo);
			long idOp2= Long.parseLong(idOp);
			boolean amoblado2= Boolean.valueOf(amoblado);
			
			VOApartamento tb=alohAndes.adicionarApartamento(costoB2, idOp2,amoblado2, numHabi2, "Habilitada", "Apartamento");
			if (tb == null)
			   {
				   throw new Exception ("No se registro al Apartmento");
		   }else {
			   String resultado = "En registroApartamento\n\n";
			   resultado += "Apartamento adicionado exitosamente: " + tb;
			   resultado += "\n Operación terminada";
			   panelDatos.actualizarInterfaz(resultado);
		   }
			
		}catch(Exception e) {
			   String resultado = generarMensajeError(e);
			   panelDatos.actualizarInterfaz(resultado);
			}
	}
	 
	 public void registrarCasa() {
		 
		 try {
			 String numHabi= JOptionPane.showInputDialog(this, "Numero de habitaciones?", "Registro Habitacion", JOptionPane.QUESTION_MESSAGE);
			 String costo= JOptionPane.showInputDialog(this, "Costo base?", "Registro Habitacion", JOptionPane.QUESTION_MESSAGE);
			 String idOp= JOptionPane.showInputDialog(this, "IdOperador?", "Registro Habitacion", JOptionPane.QUESTION_MESSAGE);
			 String seguro= JOptionPane.showInputDialog(this, "Seguro?", "Registro Habitacion", JOptionPane.QUESTION_MESSAGE);
			 int numHabi2=Integer.parseInt(numHabi);
			 int costoB2= Integer.parseInt(costo);
			 long idOp2= Long.parseLong(idOp);
			 
			 VOCasa tb=alohAndes.adicionarCasa(costoB2, idOp2, numHabi2, seguro);
			 if (tb == null)
        		{
        			throw new Exception ("No se registro la casa");
        	}else {
        		String resultado = "En registroCasa\n\n";
        		resultado += "Casa adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
        	}
			 
		 }catch(Exception e) {
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			 }
		 
	 }
	 
	 public void retirarAlojamiento() {
		try {
			String idInput= JOptionPane.showInputDialog(this, "Id del alojamiento que desea eliminar?", "Retirar oferta de alojamiento", JOptionPane.QUESTION_MESSAGE);
			Long id=Long.parseLong(idInput);
			if (id!=null) {
				alohAndes.eliminarInmueblePorId(id);
				String resultado = "En Retirar Alojamiento \n\n";
				 resultado += "Alojamiento retirado exitosamente";
				 resultado += "\n Operación terminada";
				 panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e) {
			   String resultado = generarMensajeError(e);
			   panelDatos.actualizarInterfaz(resultado);
			}
	 }
	 public void deshabilitarOferta()
	 {
		try
		{
			String inmueble= JOptionPane.showInputDialog(this,"Oferta de alojamiento a deshabilitar");
			if(inmueble!=null)
			{
				List<VOReserva> reservas=alohAndes.reservasPorInmueble(Long.parseLong(inmueble));
				String tipo= alohAndes.darTipoInmueble(Long.parseLong(inmueble));
				List<List<Long>> resp= alohAndes.deshabilitarInmueble(Long.parseLong(inmueble), reservas, tipo);
				String resultado = "En deshabilitarInmueble \n\n";
				for(int i=0;i<resp.get(0).size();i++)
				{
					resultado += "Reserva "+resp.get(0).get(i)+" reubicada exitosamente \n";
				}
				for(int i=0;i<resp.get(1).size();i++)
				{
					resultado += "Reserva "+resp.get(1).get(i)+" no reubicada exitosamente \n";
				}
	     		resultado += "Inmueble "+inmueble+" deshabilitado exitosamente";
	 			resultado += "\n Operación terminada";
	 			panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch(Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }
	 public void rehabilitarOferta()
	 {
		try
		{
			String idOferta= JOptionPane.showInputDialog(this,"Oferta de alojamiento a rehabilitar");
			if(idOferta!=null)
			{
				long resp= alohAndes.rehabilitarInmueble(Long.parseLong(idOferta));
				String resultado = "En rehabilitarInmueble \n\n";
	     		resultado += "Inmueble "+idOferta+" rehabilitado exitosamente";
	 			resultado += "\n Operación terminada";
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
	            InterfazOperador interfaz = new InterfazOperador( );
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
			Method req = InterfazOperador.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception a) 
        {
			a.printStackTrace();
		} 
	}

}
