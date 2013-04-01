package rtype.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Es clase se refiere al contenedor de elementos.
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
	private Image imgFondo;
	private static final String SRC_IMG_FONDO = "img/space.png";
	private int dificultad;
	private ArrayList<EnemigoA> enemigosA;
	private ArrayList<EnemigoB> enemigosB;
	// Total de enemigos según dificultad elegida.
	private int totalEnemigos;
	private Jugador jugador;
	private Timer timer;
	private static final int RETARDO = 5; // Retardo para el timerr
	private int enemigosEliminados = 0;
	private int velocidadEnemigos;
	
	/**
	 * Constructor e inicialización.
	 * @param dificultad: nivel de dificultad del juego.
	 */
	public Nivel(int dificultad) {
		// TODO Auto-generated constructor stub
		jugador = new Jugador();
		enemigosA = new ArrayList<EnemigoA>();
		enemigosB = new ArrayList<EnemigoB>();
		this.dificultad = dificultad;
		// Establecer total de enemigos
		setTotalEnemigos();
		
		// Añadimos al panel el listener de eventos de teclado. Clase interna (Inner class) creada al final.
		addKeyListener(new eventosTeclado());
		 
		// Extendemos de JPanel y establecemos foco en el elemento para que pueda reaccionar a eventos de teclado.
		setFocusable(true);
		
		//setDoubleBuffered(true);
		 
		// Establecemos fondo.
		ImageIcon img = new ImageIcon(SRC_IMG_FONDO);
		imgFondo = img.getImage();
		
		// Inicializar los Enemigos.
		cargarEnemigos();
		
		// timerr inicializado a 5ms.
		timer = new Timer(RETARDO, this);
		// Iniciamos timerr.
		timer.start();
	}
	
	/**
	 * Establecemos el total de enemigos que va a tener la partida
	 * según el nivel de dificultad.
	 */
	private void setTotalEnemigos() {
		// TODO Auto-generated method stub
		switch (getDificultad()) {
		case 0:
			totalEnemigos = 10;
			velocidadEnemigos = 1;
			break;
			
		case 1:
			totalEnemigos = 15;
			velocidadEnemigos = 2;
			break;
			
		case 2:
			totalEnemigos = 20;
			velocidadEnemigos = 2;
			break;
			
		case 3:
			totalEnemigos = 30;
			velocidadEnemigos = 2 ;
			break;			
		}
	}

	/**
	 * Creamos el array de enemigos según la dificultad seleccionada.
	 * PENDIENTE!
	 */
	private void cargarEnemigos() {
		// TODO Auto-generated method stub
		Random ran = new Random();
		int x,y;
		
		int j;
		
		for (int i = 0; i < totalEnemigos; i++) {
			j = ran.nextInt(2);
			System.out.println(j);
		}
		
		for (int i = 0; i < totalEnemigos/2; i++) {
			// Posición en el eje vertical y horizontal aleatoria del enemigo.
			x = ran.nextInt(200) + ANCHO_PANTALLA;
			y = ran.nextInt(ALTO_PANTALLA-100);
			// System.out.println(x + " - " + y);
			enemigosA.add(new EnemigoA(x, y, velocidadEnemigos));
			// x = ran.nextInt(100)-400;
			x = ran.nextInt(200) + ANCHO_PANTALLA;
			enemigosB.add(new EnemigoB(x, y, velocidadEnemigos));
		}
		
	}

	/**
	 * Función llamada cada 5 ms
	 * Se realizará el movimiento de la nave en pantalla.
	 * 
	 * Esta función es necesaria al implementar la interfaz ActionListener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// System.out.println(jugador.getY());
		// System.out.println(jugador.getX());
		
		// Control de colisiones
		comprobarColisiones();
		
		// Movemos la nave y pasamos los límites del panel.
		jugador.mover(ANCHO_PANTALLA, ALTO_PANTALLA);
		
		// Mover misiles.
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
		
		repaint();
	}
	
	/**
	 * Control de colisiones cada 5ms
	 */
	private void comprobarColisiones() {
		// TODO Auto-generated method stub
		
		// Área de colisión de la nave del jugador
		Rectangle limitesJugador = jugador.getBordes();
		
		ArrayList<Rectangle> BordesEnemigoA = new ArrayList<Rectangle>();
		ArrayList<Rectangle> BordesEnemigoB = new ArrayList<Rectangle>();
		
		// Creamos áreas de colisiones en los enemigos
		for (int i = 0; i < enemigosA.size(); i++) {
			EnemigoA enemigo = enemigosA.get(i);
			Rectangle enBounds = enemigo.getBordes();
			BordesEnemigoA.add(enBounds);
			if(jugador.getVisible() && limitesJugador.intersects(enBounds)){
				jugador.setVisible(false);
			}
		}
		
		for (int i = 0; i < enemigosB.size(); i++) {
			EnemigoB enemigo = enemigosB.get(i);
			Rectangle enBounds = enemigo.getBordes();
			BordesEnemigoB.add(enBounds);
			if(jugador.getVisible() && limitesJugador.intersects(enBounds)){
				jugador.setVisible(false);
			}
		}
		
		// Creamos áreas de colisiones en los proyectiles y comprobamos 
		// si colisionan con los límites de los enemigos
		ArrayList<Bala> balas = jugador.getBalas();
		for (int i = 0; i < balas.size(); i++) {
			Bala b = balas.get(i);
			Rectangle bBounds = b.getBordes();
			for (int j = 0; j < BordesEnemigoA.size(); j++) {
				if (b.getVisible() && enemigosA.get(j).getVisible() && bBounds.intersects(BordesEnemigoA.get(j))) {
					enemigosA.get(j).setVisible(false); 
					b.setVisible(false);
					enemigosEliminados++;
					totalEnemigos--;
				}
			}
			for (int j = 0; j < BordesEnemigoB.size(); j++) {
				if (b.getVisible() && enemigosB.get(j).getVisible() && bBounds.intersects(BordesEnemigoB.get(j))) {
					enemigosB.get(j).setVisible(false);
					// enemies.remove(j); 
					b.setVisible(false);
					enemigosEliminados++;
					totalEnemigos--;
				}
			}
		}
	}

	/**
	 * Función a la que se llama para redibujar la pantalla
	 * del juego con las posiciones actualizadas.
	 */
	public void paint(Graphics g){
		// System.out.println("Llamada a paint!!");
		super.paint(g);
		
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
			//System.out.println("PosX: " + b.getX() + " PosY: " + b.getY());
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
		
		if(getTotalEnemigos() == 0){
			// Mensaje de victoria.
			String msg = "¡HAS GANADO!";
            Font font = new Font("Helvetica", Font.BOLD, 16);
            g2d.setFont(font);
            // Establecemos un objeto FontMetrics que nos permitirá conocer detalles de un texto
            // para lograr un centrado en pantalla óptimo.
            FontMetrics fm = getFontMetrics(font);

            g2d.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
		}
		
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
