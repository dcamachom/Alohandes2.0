package uniandes.isis2304.alohandes.interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import java.awt.Color;


public class InterfazInic extends JFrame implements ActionListener{


	private JsonObject guiConfig;

	private static Logger log = Logger.getLogger(InterfazInic.class.getName());

	 public static void main(String[] ar){
		 InterfazInic formulario1=new InterfazInic();
	        formulario1.setVisible(true);
	        formulario1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
	
	private JButton btnCliente;
	private JButton btnOperador;
	private JButton btnGerente;
	
	public InterfazInic(){
		configurarFrame ( );
		JPanel panel= new JPanel(); 
		this.btnCliente=new JButton ("Cliente");
		this.btnOperador=new JButton ("Operador");
		this.btnGerente=new JButton ("Gerente general");
		panel.add(btnCliente);
		btnCliente.addActionListener(this);
		panel.add(btnOperador);
		btnOperador.addActionListener(this);
		panel.add(btnGerente);
		btnGerente.addActionListener(this);
		this.add(panel);
	}
	private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "AlohAndes APP Default";
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==btnCliente) {
			InterfazCliente interfazC= new InterfazCliente();
			interfazC.setVisible(true);
		}
		else if (e.getSource()==btnOperador) {
			InterfazOperador interfazO= new InterfazOperador();
			interfazO.setVisible(true);
		}
		else if (e.getSource()==btnGerente) {
			InterfazGerente interfazG= new InterfazGerente();
			interfazG.setVisible(true);
		}
	}

}
