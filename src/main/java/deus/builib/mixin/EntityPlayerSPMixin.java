package deus.builib.mixin;


import deus.builib.examples.exampleGui.ExampleBlockTileEntity;
import deus.builib.examples.exampleGui.ExampleGui;
import deus.builib.examples.interfaces.mixin.IEntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.gui.container.ScreenCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerLocal.class)
public class EntityPlayerSPMixin implements IEntityPlayer {


	@Shadow
	protected Minecraft mc;

	@Override
	public void guiLib$openExampleGui(ExampleBlockTileEntity blockTileEntity) {
		mc.displayScreen(new ExampleGui(blockTileEntity, mc.thePlayer.inventory));

	}
}
