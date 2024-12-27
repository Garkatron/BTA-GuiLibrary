package deus.guilib.nodes.stylesystem;

import deus.guilib.GuiLib;
import deus.guilib.util.rendering.TextureMode;
import deus.guilib.util.rendering.TextureProperties;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static deus.guilib.nodes.stylesystem.StyleSystem.yaml_css_selectors;

public class StyleParser {

	/**
	 * Parses a style file and returns the styles in a map.
	 *
	 * @param path The path to the style file.
	 * @return A map containing the styles.
	 */
	public static Map<String, String> parse(String path) {
		Map<String, String> styles = new HashMap<>();

		return styles;


	}

	public static TextureMode parseTextureMode(String string) {
		return switch (string.toUpperCase()) {
			case "STRETCH" -> TextureMode.STRETCH;
			case "NINE_SLICE" -> TextureMode.NINE_SLICE;
			case "TILE" -> TextureMode.TILE;
			default -> throw new IllegalArgumentException("Unknown TextureMode: " + string);
		};
	}


	public static TextureProperties.Border parseBorderObject(Map<String, Integer> map) {
		return new TextureProperties.Border(
			map.getOrDefault("top", 0),
			map.getOrDefault("bottom", 0),
			map.getOrDefault("left", 0),
			map.getOrDefault("right", 0)
		);
	}


	/**
	 * Parses a pixel value (e.g., 10px, 20%) and returns the corresponding integer value.
	 *
	 * @param value The string value to be parsed.
	 * @return The pixel value as an integer.
	 */
	public static int parsePixels(String value) {
		if (value.endsWith("%")) {
			return 0;
		} else if (value.endsWith("px")) {
			return Integer.parseInt(value.replace("px", "").trim());
		}
		return 0;
	}

	/**
	 * Parses an ID selector and removes the '#' symbol.
	 *
	 * @param id The ID string to be parsed.
	 * @return The parsed ID string.
	 */
	public static String parseId(String id) {
		return id.replace("@", "");
	}

	/**
	 * Parses a class selector and removes the '.' symbol.
	 *
	 * @param id The class string to be parsed.
	 * @return The parsed class string.
	 */
	public static String parseClass(String id) {
		return id.replace(".", "");
	}

	/**
	 * Parses a selector containing '>' symbols (child selector) and splits it.
	 *
	 * @param id The selector string to be parsed.
	 * @return An array containing the individual selectors.
	 */
	public static String[] parseArrowSelector(String id) {
		return id.split(">");
	}

	/**
	 * Parses a URL and removes the 'url()' part.
	 *
	 * @param url The URL string to be parsed.
	 * @return The parsed URL string.
	 */
	public static String parseURL(String url) {
		return url.trim();
	}

	/**
	 * Parses a border style string (e.g., '2px red') and returns a BorderStyle object.
	 *
	 * @param params The border style string to be parsed.
	 * @return A BorderStyle object containing the parsed values.
	 */
	public static BorderStyle parseBorder(String params) {
		String[] parts = params.split(" ");

		int borderWidth = 0;
		int borderColor = 0;

		if (parts.length > 0) {
			borderWidth = StyleParser.parsePixels(parts[0]);
		}
		if (parts.length > 1) {
			borderColor = StyleParser.parseColorToARGB(parts[1]);
		}

		return new BorderStyle(borderColor, borderWidth);
	}

	/**
	 * Parses a relative number (percentage) and returns the corresponding integer value.
	 *
	 * @param n The string value representing the relative number.
	 * @return The parsed integer value.
	 */
	public static int parseRelativeNumber(String n) {
		if (n.endsWith("%")) {
			return Integer.parseInt(n.substring(0, n.length() - 1));
		}
		return 0;
	}

	/**
	 * Parses a color in hexadecimal format (e.g., "#FF5733") and converts it to ARGB format.
	 *
	 * @param hexColor The hexadecimal color string.
	 * @return The ARGB integer value.
	 */
	public static int parseColorToARGB(String hexColor) {
		if (hexColor.startsWith("#")) {
			hexColor = hexColor.substring(1);
		}

		if (hexColor.length() == 6) {
			hexColor = "FF" + hexColor;
		}

		return (int) Long.parseLong(hexColor, 16);
	}

	public static List<String> parseHierarchySelectors(String input) {
		String[] parts = input.split(yaml_css_selectors.get("directParent"));

		return Arrays.stream(parts)
			.flatMap(part -> {
				String cmA = yaml_css_selectors.get("commonAncestor");
				if (part.contains(cmA)) {
					String beforeParenthesis = part.substring(0, part.indexOf(cmA)).trim();
					String afterParenthesis = part.substring(part.indexOf(cmA) + 1).replace(cmA, "").trim();
					return Stream.of(beforeParenthesis + cmA, afterParenthesis);
				} else {
					return Stream.of(part.trim());
				}
			})
			.collect(Collectors.toList());
	}


	/*

		public static List<String> parseHierarchySelectors(String input) {
		String[] parts = input.split(">");

		return Arrays.stream(parts)
			.flatMap(part -> {
				if (part.contains("(")) {
					String beforeParenthesis = part.substring(0, part.indexOf("(")).trim();
					String afterParenthesis = part.substring(part.indexOf("(") + 1).replace(")", "").trim();
					return Stream.of(beforeParenthesis, "...", afterParenthesis);
				} else {
					return Stream.of(part.trim());
				}
			})
			.collect(Collectors.toList());
	}

	 */


}
