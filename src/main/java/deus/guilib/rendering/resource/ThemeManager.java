package deus.guilib.rendering.resource;

import deus.guilib.rendering.advanced.Button;
import deus.guilib.rendering.advanced.PlayerInventory;
import deus.guilib.rendering.base.Panel;
import deus.guilib.rendering.base.Slot;

import java.util.HashMap;
import java.util.Map;

public class ThemeManager {

	// Mapa que almacenará los temas, donde la clave es el nombre del tema y el valor es otro mapa de propiedades
	private final Map<String, Map<String, String>> themes = new HashMap<>();

	// Instancia única del Singleton
	private static final ThemeManager instance = new ThemeManager();

	// Constructor privado para evitar instanciación externa
	private ThemeManager() {
		// Inicializador para añadir temas predeterminados
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

	// Obtiene la instancia única del ThemeManager
	public static ThemeManager getInstance() {
		return instance;
	}

	// Añade un nuevo tema
	public void addTheme(String name, Map<String, String> properties) {
		themes.put(name, properties);
	}

	// Obtiene las propiedades de un tema específico
	public Map<String, String> getProperties(String themeName) {
		return themes.getOrDefault(themeName, new HashMap<>());
	}
}
