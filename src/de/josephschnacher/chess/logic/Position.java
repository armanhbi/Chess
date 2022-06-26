package de.josephschnacher.chess.logic;

public class Position {

	private int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void set(Position position) {
		this.x = position.getX();
		this.y = position.getY();
	}

	public String toChessPosition() {
		char letter = (char) (x + 65);
		int number = y + 1;
		return letter + "" + number;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Position position) {
			if (x == position.getX() && y == position.getY()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
		//return toChessPosition();
	}

}
