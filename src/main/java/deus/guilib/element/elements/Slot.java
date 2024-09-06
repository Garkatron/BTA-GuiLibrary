package deus.guilib.element.elements;

import deus.guilib.element.Element;
import deus.guilib.element.interfaces.element.IUpdatable;
import deus.guilib.util.math.Tuple;
import deus.guilib.resource.Texture;

public class Slot extends Element implements IUpdatable {

	private net.minecraft.core.player.inventory.slot.Slot assignedSlot;

	public Slot() {
		super(new Texture("assets/newsteps/textures/gui/Slot.png",18,18));
	}

	// Método para obtener la posición final
	public Tuple<Integer, Integer> getPosition() {
		return new Tuple<Integer, Integer>(x,y);
	}

	public void setAssignedSlot(net.minecraft.core.player.inventory.slot.Slot assignedSlot) {
		this.assignedSlot = assignedSlot;
	}

	@Override
	public void update() {
		if (assignedSlot!=null) {
			assignedSlot.xDisplayPosition = x+1;
			assignedSlot.yDisplayPosition = y+1;
		}

	}

}
