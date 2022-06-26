package de.josephschnacher.chess.figures;

import java.util.ArrayList;
import java.util.List;

import de.josephschnacher.chess.logic.PieceColor;
import de.josephschnacher.chess.logic.Field;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.Position;

public class Rook extends Piece {

	public Rook(Position pos, PieceColor color) {
		super(pos, color);
	}

	public Rook(int x, int y, PieceColor color) {
		super(new Position(x, y), color);
	}
	
	@Override
	public char getShortName() {
		return 'R';
	}

	@Override
	public char getUnicode() {
		return (getColor() == PieceColor.WHITE) ? '♖' : '♜';
	}

	@Override
	public List<Position> getAllowedWithoutKing(GameBoard gameBoard) {
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
		return allAllowed;
	}

}
