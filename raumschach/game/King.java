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
		int[] currentPosition;

		moves = new ArrayList<int[]> ();
		currentPosition = this.getPosition ();

		for (int z = -1; z < 2; z++) {
		for (int x = -1; x < 2; x++) {
		for (int y = -1; y < 2; y++) {
			pos = new int[3];
			pos[0] = z + currentPosition[0];
			pos[1] = x + currentPosition[1];
			pos[2] = y + currentPosition[2];
			if (this.board.inBounds (pos)) {
				other = this.board.getPieceAt (pos);
				if (other == null) {
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
		int[] currentPosition;
		ArrayList<int[]> captures;

		captures = new ArrayList<int[]> ();
		currentPosition = this.getPosition ();

		for (int z = -1; z < 2; z++) {
		for (int x = -1; x < 2; x++) {
		for (int y = -1; y < 2; y++) {
				pos = new int[3];
				pos[0] = z + currentPosition [0];
				pos[1] = x + currentPosition [1];
				pos[2] = y + currentPosition [2];
				if (this.board.inBounds (pos)) {
				other = this.board.getPieceAt (pos);
				if ((other != null)
				 && (other.getColor () != this.color)) {
				captures.add (pos);
				}
			}
		}}}
		return captures;
	}


}
