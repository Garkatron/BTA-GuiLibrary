package deus.guilib.nodes.types.inventory;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.IUpdatable;
import deus.guilib.util.math.Tuple;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class Slot extends Node implements IUpdatable {


	private net.minecraft.core.player.inventory.slot.Slot assignedSlot;
	protected Item fakeItem;
	protected boolean fake = false;


	public Slot() {
		super();
	}

	public Slot(Map<String, String> attributes) {
		super(attributes);
	}
	public Tuple<Integer, Integer> getPosition() {
		return new Tuple<Integer, Integer>(gx,gy);
	}

	public void setAssignedSlot(net.minecraft.core.player.inventory.slot.Slot assignedSlot) {
		this.assignedSlot = assignedSlot;
	}

	@Override
	protected void drawIt() {
		super.drawIt();

		if (fake && fakeItem != null) {
			ItemStack icon = fakeItem.getDefaultStack();
			int iconOffsetX = this.gx + 1;
			int iconOffsetY = this.gy + 1;

			// Save the current OpenGL state before making changes
			GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);

			// Reset color to prevent the texture from darkening
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			// OpenGL configuration to render the item
			GL11.glDepthMask(true);
			GL11.glEnable(32826);  // Enable lighting/highlight
			GL11.glEnable(2929);   // Enable depth test

			// Render the ItemStack in the GUI
			ItemModelDispatcher.getInstance().getDispatch(icon).renderItemIntoGui(
				Tessellator.instance,
				this.mc.fontRenderer,
				this.mc.renderEngine,
				icon,
				iconOffsetX,
				iconOffsetY,
				1.0F
			);

			// Disable OpenGL features after rendering
			GL11.glDisable(2929);
			GL11.glDisable(32826);

			// Restore the original OpenGL state
			GL11.glPopAttrib();
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
