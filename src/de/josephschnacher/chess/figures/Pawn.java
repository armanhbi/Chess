package de.josephschnacher.chess.figures;

import java.util.ArrayList;
import java.util.List;

import de.josephschnacher.chess.logic.PieceColor;
import de.josephschnacher.chess.logic.Field;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.Position;

public class Pawn extends Piece {

	public Pawn(Position pos, PieceColor color) {
		super(pos, color);
	}

	public Pawn(int x, int y, PieceColor color) {
		super(new Position(x, y), color);
	}
	
	@Override
	public char getShortName() {
		return 'P';
	}
	
	@Override
	public char getUnicode() {
		return (getColor() == PieceColor.WHITE) ? '♙' : '♟';
	}

	@Override
	public List<Position> getAllowedWithKing(GameBoard gameBoard) {
		List<Position> allAllowed = new ArrayList<>();
		Field[][] field = gameBoard.getField();
		if (getColor() == PieceColor.WHITE) {
			if (getPosition().getY() == 1) {
				if (field[getPosition().getX()][2].getPiece() == null
						&& (field[getPosition().getX()][3].getPiece() == null)) {
					allAllowed.add(new Position(getPosition().getX(), 3));
				}
			}

			int newY = getPosition().getY() + 1;
			if (newY < 8 && (field[getPosition().getX()][newY].getPiece() == null)) {
				allAllowed.add(new Position(getPosition().getX(), newY));
			}

			int newX = getPosition().getX() - 1;
			if (newY < 8 && newX >= 0 && field[newX][newY].getPiece() != null
					&& field[newX][newY].getPiece().getColor() == PieceColor.BLACK) {
				allAllowed.add(new Position(newX, newY));
			}

			newX = getPosition().getX() + 1;
			if (newY < 8 && newX < 8 && field[newX][newY].getPiece() != null
					&& field[newX][newY].getPiece().getColor() == PieceColor.BLACK) {
				allAllowed.add(new Position(newX, newY));
			}

		} else {
			if (getPosition().getY() == 6) {
				if (field[getPosition().getX()][5].getPiece() == null
						&& (field[getPosition().getX()][4].getPiece() == null)) {
					allAllowed.add(new Position(getPosition().getX(), 4));
				}
			}

			int newY = getPosition().getY() - 1;
			if (newY >= 0 && (field[getPosition().getX()][newY].getPiece() == null)) {
				allAllowed.add(new Position(getPosition().getX(), newY));
			}

			int newX = getPosition().getX() - 1;
			if (newY >= 0 && newX >= 0 && field[newX][newY].getPiece() != null
					&& field[newX][newY].getPiece().getColor() == PieceColor.WHITE) {
				allAllowed.add(new Position(newX, newY));
			}

			newX = getPosition().getX() + 1;
			if (newY >= 0 && newX < 8 && field[newX][newY].getPiece() != null
					&& field[newX][newY].getPiece().getColor() == PieceColor.WHITE) {
				allAllowed.add(new Position(newX, newY));
			}

		}

		return allAllowed;
	}

}
