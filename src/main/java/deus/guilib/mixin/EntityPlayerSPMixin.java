package deus.guilib.mixin;


import deus.guilib.examples.exampleGui.ExampleBlockTileEntity;
import deus.guilib.examples.exampleGui.ExampleGui;
import deus.guilib.examples.interfaces.mixin.IEntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityPlayerSP.class)
public class EntityPlayerSPMixin implements IEntityPlayer {


	@Shadow
	protected Minecraft mc;

	@Override
	public void guiLib$openExampleGui(ExampleBlockTileEntity blockTileEntity) {

		mc.displayGuiScreen(new ExampleGui(blockTileEntity, mc.thePlayer.inventory));

	}
}
