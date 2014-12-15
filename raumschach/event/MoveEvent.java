package raumschach.event;

public class MoveEvent extends PieceEvent {
	private int[] destination;

	public MoveEvent (
		int eventType, long time, boolean color,
		byte pieceType, int[] origin,
		int[] destination
	) throws Exception {
		super (eventType, time, color, pieceType, origin);
		this.destination = destination;

		if (eventType < MOVE) {
			throw new Exception ("Invalid event type for Move Event");
		}
	}

	public int[] getDestination () {
		return this.destination;
	}
}

