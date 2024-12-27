package deus.guilib.util.rendering;

public record TextureProperties(int width, int height, deus.guilib.util.rendering.TextureProperties.Border border,
								boolean stretchInner, TextureMode type) {

	public record Border(int top, int bottom, int left, int right) {
	}
}
