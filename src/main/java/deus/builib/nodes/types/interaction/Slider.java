package deus.builib.nodes.types.interaction;

import deus.builib.nodes.stylesystem.StyleParser;
import deus.builib.nodes.types.templates.ClickableElement;
import deus.builib.util.GuiHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.Map;

/**
 * A scrollbar element that allows scrolling within a defined range.
 */
@Deprecated(since = "2.0.0p3", forRemoval = false)
public class Slider extends ClickableElement {

	private int scrollPosition = 0; // Current position of the scrollbar thumb
	private String direction = "horizontally";
	protected int length = 3;

	// Dimensions and limits
	private int maxScrollPosition = 0;
	private int minScrollPosition = 0;


	// Dimensions of the thumb in pixels
	private int thumbWidth = 16;
	private int thumbHeight = 16;

	// Padding for scrollbar
	private int paddingX = 8;
	private int paddingY = 8;

	public Slider() {
		super();
	}

	public Slider(Map<String, String> attr) {
		super(attr);
	}

	/**
	 * Updates the scroll limits based on the scrollbar orientation.
	 */
	private void updateScrollLimits() {
		if (direction.equals("horizontally")) {
			maxScrollPosition = getWidth() - thumbWidth;
			minScrollPosition = 0;
		} else if (direction.equals("vertically")) {
			maxScrollPosition = getHeight() - thumbHeight;
			minScrollPosition = 0;
		}
	}

	@Override
	protected void drawIt() {
		super.drawIt();

		int bgwidth, bgheight;

		if (styles.containsKey("bgWidth")) {
			bgwidth = StyleParser.parsePixels((String) styles.get("bgWidth"));
		} else {
			bgwidth = 0;
		}
		if (styles.containsKey("bgHeight")) {
			bgheight = StyleParser.parsePixels((String) styles.get("bgHeight"));
		} else {
			bgheight = 0;
		}


		if (direction.equals("horizontally")) {
			for (int i = 0; i < length; i++) {
				int textureX = (i == length - 1) ? 32 : (i > 0) ? 16 : 0;
				drawTexturedModalRect(gx + (i * 16), gy, textureX, 48, 16, 16);
			}
			// Draw thumb

			int finalX = gx + Math.min(scrollPosition, maxScrollPosition);
			//drawTexture(mc, pt, finalX, gy, thumbWidth + 1, bgheight==0 ? height : bgheight);
			//drawTexturedModalRect(, gy, 48, 48, , 16);
		} else if (direction.equals("vertically")) {
			for (int i = 0; i < length; i++) {
				int textureY = (i == length - 1) ? 32 : (i > 0) ? 16 : 0;
				drawTexturedModalRect(gx, gy + (i * 16), 0, textureY, 16, 16);
			}
			// Draw thumb
			//GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(thumbTexture.getPath()));
			drawTexturedModalRect(gx, gy + Math.min(scrollPosition, maxScrollPosition), 16, 0, 16, thumbHeight + 1);
		}


	}

	protected void updateLength() {
		if (styles.containsKey("scrollBarLength")) {
			length = (int) styles.get("scrollBarLength");
		}
	}

	@Override
	protected void updateIt() {
		super.updateIt();

		updateDirection();
		updateLength();
		updateScrollLimits();

		if (isHovered() && Mouse.isButtonDown(0)) {
			if (direction.equals("horizontally")) {
				int mouseXRelative = GuiHelper.mouseX - gx - paddingX;
				scrollPosition = Math.max(minScrollPosition, Math.min(mouseXRelative, maxScrollPosition));
			} else if (direction.equals("vertically")) {
				int mouseYRelative = GuiHelper.mouseY - gy - paddingY;
				scrollPosition = Math.max(minScrollPosition, Math.min(mouseYRelative, maxScrollPosition));
			}
		}
	}

	protected void updateDirection() {
		if (styles.containsKey("direction")) {
			direction = (String) styles.get("direction");
		}
	}

	@Override
	public int getHeight() {
		return direction.equals("vertically") ? length * 16 : 16;
	}

	@Override
	public int getWidth() {
		return direction.equals("horizontally") ? length * 16 : 16;
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
	 * Sets the dimensions of the thumb.
	 * @param width Width of the thumb.
	 * @param height Height of the thumb.
	 * @return This ScrollBar instance for chaining.
	 */
	public Slider setThumbDimensions(int width, int height) {
		this.thumbWidth = width;
		this.thumbHeight = height;
		return this;
	}

	/**
	 * Sets the padding for the scrollbar.
	 * @param paddingX Horizontal padding.
	 * @param paddingY Vertical padding.
	 * @return This ScrollBar instance for chaining.
	 */
	public Slider setPadding(int paddingX, int paddingY) {
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
