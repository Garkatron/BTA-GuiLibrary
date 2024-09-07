package deus.guilib.element.elements;

import deus.guilib.element.config.ChildrenPlacement;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.TextFieldConfig;
import deus.guilib.element.interfaces.ILambda;
import deus.guilib.gssl.Signal;
import net.minecraft.client.gui.text.ITextField;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class TextArea extends ClickableElement implements ITextField {

	private List<String> text = new ArrayList<>();
	private int cursorPosition = 0;
	private long lastCursorToggle = 0;
	private boolean drawCursor = true;

	protected int currentIndex = 0;
	protected int width = 120;
	protected int height = 15;

	public final Signal<String> textChangedSignal = new Signal<>();
	private boolean focused = false;

	private ILambda onEnter;
	private ILambda onDelete;
	private ILambda onEscape;

	protected TextFieldConfig textFieldConfig = TextFieldConfig.create();

	public TextArea() {
		text.add(0,"");
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

		int lineHeight = this.mc.fontRenderer.fontHeight;  // Altura de cada línea
		int textStartY = this.y + 4;  // Punto inicial para la primera línea

		// Dibujar cada línea de texto
		for (int i = 0; i < text.size(); i++) {
			String line = text.get(i);
			this.drawString(this.mc.fontRenderer, line, this.x + 4, textStartY + (lineHeight * i), textColor);
		}

		if (focused) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastCursorToggle > textFieldConfig.getCursorBlinkInterval()) {
				drawCursor = !drawCursor;
				lastCursorToggle = currentTime;
			}

			if (drawCursor && currentIndex < text.size()) {
				// Calcular posición del cursor en la línea actual
				int cursorX = this.x + 4 + this.mc.fontRenderer.getStringWidth(text.get(currentIndex).substring(0, cursorPosition));
				int cursorY = textStartY + (lineHeight * currentIndex);  // Ajuste vertical según la línea actual
				this.drawString(this.mc.fontRenderer, textFieldConfig.getCursorCharacter(), cursorX, cursorY, textColor);
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
						if (onDelete != null)
							onDelete.execute(this);

					} else if (key == Keyboard.KEY_ESCAPE) {
						focused = false;
						if (onEscape != null)
							onEscape.execute(this);

					} else if (key == Keyboard.KEY_RETURN) {
						// Añadir una nueva línea y mover el cursor a esa línea
						currentIndex++;
						if (currentIndex >= text.size()) {
							text.add("");
						}
						cursorPosition = 0;
						height += 10; // Incrementar altura al añadir línea
						if (onEnter != null)
							onEnter.execute(this);

					} else if (key == Keyboard.KEY_LEFT) {
						if (cursorPosition > 0) {
							cursorPosition -= 1;
						} else if (currentIndex > 0) {
							// Saltar a la línea anterior
							currentIndex--;
							cursorPosition = text.get(currentIndex).length();
						}

					} else if (key == Keyboard.KEY_RIGHT) {
						if (cursorPosition < text.get(currentIndex).length()) {
							cursorPosition += 1;
						} else if (currentIndex < text.size() - 1) {
							// Saltar a la siguiente línea
							currentIndex++;
							cursorPosition = 0;
						}

					} else if (character != 0 && isCharacterAllowed(character)) {
						addCharacter(character);
					}
				}
			}
		}
	}



	private void deleteCharacter() {
		if (focused) {
			if (cursorPosition > 0) {
				// Eliminar el carácter en la posición del cursor - 1
				text.set(currentIndex, text.get(currentIndex).substring(0, cursorPosition - 1) + text.get(currentIndex).substring(cursorPosition));
				cursorPosition = Math.max(0, cursorPosition - 1);
				textChangedSignal.emit(text.get(currentIndex));
			} else if (currentIndex > 0) {
				// Combinar esta línea con la anterior
				String previousLine = text.get(currentIndex - 1);
				String currentLine = text.get(currentIndex);
				text.set(currentIndex - 1, previousLine + currentLine);
				text.remove(currentIndex);  // Eliminar línea actual
				height -=10;
				currentIndex--;
				cursorPosition = previousLine.length();
				textChangedSignal.emit(text.get(currentIndex));
			}
		}
	}


	private void addCharacter(char character) {
		if (text.get(currentIndex).length() < maxLength() && isCharacterAllowed(character)) {
			// Insertar el carácter en la posición del cursor
			text.set(currentIndex, text.get(currentIndex).substring(0, cursorPosition) + character + text.get(currentIndex).substring(cursorPosition));
			cursorPosition++;
			textChangedSignal.emit(text.get(currentIndex));
		}
	}


	@Override
	public void setText(String newText) {
		if (!this.text.equals(newText)) {
			text.set(currentIndex,newText);
			cursorPosition = text.get(currentIndex).length();
			textChangedSignal.emit(text.get(currentIndex));
		}
	}

	@Override
	public String getText() {
		return text.get(currentIndex);
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

	public TextArea setWidth(int width) {
		this.width = width;
		return this;
	}

	public TextArea setHeight(int height) {
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

	public TextArea config(TextFieldConfig textFieldConfig) {
		this.textFieldConfig = textFieldConfig;
		return this;
	}


	public TextArea setOnEnter(ILambda onEnter) {
		this.onEnter = onEnter;
		return this;
	}

	public TextArea setOnDelete(ILambda onDelete) {
		this.onDelete = onDelete;
		return this;
	}

	public TextArea setOnEscape(ILambda onEscape) {
		this.onEscape = onEscape;
		return this;
	}
}
