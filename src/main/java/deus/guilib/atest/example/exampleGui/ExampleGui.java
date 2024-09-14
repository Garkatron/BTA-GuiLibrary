package deus.guilib.atest.example.exampleGui;

import deus.guilib.atest.example.book.ExamplePageBook;
import deus.guilib.atest.example.book.ExamplePageBook2;
import deus.guilib.atest.example.book.ExamplePageBook3;
import deus.guilib.atest.example.textArea.ExamplePageTestArea;
import deus.guilib.element.config.derivated.PageGuiConfig;
import deus.guilib.routing.Page;
import deus.guilib.user.PageGui;
import net.minecraft.core.player.inventory.IInventory;

public class ExampleGui extends PageGui {
	private static Page page = new ExamplePage(router);
	static {
		router.registerRoute("0ºhome", page);
		router.navigateTo("0ºhome");


	}

	public ExampleGui(IInventory playerInventory, IInventory inventory) {
		super(new ExampleContainer(page, playerInventory, inventory));



		//this.xSize = 176;
		//this.ySize = 166;

		config(
			PageGuiConfig.create()
				.setUseWindowSizeAsSize(true)

		);
	}

}
