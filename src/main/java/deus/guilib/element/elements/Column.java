package deus.guilib.element.elements;

import deus.guilib.element.Element;
import deus.guilib.interfaces.element.IElement;
import deus.guilib.resource.Texture;
import org.lwjgl.opengl.GL11;

public class Column extends Element {

	protected int length;
	protected int offset = 7;
	protected boolean small = false;

	public Column() {
		super(new Texture("assets/textures/gui/Column.png", 32, 32));
	}

	// Métodos encadenados para configurar las variables
	public Column setLength(int length) {
		this.length = length;
		return this;
	}

	public Column setOffset(int offset) {
		this.offset = offset;
		return this;
	}

	public Column setSmall(boolean small) {
		this.small = small;
		return this;
	}

	@Override
	protected void drawIt() {
		if (mc == null) {
			System.out.println("Error on drawIt, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}
		GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(texture.getPath()));
		GL11.glDisable(GL11.GL_BLEND);

		// Dibuja los segmentos de la fila
		if (length == 0) {
			//gui.drawTexturedModalRect(x + 32, y, 64, texture.getOffsetY(), texture.getWidth(), texture.getHeight());
		} else {
			for (int i = 0; i < length; i++) {
				int textureY = 0;
				if (i == length - 1) {
					textureY = 64;  // Final
				} else if (i > 0) {
					textureY = 32;  // Mitad
				}

				// Dibuja el segmento correspondiente
				drawTexturedModalRect(x, y + (i * 32), 0, textureY, 32, texture.getHeight());
			}
		}
	}

	@Override
	protected void drawChild() {
		int numChildren = children.size();

		if (numChildren > 0) {
			int totalWidth = 0;
			for (IElement child : children) {
				totalWidth += child.getWidth(); // Sumar el ancho de cada hijo
			}
			totalWidth += (numChildren - 1) * offset;

			int startYX = y + (getWidth() - totalWidth) / 2;

			int currentY = startYX - (small ? 3 : 0);
			for (int i = 0; i < numChildren; i++) {
				IElement child = children.get(i);

				int childHeight = child.getHeight();
				int posY = currentY;
				int posX = 7;

				child.setPosition(posX, posY);
				child.draw();

				currentY += childHeight + offset;
			}
		}
	}

	@Override
	public int getWidth() {
		return length * 32;
	}
}
