package deus.guilib.user;

import deus.guilib.element.config.Config;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.config.derivated.PageGuiConfig;
import deus.guilib.gssl.Signal;
import deus.guilib.routing.Router;
import deus.guilib.util.math.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.player.inventory.Container;

public class PageGui extends GuiContainer {

	protected static Router router = new Router();
	protected int mouseX = 0;
	protected int mouseY = 0;
	private int lastWidth = -1;
	private int lastHeight = -1;
	private PageGuiConfig config = PageGuiConfig.create();
	public Signal<Tuple<Integer, Integer>> onResize = new Signal<>();

	public PageGui(Container container) {
		super(container);
		mc = Minecraft.getMinecraft(this);
	}

	public void config(PageGuiConfig config) {
		this.config = config;
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
			if (config.isUseWindowSizeAsSize()) {
				this.xSize = newWidth;
				this.ySize = newHeight;
				lastWidth = newWidth;
				lastHeight = newHeight;
				onResize.emit(new Tuple<>(newWidth, newHeight));
			}
		}
		router.getCurrentPage().setXYWH(this.xSize, this.ySize, this.width, this.height);
		router.updatePage(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		super.drawGuiContainerForegroundLayer();
	}
}
