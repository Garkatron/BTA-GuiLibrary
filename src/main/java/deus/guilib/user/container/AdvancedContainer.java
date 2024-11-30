package deus.guilib.user.container;

import deus.guilib.element.elements.inventory.PlayerInventory;
import deus.guilib.interfaces.INodeFather;
import deus.guilib.interfaces.element.INode;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

/**
 * The `AdvancedContainer` class extends Minecraft's `Container` to manage custom inventory slots
 * for both player inventory and a general inventory. It integrates slots based on the provided
 * `Page` content and inventory objects.
 */
public class AdvancedContainer extends Container {

	private int slotIdCounter = 0; // Counter to keep track of slot IDs

	/**
	 * Constructs an `AdvancedContainer` instance and sets up slots for both the player's inventory
	 * and a general inventory based on the `Page` content.
	 *
	 * @param page The `Page` object containing the elements that define the slots.
	 * @param playerInventory The `IInventory` instance representing the player's inventory.
	 * @param inventory The `IInventory` instance representing the general inventory (log pile).
	 */
	public AdvancedContainer(INodeFather page, IInventory playerInventory, IInventory inventory) {
		super();
		addSlots(page.getContent(), inventory);
		addPlayerInventorySlots(page.getContent(), playerInventory);
	}

	/**
	 * Adds slots to the general inventory based on the provided elements.
	 *
	 * @param elements A list of `IElement` objects representing the elements to be added as slots.
	 * @param inventory The `IInventory` instance where the log pile slots will be created.
	 */
	private void addSlots(List<INode> elements, IInventory inventory) {
		for (INode element : elements) {
			if (element instanceof deus.guilib.element.elements.inventory.Slot) {
				if (!((deus.guilib.element.elements.inventory.Slot) element).isFake())
				{
					Slot newSlot = new Slot(inventory, slotIdCounter++, element.getX() + 1, element.getY() + 1);

					addSlot(newSlot);
					((deus.guilib.element.elements.inventory.Slot) element).setAssignedSlot(newSlot);
				}
			} else if (!(element instanceof PlayerInventory)) {
				if (!element.getChildren().isEmpty()) {
					addSlots(element.getChildren(), inventory);
				}
			}
		}
	}

	/**
	 * Adds slots to the player's inventory based on the provided elements.
	 *
	 * @param elements A list of `IElement` objects representing the elements to be added as slots.
	 * @param playerInventory The `IInventory` instance where the player inventory slots will be created.
	 */
	private void addPlayerInventorySlots(List<INode> elements, IInventory playerInventory) {
		for (INode element : elements) {
			if (element instanceof PlayerInventory) {
				if (!element.getChildren().isEmpty()) {
					for (INode childElement : element.getChildren()) {
						if (childElement instanceof deus.guilib.element.elements.inventory.Slot) {
							Slot newSlot = new Slot(playerInventory, slotIdCounter++, childElement.getX() + 1, childElement.getY() + 1);
							addSlot(newSlot);
							((deus.guilib.element.elements.inventory.Slot) childElement).setAssignedSlot(newSlot);
						}
					}
				}
			}
		}
	}

	/**
	 * Overrides the `addSlot` method from `Container` to add a slot to the container.
	 *
	 * @param slot The `Slot` instance to be added.
	 */
	@Override
	public void addSlot(Slot slot) {
		super.addSlot(slot);
	}

	/**
	 * Returns an empty list for move slots, as no special behavior is implemented.
	 *
	 * @param inventoryAction The action being performed on the inventory.
	 * @param slot The slot being acted upon.
	 * @param i An integer parameter related to the action.
	 * @param entityPlayer The player performing the action.
	 * @return An empty list of integers.
	 */
	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return List.of();
	}

	/**
	 * Returns an empty list for target slots, as no special behavior is implemented.
	 *
	 * @param inventoryAction The action being performed on the inventory.
	 * @param slot The slot being acted upon.
	 * @param i An integer parameter related to the action.
	 * @param entityPlayer The player performing the action.
	 * @return An empty list of integers.
	 */
	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return List.of();
	}

	/**
	 * Indicates whether the container is usable by the player. Always returns `false` in this implementation.
	 *
	 * @param entityPlayer The player to check.
	 * @return `false`
	 */
	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return false;
	}
}

