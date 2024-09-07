package deus.guilib.element.interfaces;

import deus.guilib.element.interfaces.element.IElement;

/**
 * Functional interface for executing an action on an {@link IElement}.
 * It is intended for defining custom behaviors in the GUI.
 */
@FunctionalInterface
public interface ILambda {

	/**
	 * Executes an action on the specified {@link IElement}.
	 *
	 * @param element The element on which the action is performed.
	 */
	void execute(IElement element);
}
