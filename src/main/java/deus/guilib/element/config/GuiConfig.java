package deus.guilib.element.config;


public class GuiConfig {
	private ChildrenPlacement placement = ChildrenPlacement.NONE;
	private String theme = "VANILLA";

	public GuiConfig(ChildrenPlacement placement, String theme) {
		this.placement = placement;
		this.theme = theme;
	}


	public GuiConfig(ChildrenPlacement placement) {
		this.placement = placement;
	}


	public GuiConfig(String theme) {
		this.theme = theme;
	}

	public GuiConfig(boolean internal, ChildrenPlacement placement) {
		this.placement = placement;

	}

	public ChildrenPlacement getPlacement() {
		return placement;
	}

	public void setPlacement(ChildrenPlacement placement) {
		this.placement = placement;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}
