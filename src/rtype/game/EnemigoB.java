package rtype.game;

import java.util.Random;

/**
 * Clase de los enemigos tipo B del juego.
 * 
 * La nave no efectúa ningún disparo, su movimiento
 * es constante de derecha a izquierda y va variando
 * su eje Y de forma aleatoria.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */

public class EnemigoB extends Nave{

	private static final String SRC_IMG_ENEMIGO = "img/enemyB_s.png";
	private boolean subiendo; // Sentido ascendente de la nave
	private int variacionY, dy; // Variacion vertical de la nave y desplazamiento efectuado dentro de la variacion.
	
	/**
	 * Constructor de enemigos
	 */
	public EnemigoB(int posX, int posY, int velocidad) {
		// TODO Auto-generated constructor stub
		super(posX, posY, SRC_IMG_ENEMIGO, velocidad);
		super.setTipoNave("B");
		subiendo = true; // Todas las naves empiezan subiendo.
		variacionY = new Random().nextInt(100)+50; // Random de desplazamiento.
		dy = variacionY;
	}
	
	/**
	 * Movimiento constante de las naves
	 * El movimiento horizontal es constante.
	 * La principal diferencia es que pueden moverse en el eje Y
	 * @param dy desplazamiento aleatorio en ejeY generado.
	 */
	public void mover(Nivel nivel){		
		
		// Si la nave sube
		if (getSubiendo()) {
			setY(getY() - 1); 
			dy--;
			// Si ha realizado su recorrido en su rango de variación en ejeY se cambia el sentido.
			if (dy == 0) {
				setSubiendo(false);
			}
		}else{
			setY(getY() + 1);
			dy++;
			// Si ha realizado su recorrido en su rango de variación en ejeY se cambia el sentido.
			if (dy == variacionY) {
				setSubiendo(true);
			}
		}
		
		// Desplazamiento constante en eje X.
		setX(getX() - getVelocidad());

		// Recolocar la nave en la derecha una vez llegado a la izquierda.
		if(getX() < 0-getAncho()) setX(nivel.getWidth());
		// Control límite superior eje Y
		if(getY() < 0) setY(0);
		// Control límite inferior eje Y
		if(getY() > nivel.getHeight() - getAlto()) 
			setY(nivel.getHeight() - getAlto());
	}
	
	/**
	 * Recupera el sentido del desplazamiento vertical de la nave.
	 * @return subiendo: la nave sube o baja
	 */
	public boolean getSubiendo() {
		return subiendo;
	}
	
	/**
	 * Establecemos si la nave sube o baja.
	 * @param subiendo
	 */
	public void setSubiendo(boolean subiendo) {
		this.subiendo = subiendo;
	}
	
}
