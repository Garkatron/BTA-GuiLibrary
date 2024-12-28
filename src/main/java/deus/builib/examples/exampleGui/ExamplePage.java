package deus.builib.examples.exampleGui;

import deus.builib.GuiLib;
import deus.builib.guimanagement.routing.Page;
import deus.builib.guimanagement.routing.Router;

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
