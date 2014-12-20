package raumschach.event;

public class ClockEvent extends PlayerEvent {
	protected long timeleft;
	protected boolean running;

	public ClockEvent (
		int eventType, long time, boolean color,
		long timeleft, boolean running
	) {
		super (eventType, time, color);
		this.timeleft = timeleft;
		this.running = running;

		if (eventType != CLOCK) {
			this.eventType = EVENT_INVALID;
		}
	}

	public long getTimeleft () {
		return this.timeleft;
	}

	public boolean getRunning () {
		return this.running;
	}
}

