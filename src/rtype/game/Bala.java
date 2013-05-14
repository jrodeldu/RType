package rtype.game;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * Clase Bala. 
 * 
 * Disparos de la nave.
 * 
 * Recorren el eje horizontal sin alterar su trayectoria.
 * Se perderán en el eje si no impactan con ningún enemigo.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */

public class Bala {

	// Posición inicial de la bala.
	private int x, y;
	// Alto y ancho de la bala.
	private int ancho, alto;
	private static final int VELOCIDAD_BALA = 3;
	private boolean visible;
	private Image imgBala;
	private static final String SRC_IMG_BALA = "img/bullet.png";
	
	/**
	 * Constructor del objeto.
	 * La bala saldrá desde la posición x e y que tenía la nave al disparar.
	 * 
	 * @param x: Será el ancho total de la nave.
	 * @param y: Será igual a la mitad de la altura de la nave.
	 */
	public Bala(int x, int y) {
		// Posición incial del proyectil.
		this.x = x;
		this.y = y;
		visible = true;
		// Imágen
		ImageIcon img = new ImageIcon(SRC_IMG_BALA);
		imgBala = img.getImage();
		ancho = imgBala.getWidth(null);
		alto = imgBala.getHeight(null);
	}
	
	/**
	 * @return x posición eje X
	 */
	public int getX(){
		return x;
	}

	/**
	 * @return y posición eje Y.
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * @return imgBala imagen del proyectil.
	 */
	public Image getImagen(){
		return imgBala;
	}
	
	/**
	 * @return visible
	 */
	public boolean getVisible(){
		return visible;
	}
	
	/**
	 * Establece posición en el eje X del objeto
	 * @param x Posición en eje X
	 */
	public void setX(int x) {
		this.x = x;
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
	public void mover(Nivel nivel){
		setX(getX() + VELOCIDAD_BALA);
		// Control de límite. Si se sale se cambia visibilidad del proyectil.
		if(getX() > nivel.getWidth()) setVisible(false);
	}
	
	/**
	 * Creamos un rectángulo que rodea la bala tomando su posición en
	 * los ejes (x,y) y el tamaño de la imagen.
	 * 
	 * @return rectangle límites del elemento para detección de colisiones.
	 */
	public Rectangle getBordes(){
		return new Rectangle(getX(), getY(), ancho, alto);
	}
}
