package deus.guilib.uis.guilibmain;

import deus.guilib.GuiLib;
import deus.guilib.guimanagement.routing.Page;
import deus.guilib.guimanagement.routing.Router;
import deus.guilib.nodes.types.interaction.Button;

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
