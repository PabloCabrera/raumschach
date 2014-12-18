package raumschach.event;

public class MoveEvent extends PieceEvent {
	protected int[] destination;

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

	public String getDestinationAsString () {
		char z, x, y;

		z = (char) (this.destination[0] + (int) 'A');
		x = (char) (this.destination[1] + (int) 'a');
		y = (char) (this.destination[2] + (int) '1');

		return "" + z + x + y;
	
	}


}

