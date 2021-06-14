package alejandrofan2.burgerRun.framework.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import alejandrofan2.burgerRun.framework.GameObject;
import alejandrofan2.burgerRun.framework.ObjectId;

public class InvisibleBlock extends GameObject {

	public InvisibleBlock(float x, float y, ObjectId id) {
		super(x, y, id);
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

}
