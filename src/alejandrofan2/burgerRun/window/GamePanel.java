package alejandrofan2.burgerRun.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import alejandrofan2.burgerRun.framework.BufferedImageLoader;
import alejandrofan2.burgerRun.framework.Handler;
import alejandrofan2.burgerRun.framework.KeyListener;
import alejandrofan2.burgerRun.framework.ObjectId;
import alejandrofan2.burgerRun.framework.objects.Block;
import alejandrofan2.burgerRun.framework.objects.InvisibleBlock;
import alejandrofan2.burgerRun.framework.objects.Player;
import alejandrofan2.burgerRun.framework.objects.WinZone;
import alejandrofan2.burgerRun.framework.textureManager.Texture;

/**
 * This does the magic, collects all the components and creates the scene of the
 * game. In addition, it also takes care of the fluidity, loading the levels,
 * the music ...
 * 
 * @author alejandrofan2
 *
 */
public class GamePanel extends Canvas implements Runnable {

	private static final long serialVersionUID = 3367166757979631248L;

	private boolean running = false;
	private Thread thread;
	private Handler handler;
	private static Texture textures;
	private Camera camera;
	private GameMenu menu;

	public int WIDTH, HEIGHT;

	private BufferedImage level, clouds, doubleClouds;
	private final Integer NCLOUDS = 25;
	private int[][] cloudsPos;

	protected Clip backMusic, loseMusic, winMusic;

	private boolean win = false;
	private boolean lose = false;

	/**
	 * Constructor.
	 * 
	 * @param gameMenu
	 */
	public GamePanel(GameMenu gameMenu) {
		this.menu = gameMenu;
	}

