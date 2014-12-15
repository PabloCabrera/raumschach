package raumschach.game;

class Game {
	private Board board;
	private boolean turn;

	public Game () {
		
	}

	public boolean startGame () {
		this.board = new Board ();
		return false;
	}

}
