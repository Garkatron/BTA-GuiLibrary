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
		if (value.endsWith("%")) {
			return 0;
		} else if (value.endsWith("px")) {
			return Integer.parseInt(value.replace("px", "").trim());
		}
		return 0;
	}


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

	public static int parseColorToARGB(String hexColor) {
		// Eliminar el símbolo '#' si está presente
		if (hexColor.startsWith("#")) {
			hexColor = hexColor.substring(1);  // Quitamos el '#'
		}

		// Si el color tiene 6 caracteres (RRGGBB), agregamos 'FF' como canal alfa
		if (hexColor.length() == 6) {
			hexColor = "FF" + hexColor;  // Asumimos un canal alfa completamente opaco
		}

		// Convertir el valor hexadecimal (AARRGGBB) a un entero en formato ARGB
		return (int) Long.parseLong(hexColor, 16);  // Usamos Long.parseLong para manejar el valor correctamente
	}



}
