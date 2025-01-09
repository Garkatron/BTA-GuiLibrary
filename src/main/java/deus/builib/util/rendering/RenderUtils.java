package deus.builib.util.rendering;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.Texture;
import net.minecraft.client.render.texture.meta.gui.GuiTextureProperties;


public class RenderUtils extends Gui {


	public void drawGuiTextureNoMeta(TextureManager re, GuiTextureProperties properties, int x, int y, int width, int height, String texture) {
		Texture tex = re.loadTexture(texture);
		tex.bind();
		Tessellator t = Tessellator.instance;
		switch (properties.type) {
			case "stretch": {
				t.startDrawingQuads();
				t.addVertexWithUV(x, y + height, this.zLevel, 0.0F, 1.0F);
				t.addVertexWithUV(x + width, y + height, this.zLevel, 1.0F, 1.0F);
				t.addVertexWithUV(x + width, y, this.zLevel, 1.0F, 0.0F);
				t.addVertexWithUV(x, y, this.zLevel, 0.0F, 0.0F);
				t.draw();
				break;
			}
			case "tile": {
				double uScale = (double) 1.0F / (double) properties.width;
				double vScale = (double) 1.0F / (double) properties.height;
				t.startDrawingQuads();
				t.addVertexWithUV(x, y + height, this.zLevel, 0.0F, (double) height * vScale);
				t.addVertexWithUV(x + width, y + height, this.zLevel, (double) width * uScale, (double) height * vScale);
				t.addVertexWithUV(x + width, y, this.zLevel, (double) width * uScale, 0.0F);
				t.addVertexWithUV(x, y, this.zLevel, 0.0F, 0.0F);
				t.draw();
				break;
			}
			case "nine_slice": {
				double uScale = (double) 1.0F / (double) properties.width;
				double vScale = (double) 1.0F / (double) properties.height;
				int innerWidth = properties.width - (properties.border.left + properties.border.right);
				int innerHeight = properties.height - (properties.border.top + properties.border.bottom);
				int innerLeft = properties.border.left;
				int innerTop = properties.border.top;
				int innerRight = properties.width - properties.border.right;
				int innerBottom = properties.height - properties.border.bottom;
				int leftHeight = height - (properties.border.top + properties.border.bottom);
				int repeatsY = leftHeight / innerHeight;
				int remainderY = leftHeight % innerHeight;
				int topWidth = width - (properties.border.left + properties.border.right);
				int repeatsX = topWidth / innerWidth;
				int remainderX = topWidth % innerWidth;
				if (!properties.stretchInner) {
					for (int j = 0; j < repeatsY; ++j) {
						this.drawTexturedModalRectDouble(x + innerLeft + repeatsX * innerWidth, y + innerTop + j * innerHeight, innerLeft + 1, innerTop, remainderX, innerHeight, uScale, vScale, 0.0F);
					}

					for (int i = 0; i < repeatsX; ++i) {
						this.drawTexturedModalRectDouble(x + innerLeft + i * innerWidth, y + innerTop + repeatsY * innerHeight, innerLeft, innerTop, innerWidth, remainderY, uScale, vScale, 0.0F);
					}

					this.drawTexturedModalRectDouble(x + innerLeft + repeatsX * innerWidth, y + innerTop + repeatsY * innerHeight, innerLeft, innerTop, remainderX, remainderY, uScale, vScale, 0.0F);

					for (int i = 0; i < repeatsX; ++i) {
						for (int j = 0; j < repeatsY; ++j) {
							this.drawTexturedModalRectDouble(x + innerLeft + i * innerWidth, y + innerTop + j * innerHeight, innerLeft, innerTop, innerWidth, innerHeight, uScale, vScale, 0.0F);
						}
					}
				}

				for (int i = 0; i < repeatsY; ++i) {
					this.drawTexturedModalRectDouble(x, y + innerTop + i * innerHeight, 0.0F, innerTop, innerLeft, innerHeight, uScale, vScale, 0.0F);
					this.drawTexturedModalRectDouble(x + width - properties.border.right, y + innerTop + i * innerHeight, innerRight, innerTop, innerLeft, innerHeight, uScale, vScale, 0.0F);
				}

				this.drawTexturedModalRectDouble(x, y + innerTop + repeatsY * innerHeight, 0.0F, innerTop, innerLeft, remainderY, uScale, vScale, 0.0F);
				this.drawTexturedModalRectDouble(x + width - properties.border.right, y + innerTop + repeatsY * innerHeight, innerRight, innerTop, innerLeft, remainderY, uScale, vScale, 0.0F);

				for (int i = 0; i < repeatsX; ++i) {
					this.drawTexturedModalRectDouble(x + innerLeft + i * innerWidth, y, innerLeft, 0.0F, innerWidth, properties.border.top, uScale, vScale, 0.0F);
					this.drawTexturedModalRectDouble(x + innerLeft + i * innerWidth, y + height - properties.border.bottom, innerLeft, innerBottom, innerWidth, properties.border.bottom, uScale, vScale, 0.0F);
				}

				this.drawTexturedModalRectDouble(x + innerLeft + repeatsX * innerWidth, y, innerLeft, 0.0F, remainderX, properties.border.top, uScale, vScale, 0.0F);
				this.drawTexturedModalRectDouble(x + innerLeft + repeatsX * innerWidth, y + height - properties.border.bottom, innerLeft, innerBottom, remainderX, properties.border.bottom, uScale, vScale, 0.0F);
				this.drawTexturedModalRectDouble(x, y, 0.0F, 0.0F, properties.border.left, properties.border.top, uScale, vScale, 0.0F);
				this.drawTexturedModalRectDouble(x + width - properties.border.right, y, innerRight, 0.0F, properties.border.right, properties.border.top, uScale, vScale, 0.0F);
				this.drawTexturedModalRectDouble(x, y + height - properties.border.bottom, 0.0F, innerBottom, properties.border.left, properties.border.bottom, uScale, vScale, 0.0F);
				this.drawTexturedModalRectDouble(x + width - properties.border.right, y + height - properties.border.bottom, innerRight, innerBottom, properties.border.right, properties.border.bottom, uScale, vScale, 0.0F);

			}
		}

	}

}
