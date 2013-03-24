package rtype.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
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
	private Player player;
	private Timer time;
	private static final int DELAY = 5; // Retardo para el Timer
	
	/**
	 * Constructor e inicialización.
	 * @param difficulty: nivel de dificultad del juego.
	 */
	public Level(int difficulty) {
		// TODO Auto-generated constructor stub
		
		this.setDifficulty(difficulty);
		player = new Player();
		enemies = new ArrayList<EnemyA>();
		enemiesB = new ArrayList<EnemyB>();
		
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
	 * Creamos el array de enemigos según la dificultad seleccionada.
	 */
	private void loadEnemies() {
		// TODO Auto-generated method stub
		Random ran = new Random();
		int x,y;
		
		for (int i = 0; i < 5; i++) {
			// Posición en el eje vertical y horizontal aleatoria del enemigo.
			x = ran.nextInt(200) + SCREEN_WIDTH;
			y = ran.nextInt(SCREEN_HEIGHT-100);
			System.out.println(x + " - " + y);
			enemies.add(new EnemyA(x, y));
			x = ran.nextInt(100)-400;
			System.out.println(x + " - " + y);
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
		
		for (int i = 0; i < enemiesB.size(); i++) {
			EnemyB en = enemiesB.get(i);
			if(en.isVisible()){
				en.move();
			}else{
				enemiesB.remove(i);
			}
		}
		
		repaint();
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
		
		// Dibujamos nave.
		g2d.drawImage(player.getShipImage(), player.getX(), player.getY(), null);
		
		// Dibujamos proyectiles.
		for (int i = 0; i < bulletsAL.size(); i++) {
			System.out.println("dibujo bala " + i);
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
	 * @param difficulty dificultad a establecer
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
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
