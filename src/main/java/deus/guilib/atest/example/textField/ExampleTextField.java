package deus.guilib.atest.example.textField;

import deus.guilib.element.config.ChildrenPlacement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.PlayerInventory;
import deus.guilib.element.elements.TextField;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExampleTextField extends Page {

	private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40)
		.setSid("INV")
		.config(
			ElementConfig.create()
				.setTheme("VANILLA")
				.setIgnoreFatherPlacement(true)
		);
	private final TextField textField = (TextField) new TextField()
		.setSid("A");


	public ExampleTextField(Router router) {
		super(router);
		config(GuiConfig.create().setPlacement(ChildrenPlacement.TOP));

		addContent(
			playerInventory,
			textField
		);
	}

	@Override
	public void update() {
		super.update();
		playerInventory.update();
		textField.update(mouseX,mouseY);
	}
}
