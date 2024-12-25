package deus.guilib.nodes.types.interaction;

import deus.guilib.nodes.types.templates.ClickableElement;
import deus.guilib.util.GuiHelper;
import org.lwjgl.input.Mouse;

import java.util.Map;

public class DraggableElement extends ClickableElement {

	private boolean beingHeld = false;
	protected boolean lockedY, lockedX = false;

	public DraggableElement() {
		super();
	}

	public DraggableElement(Map<String, String> attributes) {
		super(attributes);
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
	public void onPush() {} // maybe rename to onCapture?

	@Override
	public void onPushOut() {}

	@Override
	public void onRelease() {}

	protected void dragX() {
		if (!lockedX) x = GuiHelper.mouseX - (getWidth() / 2);
	}
	protected void dragY() {
		if (!lockedY) y = GuiHelper.mouseY - (getHeight() / 2);
	}

	@Override
	public void whilePressed() {
		dragX();
		dragY();
		updateChildrenPosition();
	}

	@Override
	protected void updateIt() {
		//super.updateIt();
		this.mx = GuiHelper.mouseX;
		this.my = GuiHelper.mouseY;

		boolean hovered = isHovered();
		boolean buttonDown = Mouse.isButtonDown(0);

		// Execute whilePressed action if the button is pressed and held
		if (beingHeld) whilePressed();

		if (hovered && buttonDown && !beingHeld) { // Left mouse button is pressed
			onPush();
			beingHeld = true;
		}

		else if (!buttonDown && beingHeld) {
			beingHeld = false;
			onRelease();
		}

	}
}
