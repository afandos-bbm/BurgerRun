package alejandrofan2.burgerRun.framework.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import alejandrofan2.burgerRun.framework.GameObject;
import alejandrofan2.burgerRun.framework.ObjectId;
import alejandrofan2.burgerRun.framework.textureManager.Texture;
import alejandrofan2.burgerRun.window.GamePanel;

public class WinZone extends GameObject {

	private Texture tex = GamePanel.getTextures();
	private int type;

	public WinZone(float x, float y, int type, ObjectId id, GamePanel game) {
		super(x, y, id, game);
		this.type = type;
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);

		switch (type) {
		case 1:
			g.drawImage(tex.blockTex[8], (int) x, (int) y, 32, 32, game);
			break;
		case 2:
			g.drawImage(tex.blockTex[9], (int) x, (int) y, 32, 32, game);
		default:
			break;
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

}
