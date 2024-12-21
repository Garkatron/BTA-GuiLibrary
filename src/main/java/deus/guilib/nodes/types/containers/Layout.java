package deus.guilib.nodes.types.containers;

import deus.guilib.GuiLib;
import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;

import java.util.Map;

public class Layout extends Node {

	public enum FlexBoxDirection {
		vertical,
		horizontal;
	}

	private FlexBoxDirection direction = FlexBoxDirection.vertical;

	public Layout() {
		super();
	}

	public Layout(Map<String, String> attributes) {
		super(attributes);
		if (attributes.containsKey("direction")) { parseDirection(attributes.get("direction")); }
	}

	protected void parseDirection(String str) {
		if (str.equals("horizontal"))    { this.direction = FlexBoxDirection.horizontal; }
		else if (str.equals("vertical")) { this.direction = FlexBoxDirection.vertical; }
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
			if (direction == FlexBoxDirection.horizontal) {
				child.setPosition(currentOffset, 0);
				currentOffset += child.getWidth();
			} else if (direction == FlexBoxDirection.vertical) {
				child.setPosition(0, currentOffset);
				currentOffset += child.getHeight();
			}
		}
	}

	@Override
	public int getWidth() {
		if (direction == FlexBoxDirection.horizontal) {
			int totalWidth = 0;
			for (INode child : children) {
				totalWidth += child.getWidth();
			}
			return totalWidth;
		} else if (direction == FlexBoxDirection.vertical) {
			int largestWidth = 0;
			for (INode child : children) {
				largestWidth = Math.max(child.getWidth(), largestWidth);
			}
			return largestWidth;
		}
		return 0;
	}

	@Override
	public int getHeight() {
		if (direction == FlexBoxDirection.horizontal) {
			int largestHeight = 0;
			for (INode child : children) {
				largestHeight = Math.max(child.getHeight(), largestHeight);
			}
			return largestHeight;
		} else if (direction == FlexBoxDirection.vertical) {
			int totalHeight = 0;
			for (INode child : children) {
				totalHeight += child.getHeight();
			}
			return totalHeight;
		}
		return 0;
	}

}
