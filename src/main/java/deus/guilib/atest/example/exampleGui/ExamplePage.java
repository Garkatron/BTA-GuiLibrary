package deus.guilib.atest.example.exampleGui;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.config.derivated.TextFieldStyle;
import deus.guilib.element.elements.*;

import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;
import net.minecraft.client.gui.GuiButton;

import static deus.guilib.util.Utils.print;

public class ExamplePage extends Page {

	private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40).setPosition(Placement.CENTER).setSid("INV");

	public ExamplePage(Router router, String... text) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CHILD_DECIDE));
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
