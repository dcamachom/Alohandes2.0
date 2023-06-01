package uniandes.isis2304.alohandes.interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PanelInmuebles extends JFrame implements ActionListener{

	private JRadioButton btnHabitacion;
	private JRadioButton btnApto;
	private JRadioButton btnCasa;
	private JButton btnOk;
	private InterfazOperador interfaz;
	
	
	public PanelInmuebles(InterfazOperador interfaz) {
		btnHabitacion=new JRadioButton();
		btnApto= new JRadioButton();
		btnCasa= new JRadioButton();
		btnOk= new JButton();
		
		btnOk.addActionListener(this);
		
		JPanel panel= new JPanel();
		

		panel.add(btnHabitacion);
		btnHabitacion.setText("Habitacion");
		panel.add(btnApto);
		btnApto.setText("Apartamento");
		panel.add(btnCasa);
		btnCasa.setText("Casa");
		panel.add(btnOk);
		btnOk.setText("ok");
		panel.setSize(450,250);
		panel.setLayout(new GridLayout());
		
		this.interfaz=interfaz;
		int ancho=500;
		int alto= 300;
		setSize(ancho, alto);
		this.add(panel);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==btnOk) {
			interfaz.setVisible(true);
			this.setVisible(false);
			if (btnHabitacion.isSelected()) {
				interfaz.registroHabitacion();
			}else if (btnApto.isSelected()) {
				interfaz.registrarApto();
			}else if (btnCasa.isSelected()) {
				interfaz.registrarCasa();
			}
		}
	}

}
