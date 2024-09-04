package deus.guilib.element.elements;

import deus.guilib.element.Element;
import deus.guilib.util.math.Tuple;
import deus.guilib.resource.Texture;

public class Slot extends Element {


	public Slot() {
		super(new Texture("assets/newsteps/textures/gui/Slot.png",18,18));
	}

	// Método para obtener la posición final
	public Tuple<Integer, Integer> getPosition() {
		return new Tuple<Integer, Integer>(x,y);
	}

}
