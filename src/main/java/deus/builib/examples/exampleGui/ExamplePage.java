package deus.builib.examples.exampleGui;

import deus.builib.GuiLib;
import deus.builib.guimanagement.routing.Page;
import deus.builib.guimanagement.routing.Router;

public class ExamplePage extends Page {

	public ExamplePage(Router router, String... text) {
		super(GuiLib.class, router);

		xmlPath = "";

		setup(()->{

			// Your code here

		});

	}
}
