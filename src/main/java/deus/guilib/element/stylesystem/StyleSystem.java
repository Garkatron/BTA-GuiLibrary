package deus.guilib.element.stylesystem;

import deus.guilib.GuiLib;
import deus.guilib.interfaces.element.IElement;
import deus.guilib.interfaces.element.IStylable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StyleSystem {

	private static Map<String, Object> default_styles = new HashMap<>();
	private static Map<String, Object> styles = new HashMap<>();

	public static Map<String, Object> loadFrom(String path) {
		return YAMLProcessor.read(path);
	}

	public static void loadExtern(String path) {
		styles = simplifyMap(loadFrom(path));
	}

	public static Map<String, Object> simplifyMap(Map<String, Object> rawStyle) {
		// Obtener las propiedades compartidas
		Map<String, Object> sharedProperties = (Map<String, Object>) rawStyle.getOrDefault("SharedProperties", new HashMap<>());

		// Obtener la lista de 'Select'
		List<Map<String, Object>> selectList = (List<Map<String, Object>>) rawStyle.getOrDefault("Select", List.of());

		// Crear el mapa final
		Map<String, Object> finalMap = new HashMap<>();

		// Iterar sobre la lista de selects
		for (Map<String, Object> select : selectList) {
			String at = (String) select.getOrDefault("at", ""); // Obtener el valor de 'At'

			// Crear un nuevo mapa para el select que combine las propiedades específicas de 'At'
			Map<String, Object> combinedSelect = new HashMap<>(select); // Copiar las propiedades de 'select'

			combinedSelect.forEach((k,t)->{
				if(t instanceof String) {
					((String) t).toLowerCase();
				}
			});

			// Eliminar 'At' para que no se repita en el mapa combinado
			combinedSelect.remove("at");

			// Agregar 'At' como la clave en 'finalMap' y el mapa combinado como su valor
			finalMap.put(at, combinedSelect); // Agregar el select al mapa final con 'At' como clave
		}


		return finalMap;
	}
	public static void loadDefaults() {
		try {
			Map<String, Object> styles = YAMLProcessor.read(StyleSystem.class.getResourceAsStream("/assets/textures/gui/styles/default.yaml"));

			if (styles.containsKey("Select")) {
				default_styles = simplifyMap(styles);
				GuiLib.LOGGER.info("Successful charged default styles: {}", default_styles);
				GuiLib.LOGGER.info("Elements styles: {}", default_styles.get("Elements"));
			} else {
				throw new IllegalArgumentException("You will need to add Select:");
			}
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error when loading default styles", e);
		}
	}


	private static Map<String, Object> getStyleOrDefault(String styleName) {

		Map<String, Object> empty = new HashMap<>();
		Map<String, Object> value = (Map<String, Object>) styles.getOrDefault(styleName, default_styles.get(styleName));

		if (value==null)
			return empty;

		return value;
		//throw new IllegalArgumentException("You will need to add -> Elements:");
	}


	public static void applyStyles(IElement mainNode) {
		GuiLib.LOGGER.info("Applying styles to main node: {}", mainNode.getClass().getSimpleName());
		for (IElement child : mainNode.getChildren()) {

			if (child instanceof IStylable) {

				System.out.println(child.getClass().getSimpleName());
				((IStylable) child).applyStyle(getStyleOrDefault(child.getClass().getSimpleName()));
			}
			if (!child.getChildren().isEmpty()) {
				applyStyles(child);
			}
		}
	}


}
