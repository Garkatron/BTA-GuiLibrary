package deus.guilib.nodes.types.representation;

import deus.guilib.nodes.Node;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.resource.Texture;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class Image extends Node  {

	public Image() {
		super();
	}

	public Image(Map<String, String> attributes) {
		super(attributes);
	}

	@Override
	protected void drawIt() {
		super.drawIt();
		drawImage();
	}

	protected void drawImage() {
		if (attributes.containsKey("src")) {
			GL11.glColor4f(1f, 1f, 1f, 1f);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(attributes.get("src")));
			GL11.glDisable(GL11.GL_BLEND);

			int scaleW = 256, scaleH = 256;

			if (styles.containsKey("imageScale")) {
				scaleW = scaleH = StyleParser.parsePixels((String) styles.get("imageScale"));
			}

			if (styles.containsKey("imageScaleWidth")) {
				scaleW = StyleParser.parsePixels((String) styles.get("imageScaleWidth"));
			}

			if (styles.containsKey("imageScaleHeight")) {
				scaleH = StyleParser.parsePixels((String) styles.get("imageScaleHeight"));
			}


			drawTexturedModalRect(gx, gy, scaleW, scaleH, getWidth(), getHeight());
		}
	}
}
