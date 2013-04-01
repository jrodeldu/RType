package rtype.game;

/**
 * Clase de los enemigos tipo A del juego.
 * 
 * La nave no efectúa ningún disparo, su movimiento
 * es constante de derecha a izquierda y va variando
 * su eje Y de forma aleatoria.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */

public class EnemigoB extends Enemigo{

	private static final String SRC_IMG_ENEMIGO = "img/enemyB_s.png";
	
	/**
	 * Constructor de enemigos
	 */
	public EnemigoB(int posX, int posY, int velocidad) {
		// TODO Auto-generated constructor stub
		super(posX, posY, SRC_IMG_ENEMIGO, velocidad);
	}
	
	/**
	 * Movimiento constante de las naves
	 * El movimiento horizontal es constante.
	 * La principal diferencia es que pueden moverse en el eje Y
	 * @param dy desplazamiento aleatorio en ejeY generado.
	 */
	public void mover(int dy){		
		x -= velocidad;
		y += dy;
		
		if(x < 0-getAncho())
			x = 800;
	}
	
}
