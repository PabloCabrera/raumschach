package raumschach.controller;
import raumschach.game.Game;
import java.util.ArrayList;

public class PlayerControl {
	private Game game;
	private boolean color;

	public PlayerControl (Game game, boolean color) {
		this.game = game;
		this.color = color;
	}

	public boolean play (int[] from, int[]to) {
		return false;
	}

	public ArrayList<int[]> validMoves (int[] from) {
		return this.game.getValidMoves (from);
	}

	public ArrayList<int[]> validMoves (int z, int x, int y) {
		int[] from = new int[3];
		from[0] = z;
		from[1] = x;
		from[2] = y;
		return this.game.getValidMoves (from);
	}

	public ArrayList<int[]> validCaptures (int[] from) {
		return this.game.getValidCaptures (from);
	}

	public ArrayList<int[]> validCaptures (int z, int x, int y) {
		int[] from = new int[3];
		from[0] = z;
		from[1] = x;
		from[2] = y;
		return this.game.getValidCaptures (from);
	}


}
