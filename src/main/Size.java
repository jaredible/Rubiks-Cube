package main;

public enum Size {
	TWO(2), THREE(3);

	private final int size;

	private Size(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}
}
