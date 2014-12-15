package raumschach.game;
import java.util.ArrayList;

class Knight extends Piece {
	private static int[][] moveMatrix = {
		{0,1,2},
		{0,1,-2},
		{0,-1,2},
		{0,-1,-2},

		{0,2,1},
		{0,2,-1},
		{0,-2,1},
		{0,-2,-1},

		{1,0,2},
		{1,0,-2},
		{-1,0,2},
		{-1,0,-2},

		{1,2,0},
		{1,-2,0},
		{-1,2,0},
		{-1,-2,0},

		{2,0,1},
		{2,0,-1},
		{-2,0,1},
		{-2,0,-1},

		{2,1,0},
		{2,-1,0},
		{-2,1,0},
		{-2,-1,0}
	};

	public Knight (Board board, boolean color) {
		this.color = color;
		this.type = KNIGHT;
		this.board = board;
	}

	@Override
	public ArrayList<int[]> getValidMoves () {
		Piece other;
		int[] currentPosition;
		int[] projectedPosition;
		ArrayList<int[]> moves;

		moves = new ArrayList<int[]> ();
		currentPosition = this.getPosition ();
		projectedPosition = new int[3];

		for (int i = 0; i < Knight.moveMatrix.length; i++) {
			for (int axis = 0; axis < 3; axis++) {
				projectedPosition[axis] = currentPosition[axis] + Knight.moveMatrix[i][axis];
			}
			if (this.board.inBounds (projectedPosition)) {
				other = this.board.getPieceAt (projectedPosition);
				if (other == null) {
					moves.add (projectedPosition.clone());
				}
			}
		}

		return moves;
	}

	@Override
	public ArrayList<int[]> getValidCaptures () {
		Piece other;
		int[] currentPosition;
		int[] projectedPosition;
		ArrayList<int[]> captures;

		captures = new ArrayList<int[]> ();
		currentPosition = this.getPosition ();
		projectedPosition = new int[3];

		for (int i = 0; i < Knight.moveMatrix.length; i++) {
			for (int axis = 0; axis < 3; axis++) {
				projectedPosition[axis] = currentPosition[axis] + Knight.moveMatrix[i][axis];
			}
			if (this.board.inBounds (projectedPosition)) {
				other = this.board.getPieceAt (projectedPosition);
				if ((other != null) && (other.getColor () != this.color)) {
					captures.add (projectedPosition.clone());
				}
			}
		}

		return captures;
	}


}
