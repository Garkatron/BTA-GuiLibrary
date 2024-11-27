package deus.guilib.element.util;

import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class AdvancedGui extends Gui {
	protected void drawLineDiagonal(int minX, int minY, int maxX, int maxY, int argb) {
		int dx = Math.abs(maxX - minX);
		int dy = Math.abs(maxY - minY);

		int sx = minX < maxX ? 1 : -1;
		int sy = minY < maxY ? 1 : -1;

		int err = dx - dy;

		while (true) {
			this.drawRectWithoutTessellator(minX, minY, minX + 1, minY + 1, argb);

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


	public void drawTexturedModalRectWithoutTessellator(int x, int y, int u, int v, int width, int height) {
		float uScale = 0.00390625F; // 1/256
		float vScale = 0.00390625F; // 1/256

		GL11.glEnable(GL11.GL_TEXTURE_2D); // Habilitar texturas
		GL11.glBegin(GL11.GL_QUADS); // Comenzar a dibujar un cuadrado

		// Esquina inferior izquierda
		GL11.glTexCoord2f((u + 0) * uScale, (v + height) * vScale);
		GL11.glVertex2f(x + 0, y + height);

		// Esquina inferior derecha
		GL11.glTexCoord2f((u + width) * uScale, (v + height) * vScale);
		GL11.glVertex2f(x + width, y + height);

		// Esquina superior derecha
		GL11.glTexCoord2f((u + width) * uScale, (v + 0) * vScale);
		GL11.glVertex2f(x + width, y + 0);

		// Esquina superior izquierda
		GL11.glTexCoord2f((u + 0) * uScale, (v + 0) * vScale);
		GL11.glVertex2f(x + 0, y + 0);

		GL11.glEnd(); // Terminar el dibujo
		GL11.glDisable(GL11.GL_TEXTURE_2D); // Deshabilitar texturas
	}


	public void drawTexturedModalRectWithoutTessellator(int x, int y, int u, int v, int width, int height, double uScale, double vScale) {
		GL11.glEnable(GL11.GL_TEXTURE_2D); // Habilitar texturas
		GL11.glBegin(GL11.GL_QUADS); // Comenzar a dibujar un cuadrado

		// Esquina inferior izquierda
		GL11.glTexCoord2f((float) ((u + 0) * uScale), (float) ((v + height) * vScale));
		GL11.glVertex2f(x + 0, y + height);

		// Esquina inferior derecha
		GL11.glTexCoord2f((float) ((u + width) * uScale), (float) ((v + height) * vScale));
		GL11.glVertex2f(x + width, y + height);

		// Esquina superior derecha
		GL11.glTexCoord2f((float) ((u + width) * uScale), (float) ((v + 0) * vScale));
		GL11.glVertex2f(x + width, y + 0);

		// Esquina superior izquierda
		GL11.glTexCoord2f((float) ((u + 0) * uScale), (float) ((v + 0) * vScale));
		GL11.glVertex2f(x + 0, y + 0);

		GL11.glEnd(); // Terminar el dibujo
		GL11.glDisable(GL11.GL_TEXTURE_2D); // Deshabilitar texturas
	}


	public void drawTexturedModalRectWithoutTessellator(double x, double y, int u, int v, int width, int height, int uvWidth, int uvHeight) {
		float uScale = 0.00390625F; // 1/256
		float vScale = 0.00390625F; // 1/256

		GL11.glEnable(GL11.GL_TEXTURE_2D); // Habilitar texturas
		GL11.glBegin(GL11.GL_QUADS); // Comenzar a dibujar un cuadrado

		// Esquina inferior izquierda
		GL11.glTexCoord2f((u + 0) * uScale, (v + uvHeight) * vScale);
		GL11.glVertex2d(x + 0.0, y + height);

		// Esquina inferior derecha
		GL11.glTexCoord2f((u + uvWidth) * uScale, (v + uvHeight) * vScale);
		GL11.glVertex2d(x + width, y + height);

		// Esquina superior derecha
		GL11.glTexCoord2f((u + uvWidth) * uScale, (v + 0) * vScale);
		GL11.glVertex2d(x + width, y + 0.0);

		// Esquina superior izquierda
		GL11.glTexCoord2f((u + 0) * uScale, (v + 0) * vScale);
		GL11.glVertex2d(x + 0.0, y + 0.0);

		GL11.glEnd(); // Terminar el dibujo
		GL11.glDisable(GL11.GL_TEXTURE_2D); // Deshabilitar texturas
	}


	public void drawTexturedModalRectWithoutTessellator(int x, int y, int u, int v, int width, int height, int uvWidth, float scale) {
		float uScale = scale;
		float vScale = scale;

		// Deshabilitar texturas si es necesario
		GL11.glEnable(GL11.GL_TEXTURE_2D); // Asegúrate de que las texturas están habilitadas

		// Comenzar a dibujar un cuadrado
		GL11.glBegin(GL11.GL_QUADS);

		// Esquina inferior izquierda
		GL11.glTexCoord2f((u + 0) * uScale, (v + uvWidth) * vScale);
		GL11.glVertex2f(x + 0, y + height);

		// Esquina inferior derecha
		GL11.glTexCoord2f((u + uvWidth) * uScale, (v + uvWidth) * vScale);
		GL11.glVertex2f(x + width, y + height);

		// Esquina superior derecha
		GL11.glTexCoord2f((u + uvWidth) * uScale, (v + 0) * vScale);
		GL11.glVertex2f(x + width, y + 0);

		// Esquina superior izquierda
		GL11.glTexCoord2f((u + 0) * uScale, (v + 0) * vScale);
		GL11.glVertex2f(x + 0, y + 0);

		GL11.glEnd(); // Terminar el dibujo

		// Restaurar configuraciones de OpenGL si es necesario
		GL11.glDisable(GL11.GL_TEXTURE_2D); // Deshabilitar texturas si es necesario
	}


	protected void drawRectWithoutTessellator(int minX, int minY, int maxX, int maxY, int argb) {
		int temp;
		if (minX < maxX) {
			temp = minX;
			minX = maxX;
			maxX = temp;
		}

		if (minY < maxY) {
			temp = minY;
			minY = maxY;
			maxY = temp;
		}

		float a = (float)(argb >> 24 & 255) / 255.0F;
		float r = (float)(argb >> 16 & 255) / 255.0F;
		float g = (float)(argb >> 8 & 255) / 255.0F;
		float b = (float)(argb & 255) / 255.0F;

		GL11.glEnable(GL11.GL_BLEND); // Habilitar el blending
		GL11.glDisable(GL11.GL_TEXTURE_2D); // Deshabilitar texturas
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); // Configurar la función de mezcla

		GL11.glColor4f(r, g, b, a); // Establecer el color y la transparencia

		GL11.glBegin(GL11.GL_QUADS); // Comenzar a dibujar un cuadrado
		GL11.glVertex2f(minX, maxY); // Esquina superior izquierda
		GL11.glVertex2f(maxX, maxY); // Esquina superior derecha
		GL11.glVertex2f(maxX, minY); // Esquina inferior derecha
		GL11.glVertex2f(minX, minY); // Esquina inferior izquierda
		GL11.glEnd(); // Terminar el dibujo

		GL11.glEnable(GL11.GL_TEXTURE_2D); // Volver a habilitar texturas
		GL11.glDisable(GL11.GL_BLEND); // Deshabilitar el blending
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
			this.drawRectWithoutTessellator(x1, y1, x1 + 1, y1 + 1, argb);

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
