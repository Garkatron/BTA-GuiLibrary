package deus.guilib.nodes.types.containers;

import deus.guilib.GuiLib;
import deus.guilib.error.Error;
import deus.guilib.gssl.Signal;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.config.Direction;
import deus.guilib.nodes.config.OverflowMode;
import deus.guilib.nodes.config.WheelState;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.nodes.types.interaction.Slider;
import deus.guilib.util.math.PlacementHelper;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.Map;

public class ScrollLayout extends Layout {

	protected int scroll = 0;

	protected Signal<WheelState> wheelChanged = new Signal<>();

	protected int maxScroll = 0;
	protected int minScroll = -200;
	protected int scrollSped = 5;

	protected int thumbPos = 0;

	// Nueva propiedad para controlar la inversión de la barra de desplazamiento
	protected boolean scrollBarInverted = false;

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

	@Override
	protected void drawIt() {
		super.drawIt();
		drawScrollBar();
	}

	protected void drawScrollBar() {

		int thumbWidth = 20;
		int thumbHeight = 20;

		int scrollBarSize = 20;

		boolean scrollBarVisible = true;

		if (styles.containsKey("scrollBarVisible")) {
			scrollBarVisible = (boolean) styles.get("scrollBarVisible");
		}

		if (!scrollBarVisible) return;

		if (styles.containsKey("thumbWidth")) {
			thumbWidth = StyleParser.parsePixels((String) styles.get("thumbWidth"));
		}

		if (styles.containsKey("thumbHeight")) {
			thumbHeight = StyleParser.parsePixels((String) styles.get("thumbHeight"));
		}

		if (styles.containsKey("scrollBarSize")) {
			scrollBarSize = StyleParser.parsePixels((String) styles.get("scrollBarSize"));
		}

		if (direction == Direction.horizontal) {
			this.drawRect(this.gx, this.gy + getHeight(), this.gx + getWidth(), this.gy + getHeight() + scrollBarSize, StyleParser.parseColorToARGB("FF00FF"));

			// Si el modo está invertido, cambiamos la dirección del desplazamiento
			if (scrollBarInverted) {
				this.drawRect(getWidth() - thumbPos - thumbWidth, this.gy + getHeight(), getWidth() - thumbPos, this.gy + getHeight() + thumbHeight, StyleParser.parseColorToARGB("00FF00"));
			} else {
				this.drawRect(thumbPos, this.gy + getHeight(), thumbPos + thumbWidth, this.gy + getHeight() + thumbHeight, StyleParser.parseColorToARGB("00FF00"));
			}

		} else if (direction == Direction.vertical) {
			this.drawRect(this.gx + getWidth(), this.gy, this.gx + getWidth() + scrollBarSize, this.gy + getHeight(), StyleParser.parseColorToARGB("FF00FF"));

			// Si el modo está invertido, cambiamos la dirección del desplazamiento
			if (scrollBarInverted) {
				this.drawRect(this.gx + getWidth(), getHeight() - thumbPos - thumbHeight, this.gx + getWidth() + thumbWidth, getHeight() - thumbPos, StyleParser.parseColorToARGB("00FF00"));
			} else {
				this.drawRect(this.gx + getWidth(), thumbPos, this.gx + getWidth() + thumbWidth, thumbPos + thumbHeight, StyleParser.parseColorToARGB("00FF00"));
			}
		}
	}

	protected OverflowMode parseOverflowMode(String str) {
		if (str.equals("hide"))    { return OverflowMode.hide; }
		else if (str.equals("view")) { return OverflowMode.view; }
		else {
			return OverflowMode.hide;
		}
	}

	@Override
	protected void drawChild() {
		if (mc == null) {
			throw new IllegalStateException(Error.MISSING_MC.getMessage());
		}
		for (INode child : children) {
			PlacementHelper.positionChild(child, this);

			OverflowMode overflowMode = OverflowMode.hide;

			if (styles.containsKey("overflow")) {
				overflowMode = parseOverflowMode((String) styles.get("overflow"));
			}

			if (overflowMode == OverflowMode.hide) {
				if (direction == Direction.horizontal) {
					if (child.getGx() < getWidth()) {
						child.draw();
					}
				} else if (direction == Direction.vertical) {
					if (child.getGy() < getHeight()) {
						child.draw();
					}
				}
			}
		}
	}

	@Override
	protected void updateIt() {
		super.updateIt();

		updateWheelState();
		updateScrollLimits();

		if (styles.containsKey("scrollBarInverted")) {
			this.scrollBarInverted = (boolean) styles.get("scrollBarInverted");
		}

		scroll = Math.max(minScroll + 20, Math.min(scroll, maxScroll));

		if (styles.containsKey("direction")) {
			parseDirection(attributes.get("direction"));
		}

		int currentOffset = 0;
		for (INode child : children) {
			if (direction == Direction.horizontal) {
				child.setGlobalPosition(currentOffset - scroll, 0);
				currentOffset += child.getWidth();
			} else if (direction == Direction.vertical) {
				child.setGlobalPosition(0, currentOffset - scroll);
				currentOffset += child.getHeight();
			}
		}

		// Si el modo está invertido, ajustamos la posición del "thumb" según el scroll
		if (direction == Direction.horizontal) {
			if (scrollBarInverted) {
				thumbPos = this.gx + scroll;
			} else {
				thumbPos = this.gx - scroll;
			}
		} else if (direction == Direction.vertical) {
			if (scrollBarInverted) {
				thumbPos = this.gy + scroll;
			} else {
				thumbPos = this.gy - scroll;
			}
		}
	}

	protected void updateScrollLimits() {
		if (styles.containsKey("maxScroll")) {
			maxScroll = (int) styles.get("maxScroll");
		} else {
			if (direction == Direction.vertical) {
				maxScroll = 0;
			} else if (direction == Direction.horizontal) {
				maxScroll = 0;
			}
		}

		if (styles.containsKey("minScroll")) {
			minScroll = (int) styles.get("minScroll");
		} else {
			if (direction == Direction.vertical) {
				minScroll = -height;
			} else if (direction == Direction.horizontal) {
				minScroll = -width;
			}
		}
	}

	protected void updateWheelState() {
		int wheelDelta = Mouse.getDWheel();
		WheelState wheelState = WheelState.none;

		if (wheelDelta != 0) {
			wheelState = (wheelDelta < 0) ? WheelState.down : WheelState.up;
			wheelChanged.emit(wheelState);
		}
	}
}
