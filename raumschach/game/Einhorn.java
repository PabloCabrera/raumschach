package raumschach.game;
import java.util.ArrayList;

class Einhorn extends Piece {
	public Einhorn (Board board, boolean color) {
		this.color = color;
		this.type = EINHORN;
		this.board = board;
	}

	@Override
	public ArrayList<int[]> getValidMoves () {
		Projector proj;
		proj = new Projector (this.getPosition (), this.color, this.board);

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
