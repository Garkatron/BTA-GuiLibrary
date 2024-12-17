package deus.guilib.guimanagement;

import deus.guilib.gssl.Signal;
import deus.guilib.guimanagement.routing.Page;
import deus.guilib.guimanagement.routing.Router;
import deus.guilib.interfaces.IGui;
import deus.guilib.util.GuiHelper;
import deus.guilib.util.math.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * A GUI Screen that integrates with a Router to manage page rendering and user interactions.
 * Handles dynamic resizing and mouse input within the Minecraft GUI.
 */
public class AdvancedGuiScreen extends GuiScreen implements IGui {

	protected static Router router = new Router();
	public Signal<Tuple<Integer, Integer>> onResize = new Signal<>();
	public Signal<Boolean> onRefresh = new Signal<>();
	private int lastWidth = -1;
	private int lastHeight = -1;

	public AdvancedGuiScreen() {
		mc = Minecraft.getMinecraft(this);

		router.onChange.connect(
			(ref, value) -> {

				value.onResizeSignal.emit(new Tuple<>(this.width, this.height));
				value.setXYWH(0, 0, this.width, this.height);
			}
		);

		onRefresh.connect((ref, value) -> {
			router.getCurrentPage().reloadXml();
			router.getCurrentPage().reloadStyles();
		});
	}

	@Override
	public void update() {
		int newWidth = this.mc.resolution.scaledWidth;
		int newHeight = this.mc.resolution.scaledHeight;

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
	public void drawBackground() {

	}

	@Override
	public void drawDefaultBackground() {

	}

	@Override
	public void drawWorldBackground() {

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		GuiHelper.mouseX = mouseX;
		GuiHelper.mouseY = mouseY;

		update();
		router.renderCurrentPage();
	}

}
