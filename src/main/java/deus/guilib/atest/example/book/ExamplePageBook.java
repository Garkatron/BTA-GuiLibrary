package deus.guilib.atest.example.book;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.*;
import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

import static deus.guilib.util.Utils.print;

public class ExamplePageBook extends Page {

	// Define the "Next" button
	private final Button NEXT_BUTTON = (Button) new Button()
		.setDefaultTextureRegion(0, 1)
		.setPressedTextureRegion(1, 1)
		.setHoverTextureRegion(2, 1)
		.setOnReleaseAction((b) -> router.next()) // Move to the next page
		.setTexture(new Texture("assets/textures/gui/example/paperBorders.png", 20, 20))
		.setPosition(263,199)
		.setSid("NEXT_B");

	// Define the "Back" button
	private final Button BACK_BUTTON = (Button) new Button()
		.setOnReleaseAction((b) -> router.back()) // Move to the previous page
		.setDefaultTextureRegion(0, 0)
		.setPressedTextureRegion(1, 0)
		.setHoverTextureRegion(2, 0)
		.setTexture(new Texture("assets/textures/gui/example/paperBorders.png", 20, 20))
		.setPosition(4,200)
		.setSid("BACK_B");


	private final Button CAT00 = (Button) new Button()
		.setOnReleaseAction((b) -> router.navigateTo("2ºhome")) // Move to the previous page
		.setDefaultTextureRegion(0, 0)
		.setPressedTextureRegion(0, 2)
		.setHoverTextureRegion(0, 1)
		.setTexture(new Texture("assets/textures/gui/example/categoryButtons.png", 80, 16))
		.addChildren(
			new Text().addText("Recipes").setPosition(30,0)
		)
		.setPosition(210,30)
		.setSid("CAT00");

	private final Button CAT01 = (Button) new Button()
		.setOnReleaseAction((b) -> router.navigateTo("2ºhome")) // Move to the previous page
		.setDefaultTextureRegion(0, 0)
		.setPressedTextureRegion(0, 2)
		.setHoverTextureRegion(0, 1)
		.setTexture(new Texture("assets/textures/gui/example/categoryButtons.png", 80, 16))
		.addChildren(
			new Text().addText("Mobs").setPosition(44,0)
		)
		.setPosition(210,60)
		.setSid("CAT00");


	// Constructor that initializes the page with buttons and text content
	public ExamplePageBook(Router router, String... text) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CENTER));

		addContent(
			new FreeElement(
				new Texture("assets/textures/gui/example/guideBookPaper.png", 290, 221,512,1.0f/512))
				.config((c) -> c
					.setCentered(true)
					.setChildrenPlacement(Placement.CHILD_DECIDE)
				)
				.addChildren(
					BACK_BUTTON,
					NEXT_BUTTON,
					CAT00,
					CAT01,
					new Text()
						.setTextColor(0x201120)
						.setMaxTextLength(28)
						.addText("Some lore of BTA") // Adds the provided text
						.withShadow(false)
						.setSid("THETEXT")
						.setPosition(5,0),
					new Text()
						.setTextColor(0x201120)
						.setMaxTextLength(28)
						.addText("Categories") // Adds the provided text
						.withShadow(false)
						.setSid("THETEXT")
						.setPosition(290/2+3,0)
				)
				.setSid("FREEELEMENT")
		);

	}

	// Updates button states based on mouse position
	@Override
	public void update() {
		super.update();
		BACK_BUTTON.update(mouseX, mouseY);
		NEXT_BUTTON.update(mouseX, mouseY);
		CAT00.update(mouseX,mouseY);
		CAT01.update(mouseX, mouseY);

	}
}
