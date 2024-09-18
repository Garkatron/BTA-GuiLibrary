package deus.guilib.atest.example.book;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.Button;
import deus.guilib.element.elements.FreeElement;
import deus.guilib.element.elements.ScrollBar;
import deus.guilib.element.elements.Text;
import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExamplePageBookCover extends Page {

	// Define the "Next" button
	private final Button NEXT_BUTTON = (Button) new Button()
		.setDefaultTextureRegion(0, 2)
		.setPressedTextureRegion(1, 2)
		.setHoverTextureRegion(2, 2)
		.setOnReleaseAction((b) -> router.next()) // Move to the next page
		.setTexture(new Texture("assets/textures/gui/example/paperBorders.png", 20, 20))
		.config((c) -> c.setTheme("NONE"))
		.setPosition(Placement.BOTTOM_RIGHT)
		.setSid("NEXT_B");


	// Constructor that initializes the page with buttons and text content
	public ExamplePageBookCover(Router router) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CENTER));

		addContent(
			new FreeElement(
				new Texture("assets/textures/gui/example/cover.png", 158, 220))
				.config((c) -> c
					.setTheme("NONE")
					.setCentered(true)
					.setChildrenPlacement(Placement.CHILD_DECIDE)
				)
				.addChildren(
					NEXT_BUTTON
				)
				.setSid("FREEELEMENT")

		);

	}

	// Updates button states based on mouse position
	@Override
	public void update() {
		super.update();
		NEXT_BUTTON.update(mouseX, mouseY);
	}
}
