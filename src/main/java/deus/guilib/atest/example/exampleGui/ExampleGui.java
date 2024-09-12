package deus.guilib.atest.example.exampleGui;

import deus.guilib.element.config.derivated.PageGuiConfig;
import deus.guilib.routing.Page;
import deus.guilib.user.PageGui;
import net.minecraft.core.player.inventory.IInventory;

public class ExampleGui extends PageGui {

	private static Page page = new ExamplePage(router,
		"Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
		"Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
		"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.",
		"Nisi ut aliquip ex ea commodo consequat.",
		"",
		"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore.",
		"Eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident."
		);

	private static Page page2 = new ExamplePage(router,
		"x"
	);

	private static Page page3 = new ExamplePage(router,
		"y"
	);

	static {
		router.registerRoute("0ºhome", page);
		router.registerRoute("1ºhome", page2);
		router.registerRoute("2ºhome", page3);
		router.navigateTo("1ºhome");


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
