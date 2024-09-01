package deus.guilib.test.placedLog;

import deus.guilib.adapter.AdvancedGui;
import deus.guilib.rendering.advanced.Button;
import deus.guilib.rendering.base.organization.ElementConfig;
import deus.guilib.rendering.base.organization.GuiConfig;
import deus.guilib.rendering.base.organization.childrenPlacement;
import deus.guilib.rendering.resource.Texture;
import net.minecraft.core.player.inventory.IInventory;

public class LogPileGui extends AdvancedGui {

	private int inventoryRows = 0;
	private Button mybutton =
		(Button) new Button(new Texture("assets/textures/gui/Slot.png", 18,18),0,0, ()->{})
			.config(
				new ElementConfig(false,false,false)
			);


	public LogPileGui(IInventory playerInventory, IInventory inventory) {
		super(new LogPileContainer(playerInventory, inventory));

		this.inventoryRows = inventory.getSizeInventory();
		this.xSize = 176;
		this.ySize = 166;



		config(new GuiConfig(childrenPlacement.CENTER));
		addChildren(
			mybutton
			//new Panel(0,0,3,3).config(new ElementConfig(false)),
			//new PlayerInventory(xSize, ySize)
			//	.config(new ElementConfig(Theme.DARK, true))
		);


	}

	@Override
	public void update() {
		mybutton.update(mouseX,mouseY);
	}
}
