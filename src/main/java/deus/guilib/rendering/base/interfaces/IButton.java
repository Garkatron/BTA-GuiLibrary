package deus.guilib.rendering.base.interfaces;

import deus.guilib.rendering.base.Element;

public interface IButton {
	boolean isHovered();
	boolean isOn();
	boolean isDisabled();
	IElement setToggleMode(boolean bool);
	boolean isToggle();
	void toggle(boolean activate);
	void update(int mouseX, int mouseY);

	void onPush();
	void onRelease();
	void whilePressed();
}
