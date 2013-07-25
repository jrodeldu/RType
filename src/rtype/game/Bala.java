package rtype.game;

/**
 * Clase Bala. 
 * 
 * Disparos de la nave.
 * 
 * Recorren el eje horizontal sin alterar su trayectoria.
 * Se perderán en el eje si no impactan con ningún enemigo.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 *
 */

public class Bala extends Nave{


	private static final int VELOCIDAD_BALA = 3;
	private static final String SRC_IMG_BALA = "img/bullet.png";
	
	/**
	 * Constructor del objeto.
	 * La bala saldrá desde la posición x e y que tenía la nave al disparar.
	 * 
	 * @param x: Será el ancho total de la nave.
	 * @param y: Será igual a la mitad de la altura de la nave.
	 */
	public Bala(int x, int y) {
		super(x, y, SRC_IMG_BALA, VELOCIDAD_BALA);
	}
	
	/**
	 * Movimiento horizontal del proyectil.
	 * @param maxWidth: Ancho máximo de la pantalla
	 */
	public void mover(Nivel nivel){
		setX(getX() + VELOCIDAD_BALA);
		// Control de límite. Si se sale se cambia visibilidad del proyectil.
		if(getX() > nivel.getWidth()) setVisible(false);
	}

}
