package deus.guilib.atest.example.progressBar;

import deus.guilib.element.elements.inventory.PlayerInventory;
import deus.guilib.element.elements.representation.ProgressBar;
import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExamplePageProgressBar extends Page {

	private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40)
		.setSid("INV");


	private final ProgressBar progressBar = (ProgressBar) new ProgressBar()
		.setFullTexture(new Texture("assets/textures/gui/completeBar.png", 47, 5))
		//.setTexture(new Texture("assets/textures/gui/uncompleteBar.png", 47, 5))
		.setSid("ProgressBar01")
		;

	private long lastUpdateTime = System.currentTimeMillis();
	private int progressIncrement = 5;

	public ExamplePageProgressBar(Router router) {
		super(router);

		addContent(
			playerInventory,
			progressBar
		);
	}

	@Override
	public void update() {
		super.update();
		playerInventory.update();

		long currentTime = System.currentTimeMillis();

		if (currentTime - lastUpdateTime >= 1000) {
			int currentProgress = progressBar.getProgress();
			if (currentProgress < 100) {
				progressBar.setProgress(currentProgress + progressIncrement);
			}
			lastUpdateTime = currentTime;  // Reset the timer
		}
	}
}
