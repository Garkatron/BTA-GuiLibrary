package deus.guilib.atest.example.exampleGui;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.*;

import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;
import deus.guilib.util.math.Tuple;
import net.minecraft.core.item.Item;

public class ExamplePage extends Page {

	private final Button NEXT_BUTTON = (Button) new Button()
		.setDefaultTextureRegion(0,1)
		.setPressedTextureRegion(1,1)
		.setHoverTextureRegion(2,1)
		.setOnReleaseAction((b)->{
			router.next();
		})
		.setTexture(new Texture("assets/textures/gui/example/paperBorders.png",20,20))
		.config(c->c.setPlacement(Placement.BOTTOM_RIGHT).setTheme("NONE"))
		.setSid("NEXT_B");

	private final Button BACK_BUTTON = (Button) new Button()

		.setOnReleaseAction((b)->{
			router.back();
		})
		.setDefaultTextureRegion(0,0)
		.setPressedTextureRegion(1,0)
		.setHoverTextureRegion(2,0)
		.setTexture(new Texture("assets/textures/gui/example/paperBorders.png",20,20))

		.config(c->c.setPlacement(Placement.BOTTOM_LEFT).setTheme("NONE"))
		.setSid("BACK_B");


	public ExamplePage(Router router, String... text) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CHILD_DECIDE));

		addContent(
				new FreeElement(new Texture("assets/textures/gui/example/guideBookPaper.png",158,220))
					.config(c->c
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
								text
							)
							.withShadow(false)
							.setTextColor(0x201120)
							.setMaxTextLength(28)
							.config(c->c
								.setIgnoreFatherPlacement(false)
								.setPlacement(Placement.TOP_LEFT)
							).setSid("01")

					)
					.setSid("FREELEMENT")
		);
	}

	@Override
	public void update() {
		super.update();
		BACK_BUTTON.update(mouseX,mouseY);
		NEXT_BUTTON.update(mouseX,mouseY);
	}
}
