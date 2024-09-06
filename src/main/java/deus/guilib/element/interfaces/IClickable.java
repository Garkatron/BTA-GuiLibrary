package deus.guilib.element.interfaces;

public interface IClickable {
	void onPush();
	void onRelease();
	void whilePressed();
	boolean isHovered();
	void update(int mouseX, int mouseY);
}
