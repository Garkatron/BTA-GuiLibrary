package deus.guilib.nodes.types.containers;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.resource.Texture;

import java.util.Map;

public class Bar extends Node {

	private int spaceBetween = 0;
	private int startSpace = 0;
	private String direction = "vertically";

	public Bar() {
		super();
	}

	public Bar(Map<String, String> attributes) {
		super(attributes);
	}

	@Override
	protected void updateIt() {
		super.updateIt();
		updateLengthAndOffsetFromStyle();

		direction = styles.getOrDefault("direction", direction).toString();

		if (styles.containsKey("startSpace")) {
			startSpace = StyleParser.parsePixels(styles.get("startSpace").toString());
		}
	}

	@Override
	protected void drawChild() {
		boolean barCenterItems = styles.containsKey("barCenterItems") && (boolean) styles.get("barCenterItems");

		if (!children.isEmpty()) {
			if ("vertically".equals(direction)) {
				drawChildrenVertically(barCenterItems);
			} else if ("horizontally".equals(direction)) {
				drawChildrenHorizontally(barCenterItems);
			}
		}
	}

	private void drawChildrenVertically(boolean barCenterItems) {
		int currentY = gy + startSpace;
		for (INode child : children) {
			int posX = barCenterItems ? (width / 2) - (child.getWidth() / 2) : child.getX();
			child.setPosition(posX, currentY);
			child.draw();
			currentY += child.getHeight() + spaceBetween;
		}
	}

	private void drawChildrenHorizontally(boolean barCenterItems) {
		int currentX = gx + startSpace;
		for (INode child : children) {
			int posY = barCenterItems ? (height / 2) - (child.getHeight() / 2) : child.getY();
			child.setPosition(currentX, posY);
			child.draw();
			currentX += (child.getWidth() / 2) + spaceBetween;
		}
	}

	private void updateLengthAndOffsetFromStyle() {
		if (styles.containsKey("barSpaceBetween")) {
			spaceBetween = StyleParser.parsePixels(styles.get("barSpaceBetween").toString());
		}
	}
}
