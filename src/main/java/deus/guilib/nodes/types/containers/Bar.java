package deus.guilib.nodes.types.containers;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.resource.Texture;

import java.util.Map;

public class Bar extends Node {

	public enum BarDirection {
		vertical,
		horizontal;
	}

	private int spaceBetween = 0;
	private int startSpace = 0;
	private BarDirection direction = BarDirection.vertical;

	public Bar() {
		super();
	}

	public Bar(Map<String, String> attributes) {
		super(attributes);
		if (attributes.containsKey("direction")) { parseDirection(attributes.get("direction")); }
	}

	protected void parseDirection(String str) {
		if (str.equals("horizontal"))    { this.direction = BarDirection.horizontal; }
		else if (str.equals("vertical")) { this.direction = BarDirection.vertical; }
		// else raise something? idk if it should crash or not.
	}

	@Override
	protected void updateIt() {
		super.updateIt();
		updateLengthAndOffsetFromStyle();

		if (styles.containsKey("direction")) {
			parseDirection(attributes.get("direction"));
		}

		if (styles.containsKey("startSpace")) {
			startSpace = StyleParser.parsePixels(styles.get("startSpace").toString());
		}
	}

	@Override
	protected void drawChild() {
		boolean barCenterItems = styles.containsKey("barCenterItems") && (boolean) styles.get("barCenterItems");

		if (!children.isEmpty()) {
			if (this.direction == BarDirection.vertical) { drawChildrenVertically(barCenterItems); }
			else if (this.direction == BarDirection.horizontal) { drawChildrenHorizontally(barCenterItems); }
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
		if (this.direction == BarDirection.vertical) {
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
		if (this.direction == BarDirection.horizontal) {
			int largestY = 0;
			for (INode child: this.children) {
				largestY = Math.max(child.getHeight(), largestY);
			}
			return largestY;
		}

		int sumY = 0;
		for (INode child: this.children) sumY += child.getHeight();
		sumY += spaceBetween * children.size() * 2 -spaceBetween -3;
		return sumY;
	}
}
