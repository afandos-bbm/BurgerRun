package alejandrofan2.burgerRun.window;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 6261175851689900695L;

	public MainFrame(int weight, int height, String title, GamePanel game) {
		game.setPreferredSize(weight, height);

		setTitle(title);
		add(game);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		game.start();
	}
}
