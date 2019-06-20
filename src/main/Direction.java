package main;

public enum Direction {
	N(0), CW(1), CCW(-1);

	private final int direction;

	private Direction(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}
}
