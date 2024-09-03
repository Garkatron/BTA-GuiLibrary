package deus.guilib.user;

import deus.guilib.element.Element;
import deus.guilib.routing.Router;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.player.inventory.Container;

public class PageGui extends GuiContainer {

	protected int mouseX = 0;
	protected int mouseY = 0;

	protected static Router router = new Router();

	public PageGui(Container container) {
		super(container);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		update();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		super.drawScreen(mouseX, mouseY, partialTick);

		this.mouseX = mouseX;
		this.mouseY = mouseY;

		router.renderCurrentPage();
	}

	public void update() {
		router.updatePage(mouseX,mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		update();
		super.drawGuiContainerForegroundLayer();
	}
}
