package deus.builib.nodes.types.interaction;

import deus.builib.nodes.stylesystem.StyleParser;
import deus.builib.nodes.types.templates.ClickableElement;
import deus.builib.interfaces.ILambda;
import deus.builib.gssl.Signal;
import net.minecraft.client.gui.text.ITextField;
import org.lwjgl.input.Keyboard;

import java.util.Map;

public class TextField extends ClickableElement implements ITextField {

	private String text = "";
	private int cursorPosition = 0;
	private long lastCursorToggle = 0;
	private boolean drawCursor = true;

	protected int maxTextLength = 20;

	public final Signal<String> textChangedSignal = new Signal<>();
	private boolean focused = false;

	private ILambda onEnter;
	private ILambda onDelete;
	private ILambda onEscape;

	public TextField() {
		super();
		this.setText("");
	}

	public TextField(Map<String, String> attr) {
		super(attr);
		this.setText("");
	}

	@Override
	protected void drawIt() {

		int focusBackgroundColor = 0xFF000000;
		int focusTextColor = 0xFFE9C46A;
		int focusBorderColor = 0xFFE9C46A;

		int defaultBackgroundColor = 0xFF000000;
		int defaultTextColor = 0xFFFFFFFF;
		int defaultBorderColor = 0xFFFFFFFF;

		boolean drawBackground = true;
		int cursorBlinkInterval = 500;

		String cursorCharacter = "_";

		if (styles.containsKey("focusBackgroundColor"))
			focusBackgroundColor = StyleParser.parseColorToARGB((String) styles.get("focusBackgroundColor"));

		if (styles.containsKey("focusTextColor"))
			focusTextColor = StyleParser.parseColorToARGB((String) styles.get("focusTextColor"));

		if (styles.containsKey("focusBorderColor"))
			focusBorderColor = StyleParser.parseColorToARGB((String) styles.get("focusBorderColor"));

		if (styles.containsKey("defaultBorderColor"))
			defaultBorderColor = StyleParser.parseColorToARGB((String) styles.get("defaultBorderColor"));

		if (styles.containsKey("defaultBackgroundColor"))
			defaultBackgroundColor = StyleParser.parseColorToARGB((String) styles.get("defaultBackgroundColor"));

		if (styles.containsKey("defaultTextColor"))
			defaultTextColor = StyleParser.parseColorToARGB((String) styles.get("defaultTextColor"));

		if (styles.containsKey("drawBackground"))
			drawBackground = (boolean) styles.get("drawBackground");

		if (styles.containsKey("cursorBlinkInterval"))
			cursorBlinkInterval = Integer.parseInt((String) styles.get("cursorBlinkInterval"));

		if (styles.containsKey("cursorCharacter"))
			cursorCharacter = (String) styles.get("cursorCharacter");

		int backgroundColor = focused ? focusBackgroundColor : defaultBackgroundColor;
		int textColor = focused ? focusTextColor : defaultTextColor;
		int borderColor = focused ? focusBorderColor : defaultBorderColor;

		if (drawBackground) {
			this.drawRect(this.gx - 1, this.gy - 1, this.gx + getWidth() + 1, this.gy + getHeight() + 1, borderColor);
			this.drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + getHeight(), backgroundColor);
		}

		this.drawString(this.mc.font, this.text, this.gx + 4, this.gy + (getHeight() - 8) / 2, textColor);

		if (focused) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastCursorToggle > cursorBlinkInterval) {
				drawCursor = !drawCursor;
				lastCursorToggle = currentTime;
			}

			if (drawCursor) {
				int cursorX = this.gx + 4 + this.mc.font.getStringWidth(text.substring(0, cursorPosition));
				this.drawString(this.mc.font, cursorCharacter, cursorX, this.gy + (getHeight() - 8) / 2, textColor);
			}
		}
	}

	@Override
	protected void updateIt() {
		super.updateIt();

		if (attributes.containsKey("maxTextLength")) {
			maxTextLength = Integer.parseInt(attributes.get("maxTextLength"));
		}

		if (focused) {
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					int key = Keyboard.getEventKey();
					char character = Keyboard.getEventCharacter();

					if (key == Keyboard.KEY_BACK) {
						deleteCharacter();
						if(onDelete!=null)
							onDelete.execute(this);

					} else if (key == Keyboard.KEY_ESCAPE) {
						focused = false;
						if(onEscape!=null)
							onEscape.execute(this);

					} else if (key == Keyboard.KEY_RETURN) {
						if(onEnter!=null)
							onEnter.execute(this);

					} else if (key == Keyboard.KEY_LEFT) {
						if (cursorPosition > 0) {
							cursorPosition -= 1;
						}
					} else if (key == Keyboard.KEY_RIGHT) {
						if (cursorPosition < text.length()) {
							cursorPosition += 1;
						}
					} else if (character != 0 && isCharacterAllowed(character)) {
						addCharacter(character);
					}
				}
			}
		}
	}

	private void deleteCharacter() {
		if (focused && cursorPosition > 0) {
			// Eliminar el carácter en la posición del cursor - 1
			text = text.substring(0, cursorPosition - 1) + text.substring(cursorPosition);
			cursorPosition = Math.max(0, cursorPosition - 1);
			textChangedSignal.emit(text);
		}
	}

	private void addCharacter(char character) {
		if (text.length() < maxLength() && isCharacterAllowed(character)) {
			// Insertar el carácter en la posición del cursor
			text = text.substring(0, cursorPosition) + character + text.substring(cursorPosition);
			cursorPosition++;
			textChangedSignal.emit(text);
		}
	}

	@Override
	public void setText(String newText) {
		if (!this.text.equals(newText)) {
			this.text = newText;
			cursorPosition = text.length();
			textChangedSignal.emit(this.text);
		}
	}

	@Override
	public String getText() {
		return this.text;
	}

	@Override
	public int maxLength() {
		return maxTextLength;
	}

	@Override
	public boolean isCharacterAllowed(char c) {
		return true;
	}


	public TextField setWidth(int width) {
		this.width = width;
		return this;
	}

	public TextField setHeight(int height) {
		this.height = height;
		return this;
	}

	@Override
	public void onPush() {
		focused = true;
	}

	@Override
	public void onPushOut() {
		focused = false;
	}

	@Override
	public void onRelease() {
	}

	@Override
	public void whilePressed() {
	}

	public TextField setOnEnter(ILambda onEnter) {
		this.onEnter = onEnter;
		return this;
	}

	public TextField setOnDelete(ILambda onDelete) {
		this.onDelete = onDelete;
		return this;
	}

	public TextField setOnEscape(ILambda onEscape) {
		this.onEscape = onEscape;
		return this;
	}
}
