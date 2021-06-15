package alejandrofan2.burgerRun.framework.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import alejandrofan2.burgerRun.framework.BufferedImageLoader;
import alejandrofan2.burgerRun.framework.GameObject;
import alejandrofan2.burgerRun.framework.Handler;
import alejandrofan2.burgerRun.framework.ObjectId;
import alejandrofan2.burgerRun.window.GamePanel;

public class Player extends GameObject {

	private Handler handler;
	private GamePanel game;

	private BufferedImage playerimg;

	private float width = 32, height = 64;
	private final float MAX_SPEED = 15;

	private float gravity = 0.09f;

	public Player(float x, float y, ObjectId id, Handler handler, GamePanel game) {
		super(x, y, id);
		this.handler = handler;
		this.game = game;

		BufferedImageLoader loader = new BufferedImageLoader();
		playerimg = loader.loadImage("/super-mario.gif");
	}

	@Override
	public void tick(LinkedList<GameObject> objects) {
		x += velX;
		y += velY;

		if (falling || jumping) {
			velY += gravity;
			if (velY > MAX_SPEED) {
				velY = MAX_SPEED;
			}
		}

		collision(objects);
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g.setColor(Color.blue);
		g.drawImage(playerimg, (int) x, (int) y, (int) width, (int) height, game);

		g.setColor(Color.red);
		g2d.draw(getBounds());
		g2d.draw(getBoundsTop());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());

	}

	private void collision(LinkedList<GameObject> objects) {
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject workingObject = handler.objects.get(i);

			if (workingObject.getId() == ObjectId.Block) {
				// Bottom
				if (getBounds().intersects(workingObject.getBounds())) {
					y = workingObject.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
				} else
					falling = true;

				// Top
				if (getBoundsTop().intersects(workingObject.getBounds())) {
					y = workingObject.getY() + 35;
					velY = 0;
				}

				// Right
				if (getBoundsRight().intersects(workingObject.getBounds())) {
					x = workingObject.getX() - width - 1;
				}

				// Left
				if (getBoundsLeft().intersects(workingObject.getBounds())) {
					x = workingObject.getX() + 33;
				}
			}

			if (workingObject.getId() == ObjectId.WinZone) {
				if (getBounds().intersects(workingObject.getBounds())) {
					game.setWin(true);
				}
			}
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) ((int) x + (width / 2) - (width / 2) / 2), (int) ((int) y + (height / 2)),
				(int) width / 2, (int) height / 2);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle((int) ((int) x + (width / 2) - (width / 2) / 2), (int) y, (int) width / 2,
				(int) height / 2);
	}

	public Rectangle getBoundsRight() {
		return new Rectangle((int) ((int) x + width - 5), (int) y + 5, 5, (int) height - 10);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle((int) x, (int) y + 5, 5, (int) height - 10);
	}

}
