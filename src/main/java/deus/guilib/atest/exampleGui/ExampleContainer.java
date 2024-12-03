package deus.guilib.atest.exampleGui;


import deus.guilib.interfaces.INodeFather;
import deus.guilib.user.container.AdvancedContainer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;

public class ExampleContainer extends AdvancedContainer {


	public ExampleContainer(INodeFather page, IInventory playerInventory, IInventory logPileInventory) {
		super(page, playerInventory, logPileInventory);

	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return true;
	}
}
