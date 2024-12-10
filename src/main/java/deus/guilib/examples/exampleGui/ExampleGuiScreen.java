package deus.guilib.examples.exampleGui;

import deus.guilib.guimanagement.AdvancedGuiScreen;
import deus.guilib.guimanagement.routing.Page;

public class ExampleGuiScreen extends AdvancedGuiScreen {
	private static final Page page = new ExamplePage(router);

	static {
		router.registerRoute("0ºhome", page);
		router.navigateTo("0ºhome");
	}

	public ExampleGuiScreen() {

	}
}
