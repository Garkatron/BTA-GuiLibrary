package deus.guilib.atest.example.book;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.Button;
import deus.guilib.element.elements.FreeElement;
import deus.guilib.element.elements.Text;
import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExamplePageBook extends Page {

	// Define the "Next" button
	private final Button NEXT_BUTTON = (Button) new Button()
		.setDefaultTextureRegion(0, 1)
		.setPressedTextureRegion(1, 1)
		.setHoverTextureRegion(2, 1)
		.setOnReleaseAction((b) -> router.next()) // Move to the next page
		.setTexture(new Texture("assets/textures/gui/example/paperBorders.png", 20, 20))
		.config((c) -> c.setTheme("NONE"))
		.setPosition(Placement.BOTTOM_RIGHT)
		.setSid("NEXT_B");

	// Define the "Back" button
	private final Button BACK_BUTTON = (Button) new Button()
		.setOnReleaseAction((b) -> router.back()) // Move to the previous page
		.setDefaultTextureRegion(0, 0)
		.setPressedTextureRegion(1, 0)
		.setHoverTextureRegion(2, 0)
		.setTexture(new Texture("assets/textures/gui/example/paperBorders.png", 20, 20))
		.setPosition(Placement.BOTTOM_LEFT)
		.config((c) -> c.setTheme("NONE"))
		.setSid("BACK_B");

	// Constructor that initializes the page with buttons and text content
	public ExamplePageBook(Router router, String... text) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CENTER));

		addContent(
			new FreeElement(new Texture("assets/textures/gui/example/guideBookPaper.png", 158, 220))
				.config((c) -> c
					.setTheme("NONE")
					.setCentered(true)
					.setChildrenPlacement(Placement.CHILD_DECIDE)
				)
				.setPosition(Placement.CENTER)
				.addChildren(
					new Text()
						.addText(text) // Adds the provided text
						.withShadow(false)
						.setTextColor(0x201120)
						.setMaxTextLength(28)
						.setPosition(Placement.TOP_LEFT)

						.config((c) -> c
							.setIgnoreParentPlacement(false)
						)
						.setSid("01"))
				.setSid("FREELEMENT")
		);

	}

	// Updates button states based on mouse position
	@Override
	public void update() {
		super.update();
		BACK_BUTTON.update(mouseX, mouseY);
		NEXT_BUTTON.update(mouseX, mouseY);
	}
}
