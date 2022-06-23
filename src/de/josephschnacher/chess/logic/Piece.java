package de.josephschnacher.chess.logic;

import java.util.List;

public abstract class Piece {

	private String name;
	private Position position;

	public Piece() {
		this.name = getClass().getSimpleName();
	}

	public Piece(Position pos) {
		this.position = pos;
		this.name = getClass().getSimpleName();
	}

	public boolean isAllowed(GameBoard gameBoard, Position position) {
		List<Position> allAllowed = getAllowed(gameBoard);
		if (allAllowed.contains(position)) {
			return true;
		}
		return false;
	}

	public abstract List<Position> getAllowed(GameBoard gameBoard);

	public Position getPosition() {
		return position;
	}

	public String getName() {
		return name;
	}
	
	public abstract char getShortName();

	public String toString() {
		if (position == null) {
			return getName();
		} else {
			return getName() + ": (" + getPosition().getX() + ", " + getPosition().getY() + ")";
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Piece piece) {
			if (piece.getPosition().equals(piece.getPosition()) && piece.getClass().equals(getClass())) {
				return true;
			}
		}
		return false;
	}

}
