package deus.guilib.atest.example;


import deus.guilib.element.config.ChildrenPlacement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.*;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExamplePage extends Page {


	private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40).config(ElementConfig.create().setIgnoreFatherPlacement(true));

	public ExamplePage(Router router) {
		super(router);
		config(GuiConfig.create().setPlacement(ChildrenPlacement.TOP));

		addContent(
			playerInventory,
			new Slot(),
			new Slot(),
			new Slot()


		);
	}

	@Override
	public void update() {
		super.update();
		playerInventory.update();
	}
}
