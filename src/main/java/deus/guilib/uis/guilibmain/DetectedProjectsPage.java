package deus.guilib.uis.guilibmain;

import deus.guilib.GuiLib;
import deus.guilib.guimanagement.routing.Page;
import deus.guilib.guimanagement.routing.Router;
import deus.guilib.interfaces.nodes.IButton;

public class DetectedProjectsPage extends Page {

	public DetectedProjectsPage(Router router) {
		super(GuiLib.class, router);

		this.xmlPath = "/assets/guilib/uis/UIprojectsDetected/index.xml";

		// ! DON'T DELETE IT
		reloadXml();
		reloadStyles();

		IButton b = (IButton) getDocument().getNodeById("backButton");
		b.setOnReleaseAction(
			(v) -> {
				router.navigateTo("home");
			}
		);

	}
}
