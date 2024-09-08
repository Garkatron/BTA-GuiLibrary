package deus.guilib.element.elements;

import deus.guilib.element.Element;
import deus.guilib.interfaces.element.IElement;
import deus.guilib.resource.Texture;
import org.lwjgl.opengl.GL11;

public class Row extends Element {

	protected int length;
	protected int offset = 7;
	protected boolean small = false;

	public Row() {
		super(new Texture("assets/textures/gui/Row.png",32,32));
	}

	public Row setLength(int length) {
		this.length = length;
		return this;
	}

	public Row setOffset(int offset) {
		this.offset = offset;
		return this;
	}

	public Row setSmall(boolean small) {
		this.small = small;
		return this;
	}

	@Override
	protected void drawIt() {
		//super.drawIt();

		if (mc==null) {
			System.out.println("Error on drawIt, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}
		GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(texture.getPath()));
		GL11.glDisable(GL11.GL_BLEND);
		//gui.drawTexturedModalRect(x, y, texture.getOffsetX(), texture.getOffsetY(), texture.getWidth(), texture.getHeight());

		// Dibuja los segmentos de la fila
		if (length == 0) {
			//gui.drawTexturedModalRect(x + 32, y, 64, texture.getOffsetY(), texture.getWidth(), texture.getHeight());
		} else {
			for (int i = 0; i < length; i++) {
				int textureX = 0;
				if (i == length -1) {
					textureX = 64;  // Final
				} else if (i > 0) {
					textureX = 32;  // Mitad
				}

				// Dibuja el segmento correspondiente
				drawTexturedModalRect(x + (i * 32), y, textureX, small?32:texture.getOffsetY(), 32, texture.getHeight());
			}
		}


	}

	@Override
	protected void drawChild() {
		// Calcular el ancho total requerido para todos los hijos, teniendo en cuenta el tamaño variable
		int numChildren = children.size();

		// Si hay hijos, calcular el ancho total requerido
		if (numChildren > 0) {
			// Calcular el ancho total requerido para todos los hijos
			int totalWidth = 0;
			for (IElement child : children) {
				totalWidth += child.getWidth(); // Sumar el ancho de cada hijo
			}
			// Añadir el espacio entre los hijos
			totalWidth += (numChildren - 1) * offset;

			// Calcular la posición inicial para centrar los hijos
			int startX = x + (getWidth() - totalWidth) / 2; // Suponiendo que 'getWidth()' es el ancho del área de dibujo

			// Posicionar y dibujar cada hijo
			int currentX = startX;
			for (int i = 0; i < numChildren; i++) {
				IElement child = children.get(i);

				// Ajustar la posición de cada hijo
				int childWidth = child.getWidth(); // Obtener el ancho del hijo
				int posX = currentX;
				int posY = 7; // Puedes ajustar esto si necesitas centrar verticalmente también

				child.setX(posX);
				child.setY(posY); // Ajusta la posición de cada hijo
				child.draw(); // Dibuja el hijo

				// Mover a la siguiente posición
				currentX += childWidth + offset;
			}
		}
	}


	@Override
	public int getWidth() {
		return length*32;
	}
}

