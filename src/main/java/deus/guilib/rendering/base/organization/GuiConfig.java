package deus.guilib.rendering.base.organization;

import deus.guilib.rendering.resource.Theme;

public class GuiConfig {
	private childrenPlacement placement = childrenPlacement.NONE;
	private Theme theme = Theme.VANILLA;

	public GuiConfig(childrenPlacement placement, Theme theme) {
		this.placement = placement;
		this.theme = theme;
	}

	public GuiConfig(Theme theme) {
		this.theme = theme;
	}

	public GuiConfig(boolean internal, childrenPlacement placement) {
		this.placement = placement;

	}

	public childrenPlacement getPlacement() {
		return placement;
	}

	public void setPlacement(childrenPlacement placement) {
		this.placement = placement;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}
}
