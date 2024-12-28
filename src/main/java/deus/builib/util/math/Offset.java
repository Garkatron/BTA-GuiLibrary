package deus.builib.util.math;

public enum Offset {
	CORNER_UP_LEFT(new Tuple<>(0, 0)),
	CORNER_DOWN_LEFT(new Tuple<>(0, 2)),
	CORNER_UP_RIGHT(new Tuple<>(2, 0)),
	CORNER_DOWN_RIGHT(new Tuple<>(2, 2)),
	CENTER(new Tuple<>(1, 1)),
	LEFT(new Tuple<>(0, 1)),
	RIGHT(new Tuple<>(2, 1)),
	UP(new Tuple<>(1, 0)),
	DOWN(new Tuple<>(1, 2));

	private final Tuple<Integer, Integer> offset;

	// Constructor
	Offset(Tuple<Integer, Integer> offset) {
		this.offset = offset;
	}

	// Getter
	public Tuple<Integer, Integer> getOffset() {
		return offset;
	}
}
