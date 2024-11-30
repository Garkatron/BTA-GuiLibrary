package deus.guilib.element.elements.containers;

import deus.guilib.element.Node;
import deus.guilib.element.Root;
import deus.guilib.util.math.Offset;
import deus.guilib.interfaces.element.INode;
import deus.guilib.resource.Texture;


public class Panel extends Node {

	protected int width = 3;
	protected int height = 3;

	public Panel() {
		super();
		styles.put("BackgroundImage", new Texture("assets/textures/gui/Panel.png", 32, 32));
	}

	public INode setSize(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	@Override
	protected void drawIt() {
		super.drawIt();
		for (int jx = 0; jx < width; jx += 32) {
			for (int jy = 0; jy < height; jy += 32) {
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

				drawTexturedModalRect(
					x + jx,
					y + jy,
					offset.getOffset().getFirst(),
					offset.getOffset().getSecond(),
					32,
					32
				);
			}
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
}
