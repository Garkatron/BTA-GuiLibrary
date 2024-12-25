package deus.guilib.nodes.types.containers;

import deus.guilib.GuiLib;
import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.config.Direction;
import deus.guilib.nodes.stylesystem.StyleParser;

import java.util.Map;

public class Layout extends Node {

	protected Direction direction = Direction.vertical;
	protected int spaceBetween = 0;
	protected boolean itemsCentered = false;

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


	}

	@Override
	protected void updateChildren() {
		super.updateChildren();

		int currentOffset = 0;
		for (INode child : children) {
			if (direction == Direction.horizontal) {
				child.setGlobalPosition(gx + currentOffset, itemsCentered ? (gy + (getHeight()/2) - (child.getHeight())/2) : gy);
				currentOffset += child.getWidth() + spaceBetween;
			} else if (direction == Direction.vertical) {

				child.setGlobalPosition(itemsCentered ? (gx + (getWidth()/2) - (getWidth()/2)) : gx, currentOffset);

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
				return totalHeight;
			}
		}
		return super.getHeight();
	}

}
