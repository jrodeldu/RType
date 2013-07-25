package rtype.game;

/**
 * Clase de los enemigos tipo A del juego.
 * 
 * La nave no efectúa ningún disparo, su movimiento
 * es constante de derecha a izquierda sin variar su eje Y.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */

public class EnemigoA extends Nave{
	
	private static String SRC_IMG_ENEMIGO = "img/enemyA_s.png";
	
	/**
	 * Constructor de enemigos
	 */
	public EnemigoA(int posX, int posY, int velocidad) {
		// TODO Auto-generated constructor stub
		super(posX, posY, SRC_IMG_ENEMIGO, velocidad);
		super.setTipoNave("A");
	}

	/**
	 * Movimiento constante de las naves
	 */
	public void mover(Nivel nivel){
		setX(getX() - getVelocidad());
		if(getX() < 0-getAncho())
			setX(nivel.getWidth());
	}
	
}
