package deus.guilib.element.elements;

import deus.guilib.element.Element;

public class Text extends Element {

	protected String text = "";

	public Text setText(String text) {
		this.text = text;
		return this;
	}

	@Override
	protected void drawIt() {
		//super.drawIt();
		if (mc != null && mc.fontRenderer != null) {
			this.mc.fontRenderer.drawString(text, x, y, 0xFFFFFFFF);//4210752
		}
	}


	@Override
	public int getWidth() {
		return 6 * text.length();
	}

	@Override
	public int getHeight() {
		return 10;
	}
}
