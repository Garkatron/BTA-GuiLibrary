package deus.builib.uis.builibmain;

import deus.builib.GuiLib;
import deus.builib.guimanagement.routing.Page;
import deus.builib.guimanagement.routing.Router;
import deus.builib.nodes.types.interaction.Button;

import java.util.Map;

public class ViewPage extends Page {

	public ViewPage(String xml, Router router) {
		super(GuiLib.class, router);

		this.xmlPath = xml;

		setup(
			()->{
				Button b = new Button();
				b.setOnReleaseAction(
					(v)->{
						router.backPreviousPage();
					}
				);
				getDocument().addChildren(
					b.setAttributes(Map.of("id", "backButton"))
				);
			}
		);

	}
}
