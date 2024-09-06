package deus.guilib.element.config.derivated;

import deus.guilib.element.config.Config;

public class ElementConfig extends Config<ElementConfig> {

	private boolean ignoreFatherPlacement = false;

	private ElementConfig() {
	}
	public static ElementConfig create() {
		return new ElementConfig();
	}

	public boolean isIgnoreFatherPlacement() {
		return ignoreFatherPlacement;
	}

	public ElementConfig setIgnoreFatherPlacement(boolean ignoreFatherPlacement) {
		this.ignoreFatherPlacement = ignoreFatherPlacement;
		return this;
	}

}
