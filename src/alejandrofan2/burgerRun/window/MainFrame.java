package alejandrofan2.burgerRun.window;

import javax.swing.JFrame;

/**
 * Defines the window where the game canvas goes.
 * 
 * @author alejandrofan2
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 6261175851689900695L;

	/**
	 * Constructor.
	 * 
	 * @param weight
	 * @param height
	 * @param title
	 * @param game
	 */
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
