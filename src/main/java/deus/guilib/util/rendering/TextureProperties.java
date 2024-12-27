package deus.guilib.util.rendering;

public record TextureProperties(String path, int width, int height, Border border,
								boolean stretchInner, TextureMode type) {

	public record Border(int top, int bottom, int left, int right) {
	}
}
