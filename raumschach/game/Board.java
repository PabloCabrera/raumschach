/*
eje z: Arriba/Abajo
eje x: Derecha/Izquierda
eje y: Adelante/Atras
*/

package raumschach.game;
import java.util.ArrayList;

public class Board {
	private Piece[][][] cells;

	public Board () {
		this.cells = new Piece[5][5][5]; //suponemos que se inicializa con null
	}

	public Piece getPieceAt (int z, int x , int y) {
		return this.cells[z][x][y];
	}

	public Piece getPieceAt (int[] pos) {
		return this.cells[pos[0]][pos[1]][pos[2]];
	}

	public void setLayout (byte[][][] snapshot) {
		/* Crea las piezas en su posicion inicial */
		Piece piece;
		byte type;

		this.clearBoard();
		for (int z = 0; z<5; z++) {
			for (int x = 0; x<5; x++) {
				for (int y = 0; y<5; y++) {
					type = snapshot[z][x][y];
					if (type != Piece.NOTHING) {
						this.insertPiece (type, z, x, y);
					}
				}
			}
		}
	}

	public boolean move (int fromZ, int fromX, int fromY,
			int toZ, int toX, int toY) {
		//Solo para movimientos que no sean capturas
		Piece piece;

		piece = this.getPieceAt (fromZ, fromX, fromY);
		if (piece == null) {
			return false;
		} else if (piece.isValidMove (toZ, toX, toY)){
			return this.forceMove (fromZ, fromX, fromY, toZ, toX, toY);
		} else {
			return false;
		}
	}

	public boolean capture (int fromZ, int fromX, int fromY,
		int toZ, int toX, int toY) {
		return false;
	}

	public boolean forceMove (int fromZ, int fromX, int fromY,
		int toZ, int toX, int toY) {
		/* mueve una pieza aunque el movimiento no sea valido */
		Piece piece;
		piece = this.cells[fromZ][fromX][fromY];
		if ((piece != null) && (this.cells[toZ][toX][toY] == null)) {
			this.cells[toZ][toX][toY] = piece;
			this.cells[fromZ][fromX][fromY] = null;
			return true;
			}
		return false;
	}

	public boolean forceCapture (int fromZ, int fromX, int fromY,
		int toZ, int toX, int toY) {
		/* captura una pieza aunque el movimiento no sea valida */
		return false;
	}

	public void clearBoard () {
		for (int z = 0; z<5;z++) {
			for (int x = 0; x<5; x++) {
				for (int y = 0; y<5; y++) {
					this.deletePiece (z, x, y);
				}
			}
		}
	}

	public boolean insertPiece (byte type, int z, int x, int y) {
		if (cells[z][x][y] == null) {
			this.cells[z][x][y] = Piece.create (type, this);
			return true;
		} else {
			return false;
		}
	}

	public boolean deletePiece (int z, int x, int y) {
		Piece piece;
		piece = this.getPieceAt (z, x, y);
		if (piece != null) {
			this.cells[z][x][y] = null;
			return true;
		}
		return false;
	}

	public int[] getPiecePosition (Piece piece) {
		for (int z = 0; z<5; z++) {
			for (int x = 0; x<5; x++) {
				for (int y = 0; y<5; y++) {
					if (this.cells [z][x][y] == piece) {
						int[] position  = new int [3];
						position[0] = z;
						position[1] = x;
						position[2] = y;
						return position;
					}
				}
			}
		}
		return null;
	}

	public byte[][][] getSnapshot () {
		byte[][][] snapshot = new byte[5][5][5];
		for (int z = 0; z<5; z++) {
			for (int x = 0; x<5; x++) {
				for (int y = 0; y<5; y++) {
					if (this.cells[z][x][y] != null) {
						snapshot[z][x][y] = this.cells[z][x][y].getCtype();
					}
				}
			}
		}
		return snapshot;
	}

	public boolean inBounds (int z, int x, int y) {
		return
			(z < 5) && (z > -1) &&
			(x < 5) && (x > -1) &&
			(y < 5) && (y > -1);
	}

	public boolean inBounds (int[] pos) {
		return this.inBounds (pos[0], pos[1], pos[2]);
	}

	public ArrayList<Piece> getAllPieces () {
		ArrayList<Piece> pieces;
		pieces = new ArrayList<Piece> ();
		Piece current;

		for (int z = 0; z<5; z++) {
			for (int x = 0; x<5; x++) {
				for (int y = 0; y<5; y++) {
					current = this.cells[z][x][y];
					if (current != null) {
						pieces.add (current);
					}
				}
			}
		}
		return pieces;
	}

	public byte inCheck () {
		ArrayList<Piece> pieces;
		Piece current;
		ArrayList<int[]> captures;
		int[] target;
		Piece other;

		pieces = this.getAllPieces ();
		for (int index = 0; index < pieces.size (); index++) {
			current = pieces.get (index);
			captures = current.getValidCaptures ();
			for (int cidx = 0; cidx < captures.size (); cidx++) {
				target = captures.get (cidx);
				other = this.getPieceAt (target);
				if (other.getType () == Piece.KING) {
					return other.getCtype();
				}
			}
		}

		return Piece.NOTHING;
	}


}
