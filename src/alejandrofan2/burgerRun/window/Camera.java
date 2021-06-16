package alejandrofan2.burgerRun.window;

import java.awt.Rectangle;

import alejandrofan2.burgerRun.framework.GameObject;

/**
 * Define the camera and its movement.
 * 
 * @author alejandrofan2
 *
 */
public class Camera {

	private float x, y;
	private final int WIDTH, HEIGHT;

	/**
	 * Constructor.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Camera(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.WIDTH = width;
		this.HEIGHT = height;
	}

	/**
	 * Returns the rectangle that the camera sees.
	 * 
	 * @return Rectangle
	 */
	public Rectangle getViewBounds() {
		return new Rectangle(-(int) x, -(int) y, WIDTH, HEIGHT);
	}

	/**
	 * Update the camera.
	 * 
	 * @param player
	 */
	public void tick(GameObject player) {
		x = -player.getX() + 128;
	}

	// Getters and Setters

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
