package de.josephschnacher.chess.logic;

import java.util.List;

import de.josephschnacher.chess.figures.Bishop;
import de.josephschnacher.chess.figures.King;
import de.josephschnacher.chess.figures.Knight;
import de.josephschnacher.chess.figures.Pawn;
import de.josephschnacher.chess.figures.Piece;
import de.josephschnacher.chess.figures.Queen;
import de.josephschnacher.chess.figures.Rook;

public class GameBoard {

	private final Field[][] field;

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

	public void fillStart() {
		// WHITE
		field[0][0].setPiece(new Rook(0, 0, Color.WHITE));
		field[1][0].setPiece(new Knight(1, 0, Color.WHITE));
		field[2][0].setPiece(new Bishop(2, 0, Color.WHITE));
		field[3][0].setPiece(new Queen(3, 0, Color.WHITE));
		field[4][0].setPiece(new King(4, 0, Color.WHITE));
		field[5][0].setPiece(new Bishop(5, 0, Color.WHITE));
		field[6][0].setPiece(new Knight(6, 0, Color.WHITE));
		field[7][0].setPiece(new Rook(7, 0, Color.WHITE));
		for (int i = 0; i < 8; i++) {
			field[i][1].setPiece(new Pawn(i, 1, Color.WHITE));
		}

		// BLACK
		field[0][7].setPiece(new Rook(0, 7, Color.BLACK));
		field[1][7].setPiece(new Knight(1, 7, Color.BLACK));
		field[2][7].setPiece(new Bishop(2, 7, Color.BLACK));
		field[3][7].setPiece(new Queen(3, 7, Color.BLACK));
		field[4][7].setPiece(new King(4, 7, Color.BLACK));
		field[5][7].setPiece(new Bishop(5, 7, Color.BLACK));
		field[6][7].setPiece(new Knight(6, 7, Color.BLACK));
		field[7][7].setPiece(new Rook(7, 7, Color.BLACK));
		for (int i = 0; i < 8; i++) {
			field[i][6].setPiece(new Pawn(i, 6, Color.BLACK));
		}

	}

	public boolean move(int fromX, int fromY, int toX, int toY, Color currentTurn) {
		if (fromX >= 0 && fromX < 8 && fromY >= 0 && fromY < 8 && toX >= 0 && toX < 8 && toY >= 0 && toY < 8) {
			Piece piece = field[fromX][fromY].getPiece();
			if (piece != null && piece.getColor() == currentTurn) {
				Position to = new Position(toX, toY);
				List<Position> allowed = piece.getAllowed(this);
				if (allowed.contains(to)) {
					this.set(null, fromX, fromY);
					this.set(piece, toX, toY);
					piece.setPosition(to);
					return true;
				}
			}
		}
		return false;
	}

	public boolean move(String from, String to, Color currentTurn) {
		int[] fromInt = alphabetToInt(from);
		int[] toInt = alphabetToInt(to);
		return move(fromInt[0], fromInt[1], toInt[0], toInt[1], currentTurn);
	}

	public Field[][] getField() {
		return field;
	}

	public Field get(int x, int y) {
		return field[x][y];
	}

	public Field get(String s) {
		int[] x = alphabetToInt(s);
		return field[x[0]][x[1]];
	}

	private int[] alphabetToInt(String s) {
		if (s.length() != 2) {
			return null;
		}
		char letter = (char) s.toUpperCase().charAt(0);
		int x = letter - 65;
		int y = Integer.parseInt(s.charAt(1) + "") - 1;
		return new int[] { x, y };
	}

	public void set(Piece piece, int x, int y) {
		field[x][y].setPiece(piece);
	}

	public String toString() {
		String string = "+ - - - - - - - - +\n";
		for (int i = field[0].length - 1; i >= 0; i--) {
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

	public String toStringBig() {
		String string = "+-----------------------------------+\n";
		string += "|                                   |\n";
		for (int i = field[0].length - 1; i >= 0; i--) {
			string += "|   ";
			for (int j = 0; j < field[0].length; j++) {
				char c = (field[j][i].getPiece() == null) ? '.' : field[j][i].getPiece().getShortName();
				string += c + "   ";
			}
			string += "|";
			string += "\n|                                   |\n";
		}
		string += "+-----------------------------------+";
		return string;
	}

	public String toStringAlphabet() {
		String string = "+-----------------------------------+\n";
		string += "|                                   |\n";
		for (int i = field[0].length - 1; i >= 0; i--) {
			string += (i + 1) + "   ";
			for (int j = 0; j < field[0].length; j++) {
				char c = (field[j][i].getPiece() == null) ? '.' : field[j][i].getPiece().getShortName();
				string += c + "   ";
			}
			string += "|";
			string += "\n|                                   |\n";
		}
		string += "+---A---B---C---D---E---F---G---H---+";
		return string;
	}
}
