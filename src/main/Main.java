package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;

public class Main extends Canvas implements KeyListener {
	private static final long serialVersionUID = 1L;

	private static final int WINDOW_WIDTH = 400;
	private static final int WINDOW_HEIGHT = 400;
	private static final String WINDOW_TITLE = "Rubik's Cube Simulator v3";
	private static final int N = 3;

	// face id's
	/** facelet value = 10 **/
	private static final int U = 0;
	/** facelet value = 20 **/
	private static final int D = 1;
	/** facelet value = 30 **/
	private static final int F = 2;
	/** facelet value = 40 **/
	private static final int B = 3;
	/** facelet value = 50 **/
	private static final int L = 4;
	/** facelet value = 60 **/
	private static final int R = 5;

	// cube facelets
	private static final int[][] cube = new int[6][9];

	// faces adjacent to each face
	private static final int[][] adjacentFaces = { //
			{ F, R, B, L }, // U: F R B L
			{ L, B, R, F }, // D: L B R F
			{ L, D, R, U }, // F: L D R U
			{ R, D, L, U }, // B: R D L U
			{ U, B, D, F }, // L: U B D F
			{ F, D, B, U }, // R: F D B U
	};

	public Main() {
		addKeyListener(this);
	}

	private void resetCube() {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++)
				cube[i][j] = i;
	}

	private void scrambleCube(long seed) {
		Random r = new Random(seed);
		int l = 15 + r.nextInt(16);
		for (int i = 0; i < l; i++)
			twist(r.nextInt(6), 1 - r.nextInt(2) * 2);
	}

	public static int[][] cloneArray(int[][] src) {
		int length = src.length;
		int[][] target = new int[length][src[0].length];
		for (int i = 0; i < length; i++)
			System.arraycopy(src[i], 0, target[i], 0, src[i].length);
		return target;
	}

	// TODO: add cube rotations
	// TODO: add optional mouse selecting
	// TODO: add algorithm execution
	// TODO: 2.5D?
	// TODO: 3D!?
	// TODO: add solving capability and output solution

	private void a(int[][] src, int face, int direction) {
		int i;
		int[] c = { 0, 2, 8, 6 };
		int[] e = { 1, 5, 7, 3 };

		if (direction == 1) {
			// rotate corners
			for (i = 0; i < c.length; i++)
				cube[face][c[i]] = src[face][c[(i + c.length - 1) % c.length]];

			// rotate edges
			for (i = 0; i < e.length; i++)
				cube[face][e[i]] = src[face][e[(i + e.length - 1) % e.length]];
		} else if (direction == -1) {
			// rotate corners
			for (i = 0; i < c.length; i++)
				cube[face][c[i]] = src[face][c[(i + 1) % c.length]];

			// rotate edges
			for (i = 0; i < e.length; i++)
				cube[face][e[i]] = src[face][e[(i + 1) % e.length]];
		}
	}

	/**
	 * Returns true if twist has occurred, false if error. Rotate clockwise if direction == 1 else counterclockwise if direction == -1
	 **/
	private boolean twist(int face, int direction) {
		if (Math.abs(direction) != 1) {
			System.err.println("Direction can only be 1 or -1");
			return false;
		}
		if (face < 0 || face >= 6) {
			System.err.println("Face exists 0-5 inclusively");
			return false;
		}

		int[][] prev = cloneArray(cube);

		int i;

		a(prev, face, direction);

		switch (face) {
		case U:
			if (direction == 1) {
				// rotate outer facelets
				for (i = 0; i < N; i++) {
					cube[F][i] = prev[R][i];
					cube[R][i] = prev[B][i];
					cube[B][i] = prev[L][i];
					cube[L][i] = prev[F][i];
				}
			} else if (direction == -1) {
				// rotate outer facelets
				for (i = 0; i < 3; i++) {
					cube[F][i] = prev[L][i];
					cube[R][i] = prev[F][i];
					cube[B][i] = prev[R][i];
					cube[L][i] = prev[B][i];
				}
			}
			break;
		case D:
			if (direction == 1) {
				for (i = 6; i < 9; i++) {
					cube[F][i] = prev[L][i];
					cube[R][i] = prev[F][i];
					cube[B][i] = prev[R][i];
					cube[L][i] = prev[B][i];
				}
			} else if (direction == -1) {
				for (i = 6; i < 9; i++) {
					cube[F][i] = prev[R][i];
					cube[R][i] = prev[B][i];
					cube[B][i] = prev[L][i];
					cube[L][i] = prev[F][i];
				}
			}
			break;
		case F:
			if (direction == 1) {
				for (i = 0; i < 3; i++) {
					cube[U][6 + i] = prev[L][2 + i * 3];
					cube[R][i * 3] = prev[U][6 + i];
					cube[D][i] = prev[R][i * 3];
					cube[L][2 + i * 3] = prev[D][i];
				}
			} else if (direction == -1) {
				for (i = 0; i < 3; i++) {
					cube[U][6 + i] = prev[R][i * 3];
					cube[R][i * 3] = prev[D][i];
					cube[D][i] = prev[L][2 + i * 3];
					cube[L][2 + i * 3] = prev[U][6 + i];
				}
			}
			break;
		case B:
			if (direction == 1) {
				for (i = 0; i < 3; i++) {
					cube[U][i] = prev[R][2 + i * 3];
					cube[L][i * 3] = prev[U][i];
					cube[D][6 + i] = prev[L][i * 3];
					cube[R][2 + i * 3] = prev[D][6 + i];
				}
			} else if (direction == -1) {
				for (i = 0; i < 3; i++) {
					cube[U][i] = prev[L][i * 3];
					cube[L][i * 3] = prev[D][6 + i];
					cube[D][6 + i] = prev[R][2 + i * 3];
					cube[R][2 + i * 3] = prev[U][i];
				}
			}
			break;
		case L:
			if (direction == 1) {
				for (i = 0; i < 3; i++) {
					cube[U][i * 3] = prev[B][2 + i * 3];
					cube[F][i * 3] = prev[U][i * 3];
					cube[D][i * 3] = prev[F][i * 3];
					cube[B][2 + i * 3] = prev[D][i * 3];
				}
			} else if (direction == -1) {
				for (i = 0; i < 3; i++) {
					cube[U][i * 3] = prev[F][i * 3];
					cube[F][i * 3] = prev[D][i * 3];
					cube[D][i * 3] = prev[B][2 + i * 3];
					cube[B][2 + i * 3] = prev[U][i * 3];
				}
			}
			break;
		case R:
			if (direction == 1) {
				for (i = 0; i < 3; i++) {
					cube[U][2 + i * 3] = prev[F][2 + i * 3];
					cube[F][2 + i * 3] = prev[D][2 + i * 3];
					cube[D][2 + i * 3] = prev[B][i * 3];
					cube[B][i * 3] = prev[U][2 + i * 3];
				}
			} else if (direction == -1) {
				for (i = 0; i < 3; i++) {
					cube[U][2 + i * 3] = prev[B][i * 3];
					cube[F][2 + i * 3] = prev[U][2 + i * 3];
					cube[D][2 + i * 3] = prev[F][2 + i * 3];
					cube[B][i * 3] = prev[D][2 + i * 3];
				}
			}
			break;
		default:
			break;
		}

		return true;
	}

	Color[] c = { Color.YELLOW, Color.WHITE, Color.GREEN, Color.BLUE, Color.RED, new Color(255, 127, 0) };
	int[][] p = { { 1, 0 }, { 1, 2 }, { 1, 1 }, { 1, 3 }, { 0, 1 }, { 2, 1 } };

	public void paint(Graphics g) {
		int xx = (getWidth() - WINDOW_WIDTH) / 2;
		int yy = (getHeight() - WINDOW_HEIGHT) / 2;
		g.setColor(Color.BLACK);
		g.fillRect(xx, yy, WINDOW_WIDTH, WINDOW_HEIGHT);

		int i, j, k, n = 3, s = 20, padding = 5;
		int w = (n * s + padding * (n + 1)) * 3, h = (n * s + padding * (n + 1)) * 4;
		int x = (xx + WINDOW_WIDTH - w) / 2, y = (yy + WINDOW_HEIGHT - h) / 2;
		int xo = 0, yo = 0;

		g.setColor(Color.GRAY);
		g.fillRect(x + xo + p[selectedFace][0] * (n * s + padding * (n + 1)), y + yo + p[selectedFace][1] * (n * s + padding * (n + 1)),
				n * s + padding * (n + 1), n * s + padding * (n + 1));

		xo += n * s + padding * (n + 1);

		// draw U, D face
		for (k = 0; k < 2; k++, yo += (n * s + padding * (n + 1)) * 2) {
			for (j = 0; j < n; j++) { // vertical
				for (i = 0; i < n; i++) { // horizontal
					g.setColor(c[cube[k][i + j * n]]);
					g.fillRect(x + xo + (s + padding) * i + padding, y + yo + (s + padding) * j + padding, s, s);
				}
			}
		}

		xo -= n * s + padding * (n + 1);
		yo -= (n * s + padding * (n + 1)) * 3;

		// draw L, F, R faces
		int[] a = { L, F, R };
		for (k = 0; k < 3; k++) {
			for (j = 0; j < n; j++) { // vertical
				for (i = 0; i < n; i++) { // horizontal
					g.setColor(c[cube[a[k]][i + j * n]]);
					g.fillRect(x + xo + (s + padding) * i + padding, y + yo + (s + padding) * j + padding, s, s);
				}
			}
			xo += n * s + padding * (n + 1);
		}

		xo -= (n * s + padding * (n + 1)) * 1;
		yo += (n * s + padding * (n + 1)) * 3;

		// draw B face
		for (j = n - 1; j >= 0; j--) { // vertical
			for (i = n - 1; i >= 0; i--) { // horizontal
				g.setColor(c[cube[B][i + j * n]]);
				g.fillRect(x + xo - ((s + padding) * i + padding) - s, y + yo - ((s + padding) * j + padding) - s, s, s);
			}
		}
	}

	public void update(Graphics g) {
		paint(g);
	}

	private void printCube() {
		int i, j, k;

		System.out.println();

		// show U face
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				if (j == 1) {
					for (k = 0; k < 3; k++) {
						System.out.print(" " + cube[U][k + i * 3] + " ");
					}
				} else {
					for (k = 0; k < 3; k++) {
						System.out.print(" " + " " + " ");
					}
				}
				System.out.print(" ");
			}
			System.out.println();
		}

		System.out.println();

		// show L, F, R faces
		int[] a = { L, F, R };
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				for (k = 0; k < 3; k++) {
					System.out.print(" " + cube[a[j]][k + i * 3] + " ");
				}
				System.out.print(" ");
			}
			System.out.println();
		}

		System.out.println();

		// show D face
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				if (j == 1) {
					for (k = 0; k < 3; k++) {
						System.out.print(" " + cube[D][k + i * 3] + " ");
					}
				} else {
					for (k = 0; k < 3; k++) {
						System.out.print(" " + " " + " ");
					}
				}
				System.out.print(" ");
			}
			System.out.println();
		}

		System.out.println();

		// show B face
		for (i = 2; i >= 0; i--) {
			for (j = 0; j < 3; j++) {
				if (j == 1) {
					for (k = 2; k >= 0; k--) {
						System.out.print(" " + cube[B][k + i * 3] + " ");
					}
				} else {
					for (k = 2; k >= 0; k--) {
						System.out.print(" " + " " + " ");
					}
				}
				System.out.print(" ");
			}
			System.out.println();
		}

		System.out.println();
	}

	public static void main(String[] args) {
		Main main = new Main();
		Dimension dim = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
		main.setMinimumSize(dim);
		main.setMaximumSize(dim);
		main.setPreferredSize(dim);

		JFrame frame = new JFrame(WINDOW_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(main, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		frame.setAlwaysOnTop(true);

		main.resetCube();
		// main.scrambleCube(0);
		main.printCube();
	}

	private int selectedFace = U;

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() > 48 && e.getKeyCode() <= 54) {
			selectedFace = e.getKeyCode() - 48 - 1;
			System.out.println("Selected face: " + selectedFace);
		} else if (Math.abs(e.getKeyCode() - 38) == 1) twist(selectedFace, e.getKeyCode() - 38);
		printCube();
		repaint();
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
