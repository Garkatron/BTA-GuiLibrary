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

	private final Button NEXT_BUTTON = (Button) new Button()
		.setDefaultTextureRegion(0, 1)
		.setPressedTextureRegion(1, 1)
		.setHoverTextureRegion(2, 1)
		.setOnReleaseAction((b) -> router.next())
		.setTexture(new Texture("assets/textures/gui/example/paperBorders.png", 20, 20))
		.setPosition(263,199)
		.setSid("NEXT_B");

	private final Button BACK_BUTTON = (Button) new Button()
		.setOnReleaseAction(
			(b) ->
				router.back()
			)
		.setDefaultTextureRegion(0, 0)
		.setPressedTextureRegion(1, 0)
		.setHoverTextureRegion(2, 0)
		.setTexture(new Texture("assets/textures/gui/example/paperBorders.png", 20, 20))
		.setPosition(4,200)
		.setSid("BACK_B");

	public ExamplePageBook2(Router router) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CENTER));

		int[][] frames = {
			{0, 0},
			{0, 1},
			{0, 2},
		};

		String texturePath = "assets/textures/gui/example/anim.png";
		// Adding vertical spacing
		int spacingY = 10; // Space between crafting tables
		int initialY = 10; // Starting Y position for the first crafting table


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
					// First Crafting Table
					new CraftingTable()
						.setFake(true)
						.modifyChildren(c -> {
							((Slot) c.get(0)).setFakeItem(Item.diamond);
							((Slot) c.get(1)).setFakeItem(Item.diamond);
							((Slot) c.get(2)).setFakeItem(Item.diamond);
							((Slot) c.get(4)).setFakeItem(Item.stick);
							((Slot) c.get(7)).setFakeItem(Item.stick);
						})
						.setPosition((290 / 4) - (18 * 3) / 2, initialY),

					// Second Crafting Table, with a Y offset (below the first one)
					new CraftingTable()
						.setFake(true)
						.modifyChildren(c -> {
							((Slot) c.get(0)).setFakeItem(Item.diamond);
							((Slot) c.get(1)).setFakeItem(Item.diamond);
							((Slot) c.get(2)).setFakeItem(Item.diamond);
							((Slot) c.get(4)).setFakeItem(Item.stick);
							((Slot) c.get(7)).setFakeItem(Item.stick);
						})
						.setPosition((290 / 4) - (18 * 3) / 2, initialY + (18 * 3) + spacingY),

					// Third Crafting Table, with a greater Y offset (below the second one)
					new CraftingTable()
						.setFake(true)
						.modifyChildren(c -> {
							((Slot) c.get(0)).setFakeItem(Item.diamond);
							((Slot) c.get(1)).setFakeItem(Item.diamond);
							((Slot) c.get(2)).setFakeItem(Item.diamond);
							((Slot) c.get(4)).setFakeItem(Item.stick);
							((Slot) c.get(7)).setFakeItem(Item.stick);
						})
						.setPosition((290 / 4) - (18 * 3) / 2, initialY + 2 * ((18 * 3) + spacingY))
				)
				.setSid("FREEELEMENT")
		);

	}

	@Override
	public void update() {
		super.update();
		BACK_BUTTON.update(mouseX, mouseY);
		NEXT_BUTTON.update(mouseX, mouseY);
	}
}
