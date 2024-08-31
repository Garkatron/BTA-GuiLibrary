package deus.guilib.rendering.base.organization;

import deus.guilib.rendering.resource.Theme;

public class ElementConfig {
	private boolean internal = false;
	private childrenPlacement placement = childrenPlacement.NONE;
	private Theme theme = Theme.VANILLA;
	private boolean ignoreFatherPlacement = false;

	public ElementConfig(boolean internal, childrenPlacement placement, Theme theme, boolean ignoreFatherPlacement) {
		this.internal = internal;
		this.placement = placement;
		this.theme = theme;
		this.ignoreFatherPlacement = ignoreFatherPlacement;
	}

	public ElementConfig(childrenPlacement placement) {
		this.placement = placement;
	}

	public ElementConfig(boolean ignoreFatherPlacement) {
		this.ignoreFatherPlacement = ignoreFatherPlacement;

	}
	public ElementConfig(Theme theme, boolean ignoreFatherPlacement) {
		this.theme = theme;
		this.ignoreFatherPlacement = ignoreFatherPlacement;
	}


	public ElementConfig(Theme theme) {
		this.theme = theme;
	}

	public ElementConfig(boolean internal, childrenPlacement placement) {
		this.internal = internal;
		this.placement = placement;

	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
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

	public boolean getIgnoreFatherPlacement() {
		return ignoreFatherPlacement;
	}

	public void setIgnoreFatherPlacement(boolean ignoreFatherPlacement) {
		this.ignoreFatherPlacement = ignoreFatherPlacement;
	}
}
