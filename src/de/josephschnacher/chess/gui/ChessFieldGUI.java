package de.josephschnacher.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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
import de.josephschnacher.chess.logic.Position;

public class ChessFieldGUI implements KeyListener {

	private JFrame frame;
	private JLabel[][] fields;
	private JPanel boardPanel;

	private static JButton forward;
	private static JButton backward;
	private JButton save;

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

		boardPanel = new JPanel();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setup(game, x, y, (screenSize.width - x) / 2, (screenSize.height - y) / 2);
	}

	public void setup(Game game, int width, int height, int locX, int locY) {
		frame.setTitle("Chess");
		frame.setSize(width, height);

		frame.setLocation(locX, locY);
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
				fields[i][j] = new JLabel((piece != null) ? piece.getUnicode() + "" : "", SwingConstants.CENTER);
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

		boardPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Position chessPos = getClickedChessPosition(e.getX(), e.getY());

				if (clickedPos == null) {
					if (selected) {
						removeColors();
						selected = false;
						return;
					}
					Piece curPiece = game.getGameBoard().get(chessPos).getPiece();
					if (curPiece != null) {
						setSelectedColor(toNormalPosition(chessPos));
						if (curPiece.getColor() == game.getCurColorPlaying()) {
							clickedPos = chessPos;
							setColors(game.getGameBoard(), toNormalPosition(chessPos));
						}
					}

				} else {
					removeColors();
					selected = false;
					boolean allowedMove = game.move(clickedPos.toChessPosition(), chessPos.toChessPosition());
					if (!allowedMove) {
						clickedPos = null;
					} else {
						if (firstMove) {
							backward.setForeground(Color.black);
							backward.setEnabled(true);
							firstMove = false;
						}
						update(game);
					}
				}
			}
		});

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
				}
				updateButtons(game);
				update(game);
			}
		});
		save = new JButton("Save");
		save.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				game.getHistory().save(game);
			}
		});

		JPanel buttonsPanel = new JPanel(new BorderLayout());
		JPanel forAndBackward = new JPanel(new GridLayout(1, 2));
		forAndBackward.add(backward);
		forAndBackward.add(forward);
		buttonsPanel.add(forAndBackward, BorderLayout.WEST);
		buttonsPanel.add(save, BorderLayout.EAST);

		JPanel wholePanel = new JPanel(new BorderLayout());
		wholePanel.add(boardPanel, BorderLayout.CENTER);
		wholePanel.add(buttonsPanel, BorderLayout.SOUTH);
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
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
