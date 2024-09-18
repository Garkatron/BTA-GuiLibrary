package deus.guilib.element.elements;

import deus.guilib.element.Element;
import deus.guilib.interfaces.element.IClickable;
import deus.guilib.resource.Texture;
import org.lwjgl.input.Mouse;

public abstract class ClickableElement extends Element implements IClickable {
	private boolean wasClicked = false;
	private boolean wasClickedOut = false;
	protected int mx = 0;
	protected int my = 0;

	public ClickableElement(Texture texture) {
		super(texture);
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
