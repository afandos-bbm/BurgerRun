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
import alejandrofan2.burgerRun.framework.objects.BrickBlock;
import alejandrofan2.burgerRun.framework.objects.FloorBlock;
import alejandrofan2.burgerRun.framework.objects.InvisibleBlock;
import alejandrofan2.burgerRun.framework.objects.Player;
import alejandrofan2.burgerRun.framework.objects.WinZone;
import alejandrofan2.burgerRun.framework.textureManager.Texture;

public class GamePanel extends Canvas implements Runnable {

	// constants
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

	private Clip backMusic, loseMusic, winMusic;

	private boolean win = false;
	private boolean lose = false;

	public GamePanel(GameMenu gameMenu) {
		this.menu = gameMenu;
	}

	public synchronized void start() {
		if (running) {
			return;
		}

		running = true;
		thread = new Thread(this);
		thread.start();
	}

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

	private void init() {
		WIDTH = getWidth();
		HEIGHT = getHeight();

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

	private void win() {
		JOptionPane.showMessageDialog(this, "Felicidades has ganado!", "Burger Run", JOptionPane.CLOSED_OPTION);
		running = false;
		menu.setVisible(true);
		menu.setWin(true);
	}

	private void lose() {
		JOptionPane.showConfirmDialog(this, "Has perdido...", "Burger Run", JOptionPane.CLOSED_OPTION);
		running = false;
		menu.setWin(true);
		menu.setVisible(true);
	}

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
		////////////////
		g.dispose();
		bs.show();

	}

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

	public void setPreferredSize(int weight, int height) {
		setPreferredSize(new Dimension(weight, height));
		setMaximumSize(new Dimension(weight, height));
		setMinimumSize(new Dimension(weight, height));
	}

	private void loadImageLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();

		for (int i = 0; i < h / 2; i++) {
			for (int j = 0; j < w / 2; j++) {
				Color pixel = new Color(image.getRGB(i, j));
				int red = pixel.getRed();
				int blue = pixel.getBlue();
				int green = pixel.getGreen();

				/**
				 * White = Bloque invisible
				 * Orange = Bloque
				 * Purple = Bloque especial
				 * Red = Player
				 * Green = Meta
				 */
				if (red == 255 && blue == 255 && green == 255) { // White pixel
					handler.addObject(new InvisibleBlock(i * 32, j * 32, ObjectId.Block, this));
				}
				if (red == 255 && blue == 0 && green == 154) { // Orange pixel
					handler.addObject(new FloorBlock(i * 32, j * 32, ObjectId.Block, this));
				}
				if (red == 255 && blue == 209 && green == 0) { // Purple pixel
					handler.addObject(new BrickBlock(i * 32, j * 32, ObjectId.Block, this));
				}
				if (red == 0 && blue == 0 && green == 255) { // Green pixel
					handler.addObject(new WinZone(i * 32, j * 32, ObjectId.WinZone, this));
				}
				if (red == 255 && blue == 0 && green == 0) { // Red pixel
					handler.addObject(new Player(i * 32, j * 32, ObjectId.Player, handler, this));
				}
			}
		}
	}

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
