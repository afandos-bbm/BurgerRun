package alejandrofan2.burgerRun.framework;

import java.awt.Graphics;
import java.util.LinkedList;

import alejandrofan2.burgerRun.framework.objects.Block;
import alejandrofan2.burgerRun.window.GamePanel;

public class Handler {
	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	private GameObject workingObject;
	
	public void tick() {
		for (int i = 0; i < objects.size(); i++) {
			workingObject = objects.get(i);
			workingObject.tick(objects);
		}
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			workingObject = objects.get(i);
			workingObject.render(g);
		}
	}
	
	public void createLevel() {
		// Floor
		for (int i = 0; i < GamePanel.WIDTH*2; i += 32) {
			addObject(new Block(i, GamePanel.HEIGHT-32, ObjectId.Block));
		}
		// Left Barrier
		for (int i = 0; i < GamePanel.HEIGHT+32; i += 32) {
			addObject(new Block(0, i, ObjectId.Block));
		}
		// 2ºFloor
		for (int i = 200; i < 600; i += 32) {
			addObject(new Block(i, 320, ObjectId.Block));
		}
		
	}
	
	public void addObject(GameObject object) {
		this.objects.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.objects.remove();
	}
}
