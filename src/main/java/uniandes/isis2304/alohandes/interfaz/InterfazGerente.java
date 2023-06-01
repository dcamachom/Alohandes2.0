package uniandes.isis2304.alohandes.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.sql.Timestamp;

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
public class InterfazGerente extends JFrame implements ActionListener{


	private static Logger log = Logger.getLogger(InterfazGerente.class.getName());
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigGerente.json"; 
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD.json";
	
	private JsonObject tableConfig;
	private AlohAndes alohAndes;
	 
	private JsonObject guiConfig;
	private PanelDatos panelDatos;
	private JMenuBar menuBar;
	
	public InterfazGerente( )
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
	 public void consultarFuncionamiento()
     {
        try {
            String resultado = "En consultarFuncionamiento \n";
            for(int i=1;i<53;i++)
            {
                List <VOInmueble> listaI1= alohAndes.consultarFuncionamientoInmuebles1(i);
                List <VOInmueble> listaI2= alohAndes.consultarFuncionamientoInmuebles2(i);
                List <VOOperador> listaO1= alohAndes.consultarFuncionamientoOperadores1(i);
                List <VOOperador> listaO2= alohAndes.consultarFuncionamientoOperadores2(i);

                resultado+="Semana "+i;
                resultado+="\n Ofertas con menor ocupacion: \n";
                int j = 0;
        	    for (VOInmueble tb : listaI1)
        	    {
        		    resultado += j++ + ". " + tb.toString() + "\n";
        	    }
                resultado+="\n Ofertas con mayor ocupacion: \n";
                int k = 0;
        	    for (VOInmueble tb : listaI2)
        	    {
        		    resultado += k++ + ". " + tb.toString() + "\n";
        	    }
                resultado+="\n Operadores más solicitados: \n";
                int l = 0;
        	    for (VOOperador tb : listaO1)
        	    {
        		    resultado += l++ + ". " + tb.toString() + "\n";
        	    }
                resultado+="\n Ofertas menos solicitados: \n";
                int m = 0;
        	    for (VOOperador tb : listaO2)
        	    {
        		    resultado += m++ + ". " + tb.toString() + "\n";
        	    }
                resultado+="\n";

                panelDatos.actualizarInterfaz(resultado);
            }
            
        } 
        catch (Exception e) {
            String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
        }
     }
     public void buenosClientes()
     {
        try {
            String resultado = "En consultarBuenosClientes \n";
            List<VOCliente> lista1=alohAndes.clientesReservasCostosas();
            resultado+="Clientes con reservas más costosas: \n";
            int i = 0;
        	for (VOCliente tb : lista1)
        	{
        		resultado += i++ + ". " + tb.toString() + "\n";
        	}
            List<VOCliente> lista2=alohAndes.clientesReservasUnaPorMes();
            resultado+="\n Clientes que reservan una vez al mes: \n";
            int j = 0;
        	for (VOCliente tb : lista2)
        	{
        		resultado += j++ + ". " + tb.toString() + "\n";
        	}

            panelDatos.actualizarInterfaz(resultado);
        } 
        catch (Exception e) {
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
	            InterfazGerente interfaz = new InterfazGerente( );
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
			Method req = InterfazGerente.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception a) 
        {
			a.printStackTrace();
		} 
	}

}

