package alejandrofan2.BurgerRun.main;

import java.awt.Color;
import java.awt.Graphics2D;

import alejandrofan2.BurgerRun.shapes.Drawable;
import alejandrofan2.BurgerRun.shapes.Square;
/**
 * 
 * @author Yalchin ALIYEV
 * @version 1.0 26.03.2016
 *
 */
public class Obstacle extends Square implements Drawable{

	private int R, G, B;
	
	public Obstacle( int x, int y) {
		
		super( 20);
		setLocation(x, y);
		
		R = (int) (Math.random() * 256);
		G = (int) (Math.random() * 256);
		B = (int) (Math.random() * 256);
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		g.setColor( new Color( R, G, B));
		g.fillRect(getX(), getY(), side, side);
	}
}
