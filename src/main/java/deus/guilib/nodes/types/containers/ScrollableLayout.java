package deus.guilib.nodes.types.containers;

import deus.guilib.gssl.Signal;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.config.Direction;
import deus.guilib.nodes.config.OverflowMode;
import deus.guilib.nodes.config.WheelState;
import deus.guilib.nodes.stylesystem.StyleParser;
import net.minecraft.client.render.Scissor;
import org.lwjgl.input.Mouse;

import java.util.Map;

public class ScrollableLayout extends Layout {

	/* Scroll Options */
	protected int maxScroll = 0;
	protected int minScroll = -200;

	protected int scroll = 0;
	protected int scrollSpeed = 5;

	/* Scroll thumb stuff */
	protected int thumbPos = 0;

	/* ScrollLayout options */
	protected OverflowMode overflowMode = OverflowMode.hide;

	/* Signals */
	protected Signal<WheelState> wheelChanged = new Signal<>();

	public ScrollableLayout() {
		super();
	}

	public ScrollableLayout(Map<String, String> attributes) {
		super(attributes);


		wheelChanged.connect(
			(r, w) -> {
				if (w == WheelState.down) {
					scroll -= scrollSpeed;
				} else {
					scroll += scrollSpeed;
				}
			}
		);
	}

	/* Draw methods */

	@Override
	protected void drawIt() {
		super.drawIt();
		boolean scrollBarVisible = true;

		if (styles.containsKey("scrollBarVisible")) {
			scrollBarVisible = (boolean) styles.get("scrollBarVisible");
		}

		if (scrollBarVisible) {
			drawScrollBar();
		}
	}

	@Override
	protected void drawChild() {
		for (INode child : children) {
			if (overflowMode==OverflowMode.view) {
				child.draw();
			} else {
				Scissor.scissor(gx+1,gy+1, getWidth()-1, getHeight()-1, child::draw);
			}
		}
	}

	/* Scrollbar stuff */

	protected void drawScrollBar() {
		int scrollBarSize = 20;

		String scrollBarColor = "322211";


		if (styles.containsKey("scrollBarColor")) {
			scrollBarColor = (String) styles.get("scrollBarColor");
		}

		if (styles.containsKey("scrollBarSize")) {
			scrollBarSize = StyleParser.parsePixels((String) styles.get("scrollBarSize"));
		}

		if (direction == Direction.horizontal) {
			this.drawRect(this.gx, this.gy + getHeight(), this.gx + getWidth(), this.gy + getHeight() + scrollBarSize, StyleParser.parseColorToARGB(scrollBarColor));
		} else if (direction == Direction.vertical) {
			this.drawRect(this.gx + getWidth(), this.gy, this.gx + getWidth() + scrollBarSize, this.gy + getHeight(), StyleParser.parseColorToARGB(scrollBarColor));
		}

		drawScrollThumb();
	}

	protected void drawScrollThumb() {
		int thumbWidth = 20;
		int thumbHeight = 20;
		String scrollBarThumbColor = "FF00FF";

		if (styles.containsKey("thumbWidth")) {
			thumbWidth = StyleParser.parsePixels((String) styles.get("thumbWidth"));
		}

		if (styles.containsKey("thumbHeight")) {
			thumbHeight = StyleParser.parsePixels((String) styles.get("thumbHeight"));
		}


		if (styles.containsKey("scrollBarThumbColor")) {
			scrollBarThumbColor = (String) styles.get("scrollBarThumbColor");
		}


		if (direction == Direction.horizontal) {
			this.drawRect(
				thumbPos,
				this.gy + getHeight(),
				thumbPos + thumbWidth,
				this.gy + getHeight() + thumbHeight,
				StyleParser.parseColorToARGB(scrollBarThumbColor)
			);
		} else if (direction == Direction.vertical) {
			this.drawRect(
				this.gx + getWidth(),
				gy + thumbPos,
				this.gx + getWidth() + thumbWidth,
				gy + thumbPos + thumbHeight,
				StyleParser.parseColorToARGB(scrollBarThumbColor)
			);
		}
	}

	/* Update methods */

