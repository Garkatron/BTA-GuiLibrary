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
		if (attr.containsKey("maxTextLength")) {
			setMaxTextLength(Integer.parseInt(attr.get("maxTextLength")));
		}
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
			String[] splitLines = line.split("\\\\n");
			for (String splitLine : splitLines) {
				while (splitLine.length() > maxTextLength) {
					String newText = splitLine.substring(0, maxTextLength);
					finalText.add(newText);
					splitLine = splitLine.substring(maxTextLength);
				}
				finalText.add(splitLine.isEmpty() ? "" : splitLine);
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
		super.drawIt();

		// Verifica si mc y fontRenderer no son nulos antes de dibujar
		if (mc != null && mc.fontRenderer != null) {
			// Ajustar las coordenadas de inicio
			int textStartX = this.gx + 4; // Usa gx para la posición horizontal
			int textStartY = this.gy + 4; // Usa gy para la posición vertical

			// Iterar sobre cada línea de texto y dibujarla
			for (int i = 0; i < text.size(); i++) {
				String line = text.get(i);

				if (styles.containsKey("lineHeight")) {
					int lh = StyleParser.parsePixels((String) styles.getOrDefault("lineHeight", String.valueOf(mc.fontRenderer.fontHeight) + "px"));
					if (styles.containsKey("textColor")) {
						int color = StyleParser.parseColorToARGB((String) styles.getOrDefault("textColor","#000000"));
						if (styles.containsKey("shadow")) {
							this.drawString(this.mc.fontRenderer, line, textStartX, textStartY + lh * i, color);
						} else {
							this.drawStringNoShadow(this.mc.fontRenderer, line, textStartX, textStartY + lh * i, color);
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
				int lineWidth = mc.fontRenderer.getStringWidth(s) + 6;
				if (lineWidth > width) {
					width = lineWidth;
				}
			}
		}
		return width;
	}

	@Override
	public int getHeight() {
		int lh = StyleParser.parsePixels((String) styles.getOrDefault("lineHeight", mc.fontRenderer.fontHeight + "px"));

		return text.size() * lh;
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
