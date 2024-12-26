package deus.guilib.uis.guilibmain;

import deus.guilib.GuiLib;
import deus.guilib.guimanagement.routing.Page;
import deus.guilib.guimanagement.routing.Router;
import deus.guilib.interfaces.nodes.IButton;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.nodes.Root;
import deus.guilib.nodes.types.interaction.ExpandableButton;
import deus.guilib.nodes.types.representation.Label;
import deus.guilib.util.GuiHelper;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DetectedProjectsPage extends Page {

	private static final String TEST_FOLDER_PATH = "..\\run\\GuiLibrary\\TestFolder";

	public DetectedProjectsPage(Router router) {
		super(GuiLib.class, router);

		this.xmlPath = "/assets/guilib/uis/UIprojectsDetected/index.xml";

		// ! DON'T DELETE IT

		setup(() -> {
			GuiLib.LOGGER.info("Page logic reloaded");

			Root doc = getDocument();
			configureBackButton(doc);
			loadFilesAndAddButtons(doc);

		});

		reload();

	}

	private void configureBackButton(Root doc) {
		IButton backButton = (IButton) doc.getNodeById("backButton");
		if (backButton!=null) {
			backButton.setOnReleaseAction(v -> router.navigateTo("home"));
		}
	}

	private void loadFilesAndAddButtons(Root doc) {
		List<File> files = searchXmlFiles(TEST_FOLDER_PATH);

		if (files.isEmpty()) {
			GuiLib.LOGGER.warn("No files found in the directory: " + TEST_FOLDER_PATH);
		}

		for (File file : files) {
			createExpandableButton(doc, file);
		}
	}

	private void createExpandableButton(Root doc, File file) {
		INode component = GuiHelper.getComponent("bui.options.pathButton");
		ExpandableButton expandableButton = (ExpandableButton) component.getNodeById("bui.options.pathButton.button");

		Label label = (Label) expandableButton.getChildren().get(0);
		label.addText(file.getName());

		expandableButton.setOnReleaseAction(v -> {
			router.registerRoute(file.getName(), new ViewPage(file.getPath(), router));
			router.navigateTo(file.getName());
		});

		doc.getNodeById("mainScrollableLayout").addChildren(component);
	}

	private List<File> searchXmlFiles(String directoryPath) {
		try {
			return Files.walk(Paths.get(directoryPath))
				.filter(p -> p.toString().endsWith(".xml"))
				.map(p -> p.toFile())
				.toList();
		} catch (IOException e) {
			GuiLib.LOGGER.error("Error reading XML files from the directory: " + directoryPath, e);
			return List.of();
		}
	}

}
