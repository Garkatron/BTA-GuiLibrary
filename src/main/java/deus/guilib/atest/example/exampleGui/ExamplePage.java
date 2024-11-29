package deus.guilib.atest.example.exampleGui;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;

import deus.guilib.element.elements.htmllike.Body;
import deus.guilib.element.elements.inventory.PlayerInventory;
import deus.guilib.element.elements.inventory.Slot;
import deus.guilib.element.elements.other.FreeElement;
import deus.guilib.element.stylesystem.StyleSystem;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;
import net.minecraft.core.player.inventory.slot.SlotArmor;

public class ExamplePage extends Page {

	//private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40).setPosition(Placement.CENTER).setSid("INV");

	public ExamplePage(Router router, String... text) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CHILD_DECIDE));
		addContent(
			//playerInventory
			new FreeElement().addChildren(
				new Body().addChildren(
					new FreeElement().setGlobalPosition(0, 0)
				)
			)
		);
		reloadStyles();
	}

	@Override
	public void update() {
		super.update();
		//playerInventory.update();
	}
}
