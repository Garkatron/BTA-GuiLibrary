package deus.guilib.interfaces;

import deus.guilib.interfaces.element.INode;

@FunctionalInterface
public interface IChildLambda {
	void apply(INode element);
}
