package alejandrofan2.burgerRun.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import alejandrofan2.burgerRun.window.GameMenu;
import alejandrofan2.burgerRun.window.GamePanel;

public class KeyListener extends KeyAdapter {

	private Handler handler;
	private GameMenu menu;
	private GamePanel game;

	public KeyListener(Handler handler, GameMenu menu, GamePanel game) {
		this.handler = handler;
		this.menu = menu;
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_ESCAPE) {
			menu.setVisible(true);
			menu.setFocusable(true);
		}

		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject workingObject = handler.objects.get(i);

			if (workingObject.getId() == ObjectId.Player) {
				if (key == KeyEvent.VK_D) {
					workingObject.setVelX(5);
				}
				if (key == KeyEvent.VK_A) {
					workingObject.setVelX(-5);
				}
				if (key == KeyEvent.VK_SPACE && !workingObject.isJumping()) {
					workingObject.setJumping(true);
					workingObject.setVelY(-5);
				}
				if (key == KeyEvent.VK_W && !workingObject.isJumping()) {
					workingObject.setJumping(true);
					workingObject.setVelY(-5);
				}
				if (key == KeyEvent.VK_S) {
					workingObject.setDucking(true);
					workingObject.setVelX(0);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject workingObject = handler.objects.get(i);

			if (workingObject.getId() == ObjectId.Player) {
				if (key == KeyEvent.VK_D) {
					workingObject.setVelX(0);
				}
				if (key == KeyEvent.VK_A) {
					workingObject.setVelX(0);
				}
				if (key == KeyEvent.VK_SPACE) {
					workingObject.setVelY(0);
				}
				if (key == KeyEvent.VK_W) {
					workingObject.setVelY(0);
				}
				if (key == KeyEvent.VK_S) {
					workingObject.setDucking(false);
				}
			}
		}
	}
}
