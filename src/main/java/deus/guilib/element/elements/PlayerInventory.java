package deus.guilib.element.elements;

import deus.guilib.element.Element;
import deus.guilib.interfaces.element.IElement;
import deus.guilib.interfaces.element.IUpdatable;
import deus.guilib.resource.Texture;
import org.lwjgl.opengl.GL11;

public class PlayerInventory extends Element implements IUpdatable {
	private int width;  // Width of the GUI
	private int height; // Height of the GUI
	private int xSize;  // Width of the inventory
	private int ySize;  // Height of the inventory
	private int finalY;
	private int finalX;
	private int invSize = 40;

	public PlayerInventory(int invSize) {
		super(new Texture("assets/textures/gui/Slot.png", 176, 89));
		this.invSize = invSize;
		for (int i = 0; i<36; i++) {
			addChildren(
				new Slot()
					.setSid("INVSLOT:" + i)
			);
		}
	}

	public PlayerInventory setxSize(int xSize) {
		this.xSize = xSize;
		return this;

	}

	public PlayerInventory setySize(int ySize) {
		this.ySize = ySize;
		return this;

	}

	public PlayerInventory setSize(int xSize, int ySize) {
		this.ySize = ySize;
		this.xSize = xSize;
		return this;
	}


	@Override
	protected void drawIt() {
		// Nothing here
	}

	@Override
	protected void drawChild() {
		if (mc == null) {
			System.out.println("Error on drawIt, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}

		GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glDisable(GL11.GL_BLEND);

		// Dimensiones de la pantalla, actualizadas dinámicamente
		this.width = mc.resolution.scaledWidth;
		this.height = mc.resolution.scaledHeight;

		// Coordenadas centrales para la GUI
		int centerX = (this.width - texture.getWidth()) / 2;
		int centerY = (this.height - texture.getHeight()) / 2;

		// Ajustar el offset vertical para subir todo un poco
		int offsetY = -10; // Cambia este valor para mover más arriba o abajo

		if (!children.isEmpty()) {
			int slotWidth = 18;  // Ancho de un slot estándar
			int slotHeight = 18; // Alto de un slot estándar
			int rows = 3;        // Número de filas
			int cols = 9;        // Número de columnas
			int startY = 103;    // Y inicial, similar a la posición de inventario
			int startX = 8;      // X inicial, similar a la posición de inventario

			int extraOffsetY = ((invSize / 9) - 4) * slotHeight;

			int slotIndex = 0;   // Contador para iterar sobre los hijos

			// Dibuja los hijos en la cuadrícula de inventario
			for (int row = 0; row < rows; ++row) {
				for (int col = 0; col < cols; ++col) {
					if (slotIndex < children.size()) {
						IElement child = children.get(slotIndex);
						int posX = centerX + startX + col * slotWidth;
						int posY = centerY + startY + row * slotHeight + extraOffsetY + offsetY; // Aplicar offsetY

						child.setPosition(posX, posY);
						child.draw();

						slotIndex++;
					}
				}
			}

			// Dibuja la fila inferior (barra de acción)
			for (int col = 0; col < cols; ++col) {
				if (slotIndex < children.size()) {
					IElement child = children.get(slotIndex);
					int posX = centerX + startX + col * slotWidth;
					int posY = centerY + 161 + extraOffsetY + offsetY; // Aplicar offsetY

					child.setPosition(posX, posY);

					child.draw();

					slotIndex++;
				}
			}
		}
	}


	@Override
	public void update() {
		setSize(mc.resolution.scaledWidth, mc.resolution.scaledHeight);
		for (IElement element : children) {

			if (element instanceof Slot) {
				((Slot) element).update();
			}
		}
	}
}
