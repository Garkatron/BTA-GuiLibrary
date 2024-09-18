package deus.guilib.atest.example.book;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.*;
import deus.guilib.interfaces.element.IElement;
import deus.guilib.resource.AnimatedTexture;
import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;
import net.minecraft.core.item.Item;

public class ExamplePageBook2 extends Page {
	private AnimatedTexture animatedTexture;

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
		.setOnReleaseAction((b) ->
				animatedTexture.startAnimation()
			//router.back()
			) // Move to the previous page
		.setDefaultTextureRegion(0, 0)
		.setPressedTextureRegion(1, 0)
		.setHoverTextureRegion(2, 0)
		.setTexture(new Texture("assets/textures/gui/example/paperBorders.png", 20, 20))
		.setPosition(4,200)
		.setSid("BACK_B");

	// Constructor that initializes the page with buttons and text content
	public ExamplePageBook2(Router router) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CENTER));

		int[][] frames = {
			{0, 0},      // Offset para el primer frame
			{0, 1},     // Offset para el segundo frame (suponiendo que cada frame tiene 64 píxeles de ancho)
			{0, 2},    // Offset para el tercer frame
		};

		// Ruta de la textura (puede ser un recurso del mod)
		String texturePath = "assets/textures/gui/example/anim.png";

		// Crear la textura animada con un ancho de 64 píxeles, alto de 64 píxeles y velocidad de animación de 0.5 segundos por frame
		animatedTexture = new AnimatedTexture(32, 32, 0.5f, frames, texturePath);


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

					new CraftingTable()
						.setFake(true)
						.modifyChildren(c -> {
							((Slot) c.get(0)).setFakeItem(Item.diamond);
							((Slot) c.get(1)).setFakeItem(Item.diamond);
							((Slot) c.get(2)).setFakeItem(Item.diamond);
							((Slot) c.get(4)).setFakeItem(Item.stick);
							((Slot) c.get(7)).setFakeItem(Item.stick);

						})
						.setPosition((290 / 4) - (18 * 3) / 2, 10),
					new FreeElement(animatedTexture)
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
		animatedTexture.update();
	}
}
