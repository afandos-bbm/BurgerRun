package alejandrofan2.BurgerRun.main;

import alejandrofan2.burgerRun.window.GamePanel;
import alejandrofan2.burgerRun.window.MainFrame;

public class Main {
	
	public static void main( String[] args) {
		new MainFrame(800, 400, "BurgerRun", new GamePanel());
	}
}
