package deus.guilib.container;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class AdvancedContainer extends Container {


	@Override
	public void addSlot(Slot slot) {
		super.addSlot(slot);
	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return List.of();
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return List.of();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return false;
	}


}
