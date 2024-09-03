package deus.guilib.atest.placedLog;

import deus.guilib.atest.placedLog.interfaces.mixin.IEntityPlayer;
import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class LogPile extends BlockTileEntityRotatable {
	public LogPile(String key, int id, Material material) {
		super(key, id, material);

	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new LogPileTileEntity();
	}

	public void displayGui(EntityPlayer player, LogPileTileEntity inventory) {
		((IEntityPlayer)player).newSteps$displayGUILogPile(inventory);
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer entityplayer, Side side, double xPlaced, double yPlaced) {

		LogPileTileEntity chest = (LogPileTileEntity) world.getBlockTileEntity(x, y, z);

		if (!world.isClientSide) {
			this.displayGui(entityplayer, chest);
		}
		return true;

	}
}
