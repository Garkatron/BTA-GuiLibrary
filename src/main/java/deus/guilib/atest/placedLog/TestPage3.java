package deus.guilib.atest.placedLog;


import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.*;
import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class TestPage3 extends Page {


	private final PlayerInventory2 playerInventory = (PlayerInventory2) new PlayerInventory2(40).config(ElementConfig.create().setIgnoreFatherPlacement(true));

	public TestPage3(Router router) {
		super(router);
		config(GuiConfig.create());

		addContent(
			playerInventory,
			new Row()
				.setLength(4)
				.addChildren(
				new Slot(),
				new Slot(),
				new Slot(),
				new Slot()
			)
				.config(ElementConfig.create().setTheme("VANILLA"))
		);


	}

	@Override
	public void update() {
		super.update();
		playerInventory.update();
	}
}
