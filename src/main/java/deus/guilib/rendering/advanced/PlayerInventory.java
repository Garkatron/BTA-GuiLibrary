package deus.guilib.rendering.advanced;

import deus.guilib.rendering.base.Element;
import deus.guilib.rendering.base.organization.ElementConfig;
import deus.guilib.rendering.resource.Texture;
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
		super(new Texture(config.getTheme().getProperties().get(PlayerInventory.class.getSimpleName()), 18, 18));
		this.width = mc.gameSettings.windowWidth.value/2;
		this.height = mc.gameSettings.windowHeight.value/2;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	// Constructor sin ElementConfig
	public PlayerInventory(int xSize, int ySize) {
		super(new Texture("assets/newsteps/textures/gui/Inventory.png", 18, 18));
		this.width = mc.gameSettings.windowWidth.value/2;
		this.height = mc.gameSettings.windowHeight.value/2;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	// Constructor con ElementConfig y children
	public PlayerInventory(int xSize, int ySize, ElementConfig config, Element... children) {
		super(new Texture(config.getTheme().getProperties().get(PlayerInventory.class.getSimpleName()), 18, 18), children);
		this.width = mc.gameSettings.windowWidth.value/2;
		this.height = mc.gameSettings.windowHeight.value/2;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	// Constructor con children
	public PlayerInventory(int xSize, int ySize, Element... children) {
		super(new Texture("assets/newsteps/textures/gui/Inventory.png", 18, 18), children);
		this.width = mc.gameSettings.windowWidth.value/2;
		this.height = mc.gameSettings.windowHeight.value/2;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	// Constructor con x, y, width, height
	public PlayerInventory(int x, int y, int xSize, int ySize) {
		super(new Texture("assets/newsteps/textures/gui/Inventory.png", 18, 18), x, y);
		this.width = mc.gameSettings.windowWidth.value/2;
		this.height = mc.gameSettings.windowHeight.value/2;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	// Constructor con x, y, width, height y children
	public PlayerInventory(int x, int y, int xSize, int ySize, Element... children) {
		super(new Texture("assets/newsteps/textures/gui/Inventory.png", 18, 18), x, y, children);
		this.width = mc.gameSettings.windowWidth.value/2;
		this.height = mc.gameSettings.windowHeight.value/2;
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
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(config.getTheme().getProperties().get(getClass().getSimpleName())));
		GL11.glDisable(GL11.GL_BLEND);

		int x = this.x + (this.width - this.xSize) / 2; // Coordinate x of the GUI
		int y = this.y + (this.height - this.ySize) / 2; // Coordinate y of the GUI


		gui.drawTexturedModalRect(x, y + (ySize-70), 0, 0, 176, 89);

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
