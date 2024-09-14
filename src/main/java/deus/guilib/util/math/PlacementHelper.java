package deus.guilib.util.math;

import deus.guilib.element.config.Placement;
import deus.guilib.interfaces.element.IElement;
import net.minecraft.client.render.ItemRenderer;

import java.util.Objects;

public class PlacementHelper {

	public static int[] getPlacementBasedOnFather(Placement placement, IElement father, IElement child, int width, int height) {
		// Coordenadas y tamaño del padre
		int dadX = father.getGx(); // Usar coordenadas globales
		int dadY = father.getGy();
		int dadW = father.getWidth();
		int dadH = father.getHeight();
		int childW = child.getWidth();
		int childH = child.getHeight();

		return switch (placement) {
			case CENTER -> new int[]{
				(dadX + (dadW / 2)) - (childW / 2),
				(dadY + (dadH / 2)) - (childH / 2)
			};

			case TOP_LEFT -> new int[]{
				dadX,
				dadY
			};

			case TOP_RIGHT -> new int[]{
				(dadX + dadW) - childW,
				dadY
			};

			case TOP -> new int[]{
				(dadX + (dadW / 2)) - (childW / 2),
				dadY
			};

			case BOTTOM -> new int[]{
				(dadX + (dadW / 2)) - (childW / 2),
				(dadY + dadH) - childH
			};

			case BOTTOM_RIGHT -> new int[]{
				(dadX + dadW) - childW,
				(dadY + dadH) - childH
			};

			case BOTTOM_LEFT -> new int[]{
				dadX,
				(dadY + dadH) - childH
			};

			case LEFT -> new int[]{
				dadX,
				(dadY + (dadH / 2)) - (childH / 2)
			};

			case RIGHT -> new int[]{
				(dadX + dadW) - childW,
				(dadY + (dadH / 2)) - (childH / 2)
			};

			case NONE -> new int[]{0, 0}; // No change in position

			default -> new int[]{0, 0}; // Default case
		};
	}

	public static int[] getPlacementBasedOnCanvas(Placement placement, int width, int height) {
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
			case NONE -> new int[]{0, 0}; // No change in position
			default -> new int[]{0, 0}; // Default case
		};
	}

	public static void positionElement(IElement child, Placement childrenPlacement, int width, int height) {
		// Only update position if the placement is not NONE
		if (childrenPlacement != Placement.NONE) {
			int[] basePos = getPlacementBasedOnCanvas(childrenPlacement, width, height);
			int relativeX = basePos[0];
			int relativeY = basePos[1];

			// Position the element relatively to the canvas
			child.setX(relativeX);
			child.setY(relativeY);
		}
	}

	public static void positionChild(IElement child, IElement father, int width, int height) {
		// Only update position if the placement is not NONE
		if (father.getConfig().getChildrenPlacement() != Placement.NONE) {
			int[] basePos = getPlacementBasedOnFather(child.getConfig().getPlacement(), father, child, width, height);
			int relativeX = basePos[0];
			int relativeY = basePos[1];

			// Position the child element relatively to the father
			child.setX(relativeX);
			child.setY(relativeY);
		}
	}
}
