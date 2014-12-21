package raumschach.view;

import java.io.File;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.geom.Line2D;
import java.util.Date;
import javax.swing.JPanel;

class Board3dView extends JPanel implements ComponentListener {

	private Object3d[][][] board;
	private SpriteList spriteList = null;
	private int xcenter = 0;
	private int ycenter = 0;
	private int xoffset = 0;
	private int yoffset = 0;
	private int cubemed = 0;
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
		float flux = 0.3f;

		g2.setColor (Color.BLACK);
		g2.fillRect (0,0, this.getWidth (), this.getHeight ());
		if (!this.hidden) {
			this.drawBackLines (g2, 5, flux);
			this.drawBorderLines (g2, flux);
			this.generateSpriteList (flux);
			this.drawSpriteList (g);
		}
	}

	private void generateSpriteList (float flux) {
		long now = (new Date ()).getTime ();
		Object3d obj;

		this.spriteList.clear ();
		for (int y = 4; y > -1; y--) {
			for (int x = 0; x < 5; x++) {
				for (int z = 0; z < 5; z++) {
					obj = this.board[z][x][y];
					if (obj != null) {
						this.spriteList.add (this.generateSprite (obj, now, flux));
					}
				}
			}
		}
	}

	private Sprite generateSprite (Object3d obj, long now, float flux) {
		float[] position;
		float[] projected = new float[2];
		float[] vanishing = {2.5f, 2.5f};
		Sprite sprite = null;
		float scale;

		position = obj.getActualPosition (now);
		scale = (1/(1f+((position[2]+0.25f)*flux)));

		projected[0] = ((position[1]/2f)-1f) * scale;
		projected[1] = -((position[0]/2f)-0.9f) * scale;

		sprite = new Sprite (obj.getScaledImage ((int) (scale*cubemed/2), 0), (projected[0]), (projected[1]));

		return sprite;
	}

	private void drawSpriteList (Graphics g) {
		Sprite sprite;

		for (int i = 0; i < spriteList.size(); i++) {
			sprite = spriteList.get (i);
			g.drawImage (sprite.getImage(),
				(int) ((sprite.getX () * this.cubemed) + this.xcenter - sprite.getWidth ()/2),
				(int) ((sprite.getY () * this.cubemed) + this.ycenter - sprite.getHeight ()/2),
				null);
		}
	}

	private void drawBackLines (Graphics2D g2, int layer, float flux) {
		float p1, p2; //coordinates
		float jx1, jy1, jx2, jy2; //projected
		Line2D.Float line;
		float scale = (1/(1f+(layer*flux)));
		
		g2.setColor (Color.lightGray);

		for (p1 = -1.25f; p1 < 1.26f; p1 += 0.5f) {
			for (p2 = -1.25f; p2 < 1.26f; p2 += 0.5f) {
				jx1 = (p1 * this.cubemed)*scale;
				jy1 = (p2 * this.cubemed)*scale;
				jx2 = (p2 * this.cubemed)*scale;
				jy2 = (p2 * this.cubemed)*scale;
				line = new Line2D.Float (jx1 + this.xcenter,
					jy1 + this.ycenter,
					jx2 + this.xcenter,
					jy2 + this.ycenter);
				g2.draw (line);
				line = new Line2D.Float (jy1 + this.xcenter,
					jx1 + this.ycenter,
					jy2 + this.xcenter,
					jx2 + this.ycenter);
				g2.draw (line);
			}
		}
	}

	private void drawBorderLines (Graphics2D g2, float flux) {
		float p1, p2;
		float jx1, jy1, jx2, jy2;
		float z;
		float scale;
		Line2D.Float line;

		g2.setColor (Color.lightGray);

		/* Horizontal lines */
		for (z = 0f; z < 5.01f; z += 1f) {
			scale = (1/(1f+(z*flux)));
			for (p1 = -1.25f; p1 < 1.26f; p1 += 0.5f) {
				for (int layer = 0; layer <= 5; layer+= 5) {
					p2 = -1.25f + ((float)layer /2f) ;
					jx1 = (p1 * this.cubemed)*scale;
					jy1 = (p2 * this.cubemed)*scale;
					jx2 = (p2 * this.cubemed)*scale;
					jy2 = (p2 * this.cubemed)*scale;
					line = new Line2D.Float (jx1 + this.xcenter,
						jy1 + this.ycenter,
						jx2 + this.xcenter,
						jy2 + this.ycenter);
					g2.draw (line);
					line = new Line2D.Float (jy1 + this.xcenter,
						jx1 + this.ycenter,
						jy2 + this.xcenter,
						jx2 + this.ycenter);
					g2.draw (line);
				}
			}
		}

		/* Vertical lines */
		for (p1 = -1.25f; p1 < 1.26f; p1 += 0.5f) {
			for (int mul = -1; mul < 3; mul += 2)  {
				z = 0;
				scale = (1/(1f+(z*flux)));
				jx1 = (p1 * this.cubemed)*scale;
				jy1 = mul*((-1.25f + (z*0.5f)) * this.cubemed)*scale;

				z = 5;
				scale = (1/(1f+(z*flux)));
				jx2 = (p1 * this.cubemed)*scale;
				jy2 = mul*((-z*0.25f) * this.cubemed)*scale;

				line = new Line2D.Float (jx1 + this.xcenter,
					jy1 + this.ycenter,
					jx2 + this.xcenter,
					jy2 + this.ycenter);
				g2.draw (line);

				line = new Line2D.Float (jy1 + this.xcenter,
					jx1 + this.ycenter,
					jy2 + this.xcenter,
					jx2 + this.ycenter);
				g2.draw (line);
			}
		}
	}


	private void calculateScale () {
		int width = this.getWidth ();
		int height = this.getHeight ();
		int max;

		max = (width > height)? height: width;
		this.cubemed = (int) (max * 0.4f);
		this.cubesize = max;
		this.xcenter = width / 2;
		this.ycenter = height / 2;
		this.xoffset = (cubesize - width)/2;
		this.yoffset = (cubesize - height)/2;
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
		this.calculateScale ();
	}
}
