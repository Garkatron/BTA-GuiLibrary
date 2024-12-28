package deus.builib.nodes.types.containers;

import deus.builib.GuiLib;
import deus.builib.nodes.Node;
import deus.builib.interfaces.nodes.INode;
import deus.builib.nodes.config.Direction;
import deus.builib.nodes.config.OverflowMode;
import deus.builib.nodes.stylesystem.StyleParser;
import net.minecraft.client.render.Scissor;

import java.util.Map;

public class Layout extends Node {

	protected Direction direction = Direction.vertical;
	protected int spaceBetween = 0;
	protected boolean itemsCentered = false;
	protected boolean itemsFromCenter = false;

	/* ScrollLayout options */
	protected OverflowMode overflowMode = OverflowMode.view;

	public Layout() {
		super();
	}

	public Layout(Map<String, String> attributes) {
		super(attributes);
		if (attributes.containsKey("direction")) { parseDirection(attributes.get("direction")); }
	}

	protected void parseDirection(String str) {
		if (str.equals("horizontal"))    { this.direction = Direction.horizontal; }
		else if (str.equals("vertical")) { this.direction = Direction.vertical; }
		else {
			GuiLib.LOGGER.error("Bar direction attr may be horizontal/vertical");
		}
	}

	@Override
	protected void drawChild() {
		for (INode child : children) {
			if (overflowMode== OverflowMode.view) {
				child.draw();
			} else {
				Scissor.scissor(gx,gy, getWidth(), getHeight(), child::draw);
			}
		}
	}

	@Override
	protected void updateIt() {
		super.updateIt();

		if (attributes.containsKey("direction")) {
			parseDirection(attributes.get("direction"));
		}

		if (styles.containsKey("layoutSpacing")) {
			spaceBetween = StyleParser.parsePixels((String) styles.get("layoutSpacing"));
		}

		if (styles.containsKey("layoutCenterItems")) {
			itemsCentered = (boolean) styles.get("layoutCenterItems");
		}

		if (styles.containsKey("layoutItemsFromCenter")) {
			itemsFromCenter = (boolean) styles.get("layoutItemsFromCenter");
		}

		updateOverflowMode();

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

	@Override
	protected void updateChildren() {
		super.updateChildren();

		if (itemsFromCenter) {
			updateChildrenFromCenter();
		} else {
			updateChildrenFromStart();
		}
	}

	private void updateChildrenFromStart() {
		int currentOffset = 0;
		for (INode child : children) {
			if (direction == Direction.horizontal) {
				child.setGlobalPosition(gx + currentOffset, itemsCentered ? (gy + (getHeight() / 2) - (child.getHeight()) / 2) : gy);
				currentOffset += child.getWidth() + spaceBetween;

			} else if (direction == Direction.vertical) {

				child.setGlobalPosition(itemsCentered ? (gx + (getWidth() / 2) - (child.getWidth() / 2)) : gx, gy + currentOffset);

				currentOffset += child.getHeight() + spaceBetween;
			}
		}
	}

	private void updateChildrenFromCenter() {
		int totalSize = 0;
		for (INode child : children) {
			totalSize += direction == Direction.horizontal ? child.getWidth() : child.getHeight();
		}
		totalSize += (children.size() - 1) * spaceBetween;

		int centerOffset = direction == Direction.horizontal ? (getWidth() - totalSize) / 2 : (getHeight() - totalSize) / 2;
		int currentOffset = centerOffset;

		for (INode child : children) {
			if (direction == Direction.horizontal) {
				child.setGlobalPosition(gx + currentOffset, itemsCentered ? (gy + (getHeight() / 2) - (child.getHeight() / 2)) : gy);
				currentOffset += child.getWidth() + spaceBetween;

			} else if (direction == Direction.vertical) {
				child.setGlobalPosition(itemsCentered ? (gx + (getWidth() / 2) - (child.getWidth() / 2)) : gx, gy + currentOffset);
				currentOffset += child.getHeight() + spaceBetween;
			}
		}
	}

	@Override
	public int getWidth() {
		if (!styles.containsKey("width")) {
			if (direction == Direction.horizontal) {
				int totalWidth = 0;
				for (INode child : children) {
					totalWidth += child.getWidth();
				}
				totalWidth += (children.size() - 1) * spaceBetween;
				return totalWidth;
			} else if (direction == Direction.vertical) {
				int largestWidth = 0;
				for (INode child : children) {
					largestWidth = Math.max(child.getWidth(), largestWidth);
				}
				return largestWidth;
			}
		}
		return super.getWidth();
	}

	@Override
	public int getHeight() {
		if (!styles.containsKey("height")) {
			if (direction == Direction.horizontal) {
				int largestHeight = 0;
				for (INode child : children) {
					largestHeight = Math.max(child.getHeight(), largestHeight);
				}
				return largestHeight;
			} else if (direction == Direction.vertical) {
				int totalHeight = 0;
				for (INode child : children) {
					totalHeight += child.getHeight();
				}
				totalHeight += (children.size() - 1) * spaceBetween;
				return totalHeight;
			}
		}
		return super.getHeight();
	}
}
