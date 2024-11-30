package deus.guilib.element.elements.containers;

import deus.guilib.element.GNode;
import deus.guilib.interfaces.element.INode;
import deus.guilib.resource.Texture;

public class Row extends GNode {

	protected int length;
	protected int offset = 7;
	protected boolean small = false;

	public Row() {
		super();
		styles.put("BackgroundImage",new Texture("assets/textures/gui/Row.png",32,32));
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
		super.drawIt();

		/*
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				if (i == length - 1) {
					texture.setFrameX(2);
				} else if (i > 0) {
					texture.setFrameX(1);
				} else {
					texture.setFrameX(0);
				}
				texture.draw(mc, gx + (i * 32), gy);
			}
		}
		 */
	}

	@Override
	protected void drawChild() {
		// Calcular el ancho total requerido para todos los hijos, teniendo en cuenta el tamaño variable
		int numChildren = children.size();

		// Si hay hijos, calcular el ancho total requerido
		if (numChildren > 0) {
			// Calcular el ancho total requerido para todos los hijos
			int totalWidth = 0;
			for (INode child : children) {
				totalWidth += child.getWidth(); // Sumar el ancho de cada hijo
			}
			// Añadir el espacio entre los hijos
			totalWidth += (numChildren - 1) * offset;

			// Calcular la posición inicial para centrar los hijos
			int startX = x + (getWidth() - totalWidth) / 2; // Suponiendo que 'getWidth()' es el ancho del área de dibujo

			// Posicionar y dibujar cada hijo
			int currentX = startX;
			for (int i = 0; i < numChildren; i++) {
				INode child = children.get(i);

				// Ajustar la posición de cada hijo
				int childWidth = child.getWidth(); // Obtener el ancho del hijo
				int posX = currentX;
				int posY = 7; // Puedes ajustar esto si necesitas centrar verticalmente también

				child.setPosition(posX, posY);
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

