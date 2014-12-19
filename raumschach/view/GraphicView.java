package raumschach.view;
import raumschach.event.GameEvent;
import raumschach.event.PieceEvent;
import raumschach.event.MoveEvent;
import java.lang.Thread;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

public class GraphicView implements RaumschachView {

	static String imagePath = "../res/";
	static long MOVE_DURATION = 300;

	private Object3d[][][] board;
	private JFrame window;
	private BoardLayerView[] planes;
	private JPanel proj3d;
	private Repainter repainter;

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
		this.window.getContentPane().setLayout (new BoxLayout (this.window.getContentPane(), BoxLayout.X_AXIS));
		this.planes = new BoardLayerView[5];
		this.proj3d = new JPanel ();
		this.proj3d.setSize (500, 500);

		planesContainer = new JPanel ();
		planesContainer.setLayout (new BoxLayout (planesContainer, BoxLayout.Y_AXIS));
		for (int i = 4; i > -1; i--) {
			planesContainer.add (new JLabel ("Layer " + (char) (i+65)));
			this.planes[i] = new BoardLayerView (this.board, i);
			this.planes[i].setSize (100,100);
			planesContainer.add (this.planes[i]);
		}
		this.window.add (planesContainer);
		this.window.add (this.proj3d);

		this.window.pack ();
		this.window.setVisible (true);

		this.repainter = new Repainter (this.planes);
		this.repainter.start ();
	}

	private void message (String msg) {
		System.out.println (msg);
	}
}
