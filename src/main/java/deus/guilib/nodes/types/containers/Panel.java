package deus.guilib.nodes.types.containers;

import deus.guilib.nodes.Node;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.resource.Texture;
import deus.guilib.util.math.Offset;

import java.util.Map;


public class Panel extends Node {

	private int lengthY = 3;
	private int lengthX = 3;
	private int tileSize = 32;

	public Panel() {
		super();
	}

	public Panel(Map<String, String> attributes) {
		super(attributes);
	}

	@Override
	protected void drawIt() {
		super.drawIt();
		updateLengthStyle();
	}

	@Override
	protected void updateIt() {
		super.updateIt();
		if (styles.containsKey("tileSize")) {
			tileSize = StyleParser.parsePixels((String) styles.get("tileSize"));
		}
	}

	@Override
	protected void drawBackgroundImage() {
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");




			int gridWidth = lengthX * tileSize;  // 3 * 32
			int gridHeight = lengthY * tileSize; // 3 * 32

			for (int jx = 0; jx < gridWidth; jx += tileSize) {
				for (int jy = 0; jy < gridHeight; jy += tileSize) {
					Offset offset;

					// Determinar la posición del bloque
					boolean isLeft = (jx == 0);
					boolean isRight = (jx == gridWidth - tileSize);
					boolean isUp = (jy == 0);
					boolean isDown = (jy == gridHeight - tileSize);

					if (isLeft && isUp) offset = Offset.CORNER_UP_LEFT;
					else if (isLeft && isDown) offset = Offset.CORNER_DOWN_LEFT;
					else if (isRight && isUp) offset = Offset.CORNER_UP_RIGHT;
					else if (isRight && isDown) offset = Offset.CORNER_DOWN_RIGHT;
					else if (isLeft) offset = Offset.LEFT;
					else if (isRight) offset = Offset.RIGHT;
					else if (isUp) offset = Offset.UP;
					else if (isDown) offset = Offset.DOWN;
					else offset = Offset.CENTER;

					t.drawWithFrame(
						mc,
						gx + jx,
						gy + jy,
						tileSize,
						tileSize,
						offset.getOffset().getFirst()*tileSize,
						offset.getOffset().getSecond()*tileSize
					);
				}
			}
		}
	}


	protected void updateLengthStyle() {
		if (styles.containsKey("panelLengthY")) {
			lengthY = (int) styles.get("panelLengthY");
		}
		if (styles.containsKey("panelLengthX")) {
			lengthX = (int) styles.get("panelLengthX");
		}
	}

	@Override
	public int getHeight() {
		return lengthY*tileSize;
	}

	@Override
	public int getWidth() {
		return lengthX*tileSize;
	}

}
