package deus.guilib.rendering.base.interfaces;

public interface IButton {
	boolean isHovered();
	boolean isOn();
	boolean isDisabled();
	void setToggleMode();
	boolean isToggle();
	void toggle(boolean activate);
	void update(int mouseX, int mouseY);
	void onClick();
}
