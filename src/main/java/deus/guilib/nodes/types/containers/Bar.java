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

		direction = attributes.getOrDefault("direction", direction);

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
			int posX = barCenterItems ? (this.getWidth() / 4) - (child.getWidth() / 4) : child.getX();
			child.setPosition(posX, currentY);
			child.draw();
			currentY += (child.getHeight()+1)/2 + spaceBetween;
		}
	}


	private void drawChildrenHorizontally(boolean barCenterItems) {
		int currentX = gx + startSpace;
		for (INode child : children) {
			int posY = barCenterItems ? (this.getHeight() / 4) - (child.getHeight() / 4): child.getY();
			child.setPosition(currentX, posY);
			child.draw();
			currentX += (child.getWidth()+1)/2 + spaceBetween;
		}
	}

	private void updateLengthAndOffsetFromStyle() {
		if (styles.containsKey("barSpaceBetween")) {
			spaceBetween = StyleParser.parsePixels(styles.get("barSpaceBetween").toString());
		}
	}
	@Override
	public int getWidth() {
		if ("vertically".equals(direction)) {
			int largestX = 0;
			for (INode child: this.children) {
				largestX = Math.max(child.getWidth(), largestX);
			}
			return largestX;
		}
		int sumX = 0;
		for (INode child: this.children) sumX += child.getWidth();
		sumX += spaceBetween * children.size() * 2 -spaceBetween -6;
		return sumX;
	}
	@Override
	public int getHeight() {
		{
			if ("horizontally".equals(direction)) {
				int largestY = 0;
				for (INode child: this.children) {
					largestY = Math.max(child.getHeight(), largestY);
				}
				return largestY;
			}
			int sumY = 0;
			for (INode child: this.children) sumY += child.getHeight();
			sumY += spaceBetween * children.size() * 2 -spaceBetween -1;
			return sumY;
		}
	}
}

