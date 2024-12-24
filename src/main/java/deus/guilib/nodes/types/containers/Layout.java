package deus.guilib.nodes.types.containers;

import deus.guilib.GuiLib;
import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.config.Direction;

import java.util.Map;

public class Layout extends Node {

	protected Direction direction = Direction.vertical;

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

		if (styles.containsKey("direction")) {
			parseDirection(attributes.get("direction"));
		}

		int currentOffset = 0;
		for (INode child : children) {
			if (direction == Direction.horizontal) {
				child.setGlobalPosition(currentOffset, 0);
				currentOffset += child.getWidth();
			} else if (direction == Direction.vertical) {
				child.setGlobalPosition(0, currentOffset);
				currentOffset += child.getHeight();
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
