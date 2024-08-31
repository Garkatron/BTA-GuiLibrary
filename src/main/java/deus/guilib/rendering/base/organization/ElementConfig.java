package deus.guilib.rendering.base.organization;

import deus.guilib.rendering.resource.Theme;

public class ElementConfig {
	private boolean internal = false;
	private Placement placement = Placement.NONE;
	private Theme theme = Theme.VANILLA;

	public ElementConfig(boolean internal, Placement placement, Theme theme) {
		this.internal = internal;
		this.placement = placement;
		this.theme = theme;
	}

	public ElementConfig(Theme theme) {
		this.theme = theme;
	}

	public ElementConfig(boolean internal, Placement placement) {
		this.internal = internal;
		this.placement = placement;

	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public Placement getPlacement() {
		return placement;
	}

	public void setPlacement(Placement placement) {
		this.placement = placement;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}
}
