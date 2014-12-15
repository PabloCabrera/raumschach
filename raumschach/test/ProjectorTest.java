package raumschach.test;
import raumschach.game.Board;
import raumschach.game.Piece;
import raumschach.game.Projector;

class ProjectorTest extends Test {
	private Board board;

	public static void main (String args[]) {
		ProjectorTest ptest;
		ptest = new ProjectorTest ();
	}

	public ProjectorTest () {
		this.simpleTest ();
		this.collisionTest ();
	}

	public void simpleTest () {
		Projector projector;
		int[] origin;

		testMsg ("Simple Test");
		this.clearBoard ();

		assert (this.board.insertPiece (Piece.BLACK_PAWN, 1,1,1));
		origin = new int[3];
		origin[0] = 1;
		origin[1] = 1;
		origin[2] = 1;
		projector = new Projector (origin, Piece.BLACK, this.board);

		projector.addDirection (1,0,0); // UP
		projector.project ();
		assert (projector.getMoves().size() == 3);
		assert (projector.getTeamCollisions().size() == 0);
		assert (projector.getOpponentCollisions().size() == 0);

		projector.addDirection (-1,0,0); // DOWN
		projector.project ();
		assert (projector.getMoves().size() == 4);
		assert (projector.getTeamCollisions().size() == 0);
		assert (projector.getOpponentCollisions().size() == 0);
	}

	public void collisionTest () {
		Projector projector;
		int[] origin;

		testMsg ("Collision Test");
		this.clearBoard ();

		/*
		LAYER 0:
		
		    0  1  2  3  4
		  +--+--+--+--+--+
		  |  |  |  |  |  | 4
		  +--+--+--+--+--+
		  |  |  |  |  |  | 3
		  +--+--+--+--+--+
		  |  |  |  |  |  | 2
		  +--+--+--+--+--+
		  |  |  |BK|  |  | 1
		  +--+--+--+--+--+
		  |  |WR|BP|WR|  | 0
		  +--+--+--+--+--+
*/
		assert (this.board.insertPiece (Piece.BLACK_KING, 0,2,1));
		assert (this.board.insertPiece (Piece.BLACK_PAWN, 0,2,0));
		assert (this.board.insertPiece (Piece.WHITE_ROOK, 0,3,0));
		assert (this.board.insertPiece (Piece.WHITE_ROOK, 0,1,0));
		
		origin = new int[3];
		origin[0] = 0;
		origin[1] = 2;
		origin[2] = 0;
		projector = new Projector (origin, Piece.BLACK, this.board);

		projector.addDirection (0,1,0); // RIGHT
		projector.addDirection (0,-1,0); // LEFT
		projector.addDirection (0,0,1); // FRONT
		projector.addDirection (1,0,0); // UP
		projector.addDirection (-1,0,0); // DOWN
		projector.project ();
		assert (projector.getMoves().size() == 4);
		assert (projector.getTeamCollisions().size() == 1);
		assert (projector.getOpponentCollisions().size() == 2);
	}

	private void clearBoard () {
		this.board = new Board ();
	}
}
