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

public class EnemigoA extends Enemigo{
	
	private static String SRC_IMG_ENEMIGO = "img/enemyA_s.png";
	
	/**
	 * Constructor de enemigos
	 */
	public EnemigoA(int posX, int posY, int velocidad) {
		// TODO Auto-generated constructor stub
		super(posX, posY, SRC_IMG_ENEMIGO, velocidad);
	}
	
	/**
	 * Movimiento constante de las naves
	 */
	public void mover(){
		x -= velocidad;
		if(x < 0-getAncho())
			x = 800;
	}
	
}
