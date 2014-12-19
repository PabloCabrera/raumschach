package raumschach.view;
import java.lang.Thread;
import java.util.Date;

class Repainter extends Thread {
	private BoardLayerView[] planes;
	private long lastIteration = 0;
	private long minWait = 40;
	private boolean stop = false;

	public Repainter (BoardLayerView[] planes) {
		this.planes = planes;
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
				planes[i].repaint();
			}
		}
	}
}