	@Override
	protected void updateIt() {
		super.updateIt();

		if (attributes.containsKey("direction")) {
			parseDirection(attributes.get("direction"));
		}

		if (styles.containsKey("layoutSpacing")) {
			spaceBetween = StyleParser.parsePixels((String) styles.get("layoutSpacing"));
		}

		updateWheelState();
		updateScrollLimits();
		updateOverflowMode();


		boolean isMinScrollInfinite = attributes.containsKey("minScroll") && "infinite".equals(attributes.get("minScroll"));
		boolean isMaxScrollInfinite = attributes.containsKey("maxScroll") && "infinite".equals(attributes.get("maxScroll"));

		if (!isMinScrollInfinite) {
			scroll = Math.max(minScroll, scroll);
		}

		if (!isMaxScrollInfinite) {
			scroll = Math.min(scroll, maxScroll);
		}


		updateThumbPos();

	}

	@Override
	protected void updateChildren() {

		int currentOffset = direction==Direction.horizontal ? gx : gy;
		for (INode child : children) {

			if (direction == Direction.horizontal) {
				int yPosition = itemsCentered
					? (gy + (getHeight() / 2)) - (child.getHeight() / 2)
					: gy;
				child.setGlobalPosition(currentOffset + scroll, yPosition);

				currentOffset += child.getWidth() + spaceBetween;
			} else if (direction == Direction.vertical) {
				int xPosition = itemsCentered
					? (gx + (getWidth() / 2)) - (child.getWidth()/2)
					: gx;
				child.setGlobalPosition(xPosition, currentOffset + scroll);

				currentOffset += child.getHeight() + spaceBetween;
			}

			if (!(overflowMode == OverflowMode.hide)) {
				if (direction == Direction.horizontal) {
					if (child.getGx() >= this.getGx() && child.getGx() < this.getGx() + getWidth()) {
						child.update();
					}
				} else if (direction == Direction.vertical) {
					if (child.getGy() >= this.getGy() && child.getGy() < this.getGy() + getHeight() - 20) {
						child.update();
					}
				}
			} else {
				child.update();
			}

		}

	}

	protected void updateScrollLimits() {
		if (attributes.containsKey("maxScroll")) {
			if ("infinite".equals(attributes.get("maxScroll"))) {
				maxScroll = Integer.MAX_VALUE;
			} else {
				maxScroll = Integer.parseInt(attributes.get("maxScroll"));
			}
		} else {
			maxScroll = 0;
		}

		if (attributes.containsKey("minScroll")) {
			if ("infinite".equals(attributes.get("minScroll"))) {
				minScroll = -Integer.MAX_VALUE;
			} else {
				minScroll = Integer.parseInt(attributes.get("minScroll"));
			}
		} else {
			minScroll = (direction == Direction.vertical) ? -height : -width;
		}
	}

	protected void updateThumbPos() {
		float speedFactor = scrollSpeed;

		if (direction == Direction.horizontal) {
			thumbPos = (int) ((getWidth() - 20) * ((float) (scroll - minScroll) / (maxScroll - minScroll)) * speedFactor);
		} else if (direction == Direction.vertical) {
			thumbPos = (int) ((getHeight() - 20) * (1 - (float) (scroll - minScroll) / (maxScroll - minScroll)) * speedFactor);
		}
	}

	protected void updateWheelState() {
		int wheelDelta = Mouse.getDWheel();
		WheelState wheelState = WheelState.none;

		if (wheelDelta != 0) {
			wheelState = (wheelDelta > 0) ? WheelState.up : WheelState.down;
			wheelChanged.emit(wheelState);
		}
	}

	protected void updateOverflowMode() {
		if (styles.containsKey("overflow")) {
			overflowMode = parseOverflowMode((String) styles.get("overflow"));
		}
	}

	/* Auxiliary methods*/


	protected OverflowMode parseOverflowMode(String str) {
		if (str.equals("hide")) {
			return OverflowMode.hide;
		} else if (str.equals("view")) {
			return OverflowMode.view;
		} else {
			return OverflowMode.hide;
		}
	}
}
