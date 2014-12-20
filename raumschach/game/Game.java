package raumschach.game;
import raumschach.game.Piece;
import raumschach.event.GameEvent;
import raumschach.event.PlayerEvent;
import raumschach.event.PieceEvent;
import raumschach.event.MoveEvent;
import raumschach.event.RaumschachEventHandler;
import java.util.ArrayList;
import java.util.Date;

public class Game {
		public static byte[][][] initialLayout =
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

	private Board board;
	private boolean turn = Piece.WHITE;
	private RaumschachEventHandler listener = null;

	public Game () {
		this.board = new Board ();
		this.board.setLayout (Game.initialLayout);
	}

	public void setEventListener (RaumschachEventHandler listener) {
		this.listener = listener;
	}

	public boolean start () {
		this.notifyPiecePositions ();
		this.notifyStart ();
		return true;
	}

	public boolean playerPlay (boolean player, int[] origin, int[] destination) {
		Piece p;

		if (player == turn) {
			p = this.board.getPieceAt (origin);
			if ((p != null) && (p.getColor () == player)) {
				if (this.play (origin, destination)) {
					this.changeTurn ();
					return true;
				}
			}
		}
		return false;
	}

	public ArrayList<int[]> getValidMoves (int[] origin) {
		Piece piece = this.board.getPieceAt (origin);
		ArrayList<int[]> moves;

		if (piece != null) {
			moves = piece.getValidMoves ();
		} else {
			moves = null;
		}

		return moves;
	}

	public ArrayList<int[]> getValidCaptures (int[] origin) {
		Piece piece = this.board.getPieceAt (origin);
		ArrayList<int[]> captures;

		if (piece != null) {
			captures = piece.getValidCaptures ();
		} else {
			captures = null;
		}

		return captures;
	}

	private boolean play (int[] origin, int[] destination) {
		Piece piece;
		PieceEvent event;
		long now = (new Date ()).getTime ();

		piece = this.board.getPieceAt (origin);
		if (piece.isValidMove (destination)) {
			this.board.move (origin, destination);
			event = new MoveEvent (GameEvent.MOVE, now, turn, piece.getType (), origin, destination);
			this.sendEvent (event);
			return true;
		} else if (piece.isValidCapture (destination)) {
			this.board.capture (origin, destination);
			event = new MoveEvent (GameEvent.CAPTURE, now, turn, piece.getType (), origin, destination);
			this.sendEvent (event);
		}

		return false;
	}

	private void changeTurn () {
		GameEvent event;
		long now = (new Date ()).getTime ();

		this.turn = !this.turn;
		event = new PlayerEvent (GameEvent.TURN, now, this.turn);
		this.sendEvent (event);
	}

	private void notifyStart () {
		GameEvent event;
		long now = (new Date ()).getTime ();

		event = new PlayerEvent (GameEvent.START, now, turn);
		this.sendEvent (event);

		event = new PlayerEvent (GameEvent.TURN, now, this.turn);
		this.sendEvent (event);
	}

	private void notifyPiecePositions () {
		Piece piece;
		PieceEvent event;
		long now = (new Date ()).getTime ();
		int[] pos = new int[3];

		for (int z = 0; z < 5; z++) {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					piece = this.board.getPieceAt (z, x, y);
					if (piece != null) {
						pos[0] = z;
						pos[1] = x;
						pos[2] = y;
						event = new PieceEvent (GameEvent.APPEAR, now, piece.getColor (),
							piece.getType (), pos);
						this.sendEvent (event);
					}
				}
			}
		}
	}

	private void sendEvent (GameEvent event) {
		this.listener.handleEvent (event);
	}

}
