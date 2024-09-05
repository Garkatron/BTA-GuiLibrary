package deus.guilib.element.elements;

import deus.guilib.element.Element;
import deus.guilib.resource.Texture;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

@Deprecated
public class OldPlayerInventory extends Element {
	private int width;  // Width of the GUI
	private int height; // Height of the GUI
	private int xSize;  // Width of the inventory
	private int ySize;  // Height of the inventory
	private int finalY;
	private int finalX;

	public OldPlayerInventory() {
		super(new Texture("assets/newsteps/textures/gui/Inventory.png", 176, 89));
	}

	public OldPlayerInventory setxSize(int xSize) {
		this.xSize = xSize;
		return this;

	}

	public OldPlayerInventory setySize(int ySize) {
		this.ySize = ySize;
		return this;

	}

	public OldPlayerInventory setSize(int xSize, int ySize) {
		this.ySize = ySize;
		this.xSize = xSize;
		return this;
	}


	@Override
	protected void drawIt() {
		if (mc == null || gui == null) {
			System.out.println("Error on drawIt, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}
		GL11.glColor4f(1f, 1f, 1f, 1f);


		if (!Objects.equals(config.getTheme(), "NONE"))
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(themeManager.getProperties(config.getTheme()).get(getClass().getSimpleName())));

		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(texture.getPath()));

		}
		GL11.glDisable(GL11.GL_BLEND);


		this.width = mc.resolution.scaledWidth;
		this.height = mc.resolution.scaledHeight;


		int x = this.x + (this.width - texture.getWidth()) / 2; // Coordinate x of the GUI
		int y = this.y + (this.height - texture.getHeight()) / 2; // Coordinate y of the GUI

		gui.drawTexturedModalRect(x, y + 57, 0, 0, texture.getWidth(),texture.getHeight());

	}

}
