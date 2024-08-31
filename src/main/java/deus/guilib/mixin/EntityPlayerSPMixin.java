package deus.guilib.mixin;


import deus.guilib.test.placedLog.LogPileGui;
import deus.guilib.test.placedLog.LogPileTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityPlayerSP.class)
public class EntityPlayerSPMixin implements deus.guilib.test.placedLog.interfaces.mixin.IEntityPlayer {


	@Shadow
	protected Minecraft mc;

	@Override
	public void newSteps$displayGUILogPile(LogPileTileEntity logPileTileEntity) {

		mc.displayGuiScreen(new LogPileGui(mc.thePlayer.inventory, logPileTileEntity));


	}
}
