package alejandrofan2.burgerRun.textureManager;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Texture {

	private SpriteSheet blockSheet, playerSheet;
	private BufferedImage blockSheetImg;
	private BufferedImage playerSheetImg;
	
	public BufferedImage[] blocks = new BufferedImage[1];
	public BufferedImage[] player = new BufferedImage[1];
	
	public Texture() {
		try {
			blockSheetImg = ImageIO.read(getClass().getResource("/sprite1.png"));
			playerSheetImg = ImageIO.read(getClass().getResource("/sprite1.png")); 
		} catch (Exception e) {
			System.err.println("Error loading the SpriteSheet images, aborting...");
			e.printStackTrace();
			System.exit(1);
		}
		
		blockSheet = new SpriteSheet(blockSheetImg);
		playerSheet = new SpriteSheet(playerSheetImg);
		
		getTextures();
	}
	
	private void getTextures() {
		blocks[0] = blockSheet.grabTexture(1, 1, 32, 32);
		player[0] = playerSheet.grabTexture(1, 1, 32, 32);
	}
}
