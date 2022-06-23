package de.josephschnacher.chess.logic;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

	private final char SHORT_NAME = 'Q';

	public Queen() {
	}

	public Queen(Position position) {
		super(position);
	}

	public Queen(int x, int y) {
		super(new Position(x, y));
	}

	@Override
	public char getShortName() {
		return SHORT_NAME;
	}

	@Override
	public List<Position> getAllowed(GameBoard gameBoard) {
		List<Position> allAllowed = new ArrayList<>();
		int curX = getPosition().getX();
		int curY = getPosition().getY();

		// hoch
		int y = curY + 1;
		while (y < 8 && gameBoard.getField()[curX][y] != null) {
			allAllowed.add(new Position(curX, y));
			y++;
		}

		// runter
		y = curY - 1;
		while (y >= 0 && gameBoard.getField()[curX][y] != null) {
			allAllowed.add(new Position(curX, y));
			y--;
		}
		
		// rechts
		int x = curX + 1;
		while (x < 8 && gameBoard.getField()[x][curY] != null) {
			allAllowed.add(new Position(x, curY));
			x++;
		}

		// links
		x = curX - 1;
		while (x >= 0 && gameBoard.getField()[x][curY] != null) {
			allAllowed.add(new Position(x, curY));
			x--;
		}
		
		// diagonal rechts
		

		return allAllowed;
	}

}
