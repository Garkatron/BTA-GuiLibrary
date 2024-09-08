package deus.guilib.interfaces.element;

/**
 * Interface for elements that require periodic updates to their state.
 */
public interface IUpdatable {

	/**
	 * Updates the state of the element.
	 * This method is typically called to refresh or recalculate the element's internal state.
	 */
	void update();
}
