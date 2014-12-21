package raumschach.game;
import java.util.ArrayList;

class Pawn extends Piece {
	private static int[][] captureMatrix = {
		{0, -1, 1},
		{0, 1, 1},
		{1, -1, 0},
		{1, 1, 0},
		{1, 0, 1},
	};

	public Pawn (Board board, boolean color) {
		this.color = color;
		this.type = PAWN;
		this.board = board;
	}

	@Override
	public ArrayList<int[]> getValidMoves () {
		int direction;
		int[] pos;
		int[] proj_pos;
		Piece other;
		ArrayList<int[]> moves;

		direction = (this.color == WHITE)? 1: -1;
		pos = this.getPosition ();
		moves = new ArrayList<int[]> ();

		for (int axis = 0; axis < 3; axis++) {
			if (axis != 1) {
				proj_pos = pos.clone ();
				proj_pos[axis] += direction;
				if (this.board.inBounds (proj_pos)) {
					other = this.board.getPieceAt (proj_pos);
					if (other == null) {
						moves.add (proj_pos);
					}
				}
			}
		}

		return moves;
	}

	@Override
	public ArrayList<int[]> getValidCaptures () {
		int direction;
		int[] pos;
		int[] proj_pos;
		Piece other;
		ArrayList<int[]> captures;

		direction = (this.color == WHITE)? 1: -1;
		pos = this.getPosition ();
		captures = new ArrayList<int[]> ();

		for (int index = 0; index < 2; index++) {
			proj_pos = new int[3];
			proj_pos[0] = captureMatrix[index][0]*direction;
			proj_pos[1] = captureMatrix[index][1]; //X axis
			proj_pos[2] = captureMatrix[index][2]*direction;

			if (this.board.inBounds (proj_pos)) {
				other = this.board.getPieceAt (proj_pos);
				if ((other != null)
				 && (other.getColor ()) != this.color ) {
					captures.add (proj_pos);
				}
			}
		}

	return captures;
	}


}
