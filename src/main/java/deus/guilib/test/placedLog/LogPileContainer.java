package deus.guilib.test.placedLog;


import deus.guilib.container.AdvancedContainer;
import deus.guilib.container.InventoryComponent;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class LogPileContainer extends AdvancedContainer {

	public LogPileContainer(IInventory playerInventory, IInventory logPileInventory) {
		super();

		InventoryComponent inventoryComponent = (InventoryComponent) new InventoryComponent(this,playerInventory);
		inventoryComponent.build();

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
