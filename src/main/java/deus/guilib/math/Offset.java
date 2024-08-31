package deus.guilib.math;

public enum Offset {
	CORNER_UP_LEFT(new Tuple<>(0, 0)),
	CORNER_DOWN_LEFT(new Tuple<>(0, 64)),
	CORNER_UP_RIGHT(new Tuple<>(64, 0)),
	CORNER_DOWN_RIGHT(new Tuple<>(64, 64)),
	CENTER(new Tuple<>(32, 32)),
	LEFT(new Tuple<>(0, 32)),
	RIGHT(new Tuple<>(64, 32)),
	UP(new Tuple<>(32, 0)),
	DOWN(new Tuple<>(32, 64));

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
