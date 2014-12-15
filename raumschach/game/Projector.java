package raumschach.game;
import java.util.ArrayList;

public class Projector {
	private int originZ, originX, originY;
	private Board board;
	private boolean color;
	private ArrayList<int[]> directions = null;
	private ArrayList<int[]> moves = null;
	private ArrayList<int[]> teamCollisions = null;
	private ArrayList<int[]> opponentCollisions = null;
	private boolean projected;

	public Projector (
		int[] origin,
		boolean color, Board board
	) {
		this.originZ = origin[0];
		this.originX = origin[1];
		this.originY = origin[2];

		this.color = color;
		this.board = board;

	}

	public void addDirection (int stepZ, int stepX, int stepY) {
		int[] dir;

		dir = new int[3];
		dir[0] = stepZ;
		dir[1] = stepX;
		dir[2] = stepY;
		
		if (this.directions == null) {
			this.directions = new ArrayList<int[]> ();
		}
		this.directions.add (dir);
		this.clear ();
	}

	public void project () {
		int[] direction;

		this.moves = new ArrayList<int[]> ();
		this.teamCollisions = new ArrayList<int[]> ();
		this.opponentCollisions = new ArrayList<int[]> ();

		for (int index = 0; index < this.directions.size(); index++) {
			direction = this.directions.get (index);
			this.projectStep (originZ, originX, originY, direction);
		}

		this.projected = true;
	}

	private void projectStep (
		int z, int x, int y,
		int[] direction
		) {
		Piece piece;
		int cz, cx, cy;

		cz = z + direction[0];
		cx = x + direction[1];
		cy = y + direction[2];

		if (this.board.inBounds (cz, cx, cy)) {
			int[] position;

			position = new int[3];
			position[0] = cz;
			position[1] = cx;
			position[2] = cy;
			
			piece = this.board.getPieceAt (cz, cx, cy);
			if (piece == null) {
				this.moves.add (position);
				this.projectStep (cz, cx, cy, direction);
			} else if (piece.color == this.color) {
				this.teamCollisions.add (position);
			} else {
				this.opponentCollisions.add (position);
			}
		}
	}

	public void clear () {
		this.projected = false;
		this.moves = null;
		this.teamCollisions = null;
		this.opponentCollisions = null;
	}

	public void clearDirections () {
		this.directions = null;
	}

	public ArrayList<int[]> getMoves () {
	if (!this.projected) this.project ();

		return moves;
	}

	public ArrayList<int[]> getTeamCollisions () {
	if (!this.projected) this.project ();

		return teamCollisions;
	}

	public ArrayList<int[]> getOpponentCollisions () {
	if (!this.projected) this.project ();

		return opponentCollisions;
	}

}
