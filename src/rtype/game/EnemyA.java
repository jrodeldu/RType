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

public class EnemyA extends Enemy{
	
	private static String ENEMY_SRC = "img/enemyA_s.png";
	
	/**
	 * Constructor de enemigos
	 */
	public EnemyA(int xPos, int yPos) {
		// TODO Auto-generated constructor stub
		super(xPos, yPos, ENEMY_SRC);
	}
	
	/**
	 * Movimiento constante de las naves
	 */
	public void move(){
		x -= 1;
		if(x <= 0-getWidth())
			x = 800;
	}
	
}
