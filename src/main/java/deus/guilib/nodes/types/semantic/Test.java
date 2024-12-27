package deus.guilib.nodes.types.semantic;

import deus.guilib.nodes.Node;
import deus.guilib.resource.Texture;
import deus.guilib.util.rendering.RenderUtils;
import deus.guilib.util.rendering.TextureMode;
import deus.guilib.util.rendering.TextureProperties;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class Test extends Node {

	public Test() {
		super();
	}

	public Test(Map<String, String> attributes) {




	}

	@Override
	protected void drawBackgroundImage() {
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");
			//assets/guilib/textures/gui/themes/default/nine_slice/ns_button.png

			if (t.getPath().equals("transparent")) return;


			TextureProperties properties = new TextureProperties(
				200,
				20,
				new TextureProperties.Border(2, 3, 2, 2),
				false,
				TextureMode.NINE_SLICE// type: NINE_SLICE
			);
			t.drawGuiTexture(t, properties, 0, 0, 222, 222);

		}
	}
}
