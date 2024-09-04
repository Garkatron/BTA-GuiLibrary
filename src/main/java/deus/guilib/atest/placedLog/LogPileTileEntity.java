package deus.guilib.atest.placedLog;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;

public class LogPileTileEntity extends TileEntity implements IInventory {

	protected ItemStack[] contents = new ItemStack[16];

	public LogPileTileEntity() {
		this.contents = new ItemStack[getSizeInventory()];
		contents[0]=new ItemStack(Item.basket);
		contents[1]=new ItemStack(Item.basket);
		contents[15]=new ItemStack(Item.basket);
	}

	@Override
	public int getSizeInventory() {
		return 16;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.contents[i];
	}



	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.contents[i] != null) {
			ItemStack itemstack1;
			if (this.contents[i].stackSize <= j) {
				itemstack1 = this.contents[i];
				this.contents[i] = null;
				this.onInventoryChanged();
				return itemstack1;
			} else {
				itemstack1 = this.contents[i].splitStack(j);
				if (this.contents[i].stackSize <= 0) {
					this.contents[i] = null;
				}

				this.onInventoryChanged();
				return itemstack1;
			}
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.contents[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
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
