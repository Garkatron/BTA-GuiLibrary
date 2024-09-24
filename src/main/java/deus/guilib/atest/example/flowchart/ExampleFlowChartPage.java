package deus.guilib.atest.example.flowchart;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.elements.representation.GraphElement;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExampleFlowChartPage extends Page {

	// private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40).setPosition(Placement.CENTER).setSid("INV");

	private final GraphElement graphElement1 = (GraphElement) new GraphElement()
		.config(c -> c.setIgnoreParentPlacement(true))
		.setPosition(80, 20)
		.setSid("AAA");

	private final GraphElement graphElement2 = (GraphElement) new GraphElement()
		.addConnections(graphElement1)
		.setPosition(20, 20)
		.setSid("DAS");

	private final GraphElement graphElement3 = (GraphElement) new GraphElement()
		.addConnections(graphElement2)
		.setPosition(100, 100)
		.setSid("XYZ");

	private final GraphElement graphElement4 = (GraphElement) new GraphElement()
		.addConnections(graphElement3)
		.setPosition(200, 50)
		.setSid("LMN");

	public ExampleFlowChartPage(Router router, String... text) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CHILD_DECIDE));
		addContent(
			graphElement1,
			graphElement2,
			graphElement3,
			graphElement4
		);
	}

	@Override
	public void update() {
		super.update();
		// playerInventory.update();
	}
}
