package raumschach.view;
import raumschach.event.GameEvent;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import javax.imageio.ImageIO;

class Piece3d extends Object3d {
	public final static int SPRITE3D_WIDTH = 64;
	public final static int SPRITE3D_HEIGHT = 128;
	public final static int SPRITE3D_ANGLES = 60;
	private static BufferedImage[] pieceImages;
	private static BufferedImage[] img3dSets; 
	private static String[] pieceNames = {
		"",
		"pawn_white",
		"knight_white",
		"rook_white",
		"einhorn_white",
		"bishop_white",
		"queen_white",
		"king_white",
		"",
		"pawn_black",
		"knight_black",
		"rook_black",
		"einhorn_black",
		"bishop_black",
		"queen_black",
		"king_black",
	};

	static {
		String filename;

		Piece3d.pieceImages = new BufferedImage [16];
		for (int i = 1; i < 16; i++) {
			if (i != 8) {
				filename = GraphicView.imagePath + "2dpieces/20x20/" + Piece3d.pieceNames[i] + ".png";
				try {
					Piece3d.pieceImages[i] = ImageIO.read (new File (filename));
				} catch (Exception e) {
					System.out.println ("Couldn't load 2D piece image for " + filename);
					System.out.println ("Exception: " + e.getMessage ());
				}
			}
		}

		Piece3d.img3dSets = new BufferedImage [16];
		for (int i = 1; i < 16; i++) {
			if (i != 8) {
				filename = GraphicView.imagePath + "3dpieces/" + Piece3d.pieceNames[i] + ".png";
				try {
					Piece3d.img3dSets[i] = ImageIO.read (new File (filename));
				} catch (Exception e) {
					System.out.println ("Couldn't load 3D piece image for " + filename);
					System.out.println ("Exception: " + e.getMessage ());
				}
			}
		}
	}

	private byte ctype;
	private boolean capturable = false;

	public Piece3d (float z, float x, float y, byte ctype) {
		super (z, x, y);
		this.ctype = ctype;
	}

	public byte getCType () {
		return this.ctype;
	}

	public byte getType () {
		return (byte) (((int) this.ctype) % 8);
	}

	public boolean getColor () {
		return (this.ctype < 8);
	}

	public boolean isCapturable () {
		return this.capturable;
	}

	public void setCapturable () {
		this.capturable = true;
	}

	public void clearCapturable () {
		this.capturable = false;
	}


	@Override
	public BufferedImage getImage2D () {
		return Piece3d.pieceImages[this.ctype];
	}

	@Override
	public BufferedImage get3DSet () {
		return Piece3d.img3dSets[this.ctype];
	}

	@Override
	public int getSpriteAngles () {
		return Piece3d.SPRITE3D_ANGLES;
	}

	@Override
	public int getSpriteWidth () {
		return Piece3d.SPRITE3D_WIDTH;
	}

	@Override
	public int getSpriteHeight () {
		return Piece3d.SPRITE3D_HEIGHT;
	}

}

