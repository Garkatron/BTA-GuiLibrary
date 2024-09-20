package deus.guilib.resource;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public class Texture extends Gui {
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
	protected ThemeManager themeManager = ThemeManager.getInstance();
	protected int[][] frames;

	// Constructors
	public Texture(String path, int width, int height) {
		this(path, width, height, 1);
	}

	public Texture(String path, int width, int height, int scale) {
		this(path, width, height, scale, 0, 0);
	}

	public Texture(String path, int width, int height, int totalTextureSize, float uvScale) {
		this(path, width, height, 1, totalTextureSize, uvScale);
	}

	public Texture(String path, int width, int height, int scale, int totalTextureSize, float uvScale) {
		this.path = path;
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.totalTextureSize = totalTextureSize;
		this.uvScale = uvScale;
	}

	public Texture(String theme, String name) {
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

	public int getFrameY() {
		return offsetY * height;
	}

	public void setFrameY(int offsetY) {
		this.offsetY = offsetY;
	}


	public int getFrameX() {
		return offsetX * width;
	}

	public void setFrameX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public float getUvScale() {
		return uvScale;
	}

	public int getTotalTextureSize() {
		return totalTextureSize;
	}

	// Method to set offsets
	public void setFrames(int[][] frames) {
		this.frames = frames;
	}

	public void draw(Minecraft mc, int x, int y) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		if (!Objects.equals(this.theme, "NONE") && !name.isEmpty()) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(themeManager.getProperties(theme).get(name)));
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(getPath()));
		}

		GL11.glDisable(GL11.GL_BLEND);

		if (getTotalTextureSize() != 0 && getUvScale() != 0) {
			drawTexturedModalRect(x, y, getFrameX(), getFrameY(), getWidth(), getHeight(), getTotalTextureSize(), getUvScale());
		} else {
			drawTexturedModalRect(x, y, getFrameX(), getFrameY(), getWidth(), getHeight());
		}

	}

	public void draw(Minecraft mc, int x, int y, int w, int h) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		if (!Objects.equals(this.theme, "NONE") && !name.isEmpty()) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(themeManager.getProperties(theme).get(name)));
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(getPath()));
		}

		GL11.glDisable(GL11.GL_BLEND);

		if (getTotalTextureSize() != 0 && getUvScale() != 0) {
			drawTexturedModalRect(x, y, getFrameX(), getFrameY(), w, h, getTotalTextureSize(), getUvScale());
		} else {
			drawTexturedModalRect(x, y, getFrameX(), getFrameY(), w, h);
		}

	}
}
