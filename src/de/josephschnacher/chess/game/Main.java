package de.josephschnacher.chess.game;

public class Main {

	public static void main(String[] args) {
		Game game = new Game();
		System.out.println(game.move("A2", "A4"));
		game.print();
	}

}
