package deus.guilib.nodes.types.interaction;

import deus.guilib.nodes.types.templates.ClickableElement;
import deus.guilib.util.GuiHelper;
import org.lwjgl.input.Mouse;

public class DraggableElement extends ClickableElement {

	private boolean wasClicked = false;
	protected boolean lockedY, lockedX = false;

	public DraggableElement() {
		super();
	}

	public boolean isLockedX() {
		return lockedX;
	}

	public boolean isLockedY() {
		return lockedY;
	}

	public DraggableElement setLockedX(boolean lockedX) {
		this.lockedX = lockedX;
		return this;
	}

	public DraggableElement setLockedY(boolean lockedY) {
		this.lockedY = lockedY;
		return this;
	}

	@Override
	public void onPush() {

	}

	@Override
	public void onPushOut() {

	}

	@Override
	public void onRelease() {

	}

	protected void dragX(){
		if (!lockedX)
			gx = mx - (getWidth() / 2);
	}
	protected void dragY() {
		if (!lockedY)
			gy = my - (getHeight() / 2);
	}

	@Override
	public void whilePressed() {

		dragX();
		dragY();

		updateChildrenPosition();
	}

	@Override
	public void update() {
		this.mx = GuiHelper.mouseX;
		this.my = GuiHelper.mouseY;

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
