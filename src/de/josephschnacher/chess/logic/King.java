package de.josephschnacher.chess.logic;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
	
	private final char SHORT_NAME = 'K';

	public King() {
	}

	public King(Position position) {
		super(position);
	}
	
	public King(int x, int y) {
		super(new Position(x, y));
	}
	
	@Override
	public char getShortName() {
		return SHORT_NAME;
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
					if (gameBoard.getField()[i][j].getPiece() == null) {
						allowed.add(new Position(i, j));
					}
				}
			}
		}
		return allowed;
	}

}
