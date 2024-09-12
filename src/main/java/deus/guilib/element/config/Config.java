package deus.guilib.element.config;

/**
 * Generic configuration class used for setting up and managing configuration options such as themes and placements.
 * Supports subclass-specific configurations using generics.
 *
 * @param <T> The type of the subclass extending {@code Config}.
 */
public class Config<T extends Config<T>> {
	private String theme = "VANILLA";
	private Placement childrenPlacement = Placement.NONE;
	private Placement placement = Placement.NONE;

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

	public Placement getPlacement() {
		return placement;
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

	public T setPlacement(Placement placement) {
		this.placement = placement;
		return (T) this;
	}

	/**
	 * Sets the theme for the config.
	 *
	 * @param theme The name of the theme to set.
	 * @return The current instance of the config for method chaining.
	 */
	public T setTheme(String theme) {
		this.theme = theme;
		return (T) this;
	}

	/**
	 * Returns the current theme name.
	 *
	 * @return The current theme name as a {@code String}.
	 */
	public String getTheme() {
		return theme;
	}
}
