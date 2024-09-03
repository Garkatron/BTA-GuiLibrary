package deus.guilib.atest.placedLog;


import deus.guilib.element.config.ElementConfig;
import deus.guilib.element.elements.Button;
import deus.guilib.element.elements.PlayerInventory;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class TestPage extends Page {

	private final Button button = new Button().setOnReleaseAction(
		(b) -> {
			router.navigateTo("/test");
		}
	);

	public TestPage(Router router) {
		super(router);
		content.add(button);
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
		button.update(mouseX, mouseY);
	}
}
