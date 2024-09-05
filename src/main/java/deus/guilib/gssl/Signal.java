package deus.guilib.gssl;

import java.util.ArrayList;
import java.util.List;

public class Signal<T> {
	private final List<Listener<T>> listeners = new ArrayList<>();

	public interface Listener<T> {
		void onEvent(T value);
	}

	public void connect(Listener<T> listener) {
		listeners.add(listener);
	}

	public void emit(T value) {
		for (Listener<T> listener : listeners) {
			listener.onEvent(value);
		}
	}
}
