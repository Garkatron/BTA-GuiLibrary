package deus.guilib.atest.example;


import deus.guilib.element.config.ChildrenPlacement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.*;
import deus.guilib.element.interfaces.IElement;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

import java.util.List;

public class ExamplePage extends Page {


	private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40)
		.setSid("INV")
		.config(ElementConfig.create().setIgnoreFatherPlacement(true));

	public ExamplePage(Router router) {
		super(router);
		config(GuiConfig.create().setPlacement(ChildrenPlacement.TOP));

		addContent(
			playerInventory,
			new Slot().setSid("SLOT1").setGroup("TEST"),
			new Slot().setSid("SLOT2").setGroup("TEST"),
			new Slot().setSid("SLOT3")


		);

	}

	@Override
	public void update() {
		super.update();
		playerInventory.update();
	}
}
