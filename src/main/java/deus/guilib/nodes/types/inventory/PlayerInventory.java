package deus.guilib.nodes.types.inventory;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.interfaces.nodes.IUpdatable;

import java.util.Map;

public class PlayerInventory extends Node implements IUpdatable {

	final int maxSlotsPerRow = 9;
	final int maxRows = 4;

	public PlayerInventory() {
		super();
	}

	public PlayerInventory(Map<String, String> attributes) {
		super(attributes);

		for (int i = 0; i < 36; i++) {
			addChildren(
				new Slot()
					.setSid("INV_SLOT:" + i)
			);
		}
	}

	@Override
	protected void drawChild() {
		if (mc == null) {
			System.out.println("Error in drawChild: [Minecraft dependency] or [Gui dependency] is null.");
			return;
		}

		int currentX = 0;
		int currentY = 0;

		int currentRow = 0;
		int slotsInCurrentRow = 0;

		for (INode child : children) {
			if (slotsInCurrentRow >= maxSlotsPerRow) {
				if (currentRow < maxRows - 1) {
					currentY += child.getHeight();

					if (currentRow == maxRows - 2) {
						currentY += 4;
					}

					currentRow++;
					slotsInCurrentRow = 0;
					currentX = 0;
				} else {
					break;
				}
			}

			if (slotsInCurrentRow > 0) {
				currentX += child.getWidth();
			}

			child.setGlobalPosition(gx + currentX + 4, gy + currentY + 2);
			child.draw();

			slotsInCurrentRow++;
		}
	}

	@Override
	public int getHeight() {
		return children.get(0).getHeight()*4;
	}
	@Override
	public int getWidth() {
		return children.get(0).getWidth()*9;
	}

}



/*
	@Override
	public void update() {
		//setSize(mc.resolution.scaledWidth, mc.resolution.scaledHeight);
		for (INode element : children) {

			if (element instanceof Slot) {
				((Slot) element).update();
			}
		}
	}
}

*
		GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glDisable(GL11.GL_BLEND);

		// Dimensiones de la pantalla, actualizadas dinámicamente
		this.width = mc.resolution.scaledWidth;
		this.height = mc.resolution.scaledHeight;

		// Coordenadas centrales para la GUI
		int centerX = (this.width - getWidth()) / 2;
		int centerY = (this.height - getHeight()) / 2;

		// Ajustar el offset vertical para subir todo un poco
		int offsetY = -10; // Cambia este valor para mover más arriba o abajo

		if (!children.isEmpty()) {
			int slotWidth = 18;  // Ancho de un slot estándar
			int slotHeight = 18; // Alto de un slot estándar
			int rows = 3;        // Número de filas para el inventario principal (no incluye la hotbar)
			int cols = 9;        // Número de columnas
			int startY = 103;    // Y inicial para las filas principales del inventario
			int startX = 8;      // X inicial

			// Este extraOffsetY ajusta para invSize dinámico, si hay más de 36 slots
			int extraOffsetY = ((invSize / 9) - 4) * slotHeight;

			int slotIndex = 0;   // Contador para iterar sobre los hijos

			// **Dibuja la barra de acción (hotbar)**
			int hotbarStartY = centerY + 161 + extraOffsetY + offsetY; // Posición ajustada para la hotbar
			for (int col = 0; col < cols; ++col) {
				if (slotIndex < children.size()) {
					INode child = children.get(slotIndex);
					int posX = centerX + startX + col * slotWidth;
					int posY = hotbarStartY; // Hotbar en la parte inferior

					child.setGlobalPosition(posX, posY);
					child.draw();

					slotIndex++;
				}
			}

			// **Dibuja el inventario principal (3 filas)**
			for (int row = 0; row < rows; ++row) {
				for (int col = 0; col < cols; ++col) {
					if (slotIndex < children.size()) {
						INode child = children.get(slotIndex);
						int posX = centerX + startX + col * slotWidth;
						int posY = centerY + startY + row * slotHeight + extraOffsetY + offsetY;

						child.setGlobalPosition(posX, posY);
						child.draw();

						slotIndex++;
					}
				}
			}


		}
*
*/
