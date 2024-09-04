package deus.guilib.element.config.derivated;

import deus.guilib.element.config.Config;

public class PageGuiConfig extends Config<PageGuiConfig> {

	private boolean useWindowSizeAsSize = true;

	public static PageGuiConfig create() {
		return new PageGuiConfig();
	}

	public boolean isUseWindowSizeAsSize() {
		return useWindowSizeAsSize;
	}

	public PageGuiConfig setUseWindowSizeAsSize(boolean useWindowSizeAsSize) {
		this.useWindowSizeAsSize = useWindowSizeAsSize;
		return this;
	}
}
