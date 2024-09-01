package deus.guilib.rendering.advanced;

import deus.guilib.rendering.base.Element;
import deus.guilib.rendering.base.interfaces.IButton;
import deus.guilib.rendering.base.organization.ElementConfig;
import deus.guilib.rendering.base.organization.childrenPlacement;
import deus.guilib.rendering.resource.Texture;
import org.lwjgl.input.Mouse;

public class Button extends Element implements IButton {

	private int mx = 0;
	private int my = 0;
	protected boolean activated = true; // true: activado, false: desactivado
	private boolean toggleMode = false; // Para habilitar o deshabilitar el modo de toggle
	private Runnable onClickAction; // Acción a ejecutar cuando se haga clic

	// Para manejar el estado del clic
	private boolean wasClicked = false;
	private boolean isPressed = false; // Para detectar el estado de presionado del ratón

	// Constructores de la clase Button
	public Button(Texture texture, Runnable onClickAction) {
		super(texture);
		this.onClickAction = onClickAction;
	}

	public Button(Texture texture, Runnable onClickAction, Element... children) {
		super(texture, children);
		this.onClickAction = onClickAction;
	}

	public Button(Texture texture, int x, int y, Runnable onClickAction, Element... children) {
		super(texture, x, y, children);
		this.onClickAction = onClickAction;
	}

	public Button(Texture texture, int x, int y, childrenPlacement placement, Runnable onClickAction) {
		super(texture, x, y, placement);
		this.onClickAction = onClickAction;
	}

	public Button(Texture texture, int x, int y, Runnable onClickAction) {
		super(texture, x, y);
		this.onClickAction = onClickAction;
	}

	public Button(Texture texture, int x, int y, ElementConfig config, Runnable onClickAction, Element... children) {
		super(texture, x, y, config, children);
		this.onClickAction = onClickAction;
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
	public void setToggleMode() {
		toggleMode = !toggleMode; // Cambia el estado del modo de toggle
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

		// Actualiza el estado del ratón
		isPressed = Mouse.isButtonDown(0); // Estado del clic izquierdo

		// Verificar si el botón está siendo presionado y si está sobre el botón
		if (isHovered()) {
			if (isPressed) {
				if (!wasClicked) { // Verifica si no se ha hecho clic en esta actualización
					onClick();
					wasClicked = true; // Marca como clickeado
				}
			} else {
				wasClicked = false; // Restablece la bandera si no se está presionando el botón
			}
		} else {
			wasClicked = false; // Restablece la bandera si el mouse no está sobre el botón
		}
	}

	// Método para manejar el clic en el botón
	@Override
	public void onClick() {
		System.out.println("AAAAAAAAAAA");
		if (onClickAction != null) {
			onClickAction.run(); // Ejecuta la acción del clic si está definida
		}
	}
}
