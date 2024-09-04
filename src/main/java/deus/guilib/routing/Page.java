package deus.guilib.routing;

import deus.guilib.element.interfaces.IElement;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public abstract class Page {

	protected int mouseX = 0;
	protected int mouseY = 0;
	protected Router router;
	protected Minecraft mc;

	public Page(Router router) {
		this.router = router;
		mc = Minecraft.getMinecraft(this);
	}

	protected List<IElement> content = new ArrayList<>();

	public List<IElement> getContent() {
		return content;
	}

	public void render() {
		for (IElement element : content) {
			element.draw();
		}
	} // Method to render the page content


	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void update() {

	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
}
