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
public class Nave {
	
	protected int x;
	protected int y;
	private boolean visible;
	private int ancho, alto;
	private Image imgNave;
	protected int velocidad;
	
	/**
	 * Constructor de enemigos
	 * @param xPos posición inicial en el eje X
	 * @param yPos posición inicial en el eje Y
	 * @param srcImgNave imágen de la nave.
	 */
	public Nave(int xPos, int yPos, String srcImgNave, int velocidad) {
		// TODO Auto-generated constructor stub
		visible = true;
		// Imágen
		ImageIcon img = new ImageIcon(srcImgNave);
		imgNave = img.getImage();
		ancho = imgNave.getWidth(null);
		alto = imgNave.getHeight(null);
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
	 * Establece valor visible de la nave.
	 * @param visible: si está vivo será true, si no, falso.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return imagen de la nave.
	 */
	public Image getImagen() {
		return imgNave;
	}

	/**
	 * @return ancho de la imagen de la nave.
	 */
	public int getAncho() {
		return ancho;
	}
	
	/**
	 * @return alto de la imagen de la nave.
	 */
	public int getAlto(){
		return alto;
	}
	
	/**
	 * Creamos un rectángulo que rodee el elemento tomando su posición en
	 * los ejes (x,y) y el tamaño de la imagen.
	 * @return rectangle límites del elemento para detección de colisiones.
	 */
	public Rectangle getBordes(){
		return new Rectangle(getX(), getY(), getAncho(), getAncho());
	}
}
