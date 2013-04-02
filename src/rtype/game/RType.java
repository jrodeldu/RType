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
 * Es la clase principal de la aplicación, lanzará una interfaz sencilla
 * con botones para elegir nivel de dificultad y acto seguido creará un frame
 * en el que añadirá un objeto Nivel en el que se desarrollará el juego.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */

public class RType implements ActionListener{
	
	// Niveles de dificultad.
	private ArrayList<String> dificultad;
	private JFrame frameMenu;
	
	/**
	 * Constructor e iniciación del juego.
	 * Creamos un objeto JFrame en el que se desarrollará el juego
	 */
	public RType(){
		frameMenu = new JFrame();
		frameMenu.setLayout(new GridLayout(5, 1));
		frameMenu.setSize(300, 300);
		frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frameMenu.setTitle("RType - JRODELDU");
		
		dificultad = new ArrayList<String>();
		
		JLabel lblBienvenida = new JLabel("Jugar RType - Seleccione dificultad");
		lblBienvenida.setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);
		frameMenu.add(lblBienvenida);
		
		JButton btnFacil = new JButton("FACIL");
		btnFacil.addActionListener(this);
		frameMenu.add(btnFacil);
		dificultad.add(btnFacil.getText());
		
		JButton btnNormal = new JButton("NORMAL");
		btnNormal.addActionListener(this);
		frameMenu.add(btnNormal);
		dificultad.add(btnNormal.getText());
		
		JButton btnComplicado = new JButton("COMPLICADO");
		btnComplicado.addActionListener(this);
		frameMenu.add(btnComplicado);
		dificultad.add(btnComplicado.getText());
		
		JButton btnImposible = new JButton("IMPOSIBLE");
		btnImposible.addActionListener(this);
		frameMenu.add(btnImposible);
		dificultad.add(btnImposible.getText());
		
		// Situamos la ventana en el centro de la pantalla. Al pasar null se establece centrado.
		frameMenu.setLocationRelativeTo(null);
		frameMenu.setVisible(true);

	}
	
	/**
	 * Se crea un frame donde se cargará el panel de juego.
	 * 
	 * @param dificultad
	 */
	public void jugar(int dificultad){
		JFrame frame = new JFrame();
		
		// Configuración del frame.
		frame.setTitle("RType - JRODELDU");

		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new Nivel(dificultad));
		// Hacemos visitble el frame y centrado.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	}
	
	/**
	 * Action Listener para los botones de la interfaz principal.
	 * Cerraremos el frame principal y se llamará a la función jugar.
	 * Justo en la llamada a jugar se pasará el valor entero correspondiente
	 * al botón de dificultad seleccionado.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// Jugamos pasando el índice de dificultad.
		frameMenu.dispose();
		jugar(dificultad.indexOf(e.getActionCommand()));
	}
	
	public static void main(String[] args){
		new RType();
	}
	
}
