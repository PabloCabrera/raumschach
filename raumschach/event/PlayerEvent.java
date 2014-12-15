package raumschach.event;

public class PlayerEvent extends GameEvent {
	private boolean color;

	public PlayerEvent (int eventType, long time, boolean color) throws Exception {
		super(eventType, time);
		this.color = color;
		if (eventType < EVENT_PLAYER_SCOPE) {
			throw new Exception ("Invalid event type for Player Event");
		}
	}

	public boolean getColor () {
		return this.color;
	}
}

