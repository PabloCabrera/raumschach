package raumschach.view;

import java.io.File;
import java.lang.Math;
import java.util.Date;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class BoardLayerView extends JPanel {
	private static BufferedImage[] imgBoard;
	private static int CELL_WIDTH = 20;
	private static int CELL_HEIGHT = 20;
	private static BufferedImage imgCapturable;

	static {
		try {
			BoardLayerView.imgBoard = new BufferedImage[5];
			BoardLayerView.imgBoard[0] = ImageIO.read (new File (GraphicView.imagePath + "board1.png"));
			BoardLayerView.imgBoard[1] = ImageIO.read (new File (GraphicView.imagePath + "board2.png"));
			BoardLayerView.imgBoard[2] = BoardLayerView.imgBoard[0];
			BoardLayerView.imgBoard[3] = BoardLayerView.imgBoard[1];
			BoardLayerView.imgBoard[4] = BoardLayerView.imgBoard[0];
		} catch (Exception e) {
			System.out.println ("Couldn't load board background image");
			System.out.println ("Exception: " + e.getMessage ());
			for (int i = 0; i < 5; i++) {
				BoardLayerView.imgBoard[i] = null;
			}
		}

		try {
			BoardLayerView.imgCapturable = ImageIO.read (new File (GraphicView.imagePath + "capturable.png"));
		} catch (Exception e) {
			System.out.println ("Couldn't load capturable image");
			System.out.println ("Exception: " + e.getMessage ());
		}

	}

	Object3d[][][] board;
	int layer;

	public BoardLayerView (Object3d[][][] board, int layer) {
		this.board = board;
		this.layer = layer;
		this.setPreferredSize (new Dimension (100, 100));
	}

	@Override
	public void paint (Graphics g) {
		g.drawImage (BoardLayerView.imgBoard[this.layer], 0, 0, null);
		/*  */
		this.drawPieces (g); 
	}

	public void drawPieces (Graphics g) {
		Object3d obj;
		long now = (new Date ()).getTime ();

		for (int z = 0; z < 5; z++) {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					obj = this.board[z][x][y];
					if (obj != null) {
						if (obj.isMoving ()) {
							this.drawMoving (g, obj, now);
						} else if (z == this.layer) {
							this.drawStatic (g, obj);
						}
					}
				}
			}
		}
	}

	public void drawStatic (Graphics g, Object3d obj) {
		int destX;
		int destY;
		float[] position;

		position = obj.getPosition ();
		destX = (int) (position[1] * (float) BoardLayerView.CELL_WIDTH);
		destY = (int) ((4 - position[2]) * (float) BoardLayerView.CELL_HEIGHT);
		g.drawImage(obj.getImage2D (),destX, destY, null);
		if ((obj instanceof Piece3d) && ((Piece3d) obj).isCapturable ()) {
			g.drawImage (BoardLayerView.imgCapturable, destX, destY, null);
		}
	}

	public void drawMoving (Graphics g, Object3d obj, long now) {
		float[] actualPos;
		float zDiff;

		actualPos = obj.getActualPosition (now);
		zDiff = Math.abs (actualPos[0] - (float) this.layer);
		if (zDiff < 0.96) {
			int destX;
			int destY;
			BufferedImage imgt;

			imgt = this.makeImageTranslucent (obj.getImage2D (), 1-zDiff);
			destX = (int) (actualPos[1] * (float) BoardLayerView.CELL_WIDTH);
			destY = (int) ((4 - actualPos[2]) * (float) BoardLayerView.CELL_HEIGHT);
			g.drawImage(imgt, destX, destY, null);
		}

	}

	public void drawIndicators () {
	}

/* http://www.java2s.com/Code/Java/2D-Graphics-GUI/MakeimageTransparency.htm */
	public static BufferedImage makeImageTranslucent(BufferedImage source, float alpha) {
		BufferedImage target = new BufferedImage(source.getWidth(),
			source.getHeight(), java.awt.Transparency.TRANSLUCENT);
		// Get the images graphics
		Graphics2D g = target.createGraphics();
		// Set the Graphics composite to Alpha
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		// Draw the image into the prepared reciver image
		g.drawImage(source, null, 0, 0);
		// let go of all system resources in this Graphics
		g.dispose();
		// Return the image
		return target;
	}

}
