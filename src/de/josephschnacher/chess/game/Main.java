package de.josephschnacher.chess.game;

public class Main {

	public static void main(String[] args) {
		Game game = new Game();
		game.print();
		
		System.out.println(game.move("A2", "A4"));
		game.print();
		
		//System.out.println(game.getGameBoard().get("A4").getPiece().getAllowed(game.getGameBoard()));
		
		System.out.println(game.move("H7", "H6"));
		game.print();
		
		System.out.println(game.move("A4", "A5"));
		game.print();
		
		System.out.println(game.move("B7", "B6"));
		game.print();
		
		System.out.println(game.getGameBoard().get("A5").getPiece().getAllowed(game.getGameBoard()));
		
		System.out.println(game.move("A5", "B6"));
		game.print();
		
		System.out.println(game.getGameBoard().get("B6").getPiece().getAllowed(game.getGameBoard()));
	
		System.out.println(game.move("H6", "H5"));
		game.print();
		
		System.out.println(game.move("B6", "A7"));
		game.print();
		
		System.out.println(game.move("A8", "A7"));
		game.print();
		
		System.out.println(game.getGameBoard().get("A7").getPiece().getAllowed(game.getGameBoard()));
		
	}

}
