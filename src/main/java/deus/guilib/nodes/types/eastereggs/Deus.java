package deus.guilib.nodes.types.eastereggs;

import deus.guilib.nodes.Node;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class Deus extends Node {


	public Deus() {
		super();
	}

	public Deus(Map<String, String> attributes) {
		super(attributes);
	}

	@Override
	protected void drawBackgroundImage() {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/assets/guilib/textures/people/deus.png"));
		GL11.glDisable(GL11.GL_BLEND);
		drawTexturedModalRect(gx, gy,0,0, 32, 32);
	}
}
