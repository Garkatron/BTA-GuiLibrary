package deus.builib.mixin;

import deus.builib.interfaces.textures.IRenderEngine;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.texture.Texture;
import net.minecraft.client.render.texturepack.TexturePackList;
import net.minecraft.client.util.helper.Textures;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;

@Mixin(TextureManager.class)
public abstract class MixinRenderEngine implements IRenderEngine {

	// Usamos @Shadow para acceder a los campos y métodos privados de RenderEngine
	@Shadow public TexturePackList texturePacks;
	@Final
	@Shadow public java.util.Map<String, Integer> textureMap;
	@Shadow private boolean clampTexture;
	@Shadow private boolean blurTexture;

	@Shadow
	public abstract void bindTexture(Texture texture);

	@Shadow
	public abstract BufferedImage getImage(String name);

	@Unique
	public BufferedImage bui$getImageAdvanced(String name) {
		try {
			if (!name.startsWith("/assets")) {
				InputStream inputstream = new FileInputStream(name);
				return Textures.readImage(inputstream);
			} else {
				return getImage(name);
			}
		} catch (Exception var3) {
			return Textures.missingTexture;
		}
	}



}
