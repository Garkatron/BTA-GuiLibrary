package deus.guilib.rendering.advanced;

import deus.guilib.rendering.base.Element;
import deus.guilib.rendering.base.interfaces.IButton;
import deus.guilib.rendering.base.interfaces.IElement;
import deus.guilib.rendering.base.interfaces.ILambda;
import deus.guilib.rendering.base.organization.ElementConfig;
import deus.guilib.rendering.resource.Texture;
import net.minecraft.core.sound.SoundCategory;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

public class Button extends Element implements IButton {

	private int mx = 0;
	private int my = 0;
	protected boolean activated = false; // true: activado, false: desactivado
	private boolean toggleMode = false; // Para habilitar o deshabilitar el modo de toggle
	private ILambda onRelease; // Acción a ejecutar cuando se suelte el clic
	private ILambda onPush; // Acción a ejecutar cuando se presione el botón
	private ILambda whilePressed; // Acción a ejecutar mientras el botón esté presionado
	private String soundName = "random.click";
	// Texturas para diferentes estados
	private Texture defaultTexture;
	private Texture pressedTexture;
	private Texture hoverTexture;
	private boolean withSound = true;

	// Para manejar el estado del clic
	private boolean wasClicked = false;

	// Constructor principal
	public Button() {
		super(new Texture("assets/textures/gui/Button.png", 18, 18));
		this.defaultTexture = new Texture("assets/textures/gui/Button.png", 18, 18);
		this.setHoverTexture(getTextureFromTheme(config, "hover"));
		this.setPressedTexture(getTextureFromTheme(config, "pressed"));
	}

	// Métodos encadenados para la configuración

	public Button setPressedTexture(Texture pressedTexture) {
		this.pressedTexture = pressedTexture;
		return this;
	}

	public Button setHoverTexture(Texture hoverTexture) {
		this.hoverTexture = hoverTexture;
		return this;
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

	public Button setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	// Método para obtener la textura desde el tema
	private Texture getTextureFromTheme(ElementConfig config, String state) {
		String texturePath = themeManager.getProperties(config.getTheme()).get(getClass().getSimpleName() + "." + state);
		return new Texture(texturePath, getWidth(), getHeight()); // Crea una nueva textura con la ruta obtenida
	}

	// Métodos de la interfaz IButton
	@Override
	public boolean isHovered() {
		return mx >= this.x && my >= this.y && mx < x + getWidth() && my < y + getHeight();
	}

	@Override
	public boolean isOn() {
		return activated; // Activo cuando está activado
	}

	@Override
	public boolean isDisabled() {
		return !activated; // Si no está activado, se considera deshabilitado
	}

	@Override
	public boolean isToggle() {
		return toggleMode;
	}

	@Override
	public void toggle(boolean activate) {
		// Cambia el estado del botón dependiendo del valor pasado si no está en modo toggle
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
			if (buttonDown) { // Left mouse button is pressed
				if (!wasClicked) { // Check if the button was not clicked in this update
					onPush(); // Execute onPush action
					wasClicked = true; // Mark as clicked

					if (toggleMode) {
						// Toggle the button state if in toggle mode
						activated = !activated;
					}
				}
				// Set texture to pressed if button is pressed
				setTexture(pressedTexture != null ? pressedTexture : defaultTexture);
			} else {
				// Mouse was over the button but is no longer pressed
				if (wasClicked) {
					// Execute onRelease action if button was clicked and is now released
					onRelease(); // Execute onRelease action
				}
				// Update texture based on hover state
				setTexture(hoverTexture != null ? hoverTexture : defaultTexture);
				wasClicked = false; // Reset clicked state
			}
		} else {
			// Mouse is not over the button
			if (toggleMode && activated) {
				// If in toggle mode and activated, show pressed texture
				setTexture(pressedTexture != null ? pressedTexture : defaultTexture);
			} else {
				// Default texture if not in toggle mode or not activated
				setTexture(defaultTexture);
			}
			wasClicked = false; // Reset clicked state
		}

		// Execute whilePressed action if the button is pressed and held
		if (buttonDown) {
			whilePressed();
		}
	}


	@Override
	public void onPush() {

		if (withSound) {
			this.mc.sndManager.playSound(soundName, SoundCategory.GUI_SOUNDS, 1.0F, 1.0F);
		}

		if (onPush != null) {
			onPush.execute(this); // Ejecuta la acción cuando se presiona el botón
		}
	}

	@Override
	public void onRelease() {
		if (onRelease != null) {
			onRelease.execute(this); // Ejecuta la acción del clic si está definida
		}
	}

	@Override
	public void whilePressed() {
		if (whilePressed != null) {
			whilePressed.execute(this); // Ejecuta la acción mientras el botón esté presionado
		}
	}

	public boolean isWithSound() {
		return withSound;
	}
}
