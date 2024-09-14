package deus.guilib.element.config.derivated;

import deus.guilib.element.config.Config;

/**
 * Configuration class for elements that extends {@link Config}.
 * Provides additional settings specific to elements, such as whether an element should ignore its parent's placement settings.
 */
public class ElementConfig extends Config<ElementConfig> {

	private boolean ignoreParentPlacement = false;
	private boolean centered = false;
	private int xPlacementOffset = 0;
	private int yPlacementOffset = 0;

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
	public boolean isIgnoredParentPlacement() {
		return ignoreParentPlacement;
	}

	/**
	 * Sets whether the element should ignore its parent's placement settings.
	 *
	 * @param ignoreParentPlacement A boolean value indicating if the element should ignore parent placement.
	 * @return The current instance of {@code ElementConfig} for method chaining.
	 */
	public ElementConfig setIgnoreParentPlacement(boolean ignoreParentPlacement) {
		this.ignoreParentPlacement = ignoreParentPlacement;
		return this;
	}

	/**
	 * Checks if the texture should be centered on the element.
	 *
	 * @return {@code true} if the texture should be centered; {@code false} otherwise.
	 */
	public boolean isCentered() {
		return centered;
	}

	/**
	 * Sets whether the texture should be centered on the element.
	 *
	 * @param centered A boolean value indicating if the texture should be centered.
	 * @return The current instance of {@code ElementConfig} for method chaining.
	 */
	public ElementConfig setCentered(boolean centered) {
		this.centered = centered;
		return this;
	}

	/**
	 * Gets the Y offset for placement adjustments.
	 *
	 * @return The Y offset for placement adjustments.
	 */
	public int getYPlacementOffset() {
		return yPlacementOffset;
	}

	/**
	 * Sets the Y offset for placement adjustments.
	 *
	 * @param yPlacementOffset The Y offset to set.
	 * @return The current instance of {@code ElementConfig} for method chaining.
	 */
	public ElementConfig setYPlacementOffset(int yPlacementOffset) {
		this.yPlacementOffset = yPlacementOffset;
		return this;
	}

	/**
	 * Gets the X offset for placement adjustments.
	 *
	 * @return The X offset for placement adjustments.
	 */
	public int getXPlacementOffset() {
		return xPlacementOffset;
	}

	/**
	 * Sets the X offset for placement adjustments.
	 *
	 * @param xPlacementOffset The X offset to set.
	 * @return The current instance of {@code ElementConfig} for method chaining.
	 */
	public ElementConfig setXPlacementOffset(int xPlacementOffset) {
		this.xPlacementOffset = xPlacementOffset;
		return this;
	}

	/**
	 * Creates a clone of the current configuration.
	 *
	 * @return A new {@code ElementConfig} instance with the same settings.
	 */
	public ElementConfig clone() {
		return new ElementConfig()
			.setIgnoreParentPlacement(this.ignoreParentPlacement)
			.setCentered(centered)
			.setXPlacementOffset(this.xPlacementOffset)
			.setYPlacementOffset(this.yPlacementOffset);
	}

	@Override
	public String toString() {
		return "ElementConfig{" +
			"ignoreParentPlacement=" + ignoreParentPlacement +
			", textureCenteredPosition=" + centered +
			", xPlacementOffset=" + xPlacementOffset +
			", yPlacementOffset=" + yPlacementOffset +
			'}';
	}
}
