package deus.guilib.atest.example.textArea;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.inventory.PlayerInventory;
import deus.guilib.element.elements.interaction.TextArea;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExamplePageTestArea extends Page {

	private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40)
		.setSid("INV")
		.config(
			c->c
				.setIgnoreParentPlacement(false)
		);
	private final TextArea textArea = (TextArea) new TextArea()
		.setSid("A");


	public ExamplePageTestArea(Router router) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.TOP));

		addContent(
			playerInventory,
			textArea
		);
	}

	@Override
	public void update() {
		super.update();
		playerInventory.update();
		textArea.update(mouseX,mouseY);
	}
}
