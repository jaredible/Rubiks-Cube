package main;

import java.util.Random;

public class Cube2 {
	private final Size size;
	private final Color[][] cubies;
	private Face currentFace = Face.FRONT;

	public Cube2(Size size) {
		this.size = size;
		cubies = new Color[Face.values().length][size.getSize() * size.getSize()];
		setup();
	}

	public void setup() {
		for (int i = 0; i < cubies.length; i++) {
			for (int j = 0; j < size.getSize() * size.getSize(); j++) {
				cubies[i][j] = Face.values()[i].getDefaultColor();
			}
		}
	}

	public void twist(Face face, Direction direction) {
		Color[][] previousCubies = cubies.clone();

		switch (direction) {
		case CW:
			if (size == Size.THREE) {
				// rotate 4 edges
				cubies[face.getFace()][1] = previousCubies[face.getFace()][3];
				cubies[face.getFace()][5] = previousCubies[face.getFace()][1];
				cubies[face.getFace()][7] = previousCubies[face.getFace()][5];
				cubies[face.getFace()][3] = previousCubies[face.getFace()][7];
			}
			// rotate 4 corners
			cubies[face.getFace()][0] = previousCubies[face.getFace()][6];
			cubies[face.getFace()][2] = previousCubies[face.getFace()][0];
			cubies[face.getFace()][8] = previousCubies[face.getFace()][2];
			cubies[face.getFace()][6] = previousCubies[face.getFace()][8];
			break;
		case CCW:
			if (size == Size.THREE) {
				// rotate 4 edges
				cubies[face.getFace()][1] = previousCubies[face.getFace()][5];
				cubies[face.getFace()][5] = previousCubies[face.getFace()][7];
				cubies[face.getFace()][7] = previousCubies[face.getFace()][3];
				cubies[face.getFace()][3] = previousCubies[face.getFace()][1];
			}
			// rotate 4 corners
			cubies[face.getFace()][0] = previousCubies[face.getFace()][2];
			cubies[face.getFace()][2] = previousCubies[face.getFace()][8];
			cubies[face.getFace()][8] = previousCubies[face.getFace()][6];
			cubies[face.getFace()][6] = previousCubies[face.getFace()][0];
			break;
		default:
			break;
		}

		switch (face) {
		case UP:
			break;
		case DOWN:
			break;
		case FRONT:
			switch (direction) {
			case CW:
				if (size == Size.THREE) {
					// cycle 4 edges
					cubies[Face.UP.getFace()][7] = previousCubies[Face.LEFT.getFace()][5];
					cubies[Face.RIGHT.getFace()][3] = previousCubies[Face.UP.getFace()][7];
					cubies[Face.DOWN.getFace()][1] = previousCubies[Face.RIGHT.getFace()][3];
					cubies[Face.LEFT.getFace()][5] = previousCubies[Face.DOWN.getFace()][1];
				}
				// cycle 8 corners
				cubies[Face.UP.getFace()][6] = previousCubies[Face.LEFT.getFace()][8];
				cubies[Face.UP.getFace()][8] = previousCubies[Face.LEFT.getFace()][2];
				cubies[Face.RIGHT.getFace()][0] = previousCubies[Face.UP.getFace()][6];
				cubies[Face.RIGHT.getFace()][6] = previousCubies[Face.UP.getFace()][8];
				cubies[Face.DOWN.getFace()][0] = previousCubies[Face.RIGHT.getFace()][6];
				cubies[Face.DOWN.getFace()][2] = previousCubies[Face.RIGHT.getFace()][0];
				cubies[Face.LEFT.getFace()][2] = previousCubies[Face.DOWN.getFace()][0];
				cubies[Face.LEFT.getFace()][8] = previousCubies[Face.DOWN.getFace()][2];
				break;
			case CCW:
				if (size == Size.THREE) {
					// cycle 4 edges
				}
				// cycle 8 corners
				break;
			default:
				break;
			}
			break;
		case BACK:
			break;
		case LEFT:
			break;
		case RIGHT:
			break;
		default:
			break;
		}
	}

	public void scramble(long seed) {
		Random random = new Random(seed);
		for (int i = 0; i < 15 + random.nextInt(16); i++) {
			twist(Face.values()[random.nextInt(6)], Direction.values()[1 - random.nextInt(2) * 2]); // TODO
		}
	}

	public void execute(Direction[] directions) {
	}

	public void setCurrentFace(Face face) {
		currentFace = face;
	}

	public Face getCurrentFace() {
		return currentFace;
	}

	public Color[][] getCubies() {
		return cubies;
	}
}
