package deus.guilib.nodes.types.containers;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.resource.Texture;

import java.util.Map;

public class Bar extends Node {

	private int length = 0;
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
		if (styles.containsKey("direction")) {
			direction = (String) styles.get("direction");
		}
		if (styles.containsKey("startSpace")) {
			startSpace = StyleParser.parsePixels((String) styles.get("startSpace"));
		}
	}

	@Override
	protected void drawBackgroundImage() {
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");
			if (!t.getPath().equals("transparent")) {
				if (direction.equals("vertically")) {
					drawBackgroundVertically();
				} else if (direction.equals("horizontally")) {
					drawBackgroundHorizontally();
				}
			}
		}
	}

	private void drawBackgroundHorizontally() {

		Texture t = (Texture) styles.get("backgroundImage");
		int frameY;
		for (int i = 0; i < length - 1; i++) {
			if (i == length - 2) {
				frameY = 2;
			} else if (i > 0) {
				frameY = 1;
			} else {
				frameY = 0;
			}
			t.drawWithFrame(mc, gx + (i * 32), gy, width, height, 64, frameY * 32);
		}

	}

	private void drawBackgroundVertically() {
		Texture t = (Texture) styles.get("backgroundImage");
		int frameY;
		for (int i = 0; i < length - 1; i++) {
			if (i == length - 2) {
				frameY = 2;
			} else if (i > 0) {
				frameY = 1;
			} else {
				frameY = 0;
			}
			t.drawWithFrame(mc, gx, gy + (i * 32), width, height, 0, frameY * 32);
		}

	}

	@Override
	protected void drawChild() {
		if (direction.equals("vertically")) {
			if (!children.isEmpty()) {
				int currentY = gy + startSpace;

				for (INode child : children) {
					int posY = currentY;
					int posX = (width / 2) - (child.getWidth() / 2);

					child.setPosition(posX, posY);
					child.draw();

					currentY += (child.getHeight() / 2) + spaceBetween;
				}
			}
		} else if (direction.equals("horizontally")) {
			if (!children.isEmpty()) {
				int currentX = gx + startSpace;

				for (INode child : children) {
					int posY = (height / 2) - (child.getWidth() / 2);

					int posX = currentX;

					child.setPosition(posX, posY);
					child.draw();

					currentX += (child.getWidth() / 2) + spaceBetween;
				}
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
		if (direction.equals("vertically")) {
			return (length-1) * 32;

		} else if (direction.equals("horizontally")) {
			return height;
		}
		return 0;
	}

	@Override
	public int getWidth() {
		if (direction.equals("horizontally")) {
			return (length-1) * 32;

		} else if (direction.equals("vertically")) {
			return width;
		}
		return 0;
	}
}
