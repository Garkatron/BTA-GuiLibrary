package deus.guilib.examples.exampleGui;

import deus.guilib.guimanagement.AdvancedGui;
import deus.guilib.guimanagement.routing.Page;
import net.minecraft.core.player.inventory.IInventory;

public class ExampleGui extends AdvancedGui {
	private static final Page page = new ExamplePage(router);

	static {
		router.registerRoute("0ºhome", page);
		router.navigateTo("0ºhome");

	}

	public ExampleGui(IInventory inventory, IInventory playerInventory) {
		super(new ExampleContainer(page, inventory, playerInventory));
	}



}
