package deus.builib.uis.guilibmain;

import deus.builib.guimanagement.AdvancedGuiScreen;
import deus.builib.guimanagement.routing.Page;

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
