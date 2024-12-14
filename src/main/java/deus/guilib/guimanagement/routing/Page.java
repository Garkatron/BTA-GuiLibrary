package deus.guilib.guimanagement.routing;

import deus.guilib.GuiLib;
import deus.guilib.nodes.Root;
import deus.guilib.nodes.config.Placement;
import deus.guilib.nodes.domsystem.XMLProcessor;
import deus.guilib.nodes.stylesystem.StyleSystem;
import deus.guilib.gssl.Signal;
import deus.guilib.interfaces.IPage;
import deus.guilib.util.math.PlacementHelper;
import deus.guilib.util.math.Tuple;
import net.minecraft.client.Minecraft;

import java.util.*;

/**
 * Serves as a base class for creating custom pages. Manages elements, their layout, and rendering within the page.
 */
public abstract class Page implements IPage {

	protected Router router; // Manages navigation between pages.
	protected Minecraft mc; // Reference to the Minecraft instance for rendering.
	protected int width = 0, height = 0; // Dimensions of the page.
	protected int xSize = 0, ySize = 0; // Additional size configuration for page layout.
	private Root document; // Represents the root node of the page.
	public final Signal<Tuple<Integer, Integer>> onResize = new Signal<>(); // Triggered when the page resizes.
	public Map<String, Object> styles = new HashMap<>(); // Stores styles for the page's elements.

	public String styleSheetPath = ""; // Path to the stylesheet used by the page.
	public String xmlPath = ""; // Path to the XML configuration for the page.
	private Class<?> modMainClass;

	/**
	 * Constructs a Page with the specified router.
	 *
	 * @param router The router used for navigation.
	 */
	public Page(Class<?> modMainClass, Router router) {
		this.router = router;
		this.mc = Minecraft.getMinecraft(this);
		this.modMainClass = modMainClass;

		// Connect resize event to reposition elements when necessary
		onResize.connect(t -> {
			PlacementHelper.positionElement(document, Placement.CHILD_DECIDE, width, height);
		});
	}

	/**
	 * Retrieves the root document of the page.
	 *
	 * @return the root document as a {@link Root} object.
	 */
	@Override
	public Root getDocument() {
		return document;
	}

	/**
	 * Reloads the styles for the page from the specified stylesheet path.
	 * Applies these styles to the current document's nodes.
	 */
	public void reloadStyles() {
		if (!styleSheetPath.isEmpty()) {
			if(!styleSheetPath.startsWith("/assets")) {
				styles = StyleSystem.loadFromWithDefault(styleSheetPath);
				// GuiLib.LOGGER.debug("Styles content:\n{}", styles);
			} else {
				styles = StyleSystem.loadFromAssets(modMainClass, styleSheetPath);
			}
		}

		StyleSystem.iterateSelectors(styles, document);
		StyleSystem.applyStylesByIterNodes(styles, document);
	}

	/**
	 * Reloads the XML configuration for the page from the specified XML path.
	 * Updates the root document accordingly.
	 */
	public void reloadXml() {
		if (!xmlPath.isEmpty()) {
			if (!xmlPath.startsWith("/assets")) {
				document = (Root) XMLProcessor.getNodeTree(xmlPath, true);
			} else {
				document = (Root) XMLProcessor.getNodeTree(modMainClass.getResourceAsStream(xmlPath), true);
			}
			//XMLProcessor.printChildNodes(document,"-",0);
			Map<String, String> attrs = document.getAttributes();
			if (attrs.containsKey("yaml_path")) {
				styleSheetPath = attrs.getOrDefault("yaml_path", styleSheetPath);
			}
		}
	}

	/**
	 * Renders the content of the page, positioning elements based on their configuration.
	 * Ensures layout consistency during rendering.
	 */
	public void render() {
		PlacementHelper.positionElement(document, Placement.CHILD_DECIDE, width, height);
		document.draw();
	}

	// Setters for page dimensions.
	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setXYWH(int xSize, int ySize, int width, int height) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.width = width;
		this.height = height;
	}

	/**
	 * Updates all updatable elements on the page.
	 * Ensures the dimensions and layout of the root document are refreshed.
	 */
	public void update() {
		if (document != null) {
			document.setWidth(width);
			document.setHeight(height);
			document.update();
		} else {
			GuiLib.LOGGER.info("The document(Root node) is null, check your XML");
		}
	}
}
