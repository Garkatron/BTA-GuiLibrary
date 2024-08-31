package deus.guilib.rendering.resource;


public class Texture {
	private final String path;
	private final int width;
	private final int height;
	private int offsetY = 0;
	private int offsetX = 0;
	private int scale = 1;

	// Constructor
	public Texture(String path, int width, int height) {
		this.path = path;
		this.width = width;
		this.height = height;
	}

	// Getters
	public String getPath() {
		return path;
	}

	public int getWidth() {
		return width * scale;
	}

	public int getHeight() {
		return height * scale;
	}

	public int getOffsetY() {
		return offsetY * height;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public int getOffsetX() {
		return offsetX * width;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

}
