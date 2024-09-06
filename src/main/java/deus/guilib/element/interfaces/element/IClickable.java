package deus.guilib.element.interfaces.element;

public interface IClickable {
	void onPush();
	void onPushOut();
	void onRelease();
	void whilePressed();
	boolean isHovered();
	void update(int mouseX, int mouseY);
}
