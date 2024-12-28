package deus.builib.uis.builibmain;

import deus.builib.GuiLib;
import deus.builib.guimanagement.routing.Page;
import deus.builib.guimanagement.routing.Router;
import deus.builib.interfaces.nodes.IButton;

public class MainPage extends Page {

	public MainPage(Router router, String... text) {
		super(GuiLib.class, router);

		xmlPath = "/assets/guilib/uis/UIbui/index.xml";

		// ! Work here

		setup(()->{
			IButton b = (IButton) getDocument().getNodeById("detectedProjectsButton");
			b.setOnReleaseAction(
				(v) -> {
					router.navigateTo("detectedProjectsPage");
				}
			);

		});

	}
}
