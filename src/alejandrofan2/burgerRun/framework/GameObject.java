package alejandrofan2.burgerRun.framework;

import java.awt.Graphics;
import java.util.LinkedList;

public abstract class GameObject {

	protected ObjectId id;
	protected float x, y;
	protected float velX, velY;
	
	public GameObject(float x, float y, ObjectId id) {
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public abstract void tick(LinkedList<GameObject> object);
	public abstract void render(Graphics g);
	
	public abstract float getX();
	public abstract float getY();
	public abstract void setX(float x);
	public abstract void setY(float y);

	public abstract float getVelX();
	public abstract float getVely();
	public abstract void setVelX(float velX);
	public abstract void setVelY(float velY);

	public abstract ObjectId getObjectId();
}
