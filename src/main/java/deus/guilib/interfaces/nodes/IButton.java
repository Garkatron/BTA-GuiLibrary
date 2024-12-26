package deus.guilib.interfaces.nodes;

import deus.guilib.interfaces.ILambda;
import deus.guilib.nodes.types.interaction.Button;

import java.util.Optional;

/**
 * Interface for button elements, extending the clickable interface with toggle functionality and disabled state handling.
 */
public interface IButton extends IClickable {

	/**
	 * Checks if the button is currently in the "on" state.
	 *
	 * @return true if the button is "on", false otherwise.
	 */
	boolean isOn();

	/**
	 * Checks if the button is disabled.
	 *
	 * @return true if the button is disabled, false otherwise.
	 */
	boolean isDisabled();

	/**
	 * Sets the button to toggle mode.
	 *
	 * @param bool true to enable toggle mode, false to disable it.
	 * @return The element instance for chaining.
	 */
	INode setToggleMode(boolean bool);

	/**
	 * Checks if the button is in toggle mode.
	 *
	 * @return true if the button is in toggle mode, false otherwise.
	 */
	boolean isToggle();

	/**
	 * Toggles the button state.
	 *
	 * @param activate true to activate the button, false to deactivate it.
	 */
	void toggle(boolean activate);


	IButton setOnReleaseAction(ILambda onRelease);

	IButton setOnPushAction(ILambda onPush);

	IButton setWhilePressedAction(ILambda whilePressed);

}
