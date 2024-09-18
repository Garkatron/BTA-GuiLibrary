package deus.guilib.element.elements;

import deus.guilib.element.Element;
import deus.guilib.interfaces.element.IUpdatable;
import deus.guilib.util.math.Tuple;
import deus.guilib.resource.Texture;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class Slot extends Element implements IUpdatable {

	private net.minecraft.core.player.inventory.slot.Slot assignedSlot;
	protected boolean fake = false;
	protected Item fakeItem;


	public Slot() {
		super(new Texture("assets/textures/gui/Slot.png",18,18));
	}

	// Método para obtener la posición final
	public Tuple<Integer, Integer> getPosition() {
		return new Tuple<Integer, Integer>(gx,gy);
	}

	public void setAssignedSlot(net.minecraft.core.player.inventory.slot.Slot assignedSlot) {
		this.assignedSlot = assignedSlot;
	}

	@Override
	protected void drawIt() {
		super.drawIt();

		if (fake && fakeItem!=null) {
			ItemStack icon = fakeItem.getDefaultStack();
			ItemModelDispatcher.getInstance().getDispatch(icon).renderItemIntoGui(Tessellator.instance, this.mc.fontRenderer, this.mc.renderEngine, icon, this.gx+1, this.gy+1 , 1.0F);
		}
	}

	@Override
	public void update() {
		if (assignedSlot!=null) {
			assignedSlot.xDisplayPosition = gx+1;
			assignedSlot.yDisplayPosition = gy+1;
		}


	}

	public boolean isFake() {
		return fake;
	}

	public Slot setFake(boolean fake) {
		this.fake = fake;
		return this;
	}

	public Item getFakeItem() {
		return fakeItem;
	}

	public Slot setFakeItem(Item fakeItem) {
		this.fakeItem = fakeItem;
		return this;
	}
}
