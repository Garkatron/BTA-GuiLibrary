package deus.guilib.atest.example.exampleGui;

import deus.guilib.element.config.ChildrenPlacement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.FreeElement;
import deus.guilib.element.elements.PlayerInventory;

import deus.guilib.element.elements.TextArea;
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

	private final FreeElement freeElement = (FreeElement) new FreeElement(new Texture("assets/textures/gui/example/oldPaper.png",196,256))
		.config(ElementConfig.create().setTextureCenteredPosition(true)).setSid("FREELEMENT");

	public ExamplePage(Router router) {
		super(router);
		config(GuiConfig.create().setPlacement(ChildrenPlacement.CENTER));

		addContent(
			//playerInventory,
			freeElement
		);
	}

	@Override
	public void update() {
		super.update();
		//playerInventory.update();
	}
}
