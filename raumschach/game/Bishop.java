package raumschach.game;
import java.util.ArrayList;

class Bishop extends Piece {
	public Bishop (Board board, boolean color) {
		this.color = color;
		this.type = BISHOP;
		this.board = board;
	}

	@Override
	public ArrayList<int[]> getValidMoves () {
		Projector proj;

		proj = new Projector (this.getPosition (), this.color, this.board);
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

		return proj.getMoves ();
	}

	@Override
	public ArrayList<int[]> getValidCaptures () {
		Projector proj;

		proj = new Projector (this.getPosition (), this.color, this.board);
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

		return proj.getOpponentCollisions ();
	}


}