	/**
	 * Start the thread to be able to start running the game.
	 */
	public synchronized void start() {
		if (running) {
			return;
		}

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	// This method contains the main loop of the game, which in turn adjusts the TPS
	// so that the game runs equally smoothly on all PCs.

	@Override
	public void run() {
		System.out.println("Inside: " + Thread.currentThread().getName());

		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double tps = 60;
		double ns = 1000000000 / tps;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames + " TPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	/**
	 * Loads everything the game needs to load: textures, music, camera, listeners,
	 * level, buffer...
	 */
	private void init() {
		WIDTH = getWidth();
		HEIGHT = getHeight();

		this.createBufferStrategy(3);

		loadMusic();

		textures = new Texture();
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/lvl1.png");
		clouds = loader.loadImage("/cloud.png");
		doubleClouds = loader.loadImage("/doubleCloud.png");

		camera = new Camera(0, 0, WIDTH, HEIGHT);
		handler = new Handler();
		loadImageLevel(level);
		cloudsPos = addClouds();

		this.addKeyListener(new KeyListener(handler, menu));
	}

	/**
	 * Update the logic of all objects with the handler and run your own logic to
	 * check the state of the game.
	 */
	private void tick() {
		handler.tick();
		for (int i = 0; i < handler.objects.size(); i++) {
			if (handler.objects.get(i).getId() == ObjectId.Player) {
				camera.tick(handler.objects.get(i));
			}
		}
		if (!lose && !win) {
			if (!backMusic.isRunning()) {
				backMusic.start();
			}
		} else if (win) {
			backMusic.stop();
			winMusic.start();
			backMusic.close();
			win();
			setWin(false);
		} else if (lose) {
			backMusic.stop();
			loseMusic.start();
			backMusic.close();
			lose();
			setLose(false);
		}
	}

	/**
	 * The fun part of the game, this method draws all components and objects on the
	 * canvas.
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		////////////////
		// Draw zone
		g.setColor(new Color(153, 204, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Camera zone
		g2d.translate(camera.getX(), camera.getY());

		renderClouds(g);
		handler.render(g, camera);

		g2d.translate(-camera.getX(), -camera.getY());
		// Ends Camera Zone
		// Ends Draw Zone
		////////////////
		g.dispose();
		bs.show();

	}

	/**
	 * This method is executed when the player wins the game.
	 */
	private void win() {
		JOptionPane.showMessageDialog(this, "Felicidades has ganado!", "Burger Run", JOptionPane.CLOSED_OPTION);
		running = false;
		menu.setVisible(true);
		menu.setWin(true);
	}

	/**
	 * This method is executed when the player loses the game.
	 */
	private void lose() {
		JOptionPane.showConfirmDialog(this, "Has perdido...", "Burger Run", JOptionPane.CLOSED_OPTION);
		running = false;
		menu.setWin(true);
		menu.setVisible(true);
	}

	/**
	 * Generates a series of randoms that will serve to later create the clouds.
	 * 
	 * @return int[][]
	 */
	private int[][] addClouds() {
		cloudsPos = new int[3][NCLOUDS];
		for (int i = 0; i < NCLOUDS; i++) {
			double random = (35 + (Math.random() * 150));
			cloudsPos[0][i] = (int) random;
		}
		for (int i = 0; i < NCLOUDS; i++) {
			double random = (Math.random() * 300);
			cloudsPos[1][i] = (int) random;
		}
		for (int i = 0; i < NCLOUDS; i++) {
			double random = (Math.random() * 5);
			cloudsPos[2][i] = (int) random;
		}
		return cloudsPos;
	}

	/**
	 * Draw the clouds on the canvas.
	 * 
	 * @param g
	 */
	private void renderClouds(Graphics g) {
		int count = 0;
		for (int i = 600; i < clouds.getWidth() * NCLOUDS; i += 550) {
			i += cloudsPos[1][count];
			if (cloudsPos[2][count] >= 1) {
				g.drawImage(clouds, i, cloudsPos[0][count], 110, 70, this);
			} else
				g.drawImage(doubleClouds, i, cloudsPos[0][count], 220, 150, this);

			if (count == NCLOUDS - 1) {
				break;
			}
			count++;
		}
	}

	/**
	 * It is responsible for loading a level through a black background image that
	 * contains colored pixels which represent different objects.
	 * 
	 * @param image
	 */
	private void loadImageLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w / 2; j++) {
				Color pixel = new Color(image.getRGB(i, j));
				int red = pixel.getRed();
				int blue = pixel.getBlue();
				int green = pixel.getGreen();

				/**
				 * Red = Player
				 * White = Invisible Block
				 * Orange = Floor Block
				 * Purple = ? Block
				 * Cyan = Squared Block
				 * Brown = Brick Block
				 * Blue = WinZone Top
				 * Green = Win Zone
				 * Yellow = Pipeline body left
				 * Purple = Pipeline body right
				 * Bubble gum = Pipeline top left
				 * Dark BLue = Pipeline top right
				 */
				if (red == 255 && blue == 255 && green == 255) { // White pixel
					handler.addObject(new InvisibleBlock(i * 32, j * 32, ObjectId.Block, this));
				}
				if (red == 255 && blue == 0 && green == 154) { // Orange pixel
					handler.addObject(new Block(i * 32, j * 32, 1, ObjectId.Block, this));
				}
				if (red == 255 && blue == 209 && green == 0) { // Purple pixel
					handler.addObject(new Block(i * 32, j * 32, 4, ObjectId.Block, this));
				}
				if (red == 0 && blue == 0 && green == 255) { // Green pixel
					handler.addObject(new WinZone(i * 32, j * 32, 1, ObjectId.WinZone, this));
				}
				if (red == 255 && blue == 0 && green == 0) { // Red pixel
					handler.addObject(new Player(i * 32, j * 32, ObjectId.Player, handler, this));
				}
				if (red == 0 && blue == 240 && green == 255) { // Cyan pixel
					handler.addObject(new Block(i * 32, j * 32, 3, ObjectId.Block, this));
				}
				if (red == 153 && blue == 1 && green == 90) { // Brown pixel
					handler.addObject(new Block(i * 32, j * 32, 2, ObjectId.Block, this));
				}
				if (red == 0 && blue == 255 && green == 0) { // Blue pixel
					handler.addObject(new WinZone(i * 32, j * 32, 2, ObjectId.WinZone, this));
				}
				if (red == 255 && blue == 0 && green == 223) { // Yellow pixel
					handler.addObject(new Block(i * 32, j * 32, 5, ObjectId.Block, this));
				}
				if (red == 76 && blue == 255 && green == 0) { // Purple pixel
					handler.addObject(new Block(i * 32, j * 32, 6, ObjectId.Block, this));
				}
				if (red == 255 && blue == 98 && green == 0) { // Bubble Gum pixel
					handler.addObject(new Block(i * 32, j * 32, 7, ObjectId.Block, this));
				}
				if (red == 0 && blue == 102 && green == 0) { // Dark Blue pixel
					handler.addObject(new Block(i * 32, j * 32, 8, ObjectId.Block, this));
				}
			}
		}
	}

	/**
	 * Load all the music needed for the game.
	 */
	public void loadMusic() {
		try {
			backMusic = AudioSystem.getClip();
			backMusic.open(AudioSystem.getAudioInputStream(getClass().getResource("/marioSong.wav")));
			winMusic = AudioSystem.getClip();
			winMusic.open(AudioSystem.getAudioInputStream(getClass().getResource("/winEffect.wav")));
			loseMusic = AudioSystem.getClip();
			loseMusic.open(AudioSystem.getAudioInputStream(getClass().getResource("/loseEffect.wav")));
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			System.err.println("Error loading music file");
		}
	}

	/**
	 * Set the window size.
	 * 
	 * @param weight
	 * @param height
	 */
	public void setPreferredSize(int weight, int height) {
		setPreferredSize(new Dimension(weight, height));
		setMaximumSize(new Dimension(weight, height));
		setMinimumSize(new Dimension(weight, height));
	}

	// Getters and Setters

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public boolean getWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public boolean getrunning() {
		return running;
	}

	public void setLose(boolean lose) {
		this.lose = lose;
	}

	public static Texture getTextures() {
		return textures;
	}
}
