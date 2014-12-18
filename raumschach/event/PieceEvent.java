package raumschach.event;
import raumschach.game.Piece;

public class PieceEvent extends PlayerEvent {
	protected byte pieceType;
	protected int[] piecePosition;

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

	public byte getPieceType () {
		return this.pieceType;
	}

	public byte getPieceCType () {
		if (this.color == Piece.WHITE) {
			return this.pieceType;
		} else {
			return (byte) (this.pieceType + Piece.BLACK_START);
		}
	}


	public int[] getPiecePosition () {
		return this.piecePosition;
	}

	public String getPiecePositionAsString () {
		char z, x, y;

		z = (char) (this.piecePosition[0] + (int) 'A');
		x = (char) (this.piecePosition[1] + (int) 'a');
		y = (char) (this.piecePosition[2] + (int) '1');

		return "" + z + x + y;
	
	}

}

