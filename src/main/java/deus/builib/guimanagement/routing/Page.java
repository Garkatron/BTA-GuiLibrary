package deus.builib.guimanagement.routing;

import deus.builib.GuiLib;
import deus.builib.nodes.Root;
import deus.builib.nodes.config.Placement;
import deus.builib.nodes.domsystem.XMLProcessor;
import deus.builib.nodes.stylesystem.StyleSystem;
import deus.builib.gssl.Signal;
import deus.builib.interfaces.IPage;
import deus.builib.nodes.stylesystem.YAMLError;
import deus.builib.util.math.PlacementHelper;
import deus.builib.util.math.Tuple;
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
	public final Signal<Tuple<Integer, Integer>> onResizeSignal = new Signal<>(); // Triggered when the page resizes.
	public Map<String, Object> styles = new HashMap<>(); // Stores styles for the page's elements.

	public List<String> styleSheetPath = List.of(""); // Path to the stylesheet used by the page.
	public String xmlPath = ""; // Path to the XML configuration for the page.
	private final Class<?> modMainClass;
	private Optional<Runnable> logic = Optional.empty();

	/**
	 * Constructs a Page with the specified router.
	 *
	 * @param router The router used for navigation.
	 */
	public Page(Class<?> modMainClass, Router router) {
		GuiLib.LOGGER.info("[Page loaded]");
		this.router = router;
		this.mc = Minecraft.getMinecraft(this);
		this.modMainClass = modMainClass;

		// ? Connect resize event to reposition elements when necessary
		onResizeSignal.connect(this::onResize);
	}

	protected void onResize(Object ref, Object value) {
		GuiLib.LOGGER.info("!Signal emitted with size (width: {}, height; {})", width, height);
		PlacementHelper.positionElement(document, Placement.CHILD_DECIDE, width, height);
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
	/**
	 * Reloads the styles for the page from the specified stylesheet paths.
	 * Applies these styles to the current document's nodes.
	 */
	public void reloadStyles() {
		GuiLib.LOGGER.info("[Reloading Styles]");

		if (styleSheetPath == null || styleSheetPath.isEmpty()) {
			GuiLib.LOGGER.warn("StyleSheetPath is null or empty. Loading default styles.");
			styles = StyleSystem.getDefaultStyles();
		} else {
			Map<String, Object> finalStyles = new HashMap<>();

			if (styleSheetPath.size() > 1) {
				GuiLib.LOGGER.info("Multiple stylesheets detected: {}", styleSheetPath);

				List<Map<String, Object>> loadedStyles = new ArrayList<>();

				// Load each stylesheet
				for (String path : styleSheetPath) {
					if (path != null && !path.isEmpty()) {
						GuiLib.LOGGER.info("Loading styles from: {}", path);
						loadedStyles.add(loadStyles(path));
					} else {
						GuiLib.LOGGER.warn("Encountered an empty or null path in styleSheetPath.");
					}
				}

				// Merge loaded styles
				for (Map<String, Object> styleMap : loadedStyles) {
					if (styleMap != null) {
						finalStyles = StyleSystem.mergeStyles(finalStyles, styleMap);
						GuiLib.LOGGER.info("Merged styles: {}", finalStyles);
					} else {
						GuiLib.LOGGER.warn(YAMLError.NULL_STYLE_MAP.getMessage());
					}
				}
			} else {
				String singlePath = styleSheetPath.get(0);
				if (singlePath != null && !singlePath.isEmpty()) {
					GuiLib.LOGGER.info("Loading single stylesheet: {}", singlePath);
					finalStyles = loadStyles(singlePath);
				} else {
					GuiLib.LOGGER.warn(YAMLError.SINGLE_STYLESHEET_EMPTY.getMessage());
					finalStyles = StyleSystem.getDefaultStyles();
				}
			}

			styles = finalStyles;
		}

		GuiLib.LOGGER.info("Final styles content: {}", styles);

		GuiLib.LOGGER.info("[Applying Styles]");
		StyleSystem.iterateSelectors(styles, document);
	}



	/**
	 * Reloads the XML configuration for the page from the specified XML path.
	 * Updates the root document accordingly.
	 */
	public void reloadXml() {
		GuiLib.LOGGER.info("[Reloading XML]");
		if (!xmlPath.isEmpty()) {
			if (!xmlPath.startsWith("/assets")) {
				document = (Root) XMLProcessor.getNodeTree(xmlPath, true);
			} else {
				document = (Root) XMLProcessor.getNodeTree(modMainClass.getResourceAsStream(xmlPath), true);
			}

			GuiLib.LOGGER.info("Document loaded from: {}", xmlPath);


			Map<String, String> attrs = document.getAttributes();
			if (attrs.containsKey("yaml_path")) {
				styleSheetPath = getYamlPaths(attrs.getOrDefault("yaml_path", styleSheetPath.get(0)));
			}
		} else {
			GuiLib.LOGGER.info("Xml path empty");
		}
	}

	public void reload() {
		reloadXml();
		if (logic.isPresent()) {
			GuiLib.LOGGER.info("Executing setup of the page");
			logic.get().run();
		} else {
			GuiLib.LOGGER.warn("Not logic defined for this page");
		}
		reloadStyles();

	}


	public void setup(Runnable r) {
		logic = Optional.ofNullable(r);
		reload();
	}


	private Map<String, Object> loadStyles(String styleSheetPath) {
		if(!styleSheetPath.startsWith("/assets")) {
			return StyleSystem.loadFromWithDefault(styleSheetPath);
		} else {
			return StyleSystem.loadFromAssets(modMainClass, styleSheetPath);
		}
	}

	private List<String> getYamlPaths(String str) {
		if (str.contains("ª")) {
			return List.of(str.split("ª"));
		} else {
			return List.of(str);
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

	public void setWidth(int width) {
		// GuiLib.LOGGER.info("Set new width: {}", width);
		this.width = width;
	}

	public void setHeight(int height) {
		// GuiLib.LOGGER.info("Set new height: {}", height);
		this.height = height;
	}

	public void setXYWH(int xSize, int ySize, int width, int height) {
		// GuiLib.LOGGER.info("Set new X Y W H: {} | {} | {} | {}", xSize, ySize, width, height);
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
