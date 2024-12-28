package deus.builib.nodes.stylesystem;

import java.util.HashMap;
import java.util.Map;

public class StyleMap {

	private Map<String, Object> styles = new HashMap<>();

	// Constructor
	public StyleMap() {}

	// Método para obtener los estilos (simulado)
	public Map<String, Object> getStyles() {
		return this.styles;
	}

	/**
	 * Merges the current styles with the provided styles, with the provided styles overriding the current ones.
	 *
	 * @param overrideStyles The styles to override the current styles.
	 * @return The current instance of StyleMap for method chaining.
	 */
	public StyleMap mergeStyles(StyleMap overrideStyles) {
		if (overrideStyles == null || overrideStyles.getStyles() == null) return this;

		overrideStyles.getStyles().forEach((key, value) -> {
			if (value instanceof Map && this.styles.get(key) instanceof Map) {
				// Merge sub-maps recursively
				this.styles.put(key, mergeSubMaps(
					(Map<String, Object>) this.styles.get(key),
					(Map<String, Object>) value
				));
			} else {
				// Override or add the key-value pair
				this.styles.put(key, value);
			}
		});

		return this;
	}

	/**
	 * Merges two sub-maps recursively.
	 *
	 * @param baseMap The base sub-map.
	 * @param overrideMap The overriding sub-map.
	 * @return The merged sub-map.
	 */
	private Map<String, Object> mergeSubMaps(Map<String, Object> baseMap, Map<String, Object> overrideMap) {
		Map<String, Object> merged = new HashMap<>(baseMap);

		overrideMap.forEach((key, value) -> {
			if (value instanceof Map && merged.get(key) instanceof Map) {
				merged.put(key, mergeSubMaps(
					(Map<String, Object>) merged.get(key),
					(Map<String, Object>) value
				));
			} else {
				merged.put(key, value);
			}
		});

		return merged;
	}
}
