package alejandrofan2.burgerRun.framework;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class BufferedImageLoader {
	
	private BufferedImage image;
	
	public BufferedImage loadImage(String path) {
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (Exception e) {
			e.getStackTrace();
			System.err.println("Error loagind the file in BufferedimageLoader.");
		}
		return image;
	}
}
