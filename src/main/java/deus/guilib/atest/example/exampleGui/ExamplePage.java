package deus.guilib.atest.example.exampleGui;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.*;

import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

import static deus.guilib.util.Utils.print;

public class ExamplePage extends Page {

	private final ScrollBar scrollBar = (ScrollBar) new ScrollBar().setLength(10).setScrollVertical().setSid("A");
	private final DraggableElement draggableElement = (DraggableElement) new DraggableElement(new Texture("assets/textures/gui/Button.png", 18, 18)).config(c->c.setTheme("NONE").setIgnoreParentPlacement(true));

	public ExamplePage(Router router, String... text) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CHILD_DECIDE));
		addContent(
			scrollBar.setPosition(Placement.LEFT)
			//draggableElement
		);
	}

	@Override
	public void update() {
		super.update();
		scrollBar.update(mouseX,mouseY);
		//draggableElement.update(mouseX,mouseY);
	}
}
