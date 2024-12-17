package deus.guilib.nodes.types.containers;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.resource.Texture;

import java.awt.desktop.PreferencesEvent;
import java.util.Map;

public class Bar extends Node {

	public enum BarDirection {
		vertical,
		horizontal;
	}

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
		// TODO @Garkatron - Maybe
	}

	@Override
	protected void updateIt() {
		super.updateIt();

		if (styles.containsKey("direction")) {
			parseDirection(attributes.get("direction"));
		}

	}

	@Override
	protected void drawChild() {
		boolean barCenterItems = styles.containsKey("barCenterItems") && (boolean) styles.get("barCenterItems");

		if (!children.isEmpty()) {
			if (this.direction == BarDirection.vertical) { drawChildrenVertically(); }
			else if (this.direction == BarDirection.horizontal) { drawChildrenHorizontally(); }
		}
	}

	private void drawChildrenVertically() {

	}

	private void drawChildrenHorizontally() {

	}

	@Override
	public int getWidth() {

		int largest = 0;
		for (INode child : children) {

			largest = Math.max(child.getWidth(), largest);
		}
		return largest;
	}

	@Override
	public int getHeight() {

		return height;
	}
}
