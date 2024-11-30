package deus.guilib.element.elements.templates;

import deus.guilib.element.Node;
import deus.guilib.element.Root;
import deus.guilib.interfaces.element.IClickable;
import org.lwjgl.input.Mouse;

public abstract class ClickableElement extends Node implements IClickable {
	private boolean wasClicked = false;
	private boolean wasClickedOut = false;
	protected int mx = 0;
	protected int my = 0;

	public ClickableElement() {
		super();
	}

	@Override
	public boolean isHovered() {
		return mx >= gx && my >= gy && mx < gx + getWidth() && my < gy + getHeight();
	}

	@Override
	public void update(int mouseX, int mouseY) {
		this.mx = mouseX;
		this.my = mouseY;

		boolean hovered = isHovered();
		boolean buttonDown = Mouse.isButtonDown(0);

		if (hovered) {
			if (buttonDown) {
				if (!wasClicked) {
					onPush();
					wasClicked = true;
				}

				whilePressed();
				wasClickedOut = false;

			} else {
				if (wasClicked) {
					onRelease();
					wasClicked = false;
				}

				if (buttonDown) {
					if (!wasClickedOut) {
						onPushOut();
						wasClickedOut = true;
					}
				} else {
					wasClickedOut = false;
				}
			}
		} else {
			if (buttonDown) {
				if (!wasClickedOut) {
					onPushOut();
					wasClickedOut = true;
				}
			} else {
				wasClicked = false;
				wasClickedOut = false;
			}
		}
	}



}
