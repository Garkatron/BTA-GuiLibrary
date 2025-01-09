package deus.builib.examples.exampleGui;


import deus.builib.interfaces.IPage;
import deus.builib.guimanagement.AdvancedContainer;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.core.player.inventory.container.Container;


public class ExampleContainer extends AdvancedContainer {

	public ExampleContainer(IPage page, Container inventory, Container playerInventory) {
		super(page, inventory, playerInventory);
	}


}
