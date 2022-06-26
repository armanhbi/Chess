package de.josephschnacher.chess.figures;

import java.util.ArrayList;
import java.util.List;

import de.josephschnacher.chess.logic.Color;
import de.josephschnacher.chess.logic.Field;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.Position;

public class Knight extends Piece {

	public Knight(Position pos, Color color) {
		super(pos, color);
	}

	public Knight(int x, int y, Color color) {
		super(new Position(x, y), color);
	}
	
	@Override
	public char getShortName() {
		return 'N';
	}
	
	@Override
	public char getUnicode() {
		return (getColor() == Color.WHITE) ? '♘' : '♞';
	}

	@Override
	public List<Position> getAllowedWithoutKing(GameBoard gameBoard) {

		List<Position> allAllowed = new ArrayList<>();
		Field[][] field = gameBoard.getField();

		// oben rechts und links
		Position p1 = new Position(getPosition().getX() + 1, getPosition().getY() + 2);
		Position p2 = new Position(getPosition().getX() - 1, getPosition().getY() + 2);

		// rechts oben und unten
		Position p3 = new Position(getPosition().getX() + 2, getPosition().getY() + 1);
		Position p4 = new Position(getPosition().getX() + 2, getPosition().getY() - 1);

		// unten rechts und links
		Position p5 = new Position(getPosition().getX() + 1, getPosition().getY() - 2);
		Position p6 = new Position(getPosition().getX() - 1, getPosition().getY() - 2);

		// links oben und unten
		Position p7 = new Position(getPosition().getX() - 2, getPosition().getY() + 1);
		Position p8 = new Position(getPosition().getX() - 2, getPosition().getY() - 1);

		Position[] ps = { p1, p2, p3, p4, p5, p6, p7, p8 };

		for (Position p : ps) {
			if (p.getX() >= 0 && p.getX() < 8 && p.getY() >= 0 && p.getY() < 8) {
				Piece piece = field[p.getX()][p.getY()].getPiece();
				if (piece == null || piece.getColor() != getColor()) {
					allAllowed.add(new Position(p.getX(), p.getY()));
				}
			}
		}

		return allAllowed;
	}

}
