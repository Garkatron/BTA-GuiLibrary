package deus.guilib.test.placedLog;

import deus.guilib.adapter.AdvancedGui;
import deus.guilib.rendering.advanced.Button;
import deus.guilib.rendering.advanced.PlayerInventory;
import deus.guilib.rendering.base.organization.ElementConfig;
import deus.guilib.rendering.base.organization.GuiConfig;
import deus.guilib.rendering.base.organization.childrenPlacement;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.player.inventory.IInventory;
import org.lwjgl.opengl.GL11;

/**
 * LogPileGui represents a custom GUI for a log pile.
 * It includes a toggle button and a player inventory display.
 */
public class LogPileGui extends AdvancedGui {

	// Number of inventory rows in the log pile
	private int inventoryRows = 0;

	// Button instance with defined actions
	private Button mybutton =
		(Button) new Button()
			.setToggleMode(false)
			.setOnPushAction((b) -> { System.out.println("PUSH"); }) // Action when button is pressed
			.setOnReleaseAction((b) -> { System.out.println("RELEASE"); }) // Action when button is released
			.setWhilePressedAction((b) -> {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("assets/textures/gui/image.png"));
				this.drawTexturedModalRect(30, 30, 0, 0, 169, 169);
			}) // Action while button is pressed
			.setX(80)
			.config(
				ElementConfig.create()
					.setIgnoreFatherPlacement(true)
					.setUseTheme(false)
					.setTheme("VANILLA")
			);

	/**
	 * Constructor for LogPileGui.
	 * @param playerInventory The player's inventory to be displayed
	 * @param inventory The log pile inventory to be displayed
	 */
	public LogPileGui(IInventory playerInventory, IInventory inventory) {
		super(new LogPileContainer(playerInventory, inventory));

		this.inventoryRows = inventory.getSizeInventory();
		this.xSize = 176;
		this.ySize = 166;

		// Configure the GUI with centered children placement
		config(new GuiConfig(childrenPlacement.CENTER));

		addChildren(
			mybutton,
			new PlayerInventory()
				.setSize(xSize, ySize)
				.config(
					ElementConfig.create()
						.setTheme("DARK")
				)
		);
	}

	/**
	 * Update the state of the GUI.
	 * This method is called every frame to update the button state.
	 */
	@Override
	public void update() {
		// Update button state with current mouse position
		mybutton.update(mouseX, mouseY);
	}
}
