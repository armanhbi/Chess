package de.josephschnacher.chess.logic;

import de.josephschnacher.chess.figures.Piece;

public class Change {

	private Position from;
	private Position to;
	private Piece hit;

	public Change(Position from, Position to, Piece hit) {
		this.from = from;
		this.to = to;
		this.hit = hit;
	}

	public Position getFrom() {
		return from;
	}

	public Position getTo() {
		return to;
	}

	public Piece getHit() {
		return hit;
	}

	@Override
	public String toString() {
		return from + " -> " + to;
	}

}
