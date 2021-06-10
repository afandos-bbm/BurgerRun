package alejandrofan2.burgerRun.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import alejandrofan2.BurgerRun.shapes.Rectangle;
import alejandrofan2.BurgerRun.shapes.ShapeContainer;
import alejandrofan2.BurgerRun.shapes.Square;
import alejandrofan2.burgerRun.framework.objects.Obstacle;

public class GamePanel extends Canvas implements Runnable {

	// constants
	private static final long serialVersionUID = 3367166757979631248L;
	public static final int BASEY = 250;
	public static final int MINGAP = 170;
	public static final int MAXGAP = 340;
	
	private boolean running = false;
	private Thread thread;
	
	// properties
	private ShapeContainer obstacles;
	private Timer obstacleTimer, runnerTimer, jumpTimer;
	private static int randomGap = (int) (Math.random() * (MAXGAP - MINGAP)) + MINGAP;
	private ArrayList<Image> runnerGif;
	private int index = 1;
	private int heightOfJump;
	private boolean flag;
	private static int jumpCount = 0;
	private int score;
	private Rectangle border;
	private boolean isGameOver;
	private int obstacleSpeed;
	private Timer obstacleTimer1;
	
	// constructors
	public GamePanel() {
		
		setFocusable(true);
//		
//		init();
//		this.addMouseListener( new JumpMouseListener());
//		this.addKeyListener( new JumpKeyListener());
//		
	}
	
