package alejandrofan2.BurgerRun.window;

import java.awt.Rectangle;

import alejandrofan2.BurgerRun.framework.GameObject;

public class Camera {

	private float x, y;
	private final int WIDTH, HEIGHT;

	public Camera(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.WIDTH = width;
		this.HEIGHT = height;
	}

	public Rectangle getViewBounds() {
		return new Rectangle(-(int) x, -(int) y, WIDTH, HEIGHT);
	}

	public void tick(GameObject player) {
		x = -player.getX() + 128;
	}

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
