package deus.builib.examples.exampleGui;

import deus.builib.guimanagement.AdvancedScreen;
import deus.builib.guimanagement.routing.Page;

public class ExampleGuiScreen extends AdvancedScreen {
	private static final Page page = new ExamplePage(router);

	static {
		router.registerRoute("0ºhome", page);
		router.navigateTo("0ºhome");
	}

	public ExampleGuiScreen() {

	}
}
