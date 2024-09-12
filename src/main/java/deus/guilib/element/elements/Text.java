package deus.guilib.element.elements;

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
		//super.drawIt();
		if (mc != null && mc.fontRenderer != null) {

			int textStartY = this.y + 4;

			for (int i = 0; i < text.size(); i++) {
				String line = text.get(i);
				if (shadow) {
					this.drawString(this.mc.fontRenderer, line, this.x + 4, textStartY + (lineHeight * i), textColor);
				} else {
					this.drawStringNoShadow(this.mc.fontRenderer, line, this.x + 4, textStartY + (lineHeight * i), textColor);
				}
			}
		}
	}


	@Override
	public int getWidth() {
		int width = 0;
		for (String s : text) {
			if (s.length() > width) {
				width = s.length();
			}

		}
		return width;
	}

	@Override
	public int getHeight() {
		return lineHeight;
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
