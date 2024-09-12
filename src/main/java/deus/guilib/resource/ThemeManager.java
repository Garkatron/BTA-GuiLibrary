package deus.guilib.resource;

import deus.guilib.element.elements.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages GUI themes and their associated textures.
 * <p>
 * This class maintains a collection of themes, each with a mapping of element names to texture file paths.
 * It provides methods to add new themes and retrieve texture properties for a given theme.
 * </p>
 */
public class ThemeManager {

	private final Map<String, Map<String, String>> themes = new HashMap<>();
	private static final ThemeManager instance = new ThemeManager();

	/**
	 * Private constructor to initialize default themes.
	 * <p>
	 * Adds default themes including "VANILLA" and "DARK" with predefined texture mappings.
	 * </p>
	 */
	private ThemeManager() {
		addTheme("VANILLA", new HashMap<String, String>() {{

			put(PlayerInventory.class.getSimpleName(), "assets/textures/gui/Slot.png");

			put(Slot.class.getSimpleName(), "assets/textures/gui/Slot.png");

			put(Panel.class.getSimpleName(), "assets/textures/gui/Panel.png");

			put(Row.class.getSimpleName(), "assets/textures/gui/Row.png");
			put(Column.class.getSimpleName(), "assets/textures/gui/Column.png");

			put(Button.class.getSimpleName(), "assets/textures/gui/Button.png");

		}});
	}

	/**
	 * Gets the singleton instance of the ThemeManager.
	 * @return The single instance of ThemeManager.
	 */
	public static ThemeManager getInstance() {
		return instance;
	}

	/**
	 * Adds a new theme with specified properties.
	 * @param name The name of the theme.
	 * @param properties A map of element names to texture file paths for the theme.
	 */
	public void addTheme(String name, Map<String, String> properties) {
		themes.put(name, properties);
	}

	/**
	 * Retrieves the texture properties for a given theme.
	 * @param themeName The name of the theme.
	 * @return A map of element names to texture file paths. Returns an empty map if the theme is not found.
	 */
	public Map<String, String> getProperties(String themeName) {
		return themes.getOrDefault(themeName, new HashMap<>());
	}
}
