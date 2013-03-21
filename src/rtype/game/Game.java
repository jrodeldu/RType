package rtype.game;

import javax.swing.JFrame;

/**
 * Clase Game
 * 
 * Es la clase principal, se inicializará creando un objeto JFrame
 * en el que se desarrollará el juego.
 * 
 * @author jrodeldu
 *
 */

public class Game{
	
	/**
	 * Constructor e iniciación del juego.
	 * Creamos un objeto JFrame en el que se desarrollará el juego
	 */
	public Game(){
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
		new Game();
	}
	
}
