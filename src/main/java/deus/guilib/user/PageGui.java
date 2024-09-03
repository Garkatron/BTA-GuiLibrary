package deus.guilib.user;

import deus.guilib.element.Element;
import deus.guilib.routing.Router;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.player.inventory.Container;

public class PageGui extends GuiContainer {

	private int lastWidth = -1;
	private int lastHeight = -1;
	protected int mouseX = 0;
	protected int mouseY = 0;

	protected static Router router = new Router();

	public PageGui(Container container) {
		super(container);
		mc = Minecraft.getMinecraft(this);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		update();
		router.renderCurrentPage();

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		super.drawScreen(mouseX, mouseY, partialTick);

		this.mouseX = mouseX;
		this.mouseY = mouseY;

	}

	public void update() {

		int newWidth = this.mc.resolution.scaledWidth;
		int newHeight = this.mc.resolution.scaledHeight;

		if (newWidth != lastWidth || newHeight != lastHeight) {
			this.xSize = newWidth;
			this.ySize = newHeight;
			lastWidth = newWidth;
			lastHeight = newHeight;
		}

		router.updatePage(mouseX,mouseY);

	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		super.drawGuiContainerForegroundLayer();
	}
}
