package rtype.game;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * Clase Nave.
 * 
 * Es la clase de la que heradan los dintintos tipos de naves, tanto jugador como enemigos.
 * Comparten la mayoría de los métodos salvo los movimientos que están definidos en las distintas
 * clases hijas.
 * 
 * El resto de métodos y propiedades no varía salvo la imágen que representa
 * las naves enemigas.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */
public abstract class Nave {
	
	private int x;
	private int y;
	private boolean visible;
	private int ancho, alto;
	private Image imgNave;
	private int velocidad;
	private String tipoNave;
	
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
	 * @return valocidad de movimiento.
	 */
	public int getVelocidad() {
		return velocidad;
	}
	
	/**
	 * @return tipo de Nave del objeto. Usado para diferenciar entre naves enemigas.
	 */
	public String getTipoNave() {
		return tipoNave;
	}
	
	/**
	 * Establece posición en el eje X del objeto
	 * @param x Posición en eje X
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Establece posición en el eje Y del objeto
	 * @param y Posición en eje Y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Establece valor visible de la nave.
	 * @param visible: si está vivo será true, si no, falso.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setTipoNave(String tipoNave) {
		this.tipoNave = tipoNave;
	}

	/**
	 * Creamos un rectángulo que rodee el elemento tomando su posición en
	 * los ejes (x,y) y el tamaño de la imagen.
	 * @return rectangle límites del elemento para detección de colisiones.
	 */
	public Rectangle getBordes(){
		return new Rectangle(getX(), getY(), getAncho(), getAncho());
	}
	
	/**
	 * Método para el movimiento de las naves.
	 * @param nivel con él controlaremos los límites del desplazamiento permitido.
	 */
	public abstract void mover(Nivel nivel);
}
