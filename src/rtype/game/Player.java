package rtype.game;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * Clase Juagador encargada del comportamiento de la nave del jugador.
 * 
 * Permite movimiento con teclas Q,A,O,P: arriba, abajo, izquierda y derecha respectivamente
 * como también controles clásicos con las flechas de dirección.
 * 
 * Tecla espacio encargada de disparar.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */

public class Player {
	
	// Posiciones de la nave en los ejes x e y.
	private int x, y;
	// Desplazamiento horizontal.
	private int dx;
	// Desplazamiento vertical.
	private int dy;
	// Ancho y alto de la imagen.
	private int width, height;
	private Image shipImg;
	private static final String SHIP_IMG = "img/ship.png";
	// Disparos de la nave.
	private ArrayList<Bullet> bullets;
	// Visibilidad de la nave.
	private boolean visible;
	
	public Player(){
		visible = true;
		ImageIcon img = new ImageIcon(SHIP_IMG);
		shipImg = img.getImage();
		width = shipImg.getWidth(null);
		height = shipImg.getHeight(null);
		bullets = new ArrayList<Bullet>();
		x = 10;
		y = 172;
	}

	/**
	 * Movimiento horizontal y vertical de la nave.
	 */
	public void move(int maxWidth, int maxHeight){
		// System.out.println(maxWidth - width);
		// System.out.println(maxHeight - height);
		
		x = x + dx;
		y = y + dy;
		
		// Control de límites superior e izquierdo.
		if(x < 1) x = 1;
		if(y < 1) y = 1;
		
		// Control de límites inferior y derecho.
		if(x > maxWidth - width) x = maxWidth - width;
		if(y > maxHeight - height) y = maxHeight - height;
	}
	
	/**
	 * Getter de la posición X.
	 * @return posición X
	 */
	public int getX(){
		return x;
	}

	/**
	 * Getter de la posición Y.
	 * @return posición Y
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Getter de la imagen de la nave.
	 * @return Imagen.
	 */
	public Image getShipImage(){
		return shipImg;
	}
	
	/**
	 * Getter del listado de proyectiles disparados.
	 * @return ArrayList.
	 */
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	/**
	 * Devuelve la visibilidad de la nave.
	 * @return
	 */
	public boolean isVisible(){
		return visible;
	}
	
	/**
	 * Establece visibilidad de la nave.
	 * @param visible
	 */
	public void setVisible(boolean visible){
		this.visible = visible;
	}	
	
	/**
	 * Movimientos al pulsar los botones de dirección.
	 * Al presionar botones de dirección la nave se desplaza 1 punto
	 * en la dirección pulsada.
	 * @param e
	 */
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_O || key == KeyEvent.VK_LEFT){
			dx -= 1;  
		}
		
		if(key == KeyEvent.VK_P || key == KeyEvent.VK_RIGHT){
			dx += 1;  
		}
		
		if(key == KeyEvent.VK_Q || key == KeyEvent.VK_UP){
			dy -= 1;
		}
		
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_DOWN){
			dy += 1;
		}
		
		if (key == KeyEvent.VK_SPACE){
			fire();
		}
	}
	
	/**
	 * Se dispara un proyectil y se añade a la colección de disparos.
	 * Se crea un nuevo objeto Bullet con posición definita por una fórmula
	 * que recoge la posición x,y de la nave haciendo que el proyectil salga desde
	 * el lado derecho de la nave y a mitad de altura de la misma.
	 */
	private void fire() {
		// TODO Auto-generated method stub
		// System.out.println("Fuego!");
		bullets.add(new Bullet(x + width, y + height / 2));
		System.out.println(bullets.size());
	}

	/**
	 * Movimientos al soltar los botones de dirección.
	 * Al soltar los botones de dirección la nave deja de moverse. 
	 * @param e
	 */
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_O || key == KeyEvent.VK_P || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT){
			dx = 0;  
		}
		
		if(key == KeyEvent.VK_Q || key == KeyEvent.VK_A || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN){
			dy = 0;  
		}
	}	
	
}
