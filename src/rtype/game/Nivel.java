package rtype.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Esta en la clase del que contendrá el nivel del juego.
 * Se dibujarán todos los elementos y se recogerán sus interacciones.
 * 
 * Usaremos un timer para redibujar los elementos en pantalla
 * y controlar y comprobar sus colisiones y desplazamientos en los ejes X e Y
 * 
 * Extiende de JPanel.
 * 
 * @author Jonatan Rodríguez Elduayen jrodeldu
 * @version 1.0
 *
 */

public class Nivel extends JPanel implements ActionListener{
	
	// Puntos por enemigo eliminado
	private static final int PUNTOS_ACIERTO = 100;
	// Imagen de fondo.
	private static final String SRC_IMG_FONDO = "img/space.png";
	// Retardo para el timer
	private static final int RETARDO = 5;
	private Image imgFondo;
	private Timer timer;
	private int dificultad;
	// Total de enemigos según dificultad elegida.
	private int totalEnemigos;
	private Jugador jugador;
	private int enemigosEliminados; // Contador de enemigos eliminados
	private int velocidadEnemigos;
	private ArrayList<Nave> navesEnemigas; // Naves enemigas, tanto de tipo A como tipo B
	// Variables para control de scroll de fondo.
	private int proxFondo, proxFondo2;
	private int anchoImgFondo;
	// Los enemigos se crean en el primer paint para saber las dimensiones del terreno jugable.
	private boolean enemigosCreados;
	// Variable para avance del background.
	private int avanza;
	private boolean gameOver;
	
	/**
	 * Constructor e inicialización.
	 * Se inicializan todas las variables.
	 * Se establece el número de enemigos según la dificultad.
	 * Se inicializa el Timer.
	 * @param dificultad: nivel de dificultad del juego.
	 */
	public Nivel(int dificultad) {
		// TODO Auto-generated constructor stub
		// Inicializamos variables.
		jugador = new Jugador();
		navesEnemigas = new ArrayList<Nave>();
		this.dificultad = dificultad;
		// Dejamos preparado la imágen de fondo para dibujarla en el panel.
		ImageIcon img = new ImageIcon(SRC_IMG_FONDO);
		imgFondo = img.getImage();
		
		anchoImgFondo = imgFondo.getWidth(null);
		proxFondo = anchoImgFondo;
		proxFondo2 = 0;
		avanza = 1;
		
		enemigosCreados = false;
		enemigosEliminados = 0;
		gameOver = false;
		
		// Establecer total de enemigos.
		setTotalEnemigos();
		
		// Añadimos al panel el listener de eventos de teclado. Clase interna (Inner class) creada al final.
		addKeyListener(new eventosTeclado());
		 
		// Extendemos de JPanel y establecemos foco en el elemento para que pueda reaccionar a eventos de teclado.
		setFocusable(true);
		
		// Timer. Ejecutará la función actionPerformed.
		timer = new Timer(RETARDO, this);
		timer.start();
	}

