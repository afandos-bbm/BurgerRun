package alejandrofan2.burgerRun.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import alejandrofan2.burgerRun.framework.BufferedImageLoader;
import alejandrofan2.burgerRun.framework.Handler;
import alejandrofan2.burgerRun.framework.KeyListener;
import alejandrofan2.burgerRun.framework.ObjectId;
import alejandrofan2.burgerRun.framework.objects.BrickBlock;
import alejandrofan2.burgerRun.framework.objects.FloorBlock;
import alejandrofan2.burgerRun.framework.objects.InvisibleBlock;
import alejandrofan2.burgerRun.framework.objects.Player;
import alejandrofan2.burgerRun.framework.objects.WinZone;

public class GamePanel extends Canvas implements Runnable {

	// constants
	private static final long serialVersionUID = 3367166757979631248L;

	private boolean running = false;
	private Thread thread;
	private Handler handler;
	private Camera camera;

	public static int WIDTH, HEIGHT;

	private BufferedImage level, clouds, doubleClouds;
	private final Integer NCLOUDS = 15;
	private int[][] cloudsPos;

	private boolean win = false;

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
			if (win) {
				win();
			}
		}
	}

	private void init() {
		WIDTH = getWidth();
		HEIGHT = getHeight();

		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/lvl1.png");
		clouds = loader.loadImage("/cloud.png");
		doubleClouds = loader.loadImage("/doubleCloud.png");

		camera = new Camera(0, 0, WIDTH, HEIGHT);
		handler = new Handler();
		loadImageLevel(level);
		cloudsPos = addClouds();

		this.addKeyListener(new KeyListener(handler));
	}

	private void win() {
		running = false;
	}

	private void tick() {
		handler.tick();
		for (int i = 0; i < handler.objects.size(); i++) {
			if (handler.objects.get(i).getId() == ObjectId.Player) {
				camera.tick(handler.objects.get(i));
			}
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
		g.setColor(new Color(151, 181, 252));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g2d.translate(camera.getX(), camera.getY());

		int count = 0;
		for (int i = 600; i < clouds.getWidth() * NCLOUDS; i += 250) {
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
			double random = (Math.random() * 3);
			cloudsPos[2][i] = (int) random;
		}
		return cloudsPos;
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
					handler.addObject(new InvisibleBlock(i * 32, j * 32, ObjectId.Block));
				}
				if (red == 255 && blue == 0 && green == 154) { // Orange pixel
					handler.addObject(new FloorBlock(i * 32, j * 32, ObjectId.Block));
				}
				if (red == 255 && blue == 209 && green == 0) { // Purple pixel
					handler.addObject(new BrickBlock(i * 32, j * 32, ObjectId.Block));
				}
				if (red == 0 && blue == 0 && green == 255) { // Green pixel
					handler.addObject(new WinZone(i * 32, j * 32, ObjectId.WinZone));
				}
				if (red == 255 && blue == 0 && green == 0) { // Red pixel
					handler.addObject(new Player(i * 32, j * 32, ObjectId.Player, handler, this));
				}
			}
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
}
