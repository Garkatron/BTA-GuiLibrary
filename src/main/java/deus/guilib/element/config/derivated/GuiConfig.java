package deus.guilib.element.config.derivated;


import deus.guilib.element.config.Config;

public class GuiConfig extends Config<GuiConfig> {

	private boolean useSIDs = true;

	public static GuiConfig create() {
		return new GuiConfig();
	}


	public boolean isUseSIDs() {
		return useSIDs;
	}

	public GuiConfig setUseSIDs(boolean useSIDs) {
		this.useSIDs = useSIDs;
		return this;
	}
}
