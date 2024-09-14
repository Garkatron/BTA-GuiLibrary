package deus.guilib.element.config.derivated;

import deus.guilib.element.config.Config;

/**
 * Configuration class for elements that extends {@link Config}.
 * Provides additional settings specific to elements, such as whether an element should ignore its parent’s placement settings.
 */
public class ElementConfig extends Config<ElementConfig> {

	private boolean ignoreFatherPlacement = false;
	private boolean textureCenteredPosition = false;
	private int xPlacementFix = 0;
	private int yPlacementFix = 0;


	/**
	 * Protected constructor to prevent direct instantiation.
	 */
	protected ElementConfig() {}

	/**
	 * Creates a new instance of {@code ElementConfig}.
	 *
	 * @return A new instance of {@code ElementConfig}.
	 */
	public static ElementConfig create() {
		return new ElementConfig();
	}

	/**
	 * Checks if the element is set to ignore its parent's placement.
	 *
	 * @return {@code true} if the element should ignore parent placement; {@code false} otherwise.
	 */
	public boolean isIgnoreFatherPlacement() {
		return ignoreFatherPlacement;
	}

	/**
	 * Sets whether the element should ignore its parent's placement settings.
	 *
	 * @param ignoreFatherPlacement A boolean value indicating if the element should ignore parent placement.
	 * @return The current instance of {@code ElementConfig} for method chaining.
	 */
	public ElementConfig setIgnoreFatherPlacement(boolean ignoreFatherPlacement) {
		this.ignoreFatherPlacement = ignoreFatherPlacement;
		return this;
	}

	public boolean isTextureCenteredPosition() {
		return textureCenteredPosition;
	}

	public ElementConfig setTextureCenteredPosition(boolean textureCenteredPosition) {
		this.textureCenteredPosition = textureCenteredPosition;
		return this;
	}

	public int getYPlacementFix() {
		return yPlacementFix;
	}

	public ElementConfig setYPlacementFix(int yPlacementFix) {
		this.yPlacementFix = yPlacementFix;
		return this;
	}

	public int getXPlacementFix() {
		return xPlacementFix;
	}

	public ElementConfig setXPlacementFix(int xPlacementFix) {
		this.xPlacementFix = xPlacementFix;
		return this;
	}
}
