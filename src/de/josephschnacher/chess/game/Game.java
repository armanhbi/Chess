package de.josephschnacher.chess.game;

import de.josephschnacher.chess.figures.Piece;
import de.josephschnacher.chess.gui.ChessFieldGUI;
import de.josephschnacher.chess.logic.Change;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.History;
import de.josephschnacher.chess.logic.Moveresult;
import de.josephschnacher.chess.logic.PieceColor;
import de.josephschnacher.chess.logic.Position;
import de.josephschnacher.chess.logic.StringType;

public class Game {

	private GameBoard gameBoard;
	private boolean whitePlaying;
	private History history;

	public Game() {
		gameBoard = new GameBoard();
		history = new History();
		whitePlaying = true;

		// fills the gameboard with all the chess pieces in the beginning
		gameBoard.init();
		gameBoard.fillStart();
		// logs no change -> aligning pointer and history order
		history.log(new Change(new Position(0, 0), new Position(0, 0), null));
	}

	public Moveresult move(String from, String to) {
		int[] fromArray = gameBoard.alphabetToInt(from);
		int[] toArray = gameBoard.alphabetToInt(to);
		return move(fromArray[0], fromArray[1], toArray[0], toArray[1]);
	}

	// does a move if the position is allowed, changes the current player, logs
	// change and updates the buttons if needed
	public Moveresult move(int fromX, int fromY, int toX, int toY) {
		Piece hit = gameBoard.get(toX, toY).getPiece();
		Moveresult result = gameBoard.move(fromX, fromY, toX, toY);
		if (result.getOk()) {
			whitePlaying = !whitePlaying;
			history.log(new Change(new Position(fromX, fromY), new Position(toX, toY), hit));
			ChessFieldGUI.updateButtons(this);
		}
		return result;
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
		return toString(Settings.DEFAULT_STRINGTYPE);
	}

	public void print(StringType type) {
		System.out.println(toString(type));
	}

	public void print() {
		System.out.println(toString(Settings.DEFAULT_STRINGTYPE));
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	public History getHistory() {
		return history;
	}

	public boolean isWhitePlaying() {
		return whitePlaying;
	}

	public void switchPlayer() {
		whitePlaying = !whitePlaying;
	}

	public void setWhitePlaying(boolean whitePlaying) {
		this.whitePlaying = whitePlaying;
	}

	public PieceColor getCurColorPlaying() {
		return (whitePlaying) ? PieceColor.WHITE : PieceColor.BLACK;
	}

}
