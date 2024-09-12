package deus.guilib.routing;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.gssl.Signal;
import deus.guilib.interfaces.IElementFather;
import deus.guilib.interfaces.element.IElement;
import deus.guilib.interfaces.element.IUpdatable;
import deus.guilib.error.Error;
import deus.guilib.util.math.PlacementHelper;
import deus.guilib.util.math.Tuple;
import net.minecraft.client.Minecraft;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Serves as a base class for creating custom pages. Manages elements, their layout, and rendering within the page.
 */
public abstract class Page implements IElementFather {

	protected int mouseX = 0, mouseY = 0;
	protected Router router;
	protected Minecraft mc;
	protected GuiConfig config;
	protected int width = 0, height = 0;
	protected int xSize = 0, ySize = 0;
	private final List<IElement> content = new ArrayList<>();
	public final Signal<Tuple<Integer, Integer>> onResize = new Signal<>();

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
			if (!c.getConfig().isIgnoreFatherPlacement()) {
				PlacementHelper.positionElement(c, config.getChildrenPlacement(), width, height);
			}
		}));
	}

	/**
	 * Renders the content of the page, positioning elements based on configuration.
	 */
	public void render() {
		content.forEach(child -> {
			if (!child.getConfig().isIgnoreFatherPlacement()) {
				positionChild(child);
			}
			child.draw();
		});
	}

	/**
	 * Adds elements to the page, ensuring unique SIDs if configured.
	 *
	 * @param elements Elements to be added to the page.
	 */
	public void addContent(IElement... elements) {
		if (config.isUseSIDs()) {
			Set<String> existingSids = new HashSet<>();
			for (IElement element : elements) {
				String sid = element.getSid();
				if (!existingSids.add(sid)) {
					throw new IllegalArgumentException(Error.SAME_ELEMENT_SID + " SID: " + sid + "\n");
				}
			}
		}
		Collections.addAll(content, elements);
	}

	/**
	 * Retrieves elements that belong to a specific group.
	 *
	 * @param group The group name.
	 * @return A list of elements in the specified group.
	 */
	public List<IElement> getElementsInGroup(String group) {
		return content.stream()
			.filter(c -> c.getGroup().equals(group))
			.collect(Collectors.toList());
	}

	/**
	 * Finds and returns an element with the specified SID.
	 *
	 * @param sid The SID of the element.
	 * @return The element with the specified SID, or null if not found.
	 */
	public IElement getElementWithSid(String sid) {
		return content.stream()
			.filter(c -> c.getSid().equals(sid))
			.findFirst()
			.orElse(null);
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
	 * Configures the page with a GuiConfig object.
	 *
	 * @param config The configuration for the page.
	 */
	public void config(GuiConfig config) {
		this.config = config;
	}

	/**
	 * Returns the content of the page.
	 *
	 * @return A list of elements on the page.
	 */
	@Override
	public List<IElement> getContent() {
		return content;
	}

	/**
	 * Positions a child element based on the page's configuration.
	 *
	 * @param child The child element to be positioned.
	 */
	private void positionChild(IElement child) {
		int[] basePos = new int[]{0,0};
		Placement placement = config.getChildrenPlacement();
		if (placement == Placement.CHILD_DECIDE) {
			basePos = calculateBasePosition(child.getConfig().getPlacement());
		} else {
			basePos = calculateBasePosition(placement);
		}
		int relativeX = basePos[0] + Optional.ofNullable(child.getOriginalX()).orElse(0);
		int relativeY = basePos[1] + Optional.ofNullable(child.getOriginalY()).orElse(0);

		child.setX(relativeX);
		child.setY(relativeY);
	}

	/**
	 * Calculates the base position for an element based on page configuration.
	 *
	 * @return The base position as an array [x, y].
	 */
	private int[] calculateBasePosition(Placement placement) {
		return switch (placement) {
			case CENTER -> new int[]{width / 2, height / 2};
			case TOP -> new int[]{width / 2, 0};
			case BOTTOM -> new int[]{width / 2, height};
			case LEFT -> new int[]{0, height / 2};
			case RIGHT -> new int[]{width, height / 2};
			case TOP_LEFT -> new int[]{0, 0};
			case BOTTOM_LEFT -> new int[]{0, height};
			case BOTTOM_RIGHT -> new int[]{width, height};
			case TOP_RIGHT -> new int[]{width, 0};
			case NONE -> new int[]{0,0};
			default -> new int[]{0, 0};
		};
	}

	// Getters and Setters for mouse coordinates
	public int getMouseX() { return mouseX; }
	public void setMouseX(int mouseX) { this.mouseX = mouseX; }
	public int getMouseY() { return mouseY; }
	public void setMouseY(int mouseY) { this.mouseY = mouseY; }

	/**
	 * Updates all updatable elements on the page.
	 */
	public void update() {
		content.stream()
			.filter(IUpdatable.class::isInstance)
			.map(IUpdatable.class::cast)
			.forEach(IUpdatable::update);
	}
}
