package deus.guilib.element.elements.containers;

import deus.guilib.element.Node;
import deus.guilib.element.stylesystem.StyleParser;
import deus.guilib.resource.Texture;
import deus.guilib.util.math.Offset;
import deus.guilib.interfaces.element.INode;
import org.lwjgl.opengl.GL11;

import java.util.Map;


public class Panel extends Node {

	private int lengthY = 3;
	private int lengthX = 3;

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
	protected void drawBackgroundImage() {
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");

			int tileSize = 32;

			if (styles.containsKey("tileSize")) {
				tileSize = StyleParser.parsePixels((String) styles.get("tileSize"));
			}

			int gridWidth = lengthX * tileSize;  // 3 * 32
			int gridHeight = lengthY * tileSize; // 3 * 32

			width = gridWidth;
			height = gridHeight;

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
						32,
						32,
						offset.getOffset().getFirst(),
						offset.getOffset().getSecond()
					);
				}
			}
		}
	}


	protected void updateLengthStyle() {
		if (styles.containsKey("lengthY")) {
			lengthY = (int) styles.get("lengthY");
		}
		if (styles.containsKey("lengthX")) {
			lengthX = (int) styles.get("lengthX");
		}
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	public int getLengthY() {
		return lengthY;
	}

	public void setLengthY(int lengthY) {
		this.lengthY = lengthY;
	}

	public int getLengthX() {
		return lengthX;
	}

	public void setLengthX(int lengthX) {
		this.lengthX = lengthX;
	}
}
