package deus.guilib.user;

import deus.guilib.element.config.derivated.PageGuiConfig;
import deus.guilib.gssl.Signal;
import deus.guilib.routing.Router;
import deus.guilib.util.math.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.player.inventory.Container;

/**
 * A GUI container that integrates with a Router to manage page rendering and user interactions.
 * Handles dynamic resizing and mouse input within the Minecraft GUI.
 */
public class PageGui extends GuiContainer {

	protected static Router router = new Router();
	protected int mouseX = 0;
	protected int mouseY = 0;
	private int lastWidth = -1;
	private int lastHeight = -1;
	private PageGuiConfig config = PageGuiConfig.create();
	public Signal<Tuple<Integer, Integer>> onResize = new Signal<>();

	/**
	 * Constructor for PageGui.
	 *
	 * @param container The container associated with this GUI.
	 */
	public PageGui(Container container) {
		super(container);
		mc = Minecraft.getMinecraft(this);
	}

	/**
	 * Sets the configuration for the PageGui.
	 *
	 * @param config The PageGuiConfig to set.
	 */
	public void config(PageGuiConfig config) {
		this.config = config;
	}

	/**
	 * Updates the GUI based on the current resolution and mouse coordinates.
	 * Emits a resize signal if the dimensions change.
	 */
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
				router.getCurrentPage().setXYWH(this.xSize, this.ySize, this.width, this.height);
			}
		}
		router.updatePage(mouseX, mouseY);
	}

	/**
	 * Called to render the background layer of the GUI.
	 * Updates and renders the current page.
	 *
	 * @param f The partial tick time.
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		update();
		router.renderCurrentPage();
	}

	/**
	 * Called to render the screen.
	 * Updates mouse coordinates for interaction.
	 *
	 * @param mouseX The X coordinate of the mouse.
	 * @param mouseY The Y coordinate of the mouse.
	 * @param partialTick The partial tick time.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		super.drawScreen(mouseX, mouseY, partialTick);
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	/**
	 * Called to render the foreground layer of the GUI.
	 */
	@Override
	protected void drawGuiContainerForegroundLayer() {
		super.drawGuiContainerForegroundLayer();
	}
}
