package deus.guilib.atest.placedLog;


import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.elements.Button;
import deus.guilib.element.elements.PlayerInventory;
import deus.guilib.element.elements.Slot;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class TestPage extends Page {

	private final Button button = new Button().setOnReleaseAction(
		(b) -> {
			//router.navigateTo("/test");
		}
	);

	private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory()
		.config(
			ElementConfig.create()
				.setIgnoreFatherPlacement(true)
				.setTheme("DARK")
		);

	public TestPage(Router router) {
		super(router);
		//content.add(button);
		content.add(
			playerInventory
		);
		content.add(new Slot().setX(70).setY(0).config(ElementConfig.create().setTheme("VANILLA")));
	}

	@Override
	public void update() {
		super.update();
		//button.update(mouseX, mouseY);
		playerInventory.setSize(mc.resolution.scaledWidth, mc.resolution.scaledHeight);
	}
}
