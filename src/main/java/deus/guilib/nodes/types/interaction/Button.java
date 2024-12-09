package deus.guilib.nodes.types.interaction;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.IButton;
import deus.guilib.interfaces.ILambda;
import deus.guilib.resource.Texture;
import deus.guilib.util.GuiHelper;
import deus.guilib.util.math.Tuple;
import net.minecraft.core.sound.SoundCategory;
import org.lwjgl.input.Mouse;

import java.util.Map;
import java.util.Optional;

public class Button extends Node implements IButton {

	private int mx = 0;
	private int my = 0;
	private boolean activated = false;
	private ToggleMode toggleMode = ToggleMode.DISABLED;  // Usando enum para el modo de alternancia
	private Optional<ILambda> onRelease = Optional.empty();
	private Optional<ILambda> onPush = Optional.empty();
	private Optional<ILambda> whilePressed = Optional.empty();
	private String soundName = "random.click";

	private Tuple<Integer, Integer> hoverTextureRegion;
	private Tuple<Integer, Integer> pressedTextureRegion;
	private Tuple<Integer, Integer> defaultTextureRegion;

	private boolean withSound = true;
	private boolean wasClicked = false;

	private boolean usePressedTexture = false;
	private boolean useHoverTexture = false;

	public Button() {
		super();
		setDefaultTextureRegion(0, 0);
		setPressedTextureRegion(1, 0);
		setHoverTextureRegion(2, 0);
	}

	public Button(Map<String, String> attributes) {
		super(attributes);
		setDefaultTextureRegion(0, 0);
		setPressedTextureRegion(1, 0);
		setHoverTextureRegion(2, 0);
	}

	public Button setSound(String id) {
		this.soundName = id;
		return this;
	}

	public Button setToggleMode(boolean enabled) {
		this.toggleMode = enabled ? ToggleMode.ENABLED : ToggleMode.DISABLED;
		return this;
	}

	public Button setOnReleaseAction(ILambda onRelease) {
		this.onRelease = Optional.ofNullable(onRelease);
		return this;
	}

	public Button setOnPushAction(ILambda onPush) {
		this.onPush = Optional.ofNullable(onPush);
		return this;
	}

	public Button setWhilePressedAction(ILambda whilePressed) {
		this.whilePressed = Optional.ofNullable(whilePressed);
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
		return !activated; // Devuelve si el botón está desactivado
	}

	@Override
	public boolean isToggle() {
		return toggleMode == ToggleMode.ENABLED;
	}

	@Override
	public void toggle(boolean activate) {
		if (toggleMode == ToggleMode.ENABLED) {
			this.activated = activate;
		}
	}

	@Override
	protected void drawBackgroundImage() {
		if (styles.containsKey("backgroundImage")) {
			Texture texture = (Texture) styles.get("backgroundImage");

			int scaleW = 0, scaleH = 0;

			texture.setFrameX(defaultTextureRegion.getFirst());
			texture.setFrameY(defaultTextureRegion.getSecond());

			if (usePressedTexture) {
				texture.setFrameX(pressedTextureRegion.getFirst());
				texture.setFrameY(pressedTextureRegion.getSecond());
			} else if (useHoverTexture) {
				texture.setFrameX(hoverTextureRegion.getFirst());
				texture.setFrameY(hoverTextureRegion.getSecond());
			}

			if (styles.containsKey("backgroundImageScale")) {
				scaleW = scaleH = (Integer) styles.get("backgroundImageScale");
			}

			if (styles.containsKey("backgroundImageScaleWidth")) {
				scaleW = (Integer) styles.get("backgroundImageScaleWidth");
			}

			if (styles.containsKey("backgroundImageScaleHeight")) {
				scaleH = (Integer) styles.get("backgroundImageScaleHeight");
			}

			texture.draw(mc, gx, gy, width, height, scaleW, scaleH);
		}
	}

	@Override
	public void update() {
		super.update();

		this.mx = GuiHelper.mouseX;
		this.my = GuiHelper.mouseY;

		boolean hovered = isHovered();
		boolean buttonDown = Mouse.isButtonDown(0);

		if (hovered) {
			if (buttonDown && !wasClicked) {
				onPush();
				wasClicked = true;

				if (toggleMode == ToggleMode.ENABLED) {
					activated = !activated;
				}
			}

			usePressedTexture = buttonDown;
			useHoverTexture = !buttonDown;

			if (buttonDown) {
				whilePressed();
			} else if (wasClicked) {
				onRelease();
			}
		} else {
			usePressedTexture = toggleMode == ToggleMode.ENABLED && activated;
			useHoverTexture = false;
			wasClicked = false;
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
		// Se puede agregar lógica para cuando el botón es soltado afuera
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

	public Button setHoverTextureRegion(int x, int y) {
		this.hoverTextureRegion = new Tuple<>(x, y);
		return this;
	}

	public Button setPressedTextureRegion(int x, int y) {
		this.pressedTextureRegion = new Tuple<>(x, y);
		return this;
	}

	public Button setDefaultTextureRegion(int x, int y) {
		this.defaultTextureRegion = new Tuple<>(x, y);
		return this;
	}

	public enum ToggleMode {
		ENABLED, DISABLED
	}
}
