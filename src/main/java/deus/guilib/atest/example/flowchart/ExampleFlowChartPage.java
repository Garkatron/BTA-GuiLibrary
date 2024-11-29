package deus.guilib.atest.example.flowchart;


import deus.guilib.element.elements.representation.FlowChartElement;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExampleFlowChartPage extends Page {

	// private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40).setPosition(Placement.CENTER).setSid("INV");

	private final FlowChartElement graphElement1 = (FlowChartElement) new FlowChartElement()
		.setPosition(80, 20)
		.setSid("AAA");

	private final FlowChartElement graphElement2 = (FlowChartElement) new FlowChartElement()
		.addConnections(graphElement1)
		.setPosition(20, 20)
		.setSid("DAS");

	private final FlowChartElement graphElement3 = (FlowChartElement) new FlowChartElement()
		.addConnections(graphElement2)
		.setPosition(100, 100)
		.setSid("XYZ");

	private final FlowChartElement graphElement4 = (FlowChartElement) new FlowChartElement()
		.addConnections(graphElement3)
		.setPosition(200, 50)
		.setSid("LMN");

	public ExampleFlowChartPage(Router router, String... text) {
		super(router);
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
