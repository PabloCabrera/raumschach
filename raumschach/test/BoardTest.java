package raumschach.test;
import raumschach.game.Board;
import raumschach.game.Piece;
import java.util.Arrays;

class BoardTest extends Test {
	private Board board;

	public static void main (String args[]) {
		BoardTest test;
		test = new BoardTest ();
	}

	public BoardTest () {
		this.createBoard ();
		this.loadLayout ();
		this.tryCheck (Piece.NOTHING);
		this.forceMove (0,2,0, 2,2,2); //White king at center
		this.forceMove (3,2,4, 0,0,4); //Black queen at bottom
		this.tryCheck (Piece.WHITE_KING);

	}

	public void createBoard () {
		testMsg ("Create board");
		this.board = new Board ();
		assert (board != null);
	}

	public void loadLayout () {
		byte[][][] layout =
		 {
		  {
		   {3,1,0,0,0},
		   {2,1,0,0,0},
		   {7,1,0,0,0},
		   {2,1,0,0,0},
		   {3,1,0,0,0}
		  },
		  {
		   {5,1,0,0,0},
		   {4,1,0,0,0},
		   {6,1,0,0,0},
		   {5,1,0,0,0},
		   {4,1,0,0,0}
		  },
		  {
		   {0,0,0,0,0},
		   {0,0,0,0,0},
		   {0,0,0,0,0},
		   {0,0,0,0,0},
		   {0,0,0,0,0}
		  },
		  {
		   {0,0,0,9,13},
		   {0,0,0,9,12},
		   {0,0,0,9,14},
		   {0,0,0,9,13},
		   {0,0,0,9,12}
		  },
		  {
		   {0,0,0,9,11},
		   {0,0,0,9,10},
		   {0,0,0,9,15},
		   {0,0,0,9,10},
		   {0,0,0,9,11}
		  }
		};
		byte[][][] snapshot;

		if (this.board == null) {
			this.createBoard ();
		}

		testMsg ("Load Layout");
		board.setLayout (layout);
		snapshot = board.getSnapshot ();
		assert (snapshot != null);

		testMsg ("Reading Layout");
		assert (Arrays.deepEquals (snapshot, layout));
	}

	public static void printLayout (byte[][][] layout) {
		for (int z = 0; z < 5; z++) {
			for (int x = 0; x < 5; x++) {
			System.out.println (Arrays.toString (layout[z][x]));
			}
			System.out.println ("");
		}
	}

	public void forceMove (
			int fromZ, int fromX, int fromY,
			int toZ, int toX, int toY) {
		byte[][][] snapshot;
		byte origin, destination;
		byte neworigin, newdestination;

		testMsg ("Force move");

		snapshot = this.board.getSnapshot ();
		origin = snapshot[fromZ][fromX][fromY];
		destination = snapshot[toZ][toX][toY];
		if (origin == 0) {
			fail ("There is no piece at " + fromZ + ", " + fromX + ", " + fromY);
		} else if (destination != 0) {
			fail ("There is a piece at " + toZ + ", " + toX + ", " + toY+ ": " + destination);
		} else {
			assert (this.board.forceMove (fromZ, fromX, fromY, toZ, toX, toY));

			snapshot = this.board.getSnapshot ();
			neworigin = snapshot[fromZ][fromX][fromY];
			newdestination = snapshot[toZ][toX][toY];

			assert (neworigin == Piece.NOTHING);
			assert (newdestination == origin);
		}
	}

	public void tryCheck (byte piece) {
		testMsg ("Check");
		assert (this.board.inCheck () == piece);
	}

}
