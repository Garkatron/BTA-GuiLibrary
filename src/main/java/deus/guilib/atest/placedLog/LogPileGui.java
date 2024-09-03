package deus.guilib.atest.placedLog;

import deus.guilib.user.PageGui;
import net.minecraft.core.player.inventory.IInventory;

public class LogPileGui extends PageGui {
	static {
		router.registerRoute("/home", new TestPage(router));
		router.registerRoute("/test", new TestPage2(router));
		router.navigateTo("/home");
	}

	public LogPileGui(IInventory playerInventory, IInventory inventory) {
		super(new LogPileContainer(playerInventory, inventory));

		this.xSize = 176;
		this.ySize = 166;
	}
}
