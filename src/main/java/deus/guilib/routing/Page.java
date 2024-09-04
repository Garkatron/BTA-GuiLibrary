package deus.guilib.routing;

import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.interfaces.IElement;
import deus.guilib.user.PageGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChest;

import java.util.ArrayList;
import java.util.List;

public abstract class Page {

	protected int mouseX = 0;
	protected int mouseY = 0;
	protected Router router;
	protected Minecraft mc;
	protected GuiConfig config;
	protected int width, height = 0;
	protected int xSize, ySize = 0;
	private List<IElement> content = new ArrayList<>();


	public Page(Router router) {
		this.router = router;
		mc = Minecraft.getMinecraft(this);
	}

	public void addContent(IElement... element) {
		content.addAll(List.of(element));
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public int getxSize() {
		return xSize;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setXYWH(int xSize, int ySize, int width, int height) {
		this.ySize=ySize;
		this.xSize=xSize;
		this.width=width;
		this.height=height;
	}

	public int getySize() {
		return ySize;
	}

	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void config(GuiConfig config) {
		this.config = config;
	}

	public List<IElement> getContent() {
		return content;
	}

	public void render() {


		if (content.isEmpty()) return;

		int[] basePos = calculateBasePosition();
		int baseX = basePos[0];
		int baseY = basePos[1];

		// Bucle for-each para iterar sobre los hijos y dibujarlos
		for (IElement child : content) {
			int relativeX = baseX + child.getX();
			int relativeY = baseY + child.getY();
			if (!child.getConfig().isIgnoreFatherPlacement() && !child.isPositioned()) {

				child.setPositioned(true);
				child.setX(relativeX);
				child.setY(relativeY);
			}

			child.draw();
		}
	}

	private int[] calculateBasePosition() {
		int baseX, baseY;

		switch (config.getPlacement()) {
			case CENTER:
				baseX = (width - xSize) / 2;
				baseY = (height - ySize) / 2;
				break;
			case TOP:
				baseX = (width - xSize) / 2;
				baseY = 0;
				break;
			case BOTTOM:
				baseX = (width - xSize) / 2;
				baseY = height - ySize;
				break;
			case LEFT:
				baseX = 0;
				baseY = (height - ySize) / 2;
				break;
			case RIGHT:
				baseX = width - xSize;
				baseY = (height - ySize) / 2;
				break;
			case TOP_LEFT:
				baseX = 0;
				baseY = 0;
				break;
			case BOTTOM_LEFT:
				baseX = 0;
				baseY = height - ySize;
				break;
			case BOTTOM_RIGHT:
				baseX = width - xSize;
				baseY = height - ySize;
				break;
			case TOP_RIGHT:
				baseX = width - xSize;
				baseY = 0;
				break;
			default:
				baseX = 0;
				baseY = 0;
				break;
		}
		return new int[]{baseX, baseY};
	}


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
