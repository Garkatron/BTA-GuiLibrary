package deus.builib.guimanagement;

import deus.builib.gssl.Signal;
import deus.builib.guimanagement.routing.Router;
import deus.builib.interfaces.IGui;
import deus.builib.util.GuiHelper;
import deus.builib.util.math.Tuple;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import org.lwjgl.input.Keyboard;

/**
 * A GUI Screen that integrates with a Router to manage page rendering and user interactions.
 * Handles dynamic resizing and mouse input within the Minecraft GUI.
 */
@Environment(EnvType.CLIENT)
public class AdvancedScreen extends Screen implements IGui {

	protected static Router router = new Router();
	public Signal<Tuple<Integer, Integer>> onResize = new Signal<>();
	public Signal<Boolean> onRefresh = new Signal<>();
	private int lastWidth = -1;
	private int lastHeight = -1;

	public AdvancedScreen() {
		mc = Minecraft.getMinecraft();

		router.onChange.connect(
			(ref, value) -> {

				value.onResizeSignal.emit(new Tuple<>(this.width, this.height));
				value.setXYWH(0, 0, this.width, this.height);
			}
		);

		onRefresh.connect((ref, value) -> {
			router.getCurrentPage().reload();
		});
	}

	@Override
	public void update() {
		int newWidth = this.mc.resolution.getScaledWidthScreenCoords();
		int newHeight = this.mc.resolution.getScaledHeightScreenCoords();

		if (newWidth != lastWidth || newHeight != lastHeight) {
			//this.xSize = newWidth;
			//this.ySize = newHeight;
			lastWidth = newWidth;
			lastHeight = newHeight;
			onResize.emit(new Tuple<>(newWidth, newHeight));
			router.getCurrentPage().setXYWH(0, 0, this.width, this.height);
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
	public void render(int mx, int my, float partialTick) {
		GuiHelper.mouseX = mx;
		GuiHelper.mouseY = my;

		update();
		router.renderCurrentPage();
	}
}
