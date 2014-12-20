package raumschach.event;

public class PlayerEvent extends GameEvent {
	protected boolean color;

	public PlayerEvent (int eventType, long time, boolean color) {
		super(eventType, time);
		this.color = color;
		if (eventType < EVENT_PLAYER_SCOPE) {
			this.eventType = EVENT_INVALID;
		}
	}

	public boolean getColor () {
		return this.color;
	}
}

