package rtype.game;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Clase de los enemigos del juego.
 * Las naves enemigas son de 2 tipos (A y B)
 * 
 * Ningún enemigo efectúa disparos. Simplemente se mueven en su eje X
 * y si llegan al final de la pantalla vivos vuelven a aparecer.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */

public class Enemy{
	
	private int x;
	private int y;
	private int width, height;
	private Image enemyImg;
	private String enemySrc = "img/enemyA.png";
	private boolean visible;
	//private 
	
	/**
	 * Constructor de enemigos
	 */
	public Enemy() {
		// TODO Auto-generated constructor stub
		visible = true;
		ImageIcon img = new ImageIcon(enemySrc);
		enemyImg = img.getImage();
		width = enemyImg.getWidth(null);
		height = enemyImg.getHeight(null);
		// Generar posición aleatoriamente.
		x = 520;
		y = 150;
	}
	
	/**
	 * Recoge valor de la nave en el eje horizontal
	 * @return valor ejeX
	 */
	public int getX() {
		return x;
	}

	/**
	 * Establece posición en eje horizontal del enemigo
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Recoge valor de la nave en el eje vertical
	 * @return valor ejeY
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Establece posición en eje vertical del enemigo
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Recupera valor de visibilidad del enemigo
	 * @return
	 */
	public boolean isVisible() {
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
	public Image getImage() {
		return enemyImg;
	}
	
	
	
}
