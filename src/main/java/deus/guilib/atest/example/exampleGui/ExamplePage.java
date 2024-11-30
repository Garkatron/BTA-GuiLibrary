package deus.guilib.atest.example.exampleGui;

import deus.guilib.element.elements.semantic.Body;
import deus.guilib.element.elements.other.FreeElement;
import deus.guilib.element.elements.semantic.Div;
import deus.guilib.element.stylesystem.StyleSystem;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;
import org.lwjgl.input.Keyboard;

public class ExamplePage extends Page {

	//private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40).setPosition(Placement.CENTER).setSid("INV");

	public ExamplePage(Router router, String... text) {
		super(router);

		styleSheetPath = "C:/Users/masit/IdeaProjects/BTA-GuiLibrary/run/GuiLibrary/CSS/test.yaml";

		addContent(
			//playerInventory
			new FreeElement().addChildren(
				new Body().addChildren(
					new FreeElement().setSid("test"),

					new Div().setGroup("group").addChildren(
						new FreeElement().setSid("test2")
					)
				)
			)
		);
		reloadStyles();
	}

	@Override
	public void update() {
		super.update();
		//playerInventory.update();

		if (Keyboard.getEventKeyState()) {
			int key = Keyboard.getEventKey();
			if (key == Keyboard.KEY_F12) {
				reloadStyles();
			}
		}
	}
}
