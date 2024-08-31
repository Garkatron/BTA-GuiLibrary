package deus.guilib.rendering.advanced;

import deus.guilib.rendering.base.Element;
import deus.guilib.rendering.base.organization.ElementConfig;
import deus.guilib.rendering.base.organization.childrenPlacement;
import deus.guilib.rendering.resource.Texture;
import org.lwjgl.opengl.GL11;

@Deprecated
public class OldPlayerInventory extends Element {
	private int width;  // Width of the GUI
	private int height; // Height of the GUI
	private int xSize;  // Width of the inventory
	private int ySize;  // Height of the inventory

	public OldPlayerInventory(Texture texture, int x, int y, ElementConfig config, Element... children) {
		super(texture, x, y, config, children);
	}

	public OldPlayerInventory(Texture texture, int x, int y) {
		super(texture, x, y);
	}

	public OldPlayerInventory(Texture texture, int x, int y, childrenPlacement placement) {
		super(texture, x, y, placement);
	}

	public OldPlayerInventory(Texture texture, int x, int y, Element... children) {
		super(texture, x, y, children);
	}

	public OldPlayerInventory(Texture texture, Element... children) {
		super(texture, children);
	}

	public OldPlayerInventory(Texture texture) {
		super(texture);
	}


	@Override
	protected void drawIt() {
		if (mc == null || gui == null) {
			System.out.println("Error on drawIt, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}
		GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(texture.getPath()));
		GL11.glDisable(GL11.GL_BLEND);

		int x = (this.width - this.xSize) / 2; // Coordinate x of the GUI
		int y = (this.height - this.ySize) / 2; // Coordinate y of the GUI

		int slotSize = texture.getWidth();  // Original slot size
		int spacing = 1;    // 1 pixel spacing between slots

		// Add player inventory slots (3 rows of 9 columns)
		int inventoryRowCount = 3; // Number of rows in the player inventory
		for (int row = 0; row < inventoryRowCount; ++row) {
			for (int column = 0; column < 9; ++column) {
				int xPosition = x + column * (slotSize + spacing);
				int yPosition = y + row * (slotSize + spacing) + 83; // Adjusted by 83 pixels for spacing
				gui.drawTexturedModalRect(xPosition, yPosition, 0, 0, slotSize, slotSize);
			}
		}

		// Add hotbar slots (1 row of 9 columns)
		for (int column = 0; column < 9; ++column) {
			int xPosition = x + column * (slotSize + spacing);
			int yPosition = y + 141; // 141 is the standard position for the hotbar
			gui.drawTexturedModalRect(xPosition, yPosition, 0, 0, slotSize, slotSize);
		}
	}


}
