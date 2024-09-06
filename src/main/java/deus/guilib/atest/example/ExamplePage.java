package deus.guilib.atest.example;

import deus.guilib.element.config.ChildrenPlacement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.PlayerInventory;
import deus.guilib.element.elements.ProgressBar;
import deus.guilib.resource.Texture;
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


	public ExamplePage(Router router) {
		super(router);
		config(GuiConfig.create().setPlacement(ChildrenPlacement.TOP));

		addContent(
			playerInventory
		);
	}

	@Override
	public void update() {
		super.update();
		playerInventory.update();

	}
}
