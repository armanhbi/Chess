package de.josephschnacher.chess.logic;

import java.util.ArrayList;
import java.util.List;

import de.josephschnacher.chess.game.Game;

public class History {

	private int pointer;
	private List<Change> history = new ArrayList<>();

	public History() {
		pointer = -1;
	}

	public Change next() {
		if (pointer < history.size() - 1) {
			pointer++;
			return history.get(pointer);
		}
		return null;
	}

	public Change previous() {
		if (pointer > 0) {
			pointer--;
			return history.get(pointer + 1);
		}
		return null;
	}

	public void log(Change change) {
		pointer++;
		if (pointer >= history.size()) {
			history.add(change);
		} else {
			history.set(pointer, change);
			removeFrom(pointer + 1);
		}
	}

	private void removeFrom(int pointer) {
		while (pointer < history.size()) {
			history.remove(pointer);
		}
	}

	public void save(Game game) {
		// save gamestate in a file and load later
	}

	public void load() {
		// load gamestate from a file
	}

	public int getPointer() {
		return pointer;
	}

	public int getSize() {
		return history.size();
	}

	@Override
	public String toString() {
		return "Pointer: " + pointer + "\n" + history;
	}
}
