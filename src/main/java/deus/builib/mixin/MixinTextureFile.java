package deus.builib.mixin;

import com.google.gson.JsonObject;
import deus.builib.GuiLib;
import deus.builib.interfaces.textures.IMetaHandler;
import deus.builib.interfaces.textures.ITextureFile;
import deus.builib.nodes.stylesystem.textures.TextureUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.texture.Texture;
import net.minecraft.client.render.texture.TextureFile;
import net.minecraft.client.render.texture.meta.TextureMetaHandler;
import net.minecraft.client.render.texturepack.TexturePack;
import net.minecraft.client.util.helper.Textures;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Mixin(TextureFile.class)
public class MixinTextureFile implements ITextureFile {

	@Shadow
	protected TextureMetaHandler metaHandler;

	@Shadow
	@Final
	protected String sourcePath;

	@Unique
	@Override
	public void bui$setMeta(JsonObject meta) {
		((IMetaHandler)metaHandler).bui$setMeta(meta);
	}

	// Interceptamos el método init() en lugar del constructor
	@Inject(method = "init", at = @At("RETURN"), remap = false)
	private void customInit(CallbackInfo ci) {
		this.metaHandler.reset();
		if (this.sourcePath != null) {
			Minecraft mc = Minecraft.getMinecraft();
			TexturePack pack = mc.texturePackList.getHighestPriorityTexturePackWithFile(this.sourcePath);
			if (pack == null) {
				Texture t = (Texture) (Object) this; // Accediendo a la clase base
				t.setupTexture(TextureUtils.loadImage(sourcePath));
				return;
			}

			InputStream imageStream;
			try {
				imageStream = pack.getResourceAsStream(this.sourcePath + ".mcmeta");

				try {
					if (imageStream != null) {
						this.metaHandler.loadFromStream(imageStream);
					}
				} catch (Throwable var10) {
					if (imageStream != null) {
						try {
							imageStream.close();
						} catch (Throwable var7) {
							var10.addSuppressed(var7);
						}
					}

					throw var10;
				}

				if (imageStream != null) {
					imageStream.close();
				}
			} catch (Exception var11) {
				Exception e = var11;
				GuiLib.LOGGER.error("Failed to load data for '{}.mcmeta'!", this.sourcePath, e);
			}

			try {
				imageStream = pack.getResourceAsStream(this.sourcePath);

				label87: {
					try {
						if (imageStream != null) {
							Texture t = (Texture) (Object) this;
							t.setupTexture(Textures.readImage(imageStream));
							break label87;
						}

						Texture t = (Texture) (Object) this;
						t.setupTexture(Textures.missingTexture);
					} catch (Throwable var8) {
						if (imageStream != null) {
							try {
								imageStream.close();
							} catch (Throwable var6) {
								var8.addSuppressed(var6);
							}
						}

						throw var8;
					}

					if (imageStream != null) {
						imageStream.close();
					}
					return;
				}

				if (imageStream != null) {
					imageStream.close();
				}
			} catch (IOException var9) {
				Texture t = (Texture) (Object) this;
				t.setupTexture(Textures.missingTexture);
			}
		}
	}
}
