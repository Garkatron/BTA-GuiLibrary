package deus.guilib.element.elements;

import deus.guilib.element.Element;

public class Text extends Element {

	protected String text = "";


	// Constructor vacío
	public Text() {
		super();
	}

	// Métodos para configurar los parámetros
	public Text setText(String text) {
		this.text = text;
		return this;
	}

	@Override
	protected void drawIt() {
		super.drawIt();
		if (mc != null && mc.fontRenderer != null) {
			this.mc.fontRenderer.drawString(text, x, y, 4210752);
		}
	}
}
