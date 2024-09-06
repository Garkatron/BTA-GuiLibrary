package deus.guilib.element.elements;

import deus.guilib.element.Element;
import deus.guilib.element.interfaces.IClickable;
import org.lwjgl.input.Mouse;

public class DraggableElement extends Element implements IClickable {

	private boolean wasClicked = false;
	private int mx = 0;
	private int my = 0;

	@Override
	public void onPush() {

	}

	@Override
	public void onRelease() {

	}

	@Override
	public void whilePressed() {
		x = mx-(getWidth()/2);
		y = my-(getHeight()/2);
	}

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
				// Set texture to pressed if button is pressed
			} else {
				// Mouse was over the button but is no longer pressed
				if (wasClicked) {
					// Execute onRelease action if button was clicked and is now released
					onRelease(); // Execute onRelease action
				}
				// Update texture based on hover state
				wasClicked = false; // Reset clicked state
			}

			if (buttonDown) {
				whilePressed();
			}

		} else {
			// Mouse is not over the button

			wasClicked = false; // Reset clicked state

		}

		// Execute whilePressed action if the button is pressed and held

	}
}
