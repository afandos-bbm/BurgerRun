package alejandrofan2.BurgerRun.main;

import javax.swing.*;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 6261175851689900695L;
	private JPanel gamePanel;
	
	public MainFrame() {
		
		gamePanel = new GamePanel();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("BurgerRun");
		setLocation(500, 200);
		add(gamePanel);
		pack();
		setResizable(false);
		setVisible(true);		
	}
}
