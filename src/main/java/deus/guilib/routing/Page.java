package deus.guilib.routing;

import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.interfaces.element.IElement;
import deus.guilib.element.interfaces.element.IUpdatable;
import deus.guilib.error.Error;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serves as a base class for creating custom pages. Manages elements, their layout, and rendering within the page.
 */
public abstract class Page {

	protected int mouseX = 0;
	protected int mouseY = 0;
	protected Router router;
	protected Minecraft mc;
	protected GuiConfig config;
	protected int width, height = 0;
	protected int xSize, ySize = 0;
	private List<IElement> content = new ArrayList<>();

	/**
	 * Constructs a Page with the specified router.
	 *
	 * @param router The router used for navigation.
	 */
	public Page(Router router) {
		this.router = router;
		mc = Minecraft.getMinecraft(this);
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

				if (existingSids.contains(sid)) {
					throw new IllegalArgumentException(Error.SAME_ELEMENT_SID + " SID: " + sid + "\n");
				}

				existingSids.add(sid);
			}
		}

		content.addAll(List.of(elements));
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

	/**
	 * Sets the X size of the page.
	 *
	 * @param xSize The X size of the page.
	 */
	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	/**
	 * Returns the X size of the page.
	 *
	 * @return The X size of the page.
	 */
	public int getxSize() {
		return xSize;
	}

	/**
	 * Sets the dimensions and size of the page.
	 *
	 * @param xSize  The X size of the page.
	 * @param ySize  The Y size of the page.
	 * @param width  The width of the page.
	 * @param height The height of the page.
	 */
	public void setXYWH(int xSize, int ySize, int width, int height) {
		this.ySize = ySize;
		this.xSize = xSize;
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets the Y size of the page.
	 *
	 * @param ySize The Y size of the page.
	 */
	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	/**
	 * Sets the height of the page.
	 *
	 * @param height The height of the page.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Sets the width of the page.
	 *
	 * @param width The width of the page.
	 */
	public void setWidth(int width) {
		this.width = width;
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
	public List<IElement> getContent() {
		return content;
	}

	/**
	 * Renders the content of the page, positioning elements based on configuration.
	 */
	public void render() {
		if (content.isEmpty()) return;

		int[] accumulatedPosition = {0, 0};

		for (IElement child : content) {
			if (shouldPositionChild(child)) {
				positionChild(child, accumulatedPosition);
			}
			child.draw();
		}
	}

	private boolean shouldPositionChild(IElement child) {
		return !child.getConfig().isIgnoreFatherPlacement() && !child.isPositioned();
	}

	private void positionChild(IElement child, int[] accumulatedPosition) {
		int[] basePos = calculateBasePosition(child);

		int relativeX = basePos[0] + accumulatedPosition[0] + child.getX();
		int relativeY = basePos[1] + accumulatedPosition[1] + child.getY();

		child.setPositioned(true);
		child.setX(relativeX);
		child.setY(relativeY);

		accumulatePosition(accumulatedPosition, child);
	}

	private void accumulatePosition(int[] accumulatedPosition, IElement child) {
		switch (config.getPlacement()) {
			case TOP:
				accumulatedPosition[1] += child.getHeight();
				break;
			case BOTTOM:
				accumulatedPosition[1] -= child.getHeight();
				break;
			default:
				accumulatedPosition[0] += child.getWidth();
				break;
		}
	}

	private int[] calculateBasePosition(IElement child) {
		int childWidth = child.getWidth();
		int childHeight = child.getHeight();

		return switch (config.getPlacement()) {
			case CENTER -> new int[]{(width - childWidth) / 2, (height - childHeight) / 2};
			case TOP -> new int[]{(width - childWidth) / 2, 0};
			case BOTTOM -> new int[]{(width - childWidth) / 2, height - childHeight};
			case LEFT -> new int[]{0, (height - childHeight) / 2};
			case RIGHT -> new int[]{width - childWidth, (height - childHeight) / 2};
			case TOP_LEFT -> new int[]{0, 0};
			case BOTTOM_LEFT -> new int[]{0, height - childHeight};
			case BOTTOM_RIGHT -> new int[]{width - childWidth, height - childHeight};
			case TOP_RIGHT -> new int[]{width - childWidth, 0};
			default -> new int[]{0, 0};
		};
	}

	/**
	 * Returns the current mouse X coordinate.
	 *
	 * @return The mouse X coordinate.
	 */
	public int getMouseX() {
		return mouseX;
	}

	/**
	 * Sets the mouse X coordinate.
	 *
	 * @param mouseX The mouse X coordinate.
	 */
	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	/**
	 * Returns the current mouse Y coordinate.
	 *
	 * @return The mouse Y coordinate.
	 */
	public int getMouseY() {
		return mouseY;
	}

	/**
	 * Sets the mouse Y coordinate.
	 *
	 * @param mouseY The mouse Y coordinate.
	 */
	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

	/**
	 * Updates all updatable elements on the page.
	 */
	public void update() {
		for (IElement element : content) {
			if (element instanceof IUpdatable) {
				((IUpdatable) element).update();
			}
		}
	}
}
