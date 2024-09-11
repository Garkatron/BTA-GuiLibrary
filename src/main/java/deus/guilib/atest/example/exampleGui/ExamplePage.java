package deus.guilib.atest.example.exampleGui;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.Button;
import deus.guilib.element.elements.FreeElement;
import deus.guilib.element.elements.PlayerInventory;

import deus.guilib.element.elements.Text;
import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExamplePage extends Page {

	private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40)
		.setSid("INV")
		.config(
			ElementConfig.create()
				.setTheme("VANILLA")
				.setIgnoreFatherPlacement(true)
		);

	private final Button NEXT_BUTTON = (Button) new Button()
		.config(ElementConfig.create().setPlacement(Placement.BOTTOM_RIGHT))
		.setSid("NEXT_B");

	private final Button BACK_BUTTON = (Button) new Button()
		.config(ElementConfig.create().setPlacement(Placement.BOTTOM_LEFT))
		.setSid("BACK_B");

	private final FreeElement oldPaperPage = (FreeElement)
		new FreeElement(new Texture("assets/textures/gui/example/oldPaper.png",196,256))
		.config(ElementConfig.create()
				.setTextureCenteredPosition(true)
				.setPlacement(Placement.CENTER)
				.setChildrenPlacement(Placement.CHILD_DECIDE)
		)
			.addChildren(
				BACK_BUTTON,
				NEXT_BUTTON,
				new Text()
				.setText("Some text here.")
				.config(ElementConfig.create()
					.setIgnoreFatherPlacement(false)
					.setPlacement(Placement.TOP_LEFT)
				).setSid("01")
			)
		.setSid("FREELEMENT");

	public ExamplePage(Router router) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CHILD_DECIDE));

		addContent(
			//playerInventory,
			oldPaperPage




		);
	}

	@Override
	public void update() {
		super.update();
		//playerInventory.update();
	}
}
