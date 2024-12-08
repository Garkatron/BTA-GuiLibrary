package deus.guilib.nodes.stylesystem;

import deus.guilib.interfaces.nodes.INode;
import deus.guilib.interfaces.nodes.IStylable;
import deus.guilib.nodes.Root;
import deus.guilib.resource.Texture;
import deus.guilib.guimanagement.routing.Page;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StyleSystem {

	private static final Map<String, Object> DEFAULT_STYLES = loadDefaults();

	/**
	 * Loads a style from the specified path.
	 *
	 * @param path The path to the style file.
	 * @return A map containing the styles.
	 */
	public static Map<String, Object> loadFrom(String path) {
		return simplifyMap(YAMLProcessor.read(path));
	}

	/**
	 * Loads a style from the specified path and merges it with default styles.
	 *
	 * @param path The path to the style file.
	 * @return A map containing the merged styles.
	 */
	public static Map<String, Object> loadFromWithDefault(String path) {
		Map<String, Object> default_styles = new HashMap<>();
		insertDefaultStyles(default_styles);

		return mergeStyles(default_styles, simplifyMap(YAMLProcessor.read(path)));
	}

	/**
	 * Merges two style maps, with the second map overriding the first.
	 *
	 * @param baseStyles    The base styles to merge.
	 * @param overrideStyles The styles to override the base styles.
	 * @return A map containing the merged styles.
	 */
	public static Map<String, Object> mergeStyles(Map<String, Object> baseStyles, Map<String, Object> overrideStyles) {
		Map<String, Object> merged = new HashMap<>(baseStyles);

		overrideStyles.forEach((key, value) -> {
			if (value instanceof Map && merged.get(key) instanceof Map) {
				merged.put(key, mergeStyles((Map<String, Object>) merged.get(key), (Map<String, Object>) value));
			} else {
				merged.put(key, value);
			}
		});
		return merged;
	}

	/**
	 * Inserts the default styles into the given style map.
	 *
	 * @param style The style map to insert the default styles into.
	 */
	public static void insertDefaultStyles(Map<String, Object> style) {
		style.putAll(DEFAULT_STYLES);
	}

	/**
	 * Loads external styles from an input stream and applies them to a page.
	 *
	 * @param page  The page to apply the styles to.
	 * @param stream The input stream containing the style data.
	 */
	public static void loadExtern(Page page, InputStream stream) {
		page.styles = simplifyMap(YAMLProcessor.read(stream));
	}

	/**
	 * Loads external styles from a file path and applies them to a page.
	 *
	 * @param page The page to apply the styles to.
	 * @param path The file path to the style data.
	 */
	public static void loadExtern(Page page, String path) {
		page.styles = simplifyMap(loadFrom(path));
	}

	/**
	 * Loads external styles from an input stream, merges with default styles, and applies them to a page.
	 *
	 * @param page  The page to apply the styles to.
	 * @param stream The input stream containing the style data.
	 */
	public static void loadExternWithDefault(Page page, InputStream stream) {
		Map<String, Object> default_styles = new HashMap<>();
		insertDefaultStyles(default_styles);
		page.styles = mergeStyles(default_styles, simplifyMap(YAMLProcessor.read(stream)));
	}

	/**
	 * Loads external styles from a file path, merges with default styles, and applies them to a page.
	 *
	 * @param page The page to apply the styles to.
	 * @param path The file path to the style data.
	 */
	public static void loadExternWithDefault(Page page, String path) {
		Map<String, Object> default_styles = new HashMap<>();
		insertDefaultStyles(default_styles);

		page.styles = mergeStyles(default_styles, simplifyMap(YAMLProcessor.read(path)));
	}

	/**
	 * Simplifies the raw style map by extracting shared properties and processing 'Select' entries.
	 *
	 * @param rawStyle The raw style map to simplify.
	 * @return A simplified style map.
	 */
	public static Map<String, Object> simplifyMap(Map<String, Object> rawStyle) {
		Map<String, Object> sharedProperties = (Map<String, Object>) rawStyle.getOrDefault("SharedProperties", new HashMap<>());
		List<Map<String, Object>> selectList = (List<Map<String, Object>>) rawStyle.getOrDefault("Select", List.of());

		Map<String, Object> finalMap = new HashMap<>();

		for (Map<String, Object> select : selectList) {
			String at = (String) select.getOrDefault("at", "");
			Map<String, Object> combinedSelect = new HashMap<>(select);
			combinedSelect.remove("at");

			finalMap.put(at, combinedSelect);
		}

		return finalMap;
	}

	/**
	 * Applies styles to a child node based on selectors.
	 *
	 * @param styles The map of styles to apply.
	 * @param child  The child node to apply styles to.
	 */
	public static void applyBySelector(Map<String, Object> styles, INode child) {
		if (child instanceof IStylable stylableChild) {
			System.out.println(child.getClass().getSimpleName());
			stylableChild.applyStyle(getStyleOrDefault(styles, child.getClass().getSimpleName()));

			if (!child.getSid().isEmpty() && styles.containsKey("#" + child.getSid())) {
				stylableChild.applyStyle(getStyleOrDefault(styles,"#" + child.getSid()));
			}

			if (!child.getGroup().isEmpty() && styles.containsKey("." + child.getGroup())) {
				stylableChild.applyStyle(getStyleOrDefault(styles,"." + child.getGroup()));
			}
		}
	}

	/**
	 * Loads images from styles and converts them into Texture objects.
	 *
	 * @param styles The map of styles to process.
	 */
	public static void loadImagesFromStyles(Map<String, Object> styles) {
		styles.entrySet().stream()
			.filter(entry ->
				("backgroundImage".equals(entry.getKey()) || "progressBarFullBackground".equals(entry.getKey()))
					&& entry.getValue() instanceof String)
			.forEach(entry -> {
				String url = (String) entry.getValue();
				entry.setValue(new Texture(StyleParser.parseURL(url), 0, 0));
			});
	}

	/**
	 * Loads the default styles from the resources.
	 *
	 * @return A map containing the default styles.
	 */
	public static Map<String, Object> loadDefaults() {
		try {
			Map<String, Object> styles = YAMLProcessor.read(StyleSystem.class.getResourceAsStream("/assets/textures/gui/styles/default.yaml"));

			if (styles.containsKey("Select")) {
				return simplifyMap(styles);
			} else {
				throw new IllegalArgumentException("You will need to add Select:");
			}
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error when loading default styles", e);
		}
	}

	/**
	 * Retrieves a style from the map or returns a default style if not found.
	 *
	 * @param styles    The map of styles to search.
	 * @param styleName The name of the style to retrieve.
	 * @return The found style or an empty map if not found.
	 */
	private static Map<String, Object> getStyleOrDefault(Map<String, Object> styles, String styleName) {

		Map<String, Object> empty = new HashMap<>();
		Map<String, Object> value = (Map<String, Object>) styles.getOrDefault(styleName, DEFAULT_STYLES.get(styleName));

		if (value == null)
			return empty;

		return value;
	}

	/**
	 * Applies styles by iterating over nodes in the root element.
	 *
	 * @param styles The styles to apply.
	 * @param root   The root node containing the child nodes.
	 */
	public static void applyStylesByIterNodes(@NotNull Map<String, Object> styles, @NotNull Root root) {
		styles.forEach((key, value) -> {
			if (!(value instanceof Map)) {
				throw new IllegalArgumentException("The value of each key must be a map of styles.");
			}

			System.out.println(key + "|" + value);

			if (key.startsWith(".")) {
				root.getNodeByGroup(key.substring(1)).forEach(node -> {
					if (node instanceof IStylable) {
						((IStylable) node).applyStyle(
							mergeStyles(((IStylable) node).getStyle(),(Map<String, Object>) value)
						);
					}
				});
			}

			else if (key.startsWith("#")) {
				String id = StyleParser.parseId(key);
				INode nodeById = root.getNodeById(id);
				if (nodeById instanceof IStylable) {
					((IStylable) nodeById).applyStyle(
						mergeStyles(((IStylable) nodeById).getStyle(),(Map<String, Object>) value)
					);
				}
			}

			else {
				root.getNodeByClass(key).forEach(node -> {
					if (node instanceof IStylable) {
						((IStylable) node).applyStyle(
							mergeStyles(((IStylable) node).getStyle(),(Map<String, Object>) value)
						);
					}
				});
			}
		});
	}

	/**
	 * Recursively applies styles to the children of the main node.
	 *
	 * @param styles    The map of styles to apply.
	 * @param mainNode  The main node to apply styles to.
	 */
	public static void applyStyles(Map<String, Object> styles, INode mainNode) {
		if (mainNode == null) {
			return;
		}

		for (INode child : mainNode.getChildren()) {
			if (child.getChildren() != null && !child.getChildren().isEmpty()) {
				applyStyles(styles, child);
			}
		}
	}
}
