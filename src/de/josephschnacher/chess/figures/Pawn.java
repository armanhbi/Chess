package de.josephschnacher.chess.figures;

import java.util.ArrayList;
import java.util.List;

import de.josephschnacher.chess.logic.Color;
import de.josephschnacher.chess.logic.Field;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.Position;

public class Pawn extends Piece {

	public Pawn(Position pos, Color color) {
		super(pos, 'P', color);
	}

	public Pawn(int x, int y, Color color) {
		super(new Position(x, y), 'P', color);
	}

	@Override
	public List<Position> getAllowed(GameBoard gameBoard) {
		List<Position> allAllowed = new ArrayList<>();
		Field[][] field = gameBoard.getField();
		if (getColor() == Color.WHITE) {
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
					&& field[newX][newY].getPiece().getColor() == Color.BLACK) {
				allAllowed.add(new Position(newX, newY));
			}

			newX = getPosition().getX() + 1;
			if (newY < 8 && newX < 8 && field[newX][newY].getPiece() != null
					&& field[newX][newY].getPiece().getColor() == Color.BLACK) {
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
					&& field[newX][newY].getPiece().getColor() == Color.WHITE) {
				allAllowed.add(new Position(newX, newY));
			}

			newX = getPosition().getX() + 1;
			if (newY >= 0 && newX < 8 && field[newX][newY].getPiece() != null
					&& field[newX][newY].getPiece().getColor() == Color.WHITE) {
				allAllowed.add(new Position(newX, newY));
			}

		}

		return allAllowed;
	}

}
