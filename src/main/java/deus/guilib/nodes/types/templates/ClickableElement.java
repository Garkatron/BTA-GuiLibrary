package deus.guilib.nodes.types.templates;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.IClickable;
import deus.guilib.util.GuiHelper;
import org.lwjgl.input.Mouse;

import java.util.Map;

public abstract class ClickableElement extends Node implements IClickable {
	private boolean wasClicked = false;
	private boolean wasClickedOut = false;
	protected int mx = 0;
	protected int my = 0;

	public ClickableElement(Map<String, String> attr) {
		super(attr);
	}

	public ClickableElement() {
		super();
	}

	@Override
	public boolean isHovered() {
		return mx >= gx && my >= gy && mx < gx + getWidth() && my < gy + getHeight();
	}

	@Override
	protected void updateIt() {
		super.updateIt();
		this.mx = GuiHelper.mouseX;
		this.my = GuiHelper.mouseY;

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
