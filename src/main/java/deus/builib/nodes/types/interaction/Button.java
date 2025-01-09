package deus.builib.nodes.types.interaction;

import deus.builib.nodes.Node;
import deus.builib.interfaces.nodes.IButton;
import deus.builib.interfaces.ILambda;
import deus.builib.nodes.stylesystem.StyleParser;
import deus.builib.nodes.stylesystem.textures.BuiTextureProperties;
import deus.builib.util.GuiHelper;
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

	private boolean withSound = true;
	private boolean beingHeld = false;

	private boolean usePressedTexture = false;
	private boolean useHoverTexture = false;

	public Button() {
		super();
	}

	public Button(Map<String, String> attributes) {
		super(attributes);
	}

	public Button setSound(String id) {
		this.soundName = id;
		return this;
	}

	@Override
	public Button setToggleMode(boolean enabled) {
		this.toggleMode = enabled ? ToggleMode.ENABLED : ToggleMode.DISABLED;
		return this;
	}

	@Override
	public Button setOnReleaseAction(ILambda onRelease) {
		this.onRelease = Optional.ofNullable(onRelease);
		return this;
	}

	@Override
	public Button setOnPushAction(ILambda onPush) {
		this.onPush = Optional.ofNullable(onPush);
		return this;
	}

	@Override
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
		return !activated;
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


			String id = (String) styles.get("backgroundImage");

			if ("transparent".equals(id)) return;

			BuiTextureProperties textureProps = tgm.getTexture(id);
			BuiTextureProperties texturePropsHover = textureProps;
			BuiTextureProperties texturePropsPressed = textureProps;

			int bgwidth, bgheight;

			if (styles.containsKey("bgWidth")) {
				bgwidth = StyleParser.parsePixels((String) styles.get("bgWidth"));
			} else {
				bgwidth = 0;
			}
			if (styles.containsKey("bgHeight")) {
				bgheight = StyleParser.parsePixels((String) styles.get("bgHeight"));
			} else {
				bgheight = 0;
			}


			if (styles.containsKey("backgroundImageHover")) {
				texturePropsHover = tgm.getTexture((String) styles.get("backgroundImageHover"));
			}

			if (styles.containsKey("backgroundImagePressed")) {
				texturePropsPressed = tgm.getTexture((String) styles.get("backgroundImagePressed"));

			}

			if (usePressedTexture) {
				//drawTexture(mc, texturePropsPressed, gx, gy, bgwidth==0 ? width : bgwidth, bgheight==0 ? height : bgheight);

			} else if (useHoverTexture) {
				//drawTexture(mc, texturePropsHover, gx, gy, bgwidth==0 ? width : bgwidth, bgheight==0 ? height : bgheight);

			} else {
				//drawTexture(mc, textureProps, gx, gy, bgwidth==0 ? width : bgwidth, bgheight==0 ? height : bgheight);
			}

		}
	}

	@Override
	public void update() {
		super.update();

		this.mx = GuiHelper.mouseX;
		this.my = GuiHelper.mouseY;

		boolean hovered = isHovered();
		boolean buttonDown = Mouse.isButtonDown(0);

		usePressedTexture = beingHeld;
		useHoverTexture = hovered;
		if (beingHeld) whilePressed();

		if (hovered && buttonDown && !beingHeld) {
			onPush();
			beingHeld = true;
			if (toggleMode == ToggleMode.ENABLED) activated = !activated;
		}

		else if (!buttonDown && beingHeld) {
			beingHeld = false;
			if (hovered) onRelease(); // you might want to remove this IF check, but it helps you scape unintended presses.
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


	public enum ToggleMode {
		ENABLED, DISABLED
	}
}
