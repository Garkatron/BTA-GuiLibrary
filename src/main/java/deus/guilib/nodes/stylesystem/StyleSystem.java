package deus.guilib.nodes.stylesystem;

import deus.guilib.GuiLib;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.interfaces.nodes.IStylable;
import deus.guilib.nodes.Root;
import deus.guilib.resource.Texture;
import deus.guilib.guimanagement.routing.Page;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StyleSystem {

	public static boolean useDefaultsStyles = true;
	private static final Map<String, Object> DEFAULT_STYLES = loadDefaults();

	public static final Map<String, String> yaml_css_selectors = new HashMap<>();

	static {
		yaml_css_selectors.put("id","$");
		yaml_css_selectors.put("group",".");
		yaml_css_selectors.put("directParent",">");
		yaml_css_selectors.put("commonAncestor","(");
	}

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
	 * Loads a style from the specified path and merges it with default styles.
	 *
	 * @param path The path to the style file. Example: /assets/modid/path/to/your/gui.yaml
	 * @return A map containing the merged styles.
	 */
	public static Map<String, Object> loadFromAssets(Class<?> clazz, String path) {
		Map<String, Object> default_styles = new HashMap<>();
		insertDefaultStyles(default_styles);
		return mergeStyles(default_styles, simplifyMap(YAMLProcessor.read(clazz.getResourceAsStream(path))));
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
		GuiLib.LOGGER.info("[Use default styles]: {}",useDefaultsStyles);
		if (useDefaultsStyles) {
			style.putAll(DEFAULT_STYLES);
		}
	}

	/**
	 * Loads external styles from an input stream and applies them to a page.
	 *
	 * @param page  The page to apply the styles to.
	 * @param stream The input stream containing the style data.
	 */
	public static void loadFrom(Page page, InputStream stream) {
		page.styles = simplifyMap(YAMLProcessor.read(stream));
	}

	/**
	 * Loads external styles from a file path and applies them to a page.
	 *
	 * @param page The page to apply the styles to.
	 * @param path The file path to the style data.
	 */
	public static void loadFrom(Page page, String path) {
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

		// GuiLib.LOGGER.info("Shared props: {}", sharedProperties);

		List<Map<String, Object>> selectList = (List<Map<String, Object>>) rawStyle.getOrDefault("Select", List.of());

		Map<String, Object> finalMap = new HashMap<>();

		for (Map<String, Object> select : selectList) {
			String at = (String) select.getOrDefault("at", "");
			Map<String, Object> combinedSelect = new HashMap<>(select);
			combinedSelect.remove("at");

			finalMap.put(at, mergeStyles(sharedProperties, combinedSelect));
		}

		return finalMap;
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
			Map<String, Object> styles = YAMLProcessor.read(StyleSystem.class.getResourceAsStream("/assets/guilib/yaml/default_styles.yaml"));

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

	public static void iterateSelectors(@NotNull Map<String, Object> styles, @NotNull Root root) {

		Map<String, Object> ordered = orderBySpecificity(styles);

		ordered.forEach((key, value) -> {
			// ? Parsing selectors
			List<String> selector = new ArrayList<>(StyleParser.parseHierarchySelectors(key)); // ? Mutable list
			Collections.reverse(selector);

			// ? Filter by selector
			String lastSelector = selector.get(0);

			// ? Get all nodes of the same type
			List<INode> nodes = getNodesBySelector(root, lastSelector);
			// * GuiLib.LOGGER.info("Nodes to filter: {}", nodes);

			// ? Reverse iterating
			for (int i = nodes.size() - 1; i >= 0; i--) {
				// ? Get his hierarchy
				List<String> r = getHierarchy(nodes.get(i));
				// ? Compare result with selector (If result is a sublist of hierarchy of this node)
				if (checkLists(r, selector)) {
					applyStyleIfStylable(nodes.get(i), value);
				}
			}
		});
	}

	// Utility method to apply style to nodes if they are stylable
	private static void applyStyleIfStylable(INode node, Object value) {
		if (node instanceof IStylable n) {
			n.applyStyle(mergeStyles(n.getStyle(), (Map<String, Object>) value));
		}
	}

	public static List<INode> getNodesBySelector(INode parent, String selector) {
		List<INode> nodes = new ArrayList<>();
		if (selector.startsWith(yaml_css_selectors.get("group"))) {
			nodes = parent.getNodeByGroup(selector.substring(1));

		} else if (selector.startsWith(yaml_css_selectors.get("id"))) {
			nodes.add(parent.getNodeById(selector.substring(1)));

		} else {
			nodes = parent.getNodeByClass(selector);
		}
		return nodes;

	}
	private static Map<String, String> cleanSelector(String selector) {
		Map<String, String> stuff = new HashMap<>();

		if (selector.endsWith(yaml_css_selectors.get("directParent")) || selector.endsWith(yaml_css_selectors.get("commonAncestor"))) {
			stuff.put("tag", selector.substring(0, selector.length() - 1).trim());
			stuff.put("selector", selector.substring(selector.length() - 1).trim());
		} else {
			stuff.put("tag", selector);
			stuff.put("selector", "");
		}

		return stuff;
	}



	public static boolean checkLists(List<String> l1, List<String> l2) {
		// System.out.println("List 1 size: " + l1.size());
		// System.out.println("List 2 size: " + l2.size());

		// ? Check size
		if (l2.size() > l1.size()) {
			GuiLib.LOGGER.error("List 2 is larger than List 1. Match is not possible.");
			return false;
		}

		// System.out.println(l1);
		// System.out.println(l2);

		// ? Iterate over `l1` to find matches with `l2`
		int l1Index = 0; // ? Pointer for l1
		for (int l2Index = 0; l2Index < l2.size(); l2Index++) {
			String elementFromL2 = l2.get(l2Index);
			// System.out.println("Matching l2 element: " + elementFromL2);

			if (elementFromL2.endsWith("(")) {
				// ? Extract the ancestor name before the "("
				String ancestor = elementFromL2.substring(0, elementFromL2.indexOf(yaml_css_selectors.get("commonAncestor"))).trim();
				// System.out.println("Searching for ancestor: " + ancestor);

				// ? Skip elements in l1 until ancestor is found
				boolean ancestorFound = false;
				while (l1Index < l1.size()) {
					List<String> tokens = Arrays.stream(l1.get(l1Index).split(":")).toList();
					if (tokens.contains(ancestor)) {
						ancestorFound = true;
						break;
					}
					l1Index++;
				}

				if (!ancestorFound) {
					// System.out.println("Ancestor not found in l1.");
					return false;
				}
			} else {
				// ? Normal matching logic
				if (l1Index >= l1.size()) {
					// System.out.println("Reached end of l1 without matching.");
					return false;
				}

				List<String> tokens = Arrays.stream(l1.get(l1Index).split(":")).toList();
				System.out.println(tokens);
				System.out.println(l2);
				// System.out.println("Comparing l1 element: \"" + l1.get(l1Index) + "\" with l2 element: \"" + elementFromL2 + "\"");

				if (!tokens.contains(elementFromL2)) {
					// System.out.println("No match for \"" + elementFromL2 + "\" in tokens.");
					return false;
				}
			}

			// ? Move to the next element in l1 for the next comparison
			l1Index++;
		}

		// ? If all elements of l2 are matched
		// System.out.println("All elements matched!");
		return true;
	}


	public static int[] calcSpecificity(String selector) {
		// ? Counters
		int idCount = 0;
		int classCount = 0;
		int elementCount = 0;

		// ? Regex
		String idPattern = yaml_css_selectors.get("id")+"\\w+";
		String classPattern = "\\"+yaml_css_selectors.get("group")+"\\w+";
		String elementPattern = "\\b[a-zA-Z][\\w-]*\\b";

		// ? Counter
		Pattern pattern = Pattern.compile(idPattern);
		Matcher matcher = pattern.matcher(selector);
		while (matcher.find()) {
			idCount++;
		}

		// ? Counter
		pattern = Pattern.compile(classPattern);
		matcher = pattern.matcher(selector);
		while (matcher.find()) {
			classCount++;
		}

		// ? Counter
		pattern = Pattern.compile(elementPattern);
		matcher = pattern.matcher(selector);
		while (matcher.find()) {
			elementCount++;
		}

		// ? Array with points
		return new int[]{idCount, classCount, elementCount};
	}


	public static Map<String, Object> orderBySpecificity(Map<String, Object> map) {
		return map.entrySet().stream()
			.sorted((entry1, entry2) -> {


				// ? Calculate specificity
				int[] sp1 = calcSpecificity(entry1.getKey());
				int[] sp2 = calcSpecificity(entry2.getKey());

				// ? Classify (Reversed)
				for (int i = 0; i < 3; i++) {
					if (sp1[i] > sp2[i]) {
						return 1; // -1
					} else if (sp1[i] < sp2[i]) {
						return -1; // 1
					}
				}
				return 0;
			})
			// ? Collect into a map, with key and value
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				Map.Entry::getValue,
				(e1, e2) -> e1, // ? Resolve conflicts
				LinkedHashMap::new // ? Linked list to preserve ordering
			));
	}


	public static List<String> getHierarchy(INode child) {
		if (child == null) {
			GuiLib.LOGGER.error("Invalid input parameters.");
			return new ArrayList<>();
		}

		List<String> parents = new ArrayList<>();
		INode current = child;

		while (current != null) {
			String composed = current.getClass().getSimpleName().trim().toLowerCase() + ":"+yaml_css_selectors.get("id") + current.getId() + ":"+yaml_css_selectors.get("group") + current.getGroup();
			parents.add(composed);
			current = current.getParent();
		}

		return parents;
	}
}
