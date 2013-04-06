package rtype.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Esta en la clase del que contendrá el nivel del juego.
 * Se dibujarán todos los elementos y se recogerán sus interacciones.
 * 
 * Extiende de JPanel.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 * @version 1.0
 *
 */

public class Nivel extends JPanel implements ActionListener{
	
	// Resolución
	private static final int ANCHO_PANTALLA = 800;
	private static final int ALTO_PANTALLA = 600;
	// Imagen de fondo.
	private static final String SRC_IMG_FONDO = "img/space.png";
	private static final int RETARDO = 5; // Retardo para el timerr
	private Image imgFondo;
	private Timer timer;
	private int dificultad;
	private ArrayList<EnemigoA> enemigosA;
	private ArrayList<EnemigoB> enemigosB;
	// Total de enemigos según dificultad elegida.
	private int totalEnemigos;
	private Jugador jugador;
	private int enemigosEliminados = 0;
	private int velocidadEnemigos;
	
	/**
	 * Constructor e inicialización.
	 * @param dificultad: nivel de dificultad del juego.
	 */
	public Nivel(int dificultad) {
		// TODO Auto-generated constructor stub
		// Inicializamos variables.
		jugador = new Jugador();
		enemigosA = new ArrayList<EnemigoA>();
		enemigosB = new ArrayList<EnemigoB>();
		this.dificultad = dificultad;
		// Dejamos preparado la imágen de fondo para dibujarla en el panel.
		ImageIcon img = new ImageIcon(SRC_IMG_FONDO);
		imgFondo = img.getImage();
		
		// Establecer total de enemigos y cargarlos.
		setTotalEnemigos();
		cargarEnemigos();
		
		// Añadimos al panel el listener de eventos de teclado. Clase interna (Inner class) creada al final.
		addKeyListener(new eventosTeclado());
		 
		// Extendemos de JPanel y establecemos foco en el elemento para que pueda reaccionar a eventos de teclado.
		setFocusable(true);
		
		// timer inicializado a 5ms. Ejecutará la función actionPerformed.
		timer = new Timer(RETARDO, this);
		timer.start();
	}

