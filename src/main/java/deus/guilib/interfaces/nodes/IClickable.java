package deus.guilib.interfaces.nodes;

/**
 * Interface for handling clickable GUI elements, providing methods for interaction
 * such as pressing, releasing, and checking hover state.
 */
public interface IClickable extends IUpdatable {

	/**
	 * Called when the element is pressed.
	 */
	void onPush();

	/**
	 * Called when the mouse is released outside the element after being pressed.
	 */
	void onPushOut();

	/**
	 * Called when the element is released after being pressed.
	 */
	void onRelease();

	/**
	 * Called continuously while the element is being pressed.
	 */
	void whilePressed();

	/**
	 * Checks if the mouse is hovering over the element.
	 *
	 * @return true if the mouse is hovering, false otherwise.
	 */
	boolean isHovered();


}
