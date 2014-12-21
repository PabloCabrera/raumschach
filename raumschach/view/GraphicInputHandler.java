package raumschach.view;
import raumschach.game.Piece;
import raumschach.controller.PlayerControl;
import java.util.ArrayList;
import java.util.ListIterator;

class GraphicInputHandler {
	public static final int PLAYER_WHITE = 0;
	public static final int PLAYER_BLACK = 1;

	private Piece3d selected = null;
	private Object3d[][][] board;
	private int[] selectedPos; //Only valid if selected != null
	private boolean turn = Piece.WHITE;
	private PlayerControl[] controls;

	public GraphicInputHandler (Object3d[][][] board) {
		this.board = board;
		this.selectedPos = new int [3];
		this.controls = new PlayerControl[2];
	}

	public void attachControl (PlayerControl cont, boolean color) {
		int player;

		if (color == Piece.WHITE) {
			player = PLAYER_WHITE;
		} else {
			player = PLAYER_BLACK;
		}
		assert (cont != null);
		this.controls [player] = cont;
	}

	public void notifyTurn (boolean turn) {
		//this.clearSelected ();
		this.clearIndicators ();
		this.turn = turn;
	}

	public void notifyClick (int z, int x, int y) {
	}

	public void notifyPress (int z, int x, int y) {
		Object3d obj;
		Piece3d piece;
		ValidMove3d indicator;
		int[] destination;
		destination = new int[3];
		destination[0] = z;
		destination[1] = x;
		destination[2] = y;

		if (this.isTurn ()) {
			obj = this.board[z][x][y];

			if (obj == null) {
				/* EMPTY INVALID MOVE CELL */
				this.selected = null;
				this.clearIndicators ();
			} else if (obj instanceof ValidMove3d) {
				/* EMPTY VALID MOVE CELL */
				this.clearIndicators ();
				commandPlay (selectedPos, destination);
			} else if (obj instanceof Piece3d) {
				/* BUSY CELL */
				piece = (Piece3d) obj;
				if (this.turnColor (piece.getColor ())) {
					/* OWN PIECE */
					this.clearIndicators ();
					this.selected = piece;
					this.selectedPos[0] = z;
					this.selectedPos[1] = x;
					this.selectedPos[2] = y;
					this.createIndicators (z, x, y);
				} else if (piece.isCapturable ()) {
					/* ENEMY CAPTURABLE PIECE */
					this.clearIndicators ();
					commandPlay (selectedPos, destination);
				} else {
					/* ENEMY NON-CAPTURABLE PIECE  */
					this.selected = null;
					this.clearIndicators ();
				} 
			}
		}
	}

	public void notifyRelease (int z, int x, int y) {
	}

	public void clearIndicators () {
		Object3d obj;
		Piece3d piece;

		for (int z = 0; z < 5; z++) {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					obj = this.board[z][x][y];
					if (obj != null) {
						if (obj instanceof Piece3d) {
							piece = (Piece3d) obj;
							piece.clearCapturable ();
						} else if (obj instanceof ValidMove3d) {
							this.board[z][x][y] = null; 
						}
					}
				}
			}
		}
	}

	private void createIndicators (int z, int x, int y) {
		int playerTurn;
		ArrayList<int[]> moves;
		ArrayList<int[]> captures;
		ListIterator<int[]> it;
		int[] pos;

		playerTurn = this.getPlayerTurn ();
		if (this.controls[playerTurn] != null) {
			moves = this.controls[playerTurn].validMoves (z, x, y);
			it = moves.listIterator ();
			while (it.hasNext ()) {
				pos = it.next ();
				this.createMoveIndicator (pos[0], pos[1], pos[2]);
			}

			captures = this.controls[playerTurn].validCaptures (z, x, y);
			it = captures.listIterator ();
			while (it.hasNext ()) {
				pos = it.next ();
				this.indicateCapturable (pos[0], pos[1], pos[2]);
			}
		}

	}

	private void createMoveIndicator (int z, int x, int y) {
		ValidMove3d indicator;

		indicator = new ValidMove3d ((float) z, (float) x, (float) y);
		this.board[z][x][y] = indicator;
	}

	private void indicateCapturable (int z, int x, int y) {
		Object3d obj;
		Piece3d piece;

		obj = this.board[z][x][y];
		if ((obj != null) && (obj instanceof Piece3d)) {
			piece = (Piece3d) obj;
			piece.setCapturable ();
		}
	}


	private void commandPlay (int[] origin, int [] destination) {
		if (isTurn ()) {
			int player = this.getPlayerTurn ();
			this.controls[player].play (origin, destination);
		} else {
			clearIndicators ();
			this.selected = null;
		}
	}

	private boolean isTurn () {
		if (this.turn == Piece.WHITE) {
			return (this.controls[PLAYER_WHITE] != null);
		} else {
			return (this.controls[PLAYER_BLACK] != null);
		}
	}

	private boolean turnColor (boolean color) {
		return (color == this.turn);
	}

	private int getPlayerTurn ()  {
		if (this.turn == Piece.WHITE) {
			return 0;
		} else {
			return 1;
		}
	}


}
