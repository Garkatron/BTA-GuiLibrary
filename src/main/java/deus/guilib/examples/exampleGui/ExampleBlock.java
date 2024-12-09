package deus.guilib.examples.exampleGui;

import deus.guilib.examples.interfaces.mixin.IEntityPlayer;
import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class ExampleBlock extends BlockTileEntityRotatable {
	public ExampleBlock(String key, int id, Material material) {
		super(key, id, material);

	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new ExampleBlockTileEntity();
	}

	public void displayGui(EntityPlayer player, ExampleBlockTileEntity inventory) {
		((IEntityPlayer)player).guiLib$openExampleGui(inventory);
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer entityplayer, Side side, double xPlaced, double yPlaced) {

		ExampleBlockTileEntity chest = (ExampleBlockTileEntity) world.getBlockTileEntity(x, y, z);

		if (!world.isClientSide) {
			this.displayGui(entityplayer, chest);
		}
		return true;

	}
}
