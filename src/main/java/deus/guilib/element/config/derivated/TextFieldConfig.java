package deus.guilib.element.config.derivated;

public class TextFieldConfig  {

	private int focusBackgroundColor = 0xFF000000;
	private int focusTextColor = 0xFFE9C46A;
	private int focusBorderColor = 0xFFE9C46A;

	private int defaultBackgroundColor = 0xFF000000;
	private int defaultTextColor = 0xFFFFFFFF;
	private int defaultBorderColor = 0xFFFFFFFF;

	private boolean drawBackground = true;
	private int CURSOR_BLINK_INTERVAL = 500;

	private String cursorCharacter = "|";

	private TextFieldConfig() {
		super();
	}

	public static TextFieldConfig create() {
		return new TextFieldConfig();
	}

	// Getters
	public int getFocusBackgroundColor() {
		return focusBackgroundColor;
	}

	public int getFocusTextColor() {
		return focusTextColor;
	}

	public int getFocusBorderColor() {
		return focusBorderColor;
	}

	public int getDefaultBackgroundColor() {
		return defaultBackgroundColor;
	}

	public int getDefaultTextColor() {
		return defaultTextColor;
	}

	public int getDefaultBorderColor() {
		return defaultBorderColor;
	}

	public boolean isDrawBackground() {
		return drawBackground;
	}

	public int getCursorBlinkInterval() {
		return CURSOR_BLINK_INTERVAL;
	}


	// Setters
	public TextFieldConfig setFocusBackgroundColor(int focusBackgroundColor) {
		this.focusBackgroundColor = focusBackgroundColor;
		return this;
	}

	public TextFieldConfig setFocusTextColor(int focusTextColor) {
		this.focusTextColor = focusTextColor;
		return this;
	}

	public TextFieldConfig setFocusBorderColor(int focusBorderColor) {
		this.focusBorderColor = focusBorderColor;
		return this;
	}

	public TextFieldConfig setDefaultBackgroundColor(int defaultBackgroundColor) {
		this.defaultBackgroundColor = defaultBackgroundColor;
		return this;
	}

	public TextFieldConfig setDefaultTextColor(int defaultTextColor) {
		this.defaultTextColor = defaultTextColor;
		return this;
	}

	public TextFieldConfig setDefaultBorderColor(int defaultBorderColor) {
		this.defaultBorderColor = defaultBorderColor;
		return this;
	}

	public TextFieldConfig setDrawBackground(boolean drawBackground) {
		this.drawBackground = drawBackground;
		return this;
	}

	public TextFieldConfig setCursorBlinkInterval(int cursorBlinkInterval) {
		this.CURSOR_BLINK_INTERVAL = cursorBlinkInterval;
		return this;
	}

	public String getCursorCharacter() {
		return cursorCharacter;
	}

	public TextFieldConfig setCursorCharacter(String cursorCharacter) {
		this.cursorCharacter = cursorCharacter;
		return this;
	}
}