	/**
	 * Función llamada cada 5 ms
	 * Se realizará el movimiento de la nave en pantalla.
	 * 
	 * Esta función es necesaria al implementar la interfaz ActionListener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Movemos la nave y pasamos los límites del panel.
		jugador.mover(ANCHO_PANTALLA, ALTO_PANTALLA);
		
		// Mover balas.
		ArrayList<Bala> balas = jugador.getBalas();
		for (int i = 0; i < balas.size(); i++) {
			Bala b = balas.get(i);
			if(b.getVisible()){
				b.mover(getWidth());
			}else{
				balas.remove(i);
			}
		}
		
		// Mover enemigos
		for (int i = 0; i < enemigosA.size(); i++) {
			EnemigoA enemigo = enemigosA.get(i);
			if(enemigo.getVisible()){
				enemigo.mover();
			}else{
				enemigosA.remove(i);
			}
		}
		
		// Los enemigos de tipo B tienen movimiento aleatorio en ejeY
		Random dy = new Random();
		int ran, y;
		for (int i = 0; i < enemigosB.size(); i++) {
			EnemigoB enemigo = enemigosB.get(i);
			if(enemigo.getVisible()){
				// Movmiento aleatorio. Control de que no se salga de los límites de alto de pantalla
				ran = dy.nextInt(2);
				if(ran == 0 && enemigo.getY() > 0){
					y = -1;
				}else if(ran > 0 && enemigo.getY() < ALTO_PANTALLA){ 
					y = 1;
				}else{
					y = 0;
				}
				enemigo.mover(y);
			}else{
				enemigosB.remove(i);
			}
		}
		
		// Si se han eliminado todos los enemigos o el jugador ha chocado con una nave enemiga fin del juego y vuelta al menú.
		if (getTotalEnemigos() == 0) {
			// forzamos repain para que actualice la imágen con todos los enemigos borrados.
			repaint();
			finJuego(true);
		}
		
		if( ! jugador.getVisible()){
			finJuego(false);
		}
		
		// Control de colisiones
		comprobarColisiones();
		repaint();
	}

	/**
	 * Función a la que se llama para redibujar la pantalla
	 * del juego con las posiciones actualizadas.
	 */
	public void paint(Graphics g){
		// System.out.println("Llamada a paint!!");
		super.paint(g);
		
		// Hacemos un cast de g a Graphics2D. Recomendado por tutoriales de Java.
		Graphics2D g2d = (Graphics2D) g;
		ArrayList<Bala> balas = jugador.getBalas();
		
		// Dibujamos fondo.
		g2d.drawImage(imgFondo, 0, 0, null);
		
		// Dibujamos nave sólo si sigue viva (visible)
		if(jugador.getVisible()){
			g2d.drawImage(jugador.getImagen(), jugador.getX(), jugador.getY(), null);
		}
			
		// Dibujamos proyectiles.
		for (int i = 0; i < balas.size(); i++) {
			// System.out.println("dibujo bala " + i);
			Bala b = balas.get(i);
			g2d.drawImage(b.getImagen(), b.getX(), b.getY(), null);
		}
		
		// Dibujamos enemigos tipo A.
		for (int i = 0; i < enemigosA.size(); i++) {
			g2d.drawImage(enemigosA.get(i).getImagen(), enemigosA.get(i).getX(), enemigosA.get(i).getY(), null);
		}
		
		// Dibujamos enemigos tipo B.
		for (int i = 0; i < enemigosB.size(); i++) {
			g2d.drawImage(enemigosB.get(i).getImagen(), enemigosB.get(i).getX(), enemigosB.get(i).getY(), null);
		}
		
		// HUD con datos de juego.
		g2d.setColor(Color.white);
		g2d.drawString("Puntos: " + enemigosEliminados, 0, 10);
		g2d.drawString("Enemigos restantes: " +totalEnemigos, 625, 10);
		
		// sync nos permite mantener actualizado el estado de los gráficos.
		// Tal y como dice la documentación: Algunos sistemas de ventanas pueden hacer buffering en eventos gráficos.
		Toolkit.getDefaultToolkit().sync();
		g2d.dispose();
	}
	
	/**
	 * @return dificultad del juego
	 */
	public int getDificultad() {
		return dificultad;
	}

	/**
	 * @return total de enemigos.
	 */
	public int getTotalEnemigos(){
		return totalEnemigos;
	}
	
	/**
	 * Establecemos el total de enemigos que va a tener la partida
	 * según el nivel de dificultad.
	 */
	private void setTotalEnemigos() {
		// TODO Auto-generated method stub
		switch (getDificultad()) {
		case 0: // Facil
			totalEnemigos = 10;
			velocidadEnemigos = 1;
			break;
			
		case 1: // Normal
			totalEnemigos = 15;
			velocidadEnemigos = 2;
			break;
			
		case 2: // Complicado
			totalEnemigos = 20;
			velocidadEnemigos = 2;
			break;
			
		case 3: // Imposible
			totalEnemigos = 30;
			velocidadEnemigos = 2 ;
			break;			
		}
	}

	/**
	 * Creamos el array de enemigos según la dificultad seleccionada.
	 * Se crearán enemigos de ambos tipos en cantidades aleatorias.
	 * Cada nave se situará en pantalla en una posición x e y aleatoria.
	 */
	private void cargarEnemigos() {
		Random ran = new Random();
		int x,y;
		int tipo;
		
		for (int i = 0; i < totalEnemigos; i++) {
			// Posición en el eje vertical y horizontal aleatoria del enemigo.
			// Profundidad para aparición de enemigos poco a poco.
			x = ran.nextInt(ANCHO_PANTALLA) + ANCHO_PANTALLA;
			System.out.println(x);
			y = ran.nextInt(ALTO_PANTALLA-100);
			// Tipo de nave que se crea. O nave tipo A / 1 nave tipo B
			tipo = ran.nextInt(2);
			if (tipo == 0) {
				enemigosA.add(new EnemigoA(x, y, velocidadEnemigos));
			}else{
				enemigosB.add(new EnemigoB(x, y, velocidadEnemigos));
			}
		}
		
	}
	