	/**
	 * Función llamada según los ms asignados en RETARDO. Realizará las siguientes tareas:
	 * Se realizará el movimiento de las naves en pantalla.
	 * Moverá las balas.
	 * Comprobará que el juego no haya terminado.
	 * Comprobará colisiones.
	 * Redibujará en pantalla los elementos.
	 * 
	 * Esta función es necesaria al implementar la interfaz ActionListener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		jugador.mover(this);
		
		// Mover balas.
		ArrayList<Bala> balas = jugador.getBalas();
		for (int i = 0; i < balas.size(); i++) {
			Bala b = balas.get(i);
			if(b.getVisible()){
				b.mover(this);
			}else{
				balas.remove(i);
			}
		}
		
		// Movemos enemigos.
		for (int i = 0; i < navesEnemigas.size(); i++) {
			String tipo = navesEnemigas.get(i).getTipoNave();
			if (tipo == "A") {
				EnemigoA enemigo = (EnemigoA) navesEnemigas.get(i);
				if(enemigo.getVisible()){
					enemigo.mover(this);
				}else{
					navesEnemigas.remove(i);
				}
			}	
			if (tipo == "B") {
				EnemigoB enemigo = (EnemigoB) navesEnemigas.get(i);
				if(enemigo.getVisible()){
					enemigo.mover(this);
				}else{
					navesEnemigas.remove(i);
				}
			}
		}
		
		// Si se han eliminado todos los enemigos o el jugador ha chocado con una nave enemiga fin del juego y vuelta al menú.
		if (getTotalEnemigos() == 0) {
			// forzamos repaint para que actualice la imágen con todos los enemigos borrados.
			repaint();
			pararJuego();
		}
		
		
		if( ! jugador.getVisible()){
			pararJuego();
		}
		
		// Control de colisiones
		comprobarColisiones();
		repaint();
	}

	/**
	 * Función a la que se llama para redibujar la pantalla
	 * del juego con las posiciones actualizadas.
	 * 
	 * La primera vez que se ejecute se cargarán los enemigos.
	 */
	public void paint(Graphics g){
		// Pintamos.
		super.paint(g);
		
		// Los enemigos se crean en el primer paint para poder obtener las dimensiones
		// del panel para poder situa a los enemigos en él.
		if (!enemigosCreados) {
			cargarEnemigos();
		}
		
		// Hacemos un cast de g a Graphics2D. Recomendado por tutoriales de Java.
		Graphics2D g2d = (Graphics2D) g;

		// Dibujamos fondo. Creamos un efecto scroll en el fondo para dar impresión de navegar en espacio.
		// Si el resto entre el tamaño del fondo y el ancho del panel es 0, el prox fondo se sitúa en la posición
		// marcada por el ancho de la imagen de fondo, en este caso 800.
		// El método consiste en dibujar 2 fondos e ir rodándolos infinitamente.
		// El pimero se situará en la posición 0 y el otro en 800 e iremos rodándolos haciendo que se generen en esta secuencia:
		// 0 y 800 / -1 y 799 / -2 y 798.... al llegar a un punto en el que el resto sea 0 reiniciaremos los valores.
		if (proxFondo % anchoImgFondo == 0) {
			proxFondo = anchoImgFondo;
		}
		
		if (proxFondo2 % anchoImgFondo == 0) {
			proxFondo2 = 0;
		}
		
		// getWidth()-proxFondo de inicio será igual a 0 y sucesivamente irá desplazando la imágen hacia la izq.
		g2d.drawImage(imgFondo, getWidth()-proxFondo, 0, null);
		if (proxFondo >= anchoImgFondo ) {
			g2d.drawImage(imgFondo, getWidth()-proxFondo2, 0, null);
		}
		
		// El fondo avanzará cada 2 llamadas a paint.
		// De modo que al tener dificultad fácil las naves avanzarán y no dará impresión de estar pegadas en el fondo.
		if(avanza%2 == 0){
			proxFondo++; // Desplazaremos la próx vez la imagen 1 punto.	
			proxFondo2++; // El otro fondo aux a dibujar se situará 1 punto a la izq;
		}
		
		avanza++;
		
		// Dibujamos nave sólo si sigue viva (visible)
		if(jugador.getVisible()){
			g2d.drawImage(jugador.getImagen(), jugador.getX(), jugador.getY(), null);
		}
		
		ArrayList<Bala> balas = jugador.getBalas();
		// Dibujamos proyectiles.
		for (int i = 0; i < balas.size(); i++) {
			// Dibujamos bala.
			Bala b = balas.get(i);
			g2d.drawImage(b.getImagen(), b.getX(), b.getY(), null);
		}
		
		// Dibujamos enemigos
		for (int i = 0; i < navesEnemigas.size(); i++) {
			g2d.drawImage(navesEnemigas.get(i).getImagen(), navesEnemigas.get(i).getX(), navesEnemigas.get(i).getY(), null);
		}
		
		// HUD con datos de juego.
		g2d.setColor(Color.white);
		g2d.drawString("Puntos: " + enemigosEliminados * PUNTOS_ACIERTO, 0, 10);
		g2d.drawString("Enemigos restantes: " + totalEnemigos, getWidth()-200, 10);
		
		// Mensajes de GAME OVER
		if (getTotalEnemigos() == 0 || !jugador.getVisible()) {
			Font big = new Font("Helvetica", Font.BOLD, 18); // Fuente grande.
			Font small = new Font("Helvetica", Font.BOLD, 14); // Fuente más pequeña para mensaje inferior.
			// Se establece la fuente grande
			g.setFont(big);
			// Color
			g.setColor(Color.WHITE);
			// Variables de Strings
			String mensaje = "";
			String reinicio = "¿Quierenes volver a jugar? S o N";
			
			// Mensaje según victoria o derrota.
			if (getTotalEnemigos() == 0) mensaje = "¡Has ganado!";
			if (!jugador.getVisible()) mensaje = "¡Has perdido!";
			
	        FontMetrics fm = g.getFontMetrics();
	        double mensajeAncho = fm.getStringBounds(mensaje, g).getWidth();
	        // Dibujamos mensaje victoria/derrota centrado
	        g.drawString(mensaje, (int) (getWidth()/2 - mensajeAncho/2),
	                           (int) (getHeight()/2 + fm.getMaxAscent()/2));
	        
	        g.setFont(small);
	        fm = g.getFontMetrics();
	        // Dibujamos mensaje para reiniciar/cerrar el juego
	        double reinicioAncho = fm.getStringBounds(reinicio, g).getWidth();
	        g.drawString(reinicio, (int) (getWidth()/2 - reinicioAncho/2),
	                			(int) (getHeight()/2 + fm.getMaxAscent()/2)+20);
		}
		
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
			velocidadEnemigos = 3;
			break;
			
		case 3: // Imposible
			totalEnemigos = 30;
			velocidadEnemigos = 3 ;
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
			x = ran.nextInt(getWidth()) + getWidth();
			y = ran.nextInt(getHeight()-100);
			// Tipo de nave que se crea. O nave tipo A / 1 nave tipo B
			tipo = ran.nextInt(2);
			if (tipo == 0) {
				navesEnemigas.add(new EnemigoA(x, y, velocidadEnemigos));
			}else{
				navesEnemigas.add(new EnemigoB(x, y, velocidadEnemigos));
			}
		}
		
