package deus.guilib.atest.placedLog;

import deus.guilib.element.config.ElementConfig;
import deus.guilib.element.elements.Button;
import deus.guilib.element.elements.FreeElement;
import deus.guilib.element.elements.PlayerInventory;
import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class TestPage2 extends Page {

	private final Button button = new Button().setOnReleaseAction(
		(b) -> {
			router.navigateTo("/home");
		}
	);

	public TestPage2(Router router) {
		super(router);
		content.add(
			button
		);

		content.add(
			new FreeElement(
				new Texture("assets/textures/gui/image.png", 169, 169)
			).setX(50).setY(20).config(ElementConfig.create().setUseTheme(false))
		);

		content.add(
			new PlayerInventory()
				.setSize(176, 166)
				.config(
					ElementConfig.create()
						.setTheme("DARK")
				)
		);
	}

	@Override
	public void update() {
		super.update();
		button.update(mouseX,mouseY);
	}
}
