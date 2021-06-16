package alejandrofan2.burgerRun.framework.textureManager;

import java.awt.image.BufferedImage;

/**
 * Load an image containing multiple textures within it.
 * 
 * @author alejandrofan2
 *
 */
public class SpriteSheet {

	private BufferedImage image;

	/**
	 * Constructor.
	 * 
	 * @param image
	 */
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}

	/**
	 * Grab a texture from the texture sheet.
	 * 
	 * @param col
	 * @param row
	 * @param width
	 * @param height
	 * @return BufferedImage
	 */
	public BufferedImage grabTexture(int col, int row, int width, int height) {
		BufferedImage img = image.getSubimage((col * width) - width, (row * height) - height, width, height);
		return img;
	}
}
