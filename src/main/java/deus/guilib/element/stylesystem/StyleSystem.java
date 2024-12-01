package deus.guilib.element.stylesystem;

import deus.guilib.GuiLib;
import deus.guilib.interfaces.element.INode;
import deus.guilib.interfaces.element.IRootNode;
import deus.guilib.interfaces.element.IStylable;
import deus.guilib.resource.Texture;
import deus.guilib.routing.Page;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

public class StyleSystem {

	private static Map<String, Object> default_styles = new HashMap<>();

	public static Map<String, Object> loadFrom(String path) {
		return simplifyMap(YAMLProcessor.read(path));
	}

	public static void loadExtern(Page page, InputStream stream) {
		page.styles = simplifyMap(YAMLProcessor.read(stream));
	}

	public static void loadExtern(Page page, String path) {
		page.styles = simplifyMap(loadFrom(path));
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

			combinedSelect.forEach((k, t) -> {
				if (t instanceof String) {
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

	public static String parseId(String id) {
		return id.replace("#", "");
	}
	public static String parseClass(String id) {
		return id.replace(".", "");
	}
	public static String[] parseArrowSelector(String id) {return id.split(">"); }

	public static void applyBySelector(Map<String, Object> styles, INode child) {
		if (child instanceof IStylable stylableChild) {

			stylableChild.applyStyle(getStyleOrDefault(styles, child.getClass().getSimpleName()));

			if (!child.getSid().isEmpty() && styles.containsKey("#" + child.getSid())) {
				stylableChild.applyStyle(getStyleOrDefault(styles,"#" + child.getSid()));
			}

			if (!child.getGroup().isEmpty() && styles.containsKey("." + child.getGroup())) {
				stylableChild.applyStyle(getStyleOrDefault(styles,"." + child.getGroup()));
			}
		}
	}


	public static void loadImagesFromStyles(Map<String, Object> styles) {
		styles.entrySet().stream()
			.filter(entry -> "backgroundImage".equals(entry.getKey()) && entry.getValue() instanceof String)
			.forEach(entry -> {
				String url = (String) entry.getValue();
				entry.setValue(new Texture(StyleParser.parseURL(url), 0, 0));
			});
		System.out.println(styles);
	}


	public static void loadDefaults() {
		try {
			Map<String, Object> styles = YAMLProcessor.read(StyleSystem.class.getResourceAsStream("/assets/textures/gui/styles/default.yaml"));

			if (styles.containsKey("Select")) {
				default_styles = simplifyMap(styles);
			} else {
				throw new IllegalArgumentException("You will need to add Select:");
			}
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error when loading default styles", e);
		}
	}


	private static Map<String, Object> getStyleOrDefault(Map<String, Object> styles, String styleName) {

		Map<String, Object> empty = new HashMap<>();
		Map<String, Object> value = (Map<String, Object>) styles.getOrDefault(styleName, default_styles.get(styleName));

		if (value == null)
			return empty;

		return value;
		//throw new IllegalArgumentException("You will need to add -> Elements:");
	}

	public static void applyStylesByIterNodes(@NotNull Map<String, Object> styles, @NotNull IRootNode root) {
		styles.forEach((key, value) -> {
			if (!(value instanceof Map)) {
				throw new IllegalArgumentException("El valor de cada clave debe ser un mapa de estilos.");
			}


			if (key.startsWith(".")) {
				root.getNodeByGroup(key.substring(1)).forEach(node -> {
					if (node instanceof IStylable) {
						((IStylable) node).applyStyle((Map<String, Object>) value);
					}
				});
			}

			else if (key.startsWith("#")) {
				String id = parseId(key);
				INode nodeById = root.getNodeById(id);
				if (nodeById instanceof IStylable) {
					((IStylable) nodeById).applyStyle((Map<String, Object>) value);
				}
			}

			else {
				root.getNodeByClass(key).forEach(node -> {
					if (node instanceof IStylable) {
						((IStylable) node).applyStyle((Map<String, Object>) value);
					}
				});
			}
		});
	}


	public static void applyStyles(Map<String, Object> styles, INode mainNode) {
		if (mainNode == null) {
			return;
		}

		for (INode child : mainNode.getChildren()) {

			applyBySelector(styles, child);


			if (child.getChildren() != null && !child.getChildren().isEmpty()) {
				applyStyles(styles, child);
			}
		}
	}


}
