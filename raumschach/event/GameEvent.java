package raumschach.event;

public class GameEvent {

	/* Game scope */
	public final static int START = 1;
	public final static int PAUSE = 2;
	public final static int CONTINUE = 3;
	public final static int DRAW = 4;

	/* Player scope */
	public final static int EVENT_PLAYER_SCOPE = 16;
	public final static int CHECKMATE = 17;
	public final static int RESIGN = 18;
	public final static int TIMEOUT = 19;
	public final static int STALE = 20;
	public final static int CHECK = 21;
	public final static int TURN = 22;
	public final static int CLOCK = 23;

	/* Piece scope */
	public final static int EVENT_PIECE_SCOPE = 32;
	public final static int APPEAR = 33;
	public final static int DISSAPPEAR = 34;
	public final static int MOVE = 35;
	public final static int CAPTURE = 36;
	public final static int PROMOTION = 37;

	/* Invalid event (should be ignored) */
	public final static int EVENT_INVALID = -1;


	/* Members */
	protected int eventType;
	protected long time;

	public GameEvent (int eventType, long time) {
		this.eventType = eventType;
		this.time = time;
	}

	public int getEventType () {
		return this.eventType;
	}

	public long getTime () {
		return this.time;
	}
}

