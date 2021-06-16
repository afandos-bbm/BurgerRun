package alejandrofan2.burgerRun.framework.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import alejandrofan2.burgerRun.framework.GameObject;
import alejandrofan2.burgerRun.framework.ObjectId;
import alejandrofan2.burgerRun.framework.textureManager.Texture;
import alejandrofan2.burgerRun.window.GamePanel;

/**
 * Defines the solid blocks that are seen playing.
 * 
 * @author alejandrofan2
 *
 */
public class Block extends GameObject {

	private Texture tex = GamePanel.getTextures();
	private int type;

	/**
	 * Constructor.
	 * 
	 * @param x
	 * @param y
	 * @param type
	 * @param id
	 * @param game
	 */
	public Block(float x, float y, int type, ObjectId id, GamePanel game) {
		super(x, y, id, game);
		this.type = type;
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.orange);
		switch (type) {
		case 1: // Floor Block
			g.drawImage(tex.blockTex[0], (int) x, (int) y, 32, 32, game);
			break;
		case 2: // Brick Block
			g.drawImage(tex.blockTex[1], (int) x, (int) y, 32, 32, game);
			break;
		case 3: // Squared Block
			g.drawImage(tex.blockTex[2], (int) x, (int) y, 32, 32, game);
			break;
		case 4: // ? Block
			g.drawImage(tex.blockTex[3], (int) x, (int) y, 32, 32, game);
			break;
		case 5: // Pipeline body left
			g.drawImage(tex.blockTex[4], (int) x, (int) y, 32, 32, game);
			break;
		case 6: // Pipeline body right
			g.drawImage(tex.blockTex[5], (int) x, (int) y, 32, 32, game);
			break;
		case 7: // Pipeline top left
			g.drawImage(tex.blockTex[6], (int) x, (int) y, 32, 32, game);
			break;
		case 8: // Pipeline top right
			g.drawImage(tex.blockTex[7], (int) x, (int) y, 32, 32, game);
			break;
		default: // Brick Block
			g.drawImage(tex.blockTex[1], (int) x, (int) y, 32, 32, game);
			break;
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

}
