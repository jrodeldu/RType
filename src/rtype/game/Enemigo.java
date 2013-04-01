package rtype.game;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * Clase de naves enemigas.
 * 
 * Es el modelo base para los distintos tipos de enemigos
 * que en este caso tendrán distinto comportamiento en el desplazamiento.
 * 
 * El resto de métodos y propiedades no varía salvo la imágen que representa
 * las naves enemigas.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */
public class Enemigo {
	
	protected int x;
	protected int y;
	private boolean visible;
	private int ancho, alto;
	private Image imgEnemigo;
	protected int velocidad;
	
	/**
	 * Constructor de enemigos
	 * @param xPos posición inicial en el eje X
	 * @param yPos posición inicial en el eje Y
	 * @param enemySrc imágen de la nave.
	 */
	public Enemigo(int xPos, int yPos, String enemySrc, int velocidad) {
		// TODO Auto-generated constructor stub
		visible = true;
		// Imágen
		ImageIcon img = new ImageIcon(enemySrc);
		imgEnemigo = img.getImage();
		ancho = imgEnemigo.getWidth(null);
		alto = imgEnemigo.getHeight(null);
		// Generar posición aleatoriamente.
		x = xPos;
		y = yPos;
		this.velocidad = velocidad;
	}
	
	/* Getters y Setters */
	
	/**
	 * Recoge valor de la nave en el eje horizontal
	 * @return valor ejeX
	 */
	public int getX() {
		return x;
	}

	/**
	 * Recoge valor de la nave en el eje vertical
	 * @return valor ejeY
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Recupera valor de visibilidad del enemigo
	 * @return
	 */
	public boolean getVisible() {
		return visible;
	}

	/**
	 * Establece valor visible del enemigo.
	 * @param visible: si está vivo será true, si no, falso.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Getter Image del enemigo
	 * @return imagen de la nave enemiga.
	 */
	public Image getImagen() {
		return imgEnemigo;
	}

	/**
	 * @return width de la imagen de la nave enemiga.
	 */
	public int getAncho() {
		return ancho;
	}
	
	/**
	 * Creamos un rectángulo que rodee el elemento tomando su posición en
	 * los ejes (x,y) y el tamaño de la imagen.
	 * @return rectangle límites del elemento para detección de colisiones.
	 */
	public Rectangle getBordes(){
		return new Rectangle((int) getX(), getY(), ancho, alto);
	}
}
