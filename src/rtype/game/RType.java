package rtype.game;

import javax.swing.JFrame;

/**
 * Clase RType
 * 
 * Es la clase principal, se inicializará creando un objeto JFrame
 * en el que se desarrollará el juego.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */

public class RType{
	
	/**
	 * Constructor e iniciación del juego.
	 * Creamos un objeto JFrame en el que se desarrollará el juego
	 */
	public RType(){
		JFrame frame = new JFrame();
		
		// Configuración del frame.
		frame.setTitle("RType - JRODELDU");
		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new Level());
		// Hacemos visitble el frame.
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		new RType();
	}
	
}
