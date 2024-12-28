package deus.builib.guimanagement;

import deus.builib.gssl.Signal;
import deus.builib.guimanagement.routing.Router;
import deus.builib.interfaces.IGui;
import deus.builib.util.GuiHelper;
import deus.builib.util.math.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.player.inventory.Container;
import org.lwjgl.input.Keyboard;

/**
 * A GUI container that integrates with a Router to manage page rendering and user interactions.
 * Handles dynamic resizing and mouse input within the Minecraft GUI.
 * It integrates slots based on the provided `Page` content and inventory objects.
 */
public class AdvancedGuiContainer extends GuiContainer implements IGui {

	protected static Router router = new Router();
	public Signal<Tuple<Integer, Integer>> onResize = new Signal<>();
	public Signal<Boolean> onRefresh = new Signal<>();
	private int lastWidth = -1;
	private int lastHeight = -1;

	/**
	 * Constructor for AdvancedGui.
	 *
	 * @param container The container associated with this GUI.
	 */
	public AdvancedGuiContainer(Container container) {
		super(container);
		mc = Minecraft.getMinecraft(this);

		router.onChange.connect(
			(ref, value) -> {
				value.onResizeSignal.emit(new Tuple<>(this.width, this.height));
				value.setXYWH(this.xSize, this.ySize, this.width, this.height);
			}
		);

		onRefresh.connect((ref, value) -> {
			router.getCurrentPage().reload();
			if (container instanceof AdvancedContainer) {
				((AdvancedContainer) container).refreshContainer();
			}
		});
	}

	public void update() {
		int newWidth = this.mc.resolution.scaledWidth;
		int newHeight = this.mc.resolution.scaledHeight;

		if (newWidth != lastWidth || newHeight != lastHeight) {
			this.xSize = newWidth;
			this.ySize = newHeight;
			lastWidth = newWidth;
			lastHeight = newHeight;
			onResize.emit(new Tuple<>(newWidth, newHeight));
			router.getCurrentPage().setXYWH(this.xSize, this.ySize, this.width, this.height);
		}

		if (Keyboard.getEventKeyState()) {
			int key = Keyboard.getEventKey();
			if (key == Keyboard.KEY_F12) {
				onRefresh.emit(true);
			}
		}

		router.updatePage();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		update();
		router.renderCurrentPage();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		super.drawScreen(mouseX, mouseY, partialTick);
		GuiHelper.mouseX = mouseX;
		GuiHelper.mouseY = mouseY;
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		super.drawGuiContainerForegroundLayer();
	}
}
