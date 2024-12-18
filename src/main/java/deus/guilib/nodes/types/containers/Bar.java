package deus.guilib.nodes.types.containers;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;

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

	@Override
	protected void connectSignals() {
		// super.connectSignals();
		currentChildrenSignal.connect(
			(r, children)->{

				if (this.direction == BarDirection.vertical) {
					int largest = 0;
					int currentHeight = 0;

					System.out.println(children);

					for (INode child : children) {
						largest = Math.max(child.getWidth(), largest);

						child.setGlobalPosition(gx, currentHeight);

						currentHeight += child.getHeight();

					}

					width = largest;

				}

			}
		);
	}

	@Override
	protected void drawBackgroundColor() {
		super.drawBackgroundColor();
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

}