	/**
	 * Control de colisiones entre nave del jugador y enemigos y entre
	 * balas con naves enemigas.
	 * 
	 * El control de colisiones se implementa usando una técnica básica
	 * que rodea los elementos con un rectángulo y comprobando si éstos
	 * intersectan en algún momento.
	 * 
	 * No es un método 100% fiable ya que habría que mejorarlo implementando
	 * alguna técnica más avanzada como pixel perfect.
	 */
	private void comprobarColisiones() {
		// Área de colisión de la nave del jugador
		Rectangle limitesJugador = jugador.getBordes();
		
		ArrayList<Rectangle> A = new ArrayList<Rectangle>();
		ArrayList<Rectangle> B = new ArrayList<Rectangle>();
		
		// Creamos áreas de colisiones en los enemigos
		for (int i = 0; i < enemigosA.size(); i++) {
			EnemigoA enemigo = enemigosA.get(i);
			Rectangle bordesEnemigo = enemigo.getBordes();
			A.add(bordesEnemigo);
			if(jugador.getVisible() && limitesJugador.intersects(bordesEnemigo)){
				jugador.setVisible(false);
			}
		}
		
		for (int i = 0; i < enemigosB.size(); i++) {
			EnemigoB enemigo = enemigosB.get(i);
			Rectangle bordesEnemigo = enemigo.getBordes();
			B.add(bordesEnemigo);
			if(jugador.getVisible() && limitesJugador.intersects(bordesEnemigo)){
				jugador.setVisible(false);
			}
		}
		
		// Creamos áreas de colisiones en los proyectiles y comprobamos 
		// si colisionan con los límites de los enemigos
		ArrayList<Bala> balas = jugador.getBalas();
		for (int i = 0; i < balas.size(); i++) {
			Bala bala = balas.get(i);
			Rectangle bordesBala = bala.getBordes();
			for (int j = 0; j < A.size(); j++) {
				if (bala.getVisible() && enemigosA.get(j).getVisible() && bordesBala.intersects(A.get(j))) {
					enemigosA.get(j).setVisible(false); 
					bala.setVisible(false);
					enemigosEliminados++;
					totalEnemigos--;
				}
			}
			for (int j = 0; j < B.size(); j++) {
				if (bala.getVisible() && enemigosB.get(j).getVisible() && bordesBala.intersects(B.get(j))) {
					enemigosB.get(j).setVisible(false);
 					bala.setVisible(false);
					enemigosEliminados++;
					totalEnemigos--;
				}
			}
		}
	}
	
	/**
	 * Función para mostrar mensaje de victoria o derrota y fin del juego.
	 * Una vez mostrado el mensaje se volverá a cargar el menú principal.
	 * 
	 * @param victoria booleano para saber si el jugador ganó o perdió
	 */
	private void finJuego(boolean victoria){
		// Recuperamos la ventana contenedora y la cerramos y lanzamos el menú.
		Window ventana = SwingUtilities.getWindowAncestor(this);
		// Paramos timer.
		timer.stop();
		
		if (victoria) {
			JOptionPane.showMessageDialog(null, "¡HAS GANADO!", "Juego terminado", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, "¡HAS PERDIDO!", "Juego terminado", JOptionPane.INFORMATION_MESSAGE);			
		}
		
		ventana.dispose();
		new RType();
	}

	/**
	 * Clase interna que hará las funciones de Listener
	 * sobre los eventos del teclado.
	 * 
	 * @author Jonatan Rodríguez Elduayen jrodeldu
	 *
	 */
	private class eventosTeclado extends KeyAdapter{
		// Tecla soltada
		public void keyReleased(KeyEvent e){
			jugador.keyReleased(e);
		}
		
		// Tecla pulsada
		public void keyPressed(KeyEvent e){
			jugador.keyPressed(e); 
		}
	}
	
}
