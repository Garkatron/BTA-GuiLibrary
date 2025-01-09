package deus.builib.examples.exampleGui;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.inventory.container.Container;
import org.jetbrains.annotations.Nullable;

public class ExampleBlockTileEntity extends TileEntity implements Container {

	protected ItemStack[] contents = new ItemStack[9];

	public ExampleBlockTileEntity() {
		this.contents = new ItemStack[getContainerSize()];
		for (int i = 0; i < contents.length; i++) {
			contents[i] = new ItemStack(Items.BASKET);
		}
	}

	@Override
	public int getContainerSize() {
		return 9;
	}

	@Override
	public @Nullable ItemStack getItem(int i) {
		return null;
	}

	@Override
	public @Nullable ItemStack removeItem(int i, int j) {
		if (this.contents[i] != null) {
			ItemStack itemstack1;
			if (this.contents[i].stackSize <= j) {
				itemstack1 = this.contents[i];
				this.contents[i] = null;
				// ! this.onInventoryChanged();
				return itemstack1;
			} else {
				itemstack1 = this.contents[i].splitStack(j);
				if (this.contents[i].stackSize <= 0) {
					this.contents[i] = null;
				}

				// ! this.onInventoryChanged();
				return itemstack1;
			}
		} else {
			return null;
		}
	}

	@Override
	public void setItem(int i, @Nullable ItemStack itemStack) {
		this.contents[i] = itemStack;
		if (itemStack != null && itemStack.stackSize > this.getMaxStackSize()) {
			itemStack.stackSize = this.getMaxStackSize();
		}

		// ! this.onInventoryChanged();
	}

	@Override
	public String getNameTranslationKey() {
		return "";
	}

	@Override
	public int getMaxStackSize() {
		return 0;
	}

	@Override
	public boolean stillValid(Player player) {
		return false;
	}

	@Override
	public void sortContainer() {

	}

	@Override
	public boolean locked(int index) {
		return Container.super.locked(index);
	}
}
