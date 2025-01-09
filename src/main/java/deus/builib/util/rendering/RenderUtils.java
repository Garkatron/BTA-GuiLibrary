package deus.builib.util.rendering;

import deus.builib.interfaces.IRenderEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.TextureBuffered;
import org.lwjgl.opengl.GL11;


public class RenderUtils extends Gui {



	public void bindTexture(Minecraft mc, String path) {
		try {

			IRenderEngine re = (IRenderEngine) mc.textureManager;
			TextureBuffered tb = Minecraft.getMinecraft().textureManager.loadBufferedTexture(re.bui$getImageAdvanced(path));
			tb.bind();

		} catch (Exception e) {
			System.err.println("Error loading texture: " + e.getMessage());
		}
	}

	public void drawTexture(Minecraft mc, TextureProperties textureProperties, int x, int y, int w, int h) {
		drawGuiTexture(mc, textureProperties, x, y, w, h);
	}

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

	public void drawTexturedModalRectDouble(final double x, final double y, final double u, final double v, final double width, final double height, final double uScale, final double vScale) {
		final double off = 0.05d;
		final Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x - off, y + height + off, this.zLevel, (u + 0) * uScale, (v + height) * vScale);
		tessellator.addVertexWithUV(x + width + off, y + height + off, this.zLevel, (u + width) * uScale, (v + height) * vScale);
		tessellator.addVertexWithUV(x + width + off, y - off, this.zLevel, (u + width) * uScale, (v + 0) * vScale);
		tessellator.addVertexWithUV(x - off, y - off, this.zLevel, (u + 0) * uScale, (v + 0) * vScale);
		tessellator.draw();
	}

	public void drawTexturedModalRectDouble(final double x, final double y, final double u, final double v, final double width, final double height, final double uScale, final double vScale, final double off) {
		final double foon = /*(0.25d * uScale)*/0;
		final double goon = /*(0.25d * vScale)*/0;
		final Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x - off, y + height + off, this.zLevel, (u + 0.5) * uScale, (v + height) * vScale);
		tessellator.addVertexWithUV(x + width + off, y + height + off, this.zLevel, (u + width - 0.5) * uScale, (v + height) * vScale);
		tessellator.addVertexWithUV(x + width + off, y - off, this.zLevel, (u + width - 0.5) * uScale, v * vScale);
		tessellator.addVertexWithUV(x - off, y - off, this.zLevel, (u + 0.5) * uScale, v * vScale);
		tessellator.draw();
	}


	public void drawTexturedModalRectDouble(final double x, final double y, final double u, final double v, final double width, final double height, final double uWidth, final double vHeight, final double uScale, final double vScale) {
		final double foon = (0.5f * uScale);
		final double goon = 0.0625f * (16.0f / uWidth);
		final double off = 0.05d;
		final Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x - off, y + height + off, this.zLevel, (u + foon) * uScale, (v + vHeight - foon) * vScale);
		tessellator.addVertexWithUV(x + width + off, y + height + off, this.zLevel, (u + uWidth - foon) * uScale, (v + vHeight - foon) * vScale);
		tessellator.addVertexWithUV(x + width + off, y - off, this.zLevel, (u + uWidth - foon) * uScale, (v + foon) * vScale);
		tessellator.addVertexWithUV(x - off, y - off, this.zLevel, (u + foon) * uScale, (v + foon) * vScale);
		tessellator.draw();
	}


	// Thanks useless
	public void drawGuiTexture(Minecraft mc, TextureProperties tp, final int x, final int y, final int width, final int height) {
		final Tessellator t = Tessellator.instance;

		bindTexture(mc, tp.path());

		GL11.glColor4f(1F, 1F, 1F, 1F);

		if (true) {
			switch (tp.type()) {
				case STRETCH: {
					t.startDrawingQuads();
					t.addVertexWithUV(x, y + height, this.zLevel, 0, 1);
					t.addVertexWithUV(x + width, y + height, this.zLevel, 1, 1);
					t.addVertexWithUV(x + width, y, this.zLevel, 1, 0);
					t.addVertexWithUV(x, y, this.zLevel, 0, 0);
					t.draw();
					break;
				}
				case TILE: {
					final double uScale = 1d / tp.width();
					final double vScale = 1d / tp.height();
					t.startDrawingQuads();
					t.addVertexWithUV(x, y + height, this.zLevel, 0, height * vScale);
					t.addVertexWithUV(x + width, y + height, this.zLevel, width * uScale, height * vScale);
					t.addVertexWithUV(x + width, y, this.zLevel, width * uScale, 0);
					t.addVertexWithUV(x, y, this.zLevel, 0, 0);
					t.draw();
					break;
				}
				case NINE_SLICE: {
					final double uScale = 1d / tp.width();
					final double vScale = 1d / tp.height();

					final int innerWidth = tp.width() - (tp.border().left() + tp.border().right());
					final int innerHeight = tp.height() - (tp.border().top() + tp.border().bottom());
					final int innerLeft = tp.border().left();
					final int innerTop = tp.border().top();
					final int innerRight = tp.width() - tp.border().right();
					final int innerBottom = tp.height() - tp.border().bottom();

					final int leftHeight = height - (tp.border().top() + tp.border().bottom());
					final int repeatsY = leftHeight / innerHeight;
					final int remainderY = leftHeight % innerHeight;

					final int topWidth = width - (tp.border().left() + tp.border().right());
					final int repeatsX = topWidth / innerWidth;
					final int remainderX = topWidth % innerWidth;

					{ // Inner
						if (tp.stretchInner()) { // Stretch Inner // TODO implement

						} else { // Tile Inner
							for (int j = 0; j < repeatsY; j++) {
								drawTexturedModalRectDouble(
									x + innerLeft + repeatsX * innerWidth, y + innerTop + j * innerHeight,
									innerLeft + 1, innerTop,
									remainderX, innerHeight,
									uScale, vScale, 0.0); // Inner
							}

							for (int i = 0; i < repeatsX; i++) {
								drawTexturedModalRectDouble(
									x + innerLeft + i * innerWidth, y + innerTop + repeatsY * innerHeight,
									innerLeft, innerTop,
									innerWidth, remainderY,
									uScale, vScale, 0.0); // Inner
							}
							drawTexturedModalRectDouble(
								x + innerLeft + repeatsX * innerWidth, y + innerTop + repeatsY * innerHeight,
								innerLeft, innerTop,
								remainderX, remainderY,
								uScale, vScale, 0.0); // Inner

							for (int i = 0; i < repeatsX; i++) {
								for (int j = 0; j < repeatsY; j++) {
									drawTexturedModalRectDouble(
										x + innerLeft + i * innerWidth, y + innerTop + j * innerHeight,
										innerLeft, innerTop,
										innerWidth, innerHeight,
										uScale, vScale, 0.0); // Inner
								}
							}
						}
					}

					{ // Left Right
						for (int i = 0; i < repeatsY; i++) {
							drawTexturedModalRectDouble(
								x, y + innerTop + i * innerHeight,
								0, innerTop,
								innerLeft, innerHeight,
								uScale, vScale, 0); // left

							drawTexturedModalRectDouble(
								x + width - tp.border().right(), y + innerTop + i * innerHeight,
								innerRight, innerTop,
								innerLeft, innerHeight,
								uScale, vScale, 0); // right
						}

						drawTexturedModalRectDouble(
							x, y + innerTop + repeatsY * innerHeight,
							0, innerTop,
							innerLeft, remainderY,
							uScale, vScale, 0); // left

						drawTexturedModalRectDouble(
							x + width - tp.border().right(), y + innerTop + repeatsY * innerHeight,
							innerRight, innerTop,
							innerLeft, remainderY,
							uScale, vScale, 0); // right
					}
					{ // Top Bottom
						for (int i = 0; i < repeatsX; i++) {
							drawTexturedModalRectDouble(
								x + innerLeft + i * innerWidth, y,
								innerLeft, 0,
								innerWidth, tp.border().top(),
								uScale, vScale, 0); // top

							drawTexturedModalRectDouble(
								x + innerLeft + i * innerWidth, y + height - tp.border().bottom(),
								innerLeft, innerBottom,
								innerWidth, tp.border().bottom(),
								uScale, vScale, 0); // bottom
						}

						drawTexturedModalRectDouble(
							x + innerLeft + repeatsX * innerWidth, y,
							innerLeft, 0,
							remainderX, tp.border().top(),
							uScale, vScale, 0); // top

						drawTexturedModalRectDouble(
							x + innerLeft + repeatsX * innerWidth, y + height - tp.border().bottom(),
							innerLeft, innerBottom,
							remainderX, tp.border().bottom(),
							uScale, vScale, 0); // bottom
					}

					drawTexturedModalRectDouble(
						x, y,
						0, 0,
						tp.border().left(), tp.border().top(),
						uScale, vScale, 0); // top-left

					drawTexturedModalRectDouble(
						x + width - tp.border().right(), y,
						innerRight, 0,
						tp.border().right(), tp.border().top(),
						uScale, vScale, 0); // top-right

					drawTexturedModalRectDouble(
						x, y + height - tp.border().bottom(),
						0, innerBottom,
						tp.border().left(), tp.border().bottom(),
						uScale, vScale, 0); // bottom-left

					drawTexturedModalRectDouble(
						x + width - tp.border().right(), y + height - tp.border().bottom(),
						innerRight, innerBottom,
						tp.border().right(), tp.border().bottom(),
						uScale, vScale, 0); // bottom-right

					break;
				}
			}
		} else {
			t.startDrawingQuads();
			t.addVertexWithUV(x, y + height, this.zLevel, 0, 1);
			t.addVertexWithUV(x + width, y + height, this.zLevel, 1, 1);
			t.addVertexWithUV(x + width, y, this.zLevel, 1, 0);
			t.addVertexWithUV(x, y, this.zLevel, 0, 0);
			t.draw();
		}

	}

	public void drawTexturedModalRectWithoutTessellator(int x, int y, int u, int v, int width, int height) {
		float uScale = 0.00390625F; // 1/256
		float vScale = 0.00390625F; // 1/256

		GL11.glEnable(GL11.GL_TEXTURE_2D); // Habilitar texturas
		GL11.glBegin(GL11.GL_QUADS); // Comenzar a dibujar un cuadrado

		// Esquina inferior izquierda
		GL11.glTexCoord2f((u) * uScale, (v + height) * vScale);
		GL11.glVertex2f(x, y + height);

		// Esquina inferior derecha
		GL11.glTexCoord2f((u + width) * uScale, (v + height) * vScale);
		GL11.glVertex2f(x + width, y + height);

		// Esquina superior derecha
		GL11.glTexCoord2f((u + width) * uScale, (v) * vScale);
		GL11.glVertex2f(x + width, y);

		// Esquina superior izquierda
		GL11.glTexCoord2f((u) * uScale, (v) * vScale);
		GL11.glVertex2f(x, y);

		GL11.glEnd(); // Terminar el dibujo
		GL11.glDisable(GL11.GL_TEXTURE_2D); // Deshabilitar texturas
	}


	public void drawTexturedModalRectWithoutTessellator(int x, int y, int u, int v, int width, int height, double uScale, double vScale) {
		GL11.glEnable(GL11.GL_TEXTURE_2D); // Habilitar texturas
		GL11.glBegin(GL11.GL_QUADS); // Comenzar a dibujar un cuadrado

		// Esquina inferior izquierda
		GL11.glTexCoord2f((float) ((u) * uScale), (float) ((v + height) * vScale));
		GL11.glVertex2f(x, y + height);

		// Esquina inferior derecha
		GL11.glTexCoord2f((float) ((u + width) * uScale), (float) ((v + height) * vScale));
		GL11.glVertex2f(x + width, y + height);

		// Esquina superior derecha
		GL11.glTexCoord2f((float) ((u + width) * uScale), (float) ((v) * vScale));
		GL11.glVertex2f(x + width, y);

		// Esquina superior izquierda
		GL11.glTexCoord2f((float) ((u) * uScale), (float) ((v) * vScale));
		GL11.glVertex2f(x, y);

		GL11.glEnd(); // Terminar el dibujo
		GL11.glDisable(GL11.GL_TEXTURE_2D); // Deshabilitar texturas
	}


	public void drawTexturedModalRectWithoutTessellator(double x, double y, int u, int v, int width, int height, int uvWidth, int uvHeight) {
		float uScale = 0.00390625F; // 1/256
		float vScale = 0.00390625F; // 1/256

		GL11.glEnable(GL11.GL_TEXTURE_2D); // Habilitar texturas
		GL11.glBegin(GL11.GL_QUADS); // Comenzar a dibujar un cuadrado

		// Esquina inferior izquierda
		GL11.glTexCoord2f((u) * uScale, (v + uvHeight) * vScale);
		GL11.glVertex2d(x + 0.0, y + height);

		// Esquina inferior derecha
		GL11.glTexCoord2f((u + uvWidth) * uScale, (v + uvHeight) * vScale);
		GL11.glVertex2d(x + width, y + height);

		// Esquina superior derecha
		GL11.glTexCoord2f((u + uvWidth) * uScale, (v) * vScale);
		GL11.glVertex2d(x + width, y + 0.0);

		// Esquina superior izquierda
		GL11.glTexCoord2f((u) * uScale, (v) * vScale);
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
		GL11.glTexCoord2f((u) * uScale, (v + uvWidth) * vScale);
		GL11.glVertex2f(x, y + height);

		// Esquina inferior derecha
		GL11.glTexCoord2f((u + uvWidth) * uScale, (v + uvWidth) * vScale);
		GL11.glVertex2f(x + width, y + height);

		// Esquina superior derecha
		GL11.glTexCoord2f((u + uvWidth) * uScale, (v) * vScale);
		GL11.glVertex2f(x + width, y);

		// Esquina superior izquierda
		GL11.glTexCoord2f((u) * uScale, (v) * vScale);
		GL11.glVertex2f(x, y);

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

		float a = (float) (argb >> 24 & 255) / 255.0F;
		float r = (float) (argb >> 16 & 255) / 255.0F;
		float g = (float) (argb >> 8 & 255) / 255.0F;
		float b = (float) (argb & 255) / 255.0F;

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
