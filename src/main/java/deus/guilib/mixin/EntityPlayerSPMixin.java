package deus.guilib.mixin;


import deus.guilib.atest.placedLog.LogPileGui;
import deus.guilib.atest.placedLog.LogPileTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityPlayerSP.class)
public class EntityPlayerSPMixin implements deus.guilib.atest.placedLog.interfaces.mixin.IEntityPlayer {


	@Shadow
	protected Minecraft mc;

	@Override
	public void newSteps$displayGUILogPile(LogPileTileEntity logPileTileEntity) {

		mc.displayGuiScreen(new LogPileGui(mc.thePlayer.inventory, logPileTileEntity));


	}
}
