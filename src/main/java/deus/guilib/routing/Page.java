package deus.guilib.routing;

import deus.guilib.nodes.config.Placement;
import deus.guilib.nodes.domsystem.XMLProcessor;
import deus.guilib.nodes.stylesystem.StyleSystem;
import deus.guilib.gssl.Signal;
import deus.guilib.interfaces.INodeFather;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.interfaces.nodes.IRootNode;
import deus.guilib.interfaces.nodes.IUpdatable;
import deus.guilib.error.Error;
import deus.guilib.util.math.PlacementHelper;
import deus.guilib.util.math.Tuple;
import net.minecraft.client.Minecraft;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Serves as a base class for creating custom pages. Manages elements, their layout, and rendering within the page.
 */
public abstract class Page implements INodeFather {

	protected int mouseX = 0, mouseY = 0;
	protected Router router;
	protected Minecraft mc;
	protected int width = 0, height = 0;
	protected int xSize = 0, ySize = 0;
	private List<INode> content = new ArrayList<>();
	public final Signal<Tuple<Integer, Integer>> onResize = new Signal<>();
	public Map<String, Object> styles = new HashMap<>();
	public String styleSheetPath = "";
	public String xmlPath = "";

	/**
	 * Constructs a Page with the specified router.
	 *
	 * @param router The router used for navigation.
	 */
	public Page(Router router) {
		this.router = router;
		this.mc = Minecraft.getMinecraft(this);

		// Connect resize event to reposition elements when necessary
		onResize.connect(t -> content.forEach(c -> {
			if (true) {
				PlacementHelper.positionElement(c, Placement.CHILD_DECIDE, width, height);
			}
		}));

	}

	public void reloadStyles() {
		if (!styleSheetPath.isEmpty()) {
			styles = StyleSystem.loadFromWithDefault(styleSheetPath);
			// GuiLib.LOGGER.debug("Styles content:\n{}", styles);
		}

		IRootNode root = (IRootNode) content.get(0);
		StyleSystem.applyStylesByIterNodes(styles, root);
	}

	public void reloadXml() {
		if (!xmlPath.isEmpty()) {
			ArrayList<INode> newContent = new ArrayList<>();
			newContent.add(
				XMLProcessor.parseXML(xmlPath)
			);
			content = newContent;
		}
	}

	/**
	 * Renders the content of the page, positioning elements based on configuration.
	 */
	public void render() {
		content.forEach(child -> {
			if (true) {
				PlacementHelper.positionElement(child,Placement.CHILD_DECIDE,width,height);
			}
			child.draw();
		});
	}

	/**
	 * Adds elements to the page, ensuring unique SIDs if configured.
	 *
	 * @param elements Elements to be added to the page.
	 */
	public void addContent(INode... elements) {
		if (true) {
			Set<String> existingSids = new HashSet<>();
			for (INode element : elements) {
				String sid = element.getSid();
				if (!existingSids.add(sid)) {
					throw new IllegalArgumentException(Error.SAME_ELEMENT_SID + " SID: " + sid + "\n");
				}
			}
		}
		Collections.addAll(content, elements);
	}


	// Setters for page dimensions
	public void setxSize(int xSize) { this.xSize = xSize; }
	public void setySize(int ySize) { this.ySize = ySize; }
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }
	public void setXYWH(int xSize, int ySize, int width, int height) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns the content of the page.
	 *
	 * @return A list of elements on the page.
	 */
	@Override
	public List<INode> getContent() {
		return content;
	}


	/**
	 * Updates all updatable elements on the page.
	 */
	public void update() {
		if (content == null || content.isEmpty()) {
			return;
		}

		/*
		content.stream()
			.filter(IUpdatable.class::isInstance)
			.map(IUpdatable.class::cast)
			.forEach(updatable -> {
				System.out.println(updatable.getClass().getSimpleName());
				updatable.update(mouseX, mouseY);
			});
		 */

		content.forEach(
			IUpdatable::update
		);

		var rootNode = content.get(0);
		if (rootNode != null) {
			rootNode.setWidth(width);
			rootNode.setHeight(height);
		}
	}

}
