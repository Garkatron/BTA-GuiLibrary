package deus.builib.nodes.stylesystem;

import deus.builib.GuiLib;
import deus.builib.error.YAMLError;
import deus.builib.nodes.stylesystem.objects.BorderStyle;
import net.minecraft.client.render.texture.meta.gui.GuiTextureProperties;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static deus.builib.nodes.stylesystem.StyleSystem.yaml_css_selectors;

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



	public static GuiTextureProperties.Border parseBorderObject(Map<String, Integer> map) {
		return new GuiTextureProperties.Border(
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
		if (value == null || value.isEmpty()) {
			GuiLib.LOGGER.error(YAMLError.PIXEL_FORMAT.getMessage(), value);

			return 0;
		}
		if (value.endsWith("%")) {
			return 0;
		} else if (value.endsWith("px")) {
			String numericValue = value.replace("px", "").trim();
			if (!numericValue.isEmpty() && numericValue.matches("\\d+")) {
				return Integer.parseInt(numericValue);
			}
			GuiLib.LOGGER.error(YAMLError.NOT_NUMERIC_PIXEL_FORMAT.getMessage(), value);

			return 0;
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
		if (id == null || id.isEmpty()) {
			GuiLib.LOGGER.error(YAMLError.INVALID_ID.getMessage(), id);
			return "";
		}

		if (!id.startsWith(StyleSystem.yaml_css_selectors.get("id"))) {
			GuiLib.LOGGER.error(YAMLError.ID_NOT_STARTING_WITH_AT.getMessage(), id);
			return "";
		}

		return id.substring(1);
	}


	/**
	 * Parses a class selector and removes the '.' symbol.
	 *
	 * @param id The class string to be parsed.
	 * @return The parsed class string.
	 */
	public static String parseGroup(String id) {

		if (id == null || id.isEmpty()) {
			GuiLib.LOGGER.error(YAMLError.INVALID_GROUP.getMessage(), id);
			return "";
		}

		if (!id.startsWith(StyleSystem.yaml_css_selectors.get("group"))) {
			GuiLib.LOGGER.error(YAMLError.GROUP_NOT_STARTING_WITH_DOT.getMessage(), id);
			return "";
		}

		return id.substring(1);	}

	/**
	 * Parses a selector containing '>' symbols (child selector) and splits it.
	 *
	 * @param id The selector string to be parsed.
	 * @return An array containing the individual selectors.
	 */
	public static String[] parseArrowSelector(String id) {
		return id.split(StyleSystem.yaml_css_selectors.get("directParent"));
	}

	/**
	 * Parses a URL and removes the 'url()' part.
	 *
	 * @param url The URL string to be parsed.
	 * @return The parsed URL string.
	 */
	public static String parseFileURL(String url) {
		if (url == null || url.isBlank()) {
			GuiLib.LOGGER.error(YAMLError.FILE_PATH_EMPTY.getMessage(), url);
			return "";
		}

		String trimmedUrl = url.trim();

		try {
			Paths.get(trimmedUrl);
		} catch (InvalidPathException e) {

			GuiLib.LOGGER.error(YAMLError.FILE_PATH_NOT_VALID.getMessage(), url);

		}

		return trimmedUrl;
	}

	/**
	 * Parses a border style string (e.g., '2px red') and returns a BorderStyle object.
	 *
	 * @param params The border style string to be parsed.
	 * @return A BorderStyle object containing the parsed values.
	 */
	public static BorderStyle parseBorder(String params) {
		BorderStyle def = new BorderStyle(0,0);;
		if (params == null || params.isBlank()) {
			GuiLib.LOGGER.error(YAMLError.INVALID_BORDER.getMessage(), params);
			return def;
		}

		String[] parts = params.trim().split(" ");

		int borderWidth = getBorderWidth(parts);

		String borderColorPart = parts[1];
		if (!borderColorPart.matches("^(0x)?[0-9a-fA-F]{6}$")) {
			GuiLib.LOGGER.error(YAMLError.INVALID_HEX_COLOR_FORMAT.getMessage(), params);
			return def;
		}

		int borderColor = StyleParser.parseColorToARGB(borderColorPart);

		return new BorderStyle(borderColor, borderWidth);
	}

	private static int getBorderWidth(String[] parts) {
		if (parts.length != 2) {
			GuiLib.LOGGER.error(YAMLError.INVALID_BORDER_FORMAT.getMessage(), Arrays.toString(parts));
			return 0;
		}

		String borderWidthPart = parts[0];
		if (!borderWidthPart.endsWith("px")) {
			GuiLib.LOGGER.error(YAMLError.INVALID_BORDER_FORMAT.getMessage(), borderWidthPart);
			return 0;
		}

		int borderWidth;
		try {
			borderWidth = Integer.parseInt(borderWidthPart.replace("px", "").trim());
		} catch (NumberFormatException e) {
			GuiLib.LOGGER.error(YAMLError.INVALID_BORDER_FORMAT.getMessage(), borderWidthPart);
			GuiLib.LOGGER.error(String.valueOf(e));
			return 0;

		}
		return borderWidth;
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
		if (hexColor == null || hexColor.isBlank()) {
			GuiLib.LOGGER.error(YAMLError.INVALID_HEX_COLOR_FORMAT.getMessage(), "null or empty");
			return 0;
		}

		if (hexColor.startsWith("#")) {
			hexColor = hexColor.substring(1);
		} else if (hexColor.startsWith("0x")) {
			hexColor = hexColor.substring(2);
		}

		if (hexColor.length() == 6) {
			hexColor = "FF" + hexColor; // Asumir opacidad completa si no está especificada
		} else if (hexColor.length() != 8) {
			GuiLib.LOGGER.error("[YAML] Invalid color format. Expected a hexadecimal color code (e.g., #RRGGBB or #RRGGBBAA), check yours: {}", hexColor);
			return 0;
		}

		if (!hexColor.matches("^[0-9a-fA-F]{8}$")) {
			GuiLib.LOGGER.error("[YAML] Invalid color format. Expected a hexadecimal color code (e.g., #RRGGBB or #RRGGBBAA), check yours: {}", hexColor);
			return 0;
		}

		try {
			return (int) Long.parseLong(hexColor, 16);
		} catch (NumberFormatException e) {
			GuiLib.LOGGER.error("[YAML] Invalid color format. Unable to parse color: {}", hexColor, e);
			return 0;
		}
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
}
