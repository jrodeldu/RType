package rtype.game;

import java.awt.Image;
import java.awt.Rectangle;
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

public class Jugador {
	
	// Posiciones de la nave en los ejes x e y.
	private int x, y;
	// Desplazamiento horizontal.
	private int dx;
	// Desplazamiento vertical.
	private int dy;
	// Ancho y alto de la imagen.
	private int ancho, alto;
	private Image imgNave;
	private static final String SRC_IMG_NAVE = "img/ship.png";
	// Disparos de la nave.
	private ArrayList<Bala> balas;
	// Visibilidad de la nave.
	private boolean visible;
	// Retardo del último disparo.
	private long ultimoDisparo = 0;	
	
	public Jugador(){
		visible = true;
		ImageIcon img = new ImageIcon(SRC_IMG_NAVE);
		imgNave = img.getImage();
		ancho = imgNave.getWidth(null);
		alto = imgNave.getHeight(null);
		balas = new ArrayList<Bala>();
		x = 10;
		y = 172;
	}

	/**
	 * Movimiento horizontal y vertical de la nave.
	 */
	public void mover(int maxWidth, int maxHeight){
		// System.out.println(maxWidth - width);
		// System.out.println(maxHeight - height);
		
		x = x + dx;
		y = y + dy;
		
		// Control de límites superior e izquierdo.
		if(x < 1) x = 1;
		if(y < 1) y = 1;
		
		// Control de límites inferior y derecho.
		if(x > maxWidth - ancho) x = maxWidth - ancho;
		if(y > maxHeight - alto) y = maxHeight - alto;
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
		return imgNave;
	}
	
	/**
	 * Getter del listado de proyectiles disparados.
	 * @return ArrayList.
	 */
	public ArrayList<Bala> getBalas(){
		return balas;
	}
	
	/**
	 * Devuelve la visibilidad de la nave.
	 * @return
	 */
	public boolean getVisible(){
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
			// Incluimos un retardo para que el disparo tenga un ritmo continuo y sin soltar ráfagas con balas sin separación.
			if (System.currentTimeMillis() - ultimoDisparo > 175) {
				ultimoDisparo = System.currentTimeMillis();
				disparar();
			}
		}
	}
	
	/**
	 * Si la nave está visible dispara un proyectil y se añade a la colección de disparos.
	 * Se crea un nuevo objeto Bullet con posición definita por una fórmula
	 * que recoge la posición x,y de la nave haciendo que el proyectil salga desde
	 * el lado derecho de la nave y a mitad de altura de la misma.
	 */
	private void disparar() {
		// TODO Auto-generated method stub
		if(getVisible()) balas.add(new Bala(x + ancho, y + alto / 2));
		// System.out.println(balas.size());
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
	
	/**
	 * Creamos un rectángulo que rodee el elemento tomando su posición en
	 * los ejes (x,y) y el tamaño de la imagen.
	 * @return rectangle límites del elemento para detección de colisiones.
	 */
	public Rectangle getBordes(){
		return new Rectangle(getX(), getY(), ancho, alto);
	}
	
}
