package de.josephschnacher.chess.main;

import java.util.List;

import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.King;
import de.josephschnacher.chess.logic.Position;
import de.josephschnacher.chess.logic.Queen;

public class Main {

	public static void main(String[] args) {
		King king = new King(3, 4);
		Queen queen = new Queen(new Position(3, 4));

		GameBoard gb = new GameBoard();

		gb.set(new King(3, 0), 3, 0);
		gb.set(new King(2, 0), 2, 0);
		gb.set(new King(4, 0), 4, 0);
		gb.set(new King(2, 1), 2, 1);
		gb.set(new King(3, 1), 3, 1);
		gb.set(new King(4, 1), 4, 1);

		System.out.println(gb.toString());

		List<Position> list = gb.getField()[3][0].getPiece().getAllowed(gb);
		System.out.println(list);
	}

}
