package deus.guilib.atest.example.exampleGui;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.*;

import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

import static deus.guilib.util.Utils.print;

public class ExamplePage extends Page {

	public ExamplePage(Router router, String... text) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CHILD_DECIDE));
		addContent(
			new Slot().setSid("A").setPosition(20,0).addChildren(
				new Slot().setSid("AAA").setPosition(22,0).config(c->c.setIgnoreParentPlacement(true))
			)
		);
	}

	@Override
	public void update() {
		super.update();
		print("-->", getElementWithSid("A").getElementWithSid("AAA").getX());
	}
}
