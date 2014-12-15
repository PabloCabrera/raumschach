package raumschach.event;

public class PieceEvent extends PlayerEvent {
	private byte pieceType;
	private int[] piecePosition;

	public PieceEvent (
		int eventType, long time, boolean color,
		byte pieceType, int[] piecePosition
	) throws Exception {
		super (eventType, time, color);
		this.pieceType = pieceType;
		this.piecePosition  = piecePosition;

	if (eventType < EVENT_PIECE_SCOPE) {
		throw new Exception ("Invalid event type for Piece Event");
	}

	}

	public int getPieceType () {
		return this.pieceType;
	}

	public int[] getPiecePosition () {
		return this.piecePosition;
	}

}

