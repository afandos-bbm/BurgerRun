package alejandrofan2.burgerRun.framework;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * Imported class to load images more easily.
 * 
 * @author alejandrofan2
 *
 */
public class BufferedImageLoader {

	private BufferedImage image;

	/**
	 * Loads the image to a BufferedImage.
	 * 
	 * @param path
	 * @return BufferedImage
	 */
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
