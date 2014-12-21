package raumschach.controller;
import raumschach.game.Game;
import raumschach.game.Piece;
import raumschach.event.GameEvent;
import raumschach.event.RaumschachEventHandler;
import java.util.ArrayList;
import java.util.ListIterator;

public class RaumschachController implements RaumschachEventHandler {
	private Game game;
	private PlayerControl[] controls;
	private boolean[] controlBusy;
	private ArrayList<RaumschachEventHandler> views;

	public RaumschachController (Game game) {
		this.views = new ArrayList<RaumschachEventHandler> ();
		this.game = game;

		this.controlBusy = new boolean[2];
		this.controlBusy[0] = false;
		this.controlBusy[1] = false;

		this.controls = new PlayerControl[2];
		this.controls[0] = new PlayerControl(this.game, Piece.WHITE);
		this.controls[1] = new PlayerControl(this.game, Piece.BLACK);

	}

	public void startGame () {
		this.game.start ();
	}

	public PlayerControl getPlayerControl (boolean color) {
		if (color == Piece.WHITE) {
			return this.controls[0];
		} else {
			return this.controls[1];
		}
	}

	public void attachView (RaumschachEventHandler view) {
		this.views.add (view);
	}

	public void detachView (RaumschachEventHandler view) {
		this.views.remove (view);
	}

	public void notifyEvent (GameEvent event) {
		ListIterator<RaumschachEventHandler> it;
		RaumschachEventHandler view;

		it = this.views.listIterator ();
		while (it.hasNext ()) {
			view = it.next ();
			view.handleEvent (event);
		}
	}

	@Override
	public void handleEvent (GameEvent e) {
		this.notifyEvent (e);
	}

}
