package deus.guilib.element.elements.containers;

import deus.guilib.element.Node;
import deus.guilib.resource.Texture;
import deus.guilib.util.math.Offset;
import deus.guilib.interfaces.element.INode;
import org.lwjgl.opengl.GL11;

import java.util.Map;


public class Panel extends Node {

	private int lengthY = 0;
	private int lengthX = 0;

	public Panel() {
		super();
	}

	public Panel(Map<String, String> attributes) {
		super(attributes);
	}

	public INode setSize(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	@Override
	public void draw() {
		super.draw();
		updateLengthStyle();
	}

	@Override
	protected void drawBackgroundImage() {
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");
			for (int jx = 0; jx < lengthX; jx += 32) {
				for (int jy = 0; jy < lengthY; jy += 32) {
					Offset offset;

					// Determinar la sección que se está dibujando
					if (jx == 0 && jy == 0) {
						offset = Offset.CORNER_UP_LEFT; // Esquina superior izquierda
					} else if (jx == 0 && jy == height - 32) {
						offset = Offset.CORNER_DOWN_LEFT; // Esquina inferior izquierda
					} else if (jx == width - 32 && jy == 0) {
						offset = Offset.CORNER_UP_RIGHT; // Esquina superior derecha
					} else if (jx == width - 32 && jy == height - 32) {
						offset = Offset.CORNER_DOWN_RIGHT; // Esquina inferior derecha
					} else if (jx == 0) {
						offset = Offset.LEFT; // Borde izquierdo
					} else if (jx == width - 32) {
						offset = Offset.RIGHT; // Borde derecho
					} else if (jy == 0) {
						offset = Offset.UP; // Borde superior
					} else if (jy == height - 32) {
						offset = Offset.DOWN; // Borde inferior
					} else {
						System.out.println(jx + "/" + jy);
						offset = Offset.CENTER; // Centro
					}

					t.drawWithFrame(
						mc,
						x + jx,
						y + jy,
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
