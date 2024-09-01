package deus.guilib.rendering.base;

import deus.guilib.rendering.base.interfaces.IElement;
import deus.guilib.rendering.resource.Texture;
import org.lwjgl.opengl.GL11;

public class Column extends Element {

	protected int length;
	protected int offset = 7;
	protected boolean small = false;

	public Column(int x, int y, int offset, boolean small, Element... children) {
		super(new Texture("assets/newsteps/textures/gui/Column.png", 32, 32), x, y, children);
		this.length = children.length;
		this.offset = offset;
		this.small = small;
	}

	public Column(int x, int y, int length, int offset, boolean small) {
		super(new Texture("assets/newsteps/textures/gui/Column.png", 32, 32), x, y);
		this.offset = offset;
		this.small = small;
		this.length = length;
	}

	@Override
	protected void drawIt() {
		//super.drawIt();

		if (mc==null || gui==null) {
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
				int textureY = 0;
				if (i == length -1) {
					textureY = 64;  // Final
				} else if (i > 0) {
					textureY = 32;  // Mitad
				}

				// Dibuja el segmento correspondiente
				gui.drawTexturedModalRect(x, y + (i * 32), 0, textureY, 32, texture.getHeight());
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
			int startYX = y + (getWidth() - totalWidth) / 2; // Suponiendo que 'getWidth()' es el ancho del área de dibujo

			// Posicionar y dibujar cada hijo
			int currentY = startYX - (small?3:0);
			for (int i = 0; i < numChildren; i++) {
				IElement child = children.get(i);

				// Ajustar la posición de cada hijo
				int childHeight = child.getHeight(); // Obtener el ancho del hijo
				int posY = currentY;
				int posX = 7; // Puedes ajustar esto si necesitas centrar verticalmente también

				child.setX(posX);
				child.setY(posY); // Ajusta la posición de cada hijo
				child.draw(); // Dibuja el hijo

				// Mover a la siguiente posición
				currentY += childHeight + offset;
			}
		}
	}


	@Override
	public int getWidth() {
		return length*32;
	}
}

