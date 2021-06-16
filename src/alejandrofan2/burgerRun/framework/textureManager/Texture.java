package alejandrofan2.burgerRun.framework.textureManager;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Texture {

	private SpriteSheet textureSheet;
	private BufferedImage textureSheetImg;

	public BufferedImage[] blockTex = new BufferedImage[11];
	public BufferedImage[] playerTex = new BufferedImage[7];

	public Texture() {
		try {
			textureSheetImg = ImageIO.read(getClass().getResource("/textureSheet.png"));
		} catch (Exception e) {
			System.err.println("Error loading the SpriteSheet images, aborting...");
			e.printStackTrace();
			System.exit(1);
		}

		textureSheet = new SpriteSheet(textureSheetImg);

		getTextures();
	}

	private void getTextures() {
		// Blocks
		blockTex[0] = textureSheet.grabTexture(1, 1, 32, 32); // Floor Block
		blockTex[1] = textureSheet.grabTexture(2, 1, 32, 32); // Brick Block
		blockTex[2] = textureSheet.grabTexture(3, 1, 32, 32); // Squared Block
		blockTex[3] = textureSheet.grabTexture(4, 1, 32, 32); // ? Block
		blockTex[4] = textureSheet.grabTexture(11, 1, 32, 32); // Pipeline top left
		blockTex[5] = textureSheet.grabTexture(12, 1, 32, 32); // Pipeline top right
		blockTex[6] = textureSheet.grabTexture(9, 1, 32, 32); // Pipeline body left
		blockTex[7] = textureSheet.grabTexture(10, 1, 32, 32); // Pipeline body right
		blockTex[8] = textureSheet.grabTexture(1, 2, 32, 32); // Flag body
		blockTex[9] = textureSheet.grabTexture(2, 2, 32, 32); // Flag top
		blockTex[10] = textureSheet.grabTexture(3, 2, 32, 32); // Flag Flag

		// Static Mario
		playerTex[0] = textureSheet.grabTexture(1, 2, 32, 64); // Mario Not Moving
		playerTex[1] = textureSheet.grabTexture(2, 2, 32, 64); // Ducking Mario

		// Walk Animation
		playerTex[2] = textureSheet.grabTexture(1, 3, 32, 64);
		playerTex[3] = textureSheet.grabTexture(2, 3, 32, 64);
		playerTex[4] = textureSheet.grabTexture(3, 3, 32, 64);
		playerTex[5] = textureSheet.grabTexture(4, 3, 32, 64);
		playerTex[6] = textureSheet.grabTexture(5, 3, 32, 64);

	}
}
