package deus.guilib.examples.exampleGui;

import deus.guilib.guimanagement.routing.Page;
import deus.guilib.guimanagement.routing.Router;

public class ExamplePage extends Page {

	public ExamplePage(Router router, String... text) {
		super(router);

		styleSheetPath = "/assets/guilib/xml/test.yaml";
		xmlPath = "/assets/guilib/xml/test.xml";

		// XMLProcessor.printChildNodes(XMLProcessor.parseXML(xmlPath),"-",0);

		// ! DON'T DELETE IT
		reloadXml();
		reloadStyles();

	}
}
