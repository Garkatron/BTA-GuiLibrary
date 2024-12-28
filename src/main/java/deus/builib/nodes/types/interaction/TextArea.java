package deus.builib.nodes.types.interaction;

import deus.builib.nodes.stylesystem.StyleParser;
import deus.builib.nodes.types.templates.ClickableElement;
import deus.builib.interfaces.ILambda;
import deus.builib.gssl.Signal;
import net.minecraft.client.gui.text.ITextField;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	public TextArea() {
		super();
		text.add(0,"");
		this.setText("");
	}

	public TextArea(Map<String, String> attr) {
		super(attr);
		text.add(0,"");
		this.setText("");
	}

	@Override
	protected void drawIt() {
		super.drawIt();
		drawText();
	}

	protected void drawText() {

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

		int lineHeight = this.mc.fontRenderer.fontHeight;
		int textStartY = this.gy + 4;

		for (int i = 0; i < text.size(); i++) {
			String line = text.get(i);
			this.drawString(this.mc.fontRenderer, line, this.gx + 4, textStartY + (lineHeight * i), textColor);
		}

		if (focused) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastCursorToggle > cursorBlinkInterval) {
				drawCursor = !drawCursor;
				lastCursorToggle = currentTime;
			}

			if (drawCursor && currentIndex < text.size()) {
				int cursorX = this.gx + 4 + this.mc.fontRenderer.getStringWidth(text.get(currentIndex).substring(0, cursorPosition));
				int cursorY = textStartY + (lineHeight * currentIndex);
				this.drawString(this.mc.fontRenderer, cursorCharacter, cursorX, cursorY, textColor);
			}
		}
	}

	@Override
	protected void updateIt() {
		super.updateIt();
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
						currentIndex++;
						if (currentIndex >= text.size()) {
							text.add("");
						}
						cursorPosition = 0;
						height += 10;
						if (onEnter != null)
							onEnter.execute(this);

					} else if (key == Keyboard.KEY_LEFT) {
						if (cursorPosition > 0) {
							cursorPosition -= 1;
						} else if (currentIndex > 0) {
							currentIndex--;
							cursorPosition = text.get(currentIndex).length();
						}

					} else if (key == Keyboard.KEY_RIGHT) {
						if (cursorPosition < text.get(currentIndex).length()) {
							cursorPosition += 1;
						} else if (currentIndex < text.size() - 1) {
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
				text.set(currentIndex, text.get(currentIndex).substring(0, cursorPosition - 1) + text.get(currentIndex).substring(cursorPosition));
				cursorPosition = Math.max(0, cursorPosition - 1);
				textChangedSignal.emit(text.get(currentIndex));
			} else if (currentIndex > 0) {
				String previousLine = text.get(currentIndex - 1);
				String currentLine = text.get(currentIndex);
				text.set(currentIndex - 1, previousLine + currentLine);
				text.remove(currentIndex);
				height -=10;
				currentIndex--;
				cursorPosition = previousLine.length();
				textChangedSignal.emit(text.get(currentIndex));
			}
		}
	}

	private void addCharacter(char character) {
		if (text.get(currentIndex).length() < maxLength() && isCharacterAllowed(character)) {
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
