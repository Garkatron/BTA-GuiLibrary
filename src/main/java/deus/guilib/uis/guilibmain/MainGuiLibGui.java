package deus.guilib.uis.guilibmain;

import deus.guilib.examples.exampleGui.ExamplePage;
import deus.guilib.guimanagement.AdvancedGuiScreen;
import deus.guilib.guimanagement.routing.Page;

public class MainGuiLibGui extends AdvancedGuiScreen {
	private static final Page detectedProjectsPage = new DetectedProjectsPage(router);
	private static final Page mainPage = new MainPage(router);

	static {
		// º
		router.registerRoute("detectedProjectsPage", detectedProjectsPage);
		router.registerRoute("home", mainPage);
		router.navigateTo("home");
	}

	public MainGuiLibGui() {

	}
}
