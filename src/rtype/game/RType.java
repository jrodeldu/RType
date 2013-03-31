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
	
	/**
	 * Constructor e iniciación del juego.
	 * Creamos un objeto JFrame en el que se desarrollará el juego
	 */
	public RType(){
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(5, 1));
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setTitle("RType - JRODELDU");
		
		difficulty = new ArrayList<String>();
		
		JLabel lblBienvenida = new JLabel("Jugar RType");
		lblBienvenida.setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);
		frame.add(lblBienvenida);
		
		JButton btnFacil = new JButton("FACIL");
		btnFacil.addActionListener(this);
		frame.add(btnFacil);
		difficulty.add(btnFacil.getText());
		
		JButton btnNormal = new JButton("NORMAL");
		btnNormal.addActionListener(this);
		frame.add(btnNormal);
		difficulty.add(btnNormal.getText());
		
		JButton btnComplicado = new JButton("COMPLICADO");
		btnComplicado.addActionListener(this);
		frame.add(btnComplicado);
		difficulty.add(btnComplicado.getText());
		
		JButton btnImposible = new JButton("IMPOSIBLE");
		btnImposible.addActionListener(this);
		frame.add(btnImposible);
		difficulty.add(btnImposible.getText());
		
		frame.setVisible(true);
	}
	
	public void play(int difficulty){
		JFrame frame = new JFrame();
		
		// Configuración del frame.
		frame.setTitle("RType - JRODELDU");

		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new Level(difficulty));
		// Hacemos visitble el frame.
		frame.setVisible(true);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// Jugamos pasando el índice de dificultad.
		play(difficulty.indexOf(e.getActionCommand()));
	}
	
	public static void main(String[] args){
		new RType();
	}
	
}
