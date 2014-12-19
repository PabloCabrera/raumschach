package raumschach.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Date;
import javax.swing.JPanel;

class Board3dView extends JPanel {

	private float rotationZ = 0;
	private Object3d[][][] board;
	private SpriteList spriteList = null;

	public Board3dView (Object3d[][][] board) {
		this.board = board;
		this.spriteList = new SpriteList ();
	}

	@Override
	public void paint (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor (Color.BLACK);
		g2.fillRect (0,0, this.getWidth (), this.getHeight ());
		this.generateSpriteList ();
		this.drawSpriteList (g);
	}

	private void generateSpriteList () {
		long now = (new Date ()).getTime ();
		Object3d obj;

		this.spriteList.clear ();
		for (int z = 0; z < 5; z++) {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					obj = this.board[z][x][y];
					if (obj != null) {
						this.spriteList.add (this.generateSprite (obj, now));
					}
				}
			}
		}
	}

	private Sprite generateSprite (Object3d obj, long now) {
		float[] position;
		float[] projected;
		float[] vanishing = {2.5f, 2.5f};
		Sprite sprite = null;
		int scale;

		position = obj.getActualPosition (now);
		scale = (int) (100f/(1f+position[2]));

		//projected =  this.projectPerspective (obj.getActualPosition (), vanishing);

		sprite = new Sprite (obj.getScaledImage (scale, 0), (int) (position[1] * 100f),(int) (400f - (position[0] * 100f)));

		return sprite;
	}

	private void drawSpriteList (Graphics g) {
		Sprite sprite;

		for (int i = 0; i < spriteList.size(); i++) {
			sprite = spriteList.get (i);
			g.drawImage (sprite.getImage(), sprite.getX (), sprite.getY (), null);
		}
	}

	private float[] projectPerspective (float[] point, float[] vanishing){
		return null;
	}

	private float[] rotate (float[] point, float[] pivot, double angle) {
		return null;
	}

}
