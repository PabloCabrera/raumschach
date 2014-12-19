package raumschach.view;
import raumschach.event.GameEvent;
import java.util.Date;
import java.util.Timer;
import java.awt.image.BufferedImage;

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
		/* WRITE! */
		return null;
	}
}

