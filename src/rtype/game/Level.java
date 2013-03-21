package rtype.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * Es clase se refiere al contenedor de elementos.
 * Extiende de JPanel.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 * @version 1.0
 *
 */

public class Level extends JPanel implements ActionListener{
	
	private Player player;
	private Image background;
	private Timer time;
	//private String srcBackground = "img/space.png";
	
	/**
	 * Constructor e inicialización.
	 */
	public Level() {
		// TODO Auto-generated constructor stub
		 player = new Player();
		 
		 // Timer inicializado a 5ms.
		 time = new Timer(5, this);
		 
		 // Añadimos el listener que hemos creado más abajo.
		 addKeyListener(new MyActionListener());
		 
		 // Extendemos de JPanel y establecemos foco en el elemento para que pueda reaccionar a eventos de teclado.
		 setFocusable(true);
		 
		 // Establecemos fondo.
		 ImageIcon img = new ImageIcon("img/space.png");
		 background = img.getImage();
		 
		 // Iniciamos timer.
		 time.start();
	}
	
	/**
	 * Función llamada cada 5 ms
	 * Se realizará el movimiento de la nave en pantalla.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(player.getY());
		System.out.println(player.getX());
		
		// Movemos la nave y pasamos los límites del panel.
		player.move(getWidth(), getHeight());
		repaint();
	}
	
	public void paint(Graphics g){
		System.out.println("Llamada a paint!!");
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(background, 0, 0, null);
		g2d.drawImage(player.getShipImage(), player.getX(), player.getY(), null);
	}
	
	
	/**
	 * Clase interna que hará las funciones de Listener
	 * sobre los eventos del teclado.
	 * 
	 * @author jrodeldu
	 *
	 */
	private class MyActionListener extends KeyAdapter{
		// Tecla soltada
		public void keyReleased(KeyEvent e){
			player.keyReleased(e);
		}
		
		// Tecla pulsada
		public void keyPressed(KeyEvent e){
			player.keyPressed(e); 
		}
	}
	
}
