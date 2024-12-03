package deus.guilib.resource;

import deus.guilib.nodes.rendering.AdvancedGui;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import java.util.Objects;

public class Texture extends AdvancedGui {
	protected String path;
	protected int width;
	protected int height;
	protected int offsetY = 0;
	protected int offsetX = 0;
	protected int scale = 1;
	protected float uvScale = 0;
	protected int totalTextureSize = 0;
	protected String theme = "NONE";
	protected String name = "";
	protected int[][] frames;

	// Constructors
	public Texture(final String path, final int width, final int height) {
		this(path, width, height, 1);
	}

	public Texture(final String path, final int width, final int height, final int scale) {
		this(path, width, height, scale, width + height, 0);
	}

	public Texture(final String path, final int width, final int height, final int totalTextureSize, final float uvScale) {
		this(path, width, height, 1, width + height, uvScale);
	}

	public Texture(final String path, final int width, final int height, final int scale, final int totalTextureSize, final float uvScale) {

		this.path = path;
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.totalTextureSize = totalTextureSize;
		this.uvScale = uvScale;
	}

	public Texture(final String theme, final String name) {
		this.theme = theme;
		this.name = name;
	}

	// Getters and setters
	public String getPath() {
		return path;
	}

	public int getWidth() {
		return width * scale;
	}

	public int getHeight() {
		return height * scale;
	}

	public void setWidth(final int width) {
		this.width = width * scale;
	}

	public void setHeight(final int height) {
		this.height = height * scale;
	}

	public int getFrameY() {
		return offsetY * height;
	}

	public void setFrameY(final int offsetY) {
		this.offsetY = offsetY;
	}

	public int getFrameX() {
		return offsetX * width;
	}

	public void setFrameX(final int offsetX) {
		this.offsetX = offsetX;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(final int scale) {
		this.scale = scale;
	}

	public float getUvScale() {
		return uvScale;
	}

	public int getTotalTextureSize() {
		return totalTextureSize;
	}

	// Method to set frames
	public void setFrames(final int[][] frames) {
		this.frames = frames;
	}

	public void bindTexture(Minecraft mc) {
		try {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(getPath()));
		} catch (Exception e) {
			System.err.println("Error loading texture: " + e.getMessage());
		}
	}

	public void draw(Minecraft mc, int x, int y) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		bindTexture(mc);
		GL11.glDisable(GL11.GL_BLEND);

		if (getTotalTextureSize() != 0 && getUvScale() != 0) {
			drawTexturedModalRect(x, y, getFrameX(), getFrameY(), getWidth(), getHeight(), getTotalTextureSize(), getUvScale());
		} else {
			drawTexturedModalRect(x, y, getFrameX(), getFrameY(), getWidth(), getHeight());
		}
	}

	public void drawWithFrame(Minecraft mc, int x, int y, int frameX, int frameY) {
		GL11.glColor4f(111f, 1f, 1f, 1f);
		bindTexture(mc);
		GL11.glDisable(GL11.GL_BLEND);

		if (getTotalTextureSize() != 0 && getUvScale() != 0) {
			drawTexturedModalRect(x, y, frameX, frameY, getWidth(), getHeight(), getTotalTextureSize(), getUvScale());
		} else {
			drawTexturedModalRect(x, y, frameX, frameY, getWidth(), getHeight());
		}
	}

	public void drawWithFrame(Minecraft mc, int x, int y, int w, int h, int frameX, int frameY) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		bindTexture(mc);
		GL11.glDisable(GL11.GL_BLEND);

		if (getTotalTextureSize() != 0 && getUvScale() != 0) {
			drawTexturedModalRect(x, y, getFrameX(), getFrameY(), w, h, getTotalTextureSize(), getUvScale());
		} else {
			drawTexturedModalRect(x, y, frameX, frameY, w, h);
		}
	}


	public void draw(Minecraft mc, int x, int y, int w, int h) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		bindTexture(mc);
		GL11.glDisable(GL11.GL_BLEND);

		if (getTotalTextureSize() != 0 && getUvScale() != 0) {
			drawTexturedModalRect(x, y, getFrameX(), getFrameY(), w, h, getTotalTextureSize(), getUvScale());
		} else {
			drawTexturedModalRect(x, y, getFrameX(), getFrameY(), w, h);
		}
	}

	public void draw(Minecraft mc, int x, int y, int w, int h, int uvWidth, int uvHeight) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		bindTexture(mc);
		GL11.glDisable(GL11.GL_BLEND);

		if (uvHeight != 0 && uvWidth != 0) {
			drawTexturedModalRect((double) x, y, getFrameX(), getFrameY(), w, h, uvWidth, uvHeight);
		} else {
			drawTexturedModalRect(x, y, getFrameX(), getFrameY(), w, h);
		}
	}

	/**
	 * Gets a specific frame from the animation.
	 *
	 * @param index The index of the frame you want to get.
	 * @return An array with the frame coordinates.
	 */
	public int[] getFrame(int index) {
		if (frames != null && index >= 0 && index < frames.length) {
			return frames[index];
		}
		return new int[]{0, 0}; // Returns a default value if the index is not valid
	}
}
