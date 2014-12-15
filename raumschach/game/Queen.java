package raumschach.game;
import java.util.ArrayList;

class Queen extends Piece {
	public Queen (Board board, boolean color) {
		this.color = color;
		this.type = QUEEN;
		this.board = board;
	}

	@Override
	public ArrayList<int[]> getValidMoves () {
		Projector proj;

		proj = new Projector (this.getPosition (), this.color, this.board);

		/* Like Rook */
		proj.addDirection (0,0,1);
		proj.addDirection (0,0,-1);
		proj.addDirection (0,1,0);
		proj.addDirection (0,-1,0);
		proj.addDirection (1,0,0);
		proj.addDirection (-1,0,0);

		/* Like Bishop */
		proj.addDirection (0,1,1);
		proj.addDirection (0,1,-1);
		proj.addDirection (0,-1,1);
		proj.addDirection (0,-1,-1);

		proj.addDirection (1,1,0);
		proj.addDirection (1,-1,0);
		proj.addDirection (-1,1,0);
		proj.addDirection (-1,-1,0);

		proj.addDirection (1,0,1);
		proj.addDirection (1,0,-1);
		proj.addDirection (-1,0,1);
		proj.addDirection (-1,0,-1);

		/* Like Einhorn */
		proj.addDirection (1,1,1);
		proj.addDirection (1,1,-1);
		proj.addDirection (1,-1,1);
		proj.addDirection (1,-1,-1);

		proj.addDirection (-1,1,1);
		proj.addDirection (-1,1,-1);
		proj.addDirection (-1,-1,1);
		proj.addDirection (-1,-1,-1);

		return proj.getMoves ();
	}

	@Override
	public ArrayList<int[]> getValidCaptures () {
		Projector proj;

		proj = new Projector (this.getPosition (), this.color, this.board);

		/* Like Rook */
		proj.addDirection (0,0,1);
		proj.addDirection (0,0,-1);
		proj.addDirection (0,1,0);
		proj.addDirection (0,-1,0);
		proj.addDirection (1,0,0);
		proj.addDirection (-1,0,0);

		/* Like Bishop */
		proj.addDirection (0,1,1);
		proj.addDirection (0,1,-1);
		proj.addDirection (0,-1,1);
		proj.addDirection (0,-1,-1);

		proj.addDirection (1,1,0);
		proj.addDirection (1,-1,0);
		proj.addDirection (-1,1,0);
		proj.addDirection (-1,-1,0);

		proj.addDirection (1,0,1);
		proj.addDirection (1,0,-1);
		proj.addDirection (-1,0,1);
		proj.addDirection (-1,0,-1);

		/* Like Einhorn */
		proj.addDirection (1,1,1);
		proj.addDirection (1,1,-1);
		proj.addDirection (1,-1,1);
		proj.addDirection (1,-1,-1);

		proj.addDirection (-1,1,1);
		proj.addDirection (-1,1,-1);
		proj.addDirection (-1,-1,1);
		proj.addDirection (-1,-1,-1);

		return proj.getOpponentCollisions ();
	}


}
