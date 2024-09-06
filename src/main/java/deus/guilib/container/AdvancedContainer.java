package deus.guilib.container;

import deus.guilib.element.elements.PlayerInventory;
import deus.guilib.element.interfaces.element.IElement;
import deus.guilib.routing.Page;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class AdvancedContainer extends Container {

	private int slotIdCounter = 0; // Counter to keep track of slot IDs


	public AdvancedContainer(Page page, IInventory playerInventory, IInventory logPileInventory) {
		super();

		//InventoryComponent inventoryComponent = (InventoryComponent) new InventoryComponent(this, playerInventory);
		//inventoryComponent.build();
		// Añadir los slots del LogPileInventory
		addLogPileSlots(page.getContent(), logPileInventory);

		// Añadir los slots del PlayerInventory
		addPlayerInventorySlots(page.getContent(), playerInventory);
	}

	private void addLogPileSlots(List<IElement> elements, IInventory logPileInventory) {
		for (IElement element : elements) {
			if (element instanceof deus.guilib.element.elements.Slot) {
				Slot newSlot = new Slot(logPileInventory, slotIdCounter++, element.getX() + 1, element.getY() + 1);
				addSlot(newSlot);
				((deus.guilib.element.elements.Slot) element).setAssignedSlot(newSlot);
				System.out.println(slotIdCounter + " :PI: " + element.getX() + 1);
			} else if (!(element instanceof PlayerInventory)) {
				if (!element.getChildren().isEmpty()) {
					addLogPileSlots(element.getChildren(), logPileInventory);
				}
			}

		}
	}

	private void addPlayerInventorySlots(List<IElement> elements, IInventory playerInventory) {
		for (IElement element : elements) {
			if (element instanceof PlayerInventory) {
				if (!element.getChildren().isEmpty()) {
					for (IElement childElement : element.getChildren()) {
						if (childElement instanceof deus.guilib.element.elements.Slot) {
							Slot newSlot = new Slot(playerInventory, slotIdCounter++, childElement.getX() + 1, childElement.getY() + 1);
							addSlot(newSlot);
							((deus.guilib.element.elements.Slot) childElement).setAssignedSlot(newSlot);
							System.out.println(slotIdCounter + " :PI: " + element.getX() + 1);
						}

					}
				}
			}
		}
	}

	@Override
	public void addSlot(Slot slot) {
		super.addSlot(slot);
	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return List.of();
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return List.of();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return false;
	}


}
