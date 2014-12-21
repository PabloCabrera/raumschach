package raumschach.view;
import raumschach.event.GameEvent;
import raumschach.event.PieceEvent;
import raumschach.event.MoveEvent;
import raumschach.controller.PlayerControl;
import raumschach.event.RaumschachEventHandler;
import java.lang.Thread;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

public class GraphicView implements RaumschachEventHandler {

	static String imagePath = "../res/";
	static long MOVE_DURATION = 800;

	private Object3d[][][] board;
	private JFrame window;
	private BoardLayerView[] planes;
	private Board3dView proj3d;
	private Repainter repainter;
	private GraphicInputHandler inputHandler;

	@Override
	public void handleEvent (GameEvent event) {
		PieceEvent pieceEvent = null;
		MoveEvent moveEvent = null;
		int[] position = null;
		int[] destination = null;
		byte ctype = 0;
		Object3d o3d = null;

		if (event instanceof PieceEvent) {
				pieceEvent = (PieceEvent) event;
				position = pieceEvent.getPiecePosition ();
				ctype = pieceEvent.getPieceCType ();
			}

		if (event instanceof MoveEvent) {
				moveEvent = (MoveEvent) event;
				destination = moveEvent.getDestination ();
				o3d = this.board[position[0]][position[1]][position[2]];
			}

		switch (event.getEventType ()) {
			case GameEvent.APPEAR:
				this.board[position[0]][position[1]][position[2]] = 
					new Piece3d ((float) position[0], (float) position[1], (float) position[2], ctype);
				break;

			case GameEvent.MOVE:
				if (o3d != null) {

					this.message (moveEvent.getPiecePositionAsString () + "->" + moveEvent.getDestinationAsString ());
					o3d.move (destination[0], destination[1], destination[2], MOVE_DURATION);
					this.board[destination[0]][destination[1]][destination[2]] = o3d;
					this.board[position[0]][position[1]][position[2]] = null;
				} else {
					this.message ("ERROR: Invalid move" + moveEvent.getPiecePositionAsString () + "->" + moveEvent.getDestinationAsString ());

				}
				break;
		}
	}


	public GraphicView () {
		JPanel planesContainer;

		this.board = new Object3d[5][5][5];
		this.window = new JFrame ("Raumschach");
		this.window.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.window.getContentPane().setLayout (new BorderLayout (8, 8));
		this.planes = new BoardLayerView[5];
		this.proj3d = new Board3dView (this.board);
		this.proj3d.setSize (500, 500);

		planesContainer = new JPanel ();
		planesContainer.setLayout (new BoxLayout (planesContainer, BoxLayout.Y_AXIS));
		for (int i = 4; i > -1; i--) {
			planesContainer.add (new JLabel ("Layer " + (char) (i+65)));
			this.planes[i] = new BoardLayerView (this.board, i);
			this.planes[i].setSize (100,100);
			planesContainer.add (this.planes[i]);
		}
		this.window.add (planesContainer, BorderLayout.WEST);
		this.window.add (this.proj3d, BorderLayout.CENTER);

		this.window.pack ();
		this.window.setSize (800, 600);
		this.window.setVisible (true);

		this.repainter = new Repainter (this.planes, this.proj3d);
		this.repainter.start ();
	}

	public void connectAsPlayer (PlayerControl control, boolean color) {
		if (this.inputHandler == null) {
			this.inputHandler = new GraphicInputHandler (this.board);
			for (int i = 0; i < 5; i++) {
				this.planes[i].setClickInterpreter (this.inputHandler);
			}
		}

		assert (control != null);
		this.inputHandler.attachControl (control, color);
	}

	public void message (String msg) {
		System.out.println (msg);
	}
}

