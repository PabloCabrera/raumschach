package raumschach.view;

import java.io.File;
import java.lang.Math;
import java.util.Date;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

class BoardLayerView extends JPanel {
	private static BufferedImage[] imgBoard;
	private static int CELL_WIDTH = 20;
	private static int CELL_HEIGHT = 20;

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
	}

	Object3d[][][] board;
	int layer;

	public BoardLayerView (Object3d[][][] board, int layer) {
		this.board = board;
		this.layer = layer;
	}

	@Override
	public void paint (Graphics g) {
		g.drawImage (BoardLayerView.imgBoard[this.layer], 0, 0, null);
		/*  */
		this.drawPieces (g); 
	}

	public void drawPieces (Graphics g) {
		Object3d obj;

		for (int z = 0; z < 5; z++) {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					obj = this.board[z][x][y];
					if (obj != null) {
						if (obj.isMoving ()) {
							this.drawMoving (g, obj);
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
	}

	public void drawMoving (Graphics g, Object3d obj) {
		float currentX;
		float currentY;
		float currentZ;
		float[] origin;
		float[] destination;
		long startTime;
		long duration;
		float completed;
		long now;
		float zDiff;

		destination = obj.getPosition ();
		origin = obj.getPreviousPos ();
		startTime = obj.getStartTime ();
		duration = obj.getDuration ();
		now = (new Date ()).getTime ();
		completed = ((float) (now - startTime)) / ((float) duration);
		if (completed > 1f) {
			obj.update ();
			completed = 1f;
		}
		currentZ = origin[0] + completed * (destination[0] - origin[0]);
		zDiff = Math.abs (currentZ - (float) this.layer);
		if (zDiff < 0.96) {
			int destX;
			int destY;
			BufferedImage imgt;

			imgt = this.makeImageTranslucent (obj.getImage2D (), 1-zDiff);
			currentX = origin[1] + completed * (destination[1] - origin[1]);
			currentY = origin[2] + completed * (destination[2] - origin[2]);
			destX = (int) (currentX * (float) BoardLayerView.CELL_WIDTH);
			destY = (int) ((4 - currentY) * (float) BoardLayerView.CELL_HEIGHT);
			g.drawImage(imgt, destX, destY, null);
		}

	}

	public void drawIndicators () {
	}

/* http://www.java2s.com/Code/Java/2D-Graphics-GUI/MakeimageTransparency.htm */
  public static BufferedImage makeImageTranslucent(BufferedImage source,
      float alpha) {
    BufferedImage target = new BufferedImage(source.getWidth(),
        source.getHeight(), java.awt.Transparency.TRANSLUCENT);
    // Get the images graphics
    Graphics2D g = target.createGraphics();
    // Set the Graphics composite to Alpha
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
        alpha));
    // Draw the image into the prepared reciver image
    g.drawImage(source, null, 0, 0);
    // let go of all system resources in this Graphics
    g.dispose();
    // Return the image
    return target;
  }

}
