package deus.guilib.element.config;

/**
 * Generic configuration class used for setting up and managing configuration options such as themes and placements.
 * Supports subclass-specific configurations using generics.
 *
 * @param <T> The type of the subclass extending {@code Config}.
 */
public class Config<T extends Config<T>> {
	private Placement childrenPlacement = Placement.NONE;

	/**
	 * Creates a new instance of the specified subclass of {@code Config}.
	 *
	 * @param clazz The class of the config type to create.
	 * @param <T> The type of the config.
	 * @return A new instance of the specified config type.
	 * @throws RuntimeException if instantiation fails.
	 */
	public static <T extends Config<T>> T create(Class<T> clazz) {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to create config instance", e);
		}
	}

	/**
	 * Returns the current {@link Placement} value.
	 *
	 * @return The current {@code ChildrenPlacement}.
	 */
	public Placement getChildrenPlacement() {
		return childrenPlacement;
	}


	/**
	 * Sets the placement configuration.
	 *
	 * @param placement The {@link Placement} value to set.
	 * @return The current instance of the config for method chaining.
	 */
	public T setChildrenPlacement(Placement placement) {
		this.childrenPlacement = placement;
		return (T) this;
	}
}
