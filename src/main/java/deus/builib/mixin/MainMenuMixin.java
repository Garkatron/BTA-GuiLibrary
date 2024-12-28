package deus.builib.mixin;

import deus.builib.examples.interfaces.mixin.ICustomGui;
import deus.builib.uis.builibmain.MainGuiLibGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MainMenuMixin implements ICustomGui {



	@Inject(method = "init()V", at = @At("TAIL"), remap = false)
	private void mInit(CallbackInfo ci) {
		GuiMainMenu m = (GuiMainMenu) (Object) this;
		int i = m.height / 4 + 48;
		m.controlList.add(new GuiButton(10, m.width / 2 - 128, i + 72 + 12, 25,20,"BUI"));

	}

	@Inject(method = "buttonPressed(Lnet/minecraft/client/gui/GuiButton;)V", at = @At("TAIL"), remap = false)
	private void mButtonPressed(GuiButton button, CallbackInfo ci) {
		if (button.id == 10) {
			GuiMainMenu mm = (GuiMainMenu) (Object) this;
			Minecraft m = Minecraft.getMinecraft(mm);
			m.displayGuiScreen(new MainGuiLibGui());
		}
	}
}
