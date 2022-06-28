package de.josephschnacher.chess.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class History {

	// logs changes and has a list of all the changes -> history
	// hit logs if a piece got hit in the last move

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

	// saves the gamestate to a file and loads it in later
	public void save() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Textfile (.chss)", "chss");
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle("Choose where you want to save your file");
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				FileWriter fw = new FileWriter(file);
				fw.write(pointer + "\n");
				System.out.println(pointer);
				System.out.println(history);
				for (int i = 0; i < pointer + 1; i++) {
					Change cur = history.get(i);
					String line = "";
					line += cur.getFrom().getX() + ";";
					line += cur.getFrom().getY() + "-";
					line += cur.getTo().getX() + ";";
					line += cur.getTo().getY();
					fw.write(line + "\n");
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String name = file.getName();
			if (name.length() < 4 || name.indexOf(".") == -1
					|| !name.substring(name.indexOf("."), name.length()).equals(".chss")) {
				Path source = Paths.get(file.getAbsolutePath());
				try {
					String fileName = file.getName();
					if (fileName.indexOf(".") != -1) {
						fileName = fileName.substring(0, fileName.indexOf("."));
					}
					Files.move(source, source.resolveSibling(fileName + ".chss"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public List<Change> load() {
		List<Change> changes = new ArrayList<>();
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Textfile (.chss)", "chss");
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle("Choose the file you want to load");
		int pointer = -1;
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String curLine;
				pointer = Integer.parseInt(br.readLine());
				while ((curLine = br.readLine()) != null) {
					Position from = new Position(Integer.parseInt(curLine.substring(0, 1)),
							Integer.parseInt(curLine.substring(2, 3)));
					Position to = new Position(Integer.parseInt(curLine.substring(4, 5)),
							Integer.parseInt(curLine.substring(6, 7)));
					changes.add(new Change(from, to, null));
				}
				br.close();
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		history = changes;
		this.pointer = pointer;
		return changes;
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
