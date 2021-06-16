package alejandrofan2.burgerRun.framework;

import java.awt.Graphics;
import java.util.LinkedList;

import alejandrofan2.burgerRun.window.Camera;

/**
 * It is in charge of loading and rendering all the objects that we have loaded.
 * 
 * @author alejandrofan2
 *
 */
public class Handler {
	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	private GameObject workingObject;

	/**
	 * Load the logic of all objects.
	 */
	public void tick() {
		for (int i = 0; i < objects.size(); i++) {
			workingObject = objects.get(i);
			workingObject.tick(objects);
		}
	}

	/**
	 * Draw all the objects.
	 * 
	 * @param g
	 * @param camera
	 */
	public void render(Graphics g, Camera camera) {
		for (int i = 0; i < objects.size(); i++) {
			workingObject = objects.get(i);
			if (workingObject.getBounds().intersects(camera.getViewBounds())) {
				workingObject.render(g);
			}
		}
	}

	/**
	 * Add object to the handler.
	 * 
	 * @param object
	 */
	public void addObject(GameObject object) {
		this.objects.add(object);
	}

	/**
	 * Remove object from the handler.
	 * 
	 * @param object
	 */
	public void removeObject(GameObject object) {
		this.objects.remove(object);
	}
}
