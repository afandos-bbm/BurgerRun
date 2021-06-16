package alejandrofan2.burgerRun.framework.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import alejandrofan2.burgerRun.framework.GameObject;
import alejandrofan2.burgerRun.framework.ObjectId;
import alejandrofan2.burgerRun.framework.textureManager.Texture;
import alejandrofan2.burgerRun.window.GamePanel;

/**
 * Define the solid blocks that cannot be seen playing.
 * 
 * @author alejandrofan2
 *
 */
public class InvisibleBlock extends GameObject {

	private Texture tex = GamePanel.getTextures();

	/**
	 * Constructor.
	 * 
	 * @param x
	 * @param y
	 * @param id
	 * @param game
	 */
	public InvisibleBlock(float x, float y, ObjectId id, GamePanel game) {
		super(x, y, id, game);
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
