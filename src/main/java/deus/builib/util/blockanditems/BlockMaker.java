package deus.builib.util.blockanditems;

import net.minecraft.core.block.Block;
import turniplabs.halplibe.helper.BlockBuilder;

import java.util.ArrayList;
import java.util.List;

import static deus.builib.GuiLib.MOD_ID;



public class BlockMaker {

	public static BlockBuilder genericBlockBuilder = new BlockBuilder(MOD_ID);

	public static <T extends Block> T make(T block) {
		return genericBlockBuilder.build(block);
	}

	public static <T extends Block> T make(BlockBuilder builder, T block) {
		return builder.build(block);
	}

	public static <T extends Block> List<T> make(BlockBuilder builder, T... blocks) {
		List<T> iBlocks = new ArrayList<>();
		for (T block : blocks) {
			iBlocks.add(builder.build(block));
		}
		return iBlocks;
	}
}