	public synchronized void start() {
		if (running) {
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		System.out.println("Inside: " + Thread.currentThread().getName());
		
		long lastTime = System.nanoTime();
		double tps = 60;
		double ns = 1000000000 / tps;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames + " TPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	private void tick() {
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		////////////////
		//Draw zone
//		setBackground(Color.BLACK);
//		Graphics2D g2 = (Graphics2D) g;
//		
//	//	g2.drawString( "by Yalchin", this.getWidth()-100, 20);
//		
//		
//		
//		g2.drawImage(runnerGif.get(index), 30, BASEY - 80 - heightOfJump, 80, 80, this);
//	//	g2.drawRect( 50, BASEY - 80 - heightOfJump, 40, 70);
//		
//		g2.fillRect(0, BASEY, getWidth(), 8);
//		border.setLocation(50, BASEY - 80 - heightOfJump);
//		
//		Iterator i = obstacles.iterator();
//		while( i.hasNext()) {
//			( (Obstacle) i.next() ).draw(g2);
//		}
		////////////////
		g.dispose();
		bs.show();
		
	}

	private void init() {
		
		score = 0;
		System.out.println("Score: " + score);
		
		obstacleSpeed = 6;
		index = 0;
		heightOfJump = 0;
		flag = false;
		jumpTimer = new Timer( 3,  new JumpActionListener());
		border = new Rectangle( 35, 60);
		border.setLocation(50, BASEY - 80 - heightOfJump);
		
		isGameOver = false;
		
		obstacles = new ShapeContainer();
		obstacles.add( new Obstacle( 780, BASEY - 20));
		
		runnerGif = new ArrayList<Image>();
		for ( int i = 0; i < 9; i ++)
			runnerGif.add(( new ImageIcon( this.getClass().getResource( "/alejandrofan2/BurgerRun/resources/tmp-" + i + ".gif")).getImage()));
		
		obstacleTimer = new Timer(obstacleSpeed, new TimerActionListener());
		obstacleTimer1 = new Timer(obstacleSpeed, new TimerActionListener());
		
		runnerTimer = new Timer(32, new RunnerActionListener());
		obstacleTimer.start();
		obstacleTimer1.start();
		runnerTimer.start();
		
		
	}
	
	class TimerActionListener implements ActionListener {
		public void actionPerformed( ActionEvent e) {
			
			
			for ( int i = 0; i < obstacles.size(); i++) {
				Obstacle obstacle = ( (Obstacle) obstacles.getShape(i) );
				obstacle.setLocation( obstacle.getX() - 1, obstacle.getY());
				
				if ( obstacle.getX() == -10) {
					obstacle.setSelected( true);
				}
				
			}
		    
			Obstacle obstacle = ( (Obstacle) obstacles.getShape( obstacles.size() - 1) );
			if ( 800 - obstacle.getX() == randomGap) {
				obstacles.add( new Obstacle( 780, BASEY - 20));
				randomGap = (int) ( Math.random() * (MAXGAP - MINGAP)) + MINGAP;
				
				score++;
				System.out.println("Score: " + score);
			}
		    
			obstacles.remove();
			
			for ( int i = 0; !isGameOver && i < 20; i++) {

				if ( border.contains(  ( (Square) obstacles.getShape(0)).getX() + i , ((Square) obstacles.getShape(0)).getY()) != null) {
					isGameOver = true;
				}
			}
			
			if ( isGameOver) {
				
				runnerTimer.stop();
				try {
					runnerTimer.removeActionListener(runnerTimer.getActionListeners()[0]);
				} catch (Exception e2) {
					System.err.println("Error removing the runner listener");
				}
				obstacleTimer.stop();
				try {
					obstacleTimer.removeActionListener( obstacleTimer.getActionListeners()[0]);
				} catch (Exception e2) {
					System.err.println("Error removing the timer listener");
				}
				jumpTimer.stop();
				if ( jumpTimer.getActionListeners()[0] != null) {
					jumpTimer.removeActionListener(jumpTimer.getActionListeners()[0]);
				}

				
				int confirm = JOptionPane.showConfirmDialog(null, "Score: " + score + "\n" + "Play again?", "Game Over!", 0);
				if ( confirm == 0)
					init();
				else
					System.exit(0);
			}
			
		//	System.out.println(obstacles.size());
			repaint();
		}
	}
	
	
	
	class RunnerActionListener implements ActionListener {
		public void actionPerformed( ActionEvent e) {
			
			if ( index == 8)
				index = 0;
			else
				index++;
		}
	}
	
	class JumpMouseListener extends MouseAdapter {
		public void mouseClicked( MouseEvent e) {
			
			jumpTimer.setDelay(3);
			jumpTimer.start();
			
			
		}
		
	}

	class JumpActionListener implements ActionListener {
		
		public void actionPerformed( ActionEvent e) {
			
			
			
			if ( jumpCount == 65) {
				flag = true;
			}
			
			if (flag) {
				jumpCount--;
				if ( jumpCount == 0) {
					jumpTimer.stop();
					flag = false;
					score += 2;
					System.out.println("Score: " + score);
				}
			}
			else {
				jumpCount++;
			}
			
			heightOfJump = 1 * jumpCount;
			
			
		}
	}
	
	class JumpKeyListener extends KeyAdapter {
		public void keyPressed( KeyEvent e){
			if ( e.getExtendedKeyCode() == e.VK_UP) {
				jumpTimer.setDelay(3);
				jumpTimer.start();
			}
			else if ( e.getExtendedKeyCode() == e.VK_DOWN) {
			//	jumpTimer.stop();
				jumpTimer.setDelay(2);
			
			}
			else if ( e.getExtendedKeyCode() == e.VK_RIGHT) {
				if ( obstacleSpeed > 1) {
					obstacleSpeed--;
					obstacleTimer.setDelay( obstacleSpeed);
				}
				
			}
			else if ( e.getExtendedKeyCode() == e.VK_LEFT) {
				if ( obstacleSpeed < 5) {
					obstacleSpeed++;
					obstacleTimer.setDelay( obstacleSpeed);
				}
			}
		}
	}

	public void setPreferredSize(int weight, int height) {
		setPreferredSize(new Dimension(weight, height));
		setMaximumSize(new Dimension(weight, height));
		setMinimumSize(new Dimension(weight, height));
	}
}
