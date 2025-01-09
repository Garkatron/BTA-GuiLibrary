package deus.builib.guimanagement;

import deus.builib.gssl.Signal;
import deus.builib.guimanagement.routing.Router;
import deus.builib.interfaces.IGui;
import deus.builib.util.GuiHelper;
import deus.builib.util.math.Tuple;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.container.ScreenContainerAbstract;

import net.minecraft.core.player.inventory.menu.MenuAbstract;
import org.lwjgl.input.Keyboard;

/**
 * A GUI container that integrates with a Router to manage page rendering and user interactions.
 * Handles dynamic resizing and mouse input within the Minecraft GUI.
 * It integrates slots based on the provided `Page` content and inventory objects.
 */
public class AdvancedGuiContainer extends ScreenContainerAbstract implements IGui {

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
	public AdvancedGuiContainer(MenuAbstract container) {
		super(container);
		mc = Minecraft.getMinecraft();

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
		int newWidth = this.mc.resolution.getScaledWidthScreenCoords();
		int newHeight = this.mc.resolution.getScaledWidthScreenCoords();

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
	public void render(int mx, int my, float partialTick) {
		super.render(mx, my, partialTick);
		GuiHelper.mouseX = mx;
		GuiHelper.mouseY = my;
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		super.drawGuiContainerForegroundLayer();
	}
}
