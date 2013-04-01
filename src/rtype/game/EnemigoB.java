package rtype.game;

/**
 * Clase de los enemigos tipo A del juego.
 * Las naves enemigas son de 2 tipos (A y B)
 * 
 * Ningún enemigo efectúa disparos. Simplemente se mueven en su eje X
 * y si llegan al final de la pantalla vivos vuelven a aparecer.
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
		x += velocidad;
		y += dy;
		
		if(x > 800)
			x = 0;
	}
	
}
