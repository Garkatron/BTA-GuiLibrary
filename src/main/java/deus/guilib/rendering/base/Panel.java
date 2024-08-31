package deus.guilib.rendering.base;

import deus.guilib.math.Offset;
import deus.guilib.rendering.base.organization.ElementConfig;
import deus.guilib.rendering.base.organization.Placement;
import deus.guilib.rendering.resource.Texture;


public class Panel extends Element {

	protected int width;
	protected int height;

	public Panel(int x, int y, int width, int height, ElementConfig config, Element... children) {
		super(new Texture("assets/newsteps/textures/gui/Panel.png", 32, 32), x, y, config, children);

	}

	public Panel(int x, int y , int width, int height) {
		this(x, y, width, height, new ElementConfig(false, Placement.NONE));
	}

	public Panel(int x, int y, int width, int height, Placement placement) {
		this(x, y, width, height, new ElementConfig(false, Placement.NONE));
	}

	public Panel(int x, int y, int width, int height, Element... children) {
		this(x,y,width, height, new ElementConfig(false, Placement.NONE), children);
	}

	public Panel(int width, int height, Element... children) {
		this(0,0,width, height,children);
	}

	public Panel() {
		this(0,0,2,2);
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

				gui.drawTexturedModalRect(
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
