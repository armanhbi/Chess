package de.josephschnacher.chess.figures;

import java.util.List;

import de.josephschnacher.chess.logic.Color;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.Position;

public abstract class Piece {

	private final String name;
	private final char shortname;
	private final Color color;

	private Position position;

	public Piece(Position pos, char shortname, Color color) {
		this.position = pos;
		this.name = getClass().getSimpleName();
		this.shortname = shortname;
		this.color = color;
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

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public char getShortName() {
		return shortname;
	}

	public Color getColor() {
		return color;
	}

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
