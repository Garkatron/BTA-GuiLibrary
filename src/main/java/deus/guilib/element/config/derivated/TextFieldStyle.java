package deus.guilib.element.config.derivated;

/**
 * Configuration class for text fields, including settings for colors, background drawing, and cursor properties.
 */
public class TextFieldStyle {

	private int focusBackgroundColor = 0xFF000000;
	private int focusTextColor = 0xFFE9C46A;
	private int focusBorderColor = 0xFFE9C46A;

	private int defaultBackgroundColor = 0xFF000000;
	private int defaultTextColor = 0xFFFFFFFF;
	private int defaultBorderColor = 0xFFFFFFFF;

	private boolean drawBackground = true;
	private int CURSOR_BLINK_INTERVAL = 500;

	private String cursorCharacter = "_";

	/**
	 * Creates a new instance of {@code TextFieldConfig}.
	 *
	 * @return A new instance of {@code TextFieldConfig}.
	 */
	public static TextFieldStyle create() {
		return new TextFieldStyle();
	}

	// Getters

	/**
	 * Gets the background color when the text field is focused.
	 *
	 * @return The background color in ARGB format.
	 */
	public int getFocusBackgroundColor() {
		return focusBackgroundColor;
	}

	/**
	 * Gets the text color when the text field is focused.
	 *
	 * @return The text color in ARGB format.
	 */
	public int getFocusTextColor() {
		return focusTextColor;
	}

	/**
	 * Gets the border color when the text field is focused.
	 *
	 * @return The border color in ARGB format.
	 */
	public int getFocusBorderColor() {
		return focusBorderColor;
	}

	/**
	 * Gets the default background color of the text field.
	 *
	 * @return The default background color in ARGB format.
	 */
	public int getDefaultBackgroundColor() {
		return defaultBackgroundColor;
	}

	/**
	 * Gets the default text color of the text field.
	 *
	 * @return The default text color in ARGB format.
	 */
	public int getDefaultTextColor() {
		return defaultTextColor;
	}

	/**
	 * Gets the default border color of the text field.
	 *
	 * @return The default border color in ARGB format.
	 */
	public int getDefaultBorderColor() {
		return defaultBorderColor;
	}

	/**
	 * Checks if the background should be drawn.
	 *
	 * @return {@code true} if the background should be drawn; {@code false} otherwise.
	 */
	public boolean isDrawBackground() {
		return drawBackground;
	}

	/**
	 * Gets the cursor blink interval.
	 *
	 * @return The cursor blink interval in milliseconds.
	 */
	public int getCursorBlinkInterval() {
		return CURSOR_BLINK_INTERVAL;
	}

	/**
	 * Gets the character used to represent the cursor.
	 *
	 * @return The cursor character as a {@code String}.
	 */
	public String getCursorCharacter() {
		return cursorCharacter;
	}

	// Setters

	/**
	 * Sets the background color when the text field is focused.
	 *
	 * @param focusBackgroundColor The background color in ARGB format.
	 * @return The current instance of {@code TextFieldConfig} for method chaining.
	 */
	public TextFieldStyle setFocusBackgroundColor(int focusBackgroundColor) {
		this.focusBackgroundColor = focusBackgroundColor;
		return this;
	}

	/**
	 * Sets the text color when the text field is focused.
	 *
	 * @param focusTextColor The text color in ARGB format.
	 * @return The current instance of {@code TextFieldConfig} for method chaining.
	 */
	public TextFieldStyle setFocusTextColor(int focusTextColor) {
		this.focusTextColor = focusTextColor;
		return this;
	}

	/**
	 * Sets the border color when the text field is focused.
	 *
	 * @param focusBorderColor The border color in ARGB format.
	 * @return The current instance of {@code TextFieldConfig} for method chaining.
	 */
	public TextFieldStyle setFocusBorderColor(int focusBorderColor) {
		this.focusBorderColor = focusBorderColor;
		return this;
	}

	/**
	 * Sets the default background color of the text field.
	 *
	 * @param defaultBackgroundColor The default background color in ARGB format.
	 * @return The current instance of {@code TextFieldConfig} for method chaining.
	 */
	public TextFieldStyle setDefaultBackgroundColor(int defaultBackgroundColor) {
		this.defaultBackgroundColor = defaultBackgroundColor;
		return this;
	}

	/**
	 * Sets the default text color of the text field.
	 *
	 * @param defaultTextColor The default text color in ARGB format.
	 * @return The current instance of {@code TextFieldConfig} for method chaining.
	 */
	public TextFieldStyle setDefaultTextColor(int defaultTextColor) {
		this.defaultTextColor = defaultTextColor;
		return this;
	}

	/**
	 * Sets the default border color of the text field.
	 *
	 * @param defaultBorderColor The default border color in ARGB format.
	 * @return The current instance of {@code TextFieldConfig} for method chaining.
	 */
	public TextFieldStyle setDefaultBorderColor(int defaultBorderColor) {
		this.defaultBorderColor = defaultBorderColor;
		return this;
	}

	/**
	 * Sets whether the background should be drawn.
	 *
	 * @param drawBackground A boolean indicating if the background should be drawn.
	 * @return The current instance of {@code TextFieldConfig} for method chaining.
	 */
	public TextFieldStyle setDrawBackground(boolean drawBackground) {
		this.drawBackground = drawBackground;
		return this;
	}

	/**
	 * Sets the cursor blink interval.
	 *
	 * @param cursorBlinkInterval The interval in milliseconds at which the cursor blinks.
	 * @return The current instance of {@code TextFieldConfig} for method chaining.
	 */
	public TextFieldStyle setCursorBlinkInterval(int cursorBlinkInterval) {
		this.CURSOR_BLINK_INTERVAL = cursorBlinkInterval;
		return this;
	}

	/**
	 * Sets the character used to represent the cursor.
	 *
	 * @param cursorCharacter The cursor character as a {@code String}.
	 * @return The current instance of {@code TextFieldConfig} for method chaining.
	 */
	public TextFieldStyle setCursorCharacter(String cursorCharacter) {
		this.cursorCharacter = cursorCharacter;
		return this;
	}
}
