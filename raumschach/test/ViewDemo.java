package raumschach.test;
import raumschach.view.TextView;
import raumschach.view.GraphicView;
import raumschach.game.Piece;
import raumschach.event.GameEvent;
import raumschach.event.PieceEvent;
import raumschach.event.MoveEvent;
import raumschach.event.ClockEvent;
import raumschach.event.PlayerEvent;
import raumschach.event.RaumschachEventHandler;
import java.util.Timer;
import java.util.TimerTask;


public class ViewDemo {
	private RaumschachEventHandler view;
	private int turn = 0;
	private Timer timer;
	private TimerTask task;
	private int step;
	private long roundTime = 1800000l;
	private int[] whitestart = {0, 2, 0};
	private int[] blackstart = {4, 2, 4};
	private int[] rookstart = {0, 0, 0};
	private int[] rookend = {4, 0, 0};
	private int[][][] moves = {
	/* White moves */
	{
		{0, 2, 0},
		{1, 2, 0},
		{2, 2, 1},
		{3, 1, 3},
		{3, 0, 3},
		{4, 0, 3},
		{4, 1, 2},
		{3, 2, 1},
		{2, 2, 2},
		{3, 2, 1},
		{2, 1, 0},
		{1, 0, 0},
	},
	/* Black moves */
	{
		{4, 2, 4},
		{3, 2, 3},
		{2, 1, 2},
		{3, 2, 1},
		{3, 3, 2},
		{3, 3, 3},
		{4, 4, 4},
		{3, 4, 4},
		{2, 4, 3},
		{1, 4, 2},
		{0, 4, 1},
		{0, 4, 0},
	}};

	public static void main (String args[]) {
		ViewDemo demo;
		RaumschachEventHandler view = null;

		try {
			//view = new TextView ();
			view = new GraphicView ();
			demo = new ViewDemo (view);
		} catch (Exception e) {
			System.out.println ("Exception: " + e.getMessage ());
		}
	}

	public ViewDemo (RaumschachEventHandler view) throws Exception {
		GameEvent event;

		this.view = view;

		/* Two kings */
		event = new PieceEvent (GameEvent.APPEAR, 0l, Piece.BLACK,
			Piece.KING, blackstart);
		this.view.handleEvent (event);

		event = new PieceEvent (GameEvent.APPEAR, 0l, Piece.WHITE,
			Piece.KING, whitestart);
		this.view.handleEvent (event);


		/* Game Starts! */
		event = new GameEvent (GameEvent.START, 0l);

		/* Players moves */
		this.timer = new Timer ();
		for (int step = 1; step + 1 < moves[0].length; step++) {
			this.commandMove (step, 0);
			this.commandClock (step, 0);
			this.commandTurn (step, 1);
			this.commandMove (step, 1);
			this.commandClock (step, 1);
			this.commandTurn (step, 0);
		}

		/* Rook */
		event = new PieceEvent (GameEvent.APPEAR, 0l, Piece.WHITE,
			Piece.ROOK, rookstart);
		this.view.handleEvent (event);

		event = new MoveEvent (GameEvent.MOVE, 2000, Piece.WHITE, Piece.ROOK,  rookstart, rookend);
		this.view.handleEvent (event);
	}

	private void commandMove (int step, int player) {
		int[] prevPos;
		int[] nextPos;
		long time;
		boolean color;
		GameEvent event;
		TimerTask task;

		prevPos = this.moves[player][step-1];
		nextPos = this.moves[player][step];
		time = 2000 * (step * 2 + player + 1);
		color = (player == 0)? Piece.WHITE: Piece.BLACK;
		try {
			event = new MoveEvent (GameEvent.MOVE, time, color,
				Piece.KING, prevPos, nextPos);
			task = new TimedEvent (event, this.view);
			this.timer.schedule (task, time);
		} catch (Exception e) {
			System.out.println ("Exception: " + e.getMessage ());
		}
	}

	private void commandClock (int step, int player) {
		long time;
		long timeleft;
		boolean color;
		GameEvent event;

		time = 2000 * (step * 2 + player + 1);
		timeleft = this.roundTime - time;

		try {
			/* Stop current */
			color = (player == 0)? Piece.WHITE: Piece.BLACK;
			event = new ClockEvent (GameEvent.CLOCK, time, color, timeleft, false);
			task = new TimedEvent (event, this.view);
			this.timer.schedule (task, time);

			/* Start other */
			color = (player == 0)? Piece.BLACK: Piece.WHITE;
			event = new ClockEvent (GameEvent.CLOCK, time, color, timeleft, true);
			task = new TimedEvent (event, this.view);
			this.timer.schedule (task, time);
		} catch (Exception e) {
			System.out.println ("Exception: " + e.getMessage ());
		}
	}


	private void commandTurn (int step, int player) {
		boolean color;
		GameEvent event;
		long time;

		color = (player == 0)? Piece.BLACK: Piece.WHITE;
		time = 2000 * (step * 2 + player + 1);

		try {
			event = new PlayerEvent (GameEvent.TURN, time, color);
			task = new TimedEvent (event, this.view);
			this.timer.schedule (task, time);
		} catch (Exception e) {
			System.out.println ("Exception: " + e.getMessage ());
		}
	}

}


class TimedEvent extends TimerTask {
	private GameEvent event;
	private RaumschachEventHandler view;

	public TimedEvent (GameEvent event, RaumschachEventHandler view) {
		this.event = event;
		this.view = view;
	}

	@Override
	public void run () {
		this.view.handleEvent (this.event);
	}

}
