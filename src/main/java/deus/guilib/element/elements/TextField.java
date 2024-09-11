package deus.guilib.element.elements;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.TextFieldConfig;
import deus.guilib.interfaces.ILambda;
import deus.guilib.gssl.Signal;
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

	private ILambda onEnter;
	private ILambda onDelete;
	private ILambda onEscape;

	protected TextFieldConfig textFieldConfig = TextFieldConfig.create();

	public TextField() {
		this.setText("");
		setConfig(ElementConfig.create().setChildrenPlacement(Placement.RIGHT));
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
						onDelete.execute(this);

					} else if (key == Keyboard.KEY_ESCAPE) {
						focused = false;
						onEscape.execute(this);

					} else if (key == Keyboard.KEY_RETURN) {
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
