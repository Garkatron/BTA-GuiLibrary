package deus.builib.guimanagement;

import deus.builib.nodes.types.inventory.PlayerInventory;
import deus.builib.interfaces.IPage;
import deus.builib.interfaces.nodes.INode;
import deus.builib.guimanagement.routing.Page;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;

import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

/**
 * The `AdvancedContainer` class extends Minecraft's `Container` to manage custom inventory slots
 * for both player inventory and a general inventory. It integrates slots based on the provided
 * `Page` content and inventory objects.
 */
public class AdvancedContainer extends MenuAbstract {

	private Page page;
	private Container inventory;
	private Container playerInventory;

	/**
	 * Constructs an `AdvancedContainer` instance and sets up slots for both the player's inventory
	 * and a general inventory based on the `Page` content.
	 *
	 * @param page The `Page` object containing the elements that define the slots.
	 * @param inventory The `IInventory` instance representing the general inventory.
	 */
	public AdvancedContainer(IPage page, Container inventory) {
		super();
		this.page = (Page) page;
		this.inventory = inventory;
		addSlotsOfInventory(page.getDocument());
	}

	public AdvancedContainer(IPage page, Container inventory, Container playerInventory) {
		this.page = (Page) page;
		this.inventory = inventory;
		this.playerInventory = playerInventory;
		addSlotsOfInventory(page.getDocument());

	}

	/**
	 * Adds slots to the general inventory based on the provided elements.
	 *
	 * @param root The root node to start adding slots.
	 * @param inventory The `IInventory` instance for creating inventory slots.
	 */
	public void addSlots(INode root, Container inventory) {
		int slotIdCounter = 0;
		List<INode> nodes = root.getNodeByClass("Slot");
		for (INode node : nodes) {
			if (!((deus.builib.nodes.types.inventory.Slot) node).isFake()) {
				Slot newSlot = new Slot(inventory, slotIdCounter++, node.getX() + 1, node.getY() + 1);
				addSlot(newSlot);
				((deus.builib.nodes.types.inventory.Slot) node).setAssignedSlot(newSlot);
			}
		}

		// ! this.updateInventory();
	}

	public void addSlotsOfInventory(INode root) {
		List<INode> nodes = root.getNodeByClass("Slot");

		if (nodes.isEmpty())
			return;

		if (this.playerInventory != null) {
			List<INode> playerInventoryNodes = nodes.stream()
				.filter(node -> node.getParent() instanceof PlayerInventory && this.playerInventory != null)
				.toList();

			if (playerInventoryNodes.size() == 36) {
				addPlayerInventorySlots(playerInventoryNodes);
			}
		}

		List<INode> inventoryNodes = nodes.stream()
			.filter(node -> !(node.getParent() instanceof PlayerInventory) || this.playerInventory == null)
			.toList();

		if (inventoryNodes.isEmpty())
			return;

		addSlotsToNodes(inventoryNodes, this.inventory);

		// ! this.updateInventory();
	}

	private void addPlayerInventorySlots(List<INode> nodes) {

		List<INode> filteredPlayerInventory = nodes.stream()
			.limit(27)
			.toList();

		List<INode> hotbarNodes = nodes.stream()
			.skip(nodes.size() - 9)
			.limit(9)
			.toList();

		int playerSlotIdCounter = 0;
		for (INode hotbarNode : hotbarNodes) {
			Slot newSlot;
			newSlot = new Slot(this.playerInventory, playerSlotIdCounter++, hotbarNode.getGx() + 1, hotbarNode.getGy() + 1);
			addSlot(newSlot);
			((deus.builib.nodes.types.inventory.Slot)hotbarNode).setAssignedSlot(newSlot);
		}

		for (INode iNode : filteredPlayerInventory) {
			Slot newSlot;
			newSlot = new Slot(this.playerInventory, playerSlotIdCounter++, iNode.getGx() + 1, iNode.getGy() + 1);
			addSlot(newSlot);
			((deus.builib.nodes.types.inventory.Slot)iNode).setAssignedSlot(newSlot);
		}
	}

	private void addSlotsToNodes(List<INode> nodes, Container inventory) {
		int slotIdCounter = 0;
		for (INode node : nodes) {
			Slot newSlot = new Slot(inventory, slotIdCounter++, node.getGx() + 1, node.getGy() + 1);
			addSlot(newSlot);
			((deus.builib.nodes.types.inventory.Slot) node).setAssignedSlot(newSlot);
		}
	}

	/**
	 * Adds slots to the player's inventory based on the provided elements.
	 *
	 * @param elements A list of `INode` objects representing the elements to be added as slots.
	 * @param playerInventory The `IInventory` instance for creating player inventory slots.
	 */
	public void addPlayerInventorySlots(List<INode> elements, Container playerInventory) {
		int slotIdCounter = 0;
		for (INode element : elements) {
			if (element instanceof PlayerInventory) {
				if (!element.getChildren().isEmpty()) {
					for (INode childElement : element.getChildren()) {
						if (childElement instanceof deus.builib.nodes.types.inventory.Slot) {
							Slot newSlot = new Slot(playerInventory, slotIdCounter++, childElement.getX() + 1, childElement.getY() + 1);
							addSlot(newSlot);
							((deus.builib.nodes.types.inventory.Slot) childElement).setAssignedSlot(newSlot);
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
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, Player player) {
		return List.of();
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, Player player) {
		return List.of();
	}

	@Override
	public boolean stillValid(Player player) {
		return false;
	}


	/**
	 * Removes a slot by its ID.
	 *
	 * @param slotId The ID of the slot to remove.
	 */
	public void removeSlot(int slotId) {
		if (slotId >= 0 && slotId < this.slots.size()) {
			this.slots.remove(slotId);

			for (int i = 0; i < this.slots.size(); i++) {
				this.slots.get(i).index = i;
			}

			// ! this.updateInventory();
		} else {
			throw new IllegalArgumentException("Invalid slot ID: " + slotId);
		}
	}

	/**
	 * Removes all slots from the container.
	 */
	public void removeAllSlots() {
		if (!this.slots.isEmpty()) {
			this.slots.clear();
			// ! this.inventoryItemStacks.clear();
			// ! this.updateInventory();
		}
	}

	/**
	 * Refreshes the container by removing all slots and re-adding them.
	 */
	public void refreshContainer() {
		removeAllSlots();
		//addSlots(page.getDocument(), inventory);
		addSlotsOfInventory(page.getDocument());
	}
}
