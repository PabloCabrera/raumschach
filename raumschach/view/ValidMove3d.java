package raumschach.view;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;


class ValidMove3d extends Object3d {
	static BufferedImage img2d = null;
	static BufferedImage img3d = null;
	public final static int SPRITE3D_WIDTH = 64;
	public final static int SPRITE3D_HEIGHT = 128;
	public final static int SPRITE3D_ANGLES = 1;
	
	static {
		try {
			ValidMove3d.img2d = ImageIO.read (new File (GraphicView.imagePath + "validmove.png"));
		} catch (Exception e) {
			System.out.println ("Couldn't load 2D valid move image");
			System.out.println ("Exception: " + e.getMessage ());
		}

		try {
			ValidMove3d.img3d = ImageIO.read (new File (GraphicView.imagePath + "validmove3d.png"));
		} catch (Exception e) {
			System.out.println ("Couldn't load 3D valid move image");
			System.out.println ("Exception: " + e.getMessage ());
		}
	}

	public ValidMove3d (float z, float x, float y) {
		super (z, x, y);
	}

	public BufferedImage getImage2D () {
		return ValidMove3d.img2d;
	}

	public BufferedImage get3DSet () {
		return ValidMove3d.img3d;
	}

	public int getSpriteAngles () {
		return ValidMove3d.SPRITE3D_ANGLES;
	}

	public int getSpriteWidth () {
		return ValidMove3d.SPRITE3D_WIDTH;
	}

	public int getSpriteHeight () {
		return ValidMove3d.SPRITE3D_HEIGHT;
	}


}
