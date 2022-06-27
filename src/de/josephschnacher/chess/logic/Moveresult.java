package de.josephschnacher.chess.logic;

import de.josephschnacher.chess.figures.Piece;

public class Moveresult {

	// returns hit if piece got hit, check if the king (of the color) is checked,
	// and if the move is allowed in general

	private Piece hit;
	private boolean check;
	private boolean ok;

	public Moveresult(Piece hit, boolean check, boolean ok) {
		this.check = check;
		this.hit = hit;
		this.ok = ok;
	}

	public Piece getHit() {
		return hit;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getOk() {
		return ok;
	}

	@Override
	public String toString() {
		String string = "";
		if (hit != null) {
			string += "Hit: " + hit.getName() + "";
		}
		string += "\nCheck: " + check;
		string += "\nOk: " + ok;
		return string;
	}

}
