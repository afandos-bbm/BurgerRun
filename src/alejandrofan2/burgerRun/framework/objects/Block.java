package alejandrofan2.BurgerRun.framework.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import alejandrofan2.BurgerRun.framework.GameObject;
import alejandrofan2.BurgerRun.framework.ObjectId;

public class Block extends GameObject {

	public Block(float x, float y, ObjectId id) {
		super(x, y, id);
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.drawRect((int) x, (int) y, 32, 32);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

}
