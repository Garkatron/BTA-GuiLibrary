package deus.guilib.rendering.advanced;

import deus.guilib.rendering.base.Element;
import deus.guilib.rendering.base.organization.ElementConfig;
import deus.guilib.rendering.resource.Texture;
import net.minecraft.client.GameResolution;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class PlayerInventory extends Element {
	private int width;  // Width of the GUI
	private int height; // Height of the GUI
	private int xSize;  // Width of the inventory
	private int ySize;  // Height of the inventory
	private int finalY;
	private int finalX;

	// Constructor con ElementConfig
	public PlayerInventory(int xSize, int ySize, ElementConfig config) {
		super(new Texture("assets/newsteps/textures/gui/Inventory.png", 176, 89), 176, 89);

		this.xSize = xSize;
		this.ySize = ySize;
	}

	// Constructor sin ElementConfig
	public PlayerInventory(int xSize, int ySize) {
		super(new Texture("assets/newsteps/textures/gui/Inventory.png", 176, 89));

		this.xSize = xSize;
		this.ySize = ySize;
	}

	// Constructor con ElementConfig y children
	public PlayerInventory(int xSize, int ySize, ElementConfig config, Element... children) {
		super(new Texture("assets/newsteps/textures/gui/Inventory.png", 176, 89), children);

		this.xSize = xSize;
		this.ySize = ySize;
	}

	// Constructor con children
	public PlayerInventory(int xSize, int ySize, Element... children) {
		super(new Texture("assets/newsteps/textures/gui/Inventory.png", 176, 89), children);

		this.xSize = xSize;
		this.ySize = ySize;
	}

	// Constructor con x, y, width, height
	public PlayerInventory(int x, int y, int xSize, int ySize) {
		super(new Texture("assets/newsteps/textures/gui/Inventory.png", 176, 89), x, y);


		this.xSize = xSize;
		this.ySize = ySize;
	}

	// Constructor con x, y, width, height y children
	public PlayerInventory(int x, int y, int xSize, int ySize, Element... children) {
		super(new Texture("assets/newsteps/textures/gui/Inventory.png", 176, 89), x, y, children);

		this.xSize = xSize;
		this.ySize = ySize;
	}

	@Override
	protected void drawIt() {
		if (mc == null || gui == null) {
			System.out.println("Error on drawIt, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}
		GL11.glColor4f(1f, 1f, 1f, 1f);


		if (config.isUseTheme())
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
//
//	public Tuple<Integer, Integer> getFinalPosition() {
//		int x = this.x + (this.width - this.xSize) / 2; // Coordinate x of the GUI
//		int y = this.y + (this.height - this.ySize) / 2; // Coordinate y of the GUI
//
//		finalX = x;
//		finalY = y;
//
//		return new Tuple<>(finalX,finalY);
//	}
}
