package deus.guilib.atest.exampleGui;

import deus.guilib.guimanagement.AdvancedGui;
import deus.guilib.guimanagement.routing.Page;
import net.minecraft.core.player.inventory.IInventory;

public class ExampleGui extends AdvancedGui {
	private static final Page page = new ExamplePage(router);


	static {
		router.registerRoute("0ºhome", page);
		router.navigateTo("0ºhome");

	}

	public ExampleGui(IInventory playerInventory, IInventory inventory) {
		super(new ExampleContainer(page, inventory));

		//this.xSize = 176;
		//this.ySize = 166;

		DebugThings.container = this.inventorySlots;


	}



}
