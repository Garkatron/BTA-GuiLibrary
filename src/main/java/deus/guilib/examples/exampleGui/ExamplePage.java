package deus.guilib.examples.exampleGui;

import deus.guilib.GuiLib;
import deus.guilib.guimanagement.routing.Page;
import deus.guilib.guimanagement.routing.Router;

public class ExamplePage extends Page {

	public ExamplePage(Router router, String... text) {
		super(GuiLib.class, router);

		//styleSheetPath = "C:\\Users\\masit\\IdeaProjects\\BTA-GuiLibrary\\run\\GuiLibrary\\TestFolder\\styles.yaml";
		xmlPath = "C:\\Users\\masit\\IdeaProjects\\BTA-GuiLibrary\\run\\GuiLibrary\\TestFolder\\test.xml";

		// ! DON'T DELETE IT
		reloadXml();
		reloadStyles();

	}
}
