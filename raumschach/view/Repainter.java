package raumschach.view;
import java.lang.Thread;
import java.util.Date;

class Repainter extends Thread {
	private BoardLayerView[] planes;
	private Board3dView proj3d;
	private long lastIteration = 0;
	private long minWait = 40;
	private boolean stop = false;

	public Repainter (BoardLayerView[] planes, Board3dView proj3d) {
		this.planes = planes;
		this.proj3d = proj3d;
	}

	@Override
	public void run () {
		long now;

		while (!stop) {
			now = (new Date ()).getTime ();
			if (now - this.lastIteration < this.minWait) {
				try {
					this.sleep (this.minWait - (now - this.lastIteration));
				} catch (Exception e) {}
			}
			for (int i=0; i < this.planes.length; i++) {
				this.planes[i].repaint ();
			}
			this.proj3d.repaint ();
		}
	}
}
