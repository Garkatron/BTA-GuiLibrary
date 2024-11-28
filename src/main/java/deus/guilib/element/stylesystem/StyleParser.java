package deus.guilib.element.stylesystem;

import java.util.HashMap;
import java.util.Map;

public class StyleParser {

	protected Map<String, String> params = new HashMap<String, String>() {{
		put("BackgroundColor", "#ffffff");
		put("border", "2px");
	}};

	public static Map<String, String> parse(String path) {
		Map<String, String> styles = new HashMap<>();

		/* Open & Read file */

		/* Parsing File */

		return styles;
	}

	public static int parsePixels(String value) {
		return Integer.parseInt(value.replace("px", "").trim());
	}

	public static int parseColor(String hexColor) {
		return Integer.parseInt(hexColor.replace("#", ""), 16);
	}

	public static BorderStyle parseBorder(String params) {
		String[] parts = params.split(" ");

		int borderWidth = 0;
		int borderColor = 0;

		if (parts.length > 0) {
			borderWidth = StyleParser.parsePixels(parts[0]);
		}
		if (parts.length > 1) {
			borderColor = StyleParser.parseColor(parts[1]);
		}

		return new BorderStyle(borderColor, borderWidth);

	}

}
