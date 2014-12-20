package raumschach.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.util.Date;
import javax.swing.JPanel;

class Board3dView extends JPanel implements ComponentListener {

	private float rotationZ = 0;
	private Object3d[][][] board;
	private SpriteList spriteList = null;
	private int xoffset = 0;
	private int yoffset = 0;
	private int cubesize = 0;
	private boolean hidden = false;

	public Board3dView (Object3d[][][] board) {
		this.board = board;
		this.spriteList = new SpriteList ();
		this.calculateScale ();
		this.addComponentListener (this);
	}

	@Override
	public void paint (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor (Color.BLACK);
		g2.fillRect (0,0, this.getWidth (), this.getHeight ());
		if (!this.hidden) {
			this.generateSpriteList ();
			this.drawSpriteList (g);
		}
	}

	private void generateSpriteList () {
		long now = (new Date ()).getTime ();
		Object3d obj;

		this.spriteList.clear ();
		for (int y = 4; y > -1; y--) {
			for (int x = 0; x < 5; x++) {
				for (int z = 0; z < 5; z++) {
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
		float[] projected = new float[2];
		float[] vanishing = {2.5f, 2.5f};
		float flux = 0.4f;
		Sprite sprite = null;
		float scale;

		position = obj.getActualPosition (now);
		scale = (1/(1f+(position[2]*flux)));

		projected[0] = ((position[1]/2f)-1f) * scale;
		projected[1] = -((position[0]/2f)-1f) * scale;

		sprite = new Sprite (obj.getScaledImage ((int) (scale*cubesize/2), 0), (projected[0]), (projected[1]));

		return sprite;
	}

	private void drawSpriteList (Graphics g) {
		Sprite sprite;

		for (int i = 0; i < spriteList.size(); i++) {
			sprite = spriteList.get (i);
			g.drawImage (sprite.getImage(),
				(int) ((sprite.getX () * this.cubesize) + this.xoffset - sprite.getWidth ()/2),
				(int) ((sprite.getY () * this.cubesize) + this.yoffset - sprite.getHeight ()/2),
				null);
		}
	}

	private float[] rotate (float[] point, float[] pivot, double angle) {
		return null;
	}

	private void calculateScale () {
		int width = this.getWidth ();
		int height = this.getHeight ();
		int max;

		max = (width > height)? height: width;
		this.cubesize = (int) (max * 0.4f);
		this.xoffset = width / 2;
		this.yoffset = height / 2;
	}

	@Override
	public void componentHidden (ComponentEvent e) {
		this.hidden = true;
	}

	@Override
	public void componentMoved (ComponentEvent e) {
		/* Do nothing! */
	}

	@Override
	public void componentResized (ComponentEvent e) {
		this.calculateScale ();
	}

	@Override
	public void componentShown (ComponentEvent e) {
		this.hidden = false;
	}
}
