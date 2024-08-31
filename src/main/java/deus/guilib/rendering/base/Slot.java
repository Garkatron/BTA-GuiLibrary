package deus.guilib.rendering.base;

import deus.guilib.math.Tuple;
import deus.guilib.rendering.resource.Texture;

public class Slot extends Element {

	private Tuple<Integer, Integer> finalPosition;

	public Slot() {
		super(new Texture("assets/newsteps/textures/gui/Slot.png",18,18));
	}

	public Slot(Element... children) {
		super(new Texture("assets/newsteps/textures/gui/Slot.png",18,18), children);
	}

	public Slot(int x, int y, Element... children) {
		super(new Texture("assets/newsteps/textures/gui/Slot.png",18,18), x, y, children);
	}

	// Método para establecer la posición final
	private void setFinalPosition(Tuple<Integer, Integer> finalPosition) {
		this.finalPosition = finalPosition;
	}

	// Método para obtener la posición final
	public Tuple<Integer, Integer> getFinalPosition() {
		return finalPosition;
	}
}
