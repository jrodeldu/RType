package rtype.game;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * Clase Bullet. 
 * 
 * Proyectiles de la nave.
 * 
 * Recorren el eje horizontal sin alterar su trayectoria.
 * Se perderán en el eje si no impactan con ningún enemigo.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */

public class Bullet {

	// Posición del proyectil.
	private int x, y;
	// Alto y ancho del proyectil.
	private int width, height;
	private static final int BULLET_SPEED = 3;
	private boolean visible;
	private Image bulletImg;
	private static final String BULLET_IMG = "img/bullet.png";
	
	/**
	 * Constructor del objeto.
	 * La bala saldrá desde la posición x e y que tenía la nave al disparar.
	 * @param x: Será el ancho total de la nave.
	 * @param y: Será igual a la mitad de la altura de la nave.
	 */
	public Bullet(int x, int y) {
		// TODO Auto-generated constructor stub
		// Posición incial del proyectil.
		this.x = x;
		this.y = y;
		visible = true;
		// Imágen
		ImageIcon img = new ImageIcon(BULLET_IMG);
		bulletImg = img.getImage();
		width = bulletImg.getWidth(null);
		height = bulletImg.getHeight(null);
	}
	
	/**
	 * Getter eje X.
	 * @return posición X
	 */
	public int getX(){
		return x;
	}

	/**
	 * Getter eje y.
	 * @return posición y.
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Getter Imagen.
	 * @return imagen del proyectil.
	 */
	public Image getImage(){
		return bulletImg;
	}
	
	/**
	 * Devuelve la visibilidad del proyectil.
	 * @return
	 */
	public boolean isVisible(){
		return visible;
	}
	
	/**
	 * Establece visibilidad del proyectil.
	 * @param visible
	 */
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	/**
	 * Movimiento horizontal del proyectil.
	 * @param maxWidth: Ancho máximo de la pantalla
	 */
	public void move(int maxWidth){
		x += BULLET_SPEED;
		// Control de límite. Si se sale se cambia visibilidad del proyectil.
		if(x > maxWidth) setVisible(false);
	}
	
	/**
	 * Creamos un rectángulo que rodee el proyectil tomando su posición en
	 * los ejes (x,y) y el tamaño de la imagen.
	 * @return rectangle límites del elemento para detección de colisiones.
	 */
	public Rectangle getBounds(){
		return new Rectangle(getX(), getY(), width, height);
	}
}
