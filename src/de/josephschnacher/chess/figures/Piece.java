package de.josephschnacher.chess.figures;

import java.util.List;

import javax.swing.ImageIcon;

import de.josephschnacher.chess.logic.PieceColor;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.Position;

public abstract class Piece {

	private final String name;
	private final PieceColor color;
	private Position position;

	public Piece(Position pos, PieceColor color) {
		this.position = pos;
		this.name = getClass().getSimpleName();
		this.color = color;
	}

	public boolean isAllowed(GameBoard gameBoard, Position position) {
		List<Position> allAllowed = getAllowed(gameBoard);
		if (allAllowed.contains(position)) {
			return true;
		}
		return false;
	}
	
	public abstract List<Position> getAllowedWithoutKing(GameBoard gameBoard);

	public List<Position> getAllowed(GameBoard gameBoard) {
		List<Position> withoutKing = getAllowedWithoutKing(gameBoard);
		for (Position curPos : withoutKing) {
			if (gameBoard.get(curPos).getPiece() instanceof King) {
				withoutKing.remove(curPos);
				return withoutKing;
			}
		}
		return withoutKing;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public abstract char getShortName();

	public PieceColor getColor() {
		return color;
	}

	public abstract char getUnicode();

	public ImageIcon getIcon() {
		return new ImageIcon("rcs/" + getColor().toString().toUpperCase() + "_" + getName().toUpperCase() + ".png");
	}

	public String toString() {
		if (position == null) {
			return getName();
		} else {
			return getName() + "(" + getColor() + "): [" + getPosition().getX() + ", " + getPosition().getY() + "]";
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
