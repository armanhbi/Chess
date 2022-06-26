package de.josephschnacher.chess.figures;

import java.util.ArrayList;
import java.util.List;

import de.josephschnacher.chess.logic.PieceColor;
import de.josephschnacher.chess.logic.Field;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.Position;

public class Bishop extends Piece {

	public Bishop(Position pos, PieceColor color) {
		super(pos, color);
	}

	public Bishop(int x, int y, PieceColor color) {
		super(new Position(x, y), color);
	}
	
	@Override
	public char getShortName() {
		return 'B';
	}

	@Override
	public char getUnicode() {
		return (getColor() == PieceColor.WHITE) ? '♗' : '♝';
	}

	@Override
	public List<Position> getAllowedWithoutKing(GameBoard gameBoard) {
		List<Position> allAllowed = new ArrayList<>();
		int curX = getPosition().getX();
		int curY = getPosition().getY();
		Field[][] field = gameBoard.getField();

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
			inside = x < 8 && y < 8;
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
			inside = x >= 0 && y < 8;
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
			inside = x < 8 && y >= 0;
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
			inside = x >= 0 && y >= 0;
		}
		return allAllowed;
	}

}
