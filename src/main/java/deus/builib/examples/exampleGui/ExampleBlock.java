package deus.builib.examples.exampleGui;

import deus.builib.examples.interfaces.mixin.IEntityPlayer;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicAxisAligned;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class ExampleBlock extends BlockLogicRotatable {
	public ExampleBlock(Block<?> block, Material material) {
		super(block, material);

	}

	public void displayGui(PlayerLocal player, ExampleBlockTileEntity inventory) {
		((IEntityPlayer)player).guiLib$openExampleGui(inventory);
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
		ExampleBlockTileEntity chest = (ExampleBlockTileEntity) world.getTileEntity(x, y, z);

		if (!world.isClientSide) {
			this.displayGui((PlayerLocal) player, chest);
		}
		return true;
	}
}
