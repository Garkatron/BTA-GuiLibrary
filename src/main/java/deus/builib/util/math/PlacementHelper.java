package deus.builib.util.math;

import deus.builib.nodes.config.Placement;
import deus.builib.interfaces.nodes.INode;
import deus.builib.GuiLib;

/**
 * Helper class to handle placement calculations for elements.
 */
public class PlacementHelper {

	private static final int[] DEFAULT_POSITION = {0, 0};

	/**
	 * Gets the position of a child element based on its father's placement and dimensions.
	 *
	 * @param placement  The placement configuration for the child.
	 * @param father     The parent element.
	 * @param child      The child element.
	 * @return The computed position as an array of [x, y].
	 */
	public static int[] getPlacementBasedOnFather(Placement placement, INode father, INode child) {
		int fatherX = father.getGx();
		int fatherY = father.getGy();
		int fatherWidth = father.getWidth();
		int fatherHeight = father.getHeight();

		int childWidth = child.getWidth();
		int childHeight = child.getHeight();

		switch (placement) {
			case CENTER:
				return calculateCenteredPosition(fatherX, fatherY, fatherWidth, fatherHeight, childWidth, childHeight);
			case TOP_LEFT:
				return new int[]{fatherX, fatherY};
			case TOP_RIGHT:
				return new int[]{fatherX + fatherWidth - childWidth, fatherY};
			case TOP:
				return new int[]{fatherX + (fatherWidth - childWidth) / 2, fatherY};
			case BOTTOM:
				return new int[]{fatherX + (fatherWidth - childWidth) / 2, fatherY + fatherHeight - childHeight};
			case BOTTOM_RIGHT:
				return new int[]{fatherX + fatherWidth - childWidth, fatherY + fatherHeight - childHeight};
			case BOTTOM_LEFT:
				return new int[]{fatherX, fatherY + fatherHeight - childHeight};
			case LEFT:
				return new int[]{fatherX, fatherY + (fatherHeight - childHeight) / 2};
			case RIGHT:
				return new int[]{fatherX + fatherWidth - childWidth, fatherY + (fatherHeight - childHeight) / 2};
			case CHILD_DECIDE:
				return new int[]{child.getGx(), child.getGy()};
			default:
				GuiLib.LOGGER.warn("Invalid placement, returning default position.");
				return DEFAULT_POSITION;
		}
	}

	/**
	 * Calculates the centered position based on parent and child dimensions.
	 *
	 * @param parentX       The x coordinate of the parent.
	 * @param parentY       The y coordinate of the parent.
	 * @param parentWidth   The width of the parent.
	 * @param parentHeight  The height of the parent.
	 * @param childWidth    The width of the child.
	 * @param childHeight   The height of the child.
	 * @return The centered position as an array of [x, y].
	 */
	private static int[] calculateCenteredPosition(int parentX, int parentY, int parentWidth, int parentHeight, int childWidth, int childHeight) {
		return new int[]{
			parentX + (parentWidth - childWidth) / 2,
			parentY + (parentHeight - childHeight) / 2
		};
	}

	/**
	 * Gets the position of an element based on canvas placement and dimensions.
	 *
	 * @param element   The element to be positioned.
	 * @param placement The placement configuration for the element.
	 * @param width     The width of the canvas.
	 * @param height    The height of the canvas.
	 * @return The computed position as an array of [x, y].
	 */
	public static int[] getPlacementBasedOnCanvas(INode element, Placement placement, int width, int height) {
		int elementWidth = element.getWidth();
		int elementHeight = element.getHeight();

		return switch (placement) {
			case CENTER -> new int[]{width / 2 - elementWidth / 2, height / 2 - elementHeight / 2};
			case TOP -> new int[]{width / 2 - elementWidth / 2, 0};
			case BOTTOM -> new int[]{width / 2 - elementWidth / 2, height - elementHeight};
			case LEFT -> new int[]{0, height / 2 - elementHeight / 2};
			case RIGHT -> new int[]{width - elementWidth, height / 2 - elementHeight / 2};
			case TOP_LEFT -> new int[]{0, 0};
			case BOTTOM_LEFT -> new int[]{0, height - elementHeight};
			case BOTTOM_RIGHT -> new int[]{width - elementWidth, height - elementHeight};
			case TOP_RIGHT -> new int[]{width - elementWidth, 0};
			case NONE -> new int[]{element.getGx(), element.getGy()};
			case CHILD_DECIDE -> DEFAULT_POSITION;
			default -> {
				GuiLib.LOGGER.warn("{} Invalid placement {} on canvas, returning default position.", element.getClass().getSimpleName(), placement);
				yield DEFAULT_POSITION;
			}
		};
	}

	/**
	 * Positions the child element based on the external placement relative to the canvas.
	 *
	 * @param child             The child element to be positioned.
	 * @param childrenPlacement The external placement configuration for the child.
	 * @param width             The width of the canvas.
	 * @param height            The height of the canvas.
	 */
	public static void positionElement(INode child, Placement childrenPlacement, int width, int height) {
		int[] basePos = getPlacementBasedOnCanvas(child, childrenPlacement, width, height);
		child.setGlobalPosition(basePos[0], basePos[1]);
	}

	/**
	 * Positions the child element based on its placement relative to its father.
	 *
	 * @param child The child element to be positioned.
	 * @param parent The parent element.
	 */
	public static void positionChild(INode child, INode parent) {
		Placement parentPlacement = parent.getChildrenPlacement();
		if (parentPlacement != Placement.NONE) {
			int[] basePos = getPlacementBasedOnFather(parentPlacement, parent, child);
			child.setGlobalPosition(basePos[0], basePos[1]);
		}
	}

	/**
	 * Positions the child element based on its self placement.
	 *
	 * @param child The child element to be positioned.
	 * @param parent The parent element.
	 */
	public static void positionItSelf(INode child, INode parent) {
		Placement selfPlacement = child.getSelfPlacement();
		if (selfPlacement != Placement.NONE) {
			int[] basePos = getPlacementBasedOnFather(selfPlacement, parent, child);
			child.setGlobalPosition(basePos[0], basePos[1]);
		}
	}

	/**
	 * Calculates relative size based on a percentage.
	 *
	 * @param percent The percentage of the size.
	 * @param width   The width of the element.
	 * @param height  The height of the element.
	 * @return A Tuple representing the relative size as width and height.
	 */
	public static Tuple<Integer, Integer> calcRelativeSize(int percent, int width, int height) {
		if (percent <= 0 || percent > 100) {
			throw new IllegalArgumentException("Percent must be between 1 and 100: " + percent + "%");
		}
		int relativeWidth = (width * percent) / 100;
		int relativeHeight = (height * percent) / 100;
		return new Tuple<>(relativeWidth, relativeHeight);
	}
}
