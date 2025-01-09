package deus.builib.mixin;

import deus.builib.interfaces.IRenderEngine;
import net.minecraft.client.GLAllocation;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.Renderer;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.texture.Texture;
import net.minecraft.client.render.texturepack.TexturePackList;
import net.minecraft.client.util.helper.Textures;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

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

	/*
	@Unique
	public int bui$getTextureAdvanced(String name) {
		if (name == null) {
			throw new NullPointerException();
		} else {
			Integer id = this.textureMap.get(name);
			if (id != null) {
				return id;
			} else {
				try {
					// ! 7.3 SHIT
					id = GLAllocation.generateTexture();
					if (name.startsWith("##")) {
						this.bindTexture(Textures.unwrapImageByColumns(Textures.readImage(this.texturePacks.getResourceAsStream(name.substring(2)))));
					} else if (name.startsWith("%clamp%")) {
						this.clampTexture = true;
						this.bindTexture(Textures.unwrapImageByColumns(Textures.readImage(this.texturePacks.getResourceAsStream(name.substring(7))));
						this.clampTexture = false;
					} else if (name.startsWith("%blur%")) {
						this.blurTexture = true;
						this.setupTexture(Textures.readImage(this.texturePacks.getResourceAsStream(name.substring(6))), id);
						this.blurTexture = false;
					} else {
						if (!name.startsWith("/assets")) {
							InputStream inputstream = new FileInputStream(name);
							if (inputstream == null) {
								this.setupTexture(Textures.missingTexture, id);
							} else {
								this.setupTexture(Textures.readImage(inputstream), id);
							}
						} else {
							InputStream inputstream = this.texturePacks.getResourceAsStream(name);
							if (inputstream == null) {
								this.setupTexture(Textures.missingTexture, id);
							} else {
								this.setupTexture(Textures.readImage(inputstream), id);
							}
						}
					}

					this.textureMap.put(name, id);
					return id;
				} catch (Exception var4) {
					var4.printStackTrace();
					int j = GLAllocation.generateTexture();
					this.setupTexture(Textures.missingTexture, j);
					this.textureMap.put(name, j);
					return j;
				}
			}
		}
	}
	
	 */
}
