package de.josephschnacher.chess.logic;

public class Field {

	private Piece piece;

	public Field(Piece piece) {
		this.piece = piece;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

}
