package deus.guilib.util;

import net.minecraft.client.Minecraft;

import static deus.guilib.GuiLib.MOD_ID;

public class GuiUtils
{
	public static int getGuiTexture(String texture)
	{
		Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
		return mc.renderEngine.getTexture("/assets/" + MOD_ID + "/gui/" + texture);
	}

}
