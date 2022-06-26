package de.josephschnacher.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Taskbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.josephschnacher.chess.figures.Piece;
import de.josephschnacher.chess.game.Game;
import de.josephschnacher.chess.game.Settings;
import de.josephschnacher.chess.logic.Change;
import de.josephschnacher.chess.logic.GameBoard;
import de.josephschnacher.chess.logic.Moveresult;
import de.josephschnacher.chess.logic.PieceColor;
import de.josephschnacher.chess.logic.Position;

public class ChessFieldGUI implements KeyListener {

	private JFrame frame;
	private JLabel[][] fields;
	private JPanel boardPanel;

	private static JButton forward;
	private static JButton backward;
	private JButton save;
	private JLabel messages;

	private int x, y;
	private List<Position> colors;
	private boolean selected;
	private boolean firstMove;

	private Position clickedPos;

	public ChessFieldGUI(int x, int y, Game game) {
		this.x = x;
		this.y = y;
		this.selected = false;
		this.colors = new ArrayList<>();
		this.fields = new JLabel[8][8];
		this.frame = new JFrame();
		this.clickedPos = null;
		this.firstMove = true;
		this.messages = new JLabel("", SwingConstants.CENTER);

		boardPanel = new JPanel();

		setup(game, x, y);
	}

	public void setup(Game game, int width, int height) {
		frame.setTitle("Chess");
		frame.setSize(width, height);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		boardPanel.setLayout(new GridLayout(8, 8));

		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				Piece piece;
				Position chessPos = toChessPosition(new Position(i, j));
				if (game.getGameBoard().getField()[chessPos.getX()][chessPos.getY()].getPiece() != null) {
					piece = game.getGameBoard().getField()[chessPos.getX()][chessPos.getY()].getPiece();
				} else {
					piece = null;
				}
				fields[i][j] = new JLabel("", SwingConstants.CENTER);
				fields[i][j].setText((piece != null) ? piece.getUnicode() + "" : "");
				fields[i][j].setFont(new Font("Pecita", 0, 50));
				Color c = Settings.BLACK;
				if (((i + j) & 1) == 1) {
					c = Settings.WHITE;
				}
				fields[i][j].setOpaque(true);
				fields[i][j].setBackground(c);
				fields[i][j].setBounds(i * x, j * y, (int) x / fields.length, (int) y / fields[i].length);
				/*
				 * if (piece != null) { ImageIcon icon = new
				 * ImageIcon(piece.getIcon().getImage().getScaledInstance(fields[i][j].getSize()
				 * .width, fields[i][j].getSize().height, Image.SCALE_DEFAULT));
				 * fields[i][j].setIcon(icon); }
				 */
				boardPanel.add(fields[i][j]);
			}
		}

		/*
		 * backgroundPanel.setLayout(new GridLayout(8, 8)); JLabel[][] bgLabels = new
		 * JLabel[8][8]; for (int i = 0; i < fields.length; i++) { for (int j = 0; j <
		 * fields[i].length; j++) { bgLabels[i][j] = new JLabel(); Color c =
		 * Settings.BLACK; if (((i + j) & 1) == 1) { c = Settings.WHITE; }
		 * bgLabels[i][j].setOpaque(true); bgLabels[i][j].setBackground(c);
		 * bgLabels[i][j].setBounds(i * x, j * y, (int) x / bgLabels.length, (int) y /
		 * bgLabels[i].length); backgroundPanel.add(bgLabels[i][j]); } }
		 */

		boardPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Position chessPos = getClickedChessPosition(e.getX(), e.getY());

				if (clickedPos == null) {
					messages.setText("");
					if (selected) {
						removeColors();
						selected = false;
						return;
					}
					Piece curPiece = game.getGameBoard().get(chessPos).getPiece();
					if (curPiece != null) {
						messages.setText("");
						setSelectedColor(toNormalPosition(chessPos));
						if (curPiece.getColor() == game.getCurColorPlaying()) {
							clickedPos = chessPos;
							setColors(game.getGameBoard(), toNormalPosition(chessPos));
						} else {
							String color = (curPiece.getColor() == PieceColor.WHITE) ? "White" : "Black";
							messages.setText("It's not " + color + "'s turn!");
						}
					}

				} else {
					removeColors();
					selected = false;
					Moveresult result = game.move(clickedPos.toChessPosition(), chessPos.toChessPosition());
					if (!result.getOk()) {
						clickedPos = null;
						String message = "This position is not allowed";
						if (result.getCheck()) {
							if (game.getGameBoard().isWhiteCheck()) {
								message = "White is still or gets checked...";
							} else if (game.getGameBoard().isBlackCheck()) {
								message = "Black is still or gets checked...";
							}

						}
						messages.setText(message);
					} else {
						if (firstMove) {
							backward.setForeground(Color.black);
							backward.setEnabled(true);
							firstMove = false;
						}
						String message = clickedPos.toChessPosition() + " on " + chessPos.toChessPosition();
						if (result.getHit() != null) {
							message += " (" + result.getHit().getName() + " got hit!)";
						}
						game.getGameBoard().checkForCheck();
						if (game.getGameBoard().isWhiteCheck()) {
							message += " => White is CHECKED!";
						} else if (game.getGameBoard().isBlackCheck()) {
							message += " => Black is CHECKED!";
						}
						messages.setText(message);
						update(game);
					}
				}
			}
		});

		// first idea of a dragging element
		/*
		 * final MouseAdapter dragger = new MouseAdapter() { private JLabel
		 * selectedLabel = null; // Clicked label. private Point selectedLabelLocation =
		 * null; // Location of label in panel when it was clicked. private Point
		 * panelClickPoint = null; // Panel's click point.
		 * 
		 * // Selection of label occurs upon pressing on the panel:
		 * 
		 * @Override public void mousePressed(final MouseEvent e) {
		 * 
		 * // Find which label is at the press point: final Component pressedComp =
		 * boardPanel.findComponentAt(e.getX(), e.getY());
		 * 
		 * double squareWidth = boardPanel.getSize().getWidth() / 8; double squareHeight
		 * = boardPanel.getSize().getHeight() / 8;
		 * 
		 * int labelX = (int) (e.getX() / squareWidth); int labelY = (int) (e.getY() /
		 * squareHeight);
		 * 
		 * Position clickedPos = new Position(labelX, labelY); frame.setTitle("" +
		 * clickedPos); // setColors(game.getGameBoard(), clickedPos);
		 * 
		 * // If a label is pressed, store it as selected: if (pressedComp != null &&
		 * pressedComp instanceof JLabel pressedLabel) { selectedLabel = pressedLabel;
		 * selectedLabelLocation = selectedLabel.getLocation(); panelClickPoint =
		 * e.getPoint();
		 * 
		 * boardPanel.setComponentZOrder(selectedLabel, 0); selectedLabel.repaint(); }
		 * else { selectedLabel = null; selectedLabelLocation = null; panelClickPoint =
		 * null; } }
		 * 
		 * @Override public void mouseDragged(final MouseEvent e) { if (selectedLabel !=
		 * null && selectedLabelLocation != null && panelClickPoint != null) {
		 * 
		 * final Point newPanelClickPoint = e.getPoint();
		 * 
		 * int newX = selectedLabelLocation.x + (newPanelClickPoint.x -
		 * panelClickPoint.x); int newY = selectedLabelLocation.y +
		 * (newPanelClickPoint.y - panelClickPoint.y);
		 * 
		 * double squareWidth = boardPanel.getSize().getWidth() / 8; double squareHeight
		 * = boardPanel.getSize().getHeight() / 8;
		 * 
		 * int labelX = (int) ((newX + squareWidth / 2) / squareWidth); int labelY =
		 * (int) ((newY + squareHeight / 2) / squareHeight);
		 * 
		 * selectedLabel.setLocation((int) (labelX * squareWidth), (int) (labelY *
		 * squareHeight)); } }
		 * 
		 * @Override public void mouseReleased(final MouseEvent e) {
		 * System.out.println("released"); update(game); }
		 * 
		 * };
		 * 
		 * 
		 * boardPanel.addMouseMotionListener(dragger);
		 * boardPanel.addMouseListener(dragger);
		 */

		backward = new JButton("<");
		forward = new JButton(">");
		forward.setForeground(Color.GRAY);
		forward.setEnabled(false);
		backward.setForeground(Color.GRAY);
		backward.setEnabled(false);
		backward.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Change change = game.getHistory().previous();
				if (change != null) {
					game.getGameBoard().backwardMove(change);
					game.switchPlayer();
					String message = "Step backward";
					if (game.isWhitePlaying()) {
						message += " -> It's white's turn!";
					} else {
						message += " -> It's black's turn!";
					}
					messages.setText(message);
					if (game.getHistory().getPointer() == 0) {
						firstMove = true;
					}
				}
				updateButtons(game);
				update(game);
			}
		});
		forward.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Change change = game.getHistory().next();
				if (change != null) {
					game.getGameBoard().forwardMove(change);
					game.switchPlayer();
					String message = "Step forward";
					if (game.isWhitePlaying()) {
						message += " -> It's white's turn!";
					} else {
						message += " -> It's black's turn!";
					}
					messages.setText(message);
				}
				updateButtons(game);
				update(game);
			}
		});
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.getHistory().save(game);
				ImageIcon icon = new ImageIcon("rcs/icon2.png");
				frame.setIconImage(icon.getImage());
				try {
					Taskbar.getTaskbar().setIconImage(icon.getImage());
				} catch (Exception x) {

				}
				messages.setText("You have found the ultimate easter egg!!!");
			}
		});

		JPanel buttonsPanel = new JPanel(new BorderLayout());
		JPanel forAndBackward = new JPanel(new GridLayout(1, 2));
		forAndBackward.add(backward);
		forAndBackward.add(forward);
		buttonsPanel.add(forAndBackward, BorderLayout.WEST);
		buttonsPanel.add(messages, BorderLayout.CENTER);
		buttonsPanel.add(save, BorderLayout.EAST);

		JPanel wholePanel = new JPanel(new BorderLayout());
		// backgroundPanel.add(boardPanel, JLabel.NORTH);
		// wholePanel.add(backgroundPanel, BorderLayout.CENTER);
		wholePanel.add(boardPanel, BorderLayout.CENTER);
		wholePanel.add(buttonsPanel, BorderLayout.SOUTH);

		ImageIcon icon = new ImageIcon("rcs/icon2.png");
		frame.setIconImage(icon.getImage());
		try {
			Taskbar.getTaskbar().setIconImage(icon.getImage());
		} catch (Exception e) {

		}

		frame.getContentPane().add(wholePanel);
		frame.addKeyListener(this);
		frame.setFocusable(true);
		frame.setFocusTraversalKeysEnabled(false);
		frame.setVisible(true);
	}

	public void update(Game game) {
		clickedPos = null;
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				Piece piece;
				if (game.getGameBoard().getField()[j][7 - i].getPiece() != null) {
					piece = game.getGameBoard().getField()[j][7 - i].getPiece();
				} else {
					piece = null;
				}
				fields[i][j].setText((piece != null) ? piece.getUnicode() + "" : "");
				// fields[i][j].setIcon(piece.getIcon());
			}
		}
	}

	public static void updateButtons(Game game) {
		int pointer = game.getHistory().getPointer();
		if (pointer == 0) {
			backward.setForeground(Color.GRAY);
			backward.setEnabled(false);
		} else {
			backward.setForeground(Color.BLACK);
			backward.setEnabled(true);
		}
		if (pointer == game.getHistory().getSize() - 1) {
			forward.setForeground(Color.GRAY);
			forward.setEnabled(false);
		} else {
			forward.setForeground(Color.BLACK);
			forward.setEnabled(true);
		}
	}

	public void setColors(GameBoard gameBoard, Position normalPos) {
		setSelectedColor(normalPos);

		Position chessPosition = toChessPosition(normalPos);
		List<Position> allowed = gameBoard.get(chessPosition).getPiece().getAllowed(gameBoard);
		for (Position pos : allowed) {
			Position toNormal = toNormalPosition(pos);
			fields[toNormal.getX()][toNormal.getY()].setBackground(Settings.ALLOWED);
			colors.add(toNormal);
		}
	}

	public void setSelectedColor(Position normalPos) {
		fields[normalPos.getX()][normalPos.getY()].setBackground(Settings.SELECTED);
		colors.add(normalPos);
		selected = true;
	}

	public void removeColors() {
		for (Position pos : colors) {
			Color background = ((((pos.getX() + pos.getY()) & 1) == 0)) ? Settings.BLACK : Settings.WHITE;
			fields[pos.getX()][pos.getY()].setBackground(background);
		}
		colors = new ArrayList<>();
	}

	public Position toNormalPosition(Position pos) {
		return new Position(7 - pos.getY(), pos.getX());
	}

	public Position toChessPosition(Position pos) {
		return new Position(pos.getY(), 7 - pos.getX());
	}

	public Position getClickedChessPosition(int clickedX, int clickedY) {
		int fieldX = (int) (boardPanel.getSize().getWidth() / 8);
		int fieldY = (int) (boardPanel.getSize().getHeight() / 8);

		int x = clickedX / fieldX;
		int y = clickedY / fieldY;

		return new Position(x, 7 - y);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			backward.doClick();
		} else if (e.getKeyCode() == 39) {
			forward.doClick();
		} else if (e.getKeyCode() == 10) {
			save.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
