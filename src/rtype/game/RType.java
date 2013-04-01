package rtype.game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Clase RType
 * 
 * Es la clase principal, se inicializará creando un objeto JFrame
 * en el que se desarrollará el juego.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */

public class RType implements ActionListener{
	
	// Niveles de dificultad.
	private ArrayList<String> difficulty;
	private JFrame frameMain;
	
	/**
	 * Constructor e iniciación del juego.
	 * Creamos un objeto JFrame en el que se desarrollará el juego
	 */
	public RType(){
		frameMain = new JFrame();
		frameMain.setLayout(new GridLayout(5, 1));
		frameMain.setSize(300, 300);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frameMain.setTitle("RType - JRODELDU");
		
		difficulty = new ArrayList<String>();
		
		JLabel lblBienvenida = new JLabel("Jugar RType");
		lblBienvenida.setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);
		frameMain.add(lblBienvenida);
		
		JButton btnFacil = new JButton("FACIL");
		btnFacil.addActionListener(this);
		frameMain.add(btnFacil);
		difficulty.add(btnFacil.getText());
		
		JButton btnNormal = new JButton("NORMAL");
		btnNormal.addActionListener(this);
		frameMain.add(btnNormal);
		difficulty.add(btnNormal.getText());
		
		JButton btnComplicado = new JButton("COMPLICADO");
		btnComplicado.addActionListener(this);
		frameMain.add(btnComplicado);
		difficulty.add(btnComplicado.getText());
		
		JButton btnImposible = new JButton("IMPOSIBLE");
		btnImposible.addActionListener(this);
		frameMain.add(btnImposible);
		difficulty.add(btnImposible.getText());
		
		frameMain.setVisible(true);

	}
	
	public void jugar(int difficulty){
		JFrame frame = new JFrame();
		
		// Configuración del frame.
		frame.setTitle("RType - JRODELDU");

		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new Nivel(difficulty));
		// Hacemos visitble el frame.
		frame.setVisible(true);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// Jugamos pasando el índice de dificultad.
		frameMain.dispose();
		jugar(difficulty.indexOf(e.getActionCommand()));
	}
	
	public static void main(String[] args){
		new RType();
	}
	
}
