package deus.guilib.atest.example.book;

import deus.guilib.atest.example.exampleGui.ExampleContainer;
import deus.guilib.element.config.derivated.PageGuiConfig;
import deus.guilib.routing.Page;
import deus.guilib.user.PageGui;
import net.minecraft.core.player.inventory.IInventory;

public class ExampleGuiBook extends PageGui {
	private static Page page = new ExamplePageBookCover(router);
	private static Page page2 = new ExamplePageBook(router, "Categories");
	private static Page page3 = new ExamplePageBook2(router);

	static {
		router.registerRoute("0ºhome", page);
		router.registerRoute("1ºhome", page2);
		router.registerRoute("2ºhome", page3);
		router.navigateTo("0ºhome");
	}

	public ExampleGuiBook(IInventory playerInventory, IInventory inventory) {
		super(new ExampleContainer(page, playerInventory, inventory));

		//this.xSize = 176;
		//this.ySize = 166;

		config(
			PageGuiConfig.create()
				.setUseWindowSizeAsSize(true)

		);
	}

}
