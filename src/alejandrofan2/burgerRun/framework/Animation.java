package alejandrofan2.burgerRun.framework;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class is in charge of creating animations reproducing the images that we
 * pass to the constructor.
 * 
 * @author alejandrofan2
 *
 */
public class Animation {

	private int speed;
	private int frames;

	private int index = 0;
	private int count = 0;

	private BufferedImage[] images;
	private BufferedImage currentImage;

	/**
	 * Constructor.
	 * 
	 * @param speed
	 * @param args
	 */
	public Animation(int speed, BufferedImage... args) {
		this.speed = speed;
		images = new BufferedImage[args.length];
		for (int i = 0; i < args.length; i++) {
			images[i] = args[i];
		}
		frames = args.length;
	}

	/**
	 * This method takes care of the logic of the animation.
	 */
	public void runAnimation() {
		index++;
		if (index > speed) {
			index = 0;
			nextFrame();
		}
	}

	/**
	 * This method goes to the next image of the animation.
	 */
	private void nextFrame() {
		for (int i = 0; i < frames; i++) {
			if (count == i) {
				currentImage = images[i];
			}
		}
		count++;

		if (count > frames) {
			count = 0;
		}
	}

	/**
	 * This method draws the animation.
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	public void drawAnimation(Graphics g, int x, int y) {
		g.drawImage(currentImage, x, y, null);
	}

	/**
	 * This method draws the animation resized.
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param resizeX
	 * @param resizeY
	 */
	public void drawAnimation(Graphics g, int x, int y, int resizeX, int resizeY) {
		g.drawImage(currentImage, x, y, resizeX, resizeY, null);
	}
}
