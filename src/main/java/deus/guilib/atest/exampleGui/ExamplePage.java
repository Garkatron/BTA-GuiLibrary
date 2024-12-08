package deus.guilib.atest.exampleGui;

import deus.guilib.nodes.domsystem.XMLProcessor;
import deus.guilib.guimanagement.routing.Page;
import deus.guilib.guimanagement.routing.Router;
import org.lwjgl.input.Keyboard;

public class ExamplePage extends Page {

	//private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40).setPosition(Placement.CENTER).setSid("INV");

	public ExamplePage(Router router, String... text) {
		super(router);

		styleSheetPath = "C:\\Users\\masit\\IdeaProjects\\BTA-GuiLibrary\\run\\GuiLibrary\\CSS\\test.yaml";
		xmlPath = "C:\\Users\\masit\\IdeaProjects\\BTA-GuiLibrary\\run\\GuiLibrary\\CSS\\test.xml";

		XMLProcessor.printChildNodes(XMLProcessor.parseXML(xmlPath),"-",0);
		reloadXml();
		reloadStyles();

	}

	@Override
	public void update() {
		super.update();
		//playerInventory.update();


	}
}
