package deus.builib.nodes.stylesystem.textures;

import net.minecraft.client.render.texture.meta.gui.GuiTextureProperties;

public class BuiTextureProperties extends GuiTextureProperties {

	public final String path;


	public BuiTextureProperties(String path, String type, int width, int height, Border border, boolean stretchInner) {
		super(type, width, height, border, stretchInner);
		this.path = path;
	}

}
