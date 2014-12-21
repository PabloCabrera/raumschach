package raumschach.test;
import raumschach.game.Game;
import raumschach.game.Piece;
import raumschach.controller.RaumschachController;
import raumschach.controller.PlayerControl;
import raumschach.event.RaumschachEventHandler;
import raumschach.view.GraphicView;

public class GameTest {

	public static void main (String args[]) {
		RaumschachController cont;
		Game game;
		GraphicView view;
		PlayerControl pcontrol;

		game = new Game ();
		cont = new RaumschachController (game);
		view = new GraphicView ();
		game.setEventListener (cont);
		cont.attachView (view);

		pcontrol = cont.getPlayerControl (Piece.WHITE);
		assert (pcontrol != null);
		view.connectAsPlayer (pcontrol, Piece.WHITE);

		pcontrol = cont.getPlayerControl (Piece.BLACK);
		assert (pcontrol != null);
		view.connectAsPlayer (pcontrol, Piece.BLACK);

		cont.startGame ();
	}

}

