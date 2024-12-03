package deus.guilib.interfaces;

import deus.guilib.interfaces.nodes.INode;

/**
 * Functional interface for executing an action on an {@link INode}.
 * It is intended for defining custom behaviors in the GUI.
 */
@FunctionalInterface
public interface ILambda {

	/**
	 * Executes an action on the specified {@link INode}.
	 *
	 * @param element The element on which the action is performed.
	 */
	void execute(INode element);
}
