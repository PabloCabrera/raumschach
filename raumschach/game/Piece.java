package raumschach.game;
import java.util.Arrays;
import java.util.ArrayList;

public abstract class Piece {
	public final static byte NOTHING = 0;
	public final static byte PAWN = 1;
	public final static byte KNIGHT = 2;
	public final static byte ROOK = 3;
	public final static byte EINHORN = 4;
	public final static byte BISHOP = 5;
	public final static byte QUEEN = 6;
	public final static byte KING = 7;

	public final static byte WHITE_PAWN = 1;
	public final static byte WHITE_KNIGHT = 2;
	public final static byte WHITE_ROOK = 3;
	public final static byte WHITE_EINHORN = 4;
	public final static byte WHITE_BISHOP = 5;
	public final static byte WHITE_QUEEN = 6;
	public final static byte WHITE_KING = 7;

	public final static byte BLACK_START = 8;
	public final static byte BLACK_PAWN = 9;
	public final static byte BLACK_KNIGHT = 10;
	public final static byte BLACK_ROOK = 11;
	public final static byte BLACK_EINHORN = 12;
	public final static byte BLACK_BISHOP = 13;
	public final static byte BLACK_QUEEN = 14;
	public final static byte BLACK_KING = 15;

	public final static boolean WHITE = true;
	public final static boolean BLACK = false;

	protected boolean color;
	protected byte type;
	protected Board board;

	abstract ArrayList<int[]> getValidMoves ();
	abstract ArrayList<int[]> getValidCaptures ();

	public boolean isValidMove (int z, int x, int y) {
		int[] position;
		ArrayList<int[]> validMoves;

		position = new int[3];
		position[0] = z;
		position[1] = x;
		position[2] = y;
		validMoves = this.getValidMoves ();
		for (int index = 0; index < validMoves.size (); index++) {
			if (Arrays.equals (validMoves.get (index), position)) {
				return true;
			}
		}
		return false;
	}

	public boolean isValidCapture (int z, int x, int y) {
		int[] position;
		ArrayList<int[]> validCaptures;

		position = new int[3];
		position[0] = z;
		position[1] = x;
		position[2] = y;
		validCaptures = this.getValidCaptures ();
		for (int index = 0; index < validCaptures.size (); index++) {
			if (Arrays.equals (validCaptures.get(index), position)) {
				return true;
			}
		}
		return false;
	}

	public int[] getPosition () {
		return this.board.getPiecePosition (this);
	}

	public Board getBoard () {
		return this.board;
	}

	public boolean getColor () {
		return this.color;
	}

	public byte getType () {
		return this.type;
	}

	public byte getCtype () {
		if (this.color == WHITE)
			return this.type;
		else
			return (byte)(this.type + BLACK_START);
	}

	public static Piece create (byte type, Board board) {
		Piece piece;
		byte ctype;
		boolean color;

		ctype = (byte) (type % BLACK_START);
		color = type < BLACK_START;

		switch (ctype) {
			case PAWN:
				return new Pawn (board, color);
			case KNIGHT:
				return new Knight (board, color);
			case ROOK:
				return new Rook (board, color);
			case EINHORN:
				return new Einhorn (board, color);
			case BISHOP:
				return new Bishop (board, color);
			case QUEEN:
				return new Queen (board, color);
			case KING:
				return new King (board, color);
			default:
				return null;
		}
	}

}

