package deus.guilib.element.config.derivated;

import deus.guilib.element.config.Config;

/**
 * Configuration class for GUIs that extends {@link Config}.
 * Provides settings specific to GUI configurations, such as whether to use unique SIDs for GUI elements.
 */
public class GuiConfig extends Config<GuiConfig> {

	private boolean useSIDs = true;

	/**
	 * Creates a new instance of {@code GuiConfig}.
	 *
	 * @return A new instance of {@code GuiConfig}.
	 */
	public static GuiConfig create() {
		return new GuiConfig();
	}

	/**
	 * Checks if unique SIDs are used for GUI elements.
	 *
	 * @return {@code true} if SIDs are used; {@code false} otherwise.
	 */
	public boolean isUseSIDs() {
		return useSIDs;
	}

	/**
	 * Sets whether to use unique SIDs for GUI elements.
	 *
	 * @param useSIDs A boolean value indicating if SIDs should be used.
	 * @return The current instance of {@code GuiConfig} for method chaining.
	 */
	public GuiConfig setUseSIDs(boolean useSIDs) {
		this.useSIDs = useSIDs;
		return this;
	}
}
