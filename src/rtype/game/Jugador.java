package rtype.game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Clase Juagador encargada del comportamiento de la nave del jugador.
 * 
 * Permite movimiento con teclas Q,A,O,P: arriba, abajo, izquierda y derecha respectivamente
 * como también controles clásicos con las flechas de dirección.
 * 
 * La nave se puede mover por toda la ventana sin salir de los límites.
 * 
 * Tecla espacio encargada de disparar.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */

public class Jugador extends Nave{
	
	private static final int VELOCIDAD_JUGADOR = 1;
	private static final String SRC_IMG_NAVE = "img/ship.png";
	// Desplazamiento horizontal.
	private int dx;
	// Desplazamiento vertical.
	private int dy;
	// Disparos de la nave.
	private ArrayList<Bala> balas;
	// Retardo del último disparo.
	private long ultimoDisparo = 0;	
	
	public Jugador(){
		super(10, 172, SRC_IMG_NAVE, VELOCIDAD_JUGADOR);
		balas = new ArrayList<Bala>();
	}

	/**
	 * Movimiento horizontal y vertical de la nave.
	 */
	public void mover(Nivel nivel){
		// x += dx;
		// y += dy;
		setX(getX() + dx);
		setY(getY() + dy);
		
		// Control de límites superior e izquierdo.
		if(getX() < 0) setX(0);
		if(getY() < 0) setY(0);
		
		// Control de límites inferior y derecho.
		if(getX() > nivel.getWidth() - getAncho()){
			setX(nivel.getWidth() - getAncho());
			// System.out.println("Te sales del eje X, tu posición en el eje X es: " + getX() + " Recolocado en X: " + x);
		}
		if(getY() > nivel.getHeight() - getAlto()){
			setY(nivel.getHeight() - getAlto());
			// System.out.println("Te sales del eje Y, tu posición en el eje Y es: " + getY() + " Recolocado en Y: " + y);
		}
	}
	
	/**
	 * @return ArrayList con las balas disparadas.
	 */
	public ArrayList<Bala> getBalas(){
		return balas;
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
			dx -= VELOCIDAD_JUGADOR;  
		}
		
		if(key == KeyEvent.VK_P || key == KeyEvent.VK_RIGHT){
			dx += VELOCIDAD_JUGADOR;  
		}
		
		if(key == KeyEvent.VK_Q || key == KeyEvent.VK_UP){
			dy -= VELOCIDAD_JUGADOR;
		}
		
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_DOWN){
			dy += VELOCIDAD_JUGADOR;
		}
		
		if (key == KeyEvent.VK_SPACE){
			// Incluimos un retardo para que el disparo tenga un ritmo continuo y sin soltar ráfagas con balas superpuestas.
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
	 * el lado derecho de la nave y a mitad de altura de la misma que es dónde se situa el cañon.
	 */
	private void disparar() {
		if(getVisible()) balas.add(new Bala(getX() + getAncho(), getY() + getAlto() / 2));
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
