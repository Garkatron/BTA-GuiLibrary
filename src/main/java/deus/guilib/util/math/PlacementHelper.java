package deus.guilib.util.math;

import deus.guilib.element.config.Placement;
import deus.guilib.interfaces.element.IElement;

import java.util.Optional;

public class PlacementHelper {
	public static int[] getPlacementBasedOnFather(Placement placement, IElement father, IElement child, int width, int height) {

		// Coordenadas y tamaño del padre
		int dadX = father.getX();
		int dadY = father.getY();
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
				dadY + childH
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

			case NONE -> new int[]{
				0,0
			};

			default -> new int[]{0, 0}; // Caso por defecto
		};


			//new int[]{0, 0};
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
			case NONE -> new int[]{0,0};
			default -> new int[]{0, 0};
		};
	}

	public static void positionElement(IElement child, Placement childrenPlacement, int width, int height) {
		int[] basePos = new int[]{0,0};
		if (childrenPlacement == Placement.CHILD_DECIDE) {
			basePos = getPlacementBasedOnCanvas(child.getConfig().getPlacement() , width, height);
		} else {
			basePos = getPlacementBasedOnCanvas(childrenPlacement, width, height);
		}
		int relativeX = basePos[0] + Optional.ofNullable(child.getOriginalX()).orElse(0);
		int relativeY = basePos[1] + Optional.ofNullable(child.getOriginalY()).orElse(0);

		child.setX(relativeX);
		child.setY(relativeY);
	}

	public static void positionChild(IElement child, IElement father, int width, int height) {
		int[] basePos = new int[]{0,0};


		if (father.getConfig().getChildrenPlacement() == Placement.CHILD_DECIDE) {
			basePos = getPlacementBasedOnFather(child.getConfig().getPlacement(), father, child , width, height);
		} else {
			basePos = getPlacementBasedOnFather(father.getConfig().getChildrenPlacement(), father, child, width, height);
		}
		int relativeX = basePos[0];
		int relativeY = basePos[1];


		child.setX(relativeX);
		child.setY(relativeY);

	}

}
