package rtype.game;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Clase Bullet. 
 * 
 * Proyectiles/Balas del misil.
 * 
 * Recorren el eje horizontal sin alterar su trayectoria.
 * Se perderán en el eje si no impactan con ningún enemigo.
 * 
 * @author johnny
 *
 */

public class Bullet {

	// Posición de la bala.
	private static int x;
	private static int y;
	private static final int BULLET_SPEED = 5;
	private Image bulletImg;
	private String bulletSrc = "img/bullet.png";
	
	/**
	 * Constructor del objeto.
	 * La bala saldrá desde la posición x e y que tenía la nave al disparar
	 */
	public Bullet(int x, int y) {
		// TODO Auto-generated constructor stub
		// Posición incial de la bala.
		this.x = x;
		this.y = y;
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
}
