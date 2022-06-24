package de.josephschnacher.chess.game;

import de.josephschnacher.chess.logic.Color;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.StringType;

public class Game {

	private final GameBoard gameBoard;
	private boolean whitePlaying;

	private final StringType DEFAULT_STRINGTYPE = StringType.ALPHABET;

	public Game() {
		gameBoard = new GameBoard();
		gameBoard.init();
		gameBoard.fillStart();
		whitePlaying = true;
	}

	public boolean move(String form, String to) {
		Color current = (whitePlaying) ? Color.WHITE : Color.BLACK;
		boolean success = gameBoard.move(form, to, current);
		if (success) {
			whitePlaying = !whitePlaying;
		}
		return success;
	}

	public boolean move(int fromX, int fromY, int toX, int toY) {
		Color current = (whitePlaying) ? Color.WHITE : Color.BLACK;
		boolean success = gameBoard.move(fromX, fromY, toX, toY, current);
		if (success) {
			whitePlaying = !whitePlaying;
		}
		return success;
	}

	public String toString(StringType type) {
		switch (type) {
		case NORMAL:
			return gameBoard.toString();
		case BIG:
			return gameBoard.toStringBig();
		case ALPHABET:
			return gameBoard.toStringAlphabet();
		default:
			return gameBoard.toString();
		}
	}

	public String toString() {
		return toString(DEFAULT_STRINGTYPE);
	}

	public void print(StringType type) {
		System.out.println(toString(type));
	}

	public void print() {
		System.out.println(toString(DEFAULT_STRINGTYPE));
	}

}
