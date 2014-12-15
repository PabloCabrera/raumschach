package raumschach.view;
import raumschach.event.GameEvent;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.table.AbstractTableModel;

public class UnicodeView implements RaumschachView {
	private JFrame window;
	private JTable[] boards2d;
	private byte[][][] board;

	public UnicodeView () {
		this.board = new byte[5][5][5];
		this.window = new JFrame ("Raumschach!");
		this.window.setLayout (new BoxLayout (this.window, BoxLayout.Y_AXIS));

		this.boards2d = new JTable[5];
		for (int idx = 0; idx < 5; idx++) {
			this.boards2d[idx] = new JTable ();
		}
	}

	@Override
	public void handleEvent (GameEvent event) {
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
		return RaumTableModel.pieceTranslation[this.board[layer][row][column]];
	}

	public String getColumnName (int column) {
		return Character.toString (Character.forDigit (column, 0));
	}
}
