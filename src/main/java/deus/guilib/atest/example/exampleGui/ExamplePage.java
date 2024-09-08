package deus.guilib.atest.example.exampleGui;

import deus.guilib.element.config.ChildrenPlacement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.PlayerInventory;

import deus.guilib.element.elements.TextArea;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExamplePage extends Page {

	private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40)
		.setSid("INV")
		.config(
			ElementConfig.create()
				.setTheme("VANILLA")
				.setIgnoreFatherPlacement(true)
		);
	private final TextArea textArea = (TextArea) new TextArea()
		.setSid("A");


	public ExamplePage(Router router) {
		super(router);
		config(GuiConfig.create().setPlacement(ChildrenPlacement.TOP));

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
