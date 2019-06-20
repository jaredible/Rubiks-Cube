package main;

public enum Face {
	UP(0, Color.WHITE), DOWN(1, Color.YELLOW), FRONT(2, Color.GREEN), BACK(3, Color.BLUE), LEFT(4, Color.ORANGE), RIGHT(5, Color.RED);

	private final int face;
	private final Color defaultColor;

	private Face(int face, Color defaultColor) {
		this.face = face;
		this.defaultColor = defaultColor;
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public int getFace() {
		return face;
	}
}
