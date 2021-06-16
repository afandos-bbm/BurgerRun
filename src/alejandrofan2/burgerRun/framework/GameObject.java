package alejandrofan2.burgerRun.framework;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import alejandrofan2.burgerRun.window.GamePanel;

/**
 * One of the most important classes is parent and manages all the objects in
 * the game.
 * 
 * @author alejandrofan2
 *
 */
public abstract class GameObject {

	protected GamePanel game;

	protected ObjectId id;
	protected float x, y;
	protected float velX, velY;
	protected boolean falling = true;
	protected boolean jumping = false;
	protected boolean ducking = false;
	protected boolean air = false;

	/**
	 * Constructor.
	 * 
	 * @param x
	 * @param y
	 * @param id
	 * @param game
	 */
	public GameObject(float x, float y, ObjectId id, GamePanel game) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.game = game;
	}

	/**
	 * Updates the logic of the object.
	 * 
	 * @param object
	 */
	public abstract void tick(LinkedList<GameObject> object);

	/**
	 * Draw the object.
	 * 
	 * @param g
	 */
	public abstract void render(Graphics g);

	/**
	 * Set the bounds for collision.
	 * 
	 * @return Rectangle
	 */
	public abstract Rectangle getBounds();

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

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public ObjectId getId() {
		return id;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public boolean isDucking() {
		return ducking;
	}

	public void setDucking(boolean ducking) {
		this.ducking = ducking;
	}

	public boolean isAir() {
		return air;
	}

	public void setAir(boolean air) {
		this.air = air;
	}
}
