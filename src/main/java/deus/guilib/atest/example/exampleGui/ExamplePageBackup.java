package deus.guilib.atest.example.exampleGui;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.Button;
import deus.guilib.element.elements.FreeElement;
import deus.guilib.element.elements.Text;
import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExamplePageBackup extends Page {

	private final Button NEXT_BUTTON = (Button) new Button()
		.setDefaultTextureRegion(0,1)
		.setPressedTextureRegion(1,1)
		.setHoverTextureRegion(2,1)

		.setTexture(new Texture("assets/textures/gui/example/paperBorders.png",20,20))
		.config(ElementConfig.create().setPlacement(Placement.BOTTOM_RIGHT).setTheme("NONE"))
		.setSid("NEXT_B");

	private final Button BACK_BUTTON = (Button) new Button()
		.setDefaultTextureRegion(0,0)
		.setPressedTextureRegion(1,0)
		.setHoverTextureRegion(2,0)
		.setTexture(new Texture("assets/textures/gui/example/paperBorders.png",20,20))

		.config(ElementConfig.create().setPlacement(Placement.BOTTOM_LEFT).setTheme("NONE"))
		.setSid("BACK_B");

	private final FreeElement oldPaperPage = (FreeElement)
		new FreeElement(new Texture("assets/textures/gui/example/guideBookPaper.png",158,220))
		.config(ElementConfig.create()
				.setTheme("NONE")
				.setTextureCenteredPosition(true)
				.setPlacement(Placement.CENTER)
				.setChildrenPlacement(Placement.CHILD_DECIDE)
		)
			.addChildren(
				BACK_BUTTON,
				NEXT_BUTTON,
				new Text()
				.addText(
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
					"Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
					"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.",
					"Nisi ut aliquip ex ea commodo consequat.",
					"",
					"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore.",
					"Eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident."
				)
					.withShadow(false)
					.setTextColor(0x201120)
					.setMaxTextLength(28)
				.config(ElementConfig.create()
					.setIgnoreFatherPlacement(false)
					.setPlacement(Placement.TOP_LEFT)
				).setSid("01")

			)
		.setSid("FREELEMENT");

	public ExamplePageBackup(Router router) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CHILD_DECIDE));

		addContent(
			oldPaperPage
		);
	}

	@Override
	public void update() {
		super.update();
		BACK_BUTTON.update(mouseX,mouseY);
		NEXT_BUTTON.update(mouseX,mouseY);
	}
}
