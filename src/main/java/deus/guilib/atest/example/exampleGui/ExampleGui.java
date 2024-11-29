package deus.guilib.atest.example.exampleGui;

import deus.guilib.routing.Page;
import deus.guilib.user.PageGui;
import net.minecraft.core.player.inventory.IInventory;
import org.lwjgl.opengl.GL11;

public class ExampleGui extends PageGui {
	private static final Page page = new ExamplePage(router);


	static {
		router.registerRoute("0ºhome", page);
		router.navigateTo("0ºhome");

	}

	public ExampleGui(IInventory playerInventory, IInventory inventory) {
		super(new ExampleContainer(page, playerInventory, inventory));

		//this.xSize = 176;
		//this.ySize = 166;

	}

}
