package deus.guilib.resource;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

/**
 * Represents a texture used for rendering with properties such as path, dimensions, offsets, and scaling.
 */
public class Texture extends Gui {
	private String path;
	private int width;
	private int height;
	private int offsetY = 0;
	private int offsetX = 0;
	private int scale = 1;
	private float uvScale = 0;  // Escala UV para texturas de 256x256 (0.00390625)
	private int totalTextureSize = 0;      // Tamaño total de la textura (256x256)
	private String theme = "NONE";
	private String name = "";
	protected ThemeManager themeManager = ThemeManager.getInstance();

	/**
	 * Constructs a Texture instance with the specified path, width, and height.
	 *
	 * @param path The file path of the texture.
	 * @param width The width of the texture.
	 * @param height The height of the texture.
	 */
	public Texture(String path, int width, int height) {
		this.path = path;
		this.width = width;
		this.height = height;
	}

	public Texture(String theme, String name) {
		this.theme = theme;
		this.name = name;
	}

	public Texture(String path, int width, int height, int scale) {
		this.path = path;
		this.width = width;
		this.height = height;
		this.scale = scale;
	}

	public Texture(String path, int width, int height, int totalTextureSize, float uvScale) {
		this.path = path;
		this.width = width;
		this.height = height;
		this.uvScale = uvScale;
		this.totalTextureSize = totalTextureSize;
	}


	/**
	 * Returns the file path of the texture.
	 *
	 * @return The path of the texture.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Returns the width of the texture, adjusted by the scale factor.
	 *
	 * @return The width of the texture.
	 */
	public int getWidth() {
		return width * scale;
	}

	/**
	 * Returns the height of the texture, adjusted by the scale factor.
	 *
	 * @return The height of the texture.
	 */
	public int getHeight() {
		return height * scale;
	}

	/**
	 * Returns the vertical offset, scaled by the texture's height.
	 *
	 * @return The vertical offset.
	 */
	public int getOffsetY() {
		return offsetY * height;
	}

	/**
	 * Sets the vertical offset.
	 *
	 * @param offsetY The vertical offset.
	 */
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * Returns the horizontal offset, scaled by the texture's width.
	 *
	 * @return The horizontal offset.
	 */
	public int getOffsetX() {
		return offsetX * width;
	}

	/**
	 * Sets the horizontal offset.
	 *
	 * @param offsetX The horizontal offset.
	 */
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * Returns the scaling factor.
	 *
	 * @return The scale of the texture.
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * Sets the scaling factor.
	 *
	 * @param scale The scaling factor.
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	public float getUvScale() {
		return uvScale;
	}

	public int getTotalTextureSize() {
		return totalTextureSize;
	}

	public void draw(Minecraft mc, int x, int y) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		if (!Objects.equals(this.theme, "NONE") && !name.isEmpty()) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(themeManager.getProperties(theme).get(name)));
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(getPath()));
		}

		GL11.glDisable(GL11.GL_BLEND);

		if(getTotalTextureSize()!=0 && getUvScale()!=0) {
			drawTexturedModalRect(x, y, getOffsetX(), getOffsetY(), getWidth(), getHeight(), getTotalTextureSize(), getUvScale());
		} else {
			drawTexturedModalRect(x, y, getOffsetX(), getOffsetY(), getWidth(), getHeight());
		}
	}

}
