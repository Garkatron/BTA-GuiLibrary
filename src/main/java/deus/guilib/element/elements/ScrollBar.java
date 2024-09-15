package deus.guilib.element.elements;

import deus.guilib.resource.Texture;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 * A scrollbar element that allows scrolling within a defined range.
 */
public class ScrollBar extends ClickableElement {

	private int length = 3; // Number of segments or height multiplier
	private int scrollPosition = 0; // Current position of the scrollbar thumb
	private boolean scrollHorizontal = false;
	private boolean scrollVertical = true;

	// Dimensions and limits
	private int maxScrollPosition = 0;
	private int minScrollPosition = 0;

	// Textures for scrollbar and thumb
	private Texture scrollbarTexture;
	private Texture thumbTexture;

	// Dimensions of the thumb in pixels
	private int thumbWidth = 16;
	private int thumbHeight = 16;

	// Padding for scrollbar
	private int paddingX = 8;
	private int paddingY = 8;

	public ScrollBar() {
		super(new Texture("assets/textures/gui/ScrollBar.png", 16, 96));
		scrollbarTexture = new Texture("assets/textures/gui/ScrollBar.png", 16, 96);
		thumbTexture = new Texture("assets/textures/gui/ScrollBar.png", 16, 19);
		updateScrollLimits();
	}

	/**
	 * Sets the number of segments for the scrollbar.
	 * @param length Number of segments.
	 * @return This ScrollBar instance for chaining.
	 */
	public ScrollBar setLength(int length) {
		this.length = length;
		updateScrollLimits();
		return this;
	}

	/**
	 * Gets the number of segments for the scrollbar.
	 * @return Number of segments.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Updates the scroll limits based on the scrollbar orientation.
	 */
	private void updateScrollLimits() {
		if (scrollHorizontal) {
			maxScrollPosition = getWidth() - thumbWidth;
			minScrollPosition = 0;
		} else if (scrollVertical) {
			maxScrollPosition = getHeight() - thumbHeight;
			minScrollPosition = 0;
		}
	}

	@Override
	protected void drawIt() {
		if (mc == null) {
			System.out.println("Error on drawIt: [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}
		GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(scrollbarTexture.getPath()));
		GL11.glDisable(GL11.GL_BLEND);

		// Draw scrollbar segments
		if (scrollHorizontal) {
			for (int i = 0; i < length; i++) {
				int textureX = (i == length - 1) ? 32 : (i > 0) ? 16 : 0;
				drawTexturedModalRect(gx + (i * 16), gy, textureX, 48, 16, 16);
			}
			// Draw thumb
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(thumbTexture.getPath()));
			drawTexturedModalRect(gx + Math.min(scrollPosition, maxScrollPosition), gy, 48, 48, thumbWidth + 1, 16);
		} else if (scrollVertical) {
			for (int i = 0; i < length; i++) {
				int textureY = (i == length - 1) ? 32 : (i > 0) ? 16 : 0;
				drawTexturedModalRect(gx, gy + (i * 16), 0, textureY, 16, 16);
			}
			// Draw thumb
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(thumbTexture.getPath()));
			drawTexturedModalRect(gx, gy + Math.min(scrollPosition, maxScrollPosition), 16, 0, 16, thumbHeight + 1);
		}
	}

	@Override
	public void update(int mouseX, int mouseY) {
		super.update(mouseX, mouseY);
		if (isHovered() && Mouse.isButtonDown(0)) {
			if (scrollHorizontal) {
				int mouseXRelative = mouseX - gx - paddingX;
				scrollPosition = Math.max(minScrollPosition, Math.min(mouseXRelative, maxScrollPosition));
			} else if (scrollVertical) {
				int mouseYRelative = mouseY - gy - paddingY;
				scrollPosition = Math.max(minScrollPosition, Math.min(mouseYRelative, maxScrollPosition));
			}
		}
	}

	@Override
	public int getHeight() {
		return scrollVertical ? length * 16 : 16;
	}

	@Override
	public int getWidth() {
		return scrollHorizontal ? length * 16 : 16;
	}

	/**
	 * Returns the current scroll position as a percentage of the maximum scroll range.
	 * @return Scroll position percentage (0.0 to 1.0).
	 */
	public float getScrollPercentage() {
		return (maxScrollPosition == minScrollPosition) ? 0 :
			(float) (scrollPosition - minScrollPosition) / (maxScrollPosition - minScrollPosition);
	}

	/**
	 * Configures the scrollbar for horizontal scrolling.
	 * @return This ScrollBar instance for chaining.
	 */
	public ScrollBar setScrollHorizontal() {
		scrollHorizontal = true;
		scrollVertical = false;
		updateScrollLimits();
		return this;
	}

	/**
	 * Configures the scrollbar for vertical scrolling.
	 * @return This ScrollBar instance for chaining.
	 */
	public ScrollBar setScrollVertical() {
		scrollHorizontal = false;
		scrollVertical = true;
		updateScrollLimits();
		return this;
	}

	/**
	 * Sets the texture for the scrollbar.
	 * @param texture Texture to be used for the scrollbar.
	 * @return This ScrollBar instance for chaining.
	 */
	public ScrollBar setScrollbarTexture(Texture texture) {
		this.scrollbarTexture = texture;
		return this;
	}

	/**
	 * Sets the texture for the scrollbar thumb.
	 * @param texture Texture to be used for the thumb.
	 * @return This ScrollBar instance for chaining.
	 */
	public ScrollBar setThumbTexture(Texture texture) {
		this.thumbTexture = texture;
		return this;
	}

	/**
	 * Sets the dimensions of the thumb.
	 * @param width Width of the thumb.
	 * @param height Height of the thumb.
	 * @return This ScrollBar instance for chaining.
	 */
	public ScrollBar setThumbDimensions(int width, int height) {
		this.thumbWidth = width;
		this.thumbHeight = height;
		updateScrollLimits();
		return this;
	}

	/**
	 * Sets the padding for the scrollbar.
	 * @param paddingX Horizontal padding.
	 * @param paddingY Vertical padding.
	 * @return This ScrollBar instance for chaining.
	 */
	public ScrollBar setPadding(int paddingX, int paddingY) {
		this.paddingX = paddingX;
		this.paddingY = paddingY;
		return this;
	}

	@Override
	public void onPush() {
		// Optional: Implement custom behavior when the scrollbar is pressed.
	}

	@Override
	public void onPushOut() {
		// Optional: Implement custom behavior when the press is released outside the scrollbar.
	}

	@Override
	public void onRelease() {
		// Optional: Implement custom behavior when the scrollbar is released.
	}

	@Override
	public void whilePressed() {
		// Optional: Implement custom behavior while the scrollbar is pressed.
	}
}
