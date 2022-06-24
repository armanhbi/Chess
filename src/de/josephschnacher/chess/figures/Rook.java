package de.josephschnacher.chess.figures;

import java.util.ArrayList;
import java.util.List;

import de.josephschnacher.chess.logic.Color;
import de.josephschnacher.chess.logic.Field;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.Position;

public class Rook extends Piece {

	public Rook(Position pos, Color color) {
		super(pos, 'R', color);
	}

	public Rook(int x, int y, Color color) {
		super(new Position(x, y), 'R', color);
	}

	@Override
	public List<Position> getAllowed(GameBoard gameBoard) {
		List<Position> allAllowed = new ArrayList<>();
		int curX = getPosition().getX();
		int curY = getPosition().getY();
		Field[][] field = gameBoard.getField();

		// hoch
		int index = curY + 1;
		while (index < 8
				&& (field[curX][index].getPiece() == null || field[curX][index].getPiece().getColor() != getColor())) {
			allAllowed.add(new Position(curX, index));
			index++;
			if (field[curX][index].getPiece() != null) {
				break;
			}
		}

		// runter
		index = curY - 1;
		while (index >= 0
				&& (field[curX][index].getPiece() == null || field[curX][index].getPiece().getColor() != getColor())) {
			allAllowed.add(new Position(curX, index));
			index--;
			if (field[curX][index].getPiece() != null) {
				break;
			}
		}

		// rechts
		index = curX + 1;
		while (index < 8
				&& (field[index][curY].getPiece() == null || field[index][curY].getPiece().getColor() != getColor())) {
			allAllowed.add(new Position(index, curY));
			index++;
			if (field[index][curY].getPiece() != null) {
				break;
			}
		}

		// links
		index = curX - 1;
		while (index >= 0
				&& (field[index][curY].getPiece() == null || field[index][curY].getPiece().getColor() != getColor())) {
			allAllowed.add(new Position(index, curY));
			index--;
			if (field[index][curY].getPiece() != null) {
				break;
			}
		}
		return allAllowed;
	}

}
