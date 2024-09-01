package deus.guilib.rendering.base.organization;


public class GuiConfig {
	private childrenPlacement placement = childrenPlacement.NONE;
	private String theme = "VANILLA";

	public GuiConfig(childrenPlacement placement, String theme) {
		this.placement = placement;
		this.theme = theme;
	}


	public GuiConfig(childrenPlacement placement) {
		this.placement = placement;
	}


	public GuiConfig(String theme) {
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

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}
