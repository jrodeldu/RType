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

public class EnemyB extends Enemy{

	private static final String ENEMY_SRC = "img/enemyB_s.png";
	
	/**
	 * Constructor de enemigos
	 */
	public EnemyB(int xPos, int yPos, double speed) {
		// TODO Auto-generated constructor stub
		super(xPos, yPos, ENEMY_SRC, speed);
	}
	
	/**
	 * Movimiento constante de las naves
	 * El movimiento horizontal es constante.
	 * La principal diferencia es que pueden moverse en el eje Y
	 * @param dy desplazamiento aleatorio en ejeY generado.
	 */
	public void move(int dy){		
		x += speed;
		y += dy;
		
		if(x > 800)
			x = -200;
	}
	
}
