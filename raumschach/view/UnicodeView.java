package raumschach.view;
import raumschach.event.GameEvent;
import raumschach.event.PieceEvent;
import raumschach.event.MoveEvent;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class UnicodeView implements RaumschachView {
	private JFrame window;
	private JTable[] boards2d;
	private byte[][][] board;
	private TableModel[] models;

	public UnicodeView () {
		JPanel mainContainer;

		this.board = new byte[5][5][5];
		this.window = new JFrame ("Raumschach!");
		mainContainer = new JPanel ();
		this.window.add (mainContainer);
		mainContainer.setLayout (new BoxLayout (mainContainer, BoxLayout.Y_AXIS));

		this.boards2d = new JTable[5];
		this.models = new TableModel[5];
		for (int idx = 4; idx > -1; idx--) {
			mainContainer.add (new JLabel ("Layer "+ (idx+1)));
			this.models[idx] = new RaumTableModel (this.board, idx);
			this.boards2d[idx] = new JTable (this.models[idx]);
			mainContainer.add (this.boards2d[idx]);
		}
		//this.window.setSize (120, 500);
		this.window.pack ();
		this.window.setVisible (true);
	}

	@Override
	public void handleEvent (GameEvent event) {
		int[] pos = null;
		int[] dest = null;
		byte ctype = 0;

		if (event instanceof PieceEvent) {
			ctype = ((PieceEvent) event).getPieceCType ();
			pos = ((PieceEvent) event).getPiecePosition ();
		}

		if (event instanceof MoveEvent) {
			dest = ((MoveEvent) event).getDestination ();
		}

		switch (event.getEventType ()) {
			case GameEvent.APPEAR:
				this.board[pos[0]][pos[1]][pos[2]] = ctype;
				break;

			case GameEvent.MOVE:
				this.board[dest[0]][dest[1]][dest[2]] = this.board[pos[0]][pos[1]][pos[2]];
				this.board[pos[0]][pos[1]][pos[2]] = 8;
				break;
		}

	this.refresh ();
	}

	private void refresh () {
		for (int idx = 0; idx < 5; idx++) {
			this.boards2d[idx].updateUI ();
		}
	}
}

class RaumTableModel extends AbstractTableModel {
	final static char[] pieceTranslation =
	  {' ', '♙', '♘', '♖', '/', '♗', '♕', '♔',
	   '#', '♟', '♞', '♜', '\\', '♝', '♛', '♚', };
	byte[][][] board;
	int layer;

	public RaumTableModel (byte[][][] board, int layer) {
		this.board = board;
		this.layer = layer;
	}

	public int getRowCount () {
		return 5;
	}

	public int getColumnCount () {
		return 5;
	}

	public Object getValueAt (int row, int column) {
		return RaumTableModel.pieceTranslation[this.board[layer][column][4-row]];
	}

	public String getColumnName (int column) {
		return Character.toString ((char) (column + (int) 'A'));
	}
}
