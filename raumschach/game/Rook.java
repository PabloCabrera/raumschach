package raumschach.game;
import java.util.ArrayList;

class Rook extends Piece {
	public Rook (Board board, boolean color) {
		this.color = color;
		this.type = ROOK;
		this.board = board;
	}

	@Override
	public ArrayList<int[]> getValidMoves () {
		Projector proj;

		proj = new Projector (this.getPosition (), this.color, this.board);
		proj.addDirection (0,0,1); // FRONT
		proj.addDirection (0,0,-1); // BACK
		proj.addDirection (0,1,0); // RIGHT
		proj.addDirection (0,-1,0); // LEFT
		proj.addDirection (1,0,0); // UP
		proj.addDirection (-1,0,0); // DOWN

		return proj.getMoves ();
	}

	@Override
	public ArrayList<int[]> getValidCaptures () {
		Projector proj;

		proj = new Projector (this.getPosition (), this.color, this.board);
		proj.addDirection (0,0,1); // FRONT
		proj.addDirection (0,0,-1); // BACK
		proj.addDirection (0,1,0); // RIGHT
		proj.addDirection (0,-1,0); // LEFT
		proj.addDirection (1,0,0); // UP
		proj.addDirection (-1,0,0); // DOWN

		return proj.getOpponentCollisions ();
	}


}
