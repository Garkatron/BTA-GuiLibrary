package deus.guilib.resource;

import deus.guilib.element.elements.Button;
import deus.guilib.element.elements.PlayerInventory;
import deus.guilib.element.elements.Panel;
import deus.guilib.element.elements.Slot;

import java.util.HashMap;
import java.util.Map;

public class ThemeManager {

	private final Map<String, Map<String, String>> themes = new HashMap<>();
	private static final ThemeManager instance = new ThemeManager();

	private ThemeManager() {
		addTheme("VANILLA", new HashMap<String, String>() {{
			put(PlayerInventory.class.getSimpleName(), "assets/textures/gui/Inventory.png");
			put(Slot.class.getSimpleName(), "assets/textures/gui/Slot.png");
			put(Panel.class.getSimpleName(), "assets/textures/gui/Panel.png");

			// Button
			put(Button.class.getSimpleName(), "assets/textures/gui/Button.png");
			put(Button.class.getSimpleName() + ".pressed", "assets/textures/gui/PressedButton.png");
			put(Button.class.getSimpleName() + ".hover", "assets/textures/gui/HoverButton.png");
		}});
		addTheme("DARK", new HashMap<String, String>() {{
			put(PlayerInventory.class.getSimpleName(), "assets/textures/gui/themes/dark/inventory/Inventory.png");
			put(Slot.class.getSimpleName(), "assets/textures/gui/themes/dark/inventory/Slot.png");
		}});
	}

	public static ThemeManager getInstance() {
		return instance;
	}
	public void addTheme(String name, Map<String, String> properties) {
		themes.put(name, properties);
	}
	public Map<String, String> getProperties(String themeName) {
		return themes.getOrDefault(themeName, new HashMap<>());
	}
}