		// Una vez creados los enemigos ya no hay que volver a generarlos.
		enemigosCreados = true;
		
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
		
		ArrayList<Rectangle> bordesNaves = new ArrayList<Rectangle>();
		
		Nave enemigo = null;
		// Creamos áreas de colisiones en los enemigos
		for (int i = 0; i < navesEnemigas.size(); i++) {
			if (navesEnemigas.get(i).getTipoNave() == "A") {
				enemigo = (EnemigoA) navesEnemigas.get(i);
			}
			if (navesEnemigas.get(i).getTipoNave() == "B") {
				enemigo = (EnemigoB) navesEnemigas.get(i);
			}
			Rectangle bordesEnemigo = enemigo.getBordes();
			bordesNaves.add(bordesEnemigo);
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
			for (int j = 0; j < bordesNaves.size(); j++) {
				if (bala.getVisible() && navesEnemigas.get(j).getVisible() && bordesBala.intersects(bordesNaves.get(j))) {
					navesEnemigas.get(j).setVisible(false); 
					bala.setVisible(false);
					enemigosEliminados++;
					totalEnemigos--;
				}
			}
		}
	}
	
	/**
	 * Función para detener el timer del juego y activar el
	 * flag de gameOver que permitirá que el panel reaccione
	 * a eventos de teclado para reiniciar el juego.
	 */
	private void pararJuego(){
		// Paramos timer.
		timer.stop();
		gameOver = true;
	}
	
	/**
	 * Se decide si se reinicia el juego(S)
	 * o se sale de el(N)
	 * @param e tecla pulsada
	 */
	private void gameOver(KeyEvent e){
		int key = e.getKeyCode();
		// Recuperamos la ventana contenedora y la cerramos y lanzamos el menú.
		Window ventana = SwingUtilities.getWindowAncestor(this);
		// Reiniciamos juego
		if(key == KeyEvent.VK_S){
			ventana.dispose();
			new RType();
		}
		// Cerramos juego
		if(key == KeyEvent.VK_N){
			ventana.dispose();  
		}
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
			// Eventos para fin del juego
			if (gameOver) {
				gameOver(e);
			}
		}
	}
	
}
