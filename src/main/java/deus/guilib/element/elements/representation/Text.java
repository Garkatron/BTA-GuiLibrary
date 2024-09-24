package deus.guilib.element.elements.representation;

import deus.guilib.element.Element;

import java.util.ArrayList;
import java.util.List;

public class Text extends Element {

	protected List<String> text = new ArrayList<>();
	protected int lineHeight = this.mc.fontRenderer.fontHeight;
	protected int textColor = 0xffffff;
	protected int maxTextLength = 28;
	protected boolean shadow = true;

	public Text setText(List<String> text) {
		this.text = text;
		return this;
	}

	public Text addText(String... text) {
		List<String> finalText = new ArrayList<>();

		for (String line : text) {

			while (line.length() > maxTextLength) {
				String newText = line.substring(0, maxTextLength);
				finalText.add(newText);
				line = line.substring(maxTextLength);
			}

			if (!line.isEmpty()) {
				finalText.add(line);
			} else {
				finalText.add("");
			}
		}

		this.text.addAll(finalText);

		return this;
	}


	public Text addText(List<String> text) {
		List<String> finalText = new ArrayList<>();

		for (String line : text) {

			while (line.length() > maxTextLength) {
				String newText = line.substring(0, maxTextLength);
				finalText.add(newText);
				line = line.substring(maxTextLength);
			}

			if (!line.isEmpty()) {
				finalText.add(line);
			} else {
				finalText.add("");
			}
		}

		this.text.addAll(finalText);

		return this;
	}


	@Override
	protected void drawIt() {
		// Verifica si mc y fontRenderer no son nulos antes de dibujar
		if (mc != null && mc.fontRenderer != null) {
			// Ajustar las coordenadas de inicio
			int textStartX = this.gx + 4; // Usa gx para la posición horizontal
			int textStartY = this.gy + 4; // Usa gy para la posición vertical

			// Iterar sobre cada línea de texto y dibujarla
			for (int i = 0; i < text.size(); i++) {
				String line = text.get(i);
				if (shadow) {
					// Dibuja con sombra
					this.drawString(this.mc.fontRenderer, line, textStartX, textStartY + (lineHeight * i), textColor);
				} else {
					// Dibuja sin sombra
					this.drawStringNoShadow(this.mc.fontRenderer, line, textStartX, textStartY + (lineHeight * i), textColor);
				}
			}
		}
	}

	@Override
	public int getWidth() {
		int width = 0;
		if (mc != null && mc.fontRenderer != null) {
			for (String s : text) {
				int lineWidth = mc.fontRenderer.getStringWidth(s); // Obtiene el ancho de la línea en píxeles
				if (lineWidth > width) {
					width = lineWidth; // Mantiene el valor del mayor ancho
				}
			}
		}
		return width;
	}

	@Override
	public int getHeight() {
		return text.size() * lineHeight;
	}


	public Text setLineHeight(int lineHeight) {
		this.lineHeight = lineHeight;
		return this;
	}

	public int getLineHeight() {
		return lineHeight;
	}

	public Text setTextColor(int textColor) {
		this.textColor = textColor;
		return this;
	}

	public int getTextColor() {
		return textColor;
	}

	public Text setMaxTextLength(int maxTextLength) {
		this.maxTextLength = maxTextLength;
		return this;
	}

	public int getMaxTextLength() {
		return maxTextLength;
	}

	public boolean isShadow() {
		return shadow;
	}

	public Text withShadow(boolean shadow) {
		this.shadow = shadow;
		return this;
	}
}
