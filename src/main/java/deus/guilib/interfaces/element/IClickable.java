package deus.guilib.interfaces.element;

/**
 * Interface for handling clickable GUI elements, providing methods for interaction
 * such as pressing, releasing, and checking hover state.
 */
public interface IClickable {

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

	/**
	 * Updates the element's state based on the current mouse coordinates.
	 *
	 * @param mouseX The current X position of the mouse.
	 * @param mouseY The current Y position of the mouse.
	 */
	void update(int mouseX, int mouseY);
}
