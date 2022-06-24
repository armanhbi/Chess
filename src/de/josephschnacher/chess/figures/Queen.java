package de.josephschnacher.chess.figures;

import java.util.ArrayList;
import java.util.List;

import de.josephschnacher.chess.logic.Color;
import de.josephschnacher.chess.logic.Field;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.Position;

public class Queen extends Piece {

	public Queen(Position position, Color color) {
		super(position, 'Q', color);
	}

	public Queen(int x, int y, Color color) {
		super(new Position(x, y), 'Q', color);
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
			if (field[curX][index].getPiece() != null) {
				break;
			}
			index++;
		}

		// runter
		index = curY - 1;
		while (index >= 0
				&& (field[curX][index].getPiece() == null || field[curX][index].getPiece().getColor() != getColor())) {
			allAllowed.add(new Position(curX, index));
			if (field[curX][index].getPiece() != null) {
				break;
			}
			index--;
		}

		// rechts
		index = curX + 1;
		while (index < 8
				&& (field[index][curY].getPiece() == null || field[index][curY].getPiece().getColor() != getColor())) {
			allAllowed.add(new Position(index, curY));
			if (field[index][curY].getPiece() != null) {
				break;
			}
			index++;
		}

		// links
		index = curX - 1;
		while (index >= 0
				&& (field[index][curY].getPiece() == null || field[index][curY].getPiece().getColor() != getColor())) {
			allAllowed.add(new Position(index, curY));
			if (field[index][curY].getPiece() != null) {
				break;
			}
			index--;
		}

		// diagonal rechts oben
		int x = curX + 1;
		int y = curY + 1;
		boolean inside = x < 8 && y < 8;
		while (inside && (field[x][y].getPiece() == null || field[x][y].getPiece().getColor() != getColor())) {
			allAllowed.add(new Position(x, y));
			if (field[x][y].getPiece() != null) {
				break;
			}
			x++;
			y++;
		}

		// diagonal links oben
		x = curX - 1;
		y = curY + 1;
		inside = x >= 0 && y < 8;
		while (inside && (field[x][y].getPiece() == null || field[x][y].getPiece().getColor() != getColor())) {
			allAllowed.add(new Position(x, y));
			if (field[x][y].getPiece() != null) {
				break;
			}
			x--;
			y++;
		}

		// diagonal rechts unten
		x = curX + 1;
		y = curY - 1;
		inside = x < 8 && y >= 0;
		while (inside && (field[x][y].getPiece() == null || field[x][y].getPiece().getColor() != getColor())) {
			allAllowed.add(new Position(x, y));
			if (field[x][y].getPiece() != null) {
				break;
			}
			x++;
			y--;
		}

		// diagonal links unten
		x = curX - 1;
		y = curY - 1;
		inside = x >= 0 && y >= 0;
		while (inside && (field[x][y].getPiece() == null || field[x][y].getPiece().getColor() != getColor())) {
			allAllowed.add(new Position(x, y));
			if (field[x][y].getPiece() != null) {
				break;
			}
			x--;
			y--;
		}

		return allAllowed;
	}

}
