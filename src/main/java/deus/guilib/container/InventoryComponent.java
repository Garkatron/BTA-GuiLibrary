package deus.guilib.container;

import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

public class InventoryComponent extends ContainerComponent {

	protected IInventory playerInventory;

	// Constructor principal con Container
	public InventoryComponent(AdvancedContainer container, IInventory playerInventory) {
		super(container);
		this.playerInventory = playerInventory;

	}

	// Constructor con Container y posición (x, y)
	public InventoryComponent(AdvancedContainer container, int x, int y, IInventory playerInventory) {
		super(container, x, y);
		this.playerInventory = playerInventory;
	}

	// Constructor con Container, posición (x, y) y tamaño (width, height)
	public InventoryComponent(AdvancedContainer container, int x, int y, int width, int height, IInventory playerInventory) {
		super(container, x, y, width, height);
		this.playerInventory = playerInventory;
	}

	// Constructor con Container, posición (x, y), tamaño (width, height) y lista de hijos
	public InventoryComponent(AdvancedContainer container, int x, int y, int width, int height, IInventory playerInventory, ContainerComponent... children) {
		super(container, x, y, width, height, children);
		this.playerInventory = playerInventory;
	}

	// Constructor con Container y lista de hijos
	public InventoryComponent(AdvancedContainer container, IInventory playerInventory, ContainerComponent... children) {
		super(container, children);
		this.playerInventory = playerInventory;
	}

	// Constructor con posición (x, y), tamaño (width, height) y lista de hijos
	public InventoryComponent(int x, int y, int width, int height, IInventory playerInventory, ContainerComponent... children) {
		super(x, y, width, height, children);
		this.playerInventory = playerInventory;
	}

	// Constructor con posición (x, y) y tamaño (width, height)
	public InventoryComponent(int x, int y, int width, int height, IInventory playerInventory) {
		super(x, y, width, height);
		this.playerInventory = playerInventory;
	}

	// Constructor con posición (x, y)
	public InventoryComponent(int x, int y, IInventory playerInventory) {
		super(x, y);
		this.playerInventory = playerInventory;
	}

	// Constructor con lista de hijos
	public InventoryComponent(IInventory playerInventory, ContainerComponent... children) {
		super(children);
		this.playerInventory = playerInventory;
	}

	// Método para añadir slots al contenedor
	@Override
	public ContainerComponent build() {
		if (playerInventory == null) {
			System.out.println("Player inventory is not set.");
			return this;
		}
//		int numberOfRows = playerInventory.getSizeInventory() / 9;
		int i = ((playerInventory.getSizeInventory() / 9) - 4) * 18;

		int l;
		int j1;

		for(l = 0; l < 3; ++l) {
			for(j1 = 0; j1 < 9; ++j1) {
				container.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
			}
		}

		for(l = 0; l < 9; ++l) {
			container.addSlot(new Slot(playerInventory, l, 8 + l * 18, 161 + i));
		}


		return this;
	}
}
