package rtype.game;

public class EnemyB extends Enemy{

	private static final String ENEMY_SRC = "img/enemyB.png";
	
	/**
	 * Constructor de enemigos
	 */
	public EnemyB(int xPos, int yPos) {
		// TODO Auto-generated constructor stub
		super(xPos, yPos, ENEMY_SRC);
	}
	
	/**
	 * Movimiento constante de las naves
	 */
	public void move(){
		x += 1;
		if(x > 800)
			x = -200;
	}
	
}
