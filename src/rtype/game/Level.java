package rtype.game;

import java.awt.Color;
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
import javax.swing.JLabel;
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

public class Level extends JPanel implements ActionListener{
	
	// Resolución
	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 600;
	// Imagen de fondo.
	private Image backgroundImg;
	private static final String BACKGROUND_IMG = "img/space.png";
	private int difficulty;
	private ArrayList<EnemyA> enemies;
	private ArrayList<EnemyB> enemiesB;
	// Total de enemigos según dificultad elegida.
	private int totalEnemies;
	private Player player;
	private Timer time;
	private static final int DELAY = 5; // Retardo para el Timer
	private int enemiesKilled = 0;
	
	/**
	 * Constructor e inicialización.
	 * @param difficulty: nivel de dificultad del juego.
	 */
	public Level(int difficulty) {
		// TODO Auto-generated constructor stub
		System.out.println("Dificultad: " + difficulty);
		player = new Player();
		enemies = new ArrayList<EnemyA>();
		enemiesB = new ArrayList<EnemyB>();
		this.difficulty = difficulty;
		// Establecer total de enemigos
		setTotalEnemies();
		
		// Añadimos al panel el listener de eventos de teclado. Clase interna (Inner class) creada al final.
		addKeyListener(new keyListener());
		 
		// Extendemos de JPanel y establecemos foco en el elemento para que pueda reaccionar a eventos de teclado.
		setFocusable(true);
		 
		// Establecemos fondo.
		ImageIcon img = new ImageIcon(BACKGROUND_IMG);
		backgroundImg = img.getImage();
		
		// Inicializar los Enemigos.
		loadEnemies();
		
		// Timer inicializado a 5ms.
		time = new Timer(DELAY, this);
		// Iniciamos timer.
		time.start();
	}
	
	/**
	 * Establecemos el total de enemigos que va a tener la partida
	 * según el nivel de dificultad.
	 */
	private void setTotalEnemies() {
		// TODO Auto-generated method stub
		switch (getDifficulty()) {
		case 0:
			totalEnemies = 10;
			break;
			
		case 1:
			totalEnemies = 15;
			break;
			
		case 2:
			totalEnemies = 20;
			break;
			
		case 3:
			totalEnemies = 30;
			break;			
		}
	}

