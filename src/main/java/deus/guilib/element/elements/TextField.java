package deus.guilib.element.elements;

import deus.guilib.element.config.ChildrenPlacement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.TextFieldConfig;
import deus.guilib.element.interfaces.element.IElement;
import deus.guilib.gssl.Signal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.text.ITextField;
import org.lwjgl.input.Keyboard;

public class TextField extends ClickableElement implements ITextField {

	private String text = "";
	private int cursorPosition = 0;
	private long lastCursorToggle = 0;
	private boolean drawCursor = true;

	protected int width = 120;
	protected int height = 20;

	public final Signal<String> textChangedSignal = new Signal<>();
	private boolean focused = false;

	protected TextFieldConfig textFieldConfig = TextFieldConfig.create();

	public TextField() {
		this.setText("");
		setConfig(ElementConfig.create().setPlacement(ChildrenPlacement.RIGHT));
	}

	@Override
	protected void drawIt() {
		int backgroundColor = focused ? textFieldConfig.getFocusBackgroundColor() : textFieldConfig.getDefaultBackgroundColor();
		int textColor = focused ? textFieldConfig.getFocusTextColor() : textFieldConfig.getDefaultTextColor();
		int borderColor = focused ? textFieldConfig.getFocusBorderColor() : textFieldConfig.getDefaultBorderColor();

		if (textFieldConfig.isDrawBackground()) {
			this.drawRect(this.x - 1, this.y - 1, this.x + getWidth() + 1, this.y + getHeight() + 1, borderColor);
			this.drawRect(this.x, this.y, this.x + getWidth(), this.y + getHeight(), backgroundColor);
		}

		this.drawString(this.mc.fontRenderer, this.text, this.x + 4, this.y + (getHeight() - 8) / 2, textColor);

		if (focused) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastCursorToggle > textFieldConfig.getCursorBlinkInterval()) {
				drawCursor = !drawCursor;
				lastCursorToggle = currentTime;
			}

			if (drawCursor) {
				int cursorX = this.x + 4 + this.mc.fontRenderer.getStringWidth(text.substring(0, cursorPosition));
				this.drawString(this.mc.fontRenderer, textFieldConfig.getCursorCharacter(), cursorX, this.y + (getHeight() - 8) / 2, textColor);
			}
		}
	}

	@Override
	public void update(int mouseX, int mouseY) {
		super.update(mouseX, mouseY);

		if (focused) {
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					int key = Keyboard.getEventKey();
					char character = Keyboard.getEventCharacter();

					if (key == Keyboard.KEY_BACK) {
						deleteCharacter();
					} else if (key == Keyboard.KEY_DELETE) {

					} else if (key == Keyboard.KEY_RETURN) {

					} else if (key == Keyboard.KEY_ESCAPE) {
						focused = false;
					} else if (character != 0 && isCharacterAllowed(character)) {
						addCharacter(character);
					}
				}
			}
		}
	}

	private void deleteCharacter() {
		if (!text.isEmpty() && focused) {
			// Eliminar el carácter en la posición del cursor
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
		return 19;
	}

	@Override
	public boolean isCharacterAllowed(char c) {
		return true;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
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

	public TextField config(TextFieldConfig textFieldConfig) {
		this.textFieldConfig = textFieldConfig;
		return this;
	}


}
