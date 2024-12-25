package deus.guilib.nodes.types.containers;

import deus.guilib.gssl.Signal;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.config.Direction;
import deus.guilib.nodes.config.OverflowMode;
import deus.guilib.nodes.config.WheelState;
import deus.guilib.nodes.stylesystem.StyleParser;
import org.lwjgl.input.Mouse;

import java.util.Map;

public class ScrollLayout extends Layout {

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

	public ScrollLayout() {
		super();
	}

	public ScrollLayout(Map<String, String> attributes) {
		super(attributes);
		if (attributes.containsKey("direction")) {
			parseDirection(attributes.get("direction"));
		}

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
		drawScrollBar();
	}

	@Override
	protected void drawChild() {
		for (INode child : children) {

			if (overflowMode == OverflowMode.hide) {
				if (direction == Direction.horizontal) {
					if (child.getGx() >= this.getGx() && child.getGx() < this.getGx() + getWidth()) {
						child.draw();
					}
				} else if (direction == Direction.vertical) {
					if (child.getGy() >= this.getGy() && child.getGy() < this.getGy() + getHeight() - 20) {
						child.draw();
					}
				}
			} else {
				child.draw();
			}
		}
	}

	/* Scrollbar stuff */

	protected void drawScrollBar() {
		int scrollBarSize = 20;
		boolean scrollBarVisible = true;

		String scrollBarColor = "322211";


		if (styles.containsKey("scrollBarVisible")) {
			scrollBarVisible = (boolean) styles.get("scrollBarVisible");
		}

		if (styles.containsKey("scrollBarColor")) {
			scrollBarColor = (String) styles.get("scrollBarColor");
		}


		if (!scrollBarVisible) return;

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

		if (styles.containsKey("direction")) {
			parseDirection(attributes.get("direction"));
		}

		updateThumbPos();

	}

	@Override
	protected void updateChildren() {

		positionChildren();

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

	protected void positionChildren() {
		int currentOffset = 0;
		for (INode child : children) {
			if (direction == Direction.horizontal) {
				child.setGlobalPosition(currentOffset + scroll, gy);
				currentOffset += child.getWidth();
			} else if (direction == Direction.vertical) {
				child.setGlobalPosition(gx, currentOffset + scroll);
				currentOffset += child.getHeight();
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
