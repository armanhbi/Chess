package de.josephschnacher.chess.logic;

public class GameBoard {

	private Field[][] field;

	public GameBoard() {
		this.field = new Field[8][8];
		init();
	}

	public void init() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				field[i][j] = new Field(null);
			}
		}
	}

	public Field[][] getField() {
		return field;
	}

	public void getField(Field[][] field) {
		this.field = field;
	}
	
	public Field get(int x, int y) {
		return field[x][y];
	}
	
	public void set(Piece piece, int x, int y) {
		field[x][y].setPiece(piece);
	}

	public String toString() {
		String string = "+ - - - - - - - - +\n";
		for (int i = field[0].length-1; i >= 0; i--) {
			string += "| ";
			for (int j = 0; j < field[0].length; j++) {
				char c = (field[j][i].getPiece() == null) ? '.' : field[j][i].getPiece().getShortName();
				string += c + " ";
			}
			string += "|\n";
		}
		string += "+ - - - - - - - - +";
		return string;
	}

}
