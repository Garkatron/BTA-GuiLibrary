package deus.guilib.test.placedLog;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;

public class LogPileTileEntity extends TileEntity implements IInventory {

	protected ItemStack[] contents = new ItemStack[1];

	public LogPileTileEntity() {
		this.contents = new ItemStack[getSizeInventory()];

	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index >= 0 && index < this.getSizeInventory()) {
			return this.contents[index];
		}
		return new ItemStack(Item.basket);
	}


	@Override
	public ItemStack decrStackSize(int i, int j) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		this.contents[i] = itemStack;
	}

	@Override
	public String getInvName() {
		return "LogPile";
	}

	// Métodos readFromNBT y writeToNBT

	@Override
	public int getInventoryStackLimit() {
		return 16;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return true; // Cambia la lógica según tus necesidades
	}

	@Override
	public void sortInventory() {
		// Implementa la lógica de clasificación según tus necesidades
	}
}
