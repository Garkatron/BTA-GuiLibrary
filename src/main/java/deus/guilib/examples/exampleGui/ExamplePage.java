package deus.guilib.examples.exampleGui;

import deus.guilib.GuiLib;
import deus.guilib.guimanagement.routing.Page;
import deus.guilib.guimanagement.routing.Router;
import deus.guilib.nodes.domsystem.XMLProcessor;

public class ExamplePage extends Page {

	public ExamplePage(Router router, String... text) {
		super(GuiLib.class, router);

		styleSheetPath = "/assets/guilib/examples/craftingTableExample/craftingTable.yaml";
		xmlPath = "/assets/guilib/examples/craftingTableExample/craftingTable.xml";


		// ! DON'T DELETE IT
		reloadXml();
		reloadStyles();

	}
}
