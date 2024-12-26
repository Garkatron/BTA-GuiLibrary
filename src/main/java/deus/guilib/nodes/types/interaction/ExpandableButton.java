package deus.guilib.nodes.types.interaction;

import deus.guilib.interfaces.ILambda;
import deus.guilib.interfaces.nodes.IButton;
import deus.guilib.nodes.stylesystem.StyleParser;
import deus.guilib.nodes.types.containers.Panel;
import deus.guilib.resource.Texture;
import deus.guilib.util.GuiHelper;
import deus.guilib.util.math.Offset;
import net.minecraft.core.sound.SoundCategory;
import org.lwjgl.input.Mouse;

import java.util.Map;
import java.util.Optional;

enum ToggleMode {
	ENABLED, DISABLED
}

enum ButtonStates {
	PRESSED(6),
	HOVER(3),
	DEFAULT(0);

	private final int stateValue;

	ButtonStates(int stateValue) {
		this.stateValue = stateValue;
	}

	public int getStateValue() {
		return stateValue;
	}
}
public class ExpandableButton extends Panel implements IButton {


	protected ButtonStates state = ButtonStates.DEFAULT;
	private boolean activated = false;
	private Button.ToggleMode toggleMode = Button.ToggleMode.DISABLED;
	private Optional<ILambda> onRelease = Optional.empty();
	private Optional<ILambda> onPush = Optional.empty();
	private Optional<ILambda> whilePressed = Optional.empty();
	private String soundName = "random.click";

	private boolean withSound = true;
	private boolean beingHeld = false;

	public ExpandableButton() {
		super();
	}

	public ExpandableButton(Map<String, String> attributes) {
		super(attributes);
	}

	@Override
	protected void drawIt() {
		super.drawIt();
		updateLengthStyle();
	}

	@Override
	protected void updateIt() {
		super.updateIt();
		if (styles.containsKey("tileSize")) {
			tileSize = StyleParser.parsePixels((String) styles.get("tileSize"));
		}
	}


	@Override
	protected void drawBackgroundImage() {
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");

			int gridWidth = lengthX * tileSize;  // 3 * 32
			int gridHeight = lengthY * tileSize; // 3 * 32

			for (int jx = 0; jx < gridWidth; jx += tileSize) {
				for (int jy = 0; jy < gridHeight; jy += tileSize) {
					Offset offset;

					boolean isLeft = (jx == 0);
					boolean isRight = (jx == gridWidth - tileSize);
					boolean isUp = (jy == 0);
					boolean isDown = (jy == gridHeight - tileSize);

					if (isLeft && isUp) offset = Offset.CORNER_UP_LEFT;
					else if (isLeft && isDown) offset = Offset.CORNER_DOWN_LEFT;
					else if (isRight && isUp) offset = Offset.CORNER_UP_RIGHT;
					else if (isRight && isDown) offset = Offset.CORNER_DOWN_RIGHT;
					else if (isLeft) offset = Offset.LEFT;
					else if (isRight) offset = Offset.RIGHT;
					else if (isUp) offset = Offset.UP;
					else if (isDown) offset = Offset.DOWN;
					else offset = Offset.CENTER;

					t.drawWithFrame(
						mc,
						gx + jx,
						gy + jy,
						tileSize,
						tileSize,
						(offset.getOffset().getFirst()  * tileSize) + (tileSize*state.getStateValue()),
						(offset.getOffset().getSecond() * tileSize)
					);
				}
			}
		}
	}

	@Override
	public void update() {
		super.update();

		boolean hovered = isHovered();
		boolean buttonDown = Mouse.isButtonDown(0);

		if (beingHeld) {
			state = ButtonStates.PRESSED;
		} else if (hovered) {
			state = ButtonStates.HOVER;
		} else {
			state = ButtonStates.DEFAULT;
		}

		if (beingHeld) whilePressed();

		if (hovered && buttonDown && !beingHeld) {
			onPush();
			beingHeld = true;
			if (toggleMode == Button.ToggleMode.ENABLED) activated = !activated;
		}

		else if (!buttonDown && beingHeld) {
			beingHeld = false;
			if (hovered) onRelease();
		}
	}



	@Override
	public ExpandableButton setToggleMode(boolean enabled) {
		this.toggleMode = enabled ? Button.ToggleMode.ENABLED : Button.ToggleMode.DISABLED;
		return this;
	}

	@Override
	public ExpandableButton setOnReleaseAction(ILambda onRelease) {
		this.onRelease = Optional.ofNullable(onRelease);
		return this;
	}

	@Override
	public ExpandableButton setOnPushAction(ILambda onPush) {
		this.onPush = Optional.ofNullable(onPush);
		return this;
	}

	@Override
	public ExpandableButton setWhilePressedAction(ILambda whilePressed) {
		this.whilePressed = Optional.ofNullable(whilePressed);
		return this;
	}

	public ExpandableButton withSound(boolean bool) {
		this.withSound = bool;
		return this;
	}


	@Override
	public boolean isOn() {
		return activated;
	}

	@Override
	public boolean isDisabled() {
		return !activated;
	}

	@Override
	public boolean isToggle() {
		return toggleMode == Button.ToggleMode.ENABLED;
	}

	@Override
	public void toggle(boolean activate) {
		if (toggleMode == Button.ToggleMode.ENABLED) {
			this.activated = activate;
		}
	}

	@Override
	public void onPush() {
		if (withSound) {
			this.mc.sndManager.playSound(soundName, SoundCategory.GUI_SOUNDS, 1.0F, 1.0F);
		}

		onPush.ifPresent(lambda -> lambda.execute(this));
	}

	@Override
	public void onPushOut() {

	}

	@Override
	public void onRelease() {
		onRelease.ifPresent(lambda -> lambda.execute(this));
	}

	@Override
	public void whilePressed() {
		whilePressed.ifPresent(lambda -> lambda.execute(this));
	}

	public boolean isWithSound() {
		return withSound;
	}





	@Override
	public boolean isHovered() {
		return GuiHelper.mouseX >= gx && GuiHelper.mouseY >= gy && GuiHelper.mouseX < gx + getWidth() && GuiHelper.mouseY < gy + getHeight();
	}
}
