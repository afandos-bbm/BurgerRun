package alejandrofan2.burgerRun.framework;

import java.awt.Graphics;
import java.util.LinkedList;

import alejandrofan2.burgerRun.window.Camera;

public class Handler {
	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	private GameObject workingObject;

	public void tick() {
		for (int i = 0; i < objects.size(); i++) {
			workingObject = objects.get(i);
			workingObject.tick(objects);
		}
	}

	public void render(Graphics g, Camera camera) {
		for (int i = 0; i < objects.size(); i++) {
			workingObject = objects.get(i);
			if (workingObject.getBounds().intersects(camera.getViewBounds())) {
				workingObject.render(g);
			}
		}
	}

	public void addObject(GameObject object) {
		this.objects.add(object);
	}

	public void removeObject(GameObject object) {
		this.objects.remove();
	}
}
