package deus.builib.mixin;

import deus.builib.examples.interfaces.mixin.ICustomGui;
import deus.builib.uis.builibmain.MainGuiLibGui;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.ScreenMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenMainMenu.class)
public class MainMenuMixin implements ICustomGui {



	@Inject(method = "init()V", at = @At("TAIL"), remap = false)
	private void mInit(CallbackInfo ci) {
		ScreenMainMenu m = (ScreenMainMenu) (Object) this;
		int i = m.height / 4 + 48;
		m.buttons.add(new ButtonElement(10, m.width / 2 - 128, i + 72 + 12, 25,20,"BUI"));

	}

	@Inject(method = "buttonClicked(Lnet/minecraft/client/gui/ButtonElement;)V", at = @At("TAIL"), remap = false)
	private void mButtonPressed(ButtonElement button, CallbackInfo ci) {
		if (button.id == 10) {
			// ? ScreenMainMenu mm = (ScreenMainMenu) (Object) this;
			Minecraft m = Minecraft.getMinecraft();
			m.displayScreen(new MainGuiLibGui());
		}
	}
}
