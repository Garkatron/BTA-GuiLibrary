package deus.guilib.examples.exampleGui;


import deus.guilib.interfaces.IPage;
import deus.guilib.guimanagement.AdvancedContainer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;

public class ExampleContainer extends AdvancedContainer {

	public ExampleContainer(IPage page, IInventory inventory, IInventory playerInventory) {
		super(page, inventory, playerInventory);
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return true;
	}
}
