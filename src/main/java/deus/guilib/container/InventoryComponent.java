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

//
//		int startX = x;
//		int startY = y;
//		int inventoryStartIndex = 0;
//		int inventoryRowCount = 3; // Número de filas del inventario del jugador
//
//		for (int row = 0; row < inventoryRowCount; ++row) {
//			for (int column = 0; column < 9; ++column) {
//				int slotIndex = column + row * 9 + 9; // Índice del slot en el inventario del jugador
//				int xPosition = startX + column * 19; // Ajusta el espaciado si es necesario
//				int yPosition = startY + row * 19; // Ajusta el espaciado si es necesario
//				container.addSlot(new Slot(playerInventory, slotIndex, xPosition, yPosition));
//			}
//		}
//
//		// Añadir slots de la barra de acceso rápido
//		for (int column = 0; column < 9; ++column) {
//			int slotIndex = column; // Índice del slot en la barra de acceso rápido
//			int xPosition = startX + column * 19; // Ajusta el espaciado si es necesario
//			int yPosition = startY + 58; // Ajusta el espaciado si es necesario
//			container.addSlot(new Slot(playerInventory, slotIndex, xPosition, yPosition));
//		}

		return this;
	}
}
