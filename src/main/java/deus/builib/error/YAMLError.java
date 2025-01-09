package deus.builib.error;

import deus.builib.nodes.stylesystem.StyleSystem;

public enum YAMLError {

	// ? PARSING

	// * ID
	INVALID_ID("[YAML] Bad id: ({})"),
	ID_NOT_STARTING_WITH_AT("[YAML] Bad id, not starts with '"+ StyleSystem.yaml_css_selectors.get("id")+"': ({})"),

	// * GROUP
	INVALID_GROUP("[YAML] Bad group: ({})"),
	GROUP_NOT_STARTING_WITH_DOT("[YAML] Bad group, not starts with '" + StyleSystem.yaml_css_selectors.get("group") +"': ({})"),

	// * FILEPATH
	FILE_PATH_NOT_VALID("[YAML]  Invalid filepath: {}"),
	FILE_PATH_EMPTY("[YAML] Your filepath is empty"),
	SINGLE_STYLESHEET_EMPTY("Single stylesheet path is empty or null. Loading default styles."),

	// * NULL VALUE
	NULL_STYLE_MAP("Encountered a null style map during merging."),

	// * PIXEL
	PIXEL_FORMAT("[YAML] Invalid format: expected a valid pixel unit: {}"),
	NOT_NUMERIC_PIXEL_FORMAT("[YAML] Invalid format: Must be numeric ending with 'px'"),

	// * HEX
	INVALID_HEX_COLOR_FORMAT("[YAML] Invalid color format. Expected a hexadecimal color code (e.g., #RRGGBB) check yours: {}"),

	//* BORDER
	INVALID_BORDER("[YAML] Invalid border format: {}"),
	INVALID_BORDER_FORMAT("[YAML] Invalid border format. Your value: {} | Expected format: '<number>px <hexColor>'"),

	;


	// ? STYLING


	;

	private final String message;

	YAMLError(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
