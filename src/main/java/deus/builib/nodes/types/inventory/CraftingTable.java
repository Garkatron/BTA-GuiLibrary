package deus.builib.nodes.types.inventory;

import deus.builib.nodes.Node;
import deus.builib.error.BUIError;

import java.util.Map;

public class CraftingTable extends Node {

	private int SLOT_SIZE = 18;
	private int cols = 3;
	private int rows = 3;
	private int slotQuantity = 9;
	private boolean fake = false;


	public CraftingTable() {
		super();
	}

	public CraftingTable(Map<String, String> attributes) {
		super(attributes);
		for (int i = 0; i < slotQuantity; i++) {
			addChildren(new Slot().setSid("CRAFTING_SLOT_"+i));
		}
	}

	@Override
	protected void drawChild() {
		if (!children.isEmpty()) {

			for (int i = 0; i < children.size(); i++) {

				int col = i % cols;
				int row = i / cols;
				int xPos = this.gx + col * SLOT_SIZE;
				int yPos = this.gy + row * SLOT_SIZE;

				children.get(i).setGlobalPosition(xPos, yPos);
				children.get(i).draw();
			}
		}
	}

	public int getCols() {
		return cols;
	}

	public CraftingTable setCols(int cols) {
		this.cols = cols;
		return this;
	}

	public int setRows(int rows) {
		this.rows = rows;
		return rows;
	}

	public int getRows() {
		return rows;
	}

	public CraftingTable setSlotQuantity(int slotQuantity) {
		this.slotQuantity = slotQuantity;
		return this;
	}

	public int getSlotQuantity() {
		return slotQuantity;
	}

	@Override
	public int getWidth() {
		return cols*SLOT_SIZE;
	}

	@Override
	public int getHeight() {
		return rows*SLOT_SIZE;
	}

	public boolean isFake() {
		return fake;
	}

	public CraftingTable setFake(boolean fake) {
		this.fake = fake;
		children.forEach(
			c->((Slot)c).setFake(fake)
		);
		return this;
	}
}
