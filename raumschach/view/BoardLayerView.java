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
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				float pieceZ;
				Object3d obj;

				obj = this.board[this.layer][x][y];
				if (obj != null && !obj.isMoving ())
					this.drawObject3d (g, obj);
			}
		}
	}

	public void drawObject3d (Graphics g, Object3d obj) {
		BufferedImage imgt = null;
		float alpha; 
		int destX;
		int destY;
		float[] position;

		position = obj.getPosition ();
		alpha = 1 - Math.abs ((position[0] - (float) this.layer));
		if (alpha > 0.02) {
			imgt = makeImageTranslucent (obj.getImage2D (), alpha);
			if (obj.isMoving ()) {
				float[] previousPos = obj.getPreviousPos ();
				long duration = obj.getDuration ();
				long startTime = obj.getStartTime ();
				long now = (new Date ()).getTime ();
				float completed;

				completed = (((float)(now - startTime)) / (float)duration);
				if (completed > 1) {
					completed = 1;
					obj.update ();
				}
				destX = (int) ((position[1] + (completed * (position[1] - previousPos[1]))) * (float) BoardLayerView.CELL_WIDTH);
				destY = (int) ((4f - (position[2] + (completed * (position[2] - previousPos[2])))) * (float) BoardLayerView.CELL_HEIGHT);
			} else {
				destX = (int) (position[1] * (float) BoardLayerView.CELL_WIDTH);
				destY = (int) ((4 - position[2]) * (float) BoardLayerView.CELL_HEIGHT);
			}
			g.drawImage(imgt,destX, destY, null);
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
