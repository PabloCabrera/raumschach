package raumschach.view;
import raumschach.event.GameEvent;
import java.util.Date;
import java.util.Timer;
import java.awt.image.BufferedImage;
import java.awt.Image;

class Object3d {
	private float[] position = null;
	private boolean moving = false;
	private float[] previousPos = null;
	private long startTime = 0;
	private long duration = 0;
	private Timer timer;

	public Object3d (float z, float x, float y) {
		this.position = new float [3];
		this.position[0] = z;
		this.position[1] = x;
		this.position[2] = y;
		this.timer = new Timer ();
	}

	public void move (float z, float x, float y, long duration) {
		Date now = new Date ();

		this.update ();
		this.moving = true;
		this.previousPos = this.position;
		this.position = new float [3];
		this.position[0] = z;
		this.position[1] = x;
		this.position[2] = y;
		this.startTime = now.getTime ();
		this.duration = duration;
		this.timer.schedule (new PositionUpdater (this), duration+1);
	}

	public void update () {
		Date now = new Date ();
		if (this.moving && (this.startTime + this.duration < now.getTime ())) {
			this.previousPos = null;
			this.moving = false;
			this.startTime = 0;
			this.duration = 0;
		}
	}

	public float[] getPosition () {
		return this.position;
	}

	public float[] getActualPosition (long time) {
		if (this.moving) {
			if (time <= this.startTime) {
				return this.previousPos;
			} else if (time >= this.startTime + this.duration) {
				this.update ();
				return this.position;
			} else {
				float[] actual = new float[3];
				float completed = ((float) (time - this.startTime)) / ((float) this.duration);
				for (int i = 0; i < 3; i++) {
					actual[i] = this.previousPos[i] + completed * (this.position[i] - this.previousPos[i]);
				}
			return actual;
			} 
		} else {
			return this.position;
		}
	}

	public boolean isMoving () {
		return this.moving;
	}

	public float[] getPreviousPos () {
		return this.previousPos;
	}

	public long getStartTime () {
		return this.startTime;
	}

	public long getDuration () {
		return this.duration;
	}

	public BufferedImage getImage2D () {
		/* MUST BE OVERRIDED! */
		return null;
	}

	public BufferedImage get3DSet () {
		/* MUST BE OVERRIDED! */
		return null;
	}

	public int getSpriteAngles () {
		/* MUST BE OVERRIDED! */
		return 0;
	}

	public int getSpriteWidth () {
		/* MUST BE OVERRIDED! */
		return 0;
	}

	public int getSpriteHeight () {
		/* MUST BE OVERRIDED! */
		return 0;
	}

	public Image getScaledImage (int height, double angle) {
		BufferedImage tmp;
		BufferedImage scaled = null;
		int index;
		int offset;

		while (angle < 0) {
			angle += (2 * Math.PI);
		}

		while (angle > 2 * Math.PI) {
			angle -= (2 * Math.PI);
		}

		index = (int) (((double)(angle * this.getSpriteAngles ())) / Math.PI);
		offset = this.getSpriteWidth () * index;
		tmp = this.get3DSet().getSubimage (offset, 0, this.getSpriteWidth (), this.getSpriteHeight());

		return tmp.getScaledInstance ((Piece3d.SPRITE3D_WIDTH*height)/Piece3d.SPRITE3D_HEIGHT, height, Image.SCALE_FAST);
	}



}

