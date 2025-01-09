package deus.builib.nodes.stylesystem.textures;

import deus.builib.GuiLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.texture.TextureBuffered;
import net.minecraft.client.util.helper.Textures;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import static net.minecraft.client.util.helper.Textures.*;

public class TextureUtils {

	public static BufferedImage loadImage(String path) {
		try {
			if (path.startsWith("/assets")) {
				InputStream stream = Textures.class.getResourceAsStream(path);
				if (stream != null) {
					return readImage(stream);
				} else {
					GuiLib.LOGGER.warn("No se encontró el recurso en /assets: {}", path);
				}
			} else {
				File file = new File(path);
				if (file.exists()) {
					return ImageIO.read(file);
				} else {
					GuiLib.LOGGER.warn("No se encontró el archivo externo: {}", path);
				}
			}
		} catch (Exception e) {
			GuiLib.LOGGER.error("Error al cargar la imagen desde: {}", path, e);
		}
		return missingTexture;
	}

}
