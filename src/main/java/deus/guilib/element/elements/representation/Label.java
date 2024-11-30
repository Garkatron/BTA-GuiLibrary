package deus.guilib.element.elements.representation;

import deus.guilib.GuiLib;
import deus.guilib.element.Node;
import deus.guilib.element.stylesystem.StyleParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Label extends Node {

	protected List<String> text = new ArrayList<>();
	protected int maxTextLength = 28;

	public Label(Map<String, String> attr) {
		super(attr);
		GuiLib.LOGGER.info("ATTR: {}",attr.toString());
	}

	public Label() {
		super();
	}


	public Label setText(List<String> text) {
		this.text = text;
		return this;
	}

	public Label addText(String... text) {
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


	public Label addText(List<String> text) {
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

				if (styles.containsKey("lineHeight")) {
					if (styles.containsKey("textColor")) {
						if (styles.containsKey("shadow")) {
							// Dibuja con sombra
							this.drawString(this.mc.fontRenderer, line, textStartX, textStartY + (StyleParser.parsePixels((String) styles.get("lineHeight")) * i), (Integer) styles.get("textColor"));
						} else {
							// Dibuja sin sombra
							this.drawStringNoShadow(this.mc.fontRenderer, line, textStartX, textStartY + (StyleParser.parsePixels((String) styles.get("lineHeight")) * i), (Integer) styles.get("textColor"));
						}
					}
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
		return text.size() * StyleParser.parsePixels((String) styles.get("lineHeight"));
	}

	public Label setMaxTextLength(int maxTextLength) {
		this.maxTextLength = maxTextLength;
		return this;
	}

	public int getMaxTextLength() {
		return maxTextLength;
	}

	public boolean isShadow() {
		return styles.containsKey("shadow");
	}

}
