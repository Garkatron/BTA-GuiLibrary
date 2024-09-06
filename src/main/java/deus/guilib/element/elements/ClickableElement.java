package deus.guilib.element.elements;

import deus.guilib.element.Element;
import deus.guilib.element.interfaces.element.IClickable;
import org.lwjgl.input.Mouse;

public abstract class ClickableElement extends Element implements IClickable {
	private boolean wasClicked = false;
	private boolean wasClickedOut = false;
	private int mx = 0;
	private int my = 0;

	@Override
	public boolean isHovered() {
		return mx >= x && my >= y && mx < x + getWidth() && my < y + getHeight();
	}

	@Override
	public void update(int mouseX, int mouseY) {
		this.mx = mouseX;
		this.my = mouseY;

		boolean hovered = isHovered();
		boolean buttonDown = Mouse.isButtonDown(0);

		if (hovered) {
			if (buttonDown) { // Left mouse button is pressed
				if (!wasClicked) { // Check if the button was not clicked in this update
					onPush(); // Execute onPush action
					wasClicked = true; // Mark as clicked
				}

				// Execute whilePressed action if button is pressed and held
				whilePressed();
				// Reset the wasClickedOut state when hovered
				wasClickedOut = false;
			} else {
				// Mouse was over the button but is no longer pressed
				if (wasClicked) {
					onRelease(); // Execute onRelease action if button was clicked and is now released
					wasClicked = false; // Reset clicked state
				}

				// Check if the button is still down but the mouse is not hovered
				if (buttonDown) {
					if (!wasClickedOut) {
						onPushOut(); // Execute onPushOut action
						wasClickedOut = true; // Mark as clicked outside
					}
				} else {
					// Reset the wasClickedOut state when button is released
					wasClickedOut = false;
				}
			}
		} else {
			// Mouse is not over the button
			if (buttonDown) {
				if (!wasClickedOut) {
					onPushOut(); // Execute onPushOut action
					wasClickedOut = true; // Mark as clicked outside
				}
			} else {
				// Reset both wasClicked and wasClickedOut when button is released
				wasClicked = false;
				wasClickedOut = false;
			}
		}
	}



}
