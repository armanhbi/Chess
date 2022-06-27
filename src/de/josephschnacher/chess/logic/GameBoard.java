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

	private Field[][] field;
	private boolean whiteCheck;
	private boolean blackCheck;

	public GameBoard() {
		this.field = new Field[8][8];
		whiteCheck = false;
		blackCheck = false;
		init();
	}

	public void init() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				field[i][j] = new Field(null);
			}
		}
	}

	// fills initial chess pieces to board
	public void fillStart() {
		// WHITE
		field[0][0].setPiece(new Rook(0, 0, PieceColor.WHITE));
		field[1][0].setPiece(new Knight(1, 0, PieceColor.WHITE));
		field[2][0].setPiece(new Bishop(2, 0, PieceColor.WHITE));
		field[3][0].setPiece(new Queen(3, 0, PieceColor.WHITE));
		field[4][0].setPiece(new King(4, 0, PieceColor.WHITE));
		field[5][0].setPiece(new Bishop(5, 0, PieceColor.WHITE));
		field[6][0].setPiece(new Knight(6, 0, PieceColor.WHITE));
		field[7][0].setPiece(new Rook(7, 0, PieceColor.WHITE));
		for (int i = 0; i < 8; i++) {
			field[i][1].setPiece(new Pawn(i, 1, PieceColor.WHITE));
		}

		// BLACK
		field[0][7].setPiece(new Rook(0, 7, PieceColor.BLACK));
		field[1][7].setPiece(new Knight(1, 7, PieceColor.BLACK));
		field[2][7].setPiece(new Bishop(2, 7, PieceColor.BLACK));
		field[3][7].setPiece(new Queen(3, 7, PieceColor.BLACK));
		field[4][7].setPiece(new King(4, 7, PieceColor.BLACK));
		field[5][7].setPiece(new Bishop(5, 7, PieceColor.BLACK));
		field[6][7].setPiece(new Knight(6, 7, PieceColor.BLACK));
		field[7][7].setPiece(new Rook(7, 7, PieceColor.BLACK));
		for (int i = 0; i < 8; i++) {
			field[i][6].setPiece(new Pawn(i, 6, PieceColor.BLACK));
		}

	}

	public Moveresult move(int fromX, int fromY, int toX, int toY) {
		if (fromX >= 0 && fromX < 8 && fromY >= 0 && fromY < 8 && toX >= 0 && toX < 8 && toY >= 0 && toY < 8) {
			Piece piece = field[fromX][fromY].getPiece();
			if (piece != null) {
				Position from = new Position(fromX, fromY);
				Position to = new Position(toX, toY);
				List<Position> allowed = piece.getAllowed(this);
				if (allowed.contains(to)) {
					Piece hit = get(toX, toY).getPiece();
					get(toX, toY).setPiece(null);
					this.set(null, fromX, fromY);
					this.set(piece, toX, toY);
					piece.setPosition(to);
					checkForCheck();
					if (piece.getColor() == PieceColor.WHITE && whiteCheck
							|| piece.getColor() == PieceColor.BLACK && blackCheck) {
						// System.out.println("ungültiger zug, du bist noch im schach");
						this.set(piece, fromX, fromY);
						this.set(hit, toX, toY);
						piece.setPosition(from);
						return new Moveresult(null, true, false);
					}
					return new Moveresult(hit, false, true);
				}
			}
		}
		return new Moveresult(null, false, false);
	}

	// applies changes in backward direction
	public void backwardMove(Change change) {
		int fromX = change.getTo().getX();
		int fromY = change.getTo().getY();
		Piece hit = change.getHit();

		Piece piece = field[fromX][fromY].getPiece();
		set((hit != null) ? hit : null, change.getTo());
		set(piece, change.getFrom());
		piece.setPosition(change.getFrom());
		checkForCheck();
	}

	// applies changes in forward direction
	public void forwardMove(Change change) {
		int fromX = change.getFrom().getX();
		int fromY = change.getFrom().getY();

		Piece piece = field[fromX][fromY].getPiece();
		set(null, change.getFrom());
		set(piece, change.getTo());
		piece.setPosition(change.getTo());
		checkForCheck();
	}

	public Moveresult move(String from, String to) {
		int[] fromInt = alphabetToInt(from);
		int[] toInt = alphabetToInt(to);
		return move(fromInt[0], fromInt[1], toInt[0], toInt[1]);
	}

	// checks if any king of color is checked (goes through all of the pieces)
	public void checkForCheck() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j].getPiece() != null) {
					Piece curPiece = field[i][j].getPiece();
					if (curPiece.getColor() == PieceColor.WHITE) {
						for (Position allowedPos : curPiece.getAllowedWithoutKing(this)) {
							if (get(allowedPos).getPiece() instanceof King) {
								blackCheck = true;
								return;
							}
						}
						blackCheck = false;
					}
				}
			}
		}

		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j].getPiece() != null) {
					Piece curPiece = field[i][j].getPiece();
					if (curPiece.getColor() == PieceColor.BLACK) {
						for (Position allowedPos : curPiece.getAllowedWithoutKing(this)) {
							if (get(allowedPos).getPiece() instanceof King) {
								whiteCheck = true;
								return;
							}
						}
						whiteCheck = false;
					}
				}
			}
		}
	}

	public Field[][] getField() {
		return field;
	}

	public Field get(int x, int y) {
		return field[x][y];
	}

	public Field get(Position position) {
		if (position.getX() >= 0 && position.getX() < 8 && position.getY() >= 0 && position.getY() < 8) {
			return field[position.getX()][position.getY()];
		}
		return null;
	}

	public Field get(String s) {
		int[] x = alphabetToInt(s);
		return field[x[0]][x[1]];
	}

	public boolean isWhiteCheck() {
		return whiteCheck;
	}

	public boolean isBlackCheck() {
		return blackCheck;
	}

	// translates normal position into chess languages (e.g. D5)
	public int[] alphabetToInt(String s) {
		if (s.length() != 2) {
			return null;
		}
		char letter = (char) s.toUpperCase().charAt(0);
		int x = letter - 65;
		int y = Integer.parseInt(s.charAt(1) + "") - 1;
		return new int[] { x, y };
	}

	public void set(Piece piece, Position position) {
		field[position.getX()][position.getY()].setPiece(piece);
	}

	public void set(Piece piece, int x, int y) {
		field[x][y].setPiece(piece);
	}

	// turns chess field into string (small version)
	public String toString() {
		String string = "\n+ - - - - - - - - +\n";
		for (int i = field[0].length - 1; i >= 0; i--) {
			string += "| ";
			for (int j = 0; j < field[0].length; j++) {
				char c = (field[j][i].getPiece() == null) ? '.' : field[j][i].getPiece().getShortName();
				string += c + " ";
			}
			string += "|\n";
		}
		string += "+ - - - - - - - - +\n";
		return string;
	}

	// "" big version -> without chess directions
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

	// with chess directions
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
