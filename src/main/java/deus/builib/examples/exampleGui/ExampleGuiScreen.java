package deus.builib.examples.exampleGui;

import deus.builib.guimanagement.AdvancedGuiScreen;
import deus.builib.guimanagement.routing.Page;

public class ExampleGuiScreen extends AdvancedGuiScreen {
	private static final Page page = new ExamplePage(router);

	static {
		router.registerRoute("0ºhome", page);
		router.navigateTo("0ºhome");
	}

	public ExampleGuiScreen() {

	}
}
