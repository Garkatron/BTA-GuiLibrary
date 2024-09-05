package deus.guilib.atest.example;


import deus.guilib.container.AdvancedContainer;
import deus.guilib.routing.Page;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class ExampleContainer extends AdvancedContainer {

	private int slotIdCounter = 0; // Counter to keep track of slot IDs

	public ExampleContainer(Page page, IInventory playerInventory, IInventory logPileInventory) {
		super(page, playerInventory, logPileInventory);

	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		// No se mueve ningún ítem, acción inactiva
		return null;
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		// No hay slots objetivo, acción inactiva
		return null;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		// Contenedor usable por el jugador
		return true;
	}
}
