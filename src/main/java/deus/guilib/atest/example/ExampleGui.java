package deus.guilib.atest.example;

import deus.guilib.element.config.derivated.PageGuiConfig;
import deus.guilib.routing.Page;
import deus.guilib.user.PageGui;
import net.minecraft.core.player.inventory.IInventory;

public class ExampleGui extends PageGui {

	private static Page page = new ExamplePage(router);


	static {
		router.registerRoute("/home", page);
		router.navigateTo("/home");
	}

	public ExampleGui(IInventory playerInventory, IInventory inventory) {
		super(new ExampleContainer(page, playerInventory, inventory));


		System.out.println("SIZE: "+playerInventory.getSizeInventory());

		//this.xSize = 176;
		//this.ySize = 166;

		config(
			PageGuiConfig.create()
				.setUseWindowSizeAsSize(true)

		);
	}

}
