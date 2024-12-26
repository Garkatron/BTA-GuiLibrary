package deus.guilib.uis.guilibmain;

import deus.guilib.GuiLib;
import deus.guilib.guimanagement.routing.Page;
import deus.guilib.guimanagement.routing.Router;
import deus.guilib.interfaces.nodes.IButton;

public class MainPage extends Page {

	public MainPage(Router router, String... text) {
		super(GuiLib.class, router);

		xmlPath = "/assets/guilib/uis/UIbui/index.xml";

		// ! DON'T DELETE IT
		reloadXml();
		reloadStyles();

		// ! Work here

		IButton b = (IButton) getDocument().getNodeById("detectedProjectsButton");
		b.setOnReleaseAction(
			(v) -> {
				router.navigateTo("detectedProjectsPage");
			}
		);
	}
}
