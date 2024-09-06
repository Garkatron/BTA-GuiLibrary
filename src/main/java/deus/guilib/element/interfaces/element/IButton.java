package deus.guilib.element.interfaces.element;

public interface IButton extends IClickable {
	boolean isOn();
	boolean isDisabled();
	IElement setToggleMode(boolean bool);
	boolean isToggle();
	void toggle(boolean activate);
}
