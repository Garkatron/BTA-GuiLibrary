package deus.guilib.element.config.derivated;

import deus.guilib.element.config.Config;

/**
 * Configuration class for page GUIs that extends {@link Config}.
 * Provides settings specific to page GUIs, such as whether to adjust the page size based on the window size.
 */
public class PageGuiConfig extends Config<PageGuiConfig> {

	private boolean useWindowSizeAsSize = true;

	/**
	 * Creates a new instance of {@code PageGuiConfig}.
	 *
	 * @return A new instance of {@code PageGuiConfig}.
	 */
	public static PageGuiConfig create() {
		return new PageGuiConfig();
	}

	/**
	 * Checks if the page size is set to match the window size.
	 *
	 * @return {@code true} if the page size matches the window size; {@code false} otherwise.
	 */
	public boolean isUseWindowSizeAsSize() {
		return useWindowSizeAsSize;
	}

	/**
	 * Sets whether the page size should match the window size.
	 *
	 * @param useWindowSizeAsSize A boolean value indicating if the page size should match the window size.
	 * @return The current instance of {@code PageGuiConfig} for method chaining.
	 */
	public PageGuiConfig setUseWindowSizeAsSize(boolean useWindowSizeAsSize) {
		this.useWindowSizeAsSize = useWindowSizeAsSize;
		return this;
	}
}
