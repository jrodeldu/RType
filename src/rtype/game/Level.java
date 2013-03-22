package rtype.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
	private Image backgroundImg;
	private Timer time;
	private String backgroundSrc = "img/space.png";
	private static final int DELAY = 5; // Retardo para el Timer
	
	/**
	 * Constructor e inicialización.
	 */
	public Level() {
		// TODO Auto-generated constructor stub
		 player = new Player();
		 
		 // Timer inicializado a 5ms.
		 time = new Timer(DELAY, this);
		 
		 // Añadimos al panel el listener de eventos de teclado. Clase interna (Inner class) creada al final.
		 addKeyListener(new keyListener());
		 
		 // Extendemos de JPanel y establecemos foco en el elemento para que pueda reaccionar a eventos de teclado.
		 setFocusable(true);
		 
		 // Establecemos fondo.
		 ImageIcon img = new ImageIcon(backgroundSrc);
		 backgroundImg = img.getImage();
		 
		 // Iniciamos timer.
		 time.start();
	}
	
	/**
	 * Función llamada cada 5 ms
	 * Se realizará el movimiento de la nave en pantalla.
	 * 
	 * Esta función es necesaria al implementar la interfaz ActionListener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// System.out.println(player.getY());
		// System.out.println(player.getX());
		
		// Mover misiles.
		ArrayList<Bullet> bulletsAL = player.getBullets();
		for (int i = 0; i < bulletsAL.size(); i++) {
			Bullet b = bulletsAL.get(i);
			if(b.getBulletVisible()){
				b.move(getWidth());
			}else{
				bulletsAL.remove(i);
			}
		}
		
		// Movemos la nave y pasamos los límites del panel.
		player.move(getWidth(), getHeight());
		
		repaint();
	}
	
	/**
	 * Función a la que se llama para redibujar la pantalla
	 * del juego con las posiciones actualizadas.
	 */
	public void paint(Graphics g){
		// System.out.println("Llamada a paint!!");
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		ArrayList<Bullet> bulletsAL = player.getBullets();
		
		// Dibujamos fondo.
		g2d.drawImage(backgroundImg, 0, 0, null);
		
		// Dibujamos nave.
		g2d.drawImage(player.getShipImage(), player.getX(), player.getY(), null);
		
		// Dibujamos proyectiles.
		for (int i = 0; i < bulletsAL.size(); i++) {
			System.out.println("dibujo bala " + i);
			Bullet b = bulletsAL.get(i);
			//System.out.println("PosX: " + b.getX() + " PosY: " + b.getY());
			g2d.drawImage(b.getImage(), b.getX(), b.getY(), null);
		}
	}
	
	
	/**
	 * Clase interna que hará las funciones de Listener
	 * sobre los eventos del teclado.
	 * 
	 * @author jrodeldu
	 *
	 */
	private class keyListener extends KeyAdapter{
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
