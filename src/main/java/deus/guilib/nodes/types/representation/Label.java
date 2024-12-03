package deus.guilib.nodes.types.representation;

import deus.guilib.nodes.Node;
import deus.guilib.nodes.stylesystem.StyleParser;

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
		drawText();
	}

	protected void drawText() {
		if (mc != null && mc.fontRenderer != null) {
			int textStartX = this.gx + 4;
			int textStartY = this.gy + 4;

			for (int i = 0; i < text.size(); i++) {
				String line = text.get(i);

				int lh = mc.fontRenderer.fontHeight;
				int color = StyleParser.parseColorToARGB("#000000");
				boolean shadow = false;

				if (styles.containsKey("lineHeight")) {
					lh = StyleParser.parsePixels((String) styles.get("lineHeight"));
				}

				if (styles.containsKey("textColor")) {
					color = StyleParser.parseColorToARGB((String) styles.get("textColor"));
				}

				if (styles.containsKey("textShadow")) {
					shadow = (Boolean) styles.get("textShadow");
				}

				if (shadow) {
					this.drawString(this.mc.fontRenderer, line, textStartX, textStartY + lh * i, color);
				} else {
					this.drawStringNoShadow(this.mc.fontRenderer, line, textStartX, textStartY + lh * i, color);
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
