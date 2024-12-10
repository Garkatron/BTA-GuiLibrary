package deus.guilib.nodes.types.containers;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.resource.Texture;

import java.util.Map;

public class Bar extends Node {

	private int length = 3;
	private int spaceBetween = 0;
	private int startSpace = 0;
	private String direction = "vertically";

	public Bar() {
		super();
	}

	public Bar(Map<String, String> attributes) {
		super(attributes);
	}


	public Bar setLength(int length) {
		this.length = length;
		return this;
	}

	@Override
	protected void updateIt() {
		super.updateIt();
		updateLengthAndOffsetFromStyle();
		if (styles.containsKey("barDirection")) {
			direction = (String) styles.get("barDirection");
		}
		if (styles.containsKey("startSpace")) {
			startSpace = StyleParser.parsePixels((String) styles.get("startSpace"));
		}
	}

	@Override
	protected void drawBackgroundImage() {
		if (direction.equals("vertically")) {
			drawBackgroundVertically();
		} else if (direction.equals("horizontally")) {
			drawBackgroundHorizontally();
		}
	}

	private void drawBackgroundHorizontally() {
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");

			int frameX = 0;
			for (int i = 0; i < length - 1; i++) {
				if (i == length - 1) {
					frameX = 1;
				} else if (i > 0) {
					frameX = 2;
				} else {
					frameX = 0;
				}

				t.drawWithFrame(mc, i * 32, gy, width, height, frameX, 4);
			}
		}
	}

	private void drawBackgroundVertically() {
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");
			int frameY;
			for (int i = 0; i < length; i++) {
				if (i == length - 1) {
					frameY = 2;
				} else if (i > 0) {
					frameY = 1;
				} else {
					frameY = 0;
				}
				t.drawWithFrame(mc, gx, gy + (i * 32), width, height, 0, frameY*32);
			}
		}
	}

	@Override
	protected void drawChild() {
		if (!children.isEmpty()) {

			int currentY = gy + startSpace;

			for (INode child : children) {
				int posY = currentY;
				int posX = (width / 2) - (child.getWidth() / 2);

				child.setPosition(posX, posY);
				child.draw();

				currentY += (child.getHeight()/2) + spaceBetween;
			}
		}
	}


	protected void updateLengthAndOffsetFromStyle() {
		if (styles.containsKey("barLength")) {
			length = (Integer) styles.get("barLength");
		} else {
			length = children.size();
		}
		if (styles.containsKey("barSpaceBetween")) {
			spaceBetween = StyleParser.parsePixels((String) styles.get("barSpaceBetween"));
		}
	}

	@Override
	public int getHeight() {
		return length * 32;
	}
}
