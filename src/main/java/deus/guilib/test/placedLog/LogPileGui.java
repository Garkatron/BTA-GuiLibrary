package deus.guilib.test.placedLog;

import deus.guilib.adapter.AdvancedGui;
import deus.guilib.rendering.advanced.PlayerInventory;
import deus.guilib.rendering.base.Element;
import deus.guilib.rendering.base.Panel;
import deus.guilib.rendering.base.Slot;
import deus.guilib.rendering.base.organization.ElementConfig;
import deus.guilib.rendering.resource.Theme;
import net.minecraft.client.GameResolution;
import net.minecraft.core.player.inventory.IInventory;

public class LogPileGui extends AdvancedGui {

	private int inventoryRows = 0;

	public LogPileGui(IInventory playerInventory, IInventory inventory) {
		super(new LogPileContainer(playerInventory, inventory));

		this.inventoryRows = inventory.getSizeInventory();
		this.xSize = 176;
		this.ySize = 166;


		addChildren(

			//new Panel(0,0,3,3).config(new ElementConfig(true)),
//[19:48:51] [Client-Main] AAAA: 480::: 265
			// Ignored by children placement
			new PlayerInventory(xSize, ySize)
				.config(new ElementConfig(Theme.DARK, true))
		);


	}

}
