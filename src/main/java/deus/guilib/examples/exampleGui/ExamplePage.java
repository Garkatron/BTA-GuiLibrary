package deus.guilib.examples.exampleGui;

import deus.guilib.GuiLib;
import deus.guilib.guimanagement.routing.Page;
import deus.guilib.guimanagement.routing.Router;
import deus.guilib.nodes.Node;
import deus.guilib.nodes.domsystem.XMLProcessor;
import deus.guilib.nodes.types.semantic.Div;

import java.util.HashMap;

public class ExamplePage extends Page {

	public ExamplePage(Router router, String... text) {
		super(GuiLib.class, router);

		//styleSheetPath = "C:\\Users\\masit\\IdeaProjects\\BTA-GuiLibrary\\run\\GuiLibrary\\TestFolder\\test.yaml";

		// ! Here is your bind function, @Kheppanant Khepnacious Kheppery :/
		this.bindXml("C:\\Users\\masit\\IdeaProjects\\BTA-GuiLibrary\\run\\GuiLibrary\\TestFolder\\test.xml");

		// ! DON'T DELETE IT
		reloadXml();
		reloadStyles();

	}
}
