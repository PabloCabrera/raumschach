package raumschach.view;
import java.util.TimerTask;

class PositionUpdater extends TimerTask {
	Object3d obj;

	public PositionUpdater (Object3d obj) {
		this.obj = obj;
	}

	@Override
	public void run () {
		this.obj.update ();
	}
}
