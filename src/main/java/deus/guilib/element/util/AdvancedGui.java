package deus.guilib.element.util;

import net.minecraft.client.gui.Gui;

public class AdvancedGui extends Gui {
	protected void drawLineDiagonal(int minX, int minY, int maxX, int maxY, int argb) {
		int dx = Math.abs(maxX - minX);
		int dy = Math.abs(maxY - minY);

		int sx = minX < maxX ? 1 : -1;
		int sy = minY < maxY ? 1 : -1;

		int err = dx - dy;

		while (true) {
			this.drawRect(minX, minY, minX + 1, minY + 1, argb);

			if (minX == maxX && minY == maxY) {
				break;
			}

			int e2 = 2 * err;

			if (e2 > -dy) {
				err -= dy;
				minX += sx;
			}

			if (e2 < dx) {
				err += dx;
				minY += sy;
			}
		}
	}

	protected void drawLine(int x1, int y1, int x2, int y2, int argb) {
		// Diferencias en las coordenadas
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);

		// Determina la dirección del incremento en cada eje
		int sx = x1 < x2 ? 1 : -1;
		int sy = y1 < y2 ? 1 : -1;

		// Calcula el error inicial
		int err = dx - dy;

		// Bucle para dibujar la línea
		while (true) {
			// Dibujar un punto en la posición actual
			this.drawRect(x1, y1, x1 + 1, y1 + 1, argb);

			// Si hemos llegado al punto final, salir del bucle
			if (x1 == x2 && y1 == y2) {
				break;
			}

			// Almacena el error duplicado
			int e2 = 2 * err;

			// Ajusta las coordenadas según el error
			if (e2 > -dy) {
				err -= dy;
				x1 += sx;
			}

			if (e2 < dx) {
				err += dx;
				y1 += sy;
			}
		}
	}


}
