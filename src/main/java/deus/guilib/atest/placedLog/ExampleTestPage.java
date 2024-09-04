package deus.guilib.atest.placedLog;


import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.elements.Button;
import deus.guilib.element.elements.PlayerInventory;
import deus.guilib.element.elements.Slot;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExampleTestPage extends Page {

	private final Button button = new Button().setOnReleaseAction(
		(b) -> {
			router.navigateTo("/test");
		}
	);

	public ExampleTestPage(Router router) {
		super(router);
		content.add(button);
		content.add(
			new PlayerInventory()
				.setSize(176, 166)
				.config(
					(ElementConfig) ElementConfig.create()
						.setTheme("DARK")
				)
		);
		content.add(new Slot().setX(0).setY(0));
	}

	@Override
	public void update() {
		super.update();
		button.update(mouseX, mouseY);
	}
}
