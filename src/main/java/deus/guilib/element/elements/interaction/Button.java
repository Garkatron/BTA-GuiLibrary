package deus.guilib.element.elements.interaction;

import deus.guilib.element.Element;
import deus.guilib.interfaces.element.IButton;
import deus.guilib.interfaces.ILambda;
import deus.guilib.resource.Texture;
import deus.guilib.util.math.Tuple;
import net.minecraft.core.sound.SoundCategory;
import org.lwjgl.input.Mouse;

public class Button extends Element implements IButton {

	private int mx = 0;
	private int my = 0;
	protected boolean activated = false;
	private boolean toggleMode = false;
	private ILambda onRelease;
	private ILambda onPush;
	private ILambda whilePressed;
	private String soundName = "random.click";

	private Tuple<Integer, Integer> hoverTextureRegion;
	private Tuple<Integer, Integer> pressedTextureRegion;
	private Tuple<Integer, Integer> defaultTextureRegion;

	private boolean withSound = true;
	private boolean wasClicked = false;

	public Button() {
		super(new Texture("assets/textures/gui/Button.png", 20, 20));
		setDefaultTextureRegion(0, 0);
		setPressedTextureRegion(1, 0);
		setHoverTextureRegion(2, 0);
	}

	public Button setSound(String id) {
		this.soundName = id;
		return this;
	}

	public Button setToggleMode(boolean toggleMode) {
		this.toggleMode = toggleMode;
		return this;
	}

	public Button setOnReleaseAction(ILambda onRelease) {
		this.onRelease = onRelease;
		return this;
	}

	public Button setOnPushAction(ILambda onPush) {
		this.onPush = onPush;
		return this;
	}

	public Button setWhilePressedAction(ILambda whilePressed) {
		this.whilePressed = whilePressed;
		return this;
	}

	public Button withSound(boolean bool) {
		this.withSound = bool;
		return this;
	}

	@Override
	public boolean isHovered() {
		return mx >= gx && my >= gy && mx < gx + getWidth() && my < gy + getHeight();
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
		return toggleMode;
	}

	@Override
	public void toggle(boolean activate) {
		if (!toggleMode) {
			this.activated = activate;
		}
	}

	@Override
	public void update(int mouseX, int mouseY) {
		this.mx = mouseX;
		this.my = mouseY;

		boolean hovered = isHovered();
		boolean buttonDown = Mouse.isButtonDown(0);

		if (hovered) {
			if (buttonDown) {
				if (!wasClicked) {
					onPush();
					wasClicked = true;

					if (toggleMode) {
						activated = !activated;
					}
				}
				//texture.setOffsetX(1);
				texture.setFrameX(pressedTextureRegion.getFirst());
				texture.setFrameY(pressedTextureRegion.getSecond());

			} else {
				if (wasClicked) {
					onRelease();
				}
				texture.setFrameX(hoverTextureRegion.getFirst());
				texture.setFrameY(hoverTextureRegion.getSecond());

				wasClicked = false;
			}

			if (buttonDown) {
				whilePressed();
			}

		} else {
			if (toggleMode && activated) {
				texture.setFrameX(pressedTextureRegion.getFirst());
				texture.setFrameY(pressedTextureRegion.getSecond());

			} else {
				texture.setFrameX(defaultTextureRegion.getFirst());
				texture.setFrameY(defaultTextureRegion.getSecond());
			}
			wasClicked = false;

		}

	}

	@Override
	public void onPush() {

		if (withSound) {
			this.mc.sndManager.playSound(soundName, SoundCategory.GUI_SOUNDS, 1.0F, 1.0F);
		}

		if (onPush != null) {
			onPush.execute(this);
		}
	}

	@Override
	public void onPushOut() {

	}

	@Override
	public void onRelease() {
		if (onRelease != null) {
			onRelease.execute(this);
		}
	}

	@Override
	public void whilePressed() {
		if (whilePressed != null) {
			whilePressed.execute(this);
		}
	}

	public boolean isWithSound() {
		return withSound;
	}

	public Button setHoverTextureRegion(int x, int y) {
		this.hoverTextureRegion = new Tuple<>(x,y);
		return this;
	}

	public Button setPressedTextureRegion(int x, int y) {
		this.pressedTextureRegion = new Tuple<>(x,y);
		return this;
	}

	public Button setDefaultTextureRegion(int x, int y) {
		this.defaultTextureRegion = new Tuple<>(x,y);
		return this;
	}

}
