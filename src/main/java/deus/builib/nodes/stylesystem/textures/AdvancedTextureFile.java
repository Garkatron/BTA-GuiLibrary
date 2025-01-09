package deus.builib.nodes.stylesystem.textures;

import deus.builib.GuiLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.texture.TextureFile;
import net.minecraft.client.render.texturepack.TexturePack;
import net.minecraft.client.util.helper.Textures;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class AdvancedTextureFile extends TextureFile {

	public AdvancedTextureFile(String path) {
		super(path);
	}

	@Override
	public void init() {
		this.metaHandler.reset();
		if (this.sourcePath != null) {
			Minecraft mc = Minecraft.getMinecraft();
			TexturePack pack = mc.texturePackList.getHighestPriorityTexturePackWithFile(this.sourcePath);
			if (pack == null) {


				try {
					this.setupTexture(Textures.readImage(new FileInputStream(sourcePath)));
				} catch (FileNotFoundException e) {
					this.setupTexture(Textures.missingTexture);
				}


				return;
			}

			try {
				InputStream stream = pack.getResourceAsStream(this.sourcePath + ".mcmeta");

				try {
					if (stream != null) {
						this.metaHandler.loadFromStream(stream);
					}
				} catch (Throwable var10) {
					if (stream != null) {
						try {
							stream.close();
						} catch (Throwable var7) {
							var10.addSuppressed(var7);
						}
					}

					throw var10;
				}

				if (stream != null) {
					stream.close();
				}
			} catch (Exception e) {
				GuiLib.LOGGER.error("Failed to load data for '{}.mcmeta'!", this.sourcePath, e);
			}

			try {
				InputStream imageStream = pack.getResourceAsStream(this.sourcePath);

				label99: {
					try {
						if (imageStream == null) {
							this.setupTexture(Textures.missingTexture);
							break label99;
						}

						this.setupTexture(Textures.readImage(imageStream));
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

				return;
			} catch (IOException var9) {
				this.setupTexture(Textures.missingTexture);
			}
		}
	}
}
