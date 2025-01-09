package deus.builib.examples.exampleGui;

import deus.builib.guimanagement.AdvancedGuiContainer;
import deus.builib.guimanagement.routing.Page;
import net.minecraft.core.player.inventory.container.Container;

public class ExampleGui extends AdvancedGuiContainer {
	private static final Page page = new ExamplePage(router);

	static {
		router.registerRoute("0ºhome", page);
		router.navigateTo("0ºhome");
	}

	public ExampleGui(Container inventory, Container playerInventory) {
		super(new ExampleContainer(page, inventory, playerInventory));
	}



}