	/**
	 * Creamos el array de enemigos según la dificultad seleccionada.
	 */
	private void loadEnemies() {
		// TODO Auto-generated method stub
		Random ran = new Random();
		int x,y;
		
		for (int i = 0; i < totalEnemies/2; i++) {
			// Posición en el eje vertical y horizontal aleatoria del enemigo.
			x = ran.nextInt(200) + SCREEN_WIDTH;
			y = ran.nextInt(SCREEN_HEIGHT-100);
			// System.out.println(x + " - " + y);
			enemies.add(new EnemyA(x, y));
			x = ran.nextInt(100)-400;
			enemiesB.add(new EnemyB(x, y));
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
		// System.out.println(player.getY());
		// System.out.println(player.getX());
		
		// Control de colisiones
		checkCollisions();
		
		// Movemos la nave y pasamos los límites del panel.
		player.move(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		// Mover misiles.
		ArrayList<Bullet> bulletsAL = player.getBullets();
		for (int i = 0; i < bulletsAL.size(); i++) {
			Bullet b = bulletsAL.get(i);
			if(b.isVisible()){
				b.move(getWidth());
			}else{
				bulletsAL.remove(i);
			}
		}
		
		// Mover enemigos
		for (int i = 0; i < enemies.size(); i++) {
			EnemyA en = enemies.get(i);
			if(en.isVisible()){
				en.move();
			}else{
				enemies.remove(i);
			}
		}
		
		// Los enemigos de tipo B tienen movimiento aleatorio en ejeY
		Random dy = new Random();
		int ran, y;
		for (int i = 0; i < enemiesB.size(); i++) {
			EnemyB en = enemiesB.get(i);
			if(en.isVisible()){
				// Movmiento aleatorio. Control de que no se salga de los límites de alto de pantalla
				ran = dy.nextInt(2);
				if(ran == 0 && en.getY() > 0){
					y = -1;
				}else if(ran > 0 && en.getY() < SCREEN_HEIGHT){ 
					y = 1;
				}else{
					y = 0;
				}
				en.move(y);
			}else{
				enemiesB.remove(i);
			}
		}
		
		repaint();
	}
	
	/**
	 * Control de colisiones cada 5ms
	 */
	private void checkCollisions() {
		// TODO Auto-generated method stub
		
		// Área de colisión de la nave del jugador
		Rectangle playerBounds = player.getBounds();
		
		ArrayList<Rectangle> enemyBoundsA = new ArrayList<Rectangle>();
		ArrayList<Rectangle> enemyBoundsB = new ArrayList<Rectangle>();
		
		// Creamos áreas de colisiones en los enemigos
		for (int i = 0; i < enemies.size(); i++) {
			EnemyA en = enemies.get(i);
			Rectangle enBounds = en.getBounds();
			enemyBoundsA.add(enBounds);
			if(player.isVisible() && playerBounds.intersects(enBounds)){
				player.setVisible(false);
			}
		}
		
		for (int i = 0; i < enemiesB.size(); i++) {
			EnemyB en = enemiesB.get(i);
			Rectangle enBounds = en.getBounds();
			enemyBoundsB.add(enBounds);
			if(player.isVisible() && playerBounds.intersects(enBounds)){
				player.setVisible(false);
			}
		}
		
		// Creamos áreas de colisiones en los proyectiles y comprobamos 
		// si colisionan con los límites de los enemigos
		ArrayList<Bullet> bulletsAL = player.getBullets();
		for (int i = 0; i < bulletsAL.size(); i++) {
			Bullet b = bulletsAL.get(i);
			Rectangle bBounds = b.getBounds();
			for (int j = 0; j < enemyBoundsA.size(); j++) {
				if (b.isVisible() && enemies.get(j).isVisible() && bBounds.intersects(enemyBoundsA.get(j))) {
					enemies.get(j).setVisible(false);
					//enemies.remove(j); 
					b.setVisible(false);
					enemiesKilled++;
					totalEnemies--;
				}
			}
			for (int j = 0; j < enemyBoundsB.size(); j++) {
				if (b.isVisible() && enemiesB.get(j).isVisible() && bBounds.intersects(enemyBoundsB.get(j))) {
					enemiesB.get(j).setVisible(false);
					// enemies.remove(j); 
					b.setVisible(false);
					enemiesKilled++;
					totalEnemies--;
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
		ArrayList<Bullet> bulletsAL = player.getBullets();
		
		// Dibujamos fondo.
		g2d.drawImage(backgroundImg, 0, 0, null);
		
		// Dibujamos nave sólo si sigue viva (visible)
		if(player.isVisible()){
			g2d.drawImage(player.getShipImage(), player.getX(), player.getY(), null);
		}
			
		
		// Dibujamos proyectiles.
		for (int i = 0; i < bulletsAL.size(); i++) {
			// System.out.println("dibujo bala " + i);
			Bullet b = bulletsAL.get(i);
			//System.out.println("PosX: " + b.getX() + " PosY: " + b.getY());
			g2d.drawImage(b.getImage(), b.getX(), b.getY(), null);
		}
		
		// Dibujamos enemigos tipo A.
		for (int i = 0; i < enemies.size(); i++) {
			g2d.drawImage(enemies.get(i).getImage(), enemies.get(i).getX(), enemies.get(i).getY(), null);
		}
		
		// Dibujamos enemigos tipo B.
		for (int i = 0; i < enemiesB.size(); i++) {
			g2d.drawImage(enemiesB.get(i).getImage(), enemiesB.get(i).getX(), enemiesB.get(i).getY(), null);
		}
		
		// HUD con datos de juego.
		g2d.setColor(Color.white);
		g2d.drawString("Puntos: " + enemiesKilled, 0, 10);
		g2d.drawString("Enemigos restantes: " +totalEnemies, 625, 10);
		
	}
	
	/**
	 * Getter enemigos.
	 * @return AraryLisT de enemigos
	 */
	public ArrayList<EnemyA> getEnemies(){
		return enemies;
	}
	
	/**
	 * @return dificultad del juego
	 */
	public int getDifficulty() {
		return difficulty;
	}
	
	/**
	 * @return total de enemigos.
	 */
	public int getTotalEnemies(){
		return totalEnemies;
	}

	/**
	 * Clase interna que hará las funciones de Listener
	 * sobre los eventos del teclado.
	 * 
	 * @author Jonatan Rodríguez Elduayen jrodeldu
	 *
	 */
	private class keyListener extends KeyAdapter{
		// Tecla soltada
		public void keyReleased(KeyEvent e){
			player.keyReleased(e);
		}
		
		// Tecla pulsada
		public void keyPressed(KeyEvent e){
			player.keyPressed(e); 
		}
	}
	
}
