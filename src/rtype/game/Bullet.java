package rtype.game;

import java.awt.Image;

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
	private int x;
	private int y;
	private static final int BULLET_SPEED = 3;
	private boolean visible;
	private Image bulletImg;
	private String bulletSrc = "img/bullet.png";
	
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
		ImageIcon img = new ImageIcon(bulletSrc);
		bulletImg = img.getImage();
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
	public boolean getBulletVisible(){
		return visible;
	}
	
	/**
	 * Establece visibilidad del proyectil.
	 * @param visible
	 */
	public void setBulletVisible(boolean visible){
		this.visible = visible;
	}
	
	/**
	 * Movimiento horizontal del proyectil.
	 * @param maxWidth: Ancho máximo de la pantalla
	 */
	public void move(int maxWidth){
		x += BULLET_SPEED;
		// Control de límite. Si se sale se cambia visibilidad del proyectil.
		if(x > maxWidth) setBulletVisible(false);
	}	
}
