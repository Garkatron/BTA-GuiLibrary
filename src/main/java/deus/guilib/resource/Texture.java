package deus.guilib.resource;

/**
 * Represents a texture used for rendering with properties such as path, dimensions, offsets, and scaling.
 */
public class Texture {
	private final String path;
	private final int width;
	private final int height;
	private int offsetY = 0;
	private int offsetX = 0;
	private int scale = 1;

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
}
