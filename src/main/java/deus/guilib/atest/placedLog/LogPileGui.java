package deus.guilib.atest.placedLog;

import deus.guilib.element.config.ChildrenPlacement;
import deus.guilib.element.config.derivated.PageGuiConfig;
import deus.guilib.routing.Page;
import deus.guilib.user.PageGui;
import net.minecraft.client.Minecraft;
import net.minecraft.core.player.inventory.IInventory;

public class LogPileGui extends PageGui {

	private static Page page = new TestPage3(router);


	static {
		router.registerRoute("/home", page);
		router.navigateTo("/home");
	}

	public LogPileGui(IInventory playerInventory, IInventory inventory) {
		super(new LogPileContainer(page, playerInventory, inventory));


		System.out.println("SIZE: "+playerInventory.getSizeInventory());

		//this.xSize = 176;
		//this.ySize = 166;

		config(
			PageGuiConfig.create()
				.setUseWindowSizeAsSize(true)
		);
	}

}
