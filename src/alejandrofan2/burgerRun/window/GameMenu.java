package alejandrofan2.burgerRun.window;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import alejandrofan2.burgerRun.framework.BufferedImageLoader;

public class GameMenu extends JFrame implements ActionListener {

	private static final long serialVersionUID = 6261175851689900695L;
	private Container contentPane = getContentPane();
	private JPanel menuPanel = new JPanel();
	private MainFrame frame;
	private GamePanel game;

	private BufferedImage logo;
	private JLabel logoLabel;
	private JLabel nameLabel = new JLabel("github.com/alejandrofan2");
	private JButton play = new JButton("PLAY");
	private JButton restart = new JButton("RESTART");
	private JButton lvlSelector = new JButton("LEVELS");
	private JButton exit = new JButton("EXIT");

	private boolean playing = false;
	private boolean win = false;
	private boolean lose = false;

	public GameMenu(int height, int width, String title) {
		BufferedImageLoader loader = new BufferedImageLoader();
		logo = loader.loadImage("/logo.jpg");
		logoLabel = new JLabel(new ImageIcon(logo));

		setTitle(title);
		setPreferredSize(new Dimension(height, width));
		setContentPane(contentPane);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setVisible(true);

		logoLabel.setBounds(new Rectangle(10, 5, 220, 110));
		play.setAlignmentX(CENTER_ALIGNMENT);
		play.setBounds(new Rectangle(5, 120, 230, 50));
		play.addActionListener(this);
		restart.setAlignmentX(CENTER_ALIGNMENT);
		restart.setBounds(new Rectangle(5, 170, 230, 50));
		restart.addActionListener(this);
		lvlSelector.setAlignmentX(CENTER_ALIGNMENT);
		lvlSelector.setBounds(new Rectangle(5, 220, 230, 50));
		lvlSelector.addActionListener(this);
		exit.setAlignmentX(CENTER_ALIGNMENT);
		exit.setBounds(new Rectangle(5, 270, 230, 50));
		exit.addActionListener(this);
		nameLabel.setBounds(new Rectangle(40, 315, 200, 50));

		menuPanel.add(logoLabel);
		menuPanel.add(play);
		menuPanel.add(restart);
		menuPanel.add(lvlSelector);
		menuPanel.add(exit);
		menuPanel.add(nameLabel);
		menuPanel.setLayout(null);

		contentPane.add(menuPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == play) {
			if (!playing) {
				game = new GamePanel(this);
				frame = new MainFrame(800, 407, "BurgerRun", game);
				playing = true;
			}
			if (win || lose) {
				game.backMusic.stop();
				frame.dispose();
				game = new GamePanel(this);
				frame = new MainFrame(800, 407, "BurgerRun", game);
				win = false;
				lose = false;
			}
			this.setVisible(false);
		} else if (e.getSource() == restart) {
			if (!playing) {
				JOptionPane.showConfirmDialog(menuPanel, "Debes de estar jugando para poder reiniciar el nivel.",
						"AVISO", JOptionPane.CLOSED_OPTION);
			} else {
				game.backMusic.stop();
				frame.dispose();
				game = new GamePanel(this);
				frame = new MainFrame(800, 407, "BurgerRun", game);
				this.setVisible(false);
			}
		} else if (e.getSource() == lvlSelector) {
			JOptionPane.showConfirmDialog(menuPanel, "Proximamente se implementaran nuevos niveles.", "AVISO",
					JOptionPane.CLOSED_OPTION);
		} else if (e.getSource() == exit) {
			if (JOptionPane.showConfirmDialog(menuPanel, "Seguro que quieres cerrar el juego?", "AVISO",
					JOptionPane.YES_NO_OPTION) == 0) {
				System.exit(0);
			}
		}
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public void setLose(boolean lose) {
		this.lose = lose;
	}
}
