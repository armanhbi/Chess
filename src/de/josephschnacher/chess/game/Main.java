package de.josephschnacher.chess.game;

import de.josephschnacher.chess.gui.ChessFieldGUI;

public class Main {

	public static void main(String[] args) {
		Game game = new Game();
		// game.print();
		new ChessFieldGUI(500, 500, game);
	}

}
