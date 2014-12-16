package raumschach.view;
import raumschach.event.GameEvent;
import raumschach.event.PlayerEvent;
import raumschach.event.PieceEvent;
import raumschach.event.ClockEvent;
import raumschach.game.Piece;

public class TextView implements RaumschachView {
	public TextView () {
	}

	@Override
	public void handleEvent (GameEvent event) {
		String color = "";
		String time = "";
		String origin = "";
		String destination = "";
		String piece = "";

		if (event instanceof PlayerEvent) {
			if (((PlayerEvent) event).getColor () == Piece.WHITE) {
				color = "White";
			} else {
				color = "Black";
			}
		}

		if (event instanceof ClockEvent) {
			long seconds = ((ClockEvent) event).getTimeleft () / 1000;
			time = (seconds/60) + ":" + (seconds % 60);
		}

		if (event instanceof PieceEvent) {
			
		}


		switch (event.getEventType ()) {
			case GameEvent.START :
				System.out.println ("GAME STARTS!");
				break; 

			case GameEvent.PAUSE:
				System.out.println ("GAME PAUSED...");
				break; 

			case GameEvent.CONTINUE:
				System.out.println ("... GAME CONTINUES");
				break; 

			case GameEvent.DRAW:
				System.out.println ("GAME DRAW!");
				break; 

			case GameEvent.CHECKMATE:
				System.out.println ("CHECKMATE! " + color + " wins");
				break; 

			case GameEvent.RESIGN:
				System.out.println (color + " resigns!");
				break; 

			case GameEvent.TIMEOUT:
				System.out.println (color + " runned out of time!");
				break; 

			case GameEvent.STALE:
				System.out.println (color + " staled!");
				break; 

			case GameEvent.CHECK:
				System.out.println ("CHECK!");
				break; 

			case GameEvent.TURN:
				System.out.println (color + "'s turn");
				break; 

			case GameEvent.CLOCK:
				System.out.println (color + "'s time left:" + time);
				break; 

			case GameEvent.APPEAR:
				System.out.println ("There is a " + color +
					" " + piece + " in " + origin);
				break; 

			case GameEvent.DISSAPPEAR:
				System.out.println ("There are no piece at " + origin);
				break; 

			case GameEvent.MOVE:
				System.out.println (color + "moves " +
					origin + " to " + destination);
				break; 

			case GameEvent.CAPTURE:
				System.out.println (color + " captures " +
					origin + " * " + destination);
				break; 

			case GameEvent.PROMOTION:
				System.out.println (color + " pawn in " + origin +
					" promotes to " + piece + " to " + destination);
				break; 

		}
	}
}
