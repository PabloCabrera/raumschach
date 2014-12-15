package raumschach.game;
import java.util.ArrayList;

class King extends Piece {
	public King (Board board, boolean color) {
		this.color = color;
		this.type = KING;
		this.board = board;
	}

	@Override
	public ArrayList<int[]> getValidMoves () {
		Piece other;
		int[] pos;
		ArrayList<int[]> moves;

		moves = new ArrayList<int[]> ();

		for (int z = -1; z < 2; z++) {
		for (int x = -1; x < 2; x++) {
		for (int y = -1; y < 2; y++) {
			if (this.board.inBounds (z, x, y)) {
				other = this.board.getPieceAt (z, x, y);
				if (other == null) {
					pos = new int[3];
					pos[0] = z;
					pos[1] = x;
					pos[2] = y;
					moves.add (pos);
				}
			}
		}}}
		return moves;
	}

	@Override
	public ArrayList<int[]> getValidCaptures () {
		Piece other;
		int[] pos;
		ArrayList<int[]> captures;

		captures = new ArrayList<int[]> ();

		for (int z = -1; z < 2; z++) {
		for (int x = -1; x < 2; x++) {
		for (int y = -1; y < 2; y++) {
			if (this.board.inBounds (z, x, y)) {
				other = this.board.getPieceAt (z, x, y);
				if ((other != null)
				 && (other.getColor () != this.color)) {
					pos = new int[3];
					pos[0] = z;
					pos[1] = x;
					pos[2] = y;
					captures.add (pos);
				}
			}
		}}}
		return captures;
	}


}
