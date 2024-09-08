package deus.guilib.atest.example.exampleGui;


import deus.guilib.interfaces.IElementFather;
import deus.guilib.user.container.AdvancedContainer;
import net.minecraft.core.player.inventory.IInventory;

public class ExampleContainer extends AdvancedContainer {


	public ExampleContainer(IElementFather page, IInventory playerInventory, IInventory logPileInventory) {
		super(page, playerInventory, logPileInventory);

	}

}
