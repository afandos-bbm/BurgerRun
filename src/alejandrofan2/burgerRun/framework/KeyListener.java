package alejandrofan2.burgerRun.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
	
	Handler handler;
	
	public KeyListener(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
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
			}
		}
	}
	
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

			}
		}
	}
}
