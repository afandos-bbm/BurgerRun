package alejandrofan2.burgerRun.window;

import javax.swing.JFrame;

public class GameMenu extends JFrame {

	private static final long serialVersionUID = 6261175851689900695L;

	public GameMenu(int weight, int height, String title) {

		setTitle(title);
		add();
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

	}

}
