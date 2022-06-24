package de.josephschnacher.chess.figures;

import java.util.ArrayList;
import java.util.List;

import de.josephschnacher.chess.logic.Color;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.Position;

public class King extends Piece {

	public King(Position position, Color color) {
		super(position, 'K', color);
	}

	public King(int x, int y, Color color) {
		super(new Position(x, y), 'K', color);
	}

	@Override
	public List<Position> getAllowed(GameBoard gameBoard) {
		List<Position> allowed = new ArrayList<>();
		Position curPosition = getPosition();
		int curX = curPosition.getX();
		int curY = curPosition.getY();

		for (int i = curX - 1; i <= curX + 1; i++) {
			for (int j = curY - 1; j <= curY + 1; j++) {
				if (i >= 0 && i < 8 && j >= 0 && j < 8) {
					Piece piece = gameBoard.getField()[i][j].getPiece();
					if (piece == null || piece.getColor() != getColor()) {
						allowed.add(new Position(i, j));
						if (piece != null) {
							break;
						}
					}
				}
			}
		}
		return allowed;
	}

}
