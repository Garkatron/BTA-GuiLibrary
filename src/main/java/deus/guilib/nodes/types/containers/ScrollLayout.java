package deus.guilib.nodes.types.containers;

import deus.guilib.error.Error;
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
	protected int scrollSped = 5;

	protected boolean scrollBarInverted = false;

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
					scroll -= scrollSped;
				} else {
					scroll += scrollSped;
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
					if (child.getGy() >= this.getGy() && child.getGy() < this.getGy() + getHeight()) {
						child.draw();
					}
				}
			}
		}
	}

	/* Scrollbar stuff */

	protected void drawScrollBar() {
		int scrollBarSize = 20;
		boolean scrollBarVisible = true;

		if (styles.containsKey("scrollBarVisible")) {
			scrollBarVisible = (boolean) styles.get("scrollBarVisible");
		}

		if (!scrollBarVisible) return;

		if (styles.containsKey("scrollBarSize")) {
			scrollBarSize = StyleParser.parsePixels((String) styles.get("scrollBarSize"));
		}

		if (direction == Direction.horizontal) {
			this.drawRect(this.gx, this.gy + getHeight(), this.gx + getWidth(), this.gy + getHeight() + scrollBarSize, StyleParser.parseColorToARGB("FF00FF"));
		} else if (direction == Direction.vertical) {
			this.drawRect(this.gx + getWidth(), this.gy, this.gx + getWidth() + scrollBarSize, this.gy + getHeight(), StyleParser.parseColorToARGB("FF00FF"));
		}

		drawScrollThumb();
	}

	protected void drawScrollThumb() {
		int thumbWidth = 20;
		int thumbHeight = 20;

		if (styles.containsKey("thumbWidth")) {
			thumbWidth = StyleParser.parsePixels((String) styles.get("thumbWidth"));
		}

		if (styles.containsKey("thumbHeight")) {
			thumbHeight = StyleParser.parsePixels((String) styles.get("thumbHeight"));
		}

		if (direction == Direction.horizontal) {
			this.drawRect(
				scrollBarInverted ? (getWidth() - thumbPos - thumbWidth) : thumbPos,
				this.gy + getHeight(),
				scrollBarInverted ? (getWidth() - thumbPos) : (thumbPos + thumbWidth),
				this.gy + getHeight() + thumbHeight,
				StyleParser.parseColorToARGB("00FF00")
			);
		} else if (direction == Direction.vertical) {
			this.drawRect(
				this.gx + getWidth(),
				scrollBarInverted ? gy + (getHeight() - thumbPos - thumbHeight) : thumbPos,
				this.gx + getWidth() + thumbWidth,
				scrollBarInverted ? gy + (getHeight() - thumbPos) : (thumbPos + thumbHeight),
				StyleParser.parseColorToARGB("00FF00")
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

		if (styles.containsKey("scrollBarInverted")) {
			this.scrollBarInverted = (boolean) styles.get("scrollBarInverted");
		}

		boolean isMinScrollInfinite = attributes.containsKey("minScroll") && "infinite".equals(attributes.get("minScroll"));
		boolean isMaxScrollInfinite = attributes.containsKey("maxScroll") && "infinite".equals(attributes.get("maxScroll"));


		if (!isMinScrollInfinite) {
			scroll = Math.max(minScroll + 20, scroll);

		}

		if (!isMaxScrollInfinite) {
			scroll = Math.min(scroll, maxScroll);

		}

		if (styles.containsKey("direction")) {
			parseDirection(attributes.get("direction"));
		}

		positionChildren();
		updateThumbPos();

	}
	protected void updateScrollLimits() {
		if (attributes.containsKey("maxScroll")) {
			String data = attributes.get("maxScroll");
			if (data.equals("infinite")) {
				maxScroll = 0;

			} else {
				maxScroll = Integer.parseInt(attributes.get("maxScroll"));
			}
		} else {
			maxScroll = 0;
		}

		if (attributes.containsKey("minScroll")) {
			String data = attributes.get("minScroll");
			if (data.equals("infinite")) {
				minScroll = 0;

			} else {
				minScroll = Integer.parseInt(attributes.get("minScroll"));
			}
		} else {
			minScroll = (direction == Direction.vertical) ? -height : -width;
		}
	}

	protected void updateThumbPos() {
		if (direction == Direction.horizontal) {
			thumbPos = scrollBarInverted
				? (int) ((getWidth() - 20) * (1 - (float) (scroll - minScroll) / (maxScroll - minScroll)))
				: (int) ((getWidth() - 20) * ((float) (scroll - minScroll) / (maxScroll - minScroll)));
		} else if (direction == Direction.vertical) {
			thumbPos = scrollBarInverted
				? (int) ((getHeight() - 20) * (1 - (float) (scroll - minScroll) / (maxScroll - minScroll)))
				: (int) ((getHeight() - 20) * ((float) (scroll - minScroll) / (maxScroll - minScroll)));
		}
	}

	protected void updateWheelState() {
		int wheelDelta = Mouse.getDWheel();
		WheelState wheelState = WheelState.none;

		if (wheelDelta != 0) {
			if (scrollBarInverted) {
				wheelState = (wheelDelta < 0) ? WheelState.up : WheelState.down;
			} else {
				wheelState = (wheelDelta < 0) ? WheelState.down : WheelState.up;
			}
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
				child.setGlobalPosition(currentOffset - scroll, gy);
				currentOffset += child.getWidth();
			} else if (direction == Direction.vertical) {
				child.setGlobalPosition(gx, currentOffset - scroll);
				currentOffset += child.getHeight();
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
