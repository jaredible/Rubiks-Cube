package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Cube {
	private final int size;

	// face id's
	private static final int U = 0;
	private static final int D = 1;
	private static final int F = 2;
	private static final int B = 3;
	private static final int L = 4;
	private static final int R = 5;
	@SuppressWarnings("unused")
	private static final String[] faceStrings = { "Y", "W", "G", "B", "R", "O" };

	// cube facelets
	private static final int[][] cube = new int[6][9];

	// faces adjacent to each face
	private static final int[][] adjacentFaces = { //
			{ F, R, B, L }, // U: F R B L
			{ F, R, B, L }, // D: F R B L
			{ U, R, D, L }, // F: U R D L
			{ U, L, D, R }, // B: U L D R
			{ U, F, D, B }, // L: U F D B
			{ U, F, D, B }, // R: U F D B
	};

	public Cube(int size) {
		this.size = size;

		resetCube();
		// scrambleCube(0);
		// TODO: fix
		// execute(new int[] { R, -U });
	}

	private void resetCube() {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++)
				cube[i][j] = i;
	}

	@SuppressWarnings("unused")
	private void scrambleCube(long seed) {
		Random r = new Random(seed);
		int l = 15 + r.nextInt(16);
		for (int i = 0; i < l; i++)
			twist(r.nextInt(6), 1 - r.nextInt(2) * 2);
	}

	public void execute(int[] moves) {
		for (int i = 0; i < moves.length; i++)
			twist(Math.abs(moves[i]), moves[i] > 0 ? 1 : -1);
	}

	public static int[][] cloneArray(int[][] src) {
		int length = src.length;
		int[][] target = new int[length][src[0].length];
		for (int i = 0; i < length; i++)
			System.arraycopy(src[i], 0, target[i], 0, src[i].length);
		return target;
	}

	/**
	 * Returns true if twist has occurred, false if error. Rotate clockwise if direction == 1 else counterclockwise if direction == -1
	 **/
	public boolean twist(int face, int direction) {
		if (Math.abs(direction) != 1) {
			System.err.println("Direction can only be 1 or -1");
			return false;
		}
		if (face < 0 || face >= 6) {
			System.err.println("Face exists 0-5 inclusively");
			return false;
		}

		int[][] prev = cloneArray(cube);
		int i, j;

		// rotate face facelets
		int[][] ce = { { 0, 2, 8, 6 }, { 1, 5, 7, 3 } }; // corners, edges
		for (j = 0; j < 2; j++)
			for (i = 0; i < 4; i++)
				cube[face][ce[j][i]] = prev[face][ce[j][(2 + i + direction) % 4]];

		// TODO: condense

		// rotate faces adjacent faces facelets
		switch (face) {
		case U:
			int[] a = { 0, 0, 0, 0 };
			for (i = 0; i < size; i++)
				for (j = 0; j < 4; j++)
					cube[adjacentFaces[face][j]][a[j] + i * 1] = prev[adjacentFaces[face][(2 + j - direction) % 4]][a[j] + i * 1];
			break;
		case D:
			int[] b = { 6, 6, 6, 6 };
			for (i = 0; i < size; i++)
				for (j = 0; j < 4; j++)
					cube[adjacentFaces[face][j]][b[j] + i * 1] = prev[adjacentFaces[face][(2 + j + direction) % 4]][b[j] + i * 1];
			break;
		case F:
			int[] c = { 6, 0, 0, 2 };
			for (i = 0; i < size; i++)
				for (j = 0; j < 4; j++)
					cube[adjacentFaces[face][j]][c[j] + i * (1 + (j % 2) * 2)] = prev[adjacentFaces[face][(2 + j + direction) % 4]][c[(2 + j + direction) % 4] + i * (3 - (j % 2) * 2)];
			break;
		case B:
			int[] d = { 0, 0, 6, 2 };
			for (i = 0; i < size; i++)
				for (j = 0; j < 4; j++)
					cube[adjacentFaces[face][j]][d[j] + i * (1 + (j % 2) * 2)] = prev[adjacentFaces[face][(2 + j + direction) % 4]][d[(2 + j + direction) % 4] + i * (3 - (j % 2) * 2)];
			break;
		case L:
			int[] e = { 0, 0, 0, 2 };
			for (i = 0; i < size; i++)
				for (j = 0; j < 4; j++)
					cube[adjacentFaces[face][j]][e[j] + i * 3] = prev[adjacentFaces[face][(2 + j + direction) % 4]][e[(2 + j + direction) % 4] + i * 3];
			break;
		case R:
			// TODO: condense
			// int[] f = { 2, 2, 2, 6 };
			// for (i = 0; i < size; i++)
			// for (j = 0; j < 4; j++)
			// cube[adjacentFaces[face][j]][f[j] + i * 3] = prev[adjacentFaces[face][(2 + j - direction) % 4]][f[(2 + j - direction) % 4] + i * 3];
			if (direction == 1) {
				for (i = 0; i < size; i++) {
					cube[U][2 + i * 3] = prev[F][2 + i * 3];
					cube[F][2 + i * 3] = prev[D][2 + i * 3];
					cube[D][2 + i * 3] = prev[B][6 - i * 3];
					cube[B][6 - i * 3] = prev[U][2 + i * 3];
				}
			} else if (direction == -1) {
				for (i = 0; i < size; i++) {
					cube[U][2 + i * 3] = prev[B][6 - i * 3];
					cube[F][2 + i * 3] = prev[U][2 + i * 3];
					cube[D][2 + i * 3] = prev[F][2 + i * 3];
					cube[B][6 - i * 3] = prev[D][2 + i * 3];
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

	private static final boolean TEST = false;

	public int selectedFace = U;

	public void paint(Graphics g) {
		if (TEST) {

		} else {
			int i, j, k, n = 3, s = 20, padding = 5;
			int w = (n * s + padding * (n + 1)) * 3, h = (n * s + padding * (n + 1)) * 4;
			int x = (5 + 400 - w) / 2, y = (5 + 400 - h) / 2;
			int xo = 0, yo = 0;

			g.setColor(Color.GRAY);
			g.fillRect(x + xo + p[selectedFace][0] * (n * s + padding * (n + 1)), y + yo + p[selectedFace][1] * (n * s + padding * (n + 1)), n * s + padding * (n + 1), n * s + padding * (n + 1));

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
	}

	public int getSize() {
		return size;
	}
}
