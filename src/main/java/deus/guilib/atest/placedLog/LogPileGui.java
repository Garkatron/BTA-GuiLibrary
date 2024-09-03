package deus.guilib.atest.placedLog;

import deus.guilib.routing.Page;
import deus.guilib.user.PageGui;
import net.minecraft.client.Minecraft;
import net.minecraft.core.player.inventory.IInventory;

public class LogPileGui extends PageGui {

	private static Page page = new TestPage(router);


	static {

		router.registerRoute("/home", page);
		//router.registerRoute("/test", new TestPage2(router));
		router.navigateTo("/home");
	}

	public LogPileGui(IInventory playerInventory, IInventory inventory) {
		super(new LogPileContainer(page, playerInventory, inventory));


	}


}
