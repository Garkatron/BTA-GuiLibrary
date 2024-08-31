package deus.guilib.math;

public class Tuple<A, B> {
	private final A first;
	private final B second;

	// Constructor
	public Tuple(A first, B second) {
		this.first = first;
		this.second = second;
	}

	// Getters
	public A getFirst() {
		return first;
	}

	public B getSecond() {
		return second;
	}

	// Métodos auxiliares (opcional)
	@Override
	public String toString() {
		return "Tupla{" +
			"first=" + first +
			", second=" + second +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Tuple<?, ?> tupla = (Tuple<?, ?>) o;

		if (!first.equals(tupla.first)) return false;
		return second.equals(tupla.second);
	}

	@Override
	public int hashCode() {
		int result = first.hashCode();
		result = 31 * result + second.hashCode();
		return result;
	}
}
